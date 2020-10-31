package pl.solventive.LocalFarmer.LocalFarmerApi.entities;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
class PostingDelivery {
    @Embedded
    private DeliveryType type;
    private Float price;
    private Float maxWeight;
    private Float maxDistance;
}
