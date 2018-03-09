package com.hklh8.repository.support;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.spi.MetadataBuildingContext;

/**
 * Hibernate中实体映射时的命名策略
 * Created by GouBo on 2018/1/2.
 */
public class DefaultImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    private static final long serialVersionUID = 769122522217805485L;

    @Override
    protected Identifier toIdentifier(String stringForm, MetadataBuildingContext buildingContext) {
        return super.toIdentifier("fyx_" + stringForm.toLowerCase(), buildingContext);
    }

}
