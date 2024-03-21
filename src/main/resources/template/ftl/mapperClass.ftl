

import org.springframework.stereotype.Repository;

/**
* ${className}Mapper
* Created by ${author} on ${nowDate}
*/
@Repository
public interface ${className}Mapper{

     void insertSelective(${className} ${lowerName});

     void updateByUniqKey(@Param("uniqKey") XXX,@Param("data") ${className} ${lowerName});


     List<${className}> selectSelectiveWithXXX(@Param("XXX")XXX,@Param("query")${className} ${lowerName});
      ${className} selectSelectiveWithUniqKey(@Param("uniqKey")XXX,@Param("query")${className} ${lowerName});

}