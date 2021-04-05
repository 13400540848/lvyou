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
<style>
 .headTle{
    background:url(/images/flag_new_red_24px_504620_easyicon.net.png) no-repeat;
    margin-top:18px;
  }
  .headTle label{
    margin-left:40px;
    font-size:20px;
  }
  .listBoxT14  .award-first,.award-first{
    background:url(/images/award_gold.png) no-repeat 0 6px;
  }
  .listBoxT14  .award-second,.award-second{
      background:url(/images/award_silver.png) no-repeat 0 6px;
  }
  .listBoxT14 .award-third,.award-third{
        background:url(/images/award_bronze.png) no-repeat 0 6px;
   }
   .listBoxT14 .favorite,.favorite{
           background:url(/images/thumb_up_16px_16611_easyicon.net.png) no-repeat 0 6px;
   }

   ul.danghang  li{
           display: inline-block;
           padding: 6px 12px;
           margin-bottom: 0;
           font-size: 14px;
           font-weight: 400;
           line-height: 1.42857143;
           text-align: center;
           white-space: nowrap;
           vertical-align: middle;
           -ms-touch-action: manipulation;
           touch-action: manipulation;
           cursor: pointer;
           -webkit-user-select: none;
           -moz-user-select: none;
           -ms-user-select: none;
           user-select: none;
           background-image: none;
           border: 1px solid transparent;
           border-radius: 4px;
           color: #fff;
           background-color: #eee;
           border-color: #4cae4c;
           margin:10px;
   }
</style>
</head>
<body>
<!-- 内容部分 begin-->
<div class="layui-container">
        <!-- 左侧列表 begin 最新Tops20文章列表-->
        <div class="layui-row layui-col-space15" style="margin-top:10px;">
        <div class="layui-col-md8">
          <div class="fly-panel">
           <div class="fly-panel-title fly-filter">
           <a>最新发布</a>
           </div>
           <ul class="fly-list">
           <c:forEach items="${datas[0].data.content}" var="article">
               <li> <a href="/account?id=${article.user_id}" class="fly-avatar"> <img src="${article.user_head_image}"> </a>
               <h2>
                 <a  class="layui-badge" href="/f?channel=article&type=<c:out value="${article.type}"/>" target="_self">
                 <c:if test="${article.type==1}">随笔</c:if>
                 <c:if test="${article.type==2}">诗词</c:if>
                 <c:if test="${article.type==3}">散文</c:if>
                 <c:if test="${article.type==4}">小说</c:if>
                </a> <a href="/article?id=<c:out value="${article.id}"/>"><c:out value="${article.title}" escapeXml="false"/></a> </h2>
              <div class="fly-list-info">
                 <cite class="layui-badge layui-bg-gray">来源：${(article.source==null || article.source == '')?article.nick_name:article.source}</cite>
                 <span><c:out value="${article.sdate}"/></span>
              <!-- <span  title="点赞数" ><i class="iconfont icon-zan1"></i><c:out value="${article.greats}"/></span> -->
              <span class="fly-list-nums"> <i class="iconfont icon-pinglun1" title="评论数"></i> ${article.comment_cnt} </span> </div>
              <div class="fly-list-badge"> </div></li>
              </c:forEach>
           </ul>
            </div>
        </div>
        <!-- 左侧列表 begin-->
        <!-- 右侧 begin-->
        <div class="layui-col-md4">
        <!-- 栏目导航 begin-->
         <div class="fly-panel">
                <h3 class="fly-panel-title">栏目导航</h3>
                <ul  class="fly-panel-main fly-list-static danghang">
                 <li><a href="/f?channel=article&type=1">随笔</a></li>
                 <li><a href="/f?channel=article&type=2">诗词</a></li>
                 <li><a href="/f?channel=article&type=3">散文</a></li>
                 <li><a href="/f?channel=article&type=4">小说</a></li>
               </ul>
           </div>
            <!-- 栏目导航  end-->
            <div class="fly-panel">
                <h3 class="fly-panel-title">十大热门主题</h3>
                <ul  class="fly-panel-main fly-list-static">
                  <c:forEach items="${datas[1].data.content}" var="item" varStatus="status">
                    <li>
                    <i class="layui-icon" style='color:<c:choose><c:when test="${status.index==0}">#ff0000</c:when><c:when test="${status.index==1}">#FF33FF</c:when><c:when test="${status.index==2}">#FFCCFF</c:when><c:otherwise>#dddddd</c:otherwise></c:choose>'>&#xe6c6;</i>
                    <a href="/article?id=<c:out value="${item.id}"/>" title="<c:out value="${item.title}"/>"><c:out value="${item.title}" escapeXml="false"/></a></li>
                  </c:forEach>
                </ul>
        </div>
        <!-- 右侧 end-->
        </div>
    </div>
    <!-- 内容第二部分 end-->

</div>
<!-- 内容部分 end-->
</body>
</html>
