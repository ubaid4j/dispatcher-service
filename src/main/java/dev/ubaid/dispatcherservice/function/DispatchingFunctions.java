package dev.ubaid.dispatcherservice.function;


import dev.ubaid.dispatcherservice.dto.OrderAcceptedMessage;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DispatchingFunctions {

    @Bean
    public Function<OrderAcceptedMessage, Long> pack() {
        return orderAcceptedMessage -> {
            log.info("The order with id {} is packed.",
                orderAcceptedMessage.orderId());
            return orderAcceptedMessage.orderId();
        };
    }
}
