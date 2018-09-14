package technology.dice.dicepay.stripe;

import org.junit.*;

public class StripeTests {

//    Create customer,
    //first step credit card sent directly to Stripe
//test card 4242 4242 4242 4242
    //12 / 23
    //123
    //94301
    //test API key pk_test_mf2EFiEBC8HU7TxCVe8gVTJJ
    //https://api.stripe.com/v1/customers
//    sk_test_dbta2AXoS7FNUbxVhiY1DPZY
//    source=tok_1DAEpTC3zUDJPsliIkxaaxGj
//    description="Mia Jackson"
//   -d email="mia.jackson.13@example.com"


//    Publishable pk_test_mf2EFiEBC8HU7TxCVe8gVTJJ
//    Secret sk_test_dbta2AXoS7FNUbxVhiY1DPZY
    @Before

    @Test
    public void createCustomerTest(){
        StripeCustomer expectedStripeCustomer = new StripeCustomer(name, email address, meta-data);
        //magic:
//    stripeCustomer = stripePaymentProvider.createCustomer();    // name, email address, meta-data

        //Asserts
    }

    @Test
    public void generateCardTokenTest(){
        StripeCardToken expectedStripeCardToken = new StripeCardToken();
//    stripeCardToken = stripePaymentProvider.generateCardToken();       // from card
        //Asserts

    }

    @Test
    public void payOrderTest(){
        result
//    result = stripePaymentProvider.payOrder(stripeCustomer, stripeCardToken, amount);
        //Asserts

    }

    @Test
    public void getPaymentDetailsTest(){
        paymentCharges
//    paymentCharges = stripePaymentProvider.getPaymentDetails(stripeCustomer);
        //Asserts


    }


}
