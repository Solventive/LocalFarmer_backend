package pl.solventive.LocalFarmer.LocalFarmerApi.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "productUnits")
public class ProductUnit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String apiName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
}
