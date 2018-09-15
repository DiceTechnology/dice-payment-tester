/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.stripe.service;

import com.google.common.collect.Streams;
import com.stripe.exception.StripeException;
import com.stripe.net.RequestOptions;
import technology.dice.paymenttester.stripe.exception.DiceStripeException;
import technology.dice.paymenttester.stripe.model.StripeCharge;
import technology.dice.paymenttester.stripe.model.StripeCustomerId;
import technology.dice.paymenttester.stripe.util.ModelConvertor;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Singleton
public class ChargeApiLayer {

    public List<StripeCharge> listCharges(RequestOptions options,
                                          StripeCustomerId customerId) throws DiceStripeException {
        try {
            Map<String, Object> invoiceParams = new HashMap<>();

            invoiceParams.put("customer", customerId.getId());

            return Streams.stream(com.stripe.model.Charge.list(invoiceParams, options).autoPagingIterable())
                    .map(i -> ModelConvertor.convert(i))
                    .collect(toList());
        } catch (StripeException e) {
            throw new DiceStripeException(String.format("error fetching charges for customer '%s'", customerId.getId()), e);
        }
    }

}
