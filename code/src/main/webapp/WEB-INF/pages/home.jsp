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
<link rel="stylesheet" href="/statics/css/home.css?t=1513292531245" charset="utf-8" />
</head>
<body>
<div class="layui-container">
<!--
<div class="layui-row  layui-col-space15" style="margin-top:5px;">
   <div class="layui-col-md12">
        娃儿
   </div>
</div>
-->
<div class="layui-row  layui-col-space15" style="margin-top:5px;">
   <div class="layui-col-md4">
	<div class="layui-carousel" id="test1">
      <div carousel-item>
       <c:forEach items="${datas[0].data}" var="artice" begin="0" end="2">
          <div>${artice.title}</div>
       </c:forEach>
      </div>
    </div>
    </div>
    <div class="layui-col-md8">
  <div class="fly-panel">
	  <h3 class="fly-panel-title">神来之笔</h3>
     		<div class="fly-panel-main tuijian-wz">
     			<div class="tuijian-wz-left">
     				<div class="tu">
     				 <c:forEach items="${datas[0].data}" var="artice" begin="3" end="3">
                        <div class="left">
							<a href="/article?id=${artice.id}" title="${artice.title}">
								<img src="${artice.image}" alt="${artice.title}">
							</a>
						</div>
						<div class="right">
							<h2>
								<a href="/article?id=${artice.id}" title="${artice.title}">
								  ${artice.title}
								</a>
							</h2>
							<p class="n-wrap"> ${artice.summary}</p>
						</div>
                     </c:forEach>
     				</div>
                    <div class="tuijian-wz-lb">
                       <ul>
                       <c:forEach items="${datas[0].data}" var="artice" begin="4" end="6">
                        <li> <a href="/article?id=${artice.id}" title="${artice.title}"> <i class="bd"></i><label class="no-wrap title1"> ${artice.title}</label>  <span class="time no-wrap">${artice.sdate}</span> </a> </li>
                       </c:forEach>
                      </ul>
                    </div>
     			</div>

     			<div class="tuijian-wz-right">
                     				<div class="tu">
									 <c:forEach items="${datas[0].data}" var="artice" begin="7" end="7">
										<div class="left">
											<a href="/article?id=${artice.id}" title="${artice.title}">
												<img src="${artice.image}" alt="${artice.title}">
											</a>
										</div>
										<div class="right">
											<h2>
												<a href="/article?id=${artice.id}" title="${artice.title}">
												  ${artice.title}
												</a>
											</h2>
											<p class="n-wrap"> ${artice.summary}</p>
										</div>
									 </c:forEach>
                     				</div>
                                    <div class="tuijian-wz-lb">
                                       <ul>
										   <c:forEach items="${datas[0].data}" var="artice" begin="8" end="10">
										      <li> <a href="/article?id=${artice.id}" title="${artice.title}"> <i class="bd"></i><label class="no-wrap title1"> ${artice.title}</label>  <span class="time no-wrap">${artice.sdate}</span> </a> </li>
										   </c:forEach>
									   </ul>
                                    </div>
                     			</div>
                     			<div style="clear:both;" alt="80*640">
                                     		    sdfsd
                                     		</div>
     		</div>

     		</div>
     </div>
</div>
<div class="layui-row  layui-col-space15" style="margin-top:5px;">
   <div class="layui-col-md12">
        <img src="/statics/sample/app.png" width="100%"/>
   </div>
</div>

