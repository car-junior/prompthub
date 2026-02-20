package br.com.senior.prompthub.config.security;

import br.com.senior.prompthub.utils.GeneralUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        var responseHandler = AuthResponseHandler.builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .message("Você não tem autorização para esse recurso.")
                .build();
        response.getWriter().write(GeneralUtils.objectMapperForJson().writeValueAsString(responseHandler));
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        var responseHandler = AuthResponseHandler.builder()
                .status(HttpServletResponse.SC_FORBIDDEN)
                .message("Você não tem autorização para esse recurso.")
                .build();
        response.getWriter().write(GeneralUtils.objectMapperForJson().writeValueAsString(responseHandler));
    }
}
