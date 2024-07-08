package com.example.repository;

import com.example.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findAllByPedidoId(Long id);
}