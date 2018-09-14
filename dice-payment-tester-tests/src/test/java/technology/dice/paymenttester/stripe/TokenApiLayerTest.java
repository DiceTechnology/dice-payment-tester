package technology.dice.paymenttester.stripe;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import technology.dice.payment.stripe.model.*;
import technology.dice.payment.stripe.service.StripePaymentProvider;

import java.io.IOException;
import java.time.YearMonth;

import static java.time.YearMonth.of;
import static technology.dice.payment.stripe.service.util.ConfigUtil.readConfig;

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

