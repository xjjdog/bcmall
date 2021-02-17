package cn.xjjdog.bcmall.module.crm.util;

import cn.xjjdog.bcmall.module.crm.domain.Address;
import cn.xjjdog.bcmall.module.crm.domain.Member;
import cn.xjjdog.bcmall.module.crm.persistence.MemberEntity;
import cn.xjjdog.bcmall.utils.utils.JSON;
import com.fasterxml.jackson.databind.JavaType;
import io.vavr.control.Try;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface Transform {
    Transform T = Mappers.getMapper(Transform.class);

    Member fromMemberEntity(MemberEntity entity);

    default List<Address> fromJsonToAddress(String jsonList) {
        JavaType javaType = JSON.OM.getTypeFactory().constructCollectionType(List.class, Address.class);
        return Try.of(() -> {
            List<Address> addresses = JSON.OM.readValue(jsonList, javaType);
            return addresses;
        }).getOrNull();
    }

    MemberEntity fromMember(Member member);

    default String fromAddressesToJson(List<Address> addresses) {
        return Try.of(() -> JSON.OM.writeValueAsString(addresses)).getOrNull();
    }
}
