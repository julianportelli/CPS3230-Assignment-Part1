package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.ExchangeRate;

import java.util.List;
import java.util.Scanner;

public class CurrencyManagerMenu {
    boolean exit;
    CurrencyManager cm;
    String code;
    Scanner sc = new Scanner(System.in);
    ISwitchManager switchManager;

    public CurrencyManagerMenu(CurrencyManager currencyManager) {
        this.cm = currencyManager;
    }

    public void initMenu() throws Exception {
        while (!exit) {
            System.out.println("\nMain Menu\n---------\n");

            System.out.println("1. List currencies");
            System.out.println("2. List exchange rates between major currencies");
            System.out.println("3. Check exchange rate");
            System.out.println("4. Add currency");
            System.out.println("5. Delete currency");
            System.out.println("0. Quit");

            System.out.print("\nEnter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 0:
                    case0();
                    break;
                case 1:
                    case1(switchManager);
                    break;
                case 2:
                    case2(switchManager);
                    break;
                case 3:
                    case3(switchManager, sc);
                    break;
                case 4:
                    case4(switchManager, sc);
                    break;
                case 5:
                    case5();
                    break;
            }
        }
    }

    public void case0(){
        exit = true;
    }

    public String case1(ISwitchManager switchManager){
        List<Currency> currencies = cm.currencyDatabase.getCurrencies();
        System.out.println("\nAvailable Currencies\n--------------------");
        String result = "[" + currencies.get(0).toString();
        for (int count = 1; count<currencies.size(); count++) {
            result = result + ", " + currencies.get(count).toString();
            System.out.println(currencies.get(count).toString());
        }
        return result + "]";
    }

    public String case2(ISwitchManager switchManager) throws Exception {
        List<ExchangeRate> exchangeRates = cm.getMajorCurrencyRates();
        System.out.println("\nMajor Currency Exchange Rates\n-----------------------------");
        String result = "[" + exchangeRates.get(0).toString();
        for(int count = 1; count<exchangeRates.size(); count++){
            result = result + ", " + exchangeRates.get(count).toString();
            System.out.println(exchangeRates.get(count).toString());
        }
        return result + "]";
    }

    public String case3(ISwitchManager switchManager, Scanner sc){
        String result = "";
        String source ="\nEnter source currency code (e.g. EUR): ";
        System.out.print(source);
        String src = sc.next().toUpperCase();
        System.out.println(src);
        String destination  = "\nEnter destination currency code (e.g. GBP): ";
        System.out.print(destination);
        String dst = sc.next().toUpperCase();
        System.out.println(dst);
        result = source + src + destination + dst;
        try {
            ExchangeRate rate = cm.getExchangeRate(src, dst);
            System.out.println(rate.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String case4(ISwitchManager manager, Scanner sc){
        String result = "";
        String codeLine = "\nEnter the currency code: ";
        System.out.println(codeLine);
        code = sc.next().toUpperCase();
        String currencyLine =  "Enter currency name: ";
        System.out.println(currencyLine);
        String name = sc.next();
        name += sc.nextLine();

        String major = "\n";
        while (!(major.equalsIgnoreCase("y") || major.equalsIgnoreCase("n"))) {
            System.out.println("Is this a major currency? [y/n]");
            major = sc.next();
        }

        try {
            cm.addCurrency(code, name, major.equalsIgnoreCase("y"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    public void case5(){
        System.out.print("\nEnter the currency code: ");
        code = sc.next().toUpperCase();
        try {
            cm.deleteCurrencyWithCode(code);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
