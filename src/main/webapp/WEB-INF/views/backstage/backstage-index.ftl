<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>新境界快速开发框架</title>

		<meta name="description" content="Common UI Features &amp; Elements" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${base}/ace/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${base}/ace/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${base}/ace/assets/css/jquery-ui.custom.min.css" />
		<link rel="stylesheet" href="${base}/ace/assets/css/jquery.gritter.min.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="${base}/ace/assets/css/fonts.googleapis.com.css" />
		
		<!-- ace styles -->
		<link rel="stylesheet" href="${base}/ace/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${base}/ace/assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->
		<link rel="stylesheet" href="${base}/ace/assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="${base}/ace/assets/css/ace-rtl.min.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${base}/ace/assets/css/ace-ie.min.css" />
		<![endif]-->
		
		<link href="${base}/assets/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
		
		<link rel="stylesheet" href="${base}/assets/chosen_v1.8.3/chosen.min.css" />
		<link rel="stylesheet" href="${base}/assets/xjj/xjj.css" />

		<!-- ace settings handler -->
		<script src="${base}/ace/assets/js/ace-extra.min.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="${base}/ace/assets/js/html5shiv.min.js"></script>
		<script src="${base}/ace/assets/js/respond.min.js"></script>
		<![endif]-->
		
		<link rel="stylesheet" href="${base}/assets/fileuploader/fileuploader.css" />
		<script src="${base}/assets/fileuploader/fileuploader.js"></script>
		
	</head>

	<body class="no-skin">
		<div id="navbar" class="navbar navbar-default ace-save-state">
			<div class="navbar-container ace-save-state" id="navbar-container">
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<div class="navbar-header pull-left">
					<a href="index.html" class="navbar-brand">
						<small>
							XJJ快速开发框架
						</small>
					</a>
				</div>

				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="light-blue dropdown-modal">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
			                    <img class="nav-user-photo" src="${base}/ace/assets/images/avatars/user.jpg" />
									<span class="user-info">
										<small>欢迎登录系统</small>
										[${session_manager_info_key.userName}]
									</span>
			                    <i class="ace-icon fa fa-caret-down"></i>
			                </a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
			                        <a href="#" onclick="XJJ.edit('${base}/passport/manager/mdypwd','修改密码');">
			                            <i class="ace-icon fa fa-cog"></i>
			                            修改密码
			                        </a>
			                    </li>
			
			                    <li>
			                        <a href="#" onclick="XJJ.edit('${base}/passport/manager/mdyinfo','修改个人信息');">
			                            <i class="ace-icon fa fa-cog"></i>
			                            修改个人信息
			                        </a>
			                    </li>
			
			                    <li class="divider"></li>
			                    <li>
			                        <a href="${base}/passport/manager/logout">
			                            <i class="ace-icon fa fa-power-off"></i>
			                            退出系统
			                        </a>
			                    </li>
							</ul>
						</li>
					</ul>
				</div>
			</div><!-- /.navbar-container -->
		</div>

		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>

			<div id="sidebar" class="sidebar responsive ace-save-state">
				<ul class="nav nav-list">

					<#list menuList as m>
					<li class="<#if m_index==0>open</#if>">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-${m.icon}"></i>
							<span class="menu-text">${m.title}</span>

							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
						<ul class="submenu">
						
							
							<#if m.subMenus?? && m.subMenus?size gt 0>
							<#list m.subMenus as mm>
		
							<li class="">
								<a href="javascript:XJJ.showTab({id:'${mm.privilegeCode}',text:'${mm.title}',url:'${base}${mm.url}',navs:'${m.title},${mm.title}'});">
									<i class="menu-icon fa fa-caret-right"></i>
									${mm.title}
								</a>
								<b class="arrow"></b>
							</li>
							</#list>
							</#if>
						</ul>
					</li>
					</#list>
				

				</ul><!-- /.nav-list -->

				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>
			</div>

			<div class="main-content">
				<div class="main-content-inner">
					

					<div class="page-content">

						<div class="row">
							<div class="col-xs-12">
								<div id="tabContainer"></div>
							</div><!-- /.col -->
						</div><!-- /.row -->
					
						
					
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

			<div class="footer">
				<div class="footer-inner">
					<div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">XJJ</span>
							Application &copy; 2018-2028
						</span>
						<#--
						&nbsp; &nbsp;
						<span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
						-->
					</div>
				</div>
			</div>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="${base}/ace/assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="${base}/ace/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${base}/ace/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="${base}/ace/assets/js/bootstrap.min.js"></script>
		<script src="${base}/ace/assets/js/bootstrap3-validation.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="${base}/ace/assets/js/excanvas.min.js"></script>
		<![endif]-->
		<script src="${base}/ace/assets/js/jquery-ui.custom.min.js"></script>
		<script src="${base}/ace/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="${base}/ace/assets/js/bootbox.js"></script>
		<script src="${base}/ace/assets/js/jquery.easypiechart.min.js"></script>
		<script src="${base}/ace/assets/js/jquery.gritter.min.js"></script>
		<script src="${base}/ace/assets/js/spin.js"></script>
		
		<!-- ace scripts -->
		<script src="${base}/ace/assets/js/ace-elements.min.js"></script>
		<script src="${base}/ace/assets/js/ace.min.js"></script>
		
		<!-- datetimepicker.js -->
		<script src="${base}/assets/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
		<script src="${base}/assets/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

		<!-- inline scripts related to this page -->
		<script src="${base}/assets/xjj/xjj_bootstrap-tab.js"></script>
		<script src="${base}/assets/xjj/xjj.js"></script>
		<script src="${base}/assets/xjj/xjj_extend_jquery.js"></script>
		<script src="${base}/assets/xjj/xjj_extend_string.js"></script>
		<script src="${base}/assets/chosen_v1.8.3/chosen.jquery.min.js"></script>
		
	</body>
</html>


<script>
$(function() {
	//打开主页
	$("#tabContainer").tabs({
		data : [ {
			id : 'home',
			text : '首页公告',
			url : "${base}/backstage/notice",
			closeable : false
		} ],
		showIndex : 0,
		loadAll : false
	});
	
	
	//设置菜单点击样式
	$(".submenu li").click(function(){
        $(".nav-list li").removeClass('active');
        $(".submenu li").removeClass('active');
        $(this).addClass('active');
        $(this).parent().parent().addClass('active');
    });
});
</script>
