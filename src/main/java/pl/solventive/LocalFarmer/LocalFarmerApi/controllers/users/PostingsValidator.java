package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Posting;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.UsersRepository;

@Component
public class PostingsValidator {

    @Autowired
    private UsersRepository usersRepository;

    public Boolean verifyUserId(Integer userId) {
        return usersRepository.findById(userId).isPresent();
    }

    public Boolean verifyPosting(Posting posting) {
         if (
                posting.getTitle() != null &&
                        posting.getDescription() != null &&
                        posting.getUserId() != null &&
                        posting.getPrice() != null &&
                        posting.getUnitId() != null &&
                        posting.getCategoryId() != null &&
                        posting.getTags() != null &&
                        posting.getPhotos() != null &&
                        posting.getCreatedAt() != null &&
                        posting.getExpiryDate()!= null
        ) {
            return true;
        } else {
            return false;
        }
    }
}
