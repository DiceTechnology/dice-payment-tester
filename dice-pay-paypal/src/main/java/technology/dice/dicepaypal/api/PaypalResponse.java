package technology.dice.dicepaypal.api;

import com.google.common.collect.ImmutableList;
import urn.ebay.apis.eBLBaseComponents.AckCodeType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class PaypalResponse<T> {

    private final Optional<T> data;
    private final AckCodeType ackCodeType;
    private final List<ErrorType> errorTypes;

    public PaypalResponse(AckCodeType ackCodeType, @Nullable T data, List<ErrorType> errorTypes) {
        this.data = Optional.of(data);
        this.ackCodeType = ackCodeType;
        this.errorTypes = errorTypes;
    }

    public PaypalResponse(AckCodeType ackCodeType, List<ErrorType> errorTypes) {
        this.data = Optional.empty();
        this.ackCodeType = ackCodeType;
        this.errorTypes = errorTypes;
    }

    public PaypalResponse(AckCodeType ackCodeType, T data) {
        this.data = Optional.of(data);
        this.ackCodeType = ackCodeType;
        this.errorTypes = ImmutableList.of();
    }

    public Optional<T> getData() {
        return data;
    }

    public AckCodeType getAckCodeType() {
        return ackCodeType;
    }

    public List<ErrorType> getErrorTypes() {
        return errorTypes;
    }
}
