package com.xiaoju.global.fintech.creditcard.service.datachecker;

import com.google.common.collect.Maps;
import com.xiaoju.global.fintech.creditcard.dao.ccscore.${className}Mapper;
import com.xiaoju.global.fintech.creditcard.dao.ccssharding.S${className}Mapper;
import com.xiaoju.global.fintech.creditcard.dao.entity.core.${className};
import com.xiaoju.global.fintech.creditcard.util.GlobalParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
* ${className}Mapper
* Created by ${author} on ${nowDate}
*/
@Component
public class ${className}Checker extends Abstract {
    private static final Logger logger = LoggerFactory.getLogger(${className}Checker.class);

    @Resource
    ${className}Mapper ${lowerName}Mapper;
    @Resource
    S${className}Mapper s${className}Mapper;


    @Override
    public Boolean check(String custId) {//
        List<${className}> oldData = ${lowerName}Mapper.selectByCustId(custId);
        List<${className}> newData = s${className}Mapper.selectByCustId(custId, GlobalParam.tableRouter.GetTableSuffix(custId));
        if (oldData.size() != newData.size()) {
            logger.error("data size not equal:{},{}", oldData.size(), newData.size());
            return false;
        }
        Map<String, ${className}> newMap = Maps.newHashMap();
        for (${className} item : newData) {
            newMap.put(item.getAcctNbr() + item.getAcctType(), item);
        }
        for (${className} item : oldData) {
            String uniq = item.getAcctNbr() + item.getAcctType();
            ${className} newItem = newMap.get(uniq);
            if (newItem == null) {
                logger.error("data not exist:{}", uniq);
                return false;
            }
            if (!newItem.equals(item)) {
                logger.error("data not equal:{},{}", item, newItem);
                return false;
            }
        }
        //todo 转map doublecheck
        return true;
    }
}
