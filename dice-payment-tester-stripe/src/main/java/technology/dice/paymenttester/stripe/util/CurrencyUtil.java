package technology.dice.paymenttester.stripe.util;

import java.util.Currency;

public class CurrencyUtil {

	public static Currency toCurrency(String currencyCode) {
		// NB : usd won't get rendered to USD; we must use upper case here
		return currencyCode == null ? null : Currency.getInstance(currencyCode.toUpperCase());
	}

	public static String toCode(Currency currency) {
		return currency.getCurrencyCode();
	}

	public static int getScale(Currency currency) {
		return currency.getDefaultFractionDigits();
	}
}
