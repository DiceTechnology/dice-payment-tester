package technology.dice.payment.stripe.service;

import com.google.common.collect.ImmutableMap;
import com.stripe.exception.StripeException;
import com.stripe.net.RequestOptions;
import technology.dice.payment.stripe.util.ModelConvertor;
import technology.dice.payment.stripe.exception.DiceStripeException;
import technology.dice.payment.stripe.model.ChargeablePrice;
import technology.dice.payment.stripe.model.SkuDefinition;
import technology.dice.payment.stripe.model.SkuId;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static technology.dice.payment.stripe.util.CollectionUtil.isNotEmpty;

@Singleton
public class SkuApiLayer {

    public SkuId createSku(RequestOptions options, SkuDefinition skuDefinition) throws DiceStripeException {
        try {
            ImmutableMap.Builder<String, Object> skuParams = ImmutableMap.builder();

            // identity
            skuParams.put("product", skuDefinition.getProductId().getId());
            skuParams.put("id", skuDefinition.getSkuId().getId());

            ChargeablePrice price = skuDefinition.getPrice();

            skuParams.put("currency", price.getCurrency().getCurrencyCode());
            skuParams.put("price", price.getAmount());

            // dimension
            if (isNotEmpty(skuDefinition.getDimensionValues())) {
                skuParams.put("attributes", skuDefinition.getDimensionValues());
            }

            // meta-data
            if (isNotEmpty(skuDefinition.getMetaData())) {
                skuParams.put("metadata", skuDefinition.getMetaData());
            }

            // NB : we don't currently care about inventory params -- just make it infinite
            Map<String, Object> inventoryParams = new HashMap<String, Object>();
            inventoryParams.put("type", "infinite");

            skuParams.put("inventory", inventoryParams);

            // state
            // NB : this works up to stripe api version 2017-08-15
            skuParams.put("active", true);

            return ModelConvertor.convert(com.stripe.model.SKU.create(skuParams.build(), options));
        } catch (StripeException e) {
            throw new DiceStripeException(String.format("error creating sku '%s/%s'", skuDefinition.getProductId(), skuDefinition.getSkuId()), e);
        }
    }

}
