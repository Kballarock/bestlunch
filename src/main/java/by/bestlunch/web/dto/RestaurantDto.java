package by.bestlunch.web.dto;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class RestaurantDto extends BaseDto {

    private String name;
    private String description;
    private String address;
    private LocalDateTime added;
    private Integer amount;
    private Boolean vote;

    public RestaurantDto() {
    }

    @ConstructorProperties({"id", "name", "description", "address", "added", "amount", "vote"})
    public RestaurantDto(Integer id, String name, String description, String address, LocalDateTime added, Integer amount, Boolean vote) {
        super(id);
        this.name = name;
        this.description = description;
        this.address = address;
        this.amount = amount;
        this.added = added;
        this.vote = vote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getAdded() {
        return added;
    }

    public void setAdded(LocalDateTime added) {
        this.added = added;
    }

    public LocalDate getDate() {
        return added.toLocalDate();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getVote() {
        return vote;
    }

    public void setVote(Boolean vote) {
        this.vote = vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDto that = (RestaurantDto) o;
        return amount.equals(that.amount) &&
                vote == that.vote &&
                Objects.equals(id, that.id) &&
                Objects.equals(added, that.added) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, added, amount, vote);
    }

    @Override
    public String toString() {
        return "RestaurantDto{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", amount=" + amount +
                ", vote=" + vote +
                '}';
    }
}