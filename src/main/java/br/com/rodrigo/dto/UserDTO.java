package br.com.rodrigo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank(message = "Name is required")
    public String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    public String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must have at least 8 characters")
    public String password;
}
