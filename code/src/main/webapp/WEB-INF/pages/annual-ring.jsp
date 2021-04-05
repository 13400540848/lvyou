<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getContextPath();
if(basePath.endsWith("/")){
	basePath = basePath.substring(0, basePath.length()-1);
}
String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+basePath;
%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>
<!-- 内容部分 begin-->
<div class="wrapCon">
    <!-- 内容第二部分 begin-->
    <div class="conSec">
     年轮...
    </div>
</div>
</body>
</html>
