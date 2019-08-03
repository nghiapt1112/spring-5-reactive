package com.nghia.reactive.web.ng.reactive.web;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FluxTest {
    List elements;

    @Before
    public void beforeEachTest() {
        this.elements = new ArrayList<Integer>();
    }

    @Test
    public void subscribeFlux() {
        Flux.just(1, 2, 3, 4, 5)
                .log()
                .subscribe(elements::add);
        Assert.assertEquals(elements.size(), 5);
    }


    /**
     * cái này giống với cái trên, để hiểu rõ hơn thì implement cái interface Subscribe.
     * Prove được rằng:
     * We can see that each possible stage in the above flow maps to a method in the Subscriber implementation.
     * It just happens that the Flux has provided us with a helper method to reduce this verbosity.
     */
    @Test
    public void subscribeInterfaceFlux() {
        Flux.just(1, 2, 3, 4, 5)
                .log()
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        elements.add(integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * Backpressure muốn downstream gửi 1 request lên upStream bảo rằng: gửi ít data thôi, để tránh bị overwhelmed
     */
    @Test
    public void backPressureFlux() {

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(new Subscriber<Integer>() {
                    int onNextAmount;
                    private Subscription s;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.s = s;
                        s.request(2);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        elements.add(integer);
                        onNextAmount++;
                        if (onNextAmount % 2 == 0) {
                            s.request(2);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * Advance on Flux: Combining 2 streams
     */
    @Test
    public void combineStreams() {
        elements = new ArrayList<String>();
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .zipWith(Flux.range(0, Integer.MAX_VALUE),
                        (two, one) -> String.format("First Flux: %d, Second Flux: %d", one, two))
                .subscribe(elements::add);

        Assertions.assertThat(elements).containsExactly(
                "First Flux: 0, Second Flux: 2",
                "First Flux: 1, Second Flux: 4",
                "First Flux: 2, Second Flux: 6",
                "First Flux: 3, Second Flux: 8");

        this.printElement();
    }

    /**
     * Hot Stream: stream từ những nguồn không biết trc length đầu vào, như web requets, files...
     */

    @Test
    public void hotStream() {
        ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
            while (true) {
                fluxSink.next(System.currentTimeMillis());
            }
        })
                .sample(Duration.ofSeconds(2)) // pause 2 second before send to consumers.
                .publish();

        publish.log().subscribe(elements::add);
        printElement();

    }

    @Test
    public void travelMono() {
        Mono<String> justMono = Mono.just("foo");
    }

    /**
     * concurrency
     */
    @Test
    public void concurrency() throws InterruptedException {
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .subscribeOn(Schedulers.parallel())
                .subscribe(elements::add);
        Thread.sleep(50000);
        System.out.println(elements.size());
    }


    private void printElement() {
        if (this.elements.size() < 1) {
            return;
        }
        this.elements.stream().forEach(System.out::println);
    }
}
