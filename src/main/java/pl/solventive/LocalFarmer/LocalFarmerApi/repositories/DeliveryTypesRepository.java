package pl.solventive.LocalFarmer.LocalFarmerApi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.DeliveryType;

@Repository
public interface DeliveryTypesRepository extends JpaRepository<DeliveryType, Integer> {

}
