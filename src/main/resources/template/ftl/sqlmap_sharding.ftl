<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="${mapperPackage}.${className}Mapper">



    <insert id="insertSelective" parameterType="${domainPackage}.${className}">
        INSERT INTO  ${tableName}${"$"}{suffix}
        (
        <trim suffixOverrides=",">
        <#list columnDatas as item>
            <#if item_index==1>
            <if test="data.${item.domainPropertyName} != null">
                ${item.columnName},
            </if>
            <#elseif item_index gt 1>
            <if test="data.${item.domainPropertyName} != null">
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
            <if test="data.${item.domainPropertyName} != null">
                ${"#"}{data.${item.domainPropertyName}},
            </if>
            <#elseif item_index gt 1>
            <if test="data.${item.domainPropertyName} != null">
                ${"#"}{data.${item.domainPropertyName}},
            </if>
            </#if>
        </#list>
        </trim>
        )
    </insert>

<select id="selectByCustId" parameterType="java.lang.String" resultType="{domainPackage}.dao.entity.core.${className}">
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
    <update id="updateByUniqKey" parameterType="${domainPackage}.${className}">
        UPDATE   ${tableName}${"$"}{suffix}
        <trim prefix="SET" suffixOverrides=",">
        <#list columnDatas as item>
            <if test="data.${item.domainPropertyName} != null">
            ${item.columnName} = ${"#"}{data.${item.domainPropertyName}},
            </if>
        </#list>
        </trim>
        WHERE
         = ${"#"+"{uniqValue}"}
    </update>
</mapper>