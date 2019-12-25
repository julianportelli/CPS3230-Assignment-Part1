//MAIN CLASS

package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import edu.uom.currencymanager.currencies.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

public class CurrencyManager {

    CurrencyDatabase currencyDatabase;

    public CurrencyManager() throws Exception {
        currencyDatabase = new CurrencyDatabase(CurrencyFactory.getCurrencyRepository());
    }


    public List<ExchangeRate> getMajorCurrencyRates() throws Exception {

        List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();

        List<Currency> currencies = currencyDatabase.getMajorCurrencies();

        for (Currency src : currencies) {
            for (Currency dst : currencies) {
                if (src != dst) {
                    exchangeRates.add(currencyDatabase.getExchangeRate(src.code, dst.code));
                }
            }
        }
        return exchangeRates;
    }

    public ExchangeRate getExchangeRate(String sourceCurrency, String destinationCurrency) throws Exception {
        return currencyDatabase.getExchangeRate(sourceCurrency, destinationCurrency);
    }

    public void addCurrency(String code, String name, boolean major) throws Exception {

        //Check format of code
        if (code.length() != 3) {
            throw new Exception("A currency code should have 3 characters.");
        }

        //Check minimum length of name
        if (name.length() < 4) {
            throw new Exception("A currency's name should be at least 4 characters long.");
        }

        //Check if currency already exists
        if (currencyDatabase.currencyExists(code)) {
            throw new Exception("The currency " + code + " already exists.");
        }

        //Add currency to database
        currencyDatabase.addCurrency(new Currency(code,name,major));

    }

    public void deleteCurrencyWithCode(String code) throws Exception {

        if (!currencyDatabase.currencyExists(code)) {
            throw new Exception("Currency does not exist: " + code);
        }

        currencyDatabase.deleteCurrency(code);

    }

}
