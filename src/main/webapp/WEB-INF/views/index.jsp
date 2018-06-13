<%@ page language="java" import="java.util.*,javax.servlet.*" pageEncoding="ISO-8859-1"%>
<%

String loginName=request.getParameter("loginName");
if(null !=loginName && !"".equals(loginName))
{
	session.setAttribute("loginName",loginName);
}

 ServletContext ContextA =session.getServletContext();
 ContextA.setAttribute(session.getId(),session); 
  System.out.println("session.getId()=="+session.getId());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>XJJ</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript"> 
    	window.location.href='<%=request.getContextPath()%>/passport/manager/login';
    </script>
  </head>
  
  <body>
  				
  </body>
</html>
