<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="${mapperPackage}.${className}Mapper">

    <update id="updateByUniqKey" parameterType="${domainPackage}.${className}">
        UPDATE   ${tableName}
        <trim prefix="SET" suffixOverrides=",">
        <#list columnDatas as item>
            <if test="${item.domainPropertyName} != null">
            ${item.columnName} = ${"#"}{data.${item.domainPropertyName}},
            </if>
        </#list>
        </trim>
        WHERE
        XXX = ${"#"+"{uniqKey}"}
    </update>





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


    <select id="selectSelectiveWithUniqKey" resultType="${domainPackage}.${className}">
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
        XXX = ${"#"+"{uniqKey}"}
    </select>


<select id="selectSelectiveWithXXX"  resultType="${domainPackage}.${className}">
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
        xxx=${"#"+"{uniqKey}"}
        <trim suffixOverrides=",">
        <#list columnDatas as item>
            <if test="query.${item.domainPropertyName} != null and query.${item.domainPropertyName} != ''">
                AND ${tablesAsName}.${item.columnName} =  ${"#"}{query.${item.domainPropertyName}}
            </if>
        </#list>
        </trim>
</select>
</mapper>