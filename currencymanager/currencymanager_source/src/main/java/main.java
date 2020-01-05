import edu.uom.currencymanager.CurrencyManager;
import edu.uom.currencymanager.CurrencyManagerMenu;

public class main {
    public static void main(String[] args) throws Exception {
        CurrencyManager cm = new CurrencyManager();
        CurrencyManagerMenu currencyManagerMenu = new CurrencyManagerMenu(cm);
        currencyManagerMenu.initMenu();
    }
}
