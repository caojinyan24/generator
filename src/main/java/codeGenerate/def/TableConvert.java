package codeGenerate.def;


import codeGenerate.vo.ConvertDef;

public class TableConvert {

	/**
	 * 返回字段是否为空属性
	 * @param nullable
	 * @return
	 */
	public static String getNullAble(String nullable){
		if("YES".equals(nullable)||"yes".equals(nullable)||"y".equals(nullable)||"Y".equals(nullable)){
			return ConvertDef.FIELD_NULL_ABLE_Y;
		}
		if("NO".equals(nullable)||"N".equals(nullable)||"no".equals(nullable)||"n".equals(nullable)){
			return ConvertDef.FIELD_NULL_ABLE_N;
		}
		return null;
	}
}
