<#--
/****************************************************
 * Description: 公共的信息提示页面
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      xjj
 * @version     1.0
**************************************************/
-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<title>温馨提示</title>
<link rel="stylesheet" type="text/css" />

<style>
*{margin:0;padding:0}
body{font-family:"微软雅黑";background:#DAD9D7}
img{border:none}
.bg{width:100%;background:url("${base}/style/exception/bg.jpg") no-repeat center top #DAD9D7;position:absolute;top:0;left:0;height:600px;overflow:hidden}
.cont{margin:0 auto;width:500px;line-height:20px;}
.c1{height:360px;text-align:center}
.c1 .img1{margin-top:180px}
.cont h1{text-align:center;color:#999;font-size:22px;font-weight:normal;height:35px}
.cont h2{text-align:center;color:#555;font-size:18px;font-weight:normal;height:35px}
</style>

</head>
<body>
<div class="bg">
	<div class="cont">
		<div class="c1"><img src="${base}/style/exception/face.png" class="img1" /></div>
		<#if message?exists>
		<#else>
			<h1>哎呀…您访问的页面出错了</h1>
		</#if>
		<h2>${message}</h2>
		<div id="stackTrace" style="display: none;">
			${stackTrace}
		</div>
	</div>
</div>
</body>
</html>