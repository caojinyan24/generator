<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="com.xiaoju.global.fintech.creditcard.dao.ccscore.${className}Mapper">
<sql id="wherecontation">
        <trim suffixOverrides=",">
        <#list columnDatas as item>
            <#if item.columnKey != 'PRI'>
                <if test="query.${item.domainPropertyName} != null and query.${item.domainPropertyName} != ''">
                    AND ${tablesAsName}.${item.columnName} =  ${"#"}{query.${item.domainPropertyName}}
                </if>
            </#if>
        </#list>
        </trim>
    </sql>
    <update id="updateByUniqKey" parameterType="com.xiaoju.global.fintech.creditcard.dao.entity.core.${className}">
        UPDATE   ${tableName}
        <trim prefix="SET" suffixOverrides=",">
        <#list columnDatas as item>
            <if test="${item.domainPropertyName} != null">
            ${item.columnName} = ${"#"}{data.${item.domainPropertyName}},
            </if>
        </#list>
        </trim>
        WHERE
        ${uniqCondition}
    </update>





    <insert id="insertSelective" parameterType="com.xiaoju.global.fintech.creditcard.dao.entity.core.${className}">
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






<insert id="insertOnDuplicate" parameterType="com.xiaoju.global.fintech.creditcard.dao.entity.core.${className}">
        INSERT INTO  ${tableName}
        (
    <#list columnDatas as item>
        <#if item_index==1>
        ${"                      "} ${item.columnName}
        <#elseif item_index gt 1>
        ${"                     "},${item.columnName}
        </#if>
    </#list>
        )
        values
        <foreach collection="records" separator="," item="item">
        (
    <#list columnDatas as item>
        <#if item_index==1>
        ${"                      "} ${"#"}{item.${item.domainPropertyName}}
        <#elseif item_index gt 1>
        ${"                     "},${"#"}{item.${item.domainPropertyName}}
        </#if>
    </#list>
        )
        </foreach>
        on duplicate key update
    <#list columnDatas as item>
        <#if item_index==1>
        ${"                      "}${item.columnName}=values(${item.columnName})
        <#elseif item_index gt 1>
        ${"                      "},${item.columnName}=values(${item.columnName})
        </#if>
    </#list>
    </insert>





    <select id="selectById" parameterType="java.lang.Long" resultType="com.xiaoju.global.fintech.creditcard.dao.entity.core.${className}">
        SELECT
    <#list columnDatas as item>
        <#if item_index==0>
        ${" "}${item.columnName}  AS  ${" "}${item.domainPropertyName}
        <#else>
        ${" "},${item.columnName}  AS  ${"   "}${item.domainPropertyName}
        </#if>
    </#list>
        FROM   ${tableName}
        WHERE
        id = ${"#"+"{id}"}
    </select>
<select id="selectWithUniqKey"  resultType="com.xiaoju.global.fintech.creditcard.dao.entity.core.${className}">
        SELECT
    <#list columnDatas as item>
        <#if item_index==0>
        ${" "}${item.columnName}  AS  ${" "}${item.domainPropertyName}
        <#else>
        ${" "},${item.columnName}  AS  ${"   "}${item.domainPropertyName}
        </#if>
    </#list>
        FROM   ${tableName}
        WHERE
        ${uniqCondition}
    </select>
    <select id="selectSelective" parameterType="com.xiaoju.global.fintech.creditcard.dao.entity.core.${className}" resultType="com.xiaoju.global.fintech.creditcard.dao.entity.core.${className}">
        SELECT
    <#list columnDatas as item>
        <#if item_index==0>
        ${" "}${item.columnName}  AS  ${" "}${item.domainPropertyName}
        <#else>
        ${" "},${item.columnName}  AS  ${"   "}${item.domainPropertyName}
        </#if>
    </#list>
        FROM   ${tableName}
        WHERE
        <trim suffixOverrides=",">
        <#list columnDatas as item>
            <if test="query.${item.domainPropertyName} != null and query.${item.domainPropertyName} != ''">
                AND ${tablesAsName}.${item.columnName} =  ${"#"}{query.${item.domainPropertyName}}
            </if>
        </#list>
        </trim>
    </select>
<select id="selectByCustId" parameterType="java.lang.String" resultType="com.xiaoju.global.fintech.creditcard.dao.entity.core.${className}">
        SELECT
    <#list columnDatas as item>
        <#if item_index==0>
        ${" "}${item.columnName}  AS  ${" "}${item.domainPropertyName}
        <#else>
        ${" "},${item.columnName}  AS  ${"   "}${item.domainPropertyName}
        </#if>
    </#list>
        FROM   ${tableName}
        WHERE ${tableName}.cust_id=${"#"+"{custId}"}
</select>
</mapper>