package cn.xjjdog.bcmall.utils.db;

import cn.xjjdog.bcmall.utils.utils.Snowflake;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public class DistributedId implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object obj) throws HibernateException {
        if (obj == null) throw new HibernateException(new NullPointerException()) ;

        if ((((AbstractEntity) obj).getId()) == null) {
            return String.valueOf(Snowflake.createId());
        } else {
            return ((AbstractEntity) obj).getId();
        }
    }
}
