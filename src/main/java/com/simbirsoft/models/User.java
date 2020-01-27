package com.simbirsoft.models;

import com.simbirsoft.enumTypes.UserAccess;
import com.simbirsoft.enumTypes.UserTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserAccess access;

    @Enumerated(EnumType.STRING)
    private UserTypes type;
}
