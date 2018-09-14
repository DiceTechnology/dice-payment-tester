package technology.dice.dicepaypal.api;

import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;

import java.math.BigDecimal;

public class PaypalMoney {

    final BasicAmountType basicAmountType;

    public PaypalMoney(long amount, CurrencyCodeType currencyCodeType) {
        this(getPayalValue(amount), currencyCodeType);
    }

    public PaypalMoney(String amount, CurrencyCodeType currencyCodeType) {
        basicAmountType = new BasicAmountType();
        basicAmountType.setCurrencyID(currencyCodeType);
        basicAmountType.setValue(amount);
    }

    public BasicAmountType getBasicAmountType() {
        return basicAmountType;
    }

    private static String getPayalValue(long amount) {
        return new BigDecimal(amount).movePointLeft(2).toString();
    }
}
