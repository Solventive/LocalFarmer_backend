package pl.solventive.LocalFarmer.LocalFarmerApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.LFLocation;

@Component
public interface LocationsRepository extends JpaRepository<LFLocation, String> {

    @Query("SELECT l FROM locations l WHERE l.userId = ?1")
    Iterable<LFLocation> findByUserId(Integer userId);
}
