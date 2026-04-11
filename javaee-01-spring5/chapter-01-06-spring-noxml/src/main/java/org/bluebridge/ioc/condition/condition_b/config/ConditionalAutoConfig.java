package org.bluebridge.ioc.condition.condition_b.config;

import org.bluebridge.ioc.condition.condition_b.componment.RandBooleanCondition;
import org.bluebridge.ioc.condition.condition_b.componment.RandDataComponent;
import org.bluebridge.ioc.condition.condition_b.componment.RandIntCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/8 14:25
 */
@Configuration
public class ConditionalAutoConfig {

    @Bean
    @Conditional(RandIntCondition.class)
    public RandDataComponent<Integer> randIntComponent(){
        return new RandDataComponent<>(()->{
            Random random = new Random();
            return random.nextInt(1024);
        });
    }

    @Bean
    @Conditional(RandBooleanCondition.class)
    public RandDataComponent<Boolean> randBooleanComponent(){
        return new RandDataComponent<>(()->{
            Random random = new Random();
            return random.nextBoolean();
        });
    }
}
