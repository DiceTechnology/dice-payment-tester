package technology.dice.payment.stripe.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import technology.dice.payment.stripe.model.StripeConfig;

@RunWith(MockitoJUnitRunner.class)
public class MyStripeTest {

    @Mock
    private StripeConfig stripeConfig;

    @Mock
    private CustomerApiLayer customerApiLayer;

    @Mock
    private SubscriptionApiLayer subscriptionApiLayer;

    private StripePaymentProvider paymentProvider;

    @Before
    public void setUp() {
    }

    @Test
    public void sfsdf() {
        System.out.println(paymentProvider);

    }

    @Test
    public void sdfasdfsdf() {
        System.out.println("hello world");
    }
}
