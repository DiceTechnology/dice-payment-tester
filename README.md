# DiCE Payment Tester
Payment Providers integration testing made easy.

Dice Payment Tester is a way of reaching out to the payment providers and through api calls that you will then be able to compare with what your application in test will return to you. It's no more than wrappers around those api calls to make an integratio n in your automation suite much easier.

Dice Payment Tester has currently 2 integrations with:
- Stripe 
- Paypal.

It's only purpose is to aid on either automation tests or even manual testing by creating the scenarios you want on the payment provider side in a much easier and faster way.

# Getting Started

Clone the project to your local running environment.

Compile into a jar file and all you have to do is import it on your own project.

# Pre-requisites

For Paypal testing a number of preconditions need to exist. These are:
* A paypal sandbox account already need to exist
* Paypal Billing Agreement needs to be created and accepted beforehand.

If those conditions are met, you are good to go to start using Dice Payment Tester.

## Running the tests

To run the example tests for the library:
```mvn test```

These are some example tests that give you an idea on how to use it.

They, for example, allow you to:
* Create a customer in Stripe,
``` stripePaymentProvider.createCustomer(); ```

* Add a card to it
```stripePaymentProvider.generateCardToken();```

* Make a payment,
```stripePaymentProvider.payOrder(stripeCustomer, stripeCardToken, amount);```

so you don't have to it all the time!

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors
wip

## License
wip
