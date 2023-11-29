package com.scopie.authservice.config;

import com.scopie.authservice.dto.LoginDTO;
import com.scopie.authservice.entity.User;
import java.util.Map;

public interface JwtGenerator {
    Map<String, String> generate(LoginDTO user);
}
