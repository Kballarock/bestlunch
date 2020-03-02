package by.bestlunch.web.dto;

import by.bestlunch.validation.view.ErrorSequence;

import javax.validation.constraints.*;

public class MenuDto extends BaseDto {

    @NotBlank(message = "{menuDto.NotBlank.name}", groups = ErrorSequence.First.class)
    @Size(min = 2, max = 255, message = "{menuDto.Size.name}", groups = ErrorSequence.Second.class)
    private String name;

    @NotBlank(message = "{menuDto.NotBlank.price}", groups = ErrorSequence.First.class)
    @DecimalMin(value = "0.0", message = "{menuDto.DecimalMin.price}", groups = ErrorSequence.Second.class)
    @DecimalMax(value = "9999.0", message = "{menuDto.DecimalMax.price}", groups = ErrorSequence.Second.class)
    private Double price;

    public MenuDto() {
    }

    public MenuDto(Integer id, String name, Double price) {
        super(id);
        this.name = name;
        this.price = price;
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

    @Override
    public String toString() {
        return "MenuDto{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}