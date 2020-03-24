package pl.solventive.LocalFarmer.LocalFarmerApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.LFUser;

import java.util.Optional;

@Component
public interface UsersRepository extends JpaRepository<LFUser, Integer> {

    @Query("SELECT u FROM users u WHERE u.phoneNumber = ?1")
    Optional<LFUser> findUserByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM users u WHERE u.name = ?1")
    LFUser getUserByName(String name);

    @Query("SELECT u FROM users u WHERE u.phoneNumber = ?1")
    LFUser getUserByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM users u WHERE u.id = ?1")
    LFUser getUserById(int id);
}
