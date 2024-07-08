package com.example.controller;

import com.example.model.Pedido;
import com.example.repository.PedidoRepository;
import com.example.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepo;
    @Autowired
    private ProductoRepository productoRepo;

    @GetMapping("/pedidos")
    public String findAll(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Pedido> pedidos = pedidoRepo.findAllByUserUsername(username);
        model.addAttribute("pedidos", pedidos);
        return "listaPedido";
    }

    @GetMapping("/pedidos/{id}")
    public String findById(Model model, @PathVariable Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pedido pedido = pedidoRepo.findByIdAndUserUsername(id, username).orElseThrow();
        model.addAttribute("pedido", pedido);
        model.addAttribute("productos", productoRepo.findAllByPedidoId(id));
        return "pedido";
    }


}
