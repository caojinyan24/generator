package ${domainPackage}.dao.ccscore;

import ${basePackage}.${bizPackage}.dao.entity.core.${className};
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Repository;

/**
* ${className}Mapper
* Created by ${author}
*/
@Repository
@Qualifier("ccscoreSqlSessionTemplate")
public interface ${className}Mapper{
     void insertSelective(${className} ${lowerName});

    List<${className}> selectByCustId(@Param("custId") String custId);

${generateByUniqKey}



}