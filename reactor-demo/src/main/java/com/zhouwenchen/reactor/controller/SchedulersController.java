package com.zhouwenchen.reactor.controller;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class SchedulersController {

    // TODO
    public static void test1(){
        Flux<Long> test = Flux.interval(Duration.ofMinutes(300), Schedulers.newSingle("test"));
        test.subscribe(System.out::println);
    }

    public static void test2(){
        Flux<Integer> integerFlux = Flux.range(1, 100)
                .publishOn(Schedulers.newParallel("myParallel"))
                .log()
                ;
        integerFlux.subscribe(System.out::println);
    }

    public void test3(){
        Flux<String> recovered = Flux.just(10)
                .map(this::doSomethingDangerous)
                .onErrorReturn("RECOVERED");
        recovered.subscribe(System.out::println,System.err::println);
    }

    private <Void> String doSomethingDangerous(Integer integer) {
        System.out.println("abad");
        throw new RuntimeException("出现异常西悉尼");
//        return null;
    }

    public void test4(){
        Flux<?> flux = Flux.just("key1", "key2")
                .flatMap(k -> callExternalService(k))
                .onErrorResume(e -> getFromCache(e));

        flux.subscribe(System.out::println,System.err::println);
    }

    private Publisher getFromCache(Throwable e)  {
       try {
           System.out.println(e);
           throw new Throwable(e);
       }catch (Throwable a){
           a.printStackTrace();
       }
       return null;
    }

    private Publisher<?> callExternalService(String k) {
        Flux<String> just = Flux.just(k);
        if("key2".equals(k)){
            int i = 1 / 0;
        }
        return just;
    }


    public static void main(String[] args) {
        new SchedulersController().test4();
    }
}
