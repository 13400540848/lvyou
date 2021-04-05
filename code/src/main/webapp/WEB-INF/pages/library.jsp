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
<link href="/statics/css/waterfall.css" rel="stylesheet" type="text/css"/>
<style>
  .flow>ul>li {
      width:100%;
    }
</style>
</head>
<body>
<!-- 内容部分 begin-->
<div class="wrapCon">
    <!-- 内容第二部分 begin-->
    <div class="conSec">
       <div id="waterfall"></div>
    </div>
</div>
<script src="/statics/js/waterfall.js"></script>
<script type="text/javascript">
$(document).ready(function () {
   var waterfall = $.fn.FixedWaterFall({
      el:'waterfall',
      colWidth:150,
      cellHtml:function(data){
         var image = '';
         if(data.image) {
             image = '<a href="/article?id='+data.id+'" title="'+data.title+'" target="_self">'+
                        '<img src="'+data.image+'" alt="'+data.summary+'" style="min-height:200px;">'+
                     '</a>';
         }
         var li = '<li>'+
        				 image +
        			  '</li>';
         return li;
      }
     });
     var page = 0;
     var finish  = false;
     function loadMore(api) {
          waterfall.more(api,function(resp){
              var array =   [{"id":"e659641f-7dc0-4e77-bed9-e61b75dea5ef1","title":"sdfgsdf","type":"1","createTime":1504058497000,"userId":"a181a883-83bb-4e58-9f35-2a9f7fcf4145","userName":"huangyx","summary":"fgssdfg","tags":"sd","source":null,"pvs":0,"greats":2,"content":"<p>sdfgsdfgs</p>","image":"http://localhost:8080/statics/upload/skeletonize/20170830/8861504058493369.jpg","tagList":["sd"],"sdate":"1天前"},
              {"id":"e659641f-7dc0-4e77-bed9-e61b75dea5ef2","title":"sdfgsdf","type":"1","createTime":1504058497000,"userId":"a181a883-83bb-4e58-9f35-2a9f7fcf4145","userName":"huangyx","summary":"fgssdfg","tags":"sd","source":null,"pvs":0,"greats":2,"content":"<p>sdfgsdfgs</p>","image":"http://localhost:8080/statics/upload/skeletonize/20170830/8861504058493369.jpg","tagList":["sd"],"sdate":"1天前"},
              {"id":"e659641f-7dc0-4e77-bed9-e61b75dea5ef3","title":"sdfgsdf","type":"1","createTime":1504058497000,"userId":"a181a883-83bb-4e58-9f35-2a9f7fcf4145","userName":"huangyx","summary":"fgssdfg","tags":"sd","source":null,"pvs":0,"greats":2,"content":"<p>sdfgsdfgs</p>","image":"http://localhost:8080/statics/upload/skeletonize/20170830/8861504058493369.jpg","tagList":["sd"],"sdate":"1天前"},
              {"id":"e659641f-7dc0-4e77-bed9-e61b75dea5ef4","title":"sdfgsdf","type":"1","createTime":1504058497000,"userId":"a181a883-83bb-4e58-9f35-2a9f7fcf4145","userName":"huangyx","summary":"fgssdfg","tags":"sd","source":null,"pvs":0,"greats":2,"content":"<p>sdfgsdfgs</p>","image":"http://localhost:8080/statics/upload/skeletonize/20170830/8861504058493369.jpg","tagList":["sd"],"sdate":"1天前"},
              {"id":"e659641f-7dc0-4e77-bed9-e61b75dea5ef4","title":"sdfgsdf","type":"1","createTime":1504058497000,"userId":"a181a883-83bb-4e58-9f35-2a9f7fcf4145","userName":"huangyx","summary":"fgssdfg","tags":"sd","source":null,"pvs":0,"greats":2,"content":"<p>sdfgsdfgs</p>","image":"http://localhost:8080/statics/upload/skeletonize/20170830/8861504058493369.jpg","tagList":["sd"],"sdate":"1天前"},
              {"id":"e659641f-7dc0-4e77-bed9-e61b75dea5ef4","title":"sdfgsdf","type":"1","createTime":1504058497000,"userId":"a181a883-83bb-4e58-9f35-2a9f7fcf4145","userName":"huangyx","summary":"fgssdfg","tags":"sd","source":null,"pvs":0,"greats":2,"content":"<p>sdfgsdfgs</p>","image":"http://localhost:8080/statics/upload/skeletonize/20170830/8861504058493369.jpg","tagList":["sd"],"sdate":"1天前"}
              ];
              var len = array.length || 0;
              for(var k=0;k< len ;k++) {
               var data = array[k];
               waterfall.append(data);
              }
             finish = resp.data.last;
          });
     };
     loadMore('/v0.1/article/bytype?offset='+(page++)+'&limit=50&orderby=createTime&direction=desc&type=1');
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
</body>
</html>
