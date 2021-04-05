$(function () {
	var icon = Utils.getUrlParam("icon");
	if (icon) {
		SelectIconForm.Icon = icon;
	}
	SelectIconForm.init();
});

var SelectIconForm = {
	"Icon": null,
	"init": function (callback) {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			SelectIconForm.saveData();
		});

		if (SelectIconForm.Icon) {
			$.each($("#tbForm").find("input[type='radio']"), function(i,item){
	            var value = $(item).attr("value");
	            if(value && value==SelectIconForm.Icon){
	                $(item).prop("checked", "checked");
	            }
	        });
		}
	},
	"saveData": function () {
		var selectValue = $("#tbForm").find("input[type='radio']:checked").val();
		if (parent && parent.selectIcon) {
			parent.selectIcon(selectValue);
			parent.UIUtils.closeWindow();
		}
	}
};