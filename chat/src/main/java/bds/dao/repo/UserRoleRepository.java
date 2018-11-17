package bds.dao.repo;

import bds.dao.UserRoleRecord;
import org.springframework.data.repository.CrudRepository;

// CRUD по таблице БД Roles
public interface UserRoleRepository extends CrudRepository<UserRoleRecord, Integer> {
}
