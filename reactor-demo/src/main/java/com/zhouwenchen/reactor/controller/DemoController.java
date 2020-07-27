/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.zhouwenchen.reactor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * @author lengleng
 * @date 2019-03-01
 */
@RestController
public class DemoController {
    @Autowired
    private WebClient.Builder webclientBuilder;

    @GetMapping("/test")
    public Mono<String> test(String name) {
       /* return webclientBuilder.build()
                .get()
                .uri("http://nacos-discovery-provider/demo?name=" + name)
                .retrieve()
                .bodyToMono(String.class);*/

        return Mono.just("webfulx success");
    }

    @GetMapping("test1")
    public Mono<String> findById(){
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
//        Flux.interval(Duration.of(3, ChronoUnit.SECONDS)).subscribe(System.out::println);

        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::println);

        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);


        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);
        return null;
    }

    @GetMapping("/test2")
    public void test2(){
        Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);

        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
//        Flux.intervalMillis(100).bufferMillis(1001).take(2).toStream().forEach(System.out::println);
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);

        // 过滤满足条件的数据
        Flux.range(1,10).filter(i->i % 2 == 0).subscribe(System.out::println);
        Flux.range(1,10).window(20).subscribe(System.out::println);
//        Flux.intervalMillis(100).windowMillis(1001).take(2).toStream().forEach(System.out::println);

        Flux.just("a","b").zipWith(Flux.just("c","d")).subscribe(System.out::println);
        Flux.just("a","b").zipWith(Flux.just("c","d"),(s1,s2) -> String.format("%s-%s", s1,s2)).subscribe(System.out::println);

        Flux.range(1, 100).take(10).subscribe(System.out::println);
        Flux.range(1, 100).takeLast(10).subscribe(System.out::println);

        Flux.range(1, 100).takeWhile(integer -> integer < 10).subscribe(System.out::println);
        Flux.range(1, 100).takeUntil(integer -> integer % 10 == 0).subscribe(System.out::println);

//        Flux.merge(Flux.intervalMillis(0, 100).take(5), Flux.intervalMillis(50, 100).take(5))
//                .toStream()
//                .forEach(System.out::println);
//        Flux.mergeSequential(Flux.intervalMillis(0, 100).take(5), Flux.intervalMillis(50, 100).take(5))
//                .toStream()
//                .forEach(System.out::println);

//        Flux.just(5, 10)
//                .flatMap(x -> Flux.intervalMillis(x * 10, 100).take(x))
//                .toStream()
//                .forEach(System.out::println);
    }

    @GetMapping("/test3")
    public void test3(){
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                // 错误值默认为 0
                .onErrorReturn(0)
                .subscribe(System.out::println, System.err::println);

        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalArgumentException()))
                .onErrorResume(e -> {
                    if (e instanceof IllegalStateException) {
                        return Mono.just(0);
                    } else if (e instanceof IllegalArgumentException) {
                        return Mono.just(-1);
                    }
                    return Mono.empty();
                })
                .subscribe(System.out::println);

        // 使用重试机制来实现操作
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .retry(1)
                .subscribe(System.out::println);
    }

    @GetMapping("/schedulerstest")
    public void schedulerstest(){
        Flux.create(sink -> {
            sink.next(Thread.currentThread().getName());
            sink.complete();
        })
                .publishOn(Schedulers.single())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .publishOn(Schedulers.elastic())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .subscribeOn(Schedulers.parallel())
                .toStream()
                .forEach(System.out::println);
    }



}
