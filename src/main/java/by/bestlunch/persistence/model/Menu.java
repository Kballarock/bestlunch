package by.bestlunch.persistence.model;

import by.bestlunch.persistence.model.base.AbstractBaseEntity;
import by.bestlunch.util.DateTimeUtil;
import by.bestlunch.validation.view.ErrorSequence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "menu")
public class Menu extends AbstractBaseEntity {

    @NotBlank(message = "{menu.NotBlank.name}", groups = ErrorSequence.First.class)
    @Size(min = 2, max = 255, message = "{menu.Size.name}", groups = ErrorSequence.Second.class)
    private String name;

    @NotNull(message = "{menu.NotNull.price}", groups = ErrorSequence.First.class)
    @DecimalMin(value = "0.00", message = "{menu.DecimalMin.price}", groups = ErrorSequence.Second.class)
    @DecimalMax(value = "9999.00", message = "{menu.DecimalMax.price}", groups = ErrorSequence.Second.class)
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    @Column(name = "menu_date", nullable = false, columnDefinition = "date default (current_date + interval 1 year)")
    private LocalDate date = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Menu() {
        super();
    }

    public Menu(String name, Double price) {
        this(null, name, price, LocalDate.now());
    }

    public Menu(Integer id, String name, Double price, LocalDate date) {
        super(id);
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                "name=" + name +
                "price=" + price +
                ", date=" + date +
                ", restaurant=" + restaurant +
                '}';
    }
}