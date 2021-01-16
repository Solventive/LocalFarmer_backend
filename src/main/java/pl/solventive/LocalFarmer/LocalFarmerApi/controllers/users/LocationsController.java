package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.LFLocation;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.LocationsRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.util.RequestHandler;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/locations", produces={"application/json; charset=UTF-8"})
public class LocationsController {

    @Autowired
    private LocationsRepository repository;

    @GetMapping(path = "")
    @ApiResponses(@ApiResponse(code = 200, message = "Success", response = LFLocation.class))
    public Iterable<LFLocation> getLocations(@RequestParam(name = "userId", required = false) Integer userId) {
        if (userId != null) {
            return RequestHandler.getList(repository.findByUserId(RequestHandler.getUserId()));
        } else {
            return RequestHandler.getList(repository.findAll());
        }
    }

    @GetMapping(path = "/{id}")
    @ApiResponses(@ApiResponse(code = 200, message = "Success", response = LFLocation.class))
    public LFLocation getLocation(@PathVariable("id") String locationId) {
        return RequestHandler.getSingle(repository.findById(locationId), "location");
    }

    @PostMapping(path = "")
    public LFLocation newLocation(@ApiParam(name = "location") @RequestBody @Valid LFLocation lfLocation) {
        lfLocation.setUserId(RequestHandler.getUserId());
        return repository.save(lfLocation);
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
