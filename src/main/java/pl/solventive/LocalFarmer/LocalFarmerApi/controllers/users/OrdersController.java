package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Order;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.OrdersRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.security.services.LFUserPrincipal;
import pl.solventive.LocalFarmer.LocalFarmerApi.util.RequestHandler;

@RestController
@RequestMapping(path = "/v1/postings", produces={"application/json; charset=UTF-8"})
public class OrdersController {

    @Autowired
    private OrdersRepository repository;

    @GetMapping(path = "")
    public Iterable<Order> getAllOrders() {
        return RequestHandler.handleList(repository.findByOrderByCreatedAtDesc());
    }

    @GetMapping(path = "/{id}")
    public Order getOrder(@PathVariable("id") String orderId) {
        return RequestHandler.handleSingle(repository.getById(orderId), "order");
    }

    @PostMapping(path = "")
    public Order newOrder(@RequestBody Order order) {
        LFUserPrincipal principal = (LFUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setUserId(principal.getUserId());
        if (order.getStatus() == null) order.setStatus(1);
        return repository.save(order);
    }

    @DeleteMapping(path = "")
    public ResponseEntity deleteAllOrders() {
        repository.deleteAll();
        return ResponseEntity.ok().body(null);
    }
}
