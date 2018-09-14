package technology.dice.dicepaypal.merchant;

import com.google.common.collect.ImmutableMap;
import technology.dice.dicepaypal.api.PaypalMoney;
import technology.dice.dicepaypal.api.PaypalResponse;
import technology.dice.dicepaypal.api.exception.PaypalException;
import technology.dice.dicepaypal.config.PaypalAccountConfig;
import urn.ebay.api.PayPalAPI.DoReferenceTransactionReq;
import urn.ebay.api.PayPalAPI.DoReferenceTransactionRequestType;
import urn.ebay.api.PayPalAPI.DoReferenceTransactionResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.DoReferenceTransactionRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;

import java.util.Map;
import java.util.Optional;

public class PaypalMerchantAccountWrapper {

    private final PaypalAccountConfig paypalAccountConfig;

    public PaypalMerchantAccountWrapper(PaypalAccountConfig paypalAccountConfig) {
        this.paypalAccountConfig = paypalAccountConfig;
    }

    public PaypalResponse<String> referenceTransactionPayment(String billingAgreement, PaypalMoney money, Optional<String> idempotentKey) throws PaypalException {

        DoReferenceTransactionRequestType details = new DoReferenceTransactionRequestType();

        BasicAmountType amt = money.getBasicAmountType();

        DoReferenceTransactionRequestDetailsType transactionDetails = new DoReferenceTransactionRequestDetailsType();
        transactionDetails.setReferenceID(billingAgreement);
        idempotentKey.ifPresent(ik -> transactionDetails.setMsgSubID(ik));
        transactionDetails.setPaymentAction(PaymentActionCodeType.SALE);
        PaymentDetailsType paymentDetails = new PaymentDetailsType();
        paymentDetails.setOrderTotal(amt);
        transactionDetails.setPaymentDetails(paymentDetails);
        details.setDoReferenceTransactionRequestDetails(transactionDetails);

        final DoReferenceTransactionReq doReferenceTransactionReq = new DoReferenceTransactionReq();
        doReferenceTransactionReq.setDoReferenceTransactionRequest(details);

        try {
            final DoReferenceTransactionResponseType response = createApiService().doReferenceTransaction(doReferenceTransactionReq);
            return new PaypalResponse<>(response.getAck(), response.getDoReferenceTransactionResponseDetails().getTransactionID(), response.getErrors());
        } catch (Exception e) {
            throw new PaypalException("Error executing payment", e);
        }
    }

    private PayPalAPIInterfaceServiceService createApiService() {
        return new PayPalAPIInterfaceServiceService(initConfigMap());
    }

    private Map<String, String> initConfigMap() {

        // Configuration map containing signature credentials and other required configuration.
        // For a full list of configuration parameters refer in wiki page.
        // (https://github.com/paypal/sdk-core-java/blob/master/README.md)

        final ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder()
                .put("mode", paypalAccountConfig.getMode())
                .put("acct1.UserName", paypalAccountConfig.getClientId())
                .put("acct1.Password", paypalAccountConfig.getClientSecret())
                .put("acct1.Signature", paypalAccountConfig.getClientSignature());

        paypalAccountConfig.getEndpoint().ifPresent(endpoint -> builder.put("service.EndPoint", endpoint));

        return builder.build();
    }

}
