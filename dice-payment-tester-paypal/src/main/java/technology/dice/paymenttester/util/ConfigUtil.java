/*
 *
 *  * Copyright (C) 2018 - present by Dice Technology Ltd.
 *  *
 *  * Please see distribution for license.
 *
 */

package technology.dice.paymenttester.util;

import technology.dice.paymenttester.config.PaypalAccountConfig;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class ConfigUtil {

    public static PaypalAccountConfig readPaypalAccountConfig(String configFile) throws IOException {
        Properties properties = new Properties();
        properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream(configFile));

        return new PaypalAccountConfig(properties.getProperty("clientId"),
                properties.getProperty("clientSecret"),
                properties.getProperty("clientSignature"),
                Optional.ofNullable(properties.getProperty("endpoint")));
    }

}
