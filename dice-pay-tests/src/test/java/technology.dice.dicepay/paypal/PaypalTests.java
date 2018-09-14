package technology.dice.dicepay.paypal;

import org.junit.Before;
import org.junit.Test;
import technology.dice.dicepaypal.api.PaypalMoney;
import technology.dice.dicepaypal.api.PaypalResponse;
import technology.dice.dicepaypal.api.exception.PaypalException;
import technology.dice.dicepaypal.config.PaypalAccountConfig;
import technology.dice.dicepaypal.merchant.PaypalMerchantAccountWrapper;
import technology.dice.dicepaypal.util.ConfigUtil;
import urn.ebay.apis.eBLBaseComponents.AckCodeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PaypalTests {

    private PaypalMerchantAccountWrapper paypalMerchantAccountWrapper;

    @Before
    public void setUp() throws IOException {
        PaypalAccountConfig paypalAccountConfig = ConfigUtil.readPaypalAccountConfig("config.properties");
        this.paypalMerchantAccountWrapper = new PaypalMerchantAccountWrapper(paypalAccountConfig);
    }

    @Test
    public void testSuccessReferencePayment() throws PaypalException {
        PaypalMoney paypalMoney = new PaypalMoney(100, CurrencyCodeType.USD);
        final PaypalResponse<String> paypalResponse = this.paypalMerchantAccountWrapper.referenceTransactionPayment("B-5SD57037W2513542C", paypalMoney, Optional.empty());

        assertThat(paypalResponse.getAckCodeType(), is(AckCodeType.SUCCESS));
        assertTrue(paypalResponse.getErrorTypes().isEmpty());
        assertTrue(paypalResponse.getData().isPresent());
    }

    @Test
    public void testSuccessReferencePaymentIdempotent() throws PaypalException {
        String idempontentKey = "AZAZAZAZAZAZAZAZ";

        PaypalMoney paypalMoney = new PaypalMoney(100, CurrencyCodeType.USD);
        final PaypalResponse<String> paypalInitialPaymentResponse = this.paypalMerchantAccountWrapper.
                referenceTransactionPayment("B-5SD57037W2513542C", paypalMoney, Optional.of(idempontentKey));

        assertThat(paypalInitialPaymentResponse.getAckCodeType(), is(AckCodeType.SUCCESS));
        assertTrue(paypalInitialPaymentResponse.getErrorTypes().isEmpty());
        assertTrue(paypalInitialPaymentResponse.getData().isPresent());

        final PaypalResponse<String> paypalSecondPaymentResponse = this.paypalMerchantAccountWrapper.
                referenceTransactionPayment("B-5SD57037W2513542C", paypalMoney, Optional.of(idempontentKey));

        assertThat(paypalInitialPaymentResponse.getAckCodeType(), is(AckCodeType.SUCCESS));
        assertTrue(paypalInitialPaymentResponse.getErrorTypes().isEmpty());
        assertTrue(paypalInitialPaymentResponse.getData().isPresent());
        assertEquals(paypalInitialPaymentResponse.getData().get(), paypalSecondPaymentResponse.getData().get());

    }
}
