/**
 * @param id jdGridId
 * @param url 请求url
 * @param multiselect 是否需要多选
 */
function initGridParams(id,url,multiselect) {
	var params =  {
		url: baseURL + url,
		datatype: "json",
		viewrecords: true,
		sortable:false,
		height: 'auto',
		rowNum: 10,
		rowList : [10,30,50],
		rownumbers: false,
		rownumWidth: 25,
		autowidth:true,
		multiselect: multiselect, // 多选显示多选框
		altRows: true, // 是否隔行变色
		altclass:'someClass', // 隔行变色样式
		jsonReader : {
			root: "page.list",
			page: "page.currPage",
			total: "page.totalPage",
			records: "page.totalCount"
		},
		prmNames : {
			page:"page",
			rows:"limit",
			order: "order"
		},
		pager: "#jq"+id+"GridPager",
		gridComplete:function(){
			//隐藏grid底部滚动条
			$("#jq"+id+"Grid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
		}
	}
	return params;
}