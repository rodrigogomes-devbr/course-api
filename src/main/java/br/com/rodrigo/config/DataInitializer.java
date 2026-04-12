package br.com.rodrigo.config;

import br.com.rodrigo.entity.User;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DataInitializer {

    @Transactional
    public void onStart(@Observes StartupEvent ev) {

        System.out.println("🔥 INIT EXECUTANDO...");

        User admin = new User();
        admin.name = "Admin";
        admin.email = "admin@admin.com";
        admin.password = "admin123";
        admin.role = "ADMIN";

        admin.persist();

        System.out.println("🔥 ADMIN CRIADO!");
    }
}