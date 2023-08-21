package com.challenge.elevatorcore.configuration;

import com.challenge.elevatorcore.entities.validation.WeightLimitChecker;
import com.challenge.elevatorcore.gateways.events.ElevatorEventSourceGateway;
import com.challenge.elevatorcore.gateways.events.InMemoryEventSourceQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
public class ElevatorConfiguration {

    @Value("${freight.elevator.weight.limit}")
    private String freightElevatorWeightLimit;

    @Value("${public.elevator.weight.limit}")
    private String publicElevatorWeightLimit;

    @Bean
    @Qualifier("freightElevatorWeightLimitChecker")
    public WeightLimitChecker freightElevatorWeightLimitChecker() {
        return new WeightLimitChecker(new BigDecimal(freightElevatorWeightLimit));
    }

    @Bean
    @Qualifier("publicElevatorWeightLimitChecker")
    public WeightLimitChecker publicElevatorWeightLimitChecker() {
        return new WeightLimitChecker(new BigDecimal(publicElevatorWeightLimit));
    }

    @Bean
    @Scope("prototype")
    public ElevatorEventSourceGateway eventSourceGateway() {
        return new InMemoryEventSourceQueue(new ConcurrentLinkedQueue<>());
    }

}
