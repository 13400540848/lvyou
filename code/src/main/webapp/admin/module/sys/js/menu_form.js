$(function () {
	var id = Utils.getUrlParam("id");
	if (id) {
		MenuForm.Id = id;
	}
	 DataUtils.getDictItems(MenuForm.DictKeys, function(items){
		 MenuForm.DictItemList = items;
		 FormUtils.setFormFieldDict("tbForm", items);
		 MenuForm.initNavs(function(){
			MenuForm.init();
		});
    });
});

var MenuForm = {
	"DictKeys":["MENU_TYPE", "YES_NO","MENU_OPEN_MODE"],
	"DictItemList":null,
	"FormData":null,
	"Id": null,
	"init": function (callback) {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			MenuForm.saveData();
		});
		$("#btnIcon").click(function () {
			MenuForm.openIcon();
		});
		

		if (MenuForm.Id) {
			UIUtils.loading('正在查询请稍候......');
			HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.MENU_INFO + MenuForm.Id, {}, function (data) {
				UIUtils.loaded();
				if (data && data.rows) {
					FormUtils.setFormFieldValue("tbForm", data.rows);
					MenuForm.FormData = data.rows;
//					$("#typeId").attr("readonly", "readonly");
//					$("#imageView").attr("src", data.rows.qrCode || '');
//					$("#tbImage").attr("src", data.rows.qrCode || '');
				}
			});			
		}
	},
	"initNavs":function(callback){
		DataUtils.getNavList(function(data){
			$("#tbPid").append("<option value='0' selected='selected'>——无——</option>");
			for(var i=0;i<data.length;i++){
				$("#tbPid").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
			if(callback){
				callback(data);
			}
		});
	},
	"saveData": function () {
		if(!FormUtils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = FormUtils.getFormFieldValue("tbForm", MenuForm.FormData);
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.MENU_SAVE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				if (parent && parent.MenuManage) {
					parent.MenuManage.search();
					parent.UIUtils.closeWindow();
				}
			}
		});
	},
	"openIcon" : function(){
		var t  = new Date().getTime();
		var url = "select_icon.html?t=" + t;
		if(MenuForm.FormData){
			url += "&icon=" + MenuForm.FormData.icon;
		}
		UIUtils.openWindow("选择图标", url, 500, 320);
	}
};

var selectIcon = function(value){
	$("#tbIcon").val(value);
	//MenuForm.FormData.icon = value;
}