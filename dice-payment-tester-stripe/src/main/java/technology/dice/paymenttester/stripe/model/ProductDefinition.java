package technology.dice.paymenttester.stripe.model;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ProductDefinition {
    private final String productId;
    private final List<String> dimensions;
    private final Map<String, Object> metaData;
    private Optional<String> description;

    public ProductDefinition(String productId, List<String> dimensions) {
        Objects.requireNonNull(productId, "product id is compulsory");
        this.productId = productId;
        this.dimensions = dimensions;
        this.metaData = ImmutableMap.of();
        this.description = Optional.empty();
    }

    public String getProductId() {
        return productId;
    }

    public List<String> getDimensions() {
        return dimensions;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }
}
