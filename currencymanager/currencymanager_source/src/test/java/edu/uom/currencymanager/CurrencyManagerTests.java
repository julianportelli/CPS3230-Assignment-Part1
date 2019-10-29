package edu.uom.currencymanager;

import org.junit.After;
import org.junit.Before;

public class CurrencyManagerTests {

    CurrencyManager cm;

    @Before
    public void setup() throws Exception {
        cm = new CurrencyManager();
    }

    @After
    public void teardown(){
        cm = null;
    }


}
