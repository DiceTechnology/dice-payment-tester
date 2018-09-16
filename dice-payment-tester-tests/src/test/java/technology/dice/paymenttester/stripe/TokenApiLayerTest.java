/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import technology.dice.paymenttester.stripe.model.StripeCardDefinition;
import technology.dice.paymenttester.stripe.model.StripeCardToken;
import technology.dice.paymenttester.stripe.model.StripeConfig;
import technology.dice.paymenttester.stripe.service.StripePaymentProvider;
import technology.dice.paymenttester.stripe.util.TokenUtil;

import java.io.IOException;
import java.time.YearMonth;

import static java.time.YearMonth.of;
import static technology.dice.paymenttester.stripe.util.ConfigUtil.readConfig;

public class TokenApiLayerTest {


    private static final String MY_CONFIG_FILE = "my-config.properties";
    private Injector injector;
    private StripeConfig stripeConfig;
    private StripePaymentProvider stripePaymentProvider;

    @Before
    public void setUp() throws IOException {
        stripeConfig = readConfig(MY_CONFIG_FILE);

        AbstractModule serverModule = new AbstractModule() {
            @Override
            protected void configure() {
                bind(StripeConfig.class).toInstance(stripeConfig);
            }
        };

        injector = Guice.createInjector(serverModule);
        stripePaymentProvider = injector.getInstance(StripePaymentProvider.class);
    }

    @Test
    public void generateCardTokenTest(){

        String cardHolder = "random.test.name " + RandomStringUtils.random(5, false, true);
        YearMonth yymm = of(TokenUtil.DEFAULT_EXPIRY_YEAR, TokenUtil.DEFAULT_EXPIRY_MONTH);

        StripeCardDefinition cardDefinition = new StripeCardDefinition(
                cardHolder,
                TokenUtil.DEFAULT_TEST_CARD,
                TokenUtil.DEFAULT_CV2,
                yymm);
        StripeCardToken stripeCardToken = stripePaymentProvider.generateCardToken(cardDefinition);

        System.out.println(stripeCardToken.getToken());
    }

}

