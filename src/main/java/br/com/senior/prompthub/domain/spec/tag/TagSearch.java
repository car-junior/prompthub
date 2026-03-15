package br.com.senior.prompthub.domain.spec.tag;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagSearch {
    private String query;
}
