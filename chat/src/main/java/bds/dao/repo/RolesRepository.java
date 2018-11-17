package bds.dao.repo;


import bds.dao.RolesRecord;
import org.springframework.data.repository.CrudRepository;

// CRUD по таблице БД Roles
public interface RolesRepository extends CrudRepository<RolesRecord, Integer> {
}
