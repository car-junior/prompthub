package br.com.senior.prompthub.core.dto;

import lombok.*;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageParams {
    private Integer page;
    private Integer itemsPerPage;
    private Sort.Direction sort;
    private String sortName;

    public Integer getPage() {
        return Objects.requireNonNullElse(page, 0);
    }

    public Integer getItemsPerPage() {
        return Objects.requireNonNullElse(itemsPerPage, 10);
    }

    public Sort.Direction getSort() {
        return Objects.requireNonNullElse(sort, Sort.Direction.DESC);
    }

    public String getSortName() {
        return Objects.requireNonNullElse(sortName, "id");
    }
}
