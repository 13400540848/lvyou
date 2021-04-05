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
 <script>
   window.User = {user_name:'<c:out value="${user.userName}"/>',
                  user_id:'<c:out value="${user.id}"/>',
                  user_nickname:'<c:out value="${user.nickName}"/>',
                  head_image:'<c:out value="${user.headImage}"/>'};
   window.$FileServer = '<%=path%>';
 </script>
<script src="/statics/js/jquery.js" type="text/javascript"></script>
<script src="/statics/js/angular.js" type="text/javascript"></script>
<link href="/statics/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/statics/umeditor/third-party/jquery.min.js"></script>
<script type="text/javascript" src="/statics/umeditor/third-party/template.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/statics/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/statics/umeditor/umeditor.js"></script>
<script type="text/javascript" src="/statics/umeditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/statics/js/angular-file-upload.js"></script>
<script type="text/javascript" src="/statics/js/es5-sham.min.js"></script>
 <style>
   .container{
      width:970px;
      padding-left: 0px;
   }
   .site-doc-icon .layui-anim{height: 250px; line-height: 250px; margin: 0 auto 10px; text-align: center; background-color: #fff; cursor: pointer; color: #333; }
   .layui-form select {
         display: block;
         width: 100%;
         padding-left: 10px;
   }
 </style>
</head>
<body>
  <div  ng-controller="MainCtrl" ng-cloak>
      <div class="layui-row">
          <ul class="layui-nav layui-bg-green" lay-filter="">
             <li class="layui-nav-item">
              <a href="/articles">返回首页</a>
             </li>
          </ul>
          <div style="position:absolute;right:10px;top:5px;">
              <label style="color:#ff0000;">{{$error}}</label>
              <button class="layui-btn layui-btn-normal" ng-click="publish()">发布</button>
          </div>
      </div>
    <div class="layui-row" style="margin-top:10px;">
       <input type="file" nv-file-select  uploader="uploader" class="btn btn-primary uploader" style="display:none;"/>
       <div class="layui-col-sm4" style="height: 250px;  line-height: 250px;  border-radius: 6px;background:#fff; cursor:pointer;" ng-click="toggleUploader()">
         <div class="site-doc-icon"  ng-if="uploader.success_list.length==0">
            <div class="layui-anim" data-anim="layui-anim-up">封面</div>
         </div>
         <div class="center-block" style="height:250px;line-height: 250px;" ng-if="uploader.success_list.length > 0" ng-repeat="item in uploader.success_list">
            <img style="display:block;max-width:100%;height:auto" ng-src="{{item.remote_url}}"/>
         </div>
       </div>
       <div class="layui-col-sm8">
           <form class="layui-form">
             <div class="layui-form-item layui-row">
             <div class="layui-col-md6">
               <label class="layui-form-label">分类</label>
               <div class="layui-input-block">
                 <select class="layui-select" ng-model="article.type" ng-options="type.value as type.name for type in types"></select>
               </div>
             </div>
             <div class="layui-col-md6">
                <label class="layui-form-label">来源</label>
               <div class="layui-input-block">
                 <input type="text" class="layui-input" ng-model="article.source" placeholder="如果不是自己原创，请标注来源">
               </div>
             </div>

             </div>
             <div class="layui-form-item">
                <label  class="layui-form-label">标签</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input" ng-model="article.tags" placeholder="请使用逗号分隔">
                </div>
             </div>
             <div class="layui-form-item">
                <label  class="layui-form-label">摘要</label>
                <div class="layui-input-block">
                  <textarea  class="layui-textarea" rows="2" ng-model="article.summary" placeholder="请120字以内的文章摘要"></textarea>
                </div>
             </div>
             <div class="layui-form-item">
                 <label  class="layui-form-label">标题</label>
                 <div class="layui-input-block">
                    <input type="text" class="layui-input" ng-model="article.title"  placeholder="输入标题">
                 </div>
              </div>
           </form>
       </div>
    </div>
    <div class="layui-row">
       <!--style给定宽度可以影响编辑器的最终宽度-->
       <div id="myEditor" style="width:100%;min-height:280px;"></div>
     </div>
  </div>
 <script src="/statics/js/article/indite.js" type="text/javascript"></script>
</body>
</html>