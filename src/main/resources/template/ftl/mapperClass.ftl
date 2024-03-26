

import org.springframework.stereotype.Repository;

/**
* ${className}Mapper
* Created by ${author} on ${nowDate}
*/
@Repository
public interface ${className}Mapper{

     void insertSelective(${className} ${lowerName});
${updateByUniqKey}
${selectSelectiveByIndex}
${selectSelectiveWithUniqKey}



}