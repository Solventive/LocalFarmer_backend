package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import pl.solventive.LocalFarmer.LocalFarmerApi.entities.LFUser;

public class UsersValidator {

    static Boolean validateRegisterUser(LFUser newUser) {
        return newUser.getPassword() != null &&
                newUser.getName() != null &&
                newUser.getAccountType() != null &&
                newUser.getPhoneNumber() != null &&
                newUser.getCreatedAt() != null;
    }
}
