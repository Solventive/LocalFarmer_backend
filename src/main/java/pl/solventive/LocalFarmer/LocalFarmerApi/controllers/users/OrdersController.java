package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Order;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.OrdersRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.util.RequestHandler;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/orders", produces={"application/json; charset=UTF-8"})
public class OrdersController {

    @Autowired
    private OrdersRepository repository;

    @GetMapping(path = "")
    public Iterable<Order> getAllOrders() {
        return RequestHandler.getList(repository.findByOrderByCreatedAtDesc());
    }

    @GetMapping(path = "/{id}")
    public Order getOrder(@PathVariable("id") String orderId) {
        return RequestHandler.getSingle(repository.findById(orderId), "order");
    }

    @PostMapping(path = "")
    public Order newOrder(@Valid @RequestBody Order order) {
        order.setUserId(RequestHandler.getUserId());
        if (order.getStatus() == null) order.setStatus(1);
        @Valid Order completeOrder = order;
        return repository.save(completeOrder);
    }

    @DeleteMapping(path = "")
    public ResponseEntity deleteAllOrders() {
        repository.deleteAll();
        return ResponseEntity.ok().body(null);
    }
}
