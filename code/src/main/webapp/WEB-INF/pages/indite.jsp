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
<link rel="stylesheet" href="/statics/css/bootstrap.min.css" />
<script src="/statics/js/jquery.js" type="text/javascript"></script>
<script src="/statics/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/statics/js/angular.js" type="text/javascript"></script>
<link href="/statics/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/statics/umeditor/third-party/jquery.min.js"></script>
<script type="text/javascript" src="/statics/umeditor/third-party/template.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/statics/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/statics/umeditor/umeditor.js"></script>
<script type="text/javascript" src="/statics/umeditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="/statics/js/angular-file-upload.js"></script>
<script type="text/javascript" src="/statics/js/es5-sham.min.js"></script>
<link  href="/statics/css/common/common.css" rel="stylesheet"/>
 <style>
   .container{
      width:970px;
      padding-left: 0px;
   }
 </style>
</head>
<body>

  <div class="container"  ng-controller="MainCtrl" ng-cloak>
  <div class="row" style="margin-top:10px;">
      <ul class="nav navbar-nav">
         <li>
          <a href="/articles">返回首页</a>
         </li>
      </ul>
      <div class="pull-right inline-block">
       <label style="color:#ff0000;">{{$error}}</label><button class="btn btn-primary" style="margin-right:10px;" ng-click="publish()">发布</button>
      </div>
  </div>
    <div class="row">
       <input type="file" nv-file-select  uploader="uploader" class="btn btn-primary uploader" style="display:none;"/>
       <div class="text-center col-sm-4" style="height: 200px;  line-height: 200px;  border-radius: 6px;background:#fff; cursor:pointer;" ng-click="toggleUploader()">
         <span class="glyphicon glyphicon-plus text-center" style="width: 150px; height: 50px;line-height: 50px;" ng-if="uploader.success_list.length==0">
             封面
         </span>
         <div class="center-block" style="height: 200px;line-height: 200px;" ng-if="uploader.success_list.length > 0" ng-repeat="item in uploader.success_list">
            <img style="height:200px;" class="img-responsive center-block" ng-src="{{item.remote_url}}" src="/images/public/loading.gif"/>
         </div>
       </div>
       <div class="col-sm-8">
           <form class="form-horizontal">
             <div class="form-group">
               <label class="col-sm-2 control-label">分类</label>
               <div class="col-sm-10">
                 <select class="form-control" ng-model="article.type" ng-options="type.value as type.name for type in types"></select>
               </div>
             </div>
             <div class="form-group">
                <label  class="col-sm-2 control-label">标签</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" ng-model="article.tags" placeholder="请使用逗号分隔">
                </div>
             </div>
             <div class="form-group">
                <label  class="col-sm-2 control-label">摘要</label>
                <div class="col-sm-10">
                  <textarea class="form-control" rows="2" ng-model="article.summary" placeholder="请120字以内的文章摘要"></textarea>
                </div>
             </div>
             <div class="form-group">
                 <label  class="col-sm-2 control-label">标题</label>
                 <div class="col-sm-10">
                    <input type="text" class="form-control" ng-model="article.title"  placeholder="输入标题">
                 </div>
              </div>
           </form>
       </div>
    </div>
    <div class="row">
       <!--style给定宽度可以影响编辑器的最终宽度-->
       <div id="myEditor" style="width:100%;height:500px;"></div>
     </div>
  </div>
 <script src="/statics/js/article/indite.js" type="text/javascript"></script>
</body>
</html>
