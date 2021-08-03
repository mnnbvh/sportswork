//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//请求前缀
var baseURL = "/";
var userURL =  '/';

//登录token
var token = localStorage.getItem("token");
if(token == 'null'){
    parent.location.href = baseURL + 'login.html';
}

//jquery全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false,
    headers: {
        "token": token
    },
    xhrFields: {
	    withCredentials: true
    },
    complete: function(xhr) {
        //token过期，则跳转到登录页面
        // if(xhr.responseJSON.code == 401){
        //     parent.location.href = baseURL + 'login.html';
        // }
    }
});

//jqgrid全局配置
$.extend($.jgrid.defaults, {
    ajaxGridOptions : {
        headers: {
            "token": token
        }
    }
});

//权限判断
function hasPermission(permission) {
    if (window.parent.permissions.indexOf(permission) > -1) {
        return true;
    } else {
        return false;
    }
}
//iframe中权限判断
function hasPermissionFrame(permission) {
    if (window.parent.parent.permissions.indexOf(permission) > -1) {
        return true;
    } else {
        return false;
    }
}

//重写alert
window.alert = function(msg, callback){
	layer.alert(msg, function(index){
		layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

window.loading = function(msg){
    if(msg == null || msg == ''){
        msg = "加载中...";
    }
	layer.msg(msg, {
	  icon: 16
	  ,shade: 0.01
	  ,time:10000000
	});
}
window.closeLoading = function(){
	layer.closeAll('dialog');
}
//重写confirm式样框
window.confirm = function(msg, callback,cancel){
	layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	},function(){//取消事件
		if(cancel != null && typeof(cancel) === "function"){
			cancel("cancel");
		}
	});
}

//选择一条记录
function getSelectedRow(id) {
    if(id == undefined || id == ''){
        id = 'jqGrid';
    }
    var grid = $("#"+id);
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    
    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows(id) {
    if(id == undefined || id == ''){
        id = 'jqGrid';
    }
    var grid = $("#"+id);
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    return grid.getGridParam("selarrrow");
}
//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

function isNull(value){
    if(value == undefined)
        return true;
    if(value == "undefined")
        return true;
    if(value == "null")
        return true;
    if(value == null)
        return true;
    return false;
}
function initUploadSetting(paramsName,filePathId,imgPath){
    var uploadSetting = {
        'progressData' : 'percentage',
        'removeCompleted': true,
        'auto':true,
        'buttonCursor':'hand',
        'buttonText': '附件',
        'fileSizeLimit' : '50MB',
        'fileObjName' : paramsName,
        'uploadScript' : baseURL + 'file/picUpload?filename='+paramsName,
        'onUploadComplete' : function(file, data) {
            var jsonObj = JSON.parse(data);
            var code = jsonObj.code;
            var msg = jsonObj.msg;
            if(code == 0){
                console.log('上传成功');
                var filePath = jsonObj.filePath;
                $("#"+filePathId).val(filePath);
                if(imgPath != null){
                    $("#"+imgPath).attr('src',jsonObj.fileHost);
                }
            }else{
                alert(msg);
            }
        }
    };
    return uploadSetting
}