<div class="layui-row layui-col-space15" style="margin-top:5px;">
<div class="layui-col-md4">
  <div class="fly-panel">
	  <h3 class="fly-panel-title">新书推荐</h3>
	  <ul  class="fly-panel-main fly-list-static-h320 fly-list-static-tu">
	   <li>
			<div class="tu">
				<div class="left">
					<a href="/yuanma/wangzhan/html/2670.html" title="QQ娱乐网网站源码织梦程序仿小刀娱乐 原创">
						<img src="/uploads/170131/1-1F131235635539.jpg" alt="QQ娱乐网网站源码织梦程序仿小刀 原创">
					</a>
				</div>
				<div class="right">
					<h2>
						<a href="/yuanma/wangzhan/html/2670.html" title="QQ娱乐网网站源码织梦程序仿小刀娱乐网小 原创">
						QQ娱乐网网站源码织
						</a>
					</h2>
					<p>首页展示 列表页 内容页 此网站源码为七颜仿作...</p>
				</div>
			</div>
	   </li>
	   <li>
       			<div class="tu">
       				<div class="left">
       					<a href="/yuanma/wangzhan/html/2670.html" title="QQ娱乐网网站源码织梦程序仿小刀娱乐 原创">
       						<img src="/uploads/170131/1-1F131235635539.jpg" alt="QQ娱乐网网站源码织梦程序仿小刀 原创">
       					</a>
       				</div>
       				<div class="right">
       					<h2>
       						<a href="/yuanma/wangzhan/html/2670.html" title="QQ娱乐网网站源码织梦程序仿小刀娱乐网小 原创">
       						QQ娱乐网网站源码织
       						</a>
       					</h2>
       					<p>首页展示 列表页 内容页 此网站源码为七颜仿作...</p>
       				</div>
       			</div>
       	   </li>
       	   <li>
           			<div class="tu">
           				<div class="left">
           					<a href="/yuanma/wangzhan/html/2670.html" title="QQ娱乐网网站源码织梦程序仿小刀娱乐 原创">
           						<img src="/uploads/170131/1-1F131235635539.jpg" alt="QQ娱乐网网站源码织梦程序仿小刀 原创">
           					</a>
           				</div>
           				<div class="right">
           					<h2>
           						<a href="/yuanma/wangzhan/html/2670.html" title="QQ娱乐网网站源码织梦程序仿小刀娱乐网小 原创">
           						QQ娱乐网网站源码织
           						</a>
           					</h2>
           					<p>首页展示 列表页 内容页 此网站源码为七颜仿作...</p>
           				</div>
           			</div>
           	   </li>
           	   <li>
               			<div class="tu">
               				<div class="left">
               					<a href="/yuanma/wangzhan/html/2670.html" title="QQ娱乐网网站源码织梦程序仿小刀娱乐 原创">
               						<img src="/uploads/170131/1-1F131235635539.jpg" alt="QQ娱乐网网站源码织梦程序仿小刀 原创">
               					</a>
               				</div>
               				<div class="right">
               					<h2>
               						<a href="/yuanma/wangzhan/html/2670.html" title="QQ娱乐网网站源码织梦程序仿小刀娱乐网小 原创">
               						QQ娱乐网网站源码织
               						</a>
               					</h2>
               					<p>首页展示 列表页 内容页 此网站源码为七颜仿作...</p>
               				</div>
               			</div>
               	   </li>
	 </ul>
 </div>
