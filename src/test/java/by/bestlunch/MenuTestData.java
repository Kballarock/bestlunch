package by.bestlunch;

import by.bestlunch.persistence.model.Menu;

import java.util.List;

import static java.time.LocalDate.of;

public class MenuTestData {

    public static final Menu ITEM1 = new Menu(100000, "Гамбургер", 4.39, of(2019, 12, 16));
    public static final Menu ITEM2 = new Menu(100001, "Кока Кола", 1.2, of(2019, 12, 16));
    public static final Menu ITEM3 = new Menu(100002, "Хот дог", 2.0, of(2019, 12, 17));
    public static final Menu ITEM4 = new Menu(100003, "Картофель фри", 3.45, of(2019, 12, 17));
    public static final Menu ITEM5 = new Menu(100004, "Чизбурер", 5.21, of(2019, 12, 18));
    public static final Menu ITEM6 = new Menu(100005, "Воппер", 2.67, of(2019, 12, 18));
    public static final Menu ITEM7 = new Menu(100006, "Паста", 10.60, of(2019, 12, 16));
    public static final Menu ITEM8 = new Menu(100007, "Сибас", 16.63, of(2019, 12, 16));
    public static final Menu ITEM9 = new Menu(100008, "Суп-пюре", 7.35, of(2019, 12, 17));
    public static final Menu ITEM10 = new Menu(100009, "Паста карбонара", 15.39, of(2019, 12, 17));
    public static final Menu ITEM11 = new Menu(100010, "Пицца", 16.23, of(2019, 12, 18));
    public static final Menu ITEM12 = new Menu(100011, "'Брускетта с помидорами", 7.35, of(2019, 12, 18));

    public static final List<Menu> MENU_ITEMS = List.of(ITEM1, ITEM2, ITEM3, ITEM4, ITEM5, ITEM6, ITEM7, ITEM8, ITEM9, ITEM10, ITEM11, ITEM12);


}
