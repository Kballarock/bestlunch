package by.bestlunch.util.exception;

public class MenuException extends ApplicationException {
    public static final String DAILY_MENU_EXCEPTION = "exception.dailyMenu.notFound";

    public MenuException(String arg) {
        super(ErrorType.DATA_ERROR, DAILY_MENU_EXCEPTION, arg);
    }
}