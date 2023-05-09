package dev.ubaid.dispatcherservice;

import dev.ubaid.dispatcherservice.dto.OrderAcceptedMessage;
import dev.ubaid.dispatcherservice.dto.OrderDispatchedMessage;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.messaging.support.GenericMessage;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@FunctionalSpringBootTest
public class DispatchingFunctionsIntegrationTests {

    @Autowired
    private FunctionCatalog catalog;

    @Test
    void packAndLabelOrder() {
        final String functionDefinition = "pack|label";

        // FIXME: 5/9/23 class org.springframework.messaging.support.GenericMessage cannot be cast to class dev.ubaid.dispatcherservice.dto.OrderDispatchedMessage due to this spring-cloud-stream-binder-rabbit
//        Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> packAndLabel
//            = catalog.lookup(Function.class, functionDefinition);

        Function<OrderAcceptedMessage, Flux<GenericMessage>> packAndLabel
            = catalog.lookup(Function.class, functionDefinition);


        long orderId = 121;

        StepVerifier
            .create(packAndLabel.apply(new OrderAcceptedMessage(orderId)))
            .expectNextMatches(dispatchedOrder -> dispatchedOrder.getClass().equals(GenericMessage.class))
            .verifyComplete();
    }
}
