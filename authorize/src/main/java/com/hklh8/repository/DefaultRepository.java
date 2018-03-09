package com.hklh8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by GouBo on 2018/1/2.
 */
@NoRepositoryBean
public interface DefaultRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
