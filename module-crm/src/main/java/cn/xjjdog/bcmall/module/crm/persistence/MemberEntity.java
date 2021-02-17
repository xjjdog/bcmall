package cn.xjjdog.bcmall.module.crm.persistence;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 会员，管理员，或者其他。统一抽象用户
 */
@Data
@Entity
@Table(name = "member",
        indexes = {
                @Index(name = "idx_member_phone", columnList = "phone"),
                @Index(name = "idx_member_username", columnList = "username")
        })
public class MemberEntity extends AbstractEntity {
    /**
     * 地址，存储为JSON
     */
    private String address;
    /**
     * 头像
     */
    private String headUrl;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 电话
     */
    private String phone;
    /**
     * 微信
     */
    private String socialWeixin;
    /**
     * QQ
     */
    private String socialQq;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
