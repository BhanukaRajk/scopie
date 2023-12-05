package com.scopie.authservice.config;

import com.scopie.authservice.dto.LoginDTO;
import java.util.Map;

public interface JwtGenerator {
    Map<String, String> generate(LoginDTO user);
}
