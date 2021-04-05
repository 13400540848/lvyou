$(function () {
	
	IndexManage.init(function(){
		
	});
	
	$("#tbuser_name").bind('click', function(){
		var t  = new Date().getTime();
		var url = "module/sys/my_info.html?t=" + t;
		UIUtils.openWindow("我的资料", url, 800, 520);
		//$("#iframe").attr("src", "module/sys/my_info.html?t=" + t);
	});
	
	$('.main-nav').on('click', 'li', function () {
		if($(this).hasClass('current')) return;
        $(this).siblings().removeClass('current').end().addClass('current');
        //$('#iframe').attr('src', $(this).attr('data-src'));
        IndexManage.loadMenu($(this).attr('data-index'));
    });
	
	$(".nav").on("click","li",function(){
		$(this).siblings().removeClass("current");
		var hasChild = !!$(this).find(".subnav").size();
		if(hasChild){
			$(this).toggleClass("hasChild");
		}
		$(this).addClass("current");
	});


	$(window).resize(function(e) {
//	    $("#bd").height($(window).height() - 80);
//		$(".wrap").height($("#bd").height()-6);
//		$(".nav").css("minHeight", $(".sidebar").height() - $(".sidebar-header").height()-1);
//		$("#iframe").height($(window).height() - 35);
	}).resize();

	$(".nav>li").css({"borderColor":"#dbe9f1"});
	$(".nav>.current").prev().css({"borderColor":"#7ac47f"});
	$(".nav").on("click","li",function(e){
		var aurl = $(this).find("a").attr("data-src");
		$("#iframe").attr("src",aurl);
		$(".nav>li").css({"borderColor":"#dbe9f1"});
		$(".nav>.current").prev().css({"borderColor":"#7ac47f"});
		return false;
	});

    $(window).resize(function (e) {
        $("#bd").height($(window).height() - $("#hd").height() - $("#ft").height() - 6);
        $(".wrap").height($("#bd").height() - 6);
        $(".nav").css("minHeight", $(".sidebar").height() - $(".sidebar-header").height()-1);
        $("#iframe").height($(window).height() - $("#hd").height() - $("#ft").height() - 12);
    }).resize();

    $('.exitDialog').Dialog({
        title: '确认退出系统吗?',
        autoOpen: false,
        isDrag:true,
        fixed:false,
        width: 400,
        height: 200
    });

    $('.exit').click(function () {
        //$('.exitDialog').Dialog({
        //    modal: true
        //});
        $('.exitDialog').Dialog('open');
    });

    $('.exitDialog .sapar-btn').click(function (e) {
        $('.exitDialog').Dialog('close');

        if ($(this).hasClass('ok')) {
            HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.LOGIN_OUT, {}, function (data, success) {
                if(success){
                    Utils.exitSystem();
                }
            });
        }
    });

    
});

var IndexManage = {
		"MenuData" : [],
		"init" : function(callback){
			Utils.getUserInfo(function (data) {
				console.log("用户信息：",  data);
		        $("#tbuser_name").text(data ? data.realName : "");        
		        DataUtils.getUserMenuList(function(data){
		        	IndexManage.MenuData = data;
		        	console.log("用户菜单：", data);
		        	if(data!=null && data.length>0){
			    		$.each(data, function(i, menu){
			    			$("#id_navs").append('<li '+(i==0?'class="current"':'')+' data-index='+i+' data-src="'+menu.url+'"><a href="javascript:;">'+menu.name+'</a></li>');
			    		});
			    		IndexManage.loadMenu(0);
			    		if(callback){
			    			callback();
			    		}
		        	}else{
		        		alert("您暂无权限，请联系管理员配置权限！");
		        	}
		        });
		    });
		},
		 "loadMenu":function(index){
			 var nav = IndexManage.MenuData[index];
			 console.log("加载菜单：", nav.children);
			 $("#id_nav_name").text(nav.name);
			 $("#id_menu_list").empty();
			 if(nav.children){
				 $.each(nav.children, function(i, item){
					 var html = '<li class="'+item.icon+(i==0?' current':'')+'">'+
	                 '<div class="nav-header">'+
	                     '<a href="javascript:;" data-src="'+item.url+'" class="clearfix">'+
	                         '<span>'+item.name+'</span>'+
	                         '<i class="icon"></i>'+
	                     '</a>'+
	                 '</div>'+
	             '</li>';
					 $("#id_menu_list").append(html);
				 });
			 }
		 }
};
