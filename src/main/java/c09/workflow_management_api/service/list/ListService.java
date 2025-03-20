package c09.workflow_management_api.service.list;

import c09.workflow_management_api.model.List;

import java.util.Optional;

public class ListService implements IListService {
    @Override
    public java.util.List<List> findAll() {
        return java.util.List.of();
    }

    @Override
    public Optional<List> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(List list) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
