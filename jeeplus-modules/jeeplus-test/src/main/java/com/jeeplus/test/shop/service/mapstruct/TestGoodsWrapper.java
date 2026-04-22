/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.shop.service.dto.TestGoodsDTO;
import com.jeeplus.test.shop.domain.TestGoods;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestGoodsWrapper
 * @author 刘高峰
 * @version 2024-09-03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestGoodsWrapper extends EntityWrapper<TestGoodsDTO, TestGoods> {

    TestGoodsWrapper INSTANCE = Mappers.getMapper(TestGoodsWrapper.class);
     @Mappings({
            @Mapping(source = "category.id", target = "categoryId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping (source = "updateBy.id", target = "updateById")})
    TestGoods toEntity(TestGoodsDTO dto);


    @Mappings({
            @Mapping(source = "categoryId", target = "category.id"),
            @Mapping (source = "createById", target = "createBy.id"),
            @Mapping (source = "updateById", target = "updateBy.id")})
    TestGoodsDTO toDTO(TestGoods entity);
}

