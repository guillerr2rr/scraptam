package com.example.repository;

import com.example.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByUserUsername(String username);
    Optional<Pedido> findByIdAndUserUsername(Long id, String username);
}