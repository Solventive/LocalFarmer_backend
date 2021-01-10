package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.DeliveryType;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Posting;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.DBFileRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.DeliveryTypesRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.PostingsRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.util.RequestHandler;

import javax.validation.Valid;
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
        return RequestHandler.getSingle(repository.findById(postingId), "posting");
    }

    @PostMapping(path = "")
    public Posting newPosting(@RequestBody Posting posting) {
        posting.setUserId(RequestHandler.getUserId());
        if (posting.getStatus() == null) posting.setStatus(1);
        @Valid Posting completePosting = posting;
        return repository.save(completePosting);
    }

    @DeleteMapping(path = "")
    public ResponseEntity deleteAllPostings() {
        ArrayList<String> photos = new ArrayList<>();
        List<Posting> list = repository.findAll();
        list.forEach((posting) -> photos.addAll(posting.getPhotos()));
        photos.forEach((photo) -> fileRepo.deleteById(photo));
        repository.deleteAll();
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(path = "/deliveryTypes")
    public Iterable<DeliveryType> getDeliveryTypes() {
        return RequestHandler.getList(deliveryTypeRepo.findAll());
    }

    @PostMapping(path = "/deliveryTypes")
    public DeliveryType postDeliveryType(@Valid @RequestBody DeliveryType type) {
        return deliveryTypeRepo.save(type);
    }

    @DeleteMapping(path = "/deliveryTypes")
    public ResponseEntity deleteDeliveryType(@RequestParam("id") Integer id) {
        deliveryTypeRepo.deleteById(id);
        return ResponseEntity.ok().body(null);
    }
}
