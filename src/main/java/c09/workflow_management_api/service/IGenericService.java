package c09.workflow_management_api.service;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T> {
    List<T> findAll();

    Optional<T> findById(Long id);

    void save(T t);

    void deleteById(Long id);
}
