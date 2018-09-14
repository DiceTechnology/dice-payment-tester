package technology.dice.payment.stripe.util;

import technology.dice.payment.stripe.model.StripeCustomerId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlanUtil {
    private static final DateTimeFormatter SUBSCRIPTION_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static String generatePlanId(StripeCustomerId stripeCustomerId) {
        String dateTimeSuffix = SUBSCRIPTION_DATE_TIME_FORMATTER.format(LocalDateTime.now());

        return String.format("%s-%s", stripeCustomerId.getId(), dateTimeSuffix).replace(" ", "");
    }

    public static String generatePlanName(String planId) {
        return planId.replace("-", " : ");
    }

}
