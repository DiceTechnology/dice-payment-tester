package technology.dice.payment.stripe.service;

import com.google.common.base.Strings;
import com.stripe.exception.StripeException;
import com.stripe.net.RequestOptions;
import technology.dice.payment.stripe.exception.DiceStripeException;
import technology.dice.payment.stripe.model.Customer;
import technology.dice.payment.stripe.model.CustomerDefinition;
import technology.dice.payment.stripe.model.StripeCardToken;
import technology.dice.payment.stripe.model.StripeCustomerId;
import technology.dice.dicepay.stripe.util.ModelConvertor;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static technology.dice.dicepay.stripe.util.CollectionUtil.isNotEmpty;

@Singleton
public class CustomerApiLayer {

    public Customer createCustomer(RequestOptions options, CustomerDefinition customerDefinition, Optional<StripeCardToken> cardToken) throws DiceStripeException {
        StripeCustomerId customerId = new StripeCustomerId(customerDefinition.getCustomerId());
        Objects.requireNonNull(customerId, "customer id is compulsory");

        try {
            Map<String, Object> customerParams = new HashMap<>();

            // identity
            customerParams.put("id", customerId.getId());
            customerDefinition.getDescription().ifPresent(d -> customerParams.put("description", d));
            cardToken.ifPresent(ct -> customerParams.put("source", ct.getToken()));

            // meta-data
            if (isNotEmpty(customerDefinition.getMetaData())) {
                customerParams.put("metadata", customerDefinition.getMetaData());
            }

            customerDefinition.getEmail()
                    .filter(e -> !Strings.isNullOrEmpty(e))
                    .ifPresent(e -> customerParams.put("email", e));

            return ModelConvertor.convert(com.stripe.model.Customer.create(customerParams, options));
        } catch (StripeException e) {
            throw new DiceStripeException("error creating customer", e);
        }
    }

//    public Optional<Customer> getCustomer(RequestOptions options, StripeCustomerId customerId) throws DceStripeException {
//        try {
//            return Optional.ofNullable(convert(com.stripe.model.Customer.retrieve(customerId.getCustomerCode(), options)));
//        } catch (StripeException e) {
//            return Optional.empty();
//        }
//    }
}
