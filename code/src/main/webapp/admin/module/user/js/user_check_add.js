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
		UserCheckAdd.Id = id;
		resizeWH();
	}
	UserCheckAdd.init();
});

var UserCheckAdd = {
	"PageSize": 15,
	"Id": null,
	"init": function () {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			UserCheckAdd.saveData();
		});

		if (UserCheckAdd.Id) {
			UserCheckAdd.search();
		}
	},
	"changeLinkType":function(){
		if($("#tbLinkType").val()==1){ //外部链接
			$("#divLink").show();
			$("#divProject").hide();
		}else{
			$("#divLink").hide();
			$("#divProject").show();
		}
	},
	"search": function () {
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_INFO + UserCheckAdd.Id, {}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				Utils.setFormFieldValue("tbForm", data.rows);
				$("#imageView1").attr("src", data.rows.cardImage1 || '');
				$("#imageView2").attr("src", data.rows.cardImage2 || '');
				$("#imageView3").attr("src", data.rows.cardImage3 || '');
				// $("#imageView4").attr("src", data.rows.cardImage4 || '');
			}
		});
	},
	"saveData": function () {
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		if (UserCheckAdd.Id) {
			query.id = UserCheckAdd.Id;
		}
		query.checkStatus = $("#checkStatus").val();
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.USER_CHECK, query, function (data) {
			UIUtils.loaded();
			if (parent && parent.UserCheckManage) {
				parent.UserCheckManage.search();
				parent.UIUtils.closeWindow();
			}
		});
	}
};