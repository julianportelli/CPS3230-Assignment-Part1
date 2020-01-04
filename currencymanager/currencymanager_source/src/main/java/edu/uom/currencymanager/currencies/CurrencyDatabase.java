package edu.uom.currencymanager.currencies;
import edu.uom.currencymanager.ICurrencyRepository;
import java.util.List;


public class CurrencyDatabase {

    private ICurrencyRepository repository;

    public void init(String file) throws Exception{
        repository.init(file);
    }

    public CurrencyDatabase(ICurrencyRepository repository) {
        this.repository = repository;
    }

    public List<Currency> getMajorCurrencies(){
        return repository.getMajorCurrencies();
    }

    public ExchangeRate getExchangeRate(String sourceCurrencyCode, String destinationCurrencyCode) throws Exception {
        return repository.getExchangeRate(sourceCurrencyCode, destinationCurrencyCode);
    }

    public List<Currency> getCurrencies() {
        return repository.getCurrencies();
    }

    public boolean currencyExists(String code) {
        return repository.currencyExists(code);
    }

    public void addCurrency(Currency currency) throws Exception{
        repository.addCurrency(currency);
    }

    public void deleteCurrency(String code) throws Exception{
        repository.deleteCurrency(code);
    }

    public Currency getCurrencyByCode(String code){
        return repository.getCurrencyByCode(code);
    }

    public String checkLineForTwoCommas(String nextLine, int numCommas) throws Exception{
        return repository.checkLineForTwoCommas(nextLine, numCommas);
    };

    public int getCommasAmount(String nextLine){
        return repository.getCommasAmount(nextLine);
    };

}
