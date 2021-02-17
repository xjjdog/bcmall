package cn.xjjdog.bcmall.module.crm.domain;

import cn.xjjdog.bcmall.utils.BasicInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 会员，管理员，或者其他。统一抽象用户
 */
@Data
public class Member extends BasicInfo {
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
     * 地址
     */
    private List<Address> address;

    /**
     * 获取实际地址。优先获取默认地址，否则获取第一个地址
     * @return
     */
    public String getDefaultAddress(){
        if(null == address || address.size()==0){
            return "";
        }

        Address addr = address.stream()
                .filter(a->a.isDefaultAddress())
                .findFirst()
                .orElse(address.iterator().next());

        return addr.toSingleLineAddress();
    }
}
