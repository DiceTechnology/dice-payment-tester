/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import technology.dice.paymenttester.stripe.model.Customer;
import technology.dice.paymenttester.stripe.model.CustomerDefinition;
import technology.dice.paymenttester.stripe.model.StripeConfig;
import technology.dice.paymenttester.stripe.service.StripePaymentProvider;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static technology.dice.paymenttester.stripe.util.ConfigUtil.readConfig;


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

        assertThat(customerDefinition.getCustomerId(), is(customer.getCustomerId()));
        assertThat(customerDefinition.getEmail(), is(customer.getEmail()));
        assertThat(customerDefinition.getDescription(), is(customer.getDescription()));
    }
}

