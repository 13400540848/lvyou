<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getContextPath();
if(basePath.endsWith("/")){
	basePath = basePath.substring(0, basePath.length()-1);
}
String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+basePath;
%>
<!DOCTYPE html>
<html>
 <head> 
  <meta charset="utf-8" /> 
  <meta name="keywords" content="文学交流、社评、教育文化" />
  <meta name="description" content=" YouMe文化传播社是文字爱好者交流平台，致力于在线文字发布、学习、评比信息化服务，提供文学爱好者横向切磋平台。" />
  <meta name="renderer" content="webkit" /> 
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> 
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" /> 
  <title> YouMe文化传播社</title>
  <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
  <link rel="stylesheet" href="/statics/layui/css/layui.css?t=1513292531245" />
  <link rel="stylesheet" href="/statics/global.css?t=1513292531245" charset="utf-8" />
  <script src="/statics/layui/layui.js?t=1513292531245"></script>
  <script>layui.cache.host='/statics/layui/';layui.cache.page ='';layui.cache.user ={username:"<c:out value='${user.userName}'/>" ,uid:"<c:out value='${user.id}'/>" ,avatar:"<c:out value='${user.headImage}'/>" ,experience:0 ,sum:0 ,vip:0 ,sex:''};</script>
  <sitemesh:write property='head'/>
 </head>
 <body style="margin-top:0px;">
   <div class="layui-container">
     <div class="fly-body">
      <sitemesh:write property='body'/>
    </div>
   </div>
  </body>
</html>