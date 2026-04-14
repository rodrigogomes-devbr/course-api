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

        // Só cria se ainda não existir (evita erro ao reiniciar)
        if (User.count("email", "admin") == 0) {
            User admin = new User();
            admin.name = "admin";
            admin.email = "admin";      // o script testa com email="admin"
            admin.password = "admin";   // o script testa com password="admin"
            admin.role = "ADMIN";
            admin.persist();
            System.out.println("🔥 ADMIN CRIADO!");
        } else {
            System.out.println("🔥 ADMIN JÁ EXISTE, PULANDO...");
        }
    }
}