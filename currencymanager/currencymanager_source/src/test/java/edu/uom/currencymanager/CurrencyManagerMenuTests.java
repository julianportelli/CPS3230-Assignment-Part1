package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.CurrencyDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class CurrencyManagerMenuTests {
    private CurrencyManagerMenu currencyManagerMenu;
    private CurrencyDatabase currencyDatabase;
    private CurrencyManager currencyManager;
    private ISwitchManager switchManager;
    private final String DEFAULT_SOURCE = "EUR";
    private final String DEFAULT_DESTINATION = "GBP";
    private final String BAD_SOURCE = "ASD";
    private final String BAD_DESTINATION = "JKL";
    private final String NEW_CURRENCY_CODE = "TES";
    private final String NEW_CURRENCY_NAME = "Test dollar";
    private final String NEW_CURRENCY_MAJOR = "y";

    @Before
    public void setup() throws Exception {
        currencyManager = new CurrencyManager();
        currencyManagerMenu = new CurrencyManagerMenu(currencyManager);
        currencyDatabase = new CurrencyDatabase(CurrencyFactory.getCurrencyRepository());
        switchManager = mock(ISwitchManager.class);
    }

    @After
    public void teardown() {
        currencyManagerMenu = null;
        currencyDatabase = null;
    }

    @Test
    public void testCase0() {
        //Setup
        Mockito.when(switchManager.getCase0()).thenReturn("true");

    }

    @Test
    public void testCase1() {
        //Testing for listing of currencies

        //Setup
        Mockito.when(switchManager.getCase1()).thenReturn(String.valueOf(true));

        //Exercise
        String result = currencyManagerMenu.case1(switchManager);

        //Verify
        assertEquals(currencyDatabase.getCurrencies().toString(), result);
    }

    @Test
    public void testCase2() throws Exception {
        //Testing for listing exchange rates between major currencies

        //setup
        Mockito.when(switchManager.getCase2()).thenReturn(String.valueOf(true));

        //Exercise
        String result = currencyManagerMenu.case2(switchManager);
        //Verify
        assertEquals(currencyManager.getMajorCurrencyRates().toString(), result);
    }

}
