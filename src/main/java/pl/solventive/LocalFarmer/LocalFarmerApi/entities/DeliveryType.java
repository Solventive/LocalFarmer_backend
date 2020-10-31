package pl.solventive.LocalFarmer.LocalFarmerApi.entities;

import javax.persistence.*;

@Embeddable
@Entity
@Table(name = "deliveryTypes")
public class DeliveryType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String apiName;

    private Boolean isTuFarmerService;

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

    public Boolean getTuFarmerService() {
        return isTuFarmerService;
    }

    public void setTuFarmerService(Boolean tuFarmerService) {
        isTuFarmerService = tuFarmerService;
    }
}
