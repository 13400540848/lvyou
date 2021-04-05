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
 <link href="/statics/css/article.css" rel="stylesheet" type="text/css"/>
 <style>
  .partTit{
     color:#063468 !important;
     font-weight:'blod';
     margin:0
  }
 </style>
</head>
<body>
<!-- 内容部分 begin-->
<div class="layui-container" ng-controller="MainCtrl" ng-cloak>
 <div class="layui-row layui-col-space15" style="margin-top:10px;">
 <span class="layui-breadcrumb">
   <c:if test="${datas[0].data.type==1}"><a href="/f?channel=article&type=1">随笔</a></c:if>
   <c:if test="${datas[0].data.type==2}"><a href="/f?channel=article&type=2">诗词</a></c:if>
   <c:if test="${datas[0].data.type==3}"><a href="/f?channel=article&type=3">散文</a></c:if>
   <c:if test="${datas[0].data.type==4}"><a href="/f?channel=article&type=4">小说</a></c:if>
   <a><cite>正文</cite></a>
 </span>
 </div>
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md8" ng-init="article.id='<c:out value="${datas[0].data.id}"/>'">
      <div class="fly-panel detail-box">
            <span ng-init="article.title='<c:out value="${datas[0].data.title}"/>'" ng-show="false"></span>
            <span ng-init="article.user_id='<c:out value="${datas[0].data.user_id}"/>'" ng-show="false"></span>
             <span  ng-init="article.greats=<c:out value="${datas[0].data.greats}"/>" ng-show="false"></span>
             <h1 style="color: rgb(0, 0, 0); font-family: stone-kaiti; font-size: 36px; font-weight: bold;margin-bottom:40px;"><c:out value="${datas[0].data.title}" escapeXml="false"/></h1>
             <div class="fly-detail-info">
             <div class="detail-hits">
             <span class="layui-badge layui-bg-blue"><c:out value="${datas[0].data.sdate}"/></span>
                <c:if test="${datas[0].data.source!=null && datas[0].data.source!=''}"><span class="layui-badge layui-bg-gray">来源：<c:out value="${datas[0].data.source}"/></span></c:if>
               <a style="float:right;"  class="layui-btn layui-btn-xs jie-admin" href="javascript:;"  ng-click="thumbUp()"><i class="layui-icon">&#xe6c6;</i><span class="txt"><em ng-bind="article.greats"></em>赞</span></a>
               </div>
             </div>
             <hr class="layui-bg-green">
            <div class="detail-body layui-text photos">
                 <p>
                    <c:out value="${datas[0].data.content}" escapeXml="false"/>
                 </p>
            </div>
       </div>

      <!-- 评论区-->
       <div class="fly-panel detail-box">
                    <span class="subtit"><em ng-bind="comment.cnt"></em>条评论</span>
                    <ul class="jieda">
                      <li ng-repeat="item in comment.comments">
                      <div class="detail-about detail-about-reply">
                         <a class="fly-avatar" href="/account?id={{item.user_id}}">
                            <img src="{{item.user_head_image}}" alt="{{item.user_name}}">
                         </a>
                         <div class="fly-detail-user"> <a href="/account?id={{item.user_id}}" class="fly-link"> <cite>{{item.user_name}}</cite> </a> </div>
                         <div class="detail-hits"> <span>{{item.sdate}}</span> </div>
                      </div>
                      <div class="detail-body layui-text jieda-body photos">
                        <p ng-bind-html="item.content | to_trusted"></p>
                      </div>
                      <div class="jieda-reply">
                        <span class="jieda-zan " type="zan" style="display:none;"> <i class="iconfont icon-zan"></i> <em>0</em> </span>
                        <span type="reply" ng-click=""> <i class="iconfont icon-svgmoban53"></i> 回复 </span>
                      </div>
                      </li>
                    </ul>
                  <div class="layui-form layui-form-pane">
                   <form method="post">
                   <div class="layui-form-item layui-form-text">
                    <a name="comment"></a>
                    <div class="layui-input-block">
                     <textarea id="L_content" ng-model="comment_content" name="content" required="" lay-verify="required" placeholder="请输入内容" class="layui-textarea fly-editor" style="height: 150px;"></textarea>
                    </div>
                   </div>
                   <div class="layui-form-item">
                    <button class="layui-btn" lay-filter="replay" lay-submit>提交回复</button>
                   </div>
                   </form>
                  </div>
                  </div>
      <!-- 评论区 END-->
    </div>
    <!-- 广告 外加 用户-->
    <div class="layui-col-md4">
       <c:if test="${(datas[0].data.source==null || datas[0].data.source=='') && datas[0].data.user_id!=null}">
       <div class="fly-panel">
            <h3 class="fly-panel-title"><a href="/account?id={{author.id}}" ><label ng-bind="author.nick_name"></label>的专栏</a></h3>
            <ul  class="fly-panel-main">
              <div class="thumbnail">
                  <img ng-src="{{author.head_image}}" alt="...">
                  <div class="caption">
                    <p ng-bind="author.summary || '这家伙什么都没留下~'"></p>
                  </div>
              </div>
           </ul>
        </div>
       </c:if>
    </div>
  </div>
<!-- 内容部分 end-->
<script src="/statics/js/article/main.js" type="text/javascript"></script>
</body>
</html>
