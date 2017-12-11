package ${serviceImplPackage};

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.qunar.pay.g2.utils.common.log.Logger;
import com.qunar.pay.g2.utils.common.log.LoggerFactory;

import ${servicePackage}.${className}Service;
import ${domainPackage}.${className};
import ${mapperPackage}.${className}Mapper;

/**
* ${className}Service
* Created by ${author} on ${nowDate}
*/
@Service("${lowerName}Service")
public class ${className}ServiceImpl implements ${className}Service {
	private static final Logger logger = LoggerFactory.getLogger(${className}.class);

	@Resource
	private ${className}Mapper ${lowerName}Mapper;

	
}
