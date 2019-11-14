package edu.uom.currencymanager;

public class CurrencyFactory {
    public static ICurrencyRepository getCurrencyRepository(){ return new CurrencyRepository();}
}
