package technology.dice.dicepaypal.api;

import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;

import java.math.BigDecimal;

public class PaypalMoney {

    final long amount;
    final CurrencyCodeType currencyCodeType;

    public PaypalMoney(long amount, CurrencyCodeType currencyCodeType) {
        this.amount = amount;
        this.currencyCodeType = currencyCodeType;
    }

    public PaypalMoney(String amount, CurrencyCodeType currencyCodeType) {
        this.amount = convert(amount);
        this.currencyCodeType = currencyCodeType;
    }

    public long getAmount() {
        return amount;
    }

    public CurrencyCodeType getCurrencyCodeType() {
        return currencyCodeType;
    }

    public BasicAmountType getBasicAmountType() {
        final BasicAmountType basicAmountType = new BasicAmountType();
        basicAmountType.setCurrencyID(currencyCodeType);
        basicAmountType.setValue(convert(amount));
        return basicAmountType;
    }

    private static String convert(long amount) {
        return new BigDecimal(amount).movePointLeft(2).toString();
    }

    private static long convert(String amount) {
        return new BigDecimal(amount).movePointRight(2).longValue();
    }
}
