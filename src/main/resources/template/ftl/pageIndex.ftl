<form role="form" class="form-inline" action="../${lowerName}/${lowerName}Index.do" method="post" id="formSubmit">
    <div class="col-md-10" style="width:100%">
        <div class="panel panel-default">
            <div class="panel-heading">${codeName}列表</div>
            <div class="panel-body">
                <div class="search">
                <#list columnDatas as item>
                    <#if item.columnName != 'id'>
                        <#if item.columnName != 'del_stat' && item.columnName != 'creator' && item.columnName != 'editor' && item.columnName != 'create_dt' && item.columnName != 'edit_dt' && item.columnName != 'last_edit_dt' && item.columnName != 'record_version'>
                            <#if item.columnType == "datetime" ||item.columnType == "date" || item.columnType == "timestamp">
                                <div class="form-group col-sm-3">
                                    <label for="${item.domainPropertyName}"
                                           class="control-label col-sm-3 line34">${item.columnComment}</label>
                                    <div class="col-sm-8">
                                        <input type="text" name="${item.domainPropertyName}"
                                               id="${item.domainPropertyName}"
                                               value="$!dateTool.format('yyyy-MM-dd',$!{${lowerName}.${item.domainPropertyName}})"
                                               class="form-control" placeholder="${item.columnComment}">
                                    </div>
                                </div>
                            <#else>
                                <div class="form-group col-sm-3">
                                    <label for="${item.domainPropertyName}"
                                           class="control-label col-sm-3 line34">${item.columnComment}</label>
                                    <div class="col-sm-8">
                                        <input type="text" name="${item.domainPropertyName}"
                                               id="${item.domainPropertyName}"
                                               value="$!{${lowerName}.${item.domainPropertyName}}" class="form-control"
                                               placeholder="${item.columnComment}">
                                    </div>
                                </div>
                            </#if>
                        </#if>
                    </#if>
                </#list>
                    <div class="clearfix"></div>
                    <button type="submit" class="btn btn-primary">搜 索</button>
                </div>
                <div id="legend">
                    <legend class="le">
                        <button type="button" class="btn btn-primary"
                                onclick="remoteUrl('../${lowerName}/toAddDialog.do','新增用户')">新增
                        </button>
                    </legend>
                </div>
                <table class="table table-striped">
                    <thead>
                    <#list columnDatas as item>
                        <#if item.columnName != 'id'>
                            <#if item.columnName != 'del_stat' && item.columnName != 'creator' && item.columnName != 'editor' && item.columnName != 'create_dt' && item.columnName != 'edit_dt' && item.columnName != 'last_edit_dt' && item.columnName != 'record_version'>
                            <th>${item.columnComment}</th>
                            </#if>
                        </#if>
                    </#list>
                    <th>操作</th>
                    </thead>
                    <tobody>
                        #if($!{pageInfos})
                        #foreach($!{info} in $!{pageInfos})
                        <tr>
                        <#list columnDatas as item>
                            <#if item.columnName != 'id'>
                                <#if item.columnName != 'del_stat' && item.columnName != 'creator' && item.columnName != 'editor' && item.columnName != 'create_dt' && item.columnName != 'edit_dt' && item.columnName != 'last_edit_dt' && item.columnName != 'record_version'>
                                    <#if item.columnType == "datetime" ||item.columnType == "date" || item.columnType == "timestamp">
                                        <td>$!dateTool.format("yyyy-MM-dd",$!{info.${item.domainPropertyName}})</td>
                                    <#else>
                                        <td>$!{info.${item.domainPropertyName}}</td>
                                    </#if>
                                </#if>
                            </#if>
                        </#list>
                            <td class="last">
                                <a href="javascript:remoteUrl('../${lowerName}/toEditDialog.do?id=$!{info.id}','编辑用户')">编辑</a>
                                <a href="javascript:delData('../${lowerName}/${lowerName}Delete.do?id=$!{info.id}')">删除</a>
                                <a href="../${lowerName}/${lowerName}Detail.do?id=$!{info.id}">详情</a>
                            </td>
                        </tr>
                        #end
                        #end
                    </tobody>
                </table>
                <div class="text-right">
                    <!--公用翻页代码-->
                    #set($attr='formSubmit')
                    #showPageList($pageInfos $attr)
                    <!--END公用翻页代码-->
                </div>
            </div>
        </div>
    </div>
</form>
#parse("common/modal.vm")

<script type="text/javascript">
    $(document).ready(function () {
        $("#addButton").bind("click", function () {
            location.href = "../${lowerName}/toAdd.do";
        });
        $("#dialogFormSubmit").bind("click", function () {
            $("#btn_sub").click();
        });
    });

</script>