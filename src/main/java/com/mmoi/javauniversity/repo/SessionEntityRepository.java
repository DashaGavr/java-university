package com.mmoi.javauniversity.repo;

import com.mmoi.javauniversity.models.Session;
import com.mmoi.javauniversity.models.SessionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface SessionEntityRepository extends CrudRepository<Session, Long> {
}