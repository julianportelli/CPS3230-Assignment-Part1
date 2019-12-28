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
    private final String NEW_CURRENCY_NAME = "Test_dollar";
    private final String NEW_CURRENCY_MAJOR = "y";
    private final String NONEXISTENT_CODE = "QWE";
    private final String BAD_CURR_NAME = "ABC";

    @Before
    public void setup() throws Exception {
        currencyManager = new CurrencyManager();
        currencyManagerMenu = new CurrencyManagerMenu(currencyManager);
        currencyDatabase = new CurrencyDatabase(CurrencyFactory.getCurrencyRepository());
        switchManager = mock(ISwitchManager.class);
    }

    @After
    public void teardown() {
        currencyManager = null;
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

    @Test
    public void testCase3() {
        //Testing for checking exchange rate

        //Setup
        Mockito.when(switchManager.getCase3Source()).thenReturn(DEFAULT_SOURCE);
        Mockito.when(switchManager.getCase3Destination()).thenReturn(DEFAULT_DESTINATION);
        InputStream originalSystem = System.in; // backup System.in to restore it later
        ByteArrayInputStream inputToRead = new ByteArrayInputStream((DEFAULT_SOURCE + " " + DEFAULT_DESTINATION).getBytes());
        System.setIn(inputToRead);
        Scanner sc = new Scanner(inputToRead);

        //Exercise
        String result = currencyManagerMenu.case3(switchManager, sc);

        //Verify
        assertEquals("\nEnter source currency code (e.g. EUR): " +
                DEFAULT_SOURCE + "\nEnter destination currency code (e.g. GBP): " + DEFAULT_DESTINATION, result);

        //Reset System.in to original
        System.setIn(originalSystem);
    }

    @Test
    public void testCase3Exception(){
        //Testing for checking exchange rate exception

        //Setup
        Mockito.when(switchManager.getCase3Source()).thenReturn(BAD_SOURCE);
        Mockito.when(switchManager.getCase3Destination()).thenReturn(BAD_DESTINATION);
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream((BAD_SOURCE + " " + BAD_DESTINATION).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currencyManagerMenu.case3(switchManager, sc);

        //Verify
        assertEquals("\nEnter source currency code (e.g. EUR): " +
                BAD_SOURCE + "\nEnter destination currency code (e.g. GBP): " + BAD_DESTINATION, result);

        //Reset System.in to original
        System.setIn(sysInBackup);
    }

    @Test
    public void testCase4() {
        //Testing adding a currency

        //setup
        Mockito.when(switchManager.getCase4Code()).thenReturn(NEW_CURRENCY_CODE);
        Mockito.when(switchManager.getCase4Name()).thenReturn(NEW_CURRENCY_NAME);
        Mockito.when(switchManager.getCase4IsMajor()).thenReturn(NEW_CURRENCY_MAJOR);
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream((NEW_CURRENCY_CODE + " " + NEW_CURRENCY_NAME +
                "\n" + NEW_CURRENCY_MAJOR).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currencyManagerMenu.case4(switchManager, sc);

        //Verify
        assertEquals("\nEnter the currency code: " +
                NEW_CURRENCY_CODE + "\nEnter currency name: " + NEW_CURRENCY_NAME +
                "\nIs this a major currency? [y/n] " +  NEW_CURRENCY_MAJOR, result);

        //Reset System.in to  original
        System.setIn(sysInBackup);
    }

    @Test
    public void testCase4Exception() {
        //Testing adding a currency

        //setup
        Mockito.when(switchManager.getCase4Code()).thenReturn(NEW_CURRENCY_CODE);
        Mockito.when(switchManager.getCase4Name()).thenReturn(BAD_CURR_NAME);
        Mockito.when(switchManager.getCase4IsMajor()).thenReturn(NEW_CURRENCY_MAJOR);
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream((NEW_CURRENCY_CODE + " " + BAD_CURR_NAME +
                "\n" + NEW_CURRENCY_MAJOR).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currencyManagerMenu.case4(switchManager, sc);

        //Verify
        assertEquals("\nEnter the currency code: " +
                NEW_CURRENCY_CODE + "\nEnter currency name: " + BAD_CURR_NAME +
                "\nIs this a major currency? [y/n] " +  NEW_CURRENCY_MAJOR + "\nA currency's name should be at least 4 characters long.", result);

        //Reset System.in to  original
        System.setIn(sysInBackup);
    }

    @Test
    public void testCase5() {
        //Testing adding a currency

        //setup
        Mockito.when(switchManager.getCase5Code()).thenReturn(NEW_CURRENCY_CODE);
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream((NEW_CURRENCY_CODE).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currencyManagerMenu.case5(switchManager, sc);

        //Verify
        assertEquals("\nEnter your choice: " +
                NEW_CURRENCY_CODE, result);

        //Reset System.in to  original
        System.setIn(sysInBackup);
    }

    @Test
    public void testCase5Exception() {
        //Testing adding a currency

        //setup
        Mockito.when(switchManager.getCase5Code()).thenReturn(NONEXISTENT_CODE);
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream((NONEXISTENT_CODE).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currencyManagerMenu.case5(switchManager, sc);

        //Verify
        assertEquals("\nEnter your choice: " +
                NONEXISTENT_CODE + "\nCurrency does not exist: " + NONEXISTENT_CODE, result);

        //Reset System.in to  original
        System.setIn(sysInBackup);
    }

}
