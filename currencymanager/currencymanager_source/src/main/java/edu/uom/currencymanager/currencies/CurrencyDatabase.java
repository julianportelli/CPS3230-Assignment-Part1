package edu.uom.currencymanager.currencies;

import edu.uom.currencymanager.ICurrencyRepository;
import edu.uom.currencymanager.currencyserver.CurrencyServer;
import edu.uom.currencymanager.currencyserver.DefaultCurrencyServer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CurrencyDatabase {

    private ICurrencyRepository repository;
//    private String currenciesFile = "target" + File.separator + "classes" + File.separator + "currencies.txt";

//    public CurrencyDatabase() throws Exception {
//        init();
//    }

    public CurrencyDatabase(ICurrencyRepository repository) throws Exception {
        this.repository = repository;
        init();
    }

    public List<Currency> getMajorCurrencies(){
        return repository.getMajorCurrencies();
    }

    public ExchangeRate getExchangeRate(String sourceCurrencyCode, String destinationCurrencyCode) throws Exception {
        return repository.getExchangeRate(sourceCurrencyCode, destinationCurrencyCode);
    }

    public List<Currency> getCurrencies() {
        return repository.getCurrencies();
    }

    public boolean currencyExists(String code) {
        return repository.currencyExists(code);
    }

    public void addCurrency(Currency currency) throws Exception{
        repository.addCurrency(currency);
    }

    public void deleteCurrency(String code) throws Exception{
        repository.deleteCurrency(code);
    }

    public void init() throws Exception {
        //Initialise currency server
        //currencyServer = new DefaultCurrencyServer();

        //Read in supported currencies from text file
        BufferedReader reader = new BufferedReader(new FileReader(repository.getCurrenciesFile()));

        //skip the first line to avoid header
        String firstLine = reader.readLine();
        if (!firstLine.equals("code,name,major")) {
            throw new Exception("Parsing error when reading currencies file.");
        }

        while (reader.ready()) {
            String  nextLine = reader.readLine();

            //Check if line has 2 commas
            int numCommas = 0;
            char[] chars = nextLine.toCharArray();
            for (char c : chars) {
                if (c == ',') numCommas++;
            }

            if (numCommas != 2) {
                throw new Exception("Parsing error: expected two commas in line " + nextLine);
            }

            Currency currency = Currency.fromString(nextLine);

            if (currency.code.length() == 3) {
                if (!repository.currencyExists(currency.code)) {
                    repository.getCurrencies().add(currency);
                }
            } else {
                System.err.println("Invalid currency code detected: " + currency.code);
            }
        }
    } //No control


}
