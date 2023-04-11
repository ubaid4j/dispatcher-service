package dev.ubaid.dispatcherservice.function;


import dev.ubaid.dispatcherservice.dto.OrderAcceptedMessage;
import dev.ubaid.dispatcherservice.dto.OrderDispatchedMessage;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

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

    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label() {
        return orderFlux -> orderFlux.map(orderId -> {
           log.info("The order with id {} is labeled.", orderId);
           return new OrderDispatchedMessage(orderId);
        });
    }
}
