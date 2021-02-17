package cn.xjjdog.bcmall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 在这里，开启了JPA的审计功能；开启后，将自动填充createdDate和modifyDate
 *
 * @author xjjdog
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
