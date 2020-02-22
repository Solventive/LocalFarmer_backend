package pl.solventive.LocalFarmer.LocalFarmerApi.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.LFUser;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.UsersRepository;

@Component
public class LFUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) {
        LFUser user = userRepository.getUserByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UsernameNotFoundException(phoneNumber);
        }
        return new LFUserPrincipal(user);
    }


}
