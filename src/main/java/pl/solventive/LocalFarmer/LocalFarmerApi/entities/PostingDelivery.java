package pl.solventive.LocalFarmer.LocalFarmerApi.entities;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

@Embeddable
class PostingDelivery {
    @Embedded
    private DeliveryType type;
    @NotNull(message = "Price cannot be null")
    private Float price;
    private Float maxWeight;
    private Float maxDistance;

    public DeliveryType getType() {
        return type;
    }

    public void setType(DeliveryType type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Float getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Float maxDistance) {
        this.maxDistance = maxDistance;
    }
}
