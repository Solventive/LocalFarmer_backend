package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.LFUser;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.UserType;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.UserTypesRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.UsersRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.util.RequestHandler;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/v1/users", produces = {"application/json; charset=UTF-8"})
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserTypesRepository typesRepository;

    @GetMapping(path = "")
    public Iterable<LFUser> getUsers() {
        return RequestHandler.getList(usersRepository.findAll());
    }

    @GetMapping(path = "/types")
    public Iterable<UserType> getTypes() {
        return RequestHandler.getList(typesRepository.findAll());
    }

    @GetMapping(path = "/verify/{phoneNumber}")
    public Boolean verifyPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return usersRepository.findUserByPhoneNumber(phoneNumber).isPresent();
    }

    @DeleteMapping(path = "")
    public String deleteAllUsers() {
        usersRepository.deleteAll();
        return "Users deleted";
    }

    @GetMapping(path = "/{id}")
    public LFUser getUser(@PathVariable("id") Integer userId) {
        return RequestHandler.getSingle(usersRepository.findById(userId), "user");
    }

    @PostMapping(path = "/register")
    LFUser newUser(@Validated @RequestBody LFUser newUser) {
        newUser.setRatings(0);
        newUser.setRatingPoints(0.0);
        String encodedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setCreatedAt(LocalDateTime.now());
        return usersRepository.save(newUser);
    }

    @PutMapping(path = "/edit")
    LFUser putUser(@Validated @RequestBody LFUser newUser) {
        LFUser user = RequestHandler.getSingle(usersRepository.findById(RequestHandler.getUserId()), "user");
        if (newUser.getLocationId() != null) user.setLocationId(newUser.getLocationId());
        if (newUser.getName() != null) user.setName(newUser.getName());
        if (newUser.getSurname() != null) user.setSurname(newUser.getSurname());
        if (newUser.getDescription() != null) user.setDescription(newUser.getDescription());
        if (newUser.getBackgroundPhotoId() != null) user.setBackgroundPhotoId(newUser.getBackgroundPhotoId());
        if (newUser.getProfilePhotoId() != null) user.setProfilePhotoId(newUser.getProfilePhotoId());
        user.setLastModifiedAt(LocalDateTime.now());
        return usersRepository.save(user);
    }
}
