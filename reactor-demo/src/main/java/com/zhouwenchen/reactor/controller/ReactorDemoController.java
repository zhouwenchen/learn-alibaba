package com.zhouwenchen.reactor.controller;

import com.zhouwenchen.reactor.subscribe.SampleSubscriber;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author admin
 */
@RestController
public class ReactorDemoController {

    public static void test1(){
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);

        Mono<String> empty = Mono.empty();
        Mono<String> fo = Mono.just("fo");
        Flux<Integer> range = Flux.range(1, 1000);
        Disposable subscribe = range.subscribe(System.out::println);
//        range.subscribe(System.out::println);
        subscribe.dispose();
    }


    public static void test2(){
        Flux<Integer> map = Flux.range(1, 4).map(i -> {
            if (i <= 3) {
                return i;
            }
//            throw new RuntimeException("Got to 4");
            return i;
        });

        // 处理元素，一个正常，一个错误的(错误和完成信号都是终止信号，并且二者只会出现其中之一)
        Disposable done = map.subscribe(System.out::println,
                System.err::println,
                () -> System.out.println("DONE"));

    }

    public static void test3(){
        SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
        Flux<Integer> map = Flux.range(1, 4).map(i -> {
            if (i <= 3) {
                return i;
            }
//            throw new RuntimeException("Got to 4");
            return i;
        });

        // 处理元素，一个正常，一个错误的(错误和完成信号都是终止信号，并且二者只会出现其中之一)
        Disposable done = map.subscribe(System.out::println,
                System.err::println,
                () -> System.out.println("DONE"),
                s ->ss.request(10) );
        map.subscribe(ss);
    }

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }
}
