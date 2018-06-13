<!DOCTYPE>
<html>
  <head>
    
    <title>1111111111</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <#list bookList?if_exists as book>
    ${book.name}<br/>
    </#list>
    
    	<select class="chosen-select form-control"  data-placeholder="Choose a State...">
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
			$(function(){
				var config = {
				  '.chosen-select'           : { no_results_text: '未找到匹配项'},
				  '.chosen-select-deselect'  : { allow_single_deselect: true },
				  '.chosen-select-no-single' : { disable_search_threshold: 10 },
				  '.chosen-select-no-results': { no_results_text: '未找到匹配项' },
				  '.chosen-select-rtl'       : { rtl: true },
				  '.chosen-select-width'     : { width: '95%' }
				}
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
			
			});

		</script>
  </body>
</html>
