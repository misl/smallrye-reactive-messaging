package io.smallrye.reactive.messaging.http;

import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.Collections;

public class HttpExample {

    // tag::http-message[]
    @Incoming("source")
    @Outgoing("to-http")
    public HttpMessage<JsonObject> process(Message<String> incoming) {
        return HttpMessage.HttpMessageBuilder.<JsonObject>create()
            .withMethod("PUT")
            .withPayload(new JsonObject().put("value", incoming.getPayload().toUpperCase()))
            .withHeader("Content-Type", "application/json")
            .build();
    }
    // end::http-message[]

    // tag::raw-message[]
    @Incoming("source")
    @Outgoing("to-http")
    public Message<JsonObject> handle(Message<String> incoming) {
        return Message.of(new JsonObject().put("value", incoming.getPayload().toUpperCase()))
            .withMetadata(Metadata.of(
               HttpMetadata.HEADERS, "PUT",
               HttpMetadata.HEADERS, Collections.singletonMap("Content-Type", "application/json")
            ));
    }
    // end::raw-message[]
}
