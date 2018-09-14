package technology.dice.payment.stripe.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import technology.dice.payment.stripe.model.*;

import java.io.IOException;
import java.time.YearMonth;
import java.util.Currency;
import java.util.UUID;

import static technology.dice.payment.stripe.util.ConfigUtil.readConfig;

public class RealStripeIT {
    private static final Logger LOG = LoggerFactory.getLogger(RealStripeIT.class);
    private static final String MY_CONFIG_FILE = "my-config.properties";
    private static final Currency GBP = Currency.getInstance("GBP");
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
    public void createCustomer() {
        String customerId = "harrison.tsun-771@img.com";

        CustomerDefinition customerDefinition = new CustomerDefinition(customerId, ImmutableMap.of("testing", true));
        customerDefinition.setEmail("my-test-email@test.com");

        Customer customer = stripePaymentProvider.createCustomer(customerDefinition);

        System.out.println(customer);
    }

    @Test
    public void createCardToken() {
        StripeCardDefinition cardDefinition = new StripeCardDefinition("test user", "4242424242424242", "123", YearMonth.of(2020, 12));

        StripeCardToken stripeCardToken = stripePaymentProvider.generateCardToken(cardDefinition);
        LOG.info("stripe card token => {}", stripeCardToken);
    }

    @Test
    public void createStripeProduct() {
        String productName = "test-product1";
        ProductDefinition productDefinition = new ProductDefinition(productName, ImmutableList.of("sku"));

        Product product = stripePaymentProvider.createProduct(productDefinition);
        LOG.info("product => {}", product.getProductId());
    }

    @Test
    public void createStripeSku() {
        ProductId productId = new ProductId("test-product1");
        SkuId skuId = new SkuId("test-sku-1");
        ChargeablePrice price = new ChargeablePrice(21588, Currency.getInstance("GBP"));
        SkuDefinition skuDefinition = new SkuDefinition(productId, skuId, price, ImmutableMap.of("sku", skuId.getId()));

        SkuId newSkuId = stripePaymentProvider.createSku(skuDefinition);
        LOG.info("sku => {}", newSkuId.getId());
    }

    @Test
    public void makeOrderPayment() {
        String customerId = "harrison.tsun-771@img.com";
        ProductId productId = new ProductId("test-product1");
        SkuId skuId = new SkuId("test-sku-1");

        StripeCustomerId stripeCustomerId = new StripeCustomerId(customerId);
        StripeCardToken stripeCardToken = new StripeCardToken("tok_visa");
        OrderDefinition orderDefinition = new OrderDefinition(customerId, GBP, ImmutableList.of(new OrderItem(productId,skuId.getId())));
        String idempotentKey = UUID.randomUUID().toString();

        stripePaymentProvider.bindCardToken(stripeCustomerId, stripeCardToken);

        String s = stripePaymentProvider.payOrderByCardToken(stripeCustomerId, stripeCardToken, orderDefinition, idempotentKey);
        System.out.println(s);

//        paymentCharges = stripePaymentProvider.getPaymentDetails(stripeCustomer);


    }

}
