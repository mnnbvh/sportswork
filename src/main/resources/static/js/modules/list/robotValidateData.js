function validateDataRobot(data, isAdd) {
	if(data.robotSn) {
		if(data.robotSn.length > 30) {
			alert("机器人编号：不能超过30位字符");
			return;
		}
		data.robotSn = data.robotSn.trim();
		if(data.robotSn == "") {
			alert("机器人编号不能只包含空格");
			return;
		}
	}
	if(data.robotType) {
		if(data.robotType.length > 30) {
			alert("机器人类型：不能超过30位字符");
			return;
		}
		data.robotType = data.robotType.trim();
		if(data.robotType == "") {
			alert("机器人类型不能只包含空格");
			return;
		}
	}
	if(data.liveSn) {
		if(data.liveSn.length > 30) {
			alert("所在编号：不能超过30位字符");
			return;
		}
		data.liveSn = data.liveSn.trim();
		if(data.liveSn == "") {
			alert("所在编号不能只包含空格");
			return;
		}
	}
	if(data.robotInfo) {
		if(data.robotInfo.length > 30) {
			alert("机器人信息：不能超过30位字符");
			return;
		}
		data.robotInfo = data.robotInfo.trim();
		if(data.robotInfo == "") {
			alert("机器人信息不能只包含空格");
			return;
		}
	}
	if(data.remark) {
		if(data.remark.length > 50) {
			alert("备注：不能超过50位字符");
			return;
		}
		data.remark = data.remark.trim();
		if(data.remark == "") {
			alert("备注不能只包含空格");
			return;
		}
	}
	if(data.other) {
		if(data.other.length > 50) {
			alert("其他：不能超过50位字符");
			return;
		}
		data.other = data.other.trim();
		if(data.other == "") {
			alert("其他不能只包含空格");
			return;
		}
	}
	if(data.online) {
		if(data.online.length > 10) {
			alert("在线状态：不能超过10位字符");
			return;
		}
		data.online = data.online.trim();
		if(data.online == "") {
			alert("在线状态不能只包含空格");
			return;
		}
	}
return true;
}
