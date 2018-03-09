package com.hklh8.repository;

import com.hklh8.domain.Resource;
import org.springframework.stereotype.Repository;

/**
 * Created by GouBo on 2018/1/2.
 */
@Repository
public interface ResourceRepository extends DefaultRepository<Resource> {
    Resource findByName(String name);
}
