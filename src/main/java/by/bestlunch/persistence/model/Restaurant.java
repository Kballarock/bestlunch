package by.bestlunch.persistence.model;

import by.bestlunch.persistence.model.base.AbstractNamedEntity;
import by.bestlunch.util.DateTimeUtil;
import by.bestlunch.validation.view.ErrorSequence;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {

    @NotBlank(message = "{restaurant.NotBlank.description}", groups = ErrorSequence.First.class)
    @Size(min = 2, max = 150, message = "{restaurant.Size.description}", groups = ErrorSequence.Second.class)
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank(message = "{restaurant.NotBlank.address}", groups = ErrorSequence.First.class)
    @Size(min = 2, max = 150, message = "{restaurant.Size.address}", groups = ErrorSequence.Second.class)
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "added", nullable = false)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime added = LocalDateTime.now();

    public Restaurant() {
        super();
    }

    public Restaurant(String name, String description, String address) {
        this(null, name, description, address, LocalDateTime.now());
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getDescription(), restaurant.getAddress(), restaurant.getAdded());
    }

    public Restaurant(Integer id, String name, String description, String address, LocalDateTime added) {
        super(id, name);
        this.description = description;
        this.address = address;
        this.added = added;
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

    public LocalTime getTime() {
        return added.toLocalTime();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                "description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", added=" + added +
                '}';
    }
}