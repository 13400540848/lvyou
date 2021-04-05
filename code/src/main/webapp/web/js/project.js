
$(document).ready(function() {
    // $('#header_wrapper').scrollToFixed();
    Project.init();
    $("#filters").find("a").bind('click', function(){
        $("#filters").find("a").removeClass("active");
        $(this).addClass("active");
        Project.swapType($(this).attr("data-filter"));
    });
});

//项目
var Project = {
    "init":function(){
        this.getData(function(data){
            if(data && data.length>0){
                console.log(data);
                $(".content-grids").empty();
            }
            $.each(data, function(i, item){
                var html = '<div class="grid" type="'+item.timeType+'">'+
                    '<img src="'+item.image+'" title="image-name">'+
                    '<h3>'+item.title+'</h3>'+
                    '<p class="info">'+item.description+'</p>'+
                    '<p class="time">'+
                        '<span class="start-time">开始时间：'+FormatUtils.parseTimestampToDate(item.startTime)+'</span>'+
                        '<span class="end-time">结束时间：'+FormatUtils.parseTimestampToDate(item.endTime)+'</span>'+
                    '</p>'+
                    '<div class="progress-text clearfix">'+
                        '<span>项目进度</span>'+
                        '<strong>'+item.progress+'%</strong>'+
                        '<div class="clear"></div>'+
                    '</div>'+
                    '<div class="progress">'+
                        '<div class="progress-bar" style="width: '+item.progress+'%"></div>'+
                    '</div>'+
                    '<a class="button" target="_blank" href="projectinfo.html?id='+item.id+'">查看</a>'+
                '</div>';
                $(".content-grids").append(html);
            });
        });
    },
    "getData":function(callback){
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_LIST, {}, function(data){
            if(callback){
                callback(data.rows || []);
            }
        });
    },
    "swapType":function(type){
        if(type=="" || type=="*"){
            $(".content-grids").find(".grid").show();
        }else{
            $.each($(".content-grids").find(".grid"), function(i, item){
                if($(item).attr("type") == type){
                    $(item).show();
                }else{
                    $(item).hide();
                }
            });
        }
    }
};