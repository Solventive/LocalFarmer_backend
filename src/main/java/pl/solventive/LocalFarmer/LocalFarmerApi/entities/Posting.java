package pl.solventive.LocalFarmer.LocalFarmerApi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name="postings")
@Table(name="postings")
public class Posting {

    // Fields
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Integer userId;
    @NonNull
    private Double price;
    @NonNull
    private Integer quantityUnitId;
    @NonNull
    private Integer categoryId;
    @NonNull
    private Double quantity;
    @ElementCollection(targetClass = String.class)
    private List<String> tags;
    @NonNull
    private String mainPhotoId;
    @NonNull
    @ElementCollection(targetClass = String.class)
    private List<String> photos;
    private String location;
    @NonNull
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @NonNull
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expiryDate;
    @NonNull
    private Integer status;
    @Embedded
    @ElementCollection(targetClass = PostingDelivery.class)
    private List<PostingDelivery> deliveries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getMainPhotoId() {
        return mainPhotoId;
    }

    public void setMainPhotoId(String mainPhotoId) {
        this.mainPhotoId = mainPhotoId;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityUnitId() {
        return quantityUnitId;
    }

    public void setQuantityUnitId(Integer quantityUnitId) {
        this.quantityUnitId = quantityUnitId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<PostingDelivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<PostingDelivery> deliveries) {
        this.deliveries = deliveries;
    }
}
