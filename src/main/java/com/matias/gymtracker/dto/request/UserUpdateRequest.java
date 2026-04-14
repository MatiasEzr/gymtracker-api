package com.matias.gymtracker.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private String name;

    @Email
    private String email;

    private String password;
}
