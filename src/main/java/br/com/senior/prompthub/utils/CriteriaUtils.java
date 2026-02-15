package br.com.senior.prompthub.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.senior.prompthub.utils.GeneralUtils.unaccent;

/**
 * Utilitários para construção de queries JPA Criteria API com suporte a buscas
 * case-insensitive e sem acentuação.
 *
 * <p>Esta classe fornece métodos auxiliares para normalizar campos e valores em queries,
 * permitindo buscas mais flexíveis que ignoram diferenças de maiúsculas/minúsculas e acentos.</p>
 *
 * <h3>Exemplo de uso:</h3>
 * <pre>
 * {@code
 * @Override
 * public Specification<User> buildSpecification(UserSearch search) {
 *     return (root, query, cb) -> {
 *         if (search.getUsername() != null) {
 *             return cb.like(
 *                 toNormalize(cb, root.get(User_.USERNAME)),
 *                 toLikeValue(search.getUsername())
 *             );
 *         }
 *         return null;
 *     };
 * }
 * }
 * </pre>
 *
 * @see GeneralUtils#unaccent(String)
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CriteriaUtils {
    private static final String UNACCENT = "UNACCENT";

    /**
     * Normaliza um campo do banco de dados aplicando lowercase e remoção de acentos.
     *
     * <p>Este método utiliza a função UNACCENT do PostgreSQL para remover acentuação
     * e aplica LOWER para converter para minúsculas, permitindo buscas case-insensitive
     * e sem distinção de acentos.</p>
     *
     * <p><strong>Nota:</strong> Requer que a extensão UNACCENT esteja instalada no PostgreSQL.</p>
     *
     * @param <T>           Tipo do atributo
     * @param builder       CriteriaBuilder para construção da query
     * @param pathAttribute Path do atributo a ser normalizado
     * @return Expression com lowercase e sem acentos aplicados
     *
     * @see #toLikeValue(String)
     */
    public static <T> Expression<String> toNormalize(CriteriaBuilder builder, Path<T> pathAttribute) {
        return builder.lower(builder.function(UNACCENT, String.class, pathAttribute));
    }

    /**
     * Formata um valor de busca para uso em queries LIKE, adicionando wildcards (%).
     *
     * <p>Este método prepara o valor de busca aplicando:</p>
     * <ul>
     *   <li>Remoção de acentos</li>
     *   <li>Trim (remoção de espaços)</li>
     *   <li>Conversão para lowercase</li>
     *   <li>Adição de % no início e fim (busca parcial)</li>
     * </ul>
     *
     * <p><strong>Exemplo:</strong></p>
     * <pre>
     * toLikeValue("João")  → "%joao%"
     * toLikeValue(" Maria ") → "%maria%"
     * </pre>
     *
     * @param value Valor a ser formatado para busca LIKE
     * @return Valor normalizado com wildcards (%) no início e fim
     *
     * @see #toNormalize(CriteriaBuilder, Path)
     * @see GeneralUtils#unaccent(String)
     */
    public static String toLikeValue(String value) {
        return "%".concat(unaccent(value).trim().toLowerCase()).concat("%");
    }
}
