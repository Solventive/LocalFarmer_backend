package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Posting;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.PostingsRepository;

@RestController
@RequestMapping(path = "/v1/postings", produces={"application/json; charset=UTF-8"})
public class PostingsController {

    @Autowired
    private PostingsRepository repository;
    @Autowired
    private PostingsValidator validator;

    @GetMapping(path = "")
    public Iterable<Posting> getAllPostings() {

        return repository.findByOrderByCreatedAtDesc();
    }


    @GetMapping(path = "/{id}")
    public Posting getPosting(@PathVariable("id") String postingId) {
        if (repository.getById(postingId) != null) {
            return repository.getById(postingId);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "posting not found"
            );
        }
    }

    @PostMapping(path = "")
    public Posting newPosting(@RequestBody Posting posting) {
        if (validator.verifyPosting(posting)) {
            if (validator.verifyUserId(posting.getUserId())) {
                if (posting.getStatus() == null) posting.setStatus(1);
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

    @DeleteMapping(path = "")
    public ResponseEntity deleteAllPostings() {
        repository.deleteAll();
        return ResponseEntity.ok().body(null);
    }


}
