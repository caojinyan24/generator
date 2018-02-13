<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="${className}">

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
    <update id="updateById" parameterType="${domainPackage}.${className}">
        UPDATE   ${tableName}
        <trim prefix="SET" suffixOverrides=",">
        <#list columnDatas as item>
            <if test="${item.domainPropertyName} != null">
            ${item.columnName} = ${"#"}{${item.domainPropertyName}},
            </if>
        </#list>
        </trim>
        WHERE
        id = ${"#"+"{id}"}
    </update>

    <insert id="insert" parameterType="${domainPackage}.${className}">
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
        (
    <#list columnDatas as item>
        <#if item_index==1>
        ${"                      "} ${"#"}{${item.domainPropertyName}}
        <#elseif item_index gt 1>
        ${"                     "},${"#"}{${item.domainPropertyName}}
        </#if>
    </#list>
        )
    </insert>

    <insert id="insertOnDuplicate" parameterType="${domainPackage}.${className}">
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


    <insert id="insertSelective" parameterType="${domainPackage}.${className}">
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


    <select id="selectById" parameterType="java.lang.Long" resultType="${domainPackage}.${className}">
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

    <select id="selectSelective" parameterType="${domainPackage}.${className}" resultType="${domainPackage}.${className}">
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


</mapper>