package bds.dao.repo;

import bds.dao.UserRoleRecord;
import bds.dao.UsersRecord;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRoleRecord, Integer> {
}
