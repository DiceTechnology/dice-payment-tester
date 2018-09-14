package technology.dice.paymenttester.stripe.service;

import javax.inject.Singleton;

@Singleton
public class DisputeApiLayer {

    // NB : our stripe api version doesn't support dispute creation -- look this is driven entirely by the special stripe test tokens
    //  we can list them though
    //  we can also not be able to force a win/loss situation -- will need to play with its update operation
}
