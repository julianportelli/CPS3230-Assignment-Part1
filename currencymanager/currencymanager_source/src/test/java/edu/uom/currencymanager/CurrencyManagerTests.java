package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.ExchangeRate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CurrencyManagerTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    CurrencyManager cm;
    ExchangeRate ex;
    Currency currencyMinor;
    Currency currencyMajor;
    int oldListCurrSize;

    @Before
    public void setup() throws Exception { //Initialise variables for use
        cm = new CurrencyManager();
        currencyMinor = new Currency("LMT", "Maltese Lira", false);
        currencyMajor = new Currency("CHK", "Chako Coin", true);
        ex = new ExchangeRate(currencyMinor, currencyMajor, 1.49);
        oldListCurrSize = cm.currencyDatabase.getCurrencies().size();
    }

    @After
    public void teardown(){ //OK
        cm = null;
        currencyMinor = null;
        currencyMajor = null;
        ex = null;
    }

    //I don't think main can be tested. Might be wrong

    @Test
    public void testCurrencyClassToString(){ //OK
        assertEquals(currencyMinor.toString(), "LMT - Maltese Lira");
    } //Simple comparison

    @Test
    public void testGetMajorCurrencyRates() throws Exception{  //OK
        int MajorCurrencyRatesListSize = cm.getMajorCurrencyRates().size();
        int MajorCurrenciesListSize = cm.currencyDatabase.getMajorCurrencies().size();
        assertEquals(MajorCurrencyRatesListSize, (int)Math.pow(MajorCurrenciesListSize, 2) - MajorCurrenciesListSize);
    }//Number of rates follow the equation x^2 - x, where x is the number of major currencies

    @Test
    public void testExchangeRateClassToString() throws Exception{ //OK
        assertEquals(ex.toString(), "LMT 1 = CHK 1.49");
    }//Simple comparison

    @Test
    public void testGetExchangeRate()  throws Exception{ //OK
        ex = cm.getExchangeRate("GBP", "USD");
        assertEquals(ex, cm.currencyDatabase.getExchangeRate("GBP", "USD"));
    }//Compare equality of currency in currencymanager with currencymanager's currencydatabase

    @Test
    public void testGetExchangeRateWithNonExistentCurrency()  throws Exception{ //OK
        thrown.expect(Exception.class);
        thrown.expectMessage("Unkown currency: ASDF");
        cm.getExchangeRate("ASDF", "USD");
    }//Testing for exception for getting an exchange rate for an unknown currency

    @Test
    public void testAddCurrencyCodeLengthNotThree() throws Exception{
        thrown.expect(Exception.class);
        thrown.expectMessage("A currency code should have 3 characters.");
        cm.addCurrency("TE", "Test Coin", true);
    }//Testing for exception where new currency name length is not 3 characters long

    @Test
    public void testAddCurrencyNameLengthTooShort() throws Exception{
        thrown.expect(Exception.class);
        thrown.expectMessage("A currency's name should be at least 4 characters long.");
        cm.addCurrency("TES", "Tes", true);

        cm.deleteCurrencyWithCode("TES");
    }//Testing for exception where new currency name is too short

    @Test
    public void testAddCurrencyAlreadyExists() throws Exception{
        thrown.expect(Exception.class);
        thrown.expectMessage("The currency USD already exists.");
        cm.addCurrency("USD", "US Dollar", true);
    }//Testing for exception where currency already exists

    @Test
    public void testAddCurrencyAddedToCurrencyDatabase() throws Exception{
        cm.addCurrency("DEF", "DEF Coin", false);
        assertEquals(cm.currencyDatabase.getCurrencies().size(), oldListCurrSize + 1);
        cm.deleteCurrencyWithCode("DEF");
    }//Add new currency and compare if size changed

    @Test
    public void testDeleteCurrencyWithExistingCode() throws Exception{
        System.out.println(oldListCurrSize);
        cm.addCurrency("ABC", "AB Coin", false);
        cm.deleteCurrencyWithCode("ABC");
        assertEquals(cm.currencyDatabase.getCurrencies().size(), oldListCurrSize);
    }//Delete existing currency and compare if size changed

    @Test
    public void testDeleteCurrencyWithNonexistentCode() throws Exception{
        thrown.expect(Exception.class);
        thrown.expectMessage("Currency does not exist: ASDFJKL");
        cm.deleteCurrencyWithCode("ASDFJKL");
    }//Testing for exception where currency does not exist

}