package com.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate;

    @ManyToOne
    private UsuarioEntity user;

    public Pedido() {
    }

    public Pedido(LocalDateTime creationDate, UsuarioEntity user) {
        this.creationDate = creationDate;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public UsuarioEntity getUser() {
        return user;
    }

    public void setUser(UsuarioEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id) && Objects.equals(creationDate, pedido.creationDate) && Objects.equals(user, pedido.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, user);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                '}';
    }
}
