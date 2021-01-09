package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.DeliveryType;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Posting;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.DBFileRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.DeliveryTypesRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.PostingsRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/postings", produces={"application/json; charset=UTF-8"})
public class PostingsController {

    @Autowired
    private PostingsRepository repository;
    @Autowired
    private DeliveryTypesRepository deliveryTypeRepo;
    @Autowired
    private DBFileRepository fileRepo;
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
        ArrayList<String> photos = new ArrayList<String>();
        List<Posting> list = repository.findAll();
        list.forEach((posting) -> photos.addAll(posting.getPhotos()));
        photos.forEach((photo) -> fileRepo.deleteById(photo));
        repository.deleteAll();
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(path = "/deliveryTypes")
    public Iterable<DeliveryType> getDeliveryTypes() {
        return deliveryTypeRepo.findAll();
    }

    @PostMapping(path = "/deliveryTypes")
    public DeliveryType postDeliveryType(@RequestBody DeliveryType type) {
        if (type.getTuFarmerService() != null && type.getName() != null && type.getApiName() != null) {
            return deliveryTypeRepo.save(type);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Delivery type body is incorrect");
        }
    }

    @DeleteMapping(path = "/deliveryTypes")
    public ResponseEntity deleteDeliveryType(@RequestParam("id") Integer id) {
        deliveryTypeRepo.deleteById(id);
        return ResponseEntity.ok().body(null);
    }
}
