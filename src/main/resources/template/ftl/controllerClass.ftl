
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;



 /**
 * ${className}Controller  ${codeName}
 * Created by ${author} on ${nowDate}
 */
@Controller
@RequestMapping("/${lowerName}")
public class ${className}Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);
  @Resource
  private ${className}Service ${lowerName}Service;
  
 /**
  * 列表页面
  * @return
  */
@RequestMapping(value="${lowerName}Index",method = {RequestMethod.GET,RequestMethod.POST})
public ModelAndView ${lowerName}Index(@ModelAttribute ${className} query,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) {
	 	PageQuery<${className}> pageQuery = new PageQuery<${className}>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	ModelAndView mav = new ModelAndView();
		pageQuery.setQuery(query);
		mav.addObject("query",query);
		mav.setViewName("${bussPackage}/${lowerName}Index");
		mav.addObject("pageInfos",SystemTools.convertPaginatedList(${lowerName}Service.query${className}PageList(pageQuery)));
		return mav;
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="${lowerName}Detail",method = RequestMethod.GET)
public ModelAndView ${lowerName}Detail(@RequestParam(required = true, value = "id" ) Long id){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("${bussPackage}/${lowerName}Detail");
		${className} ${lowerName} = ${lowerName}Service.queryByPriKey(id);
		mav.addObject("${lowerName}",${lowerName});
		return mav;
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAddDialog",method ={RequestMethod.GET, RequestMethod.POST})
public String toAddDialog(HttpServletRequest request,ModelMap model){
    return "${bussPackage}/${lowerName}AddDialog";
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/${lowerName}Add",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson ${lowerName}Add(@ModelAttribute ${className} ${lowerName}){
	AjaxJson j = new AjaxJson();
	try {
		${lowerName}Service.add(${lowerName});
		j.setMsg("保存成功");
	} catch (Exception e) {
		j.setSuccess(false);
		j.setMsg("保存失败");
	}
	return j;
}

/**
 * 跳转到编辑页面
 * @return
 */
@RequestMapping(value="toEditDialog",method = RequestMethod.GET)
public ModelAndView toEditDialog(@RequestParam(required = true, value = "id" ) Long id){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("${bussPackage}/${lowerName}EditDialog");
		${className} ${lowerName} = ${lowerName}Service.queryByPriKey(id);
		mav.addObject("${lowerName}",${lowerName});
		return mav;
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/${lowerName}Edit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson ${lowerName}Edit(@ModelAttribute ${className} ${lowerName}){
	AjaxJson j = new AjaxJson();
	try {
		${lowerName}Service.update(${lowerName});
		j.setMsg("编辑成功");
	} catch (Exception e) {
		j.setSuccess(false);
		j.setMsg("编辑失败");
	}
	return j;
}


/**
 * 删除
 * @return
 */
@RequestMapping(value="${lowerName}Delete",method = RequestMethod.GET)
@ResponseBody
public AjaxJson ${lowerName}Delete(@RequestParam(required = true, value = "id" ) Long id){
		AjaxJson j = new AjaxJson();
		try {
			${lowerName}Service.deleteByPriKey(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

