package bds.dao.repo;

import bds.dao.UserRoleRecord;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRoleRecord, Integer> {
}
