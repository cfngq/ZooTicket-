package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String phone;
    private String password;
    private Long createUser;
    private LocalDateTime createTime;
    private Long updateUser;
    private LocalDateTime updateTime;


}
