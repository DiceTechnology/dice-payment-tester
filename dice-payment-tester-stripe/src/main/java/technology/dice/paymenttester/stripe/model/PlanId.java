package technology.dice.paymenttester.stripe.model;

public class PlanId {
    private final String id;

    public PlanId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
