package pl.solventive.LocalFarmer.LocalFarmerApi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name="order")
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private long number;
    @NotNull(message = "userId cannot be null")
    private Integer userId;
    @NotNull(message = "sellerId cannot be null")
    private Integer sellerId;
    @NotNull(message = "Price cannot be null")
    private Double price;
    @NotNull(message = "quantityUnitId cannot be null")
    private Integer quantityUnitId;
    @NotNull(message = "categoryId cannot be null")
    private Integer categoryId;
    @NotNull(message = "quantity cannot be null")
    private Double quantity;
    @NotNull(message = "mainPhotoId cannot be null")
    private String mainPhotoId;
    @NotNull(message = "location cannot be null")
    private String location;
    @NotNull(message = "createdAt cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @NotNull(message = "deliveries cannot be null")
    private Integer status;
    @NotNull(message = "Price cannot be null")
    @Valid
    @ElementCollection(targetClass = PostingDelivery.class)
    private List<PostingDelivery> deliveries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantityUnitId() {
        return quantityUnitId;
    }

    public void setQuantityUnitId(Integer quantityUnitId) {
        this.quantityUnitId = quantityUnitId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMainPhotoId() {
        return mainPhotoId;
    }

    public void setMainPhotoId(String mainPhotoId) {
        this.mainPhotoId = mainPhotoId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PostingDelivery> getDelivery() {
        return deliveries;
    }

    public void setDelivery(List<PostingDelivery> deliveries) {
        this.deliveries = deliveries;
    }
}
