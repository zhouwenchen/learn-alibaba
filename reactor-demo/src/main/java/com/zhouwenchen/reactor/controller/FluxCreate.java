package com.zhouwenchen.reactor.controller;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import reactor.core.publisher.Flux;

import java.util.Random;

public class FluxCreate {

    /**
     * 测试未通过
     */
    public static void test1(){
        Flux<Object> create = Flux.create(emitter -> {
            Random random = new Random();
            for (int i = 0; i <= 10; i++) {
                emitter.next(random.nextDouble());
            }
            int rand = random.nextInt(2);
            if (rand < 1) {
                emitter.complete();
            } else {
                emitter.error(new RuntimeException(
                        "Bad luck, you had one chance out of 2 to complete the Flux"
                ));
            }
        });
        create.subscribe(System.out::println);
    }

    public static void main(String[] args) {
        test1();
    }
}
