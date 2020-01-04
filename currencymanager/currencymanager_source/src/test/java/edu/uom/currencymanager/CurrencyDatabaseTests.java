package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.*;


public class CurrencyDatabaseTests {

    //Can't test all lines of init() as we can't change which currencies file to choose

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

    @Test
    public void TestValidRead() throws Exception {
        //setup
        String file = "target" + File.separator + "classes" + File.separator + "currencies.txt";

        //Exercise
        cd.init(file);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String firstLine = reader.readLine();

        //Verify
        assertEquals("code,name,major",firstLine);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void TestReadInvalidFile() throws Exception {
        //Setup
        exception.expect(Exception.class);
        String file = "target" + File.separator + "classes" + File.separator + "curr.txt";

        //Exercise
        cd.init(file);

        //Verify
        exception.expectMessage(file + " (No such file or directory)");
    }

    @Test
    public void TestCommas(){
        //setup
        String test = "mlt , mt , yes";

        //Exercise
        int output = cd.getCommasAmount(test);

        //Verify
        assertEquals(2, output);
    }

    @Test
    public void Test2Commas() throws Exception {
        //Setup
        String res = "mlt , mt , yes";

        //Exercise
        int commas = cd.getCommasAmount(res);
        String output = cd.checkLineForTwoCommas(res,commas);

        //Verify
        assertEquals("mlt , mt , yes", output);
    }

    @Test
    public void TestFirstLineParsingError() throws Exception {
        //Setup
        exception.expect(Exception.class);
        String file = "target" + File.separator + "classes" + File.separator + "invalid.txt";

        //Exercise
        cd.init(file);

        //Verify
        exception.expectMessage("Parsing error when reading currencies file.");
    }

    @Test
    public void TestReadInvalidFileCommas() throws Exception {
        //Setup
        exception.expect(Exception.class);
        String file = "target" + File.separator + "classes" + File.separator + "commasFile.txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String nextLine = "";
        int commas = 0;

        //Exercise
        while (reader.ready()) {
            nextLine = reader.readLine();
            commas = cd.getCommasAmount(nextLine);
        }

        //Verify
        cd.init(file);
        exception.expectMessage("Parsing error: expected two commas in line " + cd.checkLineForTwoCommas(nextLine,commas));

    }

    @Test
    public void TestReadInvalidCurrencyCode() throws Exception {
        //Setup
        exception.expect(Exception.class);
        Currency badCurrency = null;
        String file = "target" + File.separator + "classes" + File.separator + ".txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String nextLine = "";

        //Exercise
        while (reader.ready() && badCurrency == null) {
            nextLine = reader.readLine();
            Currency currency = Currency.fromString(nextLine);

            if (currency.code.length() == 3) {
                if (cd.currencyExists(currency.code)) {
                    badCurrency = currency;
                    System.out.println(badCurrency.code);
                }
            }
        }

        //Verify
        cd.init(file);
        exception.expectMessage("Invalid currency code detected: " + badCurrency.code);

    }


}
