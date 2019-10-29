package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;


public class CurrencyDatabaseTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    CurrencyDatabase cd;
    Currency currency;

    @Before
    public void setup() throws Exception{
        cd = new CurrencyDatabase();
        currency = new Currency("LMT", "Maltese Lira", false);
    }

    @After
    public void teardown(){
        cd = null;
    }

    @Test
    public void testGetCurrencyByCode()throws Exception{
        //Exercise
        cd.addCurrency(currency);

        //Verify
        assertEquals("LMT - Maltese Lira", cd.getCurrencyByCode("LMT").toString());
    }

    @Test
    public void testGetCurrencyByNonexistentCode() throws Exception{
        //Exercise


        //Verify
        assertEquals(null, cd.getCurrencyByCode("ASFJKL:"));

    }

}
