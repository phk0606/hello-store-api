package com.hellostore.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long id;

    @Column(length = 50, unique = true)
    private String username;

    @JsonIgnore
    @Column(length = 100)
    private String password;

    @Column(length = 50)
    private String email;

    @JsonIgnore
    private boolean activated;

    @Column(length = 50)
    private String name;

    @Column(length = 11)
    private String phoneNumber;

    @Embedded
    private Address address;

    private Integer point;

    @ManyToMany
    @JoinColumn
    @JoinTable(name = "user_authority",
    joinColumns
            = {@JoinColumn(name = "user_no", referencedColumnName = "user_no")},
    inverseJoinColumns
            = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
