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
   .container{
      width:970px;
      padding-left: 0px;
   }
   .fly-column>.nav-pills>.current {
      border-bottom:solid #669999 1px;
   }
 </style>
<link href="/statics/css/waterfall.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<!-- 内容部分 begin-->
<div class="fly-panel">
    <div class="layui-container">
    <div class="fly-column">
         <ul class="nav nav-pills">
             <li role="presentation" <c:if test="${type=='1'}">class="current"</c:if>><a href="/f?channel=article&type=1">随笔</a></li>
             <li role="presentation" <c:if test="${type=='2'}">class="current"</c:if>><a href="/f?channel=article&type=2">诗词</a></li>
             <li role="presentation" <c:if test="${type=='3'}">class="current"</c:if>><a href="/f?channel=article&type=3">散文</a></li>
             <li role="presentation" <c:if test="${type=='4'}">class="current"</c:if>><a href="/f?channel=article&type=4">小说</a></li>
         </ul>
    </div>
    </div>
</div>
<div class="layui-container">
 <div id="waterfall"> </div>
</div>
<customscript>
<script type="text/javascript">
layui.use(['fly','waterfall'],function(){
   var $ = layui.jquery;
   var WaterFall = layui.waterfall;
   var waterfall = WaterFall({
      el:'waterfall',
      colWidth:220,
      colNum:5,
      cellHtml:function(data){
         var image = '';
         if(data.image) {
             image = '<a href="/article?id='+data.id+'" title="'+data.title+'" target="_self">'+
                        '<img src="'+data.image+'" alt="'+data.summary+'" style="min-height:200px;">'+
                     '</a>';
         }
         var li = '<li>'+
        				 image +
        				 '<h3>'+
        					'<a href="/article?id='+data.id+'" title="'+data.title+'" target="_self">'+data.title+'</a>'+
        				 '</h3>'+
        				 '<h5>'+data.summary+'<em>[<a  href="/article?id='+data.id+'" target="_self"  style="color:#ff0000;">详细</a>]</em>'+
        				 '</h5>'+
        				 '<h6><span class="layui-badge layui-bg-gray">来源：'+((data.source==null || data.source=='')?data.nick_name:data.source)+' </span></h6>'+
        				 '<h6><span class="layui-badge layui-bg-blue">'+data.sdate+'</span><span style="float:right;color:#FF5722;"><i class="iconfont icon-zan1"></i>'+data.greats+'</span></h6>'
        			  '</li>';
         return li;
      }
     });
     var page = 0;
     var finish  = false;
     function loadMore(api) {
          waterfall.more(api,function(resp){
              var array =  resp.data.content || [];
              var len = resp.data.content.length || 0;
              for(var k=0;k< len ;k++) {
               var data = array[k];
               waterfall.append(data);
              }
             finish = resp.data.last;
          });
     };
     loadMore('/v0.1/article/bytype?offset='+(page++)+'&limit=50&orderby=createTime&direction=desc&type=<c:out value="${type}"/>');
      $(window).scroll(function () {
         var $currentWindow = $(window);
         //当前窗口的高度
         var windowHeight = $currentWindow.height();
         console.log("current widow height is " + windowHeight);
         //当前滚动条从上往下滚动的距离
         var scrollTop = $currentWindow.scrollTop();
         console.log("current scrollOffset is " + scrollTop);
         //当前文档的高度
         var docHeight = $(document).height();
         console.log("current docHeight is " + docHeight);

         //当 滚动条距底部的距离 + 滚动条滚动的距离 >= 文档的高度 - 窗口的高度
         //换句话说：（滚动条滚动的距离 + 窗口的高度 = 文档的高度）  这个是基本的公式
         if ((scrollTop) >= docHeight - windowHeight) {
            if(!finish){
                  loadMore('/v0.1/article/bytype?offset='+(page++)+'&limit=50&orderby=createTime&direction=desc&type=<c:out value="${type}"/>');
            }
         }
     });
});
</script>
</customscript>
</body>
</html>
