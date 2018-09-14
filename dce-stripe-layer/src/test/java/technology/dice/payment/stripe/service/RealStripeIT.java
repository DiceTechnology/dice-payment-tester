package technology.dice.payment.stripe.service;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import technology.dice.payment.stripe.model.StripeConfig;

import java.io.IOException;

import static technology.dice.payment.stripe.util.ConfigUtil.readConfig;

public class RealStripeIT {
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
    public void sss() {
        System.out.println("hello world");
    }
    @Test
    public void makeOrderPayment() {
//        String customerId = "harrison.tsun-771@img.com";
//
//        CustomerDefinition customerDefinition = new CustomerDefinition(customerId, ImmutableMap.of("testing", true));
//        customerDefinition.setEmail("my-test-email@test.com");
//
//        Customer customer = stripePaymentProvider.createCustomer(customerDefinition);
//
//        System.out.println(customer);

//        StripeCardDefinition cardDefinition = new StripeCardDefinition("test user", "4242424242424242", "123", YearMonth.of(2020, 12));
//
//        StripeCardToken stripeCardToken = stripePaymentProvider.generateCardToken(cardDefinition);

//
//        result = stripePaymentProvider.payOrder(stripeCustomer, stripeCardToken, amount);
//
//        paymentCharges = stripePaymentProvider.getPaymentDetails(stripeCustomer);


    }
}
