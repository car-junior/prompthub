package br.com.senior.prompthub.core.service.modelmapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para marcar campos que devem ser preservados durante operações de atualização via ModelMapper.
 * 
 * <p>Quando aplicada a um campo de uma entidade, esta anotação impede que o valor do campo seja
 * sobrescrito durante o mapeamento automático em operações de UPDATE (PUT). O campo mantém seu
 * valor original mesmo que um novo valor seja fornecido no DTO de atualização.</p>
 * 
 * <p><strong>Importante:</strong> Esta anotação afeta apenas o mapeamento automático via ModelMapper.
 * O campo ainda pode ser atualizado programaticamente através de setters ou outros meios.</p>
 * 
 * <h3>Casos de uso comuns:</h3>
 * <ul>
 *   <li>IDs de entidades (não devem ser alterados após criação)</li>
 *   <li>Timestamps de auditoria (createdAt, createdBy)</li>
 *   <li>Campos calculados ou gerenciados pelo sistema</li>
 *   <li>Relacionamentos que não devem ser modificados via API de atualização</li>
 * </ul>
 * 
 * <h3>Exemplo de uso:</h3>
 * <pre>
 * {@code
 * @Entity
 * public class User extends Auditable {
 *     @Id
 *     @NoUpdateMapping
 *     @GeneratedValue(strategy = GenerationType.SEQUENCE)
 *     private Long id;
 *     
 *     private String username;
 *     private String email;
 * }
 * }
 * </pre>
 * 
 * <p>No exemplo acima, ao atualizar um usuário via PUT, o campo {@code id} será preservado
 * mesmo que o DTO contenha um valor diferente.</p>
 * 
 * @see ModelMapperService#updateEntity(Object, Object)
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoUpdateMapping {
    /**
     * Indica se o campo deve ser ignorado durante o mapeamento de atualização.
     * 
     * @return {@code true} para ignorar o campo (padrão), {@code false} para incluir no mapeamento
     */
    boolean skip() default true;
}