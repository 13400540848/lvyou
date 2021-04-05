$(function () {
	$(window).resize(function () {
		$('#table1').bootstrapTable('resetView');
		$('#table2').bootstrapTable('resetView');
		$('#table3').bootstrapTable('resetView');
		$('#table4').bootstrapTable('resetView');
		resizeWH();
	});

	var resizeWH = function () {
		$(".table-box").height($(window).height() - 100);
	};

	var id = Utils.getUrlParam("id");
	if (id) {
		AdvertAdd.Id = id;
		resizeWH();
	}
	AdvertAdd.init();
});

var AdvertAdd = {
	"PageSize": 15,
	"Id": null,
	"init": function () {

		layui.use('upload', function () {
			var upload = layui.upload;
			//执行实例
			var uploadInst = upload.render({
				elem: '#uploadImage', //绑定元素
				url: Config.URI.UPLOAD_IMAGE, //上传接口
				done: function (res) {
					$("#imageView").attr("src", res.url);
					$("#tbImage").val(res.url);
					//上传完毕回调
				}, 
				error: function (xhr) {
					console.log(xhr);
					alert('上传失败!');
					//请求异常回调
				}
			});
		});

		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			AdvertAdd.saveData();
		});

		AdvertAdd.initLocations(function(){
			AdvertAdd.initStatus(function(data){
				AdvertAdd.initLinkTypes(function(data){
					AdvertAdd.initProjects(function(data){
						if (AdvertAdd.Id) {
							AdvertAdd.search();
						} else {
							AdvertAdd.changeLinkType();
						}
					});
				});
			});
		});
	},
	"initLocations":function(callback){
		DataUtils.getAdvertLocations(function(data){
			for(var i=0;i<data.length;i++){
				$("#tbLocation").append("<option value='"+data[i].value+"'>"+data[i].text+"</option>");
			}
			if(callback){
				callback(data);
			}
		});
	},
	"initStatus":function(callback){
		DataUtils.getAdvertStatus(function(data){
			for(var i=0;i<data.length;i++){
				$("#tbStatus").append("<option value='"+data[i].value+"'>"+data[i].text+"</option>");
			}
			if(callback){
				callback(data);
			}
		});
	},
	"initLinkTypes":function(callback){
		DataUtils.getAdvertLinkTypes(function(data){
			for(var i=0;i<data.length;i++){
				$("#tbLinkType").append("<option value='"+data[i].value+"'>"+data[i].text+"</option>");
			}
			if(callback){
				callback(data);
			}
		});
	},	
	"initProjects":function(callback){
		DataUtils.getAllProjectList(function(list){
			if (list) {
				$.each(list, function(i, item){
					$("#tbProjectId").append("<option value='"+item.id+"'>"+item.title+"</option>");
				});	
			}
			if(callback){
				callback(list);
			}
		});
	},	
	"changeLinkType":function(){
		if($("#tbLinkType").val()==1){ //项目连接
			$("#divLink").hide();
			$("#divProject").show();
		}else if($("#tbLinkType").val()==2){ //外部链接
			$("#divLink").show();
			$("#divProject").hide();
		}else{//内部广告
			$("#divLink").hide();
			$("#divProject").hide();
		}
	},
	"search": function () {
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_INFO + AdvertAdd.Id, {}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				Utils.setFormFieldValue("tbForm", data.rows);
				um.setContent(data.rows.content || '', false);
				$("#imageView").attr("src", data.rows.image || '');
				AdvertAdd.changeLinkType();
			}
		});
	},
	"saveData": function () {
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		if (AdvertAdd.Id) {
			query.id = AdvertAdd.Id;
		}
		query.content = um.getContent();
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.ADVERT_SAVE, query, function (data) {
			UIUtils.loaded();
			if (parent && parent.AdvertManage) {
				parent.AdvertManage.search();
				parent.UIUtils.closeWindow();
			}
		});
	}
};