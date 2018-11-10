package bds.dao.repo;

import bds.dao.MessagesRecord;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<MessagesRecord, Integer> {
}
