package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import edu.uom.currencymanager.currencies.ExchangeRate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class CurrencyDatabaseTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    CurrencyDatabase cd;
    Currency currencyMinor;
    Currency currencyMajor;
    ExchangeRate ex;

    @Before
    public void setup() throws Exception{
        cd = new CurrencyDatabase();
        currencyMinor = new Currency("LMT", "Maltese Lira", false);
        currencyMajor = new Currency("CHK", "Chako Coin", true);
    }

    @After
    public void teardown(){
        cd = null;
    }

    @Test
    public void testGetCurrencyByCode()throws Exception{
        //Exercise
        cd.addCurrency(currencyMinor);

        //Verify
        assertEquals("LMT - Maltese Lira", cd.getCurrencyByCode("LMT").toString());
    }

    @Test
    public void testGetCurrencyByNonexistentCode() throws Exception{
        //Verify
        assertEquals(null, cd.getCurrencyByCode("ASFJKL:"));
    }

    @Test
    public void testGetMajorCurrencies() throws Exception{
        //Exercise
        cd.addCurrency(currencyMajor);

        //Verify
        assertTrue(cd.getMajorCurrencies().contains(currencyMajor));
    }

    @Test
    public void testGetAllCurrencies() throws Exception{
        //Exercise
        int x = cd.getCurrencies().size(); //Gets old no. of currencies
        cd.addCurrency(currencyMinor);

        //Verify
        assertEquals(cd.getCurrencies().size(),x+1);
    }

    @Test
    public void testDeleteCurrency() throws Exception{
        //Exercise
        int oldSize = cd.getCurrencies().size(); //Gets old no. of currencies
        cd.deleteCurrency("CHK");
        int newSize = cd.getCurrencies().size();

        //Verify
        assertTrue(oldSize != newSize);
    }

    @Test
    public void testGetExchangeRateSourceNull() throws Exception{
        //Verify
        thrown.expect(Exception.class);
        thrown.expectMessage("Unkown currency: null");
        cd.getExchangeRate(null, "GBP");
    }

    @Test
    public void testGetExchangeRateDestinationNull() throws Exception{
        //Verify
        thrown.expect(Exception.class);
        thrown.expectMessage("Unkown currency: null");
        cd.getExchangeRate("GBP", null);
    }



}
