package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.ExchangeRate;
import java.util.List;

public interface ICurrencyRepository {
    Currency getCurrencyByCode(String code);
    boolean currencyExists(String code);
    List<Currency> getCurrencies();
    List<Currency> getMajorCurrencies();
    ExchangeRate getExchangeRate(String sourceCurrencyCode, String destinationCurrencyCode) throws  Exception;
    void addCurrency(Currency currency) throws Exception;
    void deleteCurrency(String code) throws Exception;
    void init(String file) throws Exception;
    String checkLineForTwoCommas(String nextLine, int numCommas) throws Exception;
    int getCommasAmount(String nextLine);
}
