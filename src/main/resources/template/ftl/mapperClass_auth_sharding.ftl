package ${domainPackage}.dao.authsharding;

import ${basePackage}.${bizPackage}.dao.entity.core.${className};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
* ${className}Mapper
* Created by ${author}
*/
@Mapper
@Qualifier("authshardingSqlSessionTemplate")
public interface S${className}Mapper {

    void insertSelective(@Param("data") ${className} ${lowerName}, @Param("suffix") String suffix);

${generateByUniqKeyForShardingDB}


    List<${className}> selectByCustId(@Param("custId") String custId, @Param("suffix") String suffix);


}