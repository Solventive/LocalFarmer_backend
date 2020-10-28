package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.LFLocation;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.LocationsRepository;

@RestController
@RequestMapping(path = "/v1/locations", produces={"application/json; charset=UTF-8"})
public class LocationsController {

    @Autowired
    private LocationsRepository repository;
    @Autowired
    private LocationsValidator validator;

    @GetMapping(path = "")
    @ApiResponses(@ApiResponse(code = 200, message = "Success", response = LFLocation.class))
    public Iterable<LFLocation> getLocations(@RequestParam(name = "userId", required = false) Integer userId) {
        if (userId != null) {
            if (validator.verifyUserId(userId)) {
                return repository.findByUserId(userId);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                );
            }
        } else return repository.findAll();
    }

    @GetMapping(path = "/{id}")
    @ApiResponses(@ApiResponse(code = 200, message = "Success", response = LFLocation.class))
    public LFLocation getLocation(@PathVariable("id") String locationId) {
        if (repository.findById(locationId).isPresent()) {
            return repository.findById(locationId).get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "posting not found"
            );
        }
    }

    @PostMapping(path = "")
    public LFLocation newLocation(@RequestBody LFLocation location) {
        if (validator.verifyLocation(location)) {
//            LFUserPrincipal principal = (LFUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (validator.verifyUserId(location.getUserId())) {
                return repository.save(location);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "User does not exist"
                );
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Location body is incorrect"
            );
        }
    }

    @DeleteMapping(path = "")
    public ResponseEntity deleteAllLocations() {
        repository.deleteAll();
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteLocation(@PathVariable("id") String locationId) {
        repository.deleteById(locationId);
        return ResponseEntity.ok().body(null);
    }
}
