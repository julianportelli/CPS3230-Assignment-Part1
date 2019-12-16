package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;


public class CurrencyDatabaseTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CurrencyDatabase cd;
    private Currency currencyMinor;
    private Currency currencyMajor;

    @Before
    public void setup() throws Exception{
        cd = new CurrencyDatabase(CurrencyFactory.getCurrencyRepository());
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

        cd.deleteCurrency("LMT");
    }

    @Test
    public void testGetCurrencyByNonexistentCode(){
        //Verify
        assertNull(cd.getCurrencyByCode("ASFJKL:"));
    }

    @Test
    public void testGetMajorCurrencies() throws Exception{
        //Exercise
        cd.addCurrency(currencyMajor);

        //Verify
        assertTrue(cd.getMajorCurrencies().contains(currencyMajor));

        cd.deleteCurrency("CHK");
    }

    @Test
    public void testGetAllCurrencies() throws Exception{
        //Exercise
        int x = cd.getCurrencies().size(); //Gets old no. of currencies
        cd.addCurrency(currencyMinor);

        //Verify
        assertEquals(cd.getCurrencies().size(),x+1);

        cd.deleteCurrency("LMT");
    }

    @Test
    public void testDeleteCurrency() throws Exception{
        //Exercise
        cd.addCurrency(currencyMajor);
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
        thrown.expectMessage("Unknown currency: null");
        cd.getExchangeRate(null, "GBP");
    }

    @Test
    public void testGetExchangeRateDestinationNull() throws Exception{
        //Verify
        thrown.expect(Exception.class);
        thrown.expectMessage("Unknown currency: null");
        cd.getExchangeRate("GBP", null);
    }

    @Test
    public void testGetExchangeRateSourceNonexistent() throws Exception{
        //Verify
        thrown.expect(Exception.class);
        thrown.expectMessage("Unknown currency: ASDF");
        cd.getExchangeRate("ASDF", "GBP");
    }

    @Test
    public void testGetExchangeRateDestinationNonexistent() throws Exception{
        //Verify
        thrown.expect(Exception.class);
        thrown.expectMessage("Unknown currency: ASDF");
        cd.getExchangeRate("GBP", "ASDF");
    }

}
