package br.com.senior.prompthub.config.security;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseHandler {
    private int status;
    private String message;
}
