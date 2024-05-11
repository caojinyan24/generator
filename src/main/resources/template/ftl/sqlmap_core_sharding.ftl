<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="${basePackage}.${bizPackage}.dao.coresharding.S${className}Mapper">



    <insert id="insertSelective" parameterType="${basePackage}.${bizPackage}.dao.entity.core.${className}">
        INSERT INTO  ${tableName}${"$"}{suffix}
        (
        <trim suffixOverrides=",">
        <#list columnDatas as item>
            <if test="data.${item.domainPropertyName} != null">
                ${item.columnName},
            </if>
        </#list>
        </trim>
        )
        values
        (
        <trim suffixOverrides=",">
        <#list columnDatas as item>
            <if test="data.${item.domainPropertyName} != null">
                ${"#"}{data.${item.domainPropertyName}},
            </if>
        </#list>
        </trim>
        )
    </insert>

<select id="selectByCustId" parameterType="java.lang.String" resultType="${basePackage}.${bizPackage}.dao.entity.core.${className}">
        SELECT
    <#list columnDatas as item>
        <#if item_index==0>
        ${" "}${item.columnName}  AS  ${" "}${item.domainPropertyName}
        <#else>
        ${" "},${item.columnName}  AS  ${"   "}${item.domainPropertyName}
        </#if>
    </#list>
        FROM   ${tableName}${"$"}{suffix}
        WHERE ${tableName}${"$"}{suffix}.cust_id=${"#"+"{custId}"}
</select>
<select id="selectWithUniqKey" parameterType="java.lang.String" resultType="${basePackage}.${bizPackage}.dao.entity.core.${className}">
        SELECT
    <#list columnDatas as item>
        <#if item_index==0>
        ${" "}${item.columnName}  AS  ${" "}${item.domainPropertyName}
        <#else>
        ${" "},${item.columnName}  AS  ${"   "}${item.domainPropertyName}
        </#if>
    </#list>
        FROM   ${tableName}${"$"}{suffix}
        WHERE  ${uniqCondition}
</select>
    <update id="updateByUniqKey" parameterType="${basePackage}.${bizPackage}.dao.entity.core.${className}">
        UPDATE   ${tableName}${"$"}{suffix}
        <trim prefix="SET" suffixOverrides=",">
        <#list columnDatas as item>
            <if test="data.${item.domainPropertyName} != null">
            ${item.columnName} = ${"#"}{data.${item.domainPropertyName}},
            </if>
        </#list>
        </trim>
        WHERE
        ${uniqCondition}

    </update>
</mapper>