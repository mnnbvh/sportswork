$(function () {
	initControl();
	initTable();
});




function initControl(){



}

function initTable(){
	var params = initGridParams('','user/list',true);
	params.colModel = [
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户名', name: 'username', index: 'username', width: 80 },
			{ label: '密码', name: 'password', index: 'password', width: 80 },
			{ label: '电话', name: 'phone', index: 'phone', width: 80 },
			{ label: '是否删除', name: 'isDel', index: 'is_del', width: 80 },
			{ label: '注册时间', name: 'registerTime', index: 'register_time', width: 80 },
			{ label: '', name: 'roleId', index: 'role_id', width: 80 },
			{ label: '', name: 'createTime', index: 'create_time', width: 80 },
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
		user: {}
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
			vm.user = {};
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
			if (!validateDataUser(vm.user, vm.user.id == null)) {
                return;
            }
			var url = vm.user.id == null ? "user/save" : "user/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.user),
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
				    url: baseURL + "user/delete",
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
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "user/delete",
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
			$.get(baseURL + "user/info/"+id, function(r){
                vm.user = r.user;
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

