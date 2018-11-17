package bds.dao.repo;

import bds.dao.MessagesRecord;
import org.springframework.data.repository.CrudRepository;

// CRUD по таблице БД Messages
public interface MessagesRepository extends CrudRepository<MessagesRecord, Integer> {
}
