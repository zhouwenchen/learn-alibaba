package com.zhouwenchen.reactor.controller;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class StepVerifierController {
    public <T> Flux<T> appendBoomError(Flux<T> source) {
        return source.concatWith(Mono.error(new IllegalArgumentException("boom")));
    }

    @Test
    public void testAppendBoomError() {
        Flux<String> source = Flux.just("foo", "bar");

        StepVerifier.create(
                appendBoomError(source))
                .expectNext("foo")
                .expectNext("bar")
                .expectErrorMessage("boom")
                .verify();
    }
}
