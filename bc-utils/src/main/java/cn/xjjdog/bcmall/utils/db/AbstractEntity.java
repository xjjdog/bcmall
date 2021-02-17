package cn.xjjdog.bcmall.utils.db;

import cn.xjjdog.bcmall.utils.BasicInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 *
 * <p>
 * 这里是JPA的基类，定义了一些必须的字段。为什么要这么做？因为重复书写这些字段，耗时耗力，还容易出错;
 * <p>
 * 我们省略Repository层，因为JPA的Repository，就是Repository和DAO融合的概念
 * </p>
 * <p>
 * 此类对应的是BasicInfo
 * @see BasicInfo
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}) //直接使用bean时，避免json序列号失败
public abstract class AbstractEntity implements Serializable {
    static final String ID_GEN = "cn.xjjdog.bcmall.utils.db.DistributedId";
    /**
     * 数据库行标示，自增
     */
    @Id
    @GenericGenerator(name = "IdGen", strategy = ID_GEN)
    @GeneratedValue(generator = "IdGen")
    private String id;
    /**
     * 创建时间
     */
    @CreatedDate
    private Date createdDate;
    /**
     * 更新时间
     */
    @LastModifiedDate
    private Date lastModifiedDate;
    /**
     * 乐观锁版本号
     */
    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;
}
