package bds.dao.repo;

import bds.dao.UsersRecord;
import org.springframework.data.repository.CrudRepository;

// CRUD по таблице БД Users
public interface UsersRepository extends CrudRepository<UsersRecord, Integer> {


}
