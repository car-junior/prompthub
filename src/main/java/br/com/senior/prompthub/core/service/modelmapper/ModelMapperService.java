package br.com.senior.prompthub.core.service.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class ModelMapperService<T> extends AbstractModelMapperService {
    private final ModelMapper modelMapper;

    public ModelMapperService(ModelMapper modelMapper) {
        super(modelMapper);
        this.modelMapper = modelMapper;
    }

    /**
     * Atualiza entidade a partir de DTO, respeitando as annotations @UpdateIgnore
     */
    public void updateEntity(T entityUpdate, T entity) {
        if (entityUpdate == null || entity == null) return;

        // 1. Backup dos campos anotados com @UpdateIgnore
        Map<String, Object> backupValues = new HashMap<>();
        findAndBackupIgnoredFields(entity, backupValues, "", true);

        // 2. Aplica mapeamento normal (já ignora nulos por configuração)
        modelMapper.map(entityUpdate, entity);

        // 3. Restaura os valores dos campos ignorados
        restoreBackupValues(entity, backupValues);
    }

    /**
     * Recursivamente encontra e faz backup dos campos com @UpdateIgnore
     */
    private void findAndBackupIgnoredFields(Object obj, Map<String, Object> backup, String currentPath, boolean isRoot) {
        if (obj == null) return;

        for (Field field : getAllFields(obj.getClass())) {
            try {
                field.setAccessible(true);
                String fieldPath = currentPath.isEmpty()
                        ? field.getName()
                        : currentPath + "." + field.getName();

                // Verifica se o campo deve ser ignorado
                if (isRoot && field.isAnnotationPresent(NoUpdateMapping.class)) {
                    Object value = field.get(obj);
                    backup.put(fieldPath, value);
                }

                // Recursão para campos aninhados (objetos complexos)

                var attribute = field.get(obj);
                if (attribute instanceof Collection<?>) {
                    ((Collection<?>) attribute).clear();
                }

                Object fieldValue = field.get(obj);
                if (isComplexObject(fieldValue)) {
                    findAndBackupIgnoredFields(fieldValue, backup, fieldPath, false);
                }

            } catch (Exception e) {
                // Ignora campos inacessíveis
            }
        }
    }

    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> allFields = new ArrayList<>();
        Class<?> currentClass = clazz;

        while (currentClass != null && currentClass != Object.class) {
            allFields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
            currentClass = currentClass.getSuperclass();
        }

        return allFields;
    }

    /**
     * Restaura os valores dos campos ignorados
     */
    private void restoreBackupValues(T entity, Map<String, Object> backupValues) {
        backupValues.forEach((fieldPath, originalValue) -> {
            try {
                setNestedFieldValue(entity, fieldPath, originalValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
                // Ignora erro na restauração
            }
        });
    }

    private boolean isComplexObject(Object obj) {
        if (obj == null) return false;

        Class<?> clazz = obj.getClass();

        // 1. Verifica se é um tipo que definitivamente não é complexo
        if (clazz.isPrimitive() || clazz.isEnum() || clazz.isArray() || clazz == String.class) {
            return false;
        }

        // 2. VERIFICAÇÃO PRINCIPAL: pertence aos meus pacotes?
        return isFromMyPackages(clazz);
    }

    private boolean isFromMyPackages(Class<?> clazz) {
        String packageName = clazz.getPackageName();

        // Lista de pacotes do SEU projeto que podem ser processados recursivamente
        return packageName.startsWith("br.com.senior.prompthub.");
    }

    private Object getNestedFieldValue(Object obj, String propertyPath) throws Exception {
        String[] parts = propertyPath.split("\\.");
        Object current = obj;
        for (String part : parts) {
            Field field = current.getClass().getDeclaredField(part);
            field.setAccessible(true);
            current = field.get(current);
            if (current == null) return null;
        }
        return current;
    }

    private void setNestedFieldValue(Object obj, String propertyPath, Object value) throws Exception {
        String[] parts = propertyPath.split("\\.");
        Object current = obj;
        for (int i = 0; i < parts.length - 1; i++) {
            Field field = current.getClass().getDeclaredField(parts[i]);
            field.setAccessible(true);
            current = field.get(current);
            if (current == null) return;
        }

        var field = getAllFields(current.getClass()).stream()
                .filter(it -> Objects.equals(parts[parts.length - 1], it.getName()))
                .findFirst()
                .orElse(null);

        if (field != null) {
            field.setAccessible(true);
            field.set(current, value);
        }

    }

    // Método para conversões normais
    public <T> T convert(Object source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}