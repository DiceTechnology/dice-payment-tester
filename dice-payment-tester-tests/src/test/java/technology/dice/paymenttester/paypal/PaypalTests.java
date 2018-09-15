package technology.dice.paymenttester.paypal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import technology.dice.paymenttester.api.PaypalMoney;
import technology.dice.paymenttester.api.PaypalResponse;
import technology.dice.paymenttester.api.exception.PaypalException;
import technology.dice.paymenttester.config.PaypalAccountConfig;
import technology.dice.paymenttester.merchant.PaypalMerchantAccountWrapper;
import technology.dice.paymenttester.util.ConfigUtil;
import urn.ebay.apis.eBLBaseComponents.AckCodeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PaypalTests {

    private PaypalMerchantAccountWrapper paypalMerchantAccountWrapper;
    private final String BILLINGAGREEMENT = "B-93T965351W931242W";

    @Before
    public void setUp() throws IOException {
        PaypalAccountConfig paypalAccountConfig = ConfigUtil.readPaypalAccountConfig("config.properties");
        this.paypalMerchantAccountWrapper = new PaypalMerchantAccountWrapper(paypalAccountConfig);
    }

    @Test
    public void testSuccessReferencePayment() throws PaypalException {
        PaypalMoney paypalMoney = new PaypalMoney(100, CurrencyCodeType.USD);
        final PaypalResponse<String> paypalResponse = this.paypalMerchantAccountWrapper.referenceTransactionPayment(BILLINGAGREEMENT, paypalMoney, Optional.empty());

        assertThat(paypalResponse.getAckCodeType(), is(AckCodeType.SUCCESS));
        assertTrue(paypalResponse.getErrorTypes().isEmpty());
        assertTrue(paypalResponse.getData().isPresent());
    }

    @Test
    public void testSuccessReferencePaymentIdempotent() throws PaypalException {
        String idempontentKey = "AZAZAZAZAZAZAZAZ";

        PaypalMoney paypalMoney = new PaypalMoney(100, CurrencyCodeType.USD);
        final PaypalResponse<String> paypalInitialPaymentResponse = this.paypalMerchantAccountWrapper.
                referenceTransactionPayment(BILLINGAGREEMENT, paypalMoney, Optional.of(idempontentKey));

        assertThat(paypalInitialPaymentResponse.getAckCodeType(), is(AckCodeType.SUCCESS));
        assertTrue(paypalInitialPaymentResponse.getErrorTypes().isEmpty());
        assertTrue(paypalInitialPaymentResponse.getData().isPresent());

        final PaypalResponse<String> paypalSecondPaymentResponse = this.paypalMerchantAccountWrapper.
                referenceTransactionPayment(BILLINGAGREEMENT, paypalMoney, Optional.of(idempontentKey));

        assertThat(paypalInitialPaymentResponse.getAckCodeType(), is(AckCodeType.SUCCESS));
        assertTrue(paypalInitialPaymentResponse.getErrorTypes().isEmpty());
        assertTrue(paypalInitialPaymentResponse.getData().isPresent());
        assertEquals(paypalInitialPaymentResponse.getData().get(), paypalSecondPaymentResponse.getData().get());
    }

    @Test
    public void testCreateAuthorizationTokenSuccess() throws PaypalException {
        final PaypalResponse<String> authorizationTokenResponse = this.paypalMerchantAccountWrapper.createAuthorizationToken();

        assertThat(authorizationTokenResponse.getAckCodeType(), is(AckCodeType.SUCCESS));
        assertTrue(authorizationTokenResponse.getErrorTypes().isEmpty());
        assertTrue(authorizationTokenResponse.getData().isPresent());
    }

    @Ignore
    @Test
    public void testCreateBillingAuthorizationTokenSuccess() throws PaypalException{
        String authorisationToken = this.paypalMerchantAccountWrapper.createAuthorizationToken().getData().get();

        final PaypalResponse<String> response = this.paypalMerchantAccountWrapper.createBillingAgreement(authorisationToken);

        // Need to go to https://developer.paypal.com/demo/checkout/#/pattern/client and put the generated token in the code, and click the button.
        // Popup will ask you to login, login as a buyer and need to accept the agreement and pay, only after the agreement is deemed as created.
        String billingAgreementId = response.getData().get();

    }

    @Test
    public void testSuccessfulFullPaymentRefund() throws PaypalException {
        PaypalMoney paypalMoney = new PaypalMoney(100, CurrencyCodeType.USD);

        String transactionId = this.paypalMerchantAccountWrapper.referenceTransactionPayment(BILLINGAGREEMENT, paypalMoney, Optional.empty()).getData().get();

        PaypalMoney paypalMoneyToRefund = new PaypalMoney(100, CurrencyCodeType.USD);
        final PaypalResponse<String> response = this.paypalMerchantAccountWrapper.refundPayment(transactionId, Optional.of(paypalMoney),Optional.empty());

        assertThat(response.getAckCodeType(), is(AckCodeType.SUCCESS));
        assertTrue(response.getErrorTypes().isEmpty());
        assertTrue(response.getData().isPresent());







    }
}
