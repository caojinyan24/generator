package ${domainPackage}.service.datarepair;

import com.google.common.collect.Maps;
import ${basePackage}.${bizPackage}.dao.ccscore.${className}Mapper;
import ${basePackage}.${bizPackage}.dao.coresharding.S${className}Mapper;
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
public class ${className}Repair extends Abstract {
    private static final Logger logger = LoggerFactory.getLogger(${className}Repair.class);

    @Resource
    ${className}Mapper ${lowerName}Mapper;
    @Resource
    S${className}Mapper s${className}Mapper;
@Resource
    GlobalParam globalParam;

    @Override
    public Boolean repair(String custId) {//
        List<${className}> oldData = ${lowerName}Mapper.selectByCustId(custId);
        List<${className}> newData = s${className}Mapper.selectByCustId(custId, globalParam.getTableSuffix(custId));
        if (oldData.size() < newData.size()) {
            logger.error("attention: new db data more than old  :{},{}", oldData.size(), newData.size());
            return false;
        }
        Map<String, ${className}> newMap = Maps.newHashMap();
        for (${className} item : newData) {
            newMap.put(item.getUniqKeyValue(), item);
        }
        for (${className} item : oldData) {
            String uniq = item.getUniqKeyValue();
            ${className} newItem = newMap.get(uniq);
            if (newItem == null) {//insert data
                item.setPid(null);
                s${className}Mapper.insertSelective(item, globalParam.getTableSuffix(custId));
                continue;
            }
            if (!newItem.equals(item)) {//update
                item.setPid(null);
                s${className}Mapper.updateByUniqKey(${uniqItemParam} item, globalParam.getTableSuffix(custId));
            }
        }
        return true;
    }
}
