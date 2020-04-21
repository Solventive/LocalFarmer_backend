package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.LFLocation;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.UsersRepository;

@Component
public class LocationsValidator {

    @Autowired
    private UsersRepository usersRepository;

    public Boolean verifyUserId(Integer userId) {
        return usersRepository.findById(userId).isPresent();
    }

    public Boolean verifyLocation(LFLocation location) {
        return location.getLatitude() != null &&
                location.getLongitude() != null &&
                location.getCity() != null &&
                location.getUserId() != null &&
                location.getName() != null;
    }
}
