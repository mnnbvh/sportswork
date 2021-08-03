

/**通用饼图*/
function initPie(id, list, map, title) {
	let dom = document.getElementById(id);
	//清空之前的图表
	dom.removeAttribute("_echarts_instance_");
	let myChart = echarts.init(dom);
	let data = [];
	for (var i = 0; i < list.length; i++) {
		data.push({
			value:list[i][map.value],
			name:list[i][map.name]
		});
	}
	var option = {
		    title: {
		        text: title,
		        left: 'center'
		    },
		    tooltip: {
		        trigger: 'item',
				//formatter: '{a} <br/>{b} : {c} ({d}%)'
				formatter: function(a) {
					console.log(a);
					return '<i style="display: inline-block;width: 10px;height: 10px;background: ' + a.color 
					+ ';margin-right: 5px;border-radius: 50%;}"></i><span style="color:' + a.color + '">' + a.name
					+ '&nbsp,&nbsp' + a.value 
					+ '&nbsp,&nbsp' + a.percent + '%</span>';
				}
		    },
		    legend: getLegend(data, 4),
		    series: [
		        {
		            name: title,
		            type: 'pie',
		            radius: '50%',
					center: ['24%', '40%'],
		            data: data,
		            emphasis: {
		                itemStyle: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
	
	if (option && typeof option === 'object') {
	    myChart.setOption(option);
	}
}


/**通用多曲线 柱状图*/
function initCharts(id, list, cahrsMap, title, stack) {
	let dom = document.getElementById(id);
	//清空之前的图表
	dom.removeAttribute("_echarts_instance_");
	let myChart = echarts.init(dom);
	let option = getLineOption(list, cahrsMap, title, stack)
	if (option && typeof option === 'object') {
	    myChart.setOption(option);
	}
}


//多曲线  多柱状图 堆叠图（堆叠图需要传stack）
function getLineOption(list, cahrsMap, title, stack) {
	var option = {
		title: {
			text: title,
			left: 'center'
		},
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data: [],
			bottom:10
		},
		grid: {
			left: '3%',
			right: '5%',
			bottom: '15%',
			containLabel: true
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			type: 'category',
			name:cahrsMap.xTitle,
			boundaryGap: false,
			data: []
		},
		yAxis: [{
            type: 'value',
            //设置轴线的属性
            axisLine:{
                show:true,
//                symbol:['none','path://M5,20 L5,5 L8,8 L5,2 L2,8 L5,5 L5.3,6 L5.3,20'], //箭头
//                symbolOffset:15,
//                symbolSize:[30,28]
            }, 
            name:cahrsMap.yTitle,
//            nameGap:33 //位移标题高度
        }],
		series: [
			
		]
	};
	if (list) {
		for (let i = 0; i < list.length;i++) {
			let xAxisName = '';
			for(let j = 0; j < cahrsMap.xname.length; j++) {
				xAxisName += list[i][cahrsMap.xname[j]] + " "
			}
			if (xAxisName == '' || xAxisName == ' ' ) {
				xAxisName = '未知';
			}
			option.xAxis.data.push(xAxisName);
			if(i==0) {
				for(let j = 0; j < cahrsMap.legend.length; j++) {
					option.legend.data.push(cahrsMap.legend[j].name);
					let item = {
						name:cahrsMap.legend[j].name,
						type: cahrsMap.legend[j].type,
						yAxisIndex: cahrsMap.legend[j].yAxisIndex,
						stack: stack,
						data:[]
					}
					if(item.type == 'bar') {
						item.barWidth = '10%';
						option.xAxis.boundaryGap = true;
					}
					option.series.push(item);
					if (item.yAxisIndex == 1 && option.yAxis.length == 1) {
						option.yAxis.push({
							type: 'value'
						})
					}
				}
			}
			for(let j = 0; j < cahrsMap.legend.length; j++) {
				option.series[j].data.push(list[i][cahrsMap.legend[j].attr]);
			}
		}
	}
	if (cahrsMap.xname.indexOf("date") >= 0) { //包含日期，按字符排序
		option.xAxis.data = option.xAxis.data.sort();
	}
	if (cahrsMap.otherAttr) {
		 option.tooltip.formatter = function(params){
		   console.log(params);
		   let index = 0;
		   let html = ""
           for(x in params){
        	   a = params[x];
        	   index = a.dataIndex;
        	   html += '<i style="display: inline-block;width: 10px;height: 10px;background: ' + a.color 
				+ ';margin-right: 5px;border-radius: 50%;}"></i><span style="color:' + a.color + '">' + a.seriesName
				+ '&nbsp&nbsp&nbsp' + a.value + '</span></br>';
           }
		   if (index === 0 || index) {
			   for(x in cahrsMap.otherAttr){
				   a = cahrsMap.otherAttr[x];
				   html += a.name + '&nbsp&nbsp&nbsp' + list[index][a.attr] + '</br>';
			   }
		   }
		   return html;
	    } 
	}
	console.log(option);
	return option;
}


//饼图, 类型设置
function getLegend(legendData, num) {
	let row = legendData.length / num;
	if(legendData.length % num != 0) {
		row = row + 1;
	}
	console.log(row);
	let topStart = 28 - row ;
	if (row > 12) {
		topStart = 1;
	}
	console.log(topStart);
	let arr = [];
	for (let i = 0; i < legendData.length;) {
		let obj = {
			orient: 'horizontal',
			left: '45%',
			data:[]
		}
		for(let j = 0; j < num; j++) {
			obj.data.push(legendData[i]);
			i++;
		}
		topStart = topStart + 6;
		obj.top = topStart + '%';
		arr.push(obj);
	}
	console.log(arr);
	return arr;
}



