package com.app.configuration;

import com.app.dto.ServiceResponse;
import com.app.dto.UserRegister;
import com.app.services.CategoryService;
import com.app.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class StartUpRunner implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {
        var result = userService.registerUser(UserRegister.builder()
                .firstName("Admin")
                .lastName("Admin")
                .password("admin")
                .username("admin@gmail.com")
                .roles("ADMIN")
                .build());
        log.error("Admin user created: {}", ((ServiceResponse<?>)result.getBody()).isSuccess());

        categoryService.createDefaultCategories();
    }
}
