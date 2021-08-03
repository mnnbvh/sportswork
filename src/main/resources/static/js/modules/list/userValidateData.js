function validateDataUser(data, isAdd) {
	if(data.username) {
		if(data.username.length > 64) {
			alert("用户名：不能超过64位字符");
			return;
		}
		data.username = data.username.trim();
		if(data.username == "") {
			alert("用户名不能只包含空格");
			return;
		}
	}
	if(data.password) {
		if(data.password.length > 64) {
			alert("密码：不能超过64位字符");
			return;
		}
		data.password = data.password.trim();
		if(data.password == "") {
			alert("密码不能只包含空格");
			return;
		}
	}
	if(data.phone) {
		if(data.phone.length > 16) {
			alert("电话：不能超过16位字符");
			return;
		}
		data.phone = data.phone.trim();
		if(data.phone == "") {
			alert("电话不能只包含空格");
			return;
		}
	}
	if(data.isDel) {
		if(data.isDel.length > 10) {
			alert("是否删除：不能超过10位数字");
			return;
		}
		if (data.isDel) {
			if(!/^[0-9]+$/.test(data.isDel)) {
				alert("是否删除：只能 为 大于等于 0 的整数");
				return;
			}
		}
	}
	if(data.roleId) {
		if(data.roleId.length > 255) {
			alert("：不能超过255位字符");
			return;
		}
		data.roleId = data.roleId.trim();
		if(data.roleId == "") {
			alert("不能只包含空格");
			return;
		}
	}
return true;
}
