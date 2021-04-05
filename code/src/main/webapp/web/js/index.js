$(document).ready(function () {
    Index.init();
});
var Index = {
    "init": function () {
        IndexAdvert.init();
        IndexNotice.init();
        OfficialNotice.init();
        LatestActive.init();
        this.initReg();
        this.initAdvertActive();
    },
    "initReg": function () {//推荐的链接
        var reg = Utils.getUrlParam("reg");
        if(reg){
            DataUtils.getUserIsLogin(function(isLogin){
                if(!isLogin){
                    $("#btn_reg").attr("href", "reg.html?reg=" + reg);
                    $("#btn_reg").click();
                }
            });
        }
    },
    "initAdvertActive": function () {
        $("#advert_active").find(".title-item").on('click', function () {
            if (!$(this).hasClass("active-item")) {
                var bodyid = $(this).attr("data-id");
                $("#advert_active").find(".title-item").removeClass("active-item");
                $(this).addClass("active-item");
                $("#advert_active").find(".tab-wrap").hide();
                $("#" + bodyid).show();
            }
        });
    },
    "getLinkHref": function (advert) {
        var href = "";
        if (advert.linkType == 1) {
            href = "projectinfo.html?id=" + advert.linkProject;
        } else if (advert.linkType == 2) {
            href = advert.linkUrl;
        } else {
            href = "advertinfo.html?id=" + advert.id;
        }
        return href;
    }
};
//轮播
var IndexAdvert = {
    "init": function () {
        this.getData(function (data) {
            if (data && data.length > 0) {
                $(".layui-carousel-body").empty();
            }
            $.each(data, function (i, item) {
                var href = Index.getLinkHref(item);
                var html = '<div class="layui-carousel-item" href="' + href + '" style="background-image:url(' + item.image + ')"></div>';
                $(".layui-carousel-body").append(html);
            });
            layui.use(['element', 'carousel'], function () {
                var element = layui.element, carousel = layui.carousel;
                //图片轮播
                carousel.render({
                    elem: '#advert_index'
                    , width: '100%'
                    , height: '700px'
                    , interval: 3000
                });
            });
            $(".layui-carousel-item").on('click', function () {
                var href = $(this).attr('href');
                if (href) {
                    window.open(href);
                }
            });
        });
    },
    "getData": function (callback) {
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_INDEX, {}, function (data) {
            if (callback) {
                callback(data.rows || []);
            }
        });
    }
};

//通知
var IndexNotice = {
    "init": function () {
        this.getData(function (data) {
            $.each(data, function (i, item) {
                var href = Index.getLinkHref(item);
                var html = '<a class="btn-view" href="' + href + '" target="_blank">' + item.title + '</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                $("#advert_notice .body").append(html);
            });

            $('#advert_notice .body').marquee({
                speed: 100,
                direction: 'left',
                pauseOnHover: true
            });
        });
    },
    "getData": function (callback) {
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_NOTICE, {}, function (data) {
            if (callback) {
                callback(data.rows || []);
            }
        });
    }
};

//官方公告
var OfficialNotice = {
    "init": function () {
        this.getData(function (data) {
            $.each(data, function (i, item) {
                var href = Index.getLinkHref(item);
                var html = '<a href="' + href + '" target="_blank" class="process-item">' +
                    '<div class="bd-gray-color process-time">' + OfficialNotice.getDateTime(item.createTime) + '</div>' +
                    '<div class="process-title">' + item.title + '</div>' +
                    '<div class="bd-gray-color process-item-des">' + item.description + '</div>' +
                    '</a>';
                if (i < 2) {
                    $("#announcementDes .process-desc-1").append(html);
                } else {
                    $("#announcementDes .process-desc-2").append(html);
                }
            });
        });
    },
    "getData": function (callback) {
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_OFFICIAL_NOTICE, {}, function (data) {
            if (callback) {
                callback(data.rows || []);
            }
        });
    },
    "getDateTime": function (d) {
        var date = new Date(d);
        return date.getMonth()+1 + "/" + date.getDate();
    }
};

//最新活动
var LatestActive = {
    "init": function () {
        this.getData(function (data) {
            $.each(data, function (i, item) {
                var href = Index.getLinkHref(item);
                var html = '<a href="' + href + '" target="_blank" class="process-item">' +
                    '<div class="bd-gray-color process-time">' + OfficialNotice.getDateTime(item.createTime) + '</div>' +
                    '<div class="process-title">' + item.title + '</div>' +
                    '<div class="bd-gray-color process-item-des">' + item.description + '</div>' +
                    '</a>';
                if (i < 2) {
                    $("#activeDes .process-desc-1").append(html);
                } else {
                    $("#activeDes .process-desc-2").append(html);
                }
            });
        });
    },
    "getData": function (callback) {
        HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_LATEST_ACTIVE, {}, function (data) {
            if (callback) {
                callback(data.rows || []);
            }
        });
    },
    "getDateTime": function (d) {
        var date = new Date(d);
        return date.getMonth()+1 + "/" + date.getDate();
    }
};
