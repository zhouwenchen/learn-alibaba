package com.zhouwenchen.reactor.bean;

import lombok.*;

/**
 *  用户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    private String id;
    private String userName;
    private String password;

    private String name;
    private String email;
}
