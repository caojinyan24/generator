<div  class="col-md-10" style="width:100%">
	<div class="panel panel-default minHeight">
	  	<div class="panel-heading">查看详情</div>
	  	<div class="panel-body">
	   		<form class="form-horizontal" role="form">
				  <fieldset disabled>
				  <#list columnDatas as item>
				  	<#if item.domainPropertyName != 'id'>
					<#if item.columnName != 'del_stat' && item.columnName != 'creator' && item.columnName != 'editor' && item.columnName != 'create_dt' && item.columnName != 'edit_dt' && item.columnName != 'last_edit_dt' && item.columnName != 'record_version'>
						<#if item.columnType == "datetime" ||item.columnType == "date" || item.columnType == "timestamp">
							  <div class="form-group mno">
							    <label for="inputEmail3" class="col-sm-1 control-label" style="text-align:left;">${item.columnComment}</label>
							    <div class="col-sm-2">
							      <input type="text" value="$!dateTool.format("yyyy-MM-dd",$!{${lowerName}.${item.domainPropertyName}})" name="${item.domainPropertyName}" id="${item.domainPropertyName}" class="form-control" />
							    </div>
							  </div>
						<#else>
							 <div class="form-group mno">
							    <label for="inputEmail3" class="col-sm-1 control-label" style="text-align:left;">${item.columnComment}</label>
							    <div class="col-sm-2">
							      <input type="text" value="$!{${lowerName}.${item.domainPropertyName}}" name="${item.domainPropertyName}" id="${item.domainPropertyName}" class="form-control" />
							    </div>
							  </div>
						</#if>
					</#if>
					</#if>
				</#list> 
				  </fieldset>
				  <div class="form-group">
				  		<span class="col-sm-1"></span>
				  		 <a href="#" class="btn btn-primary" id="returnButton" role="button">返回</a>
				  </div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
	 $("#returnButton").bind("click",function(){
		history.go(-1);
	});
</script>