package technology.dice.payment.stripe.service;

import com.google.common.collect.ImmutableMap;
import com.stripe.exception.StripeException;
import com.stripe.net.RequestOptions;
import technology.dice.payment.stripe.util.ModelConvertor;
import technology.dice.payment.stripe.exception.DiceStripeException;
import technology.dice.payment.stripe.model.OrderDefinition;
import technology.dice.payment.stripe.model.OrderItem;
import technology.dice.payment.stripe.model.StripeCustomerId;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static technology.dice.payment.stripe.util.CollectionUtil.isNotEmpty;

@Singleton
public class OrderApiLayer {

    public String payOrder(RequestOptions options, StripeCustomerId stripeCustomerId, OrderDefinition orderDefinition, String idempotentKey) throws DiceStripeException {
        try {
            // create order items
            ImmutableMap.Builder<String, Object> orderParams = ImmutableMap.builder();

            orderParams.put("customer", stripeCustomerId.getId());
            orderParams.put("currency", orderDefinition.getCurrency());

            // TODO : handle coupon

            // order items
            List<OrderItem> orderItems = orderDefinition.getOrderItems();

            if (isNotEmpty(orderItems)) {
                orderParams.put("items", toOrderItemsMapValues(orderItems));
            }

            // meta-data
            orderParams.put("metadata", orderDefinition.getMetaData());

            com.stripe.model.Order order = com.stripe.model.Order.create(orderParams.build(), options);
            String orderId = order.getId();

            // pay order
            ImmutableMap.Builder<String, Object> payParams = ImmutableMap.builder();

            payParams.put("customer", stripeCustomerId.getId());        // we expect source to have been bound to customer first
//            payParams.put("email", customerEmailAddress);

            RequestOptions payOptions = buildPayRequestOptions(options, idempotentKey);
            return ModelConvertor.convert(order.pay(payParams.build(), payOptions));
        } catch (StripeException e) {
            throw new DiceStripeException(String.format("error creating order : customer='%s'", stripeCustomerId), e);
        }
    }

    private List<Map<String, Object>> toOrderItemsMapValues(List<OrderItem> orderItems) {
        List<Map<String, Object>> stripeOrderItems = new ArrayList<>();

        orderItems.forEach(oi -> stripeOrderItems.add(toOrderItemMapValues(oi)));

        return stripeOrderItems;
    }

    // NB : Order API doesn't care about amount/currency when using sku order type
    // NB : Order API doesn't seem to update description at all -- definitely a bug
    //      the doc is just too vague
    private Map<String, Object> toOrderItemMapValues(OrderItem orderItem) {
        Map<String, Object> stripeOrderItem = new HashMap<>();

        stripeOrderItem.put("quantity", orderItem.getQuantity());

//        orderItem.getDescription().ifPresent(d -> stripeOrderItem.put("description", d));

        stripeOrderItem.put("type", "sku");         // TODO : make this more flexible, e.g. discount, shipping, etc.
        stripeOrderItem.put("parent", orderItem.getSku());

        return stripeOrderItem;
    }

    private RequestOptions buildPayRequestOptions(RequestOptions options, String idempotentKey) {
        if (idempotentKey == null) {
            return options;         // existing one
        } else {
            return RequestOptions.builder().setApiKey(options.getApiKey()).setIdempotencyKey(idempotentKey).build();
        }
    }

}
