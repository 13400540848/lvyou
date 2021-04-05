$(function () {
	var id = Utils.getUrlParam("id");
	if (id) {
		MoneyTypeAdd.Id = id;
	}
	MoneyTypeAdd.init();
});

var MoneyTypeAdd = {
	"PageSize": 15,
	"Id": null,
	"init": function () {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			MoneyTypeAdd.saveData();
		});

		if (MoneyTypeAdd.Id) {
			UIUtils.loading('正在查询请稍候......');
			HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.MONEY_TYPE_INFO + MoneyTypeAdd.Id, {}, function (data) {
				UIUtils.loaded();
				if (data && data.rows) {
					Utils.setFormFieldValue("tbForm", data.rows);
					$("#typeId").attr("readonly", "readonly");
					$("#imageView").attr("src", data.rows.qrCode || '');
					$("#tbImage").attr("src", data.rows.qrCode || '');
				}
			});			
		}

		layui.use('upload', function () {
			var upload = layui.upload;
			//执行实例
			var uploadInst = upload.render({
				elem: '#imageView', //绑定元素
				url: Config.URI.UPLOAD_IMAGE, //上传接口
				done: function (res) {
					$("#imageView").attr("src", res.url);
					$("#tbImage").val(res.url);
				}, 
				error: function (xhr) {
					console.log(xhr);
					alert('上传失败!');
					//请求异常回调
				}
			});
		});
	},
	"saveData": function () {
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		if (MoneyTypeAdd.Id) {
			query.typeId = MoneyTypeAdd.Id;
		}else{
			query.typeId = MoneyTypeAdd.Id;			
		}
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.MONEY_TYPE_SAVE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				if (parent && parent.MoneyTypeManage) {
					parent.MoneyTypeManage.search();
					parent.UIUtils.closeWindow();
				}
			}
		});
	}
};