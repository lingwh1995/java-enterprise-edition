package org.bluebridge.guard.component;

import org.bluebridge.guard.constant.Pbkdf2Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/31 17:53
 */
public class Pbkdf2ConstantsProcessor implements EnvironmentPostProcessor {

    private static final String PREFIX = "boost.security.crypto.pbkdf2.";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 1. 从环境对象中读取配置（支持松散绑定，Spring底层已处理）
        Boolean enabled = environment.getProperty(PREFIX + "enabled", Boolean.class, true);
        String algorithm = environment.getProperty(PREFIX + "algorithm", String.class, "PBKDF2WithHmacSHA1");
        Integer keySizeBits = environment.getProperty(PREFIX + "key-size-bits", Integer.class, 256);
        Integer iterationCount = environment.getProperty(PREFIX + "iteration-count", Integer.class, 1000);

        // 2. 静态赋值
        Pbkdf2Constants.ENABLED = enabled;
        Pbkdf2Constants.ALGORITHM = algorithm;
        Pbkdf2Constants.KEY_SIZE_BITS = keySizeBits;
        Pbkdf2Constants.ITERATION_COUNT = iterationCount;
    }

}
