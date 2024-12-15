package com.hdsinh.cardealer.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class User_Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    // Constructor mặc định
    public User_Role() {
    }

    // Constructor đầy đủ
    public User_Role(Long id, Users users, Role role) {
        this.id = id;
        this.users = users;
        this.role = role;
    }

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
