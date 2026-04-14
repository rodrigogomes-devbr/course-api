package br.com.rodrigo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    public String name;

    @Column(unique = true)
    public String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

    public String role;
}