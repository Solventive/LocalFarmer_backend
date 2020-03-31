package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Posting;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.PostingsRepository;

@RestController
@RequestMapping(path = "/v1/postings")
public class PostingsController {

    @Autowired
    private PostingsRepository repository;
    @Autowired
    private PostingsValidator validator;

    @GetMapping(path = "")
    public Iterable<Posting> getAllPostings() {

        return repository.findAll();
    }


    @GetMapping(path = "/{id}")
    public Posting getPosting(@PathVariable("id") Integer postingId) {
        if (repository.findById(postingId).isPresent()) {
            return repository.findById(postingId).get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "posting not found"
            );
        }
    }

    @PostMapping(path = "/")
    Posting newPosting(@RequestBody Posting posting) {
        if (validator.verifyPosting(posting)) {
            if (validator.verifyUserId(posting.getUserId())) {
                return repository.save(posting);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "User does not exist"
                );
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Posting body is incorrect"
            );
        }

    }


}
