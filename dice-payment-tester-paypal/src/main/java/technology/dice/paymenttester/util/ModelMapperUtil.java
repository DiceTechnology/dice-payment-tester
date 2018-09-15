/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.util;

import com.google.common.collect.ImmutableMap;
import technology.dice.paymenttester.api.PaypalAccountBalance;
import technology.dice.paymenttester.api.PaypalMoney;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;

import java.util.List;

public class ModelMapperUtil {

    public static String fromBoolean(boolean value) {
        return value ? "1" : "0";
    }

    public static PaypalMoney convert(BasicAmountType basicAmountType) {
        return new PaypalMoney(basicAmountType.getValue(), basicAmountType.getCurrencyID());
    }

    public static PaypalAccountBalance convert(BasicAmountType defaultCurrencyBalance, List<BasicAmountType> currencyBalances) {
        return new PaypalAccountBalance(convert(defaultCurrencyBalance),
                currencyBalances.stream().collect(ImmutableMap.toImmutableMap(balance -> balance.getCurrencyID(), balance -> convert(balance))));
    }
}
