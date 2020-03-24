package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.LFUser;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.UsersRepository;

@RestController
@RequestMapping(path = "/v1/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(path = "")
    public Iterable<LFUser> getAllUsers() {

        return usersRepository.findAll();
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
        if (usersRepository.findById(userId).isPresent()) {
            return usersRepository.findById(userId).get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found"
            );
        }
    }

    @PostMapping(path = "/register")
    LFUser newUser(@RequestBody LFUser newUser) {
        if (UsersValidator.validateRegisterUser(newUser)) {
            String encodedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
            newUser.setPassword(encodedPassword);
            return usersRepository.save(newUser);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "bad user body"
            );
        }

    }


}
