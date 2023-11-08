package com.yachtrent.main.techniacal.mapper;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public interface Mapper<D, E> {

    D toDto(E entity);

    E toEntity(D dto);

    default List<D> toDtoList(List<E> entityList) {
        return entityList.stream().map(this::toDto).toList();
    }

    default List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).toList();
    }

    default Set<D> toDtoSet(Set<E> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toSet());
    }

    default Set<E> toEntitySet(Set<D> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
