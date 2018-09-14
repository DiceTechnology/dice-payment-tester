package technology.dice.payment.stripe.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class SkuDefinition {
    private final ProductId productId;
    private final SkuId skuId;
    private final ChargeablePrice price;
    private final Map<String, String> dimensionValues;
    private final Map<String, Object> metaData;

    public SkuDefinition(ProductId productId, SkuId skuId, ChargeablePrice price, Map<String, String> dimensionValues) {
        this.productId = productId;
        this.skuId = skuId;
        this.price = price;
        this.dimensionValues = dimensionValues;
        this.metaData = ImmutableMap.of();
    }

    public ProductId getProductId() {
        return productId;
    }

    public SkuId getSkuId() {
        return skuId;
    }

    public ChargeablePrice getPrice() {
        return price;
    }

    public Map<String, String> getDimensionValues() {
        return dimensionValues;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }

    @Override
    public String toString() {
        return getSkuId().toString();
    }
}
