package pl.solventive.LocalFarmer.LocalFarmerApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Order;

import java.util.List;

@Component
public interface OrdersRepository extends JpaRepository<Order, String> {

    @Query("SELECT o FROM orders o WHERE o.id = ?1")
    Order getById(String id);

    @Query("SELECT o FROM orders o WHERE o.userId = ?1")
    List<Order> findOrdersByUserId(String name);

    List<Order> findByOrderByCreatedAtDesc();
}
