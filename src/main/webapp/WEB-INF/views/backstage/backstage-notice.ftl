<#include "/templates/xjj-index.ftl">
<#--导航-->
<@nav '新境界','首页公告'/>
<@content id="notice">

	<div class="panel panel-info">
      <div class="panel-heading">
        <h3 class="panel-title">开发者信息</h3>
      </div>
      <div class="panel-body">
      	邮箱: jlsdzhj@126.com<br/>
        QQ号: 215642191<br/>
        QQ群：174266358<br/>
      </div>
    </div>
    
    
   
	
</@content>


 <select class="chosen-select form-control" data-placeholder="Choose a State...">
			<option value="">-请选择-</option>
			<option value="AL">Alabama</option>
			<option value="AK">Alaska</option>
			<option value="AZ">Arizona</option>
			<option value="AK">Alaska</option>
			<option value="MT">Montana</option>
			<option value="NE">Nebraska</option>
			<option value="NV">Nevada</option>
			<option value="WI">Wisconsin</option>
			<option value="WY">Wyoming</option>
		</select>
		
    
 		<script type="text/javascript">
		
		 $(document).ready(function(){
			$(".chosen-select").chosen();
	    });

		</script>
	