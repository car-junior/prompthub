package br.com.senior.prompthub.core.service.modelmapper;

import br.com.senior.prompthub.core.dto.PageResult;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public abstract class AbstractModelMapperService {
    private final ModelMapper modelMapper;

    public <T> List<T> toList(Class<T> clazz, List<?> items) {

        return items.stream()
                .map(item -> modelMapper.map(item, clazz))
                .toList();
    }

    public <T> Set<T> toSet(Class<T> clazz, Set<?> items) {
        return items.stream()
                .map(item -> modelMapper.map(item, clazz))
                .collect(Collectors.toSet());
    }

    public <T> T toObject(Class<T> clazz, Object item) {
        if (item == null) return null;
        return modelMapper.map(item, clazz);
    }

    public void updateFromDto(Object dto, Object object) {
        if (dto != null && object != null) {
            modelMapper.map(dto, object);
        }
    }

    public <T> PageResult<T> toPage(Class<T> clazz, Page<?> page) {
        if (page == null) return new PageResult<>();
        return new PageResult<>(
                page.getTotalPages(),
                page.getTotalElements(),
                toList(clazz, page.getContent())
        );
    }

}
