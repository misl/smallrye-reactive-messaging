package acme;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.reactive.messaging.Emitter;
import io.smallrye.reactive.messaging.annotations.Channel;

@ApplicationScoped
public class BeanUsingAnEmitter {

    @Inject
    @Channel("my-channel")
    Emitter<String> emitter;

    public void periodicallySendMessageToKafka() {
        AtomicInteger counter = new AtomicInteger();
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> {
                    emitter.send("Hello " + counter.getAndIncrement());
                },
                        1, 1, TimeUnit.SECONDS);
    }

}
