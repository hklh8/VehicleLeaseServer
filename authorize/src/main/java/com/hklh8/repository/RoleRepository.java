package com.hklh8.repository;

import com.hklh8.domain.Role;
import org.springframework.stereotype.Repository;

/**
 * Created by GouBo on 2018/1/2.
 */
@Repository
public interface RoleRepository extends DefaultRepository<Role> {
    Role findByName(String name);
}
