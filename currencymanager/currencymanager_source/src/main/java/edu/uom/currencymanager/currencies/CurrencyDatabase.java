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

    public CurrencyDatabase(ICurrencyRepository repository) {
        this.repository = repository;
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

    public Currency getCurrencyByCode(String code){
        return repository.getCurrencyByCode(code);
    }

}
