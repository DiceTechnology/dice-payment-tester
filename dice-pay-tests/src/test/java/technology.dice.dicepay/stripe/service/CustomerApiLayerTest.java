package technology.dice.dicepay.stripe.service;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import technology.dice.payment.stripe.model.Customer;
import technology.dice.payment.stripe.model.CustomerDefinition;
import technology.dice.payment.stripe.model.StripeConfig;
import org.apache.commons.lang3.RandomStringUtils;
import technology.dice.payment.stripe.service.StripePaymentProvider;

import java.io.IOException;

import static technology.dice.dicepay.stripe.util.ConfigUtil.readConfig;


public class CustomerApiLayerTest {

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
    public void createUserTest(){
        String customerId = "random.test.name." + RandomStringUtils.random(5, false, true) + "@example.com";

        CustomerDefinition customerDefinition = new CustomerDefinition(customerId, ImmutableMap.of("testing", true));
        customerDefinition.setEmail("my-test-email@test.com");

        Customer customer = stripePaymentProvider.createCustomer(customerDefinition);

        System.out.println(customer.getCustomerId());
        System.out.println(customer.getEmail());
        System.out.println(customer.getDescription());
        System.out.println(customer.getMetaData());

    }
}