</div>
<div class="layui-col-md8">
  <div class="fly-panel">
	  <h3 class="fly-panel-title">最新上架</h3>
	  <ul  class="fly-panel-main fly-list-static-h320 list clearfix">
                                                     <li>
                         <a href="/novel/MtjYExezLnG8Pj.html?su=516&amp;seq=1" title="山里汉的小农妻" class="frame">
                             <span class="book_img"><img class="cover" src="https://p1.ssl.qhimg.com/dm/120_160_100/t010f30481f301fac2f.jpg" onerror="replaceCover(this)"></span>
                             <p class="name no-wrap">山里汉的小农妻</p>
                         </a>
                     </li>
                                                     <li>
                         <a href="/novel/KdnXExezLnO7OT.html?su=516&amp;seq=2" title="国民女神：重生王牌千金" class="frame">
                             <span class="book_img"><img class="cover" src="https://p3.ssl.qhimg.com/dm/120_160_100/t015020c393bb20277f.jpg" onerror="replaceCover(this)"></span>
                             <p class="name no-wrap">国民女神：重生王牌千金</p>
                         </a>
                     </li>
                                                     <li>
                         <a href="/novel/KdbZExeyMHv6PT.html?su=516&amp;seq=3" title="99次心动，情迷首席纪先生" class="frame">
                             <span class="book_img"><img class="cover" src="https://p2.ssl.qhimg.com/dm/120_160_100/t017872021cc2f30dc0.jpg" onerror="replaceCover(this)"></span>
                             <p class="name no-wrap">99次心动，情迷首席纪先生</p>
                         </a>
                     </li>
                                                     <li>
                         <a href="/novel/LdHaExezLnG5Oj.html?su=516&amp;seq=4" title="报告长官：夫人在捉鬼" class="frame">
                             <span class="book_img"><img class="cover" src="https://p2.ssl.qhimg.com/dm/120_160_100/t0138af66354b6cc3a3.jpg" onerror="replaceCover(this)"></span>
                             <p class="name no-wrap">报告长官：夫人在捉鬼</p>
                         </a>
                     </li>
                                                     <li>
                         <a href="/novel/LAPYExeyLn7DOj.html?su=516&amp;seq=5" title="重生七零美好生活" class="frame">
                             <span class="book_img"><img class="cover" src="https://p3.ssl.qhimg.com/dm/120_160_100/t015b5cdef723e9a2d2.jpg" onerror="replaceCover(this)"></span>
                             <p class="name no-wrap">重生七零美好生活</p>
                         </a>
                     </li>
                                                     <li>
                         <a href="/novel/LdHXExeyMHC7QD.html?su=516&amp;seq=6" title="春闺密事" class="frame">
                             <span class="book_img"><img class="cover" src="https://p4.ssl.qhimg.com/dm/120_160_100/t01d8a6d8019ef21da2.jpg" onerror="replaceCover(this)"></span>
                             <p class="name no-wrap">春闺密事</p>
                         </a>
                     </li>
                                                     <li>
                         <a href="/novel/LdnbExeyNXK4Pj.html?su=516&amp;seq=7" title="名门暖婚：战神宠娇妻" class="frame">
                             <span class="book_img"><img class="cover" src="https://p4.ssl.qhimg.com/dm/120_160_100/t0101f6ad1957df0f14.jpg" onerror="replaceCover(this)"></span>
                             <p class="name no-wrap">名门暖婚：战神宠娇妻</p>
                         </a>
                     </li>
                                                     <li>
                         <a href="/novel/MNbWExezM3zBOj.html?su=516&amp;seq=8" title="快穿系统：男主别着急！" class="frame">
                             <span class="book_img"><img class="cover" src="https://p4.ssl.qhimg.com/dm/120_160_100/t01265929946fd3b93a.jpg" onerror="replaceCover(this)"></span>
                             <p class="name no-wrap">快穿系统：男主别着急！</p>
                         </a>
                     </li>
	 </ul>
  </div>
</div>
</div>

<!--
 <div class="layui-row  layui-col-space15" style="margin-top:5px;">
    <div class="layui-col-md12">
         <img src="/statics/sample/app.png" width="100%"/>
    </div>
 </div>
 -->
<div class="layui-row layui-col-space15" style="margin-top:5px;">
<div class="layui-col-md12">
  <div class="fly-panel">
	  <h3 class="fly-panel-title">文坛盛事</h3>
	  <ul  class="fly-panel-main fly-list-static-tu">
	   <li>
			<div class="tu">
				<div class="left">
					<a href="/yuanma/wangzhan/html/2670.html" title="QQ娱乐网网站源码织梦程序仿小刀娱乐网小k娱乐网样式 原创">
						<img src="/uploads/170131/1-1F131235635539.jpg" alt="QQ娱乐网网站源码织梦程序仿小刀娱乐网小k娱乐网样式 原创">
					</a>
				</div>
				<div class="right">
					<h2>
						<a href="/yuanma/wangzhan/html/2670.html" title="QQ娱乐网网站源码织梦程序仿小刀娱乐网小k娱乐网样式 原创">
						QQ娱乐网网站源码织梦程序仿小刀
						</a>
					</h2>
					<p>首页展示 列表页 内容页 此网站源码为七颜仿照小刀娱乐网以及小k娱乐网制作...</p>
				</div>
			</div>
	   </li>
	   <li><a href="/f?channel=article&type=2">诗词</a></li>
	   <li><a href="/f?channel=article&type=3">散文</a></li>
	   <li><a href="/f?channel=article&type=4">小说</a></li>
	 </ul>
 </div>
</div>

</div>

<script>
layui.use('carousel', function(){
  var carousel = layui.carousel;
  //建造实例
  carousel.render({
    elem: '#test1'
    ,width: '100%' //设置容器宽度
    ,height:300
    ,arrow: 'always' //始终显示箭头
  });
});
</script>
</body>
</html>
