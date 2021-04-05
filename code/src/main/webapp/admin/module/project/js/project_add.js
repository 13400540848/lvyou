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
		ProjectAdd.Id = id;
		resizeWH();
	}
	ProjectAdd.init();
});

var ProjectAdd = {
	"PageSize": 15,
	"Id": null,
	"MoneyTypeList": null,
	"ProjectData":{},
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
			ProjectAdd.saveData();
		});
		$("#btnAddType").click(function(){
			ProjectAdd.addType(ProjectAdd.Id);
		});
		this.initType(function(){
			if (ProjectAdd.Id) {
				$('#tableType').bootstrapTable({
					columns: [
						// { field: 'typeId', title: '币种编号', width: 50 },
						{ field: 'typeName', title: '币种名称', width: 50 },
						{ field: 'allMoney', title: '目标数量', width: 50 },
						{ field: 'money', title: '已投资数量', width: 50 },
						{ field: 'min', title: '上限', width: 50 },
						{ field: 'max', title: '下限', width: 50 },
						{ field: 'moneyScale', title: '币种比例', width: 50, formatter: ProjectAdd.formatterScale },
						{ field: 'typeName', title: '操作', width: 50, formatter: ProjectAdd.formatterOp }
					]
				});
				ProjectAdd.search();
			} else {
				$("#tab3").hide();
			}
		});		
	},
	"initType": function (callback) {
		DataUtils.getMoneyTypeList("1", function(data){
			if (data) {
				$.each(data, function(i, item){
					$("#tbMoneyTypeId").append("<option value='"+item.typeId+"'>"+item.typeName+"</option>");
				});	
			}
			if(callback){
				callback();
			}
		});
	},
	"search": function () {
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_INFO + ProjectAdd.Id, {}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				ProjectAdd.ProjectData = data.rows;
				Utils.setFormFieldValue("tbForm", ProjectAdd.ProjectData);
				um.setContent(ProjectAdd.ProjectData.content || '', false);
				$("#imageView").attr("src", ProjectAdd.ProjectData.image || '');
				$("#tbImage").attr("src", ProjectAdd.ProjectData.image || '');
				ProjectAdd.searchMoney();
			}
		});		
		// DataUtils.getMoneyTypeList("0", function(data){
		// 	ProjectAdd.MoneyTypeList = data;
		// 	ProjectAdd.searchMoney();
		// });
	},	
	"searchMoney":function(){
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_MONEY_SEARCH, {project_id:ProjectAdd.Id}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				$('#tableType').bootstrapTable('load', data.rows);
			}
		});			
	},
	"formatterScale": function (value, row, index) {
		return "1" + row.typeName + "=" + value + ProjectAdd.ProjectData.moneyTypeName;
	},
	"formatterOp": function (value, row, index) {
		return '<a href="javascript:ProjectAdd.editType(\'' + row.id + '\');">修改</a>&nbsp;&nbsp;&nbsp;' +
			'<a href="javascript:ProjectAdd.deleteType(\'' + value + '\', \'' + row.id + '\');">删除</a>';
	},
	"saveData": function () {
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		if (ProjectAdd.Id) {
			query.id = ProjectAdd.Id;
		}
		query.content = um.getContent();
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.PROJECT_SAVE, query, function (data) {
			UIUtils.loaded();
			if (parent && parent.UIUtils) {
				parent.ProjectManage.search();
				parent.UIUtils.closeWindow();
			}
		});
	},
	"addType":function(pid){
		var t  = new Date().getTime();
		var url = "project_money_add.html?t=" + t + "&pid=" + pid;
		UIUtils.openWindow("添加币种设置", url, 370, 350);
	},
	"editType":function(id){
		var t  = new Date().getTime();
		var url = "project_money_add.html?t=" + t + "&id=" + id;
		UIUtils.openWindow("修改币种设置", url, 370, 350);
	},
	"deleteType":function(title, id){
		UIUtils.confirm("确定要删除此项目【"+title+"】币种的投资吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.PROJECT_MONEY_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					ProjectAdd.searchMoney();
				}
			});
		});
	},
};