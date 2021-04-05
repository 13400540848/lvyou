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
<script src="/statics/js/angular.js" type="text/javascript"></script>
<script src="/statics/js/angular-route.min.js" type="text/javascript"></script>
<script src="/statics/js/md5.min.js" type="text/javascript"></script>
</head>
<body>
<div class="layui-container fly-marginTop fly-user-main" ng-controller="UserCtrl"  ng-cloak>
	<ul class="layui-nav layui-nav-tree layui-inline fly-layui-nav" lay-filter="user">
	   <!--
		<li class="layui-nav-item" ng-class="{true:'layui-this',false:''}[currentPath=='/']">
			<a href="#/">
				<i class="layui-icon">
					
				</i>
				用户中心
			</a>
		</li>
		-->
		<li class="layui-nav-item" ng-class="{true:'layui-this',false:''}[currentPath=='/setting']">
			<a href="#/setting">
				<i class="layui-icon">
					
				</i>
				基本设置
			</a>
		</li>
		<!--
		<li class="layui-nav-item" ng-class="{true:'layui-this',false:''}[currentPath=='/collect']">
			<a href="#/collect">
				<i class="iconfont icon-tiezi">
				</i>
				我的收藏
			</a>
		</li>
		<li class="layui-nav-item" ng-class="{true:'layui-this',false:''}[currentPath=='/message']">
			<a href="#/message">
				<i class="layui-icon">
					
				</i>
				我的消息
			</a>
		</li>
		-->
		<span class="layui-nav-bar" style="top: 122.5px; height: 0px; opacity: 0;">
		</span>
	</ul>
	<div class="site-tree-mobile layui-hide">
		<i class="layui-icon">
			
		</i>
	</div>
	<div class="site-mobile-shade">
	</div>
	<div class="fly-panel fly-panel-user" pad20="">
    <ng-view></ng-view>
    </div>
</div>
<script src="/statics/js/uc/index.js?_t=1234" type="text/javascript"></script>
</body>
</html>