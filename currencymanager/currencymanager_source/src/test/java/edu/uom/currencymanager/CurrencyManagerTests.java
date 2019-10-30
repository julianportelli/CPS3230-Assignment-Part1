package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.ExchangeRate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CurrencyManagerTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    CurrencyManager cm;
    ExchangeRate ex;
    Currency currencyMinor;
    Currency currencyMajor;
    int oldSize;

    @Before
    public void setup() throws Exception {
        cm = new CurrencyManager();
        currencyMinor = new Currency("LMT", "Maltese Lira", false);
        currencyMajor = new Currency("CHK", "Chako Coin", true);
        ex = new ExchangeRate(currencyMinor, currencyMajor, 1.49);
        oldSize = cm.currencyDatabase.getCurrencies().size();
    }

    @After
    public void teardown(){
        cm = null;
        currencyMinor = null;
        currencyMajor = null;
        ex = null;
    }

    //I don't think main can be tested. Might be wrong

    @Test
    public void testCurrencyClassToString(){
        assertEquals(currencyMajor.toString(), "LMT 1 - Maltese Lira");
    }

    @Test
    public void testGetMajorCurrencyRates()  throws Exception{
        cm.currencyDatabase.addCurrency(currencyMajor);
        assertEquals(cm.getMajorCurrencyRates().size(), oldSize + 1);
    }

    @Test
    public void testExchangeRateClassToString()  throws Exception{
        assertEquals(ex.toString(), "LMT 1 = CHK 1.49");
    }

    @Test
    public void testGetExchangeRate()  throws Exception{
        ex = cm.getExchangeRate("GBP", "USD");
        assertEquals(ex, cm.currencyDatabase.getExchangeRate("GBP", "USD"));
    }

    @Test
    public void testGetExchangeRateWithNonExistentCurrency()  throws Exception{
        thrown.expect(Exception.class);
        thrown.expectMessage("Unkown currency: ASDF");
        cm.getExchangeRate("ASDF", "USD");
    }

    @Test
    public void testAddCurrencyCodeLengthNotThree() throws Exception{
        thrown.expect(Exception.class);
        thrown.expectMessage("A currency code should have 3 characters.");
        cm.addCurrency("TE", "Test Coin", true);
    }

    @Test
    public void testAddCurrencyNameLengthTooShort() throws Exception{
        thrown.expect(Exception.class);
        thrown.expectMessage("A currency's name should be at least 4 characters long.");
        cm.addCurrency("TES", "Tes", true);
    }

    @Test
    public void testAddCurrencyAlreadyExsists() throws Exception{
        thrown.expect(Exception.class);
        thrown.expectMessage("The currency TSC already exists.");
        cm.addCurrency("TSC", "Test Coin", true);
    }

    @Test
    public void testAddCurrencyAddedToCurrencyDatabase() throws Exception{

        cm.addCurrency("ABC", "AB Coin", false);
        assertEquals(cm.currencyDatabase.getCurrencies().size(), oldSize + 1);
    }

    @Test
    public void testDeleteCurrencyWithExistingCode() throws Exception{
        cm.deleteCurrencyWithCode("ABC");
        assertEquals(cm.currencyDatabase.getCurrencies().size(), oldSize - 1);
    }

    @Test
    public void testDeleteCurrencyWithNonexistentCode() throws Exception{
        thrown.expect(Exception.class);
        thrown.expectMessage("Currency does not exist: ASDFJKL");
        cm.deleteCurrencyWithCode("ASDFJKL");
    }




}
