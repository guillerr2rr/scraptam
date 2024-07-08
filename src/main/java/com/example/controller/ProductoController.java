package com.example.controller;

import com.example.model.Producto;
import com.example.repository.ProductoRepository;
import com.example.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productRepo;

    @Autowired
    private StorageService storageService;

    // http://localhost:8080/productos
    @GetMapping("/productos")
    public String findAll(Model model){
        model.addAttribute("productos", productRepo.findAll());
        return "listaProducto";
    }
    @PostMapping("/productos")
    public String submit(
            @ModelAttribute Producto product,
            @RequestParam("file") MultipartFile file
    ){
        if(!file.isEmpty()){
            String imagen = storageService.store(file);
            String imagenUrl = MvcUriComponentsBuilder.
                    fromMethodName(
                            FileController.class, "serveFile", imagen
                    ).build().toUriString();
            product.setImagen(imagenUrl);
        }
        productRepo.save(product);
        return "redirect:/productos";
    }

    @GetMapping("/productos/new")
    public String create(Model model){
        model.addAttribute("producto", new Producto());
        return "formularioProducto";
    }


}
