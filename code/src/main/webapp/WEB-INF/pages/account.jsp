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
</head>
<body>
<div class="layui-container"  ng-controller="AccountCtrl" ng-cloak>
<div class="fly-home fly-panel" style="background-image: url();" ng-init="userId='${id==null?user.id:id}'">
	<img src="{{user.head_image}}" alt="{{user.nick_name}}">
	<h1>
		{{user.nick_name}}
		<i class="iconfont ng-class="{1:'icon-nan',2:'icon-nv'}[user.sex]">
		</i>
	</h1>
	<p class="fly-home-info">
		<i class="iconfont icon-shijian">
		</i>
		<span>
			{{user.sdate}} 加入
		</span>
		<i class="iconfont icon-chengshi">
		</i>
		<span>
			来自{{user.city}}
		</span>
	</p>
	<p class="fly-home-sign">
	 {{user.summary || '（这个人懒得留下签名）'}}
	</p>
</div>

<div style="background-image: url();">

<ul class="layui-timeline" style="margin-bottom:20px;">
  <li class="layui-timeline-item  fly-panel" ng-repeat="group in groups" style="margin-bottom:1px;">
    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
    <div class="layui-timeline-content layui-text">
      <h3 class="layui-timeline-title">{{group.date}}</h3>
      <p>
        <ul ng-repeat="item in group.items">
			<li><a href='/article?id={{item.id}}'>{{item.title}}</a>  ----<a href="/editor?id={{item.id}}" ng-if="item.user_id=='${user.id}'">修改</a> </li>
        </ul>
      </p>
    </div>
  </li>
  <li class="layui-timeline-item  fly-panel" style="margin-bottom:1px;">
    <i class="layui-icon layui-timeline-axis">&#x1002;</i>
    <div class="layui-timeline-content layui-text">
	  <h3 class="layui-timeline-title">很久以前</h3>
	  <p>
		 加载么？
	  </p>
	</div>
  </li>
</ul>
</div>
</div>
<script src="/statics/js/account/index.js?_t=1234" type="text/javascript"></script>
</body>
</html>
