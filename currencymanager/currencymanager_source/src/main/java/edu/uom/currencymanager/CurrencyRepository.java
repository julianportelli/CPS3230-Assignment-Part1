package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.ExchangeRate;
import edu.uom.currencymanager.currencyserver.CurrencyServer;
import edu.uom.currencymanager.currencyserver.DefaultCurrencyServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CurrencyRepository implements ICurrencyRepository {
    private CurrencyServer currencyServer = new DefaultCurrencyServer();
    private List<Currency> currencies = new ArrayList<Currency>();
    private HashMap<String, ExchangeRate> exchangeRates = new HashMap<String, ExchangeRate>();
    private String currenciesFile;

    public void setFilePath(){
        Scanner sc = new Scanner(System.in);
        File f;
        boolean exists;
        do{
            System.out.print("Enter the name of the currencies text file: ");
            String fileName = sc.nextLine();
            currenciesFile = "target" + File.separator + "classes" + File.separator + fileName;
            f = new File(currenciesFile);
            exists = f.exists() && !f.isDirectory();
            if(exists == false) System.out.println("File not found. Try again");
        }while(!exists);

    }

    public String getCurrenciesFile(){
        return currenciesFile;
    }

    public Currency getCurrencyByCode(String code) {

        for (Currency currency : currencies) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }

        return null;
    }

    public boolean currencyExists(String code) {
        return getCurrencyByCode(code) != null;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<Currency> getMajorCurrencies() {
        List<Currency> result = new ArrayList<Currency>();

        for (Currency currency : currencies) {
            if (currency.major) {
                result.add(currency);
            }
        }

        return result;
    }

    public ExchangeRate getExchangeRate(String sourceCurrencyCode, String destinationCurrencyCode) throws  Exception {
        long FIVE_MINUTES_IN_MILLIS = 300000;  //5*60*100

        ExchangeRate result = null;

        Currency sourceCurrency = getCurrencyByCode(sourceCurrencyCode);
        if (sourceCurrency == null) {
            throw new Exception("Unkown currency: " + sourceCurrencyCode);
        }

        Currency destinationCurrency = getCurrencyByCode(destinationCurrencyCode);
        if (destinationCurrency == null) {
            throw new Exception("Unkown currency: " + destinationCurrencyCode);
        }

        //Check if exchange rate exists in database
        String key = sourceCurrencyCode + destinationCurrencyCode;
        if (exchangeRates.containsKey(key)) {
            result = exchangeRates.get(key);
            if (System.currentTimeMillis() - result.timeLastChecked > FIVE_MINUTES_IN_MILLIS) {
                result = null;
            }
        }

        if (result == null) {
            double rate = currencyServer.getExchangeRate(sourceCurrencyCode, destinationCurrencyCode);
            result = new ExchangeRate(sourceCurrency,destinationCurrency, rate);

            //Cache exchange rate
            exchangeRates.put(key, result);

            //Cache inverse exchange rate
            String inverseKey = destinationCurrencyCode+sourceCurrencyCode;
            exchangeRates.put(inverseKey, new ExchangeRate(destinationCurrency, sourceCurrency, 1/rate));
        }

        return result;
    }

    public void addCurrency(Currency currency) throws Exception {

        //Save to list
        currencies.add(currency);

        //Persist
        persist();
    }

    public void deleteCurrency(String code) throws Exception {

        //Save to list
        currencies.remove(getCurrencyByCode(code));

        //Persist
        persist();
    }

    public void persist() throws Exception {

        //Persist list
        BufferedWriter writer = new BufferedWriter(new FileWriter(currenciesFile));

        writer.write("code,name,major\n");
        for (Currency currency : currencies) {
            writer.write(currency.code + "," + currency.name + "," + (currency.major ? "yes" : "no"));
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }

}
