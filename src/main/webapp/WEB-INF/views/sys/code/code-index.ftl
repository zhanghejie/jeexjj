<#include "/templates/xjj-index.ftl">
<#--导航-->
<@navList navs=navArr/>
<@content id="code">
	<form id="codeGenerate" action="${base}/sys/code/generate" method="post">
	<div class="panel panel-info">
	  <div class="panel-heading">
        <h3 class="panel-title">请选择数据库表</h3>
      </div>
      <div class="panel-body">
      	<#list tableList as table>
			<input type="checkbox" name="tables" value="${table}"/>${table}
			&nbsp;&nbsp;&nbsp;&nbsp;
		</#list>
		<br/>
		<font color="red">注*表名结构为【前缀_模块名_表名】</font><br/>
      </div>
    </div>
    
    
    <div class="panel panel-info">
	  <div class="panel-heading">
        <h3 class="panel-title">请输入配置息信息</h3>
      </div>
      <div class="panel-body">
      		
			生成路径：<input type="text" name="codePath" value="d:/xjj_code/"/><br/>
			代码包名：<input type="text" name="globalPackage" value="com.xjj"/><br/>
			项目名称：<input type="text" name="projectName" value="新境界"/><br/>
			表名前缀：<input type="text" name="tablePre" value="t_"/><br/>
			生成模版：<input type="radio" name="template" checked="checked" value="ace"/>ace<br/>
			模块下是否按表名区分目录：
			<input type="radio" name="diffTable" checked="checked" value="yes"/>是
			<input type="radio" name="diffTable" value="no"/>否
			<br/>
			<font color="red">注*表名结构为【前缀_模块名_表名】</font><br/>
      </div>
    </div>
    
    
    </form>
    <@button type="info" icon="glyphicon glyphicon-transfer" onclick="generateCode();">生成代码</@button>
</@content>
<script>
	
	//生成代码
	function generateCode()
	{
		var data = $('#codeGenerate').formSerialize("tables");
		if(data==null||data=="")
		{
			XJJ.msger("请选择要生成代码的数据库表");
			return;
		}
		// 开始远程调用
		$.ajax({
			type : "post",
			url : "${base}/sys/code/generate",
			dataType : "json",
			data : $('#codeGenerate').formSerialize("tables"),
			success : function(data) {
			
				if(data.type=="success")
				{
					XJJ.msgok("代码生成完毕！");
				}else
				{
					XJJ.msger(data.message);
				}
				
			},
            error : function() {
            }
		});
	}
</script>