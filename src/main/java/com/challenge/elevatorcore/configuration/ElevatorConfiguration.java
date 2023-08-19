package com.challenge.elevatorcore.configuration;

import com.challenge.elevatorcore.entities.validation.WeightLimitChecker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ElevatorConfiguration {

    /*@Value("#{'${building.adminAccessFloors}'.replaceAll('[\\[\\]]', '').split(',')}")
    private List<Integer> adminAccessFloors;*/

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

    /*@Bean(name = "publicElevator")
    public PublicElevator publicElevator() {
        return new PublicElevator(
                publicElevatorWeightLimitChecker(),
                keyAccessFilter());
    }

    @Bean(name = "freightElevator")
    public FreightElevator freightElevator() {
        return new FreightElevator(
                freightElevatorWeightLimitChecker());
    }*/

}
