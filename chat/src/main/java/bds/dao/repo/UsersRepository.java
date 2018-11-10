package bds.dao.repo;

import bds.dao.UsersRecord;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UsersRecord, Integer> {
}
