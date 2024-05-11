<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="${basePackage}.${bizPackage}.dao.ccscore.${className}Mapper">

    <update id="updateByUniqKey" parameterType="${basePackage}.${bizPackage}.dao.entity.core.${className}">
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







<insert id="insertSelective" parameterType="${basePackage}.${bizPackage}.dao.entity.core.${className}">
        INSERT INTO  ${tableName}
        (
        <trim suffixOverrides=",">
        <#list columnDatas as item>
            <if test="${item.domainPropertyName} != null">
                ${item.columnName},
            </if>
        </#list>
        </trim>
        )
        values
        (
        <trim suffixOverrides=",">
        <#list columnDatas as item>
            <if test="${item.domainPropertyName} != null">
                ${"#"}{${item.domainPropertyName}},
            </if>
  </#list>
        </trim>
        )
    </insert>





<select id="selectWithUniqKey"  resultType="${basePackage}.${bizPackage}.dao.entity.core.${className}">
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

<select id="selectByCustId" parameterType="java.lang.String" resultType="${basePackage}.${bizPackage}.dao.entity.core.${className}">
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