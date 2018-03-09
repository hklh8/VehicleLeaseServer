package com.hklh8.repository.spec;

import com.hklh8.domain.BaseUser;
import com.hklh8.dto.UserCondition;
import com.hklh8.repository.support.DefaultSpecification;
import com.hklh8.repository.support.QueryWraper;

/**
 * Created by GouBo on 2018/1/2.
 */
public class UserSpec extends DefaultSpecification<BaseUser, UserCondition> {

    public UserSpec(UserCondition condition) {
        super(condition);
    }

    @Override
    protected void addCondition(QueryWraper<BaseUser> queryWraper) {
        addLikeCondition(queryWraper, "username");
    }

}
