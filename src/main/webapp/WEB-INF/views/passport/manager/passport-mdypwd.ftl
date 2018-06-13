<#include "/templates/xjj-index.ftl"> 
<script type="text/javascript">
	function _checked(value){
		if($("#pwd").val() != value){
			XJJ.msger("新密码两次输入的不一致");
		} 
	}
</script>
<@input url="${base}/passport/manager/mdypwd/save">
    <@formgroup title='原密码'><input type="password" name="oldpassword" check-type='required' ></@formgroup>
    <@formgroup title='新密码'><input type="password" id="pwd" name="newpassword" check-type='required' ></@formgroup>
    <@formgroup title='确认新密码'><input type="password" onblur="_checked(this.value);" check-type='required' ></@formgroup>
</@input>