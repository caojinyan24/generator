<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="{domainPackage}.dao.ccscore.${className}Mapper">

<insert id="insertSelective" parameterType="{domainPackage}.dao.entity.core.${className}">
        INSERT INTO  ${tableName}
        (
        <trim suffixOverrides=",">
        <#list columnDatas as item>
            <#if item_index==1>
            <if test="${item.domainPropertyName} != null">
                ${item.columnName},
            </if>
            <#elseif item_index gt 1>
            <if test="${item.domainPropertyName} != null">
               ${item.columnName},
            </if>
            </#if>
        </#list>
        </trim>
        )
        values
        (
        <trim suffixOverrides=",">
        <#list columnDatas as item>
            <#if item_index==1>
            <if test="${item.domainPropertyName} != null">
                ${"#"}{${item.domainPropertyName}},
            </if>
            <#elseif item_index gt 1>
            <if test="${item.domainPropertyName} != null">
                ${"#"}{${item.domainPropertyName}},
            </if>
            </#if>
        </#list>
        </trim>
        )
    </insert>

    <select id="selectByCustId"  resultType="{domainPackage}.dao.entity.core.${className}">
        SELECT
    <#list columnDatas as item>
        <#if item_index==0>
        ${" "}${item.columnName}  AS  ${" "}${item.domainPropertyName}
        <#else>
        ${" "},${item.columnName}  AS  ${"   "}${item.domainPropertyName}
        </#if>
    </#list>
        FROM   ${tableName}
        WHERE cust_id=${"#"+"{custId}"}

    </select>

</mapper>