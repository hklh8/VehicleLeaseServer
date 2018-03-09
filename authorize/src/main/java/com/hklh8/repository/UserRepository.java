package com.hklh8.repository;

import com.hklh8.domain.BaseUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by GouBo on 2018/1/2.
 */
@Repository
public interface UserRepository extends DefaultRepository<BaseUser> {
    BaseUser findUserByUsernameAndActive(String username, boolean active);

    @Query(value = "select * from user where id in (select user_id from role_user where role_id = :roleId) /*#pageable*/",
            countQuery = "select count(*) from user where id in (select user_id from role_user where role_id = :roleId)",
            nativeQuery = true)
    Page<BaseUser> findByRoleId(@Param("roleId") Long roleId, Pageable pageable);
}
