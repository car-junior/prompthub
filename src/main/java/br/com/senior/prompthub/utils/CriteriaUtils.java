package br.com.senior.prompthub.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.senior.prompthub.utils.GeneralUtils.unaccent;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CriteriaUtils {
    private static final String UNACCENT = "UNACCENT";

    public static <T> Expression<String> lowerAndUnaccented(CriteriaBuilder builder, Path<T> pathAttribute) {
        return builder.lower(builder.function(UNACCENT, String.class, pathAttribute));
    }

    public static String formatQueryToLike(String value) {
        return "%".concat(unaccent(value).trim().toLowerCase()).concat("%");
    }
}
