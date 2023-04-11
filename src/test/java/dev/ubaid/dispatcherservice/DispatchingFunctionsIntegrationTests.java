package dev.ubaid.dispatcherservice;

import dev.ubaid.dispatcherservice.dto.OrderAcceptedMessage;
import dev.ubaid.dispatcherservice.dto.OrderDispatchedMessage;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@FunctionalSpringBootTest
public class DispatchingFunctionsIntegrationTests {

    @Autowired
    private FunctionCatalog catalog;

    @Test
    void packAndLabelOrder() {
        final String functionDefinition = "pack|label";

        Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> packAndLabel
            = catalog.lookup(Function.class, functionDefinition);

        long orderId = 121;

        StepVerifier
            .create(packAndLabel.apply(new OrderAcceptedMessage(orderId)))
            .expectNextMatches(dispatchedOrder -> dispatchedOrder.equals(new OrderDispatchedMessage(orderId)))
            .verifyComplete();
    }
}
