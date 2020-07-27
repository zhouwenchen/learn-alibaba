package com.zhouwenchen.reactor.controller;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class FluxGenerate {

    public static void test1(){
        Flux<Object> generate = Flux.generate(() -> 0, (state, sink) -> {
            sink.next("3 X " + state + " = " + 3 * state);
            if (state == 10) {
                sink.complete();
            }
            return state + 1;
        });

        generate.subscribe(System.out::println);
    }

    public static void test2(){
        Flux<Object> generate = Flux.generate(AtomicLong::new, (state, sink) -> {
            long i = state.getAndIncrement();
            sink.next("3 X " + state + " = " + 3 * i);
            if (i == 10) {
                sink.complete();
            }
            return state;
            // 这个   state->System.out.println("state: " + state) 用于清理工作，比如说数据库连接池的关闭等
        }, state->System.out.println("state: " + state));

//        generate.subscribe();
        generate.subscribe(System.out::println);
    }

    public static void test3(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Flux.fromIterable(list).subscribe(System.out::println);
    }

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }
}
