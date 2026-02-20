package br.com.senior.prompthub.domain.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PasswordGeneratorService {
    
    public String generateTemporaryPassword() {
        // Senha forte aleatória: 12 caracteres
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "@#$%&*!";
        
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        // Garante pelo menos 1 de cada tipo
        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));
        
        // Completa com caracteres aleatórios
        String all = upper + lower + digits + special;
        for (int i = 4; i < 12; i++) {
            password.append(all.charAt(random.nextInt(all.length())));
        }
        
        // Embaralha
        return shuffleString(password.toString());
    }
    
    private String shuffleString(String str) {
        List<Character> chars = str.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toList());
        Collections.shuffle(chars);
        return chars.stream()
            .map(String::valueOf)
            .collect(Collectors.joining());
    }
}
