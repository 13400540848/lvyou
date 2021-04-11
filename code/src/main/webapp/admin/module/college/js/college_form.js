$(function () {
	var id = Utils.getUrlParam("id");
	if (id) {
		CollegeForm.Id = id;
	}
	 DataUtils.getDictItems(CollegeForm.DictKeys, function(items){
		 CollegeForm.DictItemList = items;
		 FormUtils.setFormFieldDict("tbForm", items);
		 CollegeForm.initSchools(function(){
			 CollegeForm.init();
		 });		
    });
});

var CollegeForm = {
	"PageSize": 15,
	"DictKeys":["NORMAL_STATUS"],
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
			CollegeForm.saveData();
		});

		if (CollegeForm.Id) {
			UIUtils.loading('正在查询请稍候......');
			HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.COLLEGE_INFO + CollegeForm.Id, {}, function (data) {
				UIUtils.loaded();
				if (data && data.rows) {
					FormUtils.setFormFieldValue("tbForm", data.rows);
					CollegeForm.FormData = data.rows;
				}
			});			
		}
	},
	"initSchools":function(callback){
		DataUtils.getSchoolList(function(data){
			$("#tbSchoolId").append("<option value='0' selected='selected'>——无——</option>");
			for(var i=0;i<data.length;i++){
				$("#tbSchoolId").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
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
		var query = FormUtils.getFormFieldValue("tbForm", CollegeForm.FormData);
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.COLLEGE_SAVE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				UIUtils.tip("保存成功", function(){
					if (parent && parent.CollegeManage) {
						parent.CollegeManage.search();
						parent.UIUtils.closeWindow();
					}
				});				
			}
		});
	}
};