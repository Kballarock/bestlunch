package by.bestlunch.persistence.model;

import by.bestlunch.persistence.model.base.AbstractNamedEntity;
import by.bestlunch.util.DateTimeUtil;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    private LocalDate date = LocalDate.now();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Menu() {
        super();
    }

    public Menu(String name, Double price) {
        this(null, name, price, LocalDate.now());
    }

    public Menu(Integer id, String name, Double price, LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
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