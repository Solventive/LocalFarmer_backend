package pl.solventive.LocalFarmer.LocalFarmerApi.controllers.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.ProductCategory;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.ProductUnit;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.ProductCategoriesRepository;
import pl.solventive.LocalFarmer.LocalFarmerApi.repositories.ProductUnitsRepository;

@RestController
@RequestMapping(path = "/v1/products")
public class ProductsController {

    @Autowired
    private ProductUnitsRepository productUnitsRepository;
    @Autowired
    private ProductCategoriesRepository productCategoriesRepository;

    @GetMapping(path = "/categories")
    public Iterable<ProductCategory> getProductCategories() {

        return productCategoriesRepository.findAll();
    }

    @GetMapping(path = "/units")
    public Iterable<ProductUnit> getProductUnits() {

        return productUnitsRepository.findAll();
    }

    @PostMapping(path = "/units")
    public ProductUnit postProductUnit(@RequestBody ProductUnit unit) {

        return productUnitsRepository.save(unit);
    }

    @PostMapping(path = "/categories")
    public ProductCategory getProductCategory(@RequestBody ProductCategory category) {

        return productCategoriesRepository.save(category);
    }

    @PutMapping(path = "/units")
    public ProductUnit putProductUnit(@RequestBody ProductUnit unit) {

        return productUnitsRepository.save(unit);
    }

    @PutMapping(path = "/categories")
    public ProductCategory putProductCategory(@RequestBody ProductCategory category) {

        return productCategoriesRepository.save(category);
    }

    @DeleteMapping(path = "/units/{id}")
    public String deleteProductUnit(@PathVariable("id") Integer id) {

        productCategoriesRepository.deleteById(id);
        return "category" + id + " deleted";
    }

    @DeleteMapping(path = "/categories/{id}")
    public String deleteProductCategory(@PathVariable("id") Integer id) {

        productCategoriesRepository.deleteById(id);
        return "category" + id + " deleted";
    }
}
