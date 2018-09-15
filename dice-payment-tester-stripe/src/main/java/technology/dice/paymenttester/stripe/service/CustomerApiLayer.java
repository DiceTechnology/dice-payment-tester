/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.service;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.stripe.exception.StripeException;
import com.stripe.net.RequestOptions;
import technology.dice.paymenttester.stripe.exception.DiceStripeException;
import technology.dice.paymenttester.stripe.model.Customer;
import technology.dice.paymenttester.stripe.model.CustomerDefinition;
import technology.dice.paymenttester.stripe.model.StripeCardToken;
import technology.dice.paymenttester.stripe.model.StripeCustomerId;
import technology.dice.paymenttester.stripe.util.ModelConvertor;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static technology.dice.paymenttester.stripe.util.CollectionUtil.isNotEmpty;

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

    public Customer bindCardToken(RequestOptions options, StripeCustomerId stripeCustomerId, StripeCardToken stripeCardToken) throws DiceStripeException {
        try {
            com.stripe.model.Customer customer = com.stripe.model.Customer.retrieve(stripeCustomerId.getId(), options);

            if (customer == null) {
                throw new DiceStripeException(String.format("customer '%s' is not found", stripeCustomerId.getId()));
            }

            return ModelConvertor.convert(customer.update(ImmutableMap.of("source", stripeCardToken.getToken()), options));
        } catch (StripeException e) {
            throw new DiceStripeException(String.format("error bind card token to customer '%s'", stripeCustomerId.getId()), e);
        }
    }
}
