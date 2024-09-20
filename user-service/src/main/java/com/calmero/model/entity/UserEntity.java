package com.calmero.model.entity;

import com.calmero.model.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@Table(name = "chat_users")
@NoArgsConstructor
public class UserEntity extends BaseEntity<Long> implements Serializable {

    @Column
    private String username;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private Role role;
}
