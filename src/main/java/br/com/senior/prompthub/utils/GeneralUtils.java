package br.com.senior.prompthub.utils;

import br.com.senior.prompthub.core.dto.PageParams;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.text.Normalizer;
import java.util.Collection;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralUtils {

    public static boolean isPresent(Object value) {
        var exists = Optional.ofNullable(value).isPresent();
        if (value instanceof String) {
            return !((String) value).isEmpty();
        }
        return exists;
    }

    public static <T extends Collection<?>> boolean isNotEmpty(T list) {
        return isPresent(list) && !list.isEmpty();
    }

    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public static PageRequest getPageRequest(PageParams pageParams) {
        return PageRequest.of(pageParams.getPage(), pageParams.getItemsPerPage(), Sort.by(pageParams.getSort(), pageParams.getSortName()));
    }

    public static ObjectMapper objectMapperForJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
