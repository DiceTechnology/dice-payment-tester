package technology.dice.payment.stripe.service;

import com.google.common.collect.ImmutableMap;
import com.stripe.exception.StripeException;
import com.stripe.net.RequestOptions;
import technology.dice.payment.stripe.exception.DiceStripeException;
import technology.dice.payment.stripe.model.Product;
import technology.dice.payment.stripe.model.ProductDefinition;
import technology.dice.payment.stripe.util.ModelConvertor;

import javax.inject.Singleton;
import java.util.Objects;

import static technology.dice.payment.stripe.util.CollectionUtil.isNotEmpty;

@Singleton
public class ProductApiLayer {

    public Product createProduct(RequestOptions options, ProductDefinition productDefinition) throws DiceStripeException {
        String productId = productDefinition.getProductId();
        Objects.requireNonNull(productId);

        try {
            ImmutableMap.Builder<String, Object> productParams = ImmutableMap.builder();

            // identity
            productParams.put("id", productId);
            productParams.put("name", productId);

            productDefinition.getDescription().ifPresent(d -> productParams.put("description", d));

            if (isNotEmpty(productDefinition.getDimensions())) {
                productParams.put("attributes", productDefinition.getDimensions());
            }

            // meta-data
            if (isNotEmpty(productDefinition.getMetaData())) {
                productParams.put("metadata", productDefinition.getMetaData());
            }

            // state
            // NB : this works up to stripe api version 2017-08-15
            productParams.put("active", true);
            productParams.put("shippable", false);

            return ModelConvertor.convert(com.stripe.model.Product.create(productParams.build(), options));
        } catch (StripeException e) {
            throw new DiceStripeException(String.format("error creating product '%s'", productId), e);
        }
    }

}
