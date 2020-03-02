package by.bestlunch.util;

import by.bestlunch.persistence.model.Menu;
import by.bestlunch.web.dto.MenuDto;

import java.time.LocalDate;

public class MenuUtil {

    public static Menu createNewFromDto(MenuDto menuDto) {
        return new Menu(null, menuDto.getName(), menuDto.getPrice(), LocalDate.now());
    }

    public static Menu updateFromDto(Menu item, MenuDto itemDto) {
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        return item;
    }
}