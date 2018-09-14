package technology.dice.dicepaypal.api;

import com.google.common.collect.ImmutableMap;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;

import java.util.Map;

public class PaypalAccountBalance {

    private PaypalMoney defaultCurrencyBalance;

    private Map<CurrencyCodeType, PaypalMoney> currencyBalanceMap;

    public PaypalAccountBalance(PaypalMoney defaultCurrencyBalance) {
        this(defaultCurrencyBalance, ImmutableMap.of());
    }

    public PaypalAccountBalance(PaypalMoney defaultCurrencyBalance, Map<CurrencyCodeType, PaypalMoney> currencyBalanceMap) {
        this.defaultCurrencyBalance = defaultCurrencyBalance;
        this.currencyBalanceMap = currencyBalanceMap;
    }

}

