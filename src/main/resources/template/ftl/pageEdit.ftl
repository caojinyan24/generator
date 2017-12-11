<div class="panel-body">
	<form class="form-horizontal" role="form" id="dailogForm" action="../${lowerName}/${lowerName}Edit.do" method="POST">
		 <input type="hidden" id="btn_sub" class="btn_sub" />
		 <input type="hidden" value="$!{${lowerName}.id}" name="id" id="id" />
		  <#list columnDatas as item>
		  	<#if item.domainPropertyName != 'id'>
			<#if item.columnName != 'del_stat' && item.columnName != 'creator' && item.columnName != 'editor' && item.columnName != 'create_dt' && item.columnName != 'edit_dt' && item.columnName != 'last_edit_dt' && item.columnName != 'record_version'>
				<#if item.columnType == "datetime" ||item.columnType == "date" || item.columnType == "timestamp">
					<div class="form-group mno">
					    <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">${item.columnComment}</label>
					    <div class="col-sm-8">
					      <input type="text" value="$!dateTool.format("yyyy-MM-dd",$!{${lowerName}.${item.domainPropertyName}})" name="${item.domainPropertyName}" id="${item.domainPropertyName}" class="form-control" placeholder="${item.columnComment}"/>
					    </div>
					</div>
				<#else>
					<div class="form-group mno">
					    <label for="inputEmail3" class="col-sm-2 control-label" style="text-align:left;">${item.columnComment}</label>
					    <div class="col-sm-8">
					      <input type="text" value="$!{${lowerName}.${item.domainPropertyName}}" name="${item.domainPropertyName}" id="${item.domainPropertyName}" class="form-control" placeholder="${item.columnComment}"/>
					    </div>
					</div>
				</#if>
			</#if>
			</#if>
		</#list> 
	</form>
</div>
<script type="text/javascript" src="/resources/js/Validform_v5.3.2.js"></script> 
<script type="text/javascript" src="/resources/js/forminit.js"></script> 