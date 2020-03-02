package by.bestlunch.persistence.model;

import by.bestlunch.persistence.model.base.AbstractNamedEntity;
import by.bestlunch.util.DateTimeUtil;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu")
public class Menu extends AbstractNamedEntity {

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    @Column(name = "menu_date", nullable = false, columnDefinition = "date default (current_date + interval 1 year)")
    private LocalDate date;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Integer id, String name, Double price, LocalDate date, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
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
                "price=" + price +
                ", date=" + date +
                ", restaurant=" + restaurant +
                '}';
    }
}