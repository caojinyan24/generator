package ${domainPackage}.service.datachecker;

import com.google.common.collect.Maps;
import ${basePackage}.${bizPackage}.dao.ccscore.${className}Mapper;
import ${basePackage}.${bizPackage}.dao.authsharding.S${className}Mapper;
import ${basePackage}.${bizPackage}.dao.entity.core.${className};
import ${basePackage}.${bizPackage}.util.GlobalParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
* ${className}Mapper
* Created by ${author}
*/
@Component
public class ${className}Checker extends Abstract {
    private static final Logger logger = LoggerFactory.getLogger(${className}Checker.class);

    @Resource
    ${className}Mapper ${lowerName}Mapper;
    @Resource
    S${className}Mapper s${className}Mapper;
   @Resource
    GlobalParam globalParam;

    @Override
    public Boolean check(String custId) {//
        List<${className}> oldData = ${lowerName}Mapper.selectByCustId(custId);
        List<${className}> newData = s${className}Mapper.selectByCustId(custId, globalParam.getTableSuffix(custId));
        if (oldData.size() != newData.size()) {
            logger.error("data size not equal:{},{}", oldData.size(), newData.size());
            return false;
        }
        Map<String, ${className}> newMap = Maps.newHashMap();
        for (${className} item : newData) {
            newMap.put(item.getUniqKeyValue(), item);
        }
        for (${className} item : oldData) {
            ${className} newItem = newMap.get(item.getUniqKeyValue());
            if (newItem == null) {
                logger.error("data not exist:{}", item.getUniqKeyValue());
                return false;
            }
            if (!newItem.equals(item)) {
                logger.error("data not equal:{},{}", item, newItem);
                return false;
            }
        }
        return true;
    }
}
