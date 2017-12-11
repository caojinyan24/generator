package ${mapperPackage};


import ${domainPackage}.${className};

/**
* ${className}Mapper
* Created by ${author} on ${nowDate}
*/
public interface ${className}Mapper{

    public void insertSelective(${className} ${lowerName});

    public void updateById(${className} ${lowerName});


    public ${className} selectSelective(${className} ${lowerName});

}