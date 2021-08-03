$(function () {
	initControl();
	initTable();
});




function initControl(){



}

function initTable(){
	var params = initGridParams('','robot/list',true);
	params.colModel = [
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '机器人编号', name: 'robotSn', index: 'robot_sn', width: 80 },
			{ label: '机器人类型', name: 'robotType', index: 'robot_type', width: 80 },
			{ label: '所在编号', name: 'liveSn', index: 'live_sn', width: 80 },
			{ label: '机器人信息', name: 'robotInfo', index: 'robot_info', width: 80 },
			{ label: '备注', name: 'remark', index: 'remark', width: 80 },
			{ label: '其他', name: 'other', index: 'other', width: 80 },
			{ label: '在线状态', name: 'online', index: 'online', width: 80 },
		{ label: '绑定用户', name: 'username', index: 'username', width: 80 },
			{ label:"操作",name:"id",index:"id",formatter(value,options,row){
                return [
                    "<button type='button' class='btn btn-success btn-xs' onclick='vm.update(" + row.id + ")'>修改</button>",
                    "<button type='button' class='btn btn-danger btn-xs' onclick='vm.del(" + row.id + ")'>删除</button>"
                ].join(" ");
            }}
	];
	$("#jqGrid").jqGrid(params);
}


var vm = new Vue({
	el:'#rrapp',

	data:{
		showList: true,
		title: null,
		search: {},
		robot: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		resetSearch: function () {
			vm.search = {};
			var postData = $('#jqGrid').jqGrid("getGridParam", "postData");
			$.each(postData, function (k, v) {
			     delete postData[k];
			});
		},
		add: function(){
			vm.title = "新增";
			vm.robot = {};
			$('#modal').modal('show');
		},
		updateEvent: function (event) {
			var id = getSelectedRow();
            vm.update(id)
		},
		update: function (id) {
			if(id == null){
				return ;
			}
            vm.title = "修改";
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			if (!validateDataRobot(vm.robot, vm.robot.id == null)) {
                return;
            }
			var url = vm.robot.id == null ? "robot/save" : "robot/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.robot),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
							$('#modal').modal('hide');
						});
					}else{
						alert(r.msg);
					}
				}
			});
		}, 
		del: function (id) {
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "robot/delete",
                    contentType: "application/json",
				    data: JSON.stringify([id]),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		delEvent: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			if(ids.length>1){
				alert('暂不支持多条删除');
				return;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "robot/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "robot/info/"+id, function(r){
                vm.robot = r.robot;
				$('#modal').modal('show');
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData: vm.search
            }).trigger("reloadGrid");
		},linkUser:function(){
			if(getSelectedRow()==''){
				alert('请选择一条数据后再点击按钮')
				return;
			}
			$.ajax({
				type: "POST",
				url: baseURL + "robot/bind",
				contentType: "application/json",
				data: JSON.stringify({"id":getSelectedRow()}),
				success: function(r){
					if(r.code == 0){
						alert('操作成功', function(index){
							$("#jqGrid").trigger("reloadGrid");
						});
					}else{
						alert(r.msg);
					}
				}
			});
		}
	}
});

//回车事件响应
$(document).keypress(function(e) {
    var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
     if (eCode == 13){
    	 if($('#modal').is(":hidden")) {
    		 vm.query();
    	 } else {
    		 vm.saveOrUpdate();
    	 }
     }
});

