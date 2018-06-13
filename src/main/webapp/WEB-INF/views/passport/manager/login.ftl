<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>新境界快速开发平台</title>

        <!-- CSS -->
        <link rel="stylesheet" href="${base}/style/passport/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${base}/style/passport/assets/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="${base}/style/passport/assets/css/form-elements.css">
        <link rel="stylesheet" href="${base}/style/passport/assets/css/style.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="${base}/style/passport/assets/js/html5shiv.js"></script>
            <script src="${base}/style/passport/assets/js/respond.min.js"></script>
        <![endif]-->
        <!-- Javascript -->
        <script src="${base}/style/passport/assets/js/jquery-1.11.1.min.js"></script>

        <script src="${base}/assets/layer-v3.1.1/layer/layer.js"></script>
		<link rel="stylesheet" href="${base}/assets/layer-v3.1.1/layer/theme/default/layer.css">
		
        <script src="${base}/style/passport/assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="${base}/style/passport/assets/js/jquery.backstretch.min.js"></script>
        
        <!--[if lt IE 10]>
            <script src="${base}/style/passport/assets/js/placeholder.js"></script>
        <![endif]-->
        <#--
		<script src="${base}/assets/xjj.js"></script>
		-->
    </head>

    <body>

        <!-- Top content -->
        <div class="top-content">
        	
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-5 col-sm-offset-3 form-box">
                        	<div class="form-top">
                        		<div class="form-top-left">
                        			<h3>XJJ</h3>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-lock"></i>
                        		</div>
                            </div>
                            <div class="form-bottom">
			                    <form id="loginForm">
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-username">Username</label>
			                        	<input type="text" id="loginName" name="loginName" placeholder="请输入账号" value="" class="form-username form-control" id="form-username">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-password">Password</label>
			                        	<input type="password" id="password" name="password" placeholder="请输入密码" class="form-password form-control" id="form-password">
			                        </div>
			                        <button id="loginBtn" type="button" class="btn" onclick="checkLogin();">登录</button>
			                        <font color="red"></font>
			                    </form>
		                    </div>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    </body>
</html>

<script type="text/javascript">
		$(function() {
		    //背景更换
		    $.backstretch([
		                    "${base}/style/passport/assets/img/bg/1.jpg"
			              , "${base}/style/passport/assets/img/bg/2.jpg"
			              , "${base}/style/passport/assets/img/bg/3.jpg"
			              , "${base}/style/passport/assets/img/bg/4.jpg"
			             ], {
			             	duration: 10000,
			            	transition: 'push_left|push_right|cover_up|cover_down|fade', transitionDuration: 500
			             });
		    //获得焦点去掉提示错误框
		    $('.form-control').on('focus', function() {
		    	$(this).removeClass('input-error');
		    });
		    
		    <#--
		    $('.login-form').on('submit', function(e) {
		    	$(this).find('input[type="text"], input[type="password"], textarea').each(function(){
		    		if( $(this).val() == "" ) {
		    			e.preventDefault();
		    			$(this).addClass('input-error');
		    		}
		    		else {
		    			$(this).removeClass('input-error');
		    		}
		    	});
		    	
		    });
		    -->
		});
		
		
		function checkLogin()
		{
			var $loginName = $("#loginName");
	    	var $password = $("#password");
	    	
	    	if($loginName.val() == "")
	    	{
	    		$loginName.addClass('input-error');
	    		layer.tips("请输入账号","#loginName",{tips:1});
	    		return;
	    	}
	    	
	    	if($password.val() == "")
	    	{
	    		$password.addClass('input-error');
	    		layer.tips("请输入密码","#password",{tips:3});
	    		return;
	    	}
	    	// 开始远程调用
			$.ajax({
				type : "post",
				async: true,
				url : "${base}/passport/manager/logon",
				data : $("#loginForm").serialize(),
				success : function(data, textStatus) {
					if(data.type=="success")
					{
						window.location.href="${base}/backstage/index";
					}else if(data.item=="loginName")
					{
						$loginName.addClass('input-error');
	    				layer.tips(data.message,"#loginName",{tips:1});
					}else if(data.item=="password")
					{
						$password.addClass('input-error');
	    				layer.tips(data.message,"#password",{tips:3});
					}
				}
			});
		}
        
</script>