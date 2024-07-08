package com.example;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Producto;
import com.example.model.UsuarioEntity;
import com.example.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.model.UserAuthority;
import com.example.repository.UsuarioEntityRepository;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner initData(
            UsuarioEntityRepository userRepo,
            ProductoRepository productRepo,
            PasswordEncoder encoder
    ){
        return args -> {
            UsuarioEntity user1 = new UsuarioEntity(
                    "user1", encoder.encode("1234"), "user1@localhost", new ArrayList<>(List.of(UserAuthority.ADMIN, UserAuthority.USER))
            );
            UsuarioEntity user2 = new UsuarioEntity(
                    "user2", encoder.encode("1234"), "user2@localhost", new ArrayList<>(List.of(UserAuthority.USER))
            );
            userRepo.save(user1);
            userRepo.save(user2);

            List<Producto> productos = List.of(
                    new Producto("Prod1", 3.99, "https://dummyimage.com/200x200/fff/aaa"),
                    new Producto("Prod2", 4.99, "https://dummyimage.com/200x200/fff/aaa"),
                    new Producto("Prod3", 7.99, "https://dummyimage.com/200x200/fff/aaa"),
                    new Producto("Prod4", 8.99, "https://dummyimage.com/200x200/fff/aaa"),
                    new Producto("Prod5", 9.99, "https://dummyimage.com/200x200/fff/aaa"),
                    new Producto("Prod6", 1.99, "https://dummyimage.com/200x200/fff/aaa")
            );

            productRepo.saveAll(productos);

        };


    }

}
