/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.merchant;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import technology.dice.paymenttester.api.PaypalAccountBalance;
import technology.dice.paymenttester.api.PaypalMoney;
import technology.dice.paymenttester.api.PaypalResponse;
import technology.dice.paymenttester.api.exception.PaypalException;
import technology.dice.paymenttester.config.PaypalAccountConfig;
import technology.dice.paymenttester.util.ModelMapperUtil;
import urn.ebay.api.PayPalAPI.*;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.*;

import java.util.Map;
import java.util.Optional;

public class PaypalMerchantAccountWrapper {

    private static final String RETURN_URL = "https://localhost/return";
    private static final String CANCEL_URL = "https://localhost/cancel";

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
            return new PaypalResponse<>(response.getAck(), response.getDoReferenceTransactionResponseDetails().getPaymentInfo().getTransactionID(), response.getErrors());
        } catch (Exception e) {
            throw new PaypalException("Error executing payment", e);
        }
    }

    public PaypalResponse<String> refundPayment(String transactionId, Optional<PaypalMoney> refundValue, Optional<String> idempotentKey) throws PaypalException {
        final RefundTransactionRequestType requestType = new RefundTransactionRequestType();
        requestType.setTransactionID(transactionId);
        idempotentKey.ifPresent(ik -> requestType.setMsgSubID(ik));

        requestType.setRefundType(refundValue.map(dceMoney -> RefundType.PARTIAL).orElse(RefundType.FULL));
        refundValue.ifPresent(money -> requestType.setAmount(money.getBasicAmountType()));

        final RefundTransactionReq request = new RefundTransactionReq();
        request.setRefundTransactionRequest(requestType);

        try {
            final RefundTransactionResponseType response = createApiService().refundTransaction(request);
            return new PaypalResponse<>(response.getAck(), response.getRefundTransactionID(), response.getErrors());
        } catch (Exception e) {
            throw new PaypalException("Error refund payment", e);
        }
    }

    public PaypalResponse<String> createAuthorizationToken() throws PaypalException {

        // Request type
        final SetExpressCheckoutRequestType requestType = new SetExpressCheckoutRequestType();
        final SetExpressCheckoutRequestDetailsType requestDetailsType = new SetExpressCheckoutRequestDetailsType();
        requestType.setSetExpressCheckoutRequestDetails(requestDetailsType);

        // Request specification
        requestDetailsType.setReturnURL(RETURN_URL);
        requestDetailsType.setCancelURL(CANCEL_URL);
        requestDetailsType.setBillingAgreementDetails(ImmutableList.of(new BillingAgreementDetailsType(BillingCodeType.MERCHANTINITIATEDBILLING)));

        // PaymentDetailsType
        final PaymentDetailsType paymentDetailsType = new PaymentDetailsType();
        paymentDetailsType.setPaymentAction(PaymentActionCodeType.SALE);
        requestDetailsType.setPaymentDetails(ImmutableList.of(paymentDetailsType));


        final SetExpressCheckoutReq request = new SetExpressCheckoutReq();
        request.setSetExpressCheckoutRequest(requestType);

        try {
            final SetExpressCheckoutResponseType response = createApiService().setExpressCheckout(request);
            return new PaypalResponse<>(response.getAck(), response.getToken(), response.getErrors());
        } catch (Exception e) {
            throw new PaypalException("Error creating authorization token", e);
        }
    }

    public PaypalResponse<String> createBillingAgreement(String authorizationToken) throws PaypalException {
        // Request type
        final CreateBillingAgreementRequestType requestType = new CreateBillingAgreementRequestType();

        // Request specification
        requestType.setToken(authorizationToken);

        final CreateBillingAgreementReq createBillingAgreementReq = new CreateBillingAgreementReq();
        createBillingAgreementReq.setCreateBillingAgreementRequest(requestType);

        try {
            final CreateBillingAgreementResponseType response = createApiService().createBillingAgreement(createBillingAgreementReq);
            return new PaypalResponse<>(response.getAck(), response.getBillingAgreementID(), response.getErrors());
        } catch (Exception e) {
            throw new PaypalException("Error creating billing agreement", e);
        }
    }

    public PaypalResponse<PaypalAccountBalance> getAccountBalance(boolean allCurrencies) throws PaypalException {
        final GetBalanceRequestType requestType = new GetBalanceRequestType();
        requestType.setReturnAllCurrencies(ModelMapperUtil.fromBoolean(allCurrencies));

        final GetBalanceReq getBalanceReq = new GetBalanceReq();
        getBalanceReq.setGetBalanceRequest(requestType);

        try {
            final GetBalanceResponseType response = createApiService().getBalance(getBalanceReq);
            return new PaypalResponse<>(response.getAck(),
                    ModelMapperUtil.convert(response.getBalance(), response.getBalanceHoldings()), response.getErrors());
        } catch (Exception e) {
            throw new PaypalException("Error getting balance", e);
        }
    }

    public PaypalResponse<Void> changeBillingAgreementStatus(final String billingAgreement, final MerchantPullStatusCodeType statusCodeType) throws PaypalException {
        // Request type
        final BAUpdateRequestType requestType = new BAUpdateRequestType();

        // Request specification
        requestType.setReferenceID(billingAgreement);
        requestType.setBillingAgreementStatus(statusCodeType);

        final BillAgreementUpdateReq billAgreementUpdateReq = new BillAgreementUpdateReq();
        billAgreementUpdateReq.setBAUpdateRequest(requestType);

        try {
            final BAUpdateResponseType response = createApiService().billAgreementUpdate(billAgreementUpdateReq);
            return new PaypalResponse<>(response.getAck(), response.getErrors());
        } catch (Exception e) {
            throw new PaypalException("Error changing billing agreement status", e);
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
