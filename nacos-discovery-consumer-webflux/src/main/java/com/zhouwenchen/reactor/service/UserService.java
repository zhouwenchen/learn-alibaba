package com.zhouwenchen.reactor.service;

import com.zhouwenchen.reactor.bean.User;
import com.zhouwenchen.reactor.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 */
@Service
public class UserService {

    private final Map<String, User> data = new ConcurrentHashMap<String, User>();

    public UserService(){
        User.UserBuilder user1 = User.builder().id("1").userName("zwc").password("123");
        data.put("1", user1.build());

        User.UserBuilder user2 = User.builder().id("2").userName("zwc").password("123");
        data.put("2", user2.build());
    }

    public Flux<User> list() {


        return Flux.fromIterable(this.data.values());
    }

    Flux<User> getById(final Flux<String> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(this.data.get(id)));
    }

    public Mono<User> getById(final String id) {
        return Mono.justOrEmpty(this.data.get(id))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()));
    }

    public Mono<User> createOrUpdate(final User user) {
        this.data.put(user.getId(), user);
        return Mono.just(user);
    }

    public Mono<User> delete(final String id) {
        return Mono.justOrEmpty(this.data.remove(id));
    }

}
