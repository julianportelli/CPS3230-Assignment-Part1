package edu.uom.currencymanager;

class CurrencyFactory {
    static ICurrencyRepository getCurrencyRepository() throws Exception { return new CurrencyRepository();}
}
