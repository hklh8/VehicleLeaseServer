package com.hklh8.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * 抽象数据初始化器，所有的数据初始化器应该继承此类
 * Created by GouBo on 2018/1/2.
 */
public abstract class AbstractDataInitializer implements DataInitializer {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public void init() throws Exception {
        if (isNeedInit()) {
            logger.info("使用" + getClass().getSimpleName() + "初始化数据");
            doInit();
            logger.info("使用" + getClass().getSimpleName() + "初始化数据完毕");
        }
    }

    /**
     * 实际的数据初始化逻辑
     */
    protected abstract void doInit() throws Exception;

    /**
     * 是否执行数据初始化行为
     */
    protected abstract boolean isNeedInit();
}
