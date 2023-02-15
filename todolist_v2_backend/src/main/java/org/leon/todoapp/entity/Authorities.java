package org.leon.todoapp.entity;


import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String authority;

    public Authorities() {
    }

    public Authorities(Long id, @NonNull String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(@NonNull String authority) {
        this.authority = authority;
    }
}
