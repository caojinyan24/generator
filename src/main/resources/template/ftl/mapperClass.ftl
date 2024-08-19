

import org.springframework.stereotype.Repository;

/**
* ${className}Mapper
* Created by ${author} on ${nowDate}
*/
@Repository
public interface ${className}Mapper{

 public void insertSelective(${className} ${lowerName});

    public void updateById(@Param("id") ${className} ${lowerName});


    public ${className} selectSelective(${className} ${lowerName});

${generateByUniqKey}



}