package com.example.controller;

import com.example.model.Pedido;
import com.example.model.Producto;
import com.example.model.UsuarioEntity;
import com.example.repository.PedidoRepository;
import com.example.repository.ProductoRepository;
import com.example.repository.UsuarioEntityRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarritoController {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private ProductoRepository productRepo;

    @Autowired
    private UsuarioEntityRepository userRepo;

    @Autowired
    private HttpSession session;

    @GetMapping("/carrito")
    public String show(Model model){
        List<Long> productIds = Optional.ofNullable((List<Long>) session.getAttribute("carrito_productos"))
                .orElse(new ArrayList<>());
        model.addAttribute("productos", productRepo.findAllById(productIds));
        return "carrito";
    }

    @GetMapping("/carrito/add/{id}")
    public String add(@PathVariable Long id){
        List<Long> productIds = Optional.ofNullable((List<Long>) session.getAttribute("carrito_productos"))
                .orElse(new ArrayList<>());

        if(!productIds.contains(id)) productIds.add(id);
        session.setAttribute("carrito_productos", productIds);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/remove/{id}")
    public String remove(@PathVariable Long id){
        List<Long> productIds = Optional.ofNullable((List<Long>) session.getAttribute("carrito_productos"))
                .orElse(new ArrayList<>());

        productIds.removeIf(productId -> productId.equals(id));
        session.setAttribute("carrito_productos", productIds);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/checkout")
    public String checkout(){
        List<Long> productIds = Optional.ofNullable((List<Long>) session.getAttribute("carrito_productos"))
                .orElse(new ArrayList<>());
        List<Producto> productos = productRepo.findAllById(productIds);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity user = userRepo.findByUsername(username).orElseThrow();

        Pedido pedido = new Pedido(LocalDateTime.now(), user);
        pedidoRepo.save(pedido);

        productos.forEach(p -> p.setPedido(pedido));
        productRepo.saveAll(productos);

        session.removeAttribute("carrito_productos");
        return "redirect:/pedidos/" + pedido.getId();
    }

    @ModelAttribute("carrito_total")
    public Double calculateTotal(){
        List<Long> productIds = Optional.ofNullable((List<Long>) session.getAttribute("carrito_productos"))
                .orElse(new ArrayList<>());
        List<Producto> productos = productRepo.findAllById(productIds);
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }
}
