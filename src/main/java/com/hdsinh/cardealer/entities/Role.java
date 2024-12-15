package com.hdsinh.cardealer.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User_Role> roleUsers;

    // Constructor mặc định
    public Role() {}

    // Constructor đầy đủ
    public Role(Long id, String name, Set<User_Role> roleUsers) {
        this.id = id;
        this.name = name;
        this.roleUsers = roleUsers;
    }

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User_Role> getRoleUsers() {
        return roleUsers;
    }

    public void setRoleUsers(Set<User_Role> roleUsers) {
        this.roleUsers = roleUsers;
    }
}
