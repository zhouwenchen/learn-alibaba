package com.zhouwenchen.reactor.controller;

import reactor.core.publisher.Flux;

public class FluxHandle {

    public static void test1(){
        Flux<Integer> just = Flux.just(-1, 30, 20, 10, 43)
                // 处理满足条件的数据
                .handle((i,sink)->{
                   if(i% 10 == 0){
                       sink.next(i);
                   }
                });

        just.subscribe(System.out::println);
    }

    public static void main(String[] args) {
        test1();
    }
}
