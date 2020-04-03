package by.bestlunch;

import by.bestlunch.persistence.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;

public class MenuTestData {

    public static final Menu MENU_ITEM1 = new Menu(100000, "Гамбургер", 4.39, of(2019, 12, 16));
    public static final Menu MENU_ITEM2 = new Menu(100001, "Кока Кола", 1.2, of(2019, 12, 16));
    public static final Menu MENU_ITEM3 = new Menu(100006, "Паста", 10.60, of(2019, 12, 16));
    public static final Menu MENU_ITEM4 = new Menu(100007, "Сибас", 16.63, of(2019, 12, 16));

    public static final List<Menu> MENU_ITEMS = List.of(MENU_ITEM3, MENU_ITEM4);

    public static Menu getNew() {
        return new Menu(null, "NewItem", 5.06, LocalDate.now());
    }

    public static Menu getUpdated() {
        Menu updated = new Menu(MENU_ITEM2);
        updated.setName("UpdatedItem");
        updated.setPrice(7.99);
        return updated;
    }

    public static TestMatchers<Menu> MENU_MATCHERS = TestMatchers.useFieldsComparator(Menu.class, "restaurant", "data");
}
