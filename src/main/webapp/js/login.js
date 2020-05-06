var devid = "";
var model = "";
var isonline = 1;
var devname = "";
var lasttime = "";
var fktime = "";
var facedataver = "";
var firmware = "";
var firmwarefilename = "";
var fkbindatalib = "";
var fpdataver = "";
var support = "";
var check = false;

$(function() {

	$(".table").find("input").click(function() {
		if (devid != null && devid != "") {
			// alert("被点击了");
			var cmdcode = $(this).val();
			if (cmdcode == "同步时间") {

				$(".form1").html("");
				settime();

			} else if (cmdcode == "重启设备") {

				$(".form1").html("");
				resetfk();

			} else if (cmdcode == "恢复出厂设置") {

				$(".form1").html("");
				returnold_V536();
			}
			else if (cmdcode == "删除人员") {

				$(".form1").html("");
				if (model == null || model=="") {
					addform_deleteuser_X1000();
				} else if (model == "V536") {
					addform_deleteuser_V536();
				}

			} else if (cmdcode == "更改设备名称") {

				$(".form1").html("");
				if (model == null || model=="") {
					addform_setfkname_X1000();
				} else if (model == "V536") {
					//v536无
				}

			} else if (cmdcode == "清除记录数据") {

				$(".form1").html("");
				clearlogdata();

			} else if (cmdcode == "清除注册人员") {

				$(".form1").html("");
				clearenrolldata();

			} else if (cmdcode == "清除管理员") {

				$(".form1").html("");
				clearmanager();

			} else if (cmdcode == "写入人员") {

				$(".form1").html("");
				if (model == null || model=="") {
					addform_setuserinfo_X1000();
				} else if (model == "V536") {
					addform_setuserinfo_V536();
				}
			} else if (cmdcode == "远程注册") {

				$(".form1").html("");
				if (model == null || model=="") {
					addform_longrange_X1000();
				} else if (model == "V536") {
					addform_longrange_V536();
				}

			} else if (cmdcode == "设置门禁时间组") {
				addform_settimezone_X1000();
			} 
			 else if (cmdcode == "获取门禁时间组") {
				 addform_gettimezone_X1000();
			}
			 else if (cmdcode == "设置用户门禁时间段") {
						addform_setuserparsetime_X1000();
		    } 
		    else if (cmdcode == "获取用户门禁时间段") {
					 addform_getuserparsetime_X1000();
			}
		    else if (cmdcode == "设置设备门禁参数") {
				addform_setdoorsetting_X1000();
            } 
		    else if (cmdcode == "设置门控") {
				addform_setdoorstatus_v536();
            }
            else if (cmdcode == "获取设备门禁参数") {
			 getdoorsetting_X1000();
	        }
			else if (cmdcode == "更改服务器ip") {

				$(".form1").html("");
				if (model == null || model=="") {
					addform_setwebserverinfo_X1000();
				} else if (model == "V536") {
					addform_setwebserverinfo_V536();
				}

			}
			 else if (cmdcode == "更新固件") {

				$(".form1").html("");
				if (model == null || model=="") {
					addform_updatefirmware_X1000();
				} else if (model == "V536") {
					addform_updatefirmware_V536();
				}

			} else if (cmdcode == "获取人员列表") {
				$(".form1").html("");
				getuseridlist();

			} else if (cmdcode == "获取记录信息") {

				$(".form1").html("");
				if (model == null || model=="") {
					addform_getlogdata_X1000();
				} else if (model == "V536") {
					addform_getlogdata_V536();
				}

			} else if (cmdcode == "获取设备状况") {

				$(".form1").html("");
				getdevicestatus_X1000();

			} else if (cmdcode == "获取设备信息") {

				$(".form1").html("");
				getdeviceinfo_V536();

			} else if (cmdcode == "获取设备参数") {

				$(".form1").html("");
				getdevicesetting_V536();
			} else if (cmdcode == "设置设备参数") {

				$(".form1").html("");
				addform_setdevicesetting_V536();
			} else if (cmdcode == "获取人员信息") {

				$(".form1").html("");
				if (model == null || model=="") {
					addform_getuserinfo_X1000();
				} else if (model == "V536") {
					addform_getuserinfo_V536();
				}

			}  else if (cmdcode == "获取所有人员信息") {

				$(".form1").html("");
				if (model == null || model=="") {
					getalluserinfo_X1000();
				} else if (model == "V536") {
				}

			}
			else if (cmdcode == "实时登记") {
				window.location.href = path + "/user/users?devid=" + devid;
			} else if (cmdcode == "实时出入") {
				window.location.href = path + "/user/logs?devid=" + devid;
			}
		} else {
			alert("您还未选择任何设备！");
		}

	})

	$("#devset").click(function() {
		if (devid != null && devid != "") {
			$(this).css({"background-color":"#F4BF44","width":"100px","height":"50px"});
			$(this).siblings().attr("style", "background-color:#A9B3AB;width:100px;height:50px");
			$("#t1").show();
			$("#t1").siblings().hide();
			if (model == "V536") {
				$("#t1").find("input").removeAttr("disabled");
				$("#getdsta").attr("disabled", "disabled");
				$("#changedname").attr("disabled", "disabled");
				$("#changeip").attr("disabled", "disabled");
			} else {
				$("#t1").find("input").removeAttr("disabled");
				$("#getdinf").attr("disabled", "disabled");
				$("#getdpro").attr("disabled", "disabled");
				$("#setdpro").attr("disabled", "disabled");
				$("#returnold").attr("disabled", "disabled");
			}
		} else {
			alert("您未选择设备");
		}
		disabled = "disabled"
	})
	$("#peoset").click(function() {
		if (devid != null && devid != "") {
			$(this).css({"background-color":"#F4BF44","width":"100px","height":"50px"});
			$(this).siblings().attr("style", "background-color:#A9B3AB;width:100px;height:50px");
			$("#t2").show();
			$("#t2").siblings().hide();
			if (model == "V536") {
				$("#t2").find("input").removeAttr("disabled");
				$("#clusers").attr("disabled", "disabled");
				$("#getalluserinfo").attr("disabled", "disabled");
			} else {
				$("#t2").find("input").removeAttr("disabled");
			}
		} else {
			alert("您未选择设备");
		}
	})
	
	
	$("#download").click(function() {
		if (devid != null && devid != "") {
			window.location.href = path + "/user/download?devid=" + devid;
			
		} else {
			alert("您未选择设备");
		}
	})
	
	
	$("#logset").click(function() {
		if (devid != null && devid != "") {
			$(this).css({"background-color":"#F4BF44","width":"100px","height":"50px"});
			$(this).siblings().attr("style", "background-color:#A9B3AB;width:100px;height:50px");
			$("#t3").show();
			$("#t3").siblings().hide();
		} else {
			alert("您未选择设备");
		}
	})
	$("#enset").click(function() {
		if (devid != null && devid != "") {
			$(this).css({"background-color":"#F4BF44","width":"100px","height":"50px"});
			$(this).siblings().attr("style", "background-color:#A9B3AB;width:100px;height:50px");
			$("#t4").show();
			$("#t4").siblings().hide();
		} else {
			alert("您未选择设备");
		}
	})
	$("#dorset").click(function() {
		if (devid != null && devid != "") {
			$(this).css({"background-color":"#F4BF44","width":"100px","height":"50px"});
			$(this).siblings().attr("style", "background-color:#A9B3AB;width:100px;height:50px");
			$("#t5").show();
			$("#t5").siblings().hide();
			if (model == "V536") {
				$("#t5").find("input").attr("disabled","disabled");
				$("#doorsta").removeAttr("disabled");
			} else {
				$("#t5").find("input").removeAttr("disabled");
				$("#doorsta").attr("disabled","disabled");
			}
		} else {
			alert("您未选择设备");
		}
	})

	//选取设备
	$(".device").change(function() {

		devid = $(this).find("option:selected").val();
		if (devid != -1) {
			$.ajax({
				url : path + "/user/ajax_selectdevice",
				data : {
					"devid" : devid
				},
				type : "post",
				dataType : "json",
				async : false,
				success : function(res) {
					lasttime = res["lasttime"];
					lasttime = new Date(lasttime);
					lasttime = gettime(lasttime);
					devname = res["devname"];
					model = res["model"];
					isonline = res["isonline"];
					devid = res["devid"];
					fktime = res["fktime"];
					fktime = new Date(fktime);
					fktime = gettime(fktime);
					facedataver = res["facedataver"];
					firmware = res["firmware"];
					firmwarefilename = res["firmwarefilename"];
					fkbindatalib = res["fkbindatalib"];
					fpdataver = res["fpdataver"];
					support = res["support"];
					check = true;
				}
			})
		} else {
			lasttime = "";
			devid = "-1";
			devname = "";
			model = "";
			isonline = "";
			fktime = "";
			facedataver = "";
			firmware = "";
			firmwarefilename = "";
			fkbindatalib = "";
			fpdataver = "";
			support = "";
			check = false;

		}
		$(".devname").text(devname);
		$(".devid").text(devid);
		$(".model").text(model);
		$(".lasttime").text(lasttime);
		$(".fktime").text(fktime);
		$(".facedataver").text(facedataver);
		$(".firmware").text(firmware);
		$(".firmwarefilename").text(firmwarefilename);
		$(".fkbindatalib").text(fkbindatalib);
		$(".fpdataver").text(fpdataver);
		$(".support").text(support);
		$(".form1").html("");
		$("#uls").find("input").attr("style", "width:100px;height:50px;background-color:#A9B3AB");
		$(".na1").children("table").hide();
		if (isonline == 1) {
			$(".isonline").text("离线");
		} else if (isonline == 2) {
			$(".isonline").text("在线");
		} else if (isonline == "") {
			$(".isonline").text("无设备");
		}
		if(model=="V536"){
			$("#dev").find(".la").hide();
		}else{
			$("#dev").find(".la").show();
		}

	})

	// 删除人员 x1000
	$(".form1").on("click", ".deleteuser_x1000", function() {
		var userid = $(".userid").val();
		var userSends = [ {
			"userid" : userid
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 3,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})

	// 删除人员 v536
	$(".form1").on("click", ".deleteuser_v536", function() {
		var usersid = $(".usersid").val();
		var userscount = $(".userscount").val();
		var userlen = new Array();
		userlen = usersid.split(",");
		if (parseInt(userscount) != 0 && userscount != "") {
			if (usersid != "") {
				if (userlen.length != parseInt(userscount)) {
					alert("数量不一致")
				} else {
					var userSends = [ {
						"usersid" : usersid,
						"userscount" : userscount
					} ];
					var task = JSON.stringify({
						"deviceid" : devid,
						"userid" : 3,
						"state" : "WAIT",
						"instructid" : 3,
						"usersends" : userSends
					});
					sendajax(task);
					$(".form1").html("");
				}
			} else {
				alert("数量不一致");
			}
		} else if (parseInt(userscount) == 0) {
			var userSends = [ {
				"userscount" : userscount
			} ];
			var task = JSON.stringify({
				"deviceid" : devid,
				"userid" : 3,
				"state" : "WAIT",
				"instructid" : 3,
				"usersends" : userSends
			});
			sendajax(task);
			$(".form1").html("");
		} else {
			alert("人员数量不可为空")
		}
	})

	// 更改设备名称 x1000
	$(".form1").on("click", ".setfkname_x1000", function() {
		var devname = $(".fkname").val();
		var userSends = [ {
			"fkname" : devname
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 4,
			"usersends" : userSends
		});
		sendajax(task);

		$(".form1").html("");

	})

	// 更改设备名称 v536
	$(".form1").on("click", ".setfkname_v536", function() {
		var devname = $(".fkname").val();
		var userSends = [ {
			"fkname" : devname
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 4,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})

	// 写入人员 X1000
	$(".form1").on("click", ".setuserinfo_x1000", function() {
		var userid = $(".userid").val();
		var name = $(".name").val();
		var privilege = $(".privilege").find("option:selected").val();
		var card = $(".card").val();
		var pwd = $(".pwd").val();
		var face = $(".face_base64").val();
		var fps = $(".fps_base64").val();
		
		//验证是否指纹
		var isok=[1];
		var fpsver = [1];
		if(fps!="" && fps!=null){
		  checkfps(fps,isok,fpsver);
		
		if(isok[0]=="no"){
			$(".fps_base64").val("");
			alert("文件不是指纹，请重新选择");
		}else{
			if(fpsver[0] != fpdataver){
				alert("您选取的指纹版本是"+fpsver[0]+"与当前机器"+fpdataver+"不匹配!")
			}else{
				var photo = $(".photo_base64").val();
				var listfps = [ {
					"fpsdata_base64" : fps
				} ]
				var listusers = [ {
					"userid" : userid,
					"name" : name,
					"privilege" : privilege,
					"card" : card,
					"pwd" : pwd,
					"face_base64" : face,
					"photo_base64" : photo,
					"listfps" : listfps
				} ]
				var userSends = [ {
					"listusers" : listusers
				} ];
				var task = JSON.stringify({
					"deviceid" : devid,
					"userid" : 3,
					"state" : "WAIT",
					"instructid" : 7,
					"usersends" : userSends
				});
				sendajax(task);
				$(".form1").html("");
			}
		}
		}else{
		var photo = $(".photo_base64").val();
		var listfps = [ {
			"fpsdata_base64" : fps
		} ]
		var listusers = [ {
			"userid" : userid,
			"name" : name,
			"privilege" : privilege,
			"card" : card,
			"pwd" : pwd,
			"face_base64" : face,
			"photo_base64" : photo,
			"listfps" : listfps
		} ]
		var userSends = [ {
			"listusers" : listusers
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 7,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");
		}

	})

	// v536 写入人员增加指纹
	$(".form1")
			.on(
					"click",
					".people .addfps",
					function() {
						$(this)
								.before(
										"<input type='hidden'class='fps_base64'><input type='file' name='fps' class='fps'>");

					})

	// v536 写入人员增加人员
	$(".form1").on(
			"click",
			".addpeople",
			function() {
				var html = "" + "<div name='people' class='people'>"
						+ "<img class='showphoto' height=50 width=50><br>"
						+ "<span>人员id</span>" + "<input class='userid'><br>"
						+ "<span>姓名</span>" + "<input class='name'><br>"
						+ "<span>权限</span>" + "<select class='privilege'>"
						+ "<option value='USER'>USER</option>"
						+ "<option value='MANAGER'>MANAGER</option>"
						+ "</select><br>" + "<span>密码</span>"
						+ "<input class='pwd'><br>" + "<span>卡号</span>"
						+ "<input class='card'><br>" + "<span>有效期</span>"
						+ "<input type='date'class='vaildstart'>-"
						+ "<input type='date'class='vaildend'><br>"
						+ "<span>人脸</span>"
						+ "<input type='hidden' class='face_base64'>"
						+ "<input type='file'class='facev'><br>"
						+ "<span>掌纹</span>"
						+ "<input type='hidden' class='palm_base64'>"
						+ "<input type='file'class='palmv'><br>"
						+ "<span>头像</span>"
						+ "<input type='hidden'class='photo_base64'>"
						+ "<input type='file'class='photov'><br>"
						+ "<span>指纹</span>"
						+ "<input type='hidden'class='fps_base64'>"
						+ "<input type='file' name='fps' class='fpsv'>"
						+ "<input type='button' class='addfps' value='增加指纹'><br>"
						+"<span>是否删除新增</span>"
						+"<select class='isupdate'>"
						  +"<option value=0>删除新增</option>"
						  +"<option value=1>覆盖新增</option>"
						+"</select><br>"
						+"<span>是否用照片</span>"
						+"<select class='isusephoto'>"
						  +"<option value=0>不使用</option>"
						  +"<option value=1>使用</option>"
						+"</select>"
						+ "</div>"
				$(this).before(html);

			})

	// v536提交人员写入
	$(".form1").on("click", ".setuserinfo_v536", function() {
		var userslist = [];
		$(".form1").find("div").each(function() {
			var userid = $(this).find(".userid").val();
			var name = $(this).find(".name").val();
			var privilege = $(this).children(".privilege").find("option:selected").val();
			var pwd = $(this).find(".pwd").val();
			var card = $(this).find(".card").val();
			var vaildstart = $(this).find(".vaildstart").val();
			var vaildend = $(this).find(".vaildend").val();
			var face = $(this).find(".face_base64").val();
			var palm = $(this).find(".palm_base64").val();
			var photo = $(this).find(".photo_base64").val();
			var updates = $(this).children(".isupdate").find("option:selected").val();
			var photoEnroll = $(this).children(".isusephoto").find("option:selected").val();
			var fpslist = [];
			$(this).find(".fps_base64").each(function() {
				var fpsdata = $(this).val();
				var fps = {
					"fpsdata_base64" : fpsdata
				};
				fpslist.push(fps);
			})
			var user = {
				"userid" : userid,
				"name" : name,
				"privilege" : privilege,
				"pwd" : pwd,
				"card" : card,
				"vaildstart" : vaildstart,
				"vaildend" : vaildend,
				"face_base64" : face,
				"palm_base64" : palm,
				"photo_base64" : photo,
				"updates": updates,
				"photoEnroll":photoEnroll,
				"listfps" : fpslist
			}
			userslist.push(user);
		})
		var userSends = [ {
			"listusers" : userslist
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 7,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})

	// x1000 更改服务器ip端口
	$(".form1").on("click", ".setwebserverinfo_x1000", function() {
		var serverip = $(".serverip").val();
		var serverport = $(".serverport").val();
		var userSends = [ {
			"serverip" : serverip,
			"serverport" : serverport
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 8,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})

	// v536 更改服务器ip端口
	$(".form1").on("click", ".setwebserverinfo_v536", function() {
		var serverip = $(".serverip").val();
		var serverport = $(".serverport").val();
		var userSends = [ {
			"serverip" : serverip,
			"serverport" : serverport
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 8,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})

	// x1000 更新固件
	$(".form1").on("click", ".updatefirmware_x1000", function() {
		var firmwarefilename = $(".firmwarefilename").val();
		var data_base64 = $(this).parent().find(".data_base64").val();
		var userSends = [ {
			"firmwarefilename" : firmwarefilename,
			"firmwarebindata" : "BIN_1",
			"data_base64" : data_base64
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 9,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})

	// v536 更新固件
	$(".form1").on("click", ".updatefirmware_v536", function() {
		var firmwareFIleURL = $(".firmwareFIleURL").val();
		var userSends = [ {
			"firmwarefileurl" : firmwareFIleURL
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 9,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})

	// x1000 获取记录数据
	$(".form1").on("click", ".getlogdata_x1000", function() {
		var begintime = $(this).parent().find(".begintime").val();
		var endtime = $(this).parent().find(".endtime").val();
		var userSends = [ {
			"begintime" : begintime,
			"endtime" : endtime
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 11,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})

	// v536 获取记录数据
	$(".form1").on("click", ".getlogdata_v536", function() {
		var begintime = $(this).parent().find(".begintime").val();
		var endtime = $(this).parent().find(".endtime").val();
		if(begintime!="" && endtime!=""){
		var userSends = [ {
			"begintime" : begintime,
			"endtime" : endtime
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 11,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");
		}else{
			alert("日期不可为空");
		}

	})

	// x1000 获取人员信息
	$(".form1").on("click", ".getuserinfo_x1000", function() {
		var userid = $(this).parent().find(".userid").val();
		var userSends = [ {
			"userid" : userid
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 13,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})

	// v536 获取人员信息
	$(".form1").on("click", ".getuserinfo_v536", function() {
		var usersid = $(this).parent().find(".usersid").val();
		var userSends = [ {
			"usersid" : usersid
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 13,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})
	
	
	// X1000远程注册
	$(".form1").on("click", ".longrange_x1000", function() {
		var userid = $(this).parent().find(".userid").val();
		var backupnumber = $(this).parent().find("option:selected").val();
		var userSends = [ {
			"userid" : userid,
			"backupnumber" : backupnumber
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 22,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})
	
	// V536远程注册
	$(".form1").on("click", ".longrange_v536", function() {
		var userid = $(this).parent().find(".userid").val();
		var feature = $(this).parent().find("option:selected").val();
		var userSends = [ {
			"userid" : userid,
			"feature" : feature
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 19,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");

	})
	
	
	//V536设置设备参数
	$(".form1").on("click", ".setdevicesetting_v536", function() {
		var paramskey = $(this).parent().find("option:selected").val();
		var paramsval = $(this).parent().find(".paramsval").val();
		var userSends = [ {
			"paramskey" : paramskey,
			"paramsval" : paramsval
		} ];
		var task = JSON.stringify({
			"deviceid" : devid,
			"userid" : 3,
			"state" : "WAIT",
			"instructid" : 21,
			"usersends" : userSends
		});
		sendajax(task);
		$(".form1").html("");
	})
	
	
	//设置时间组
$(".form1").on("click", ".settimezone_x1000", function() {
	var timezoneno = $(this).parent().find(".timezoneno").val();
	var t1start = $(this).parent().find(".t1start").val();
	var t1end =  $(this).parent().find(".t1end").val();
	var t2start = $(this).parent().find(".t2start").val();
	var t2end =  $(this).parent().find(".t2end").val();
	var t3start = $(this).parent().find(".t3start").val();
	var t3end =  $(this).parent().find(".t3end").val();
	var t4start = $(this).parent().find(".t4start").val();
	var t4end =  $(this).parent().find(".t4end").val();
	var t5start = $(this).parent().find(".t5start").val();
	var t5end =  $(this).parent().find(".t5end").val();
	var t6start = $(this).parent().find(".t6start").val();
	var t6end =  $(this).parent().find(".t6end").val();
	t1 = t1start+","+t1end;
	t2 = t2start+","+t2end;
	t3 = t3start+","+t3end;
	t4 = t4start+","+t4end;
	t5 = t5start+","+t5end;
	t6 = t6start+","+t6end;
	
	var timezone = [{"timezoneno":timezoneno,"t1":t1,"t2":t2,"t3":t3,"t4":t4,"t5":t5,"t6":t6}];
	var userSends = [ {
		"listtimezone":timezone
	} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 24,
		"usersends" : userSends
	});
	sendajax(task);
	$(".form1").html("");
})


//获取时间组
$(".form1").on("click", ".gettimezone_x1000", function() {
	var timezoneno = $(this).parent().find(".timezoneno").val();
	var userSends = [ {
		"timezoneno":timezoneno
	} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 25,
		"usersends" : userSends
	});
	sendajax(task);
	$(".form1").html("");
})


	//设置用户门禁时间段
$(".form1").on("click", ".setuserparsetime_x1000", function() {
	var userid = $(this).parent().find(".userid").val();
	var vastart = $(this).parent().find(".start").val();
	var vaend =  $(this).parent().find(".end").val();
	var sunno =  $(this).parent().find(".sun").val();
	var monno =  $(this).parent().find(".mon").val();
	var tueno =  $(this).parent().find(".tue").val();
	var wedno =  $(this).parent().find(".wed").val();
	var thuno =  $(this).parent().find(".thu").val();
	var frino =  $(this).parent().find(".fri").val();
	var satno =  $(this).parent().find(".sat").val();
	var weekno = sunno+","+monno+","+tueno+","+wedno+","+thuno+","+frino+","+satno;
	var userparsetime = [{"userid":userid,"validedatestart":vastart,"validedateend":vaend,"weekno":weekno}];
	var userSends = [ {
		"listparsetime":userparsetime
	} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 26,
		"usersends" : userSends
	});
	sendajax(task);
	$(".form1").html("");
})

//获取用户门禁时间段
$(".form1").on("click", ".getuserparsetime_x1000", function() {
	var userid = $(this).parent().find(".userid").val();
	var userSends = [ {
		"userid":userid
	} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 27,
		"usersends" : userSends
	});
	sendajax(task);
	$(".form1").html("");
})


	//设置门禁参数
$(".form1").on("click", ".setdoorsetting_x1000", function() {
	var OpenDoor_Delay = $(this).parent().find(".OpenDoor_Delay").val();
	var DoorMagnetic_Type = $(this).parent().find(".DoorMagnetic_Type").val();
	var DoorMagnetic_Delay =  $(this).parent().find(".DoorMagnetic_Delay").val();
	var Anti_back =  $(this).parent().find(".Anti_back").val();
	var Alarm_Delay =  $(this).parent().find(".Alarm_Delay").val();
	var Use_Alarm =  $(this).parent().find(".Use_Alarm").val();
	var Wiegand_Type =  $(this).parent().find(".Wiegand_Type").val();
	var Sleep_Time =  $(this).parent().find(".Sleep_Time").val();
	var Screensavers_Time =  $(this).parent().find(".Screensavers_Time").val();
	var Reverify_Time =  $(this).parent().find(".Reverify_Time").val();
	var Glog_Warning =  $(this).parent().find(".Glog_Warning").val();
	var Volume =  $(this).parent().find(".Volume").val();
	var doorsetting = [{"openDoor_Delay":OpenDoor_Delay,"doorMagnetic_Type":DoorMagnetic_Type,"doorMagnetic_Delay":DoorMagnetic_Delay,"anti_back":Anti_back,
		"alarm_Delay":Alarm_Delay,"use_Alarm":Use_Alarm,"wiegand_Type":Wiegand_Type,"sleep_Time":Sleep_Time,"screensavers_Time":Screensavers_Time,
		"reverify_Time":Reverify_Time,"glog_Warning":Glog_Warning,"volume":Volume}];
	var userSends = [ {
		"listdoorsetting":doorsetting
	} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 21,
		"usersends" : userSends
	});
	sendajax(task);
	$(".form1").html("");
})


//设置门控状态
$(".form1").on("click", ".setdoorstatus_v536", function() {
	var doorsta = $(".doorstatus").find("option:selected").val();
	var listdoorsta = [{"doorstatus":doorsta}];
	var userSends = [ {
		"listdoorstatus":listdoorsta
	} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 29,
		"usersends" : userSends
	});
	sendajax(task);
	$(".form1").html("");
})



	$(".form1").on("change", ".people .facev", function() {
		var file = this.files[0];
		var face = $(this).prev();
		uploadFile(file, face);
	})

	$(".form1").on("change", ".people .palmv", function() {
		var file = this.files[0];
		var palm = $(this).prev();
		uploadFile(file, palm);
	})

	$(".form1").on("change", ".people .fpsv", function() {
		var file = this.files[0];
		var fps = $(this).prev();
		uploadFile(file, fps);

	})

	$(".form1").on("change", ".people .photov", function() {
		var file = this.files[0];
		var type = file.type.split("/")[1];
		if(type=="jpeg"){
		var url = window.URL.createObjectURL(file);
		$(this).parent("div").children("img").attr("src", url);
		// 文件转为base64
		var photo = $(this).prev();
		uploadFile(file, photo);
		}else{
			alert("头像必须是jpg类型");
		}
	})

	$(".form1").on("change", ".face", function() {
		var file = this.files[0];
		var face = $(this).prev();
		uploadFile(file, face);
	})

	$(".form1").on("change", ".fps", function() {
		var file = this.files[0];
		var fps = $(this).prev();
		uploadFile(file, fps);
		
	})

	
	$(".form1").on("change", ".photo", function() {
		var file = this.files[0];
		var size = file.size;
		var type = file.type.split("/")[1];
		if(type=="jpeg"){
		if(size>6*1024){
			alert("图片不能超过6K")
		}else{
			
		var url = window.URL.createObjectURL(file);
		$(this).parent().children("img").attr("src", url);
		// 文件转为base64
		var photo = $(this).prev();
		uploadFile(file, photo);
		}
		}else{
			alert("头像必须是jpg类型");
		}
	})

	$(".form1").on("change", ".data", function() {
		var file = this.files[0];
		var data = $(this).prev();
		uploadFile(file, data);

	})

})

setInterval("checkisonline()", 60000);  //若选择了设备，每分钟查询一次设备在线状态
setInterval("selecttask()", 30000);   //若选择了设备，每半分钟查询一次当前用户给此设备下达的指令
setInterval("selectdevice()", 15000);   //定时更新设备列表


//定时查询所有设备数量
function selectdevice() {
		$.ajax({
			url : path + "/user/selectalldevice",
			data : {},
			type : "post",
			dataType : "json",
			async : false,
			success : function(res) {
				var html="<option value='-1'>---请选择设备---</option>";
				var listdevice = res["listdv"];
				if(listdivice.length>0){
				for(var key in listdevice){
					var deviceid = listdevice[key]["devid"];
					var devname = listdevice[key]["devname"];
					html = html
					+"<option value="+deviceid+">"+devname+"</option>"
				}
				}
				$(".device").html(html);
				$(".device").val(devid);
			}

		})
	

}


// 定时查询设备在线状态
function checkisonline() {
	if (check == true) {
		$.ajax({
			url : path + "/user/ajax_selectdevice",
			data : {
				"devid" : devid
			},
			type : "post",
			dataType : "json",
			async : false,
			success : function(res) {
				isonline = res["isonline"];
				lasttime = res["lasttime"];
				lasttime = new Date(lasttime);
				fktime = res["fktime"]
				fktime = new Date(fktime);
				devname = res["devname"]
				if (isonline == 1) {
					$(".isonline").text("离线");
				} else {
					$(".isonline").text("在线");
				}
				$(".lasttime").text(gettime(lasttime));
				$(".fktime").text(gettime(fktime));
				$(".devname").text(devname);
			}

		})
	}

}

function checkfps(data,isfps,fpsver){
	$.ajax({
		url : path + "/user/ajax_checkfps",
		data : {
			"fps" : data
		},
		type : "post",
		dataType : "json",
		async : false,
		success : function(res) {
		  isfps[0] = res["isok"];
		  fpsver[0] = res["fpsver"]
		}
		
	})
}

//暂无第三方接口验证
function checkface(data,isface,facever){
	$.ajax({
		url : path + "/user/ajax_checkface",
		data : {
			"face" : data
		},
		type : "post",
		dataType : "json",
		async : false,
		success : function(res) {
			isface = res["isok"];
			facever = res["facever"]
		}
		
	})
}

// 定时查询任务状态
function selecttask() {
	// 没有设置登录，管理员id默认3
	if (check == true) {
		task = {
			"deviceid" : devid,
			"userid" : 3
		}
		$
				.ajax({
					url : path + "/user/ajax_selecttask",
					data : task,
					type : "post",
					dataType : "json",
					async : false,
					success : function(res) {
						var html2 = "";
						if (res["ok"] == "NO") {
							var isok = "没有已经完成的指令。";
							html2 = isok;
						} else {
							for ( var key in res["ok"]) {
								var deviceid = res["ok"][key]["deviceid"];
								var instructid = res["ok"][key]["instruct"]["instructid"];
								var instructname = res["ok"][key]["instruct"]["instructname"];
								var username = res["ok"][key]["user"]["username"]
                                var state = res["ok"][key]["state"]
								html2 = html2 + "设备id:" + deviceid + ";用户:"
										+ username + ";指令:" + instructname
										+ ";执行结果;"+state+"."
								// 机器发送的数据
								var userget = res["ok"][key]["usergets"];
								if (userget == null || userget == "") {
									html2 = html2;
								} else {
									for ( var key1 in res["ok"][key]["usergets"]) {
										// 获取用户列表 x1000
										if (instructid == "10") {
											if (model != "V536") {
												for ( var key2 in res["ok"][key]["usergets"][key1]["listuid"]) {
													var useridcount = res["ok"][key]["usergets"][key1]["listuid"][key2]["userscount"];
													var oneuseridsize = res["ok"][key]["usergets"][key1]["listuid"][key2]["oneuseridsize"];
													var useridarray = res["ok"][key]["usergets"][key1]["listuid"][key2]["useridarray"];
													html2 = html2
															+ "useridcount:"
															+ useridcount
															+ ";oneuseridsize:"
															+ oneuseridsize
															+ ";useridarray:"
															+ useridarray + "。";
												}
											} else {
												for ( var key2 in res["ok"][key]["usergets"][key1]["packages"]) {
													var useridcount = res["ok"][key]["usergets"][key1]["packages"][key2]["userscount"];
													var html3 = "";
													for(var key3 in res["ok"][key]["usergets"][key1]["packages"][key2]["lsuid"]){
														var userId = res["ok"][key]["usergets"][key1]["packages"][key2]["lsuid"][key3]["userId"];
														var name = res["ok"][key]["usergets"][key1]["packages"][key2]["lsuid"][key3]["name"];
														html3 = html3
														+";userId:"+userId
														+";name:"+name
													}
													html2 = html2
															+ "userscount:"+ useridcount
															+ html3
															+ "。";
												}

											}

										}
										// 获取记录信息
										else if (instructid == "11") {
											if (model != "V536") {
												for ( var key2 in res["ok"][key]["usergets"][key1]["logs"]) {
													var logcount = res["ok"][key]["usergets"][key1]["logs"][key2]["logcount"];
													var onelogsize = res["ok"][key]["usergets"][key1]["logs"][key2]["onelogsize"];
													var logarray = res["ok"][key]["usergets"][key1]["logs"][key2]["logarray"];
													html2 = html2 + "logcount:"
															+ logcount
															+ ";onelogsize:"
															+ onelogsize
															+ ";logarray:"
															+ logarray + "。";
												}
											} else {
												for ( var key2 in res["ok"][key]["usergets"][key1]["packages"]) {
													var alllogcount = res["ok"][key]["usergets"][key1]["packages"][key2]["alllogcount"];
													var logcount = res["ok"][key]["usergets"][key1]["packages"][key2]["logscount"];
													var html3 = "";
													for ( var key3 in res["ok"][key]["usergets"][key1]["packages"][key2]["logs"]) {
														var userid = res["ok"][key]["usergets"][key1]["packages"][key2]["logs"][key3]["userid"]
														var time = res["ok"][key]["usergets"][key1]["packages"][key2]["logs"][key3]["iotime"]
														var verifymode = res["ok"][key]["usergets"][key1]["packages"][key2]["logs"][key3]["verifymode"]
														var iomode = res["ok"][key]["usergets"][key1]["packages"][key2]["logs"][key3]["iomode"]
														var inout = res["ok"][key]["usergets"][key1]["packages"][key2]["logs"][key3]["inouts"]
														html3 = html3
																+ ";userid:"
																+ userid
																+ ";time:"
																+ time
																+ ";verifymode:"
																+ verifymode
																+ ";inout:"
																+ inout + "。"
													}
													html2 = html2
															+ "alllogcount:"
															+ alllogcount
															+ ";logcount:"
															+ logcount + ";"
															+ html3 + "。";
												}
											}
										}
										// 获取设备状况
										else if (instructid == "12") {
											for ( var key2 in res["ok"][key]["usergets"][key1]["devicestates"]) {
												var alllogcount = res["ok"][key]["usergets"][key1]["devicestates"][key2]["alllogcount"];
												var facecount = res["ok"][key]["usergets"][key1]["devicestates"][key2]["facecount"];
												var facemax = res["ok"][key]["usergets"][key1]["devicestates"][key2]["facemax"];
												var fpcount = res["ok"][key]["usergets"][key1]["devicestates"][key2]["fpcount"];
												var fpmax = res["ok"][key]["usergets"][key1]["devicestates"][key2]["fpmax"];
												var idcardcount = res["ok"][key]["usergets"][key1]["devicestates"][key2]["idcardcount"];
												var idcardmax = res["ok"][key]["usergets"][key1]["devicestates"][key2]["idcardmax"];
												var managercount = res["ok"][key]["usergets"][key1]["devicestates"][key2]["managercount"];
												var managermax = res["ok"][key]["usergets"][key1]["devicestates"][key2]["managermax"];
												var passwordcount = res["ok"][key]["usergets"][key1]["devicestates"][key2]["passwordcount"];
												var passwordmax = res["ok"][key]["usergets"][key1]["devicestates"][key2]["passwordmax"];
												var pvcount = res["ok"][key]["usergets"][key1]["devicestates"][key2]["pvcount"];
												var pvmax = res["ok"][key]["usergets"][key1]["devicestates"][key2]["pvmax"];
												var totallogcount = res["ok"][key]["usergets"][key1]["devicestates"][key2]["totallogcount"];
												var totallogmax = res["ok"][key]["usergets"][key1]["devicestates"][key2]["totallogmax"];
												var usercount = res["ok"][key]["usergets"][key1]["devicestates"][key2]["usercount"];
												var usermax = res["ok"][key]["usergets"][key1]["devicestates"][key2]["usermax"];
												html2 = html2 + "alllogcount:"
														+ alllogcount
														+ ";facecount:"
														+ facecount
														+ ";facemax:" + facemax
														+ ";fpcount:" + fpcount
														+ ";fpmax:" + fpmax
														+ ";idcardcount:"
														+ idcardcount
														+ ";idcardmax:"
														+ idcardmax
														+ ";managercount:"
														+ managercount
														+ ";passwordcount:"
														+ passwordcount
														+ "passwordmax:"
														+ passwordmax
														+ ";pvcount:" + pvcount
														+ ";pvmax:" + pvmax
														+ ";totallogcount:"
														+ totallogcount
														+ ";totallogmax:"
														+ totallogmax
														+ ";usercount:"
														+ usercount
														+ ";usermax:" + usermax
														+ "。";
											}
										}
										// 获取设备信息
										else if (instructid == "17") {
											for ( var key2 in res["ok"][key]["usergets"][key1]["deviceinfo"]) {
												var devid = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["devid"];
												var devmodel = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["devmodel"];
												var token = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["token"];
												var devname = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["devname"];
												var firmware = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["firmware"];
												var fpver = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["fpver"];
												var facever = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["facever"];
												var pvver = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["pvver"];
												var maxbufferlen = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["maxbufferlen"];
												var userlimit = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["userlimit"];
												var fplimit = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["fplimit"];
												var facelimit = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["facelimit"];
												var pwdlimit = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["pwdlimit"];
												var cardlimit = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["cardlimit"];
												var loglimit = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["loglimit"];
												var usercount = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["usercount"];
												var managercount = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["managercount"];
												var fpcount = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["fpcount"];
												var pwdcount = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["pwdcount"];
												var cardcount = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["cardcount"];
												var logcount = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["logcount"];
												var allLogCount = res["ok"][key]["usergets"][key1]["deviceinfo"][key2]["allLogCount"];
												html2 = html2 + "devid:"
														+ devid + ";devmodel:"
														+ devmodel + ";token:"
														+ token + ";devname:"
														+ devname
														+ ";firmware:"
														+ firmware + ";fpver:"
														+ fpver + ";facever:"
														+ facever + ";pvver:"
														+ pvver
														+ ";maxbufferlen:"
														+ maxbufferlen
														+ "userlimit:"
														+ userlimit
														+ ";fplimit:" + fplimit
														+ ";facelimit:"
														+ facelimit
														+ ";pwdlimit:"
														+ pwdlimit
														+ ";cardlimit:"
														+ cardlimit
														+ ";loglimit:"
														+ loglimit
														+ ";usercount:"
														+ usercount
														+ ";managercount:"
														+ managercount
														+ ";fpcount:" + fpcount
														+ ";pwdcount:"
														+ pwdcount
														+ ";cardcount:"
														+ cardcount
														+ ";logcount:"
														+ logcount 
														+ ";allLogCount:"
														+ allLogCount 
														+ "。";
											}
										}
										// 获取设备设置参数
										else if (instructid == "20") {
											if(model == "V536"){
											for ( var key2 in res["ok"][key]["usergets"][key1]["devsts"]) {
												var alarmDelay = res["ok"][key]["usergets"][key1]["devsts"][key2]["alarmDelay"];
												var antiPass = res["ok"][key]["usergets"][key1]["devsts"][key2]["antiPass"];
												var devName = res["ok"][key]["usergets"][key1]["devsts"][key2]["devName"];
												var interval = res["ok"][key]["usergets"][key1]["devsts"][key2]["interval"];
												var language = res["ok"][key]["usergets"][key1]["devsts"][key2]["language"];
												var openDoorDelay = res["ok"][key]["usergets"][key1]["devsts"][key2]["openDoorDelay"];
												var reverifyTime = res["ok"][key]["usergets"][key1]["devsts"][key2]["reverifyTime"];
												var screensaversTime = res["ok"][key]["usergets"][key1]["devsts"][key2]["screensaversTime"];
												var serverHost = res["ok"][key]["usergets"][key1]["devsts"][key2]["serverHost"];
												var serverPort = res["ok"][key]["usergets"][key1]["devsts"][key2]["serverPort"];
												var sleepTime = res["ok"][key]["usergets"][key1]["devsts"][key2]["sleepTime"];
												var tamperAlarm = res["ok"][key]["usergets"][key1]["devsts"][key2]["tamperAlarm"];
												var volume = res["ok"][key]["usergets"][key1]["devsts"][key2]["volume"];
												var wiegandType = res["ok"][key]["usergets"][key1]["devsts"][key2]["wiegandType"];
												var pushServerHost = res["ok"][key]["usergets"][key1]["devsts"][key2]["pushServerHost"];
												var pushServerPort = res["ok"][key]["usergets"][key1]["devsts"][key2]["pushServerPort"];
												var pushEnable = res["ok"][key]["usergets"][key1]["devsts"][key2]["pushEnable"];
												var verifyMode = res["ok"][key]["usergets"][key1]["devsts"][key2]["verifyMode"];
												
												html2 = html2 + "alarmDelay:"+ alarmDelay 
												+ ";antiPass:"+ antiPass 
												+ ";devName:"+ devName 
												+ ";interval:"+ interval
												+ ";language:"+ language 
												+ ";openDoorDelay:"+ openDoorDelay
												+ ";reverifyTime:"+ reverifyTime 
												+ ";screensaversTime:"+ screensaversTime 
												+ ";serverHost:"+ serverHost
												+ ";serverPort:"+ serverPort
												+ ";sleepTime:"+ sleepTime
												+ ";tamperAlarm:" + tamperAlarm
												+ ";volume:"+ volume
												+ ";wiegandType:"+ wiegandType
												+ ";pushServerHost:"+ pushServerHost
												+ ";pushServerPort:"+ pushServerPort
												+ ";pushEnable:"+ pushEnable
												+ ";verifyMode:"+ verifyMode
												+ "。";
											}
											}
											//获取门禁参数
											else{
												for ( var key2 in res["ok"][key]["usergets"][key1]["doorsettings"]) {
													var OpenDoor_Delay = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["openDoor_Delay"];
													var DoorMagnetic_Type = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["doorMagnetic_Type"];
													var DoorMagnetic_Delay = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["doorMagnetic_Delay"];
													var Anti_back = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["anti_back"];
													var Alarm_Delay = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["alarm_Delay"];
													var Use_Alarm = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["use_Alarm"];
													var Wiegand_Type = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["wiegand_Type"];
													var Sleep_Time = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["sleep_Time"];
													var Screensavers_Time = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["screensavers_Time"];
													var Reverify_Time = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["reverify_Time"];
													var Glog_Warning = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["glog_Warning"];
													var Volume = res["ok"][key]["usergets"][key1]["doorsettings"][key2]["volume"];
													
													html2 = html2 + "OpenDoor_Delay:"+ OpenDoor_Delay 
													+ ";DoorMagnetic_Type:"+ DoorMagnetic_Type 
													+ ";DoorMagnetic_Delay:"+ DoorMagnetic_Delay 
													+ ";Anti_back:"+ Anti_back
													+ ";Alarm_Delay:"+ Alarm_Delay 
													+ ";Use_Alarm:"+ Use_Alarm 
													+ ";Wiegand_Type:"+ Wiegand_Type
													+ ";Sleep_Time:"+ Sleep_Time
													+ ";Screensavers_Time:"+ Screensavers_Time
													+ ";Reverify_Time:"+ Reverify_Time
													+ ";Glog_Warning:"+ Glog_Warning
													+ ";Volume:"+ Volume
													+ "。";
												}
											}
										}
										// 获取时间组
										else if (instructid == "25") {
											for ( var key2 in res["ok"][key]["usergets"][key1]["timezones"]) {
												var timezoneno = res["ok"][key]["usergets"][key1]["timezones"][key2]["timezoneno"];
												var t1 = res["ok"][key]["usergets"][key1]["timezones"][key2]["t1"];
												var t2 = res["ok"][key]["usergets"][key1]["timezones"][key2]["t2"];
												var t3 = res["ok"][key]["usergets"][key1]["timezones"][key2]["t3"];
												var t4 = res["ok"][key]["usergets"][key1]["timezones"][key2]["t4"];
												var t5 = res["ok"][key]["usergets"][key1]["timezones"][key2]["t5"];
												var t6 = res["ok"][key]["usergets"][key1]["timezones"][key2]["t6"];
												html2 = html2 + "时间组编号:"+ timezoneno 
												+ ";t1:"+ t1 
												+ ";t2:"+ t2 
												+ ";t3:"+ t3
												+ ";t4:"+ t4 
												+ ";t5:"+ t5 
												+ ";t6:"+ t6
												+ "。";
											}
										}
										// 获取用户时间段
										else if (instructid == "27") {
											for ( var key2 in res["ok"][key]["usergets"][key1]["parsetimes"]) {
												var userid = res["ok"][key]["usergets"][key1]["parsetimes"][key2]["userid"];
												var validedatestart = res["ok"][key]["usergets"][key1]["parsetimes"][key2]["validedatestart"];
												var validedateend = res["ok"][key]["usergets"][key1]["parsetimes"][key2]["validedateend"];
												var weekno = res["ok"][key]["usergets"][key1]["parsetimes"][key2]["weekno"];
											
												html2 = html2 + "人员id:"+ userid 
												+ ";起始日期:"+ validedatestart 
												+ ";结束日期:"+ validedateend 
												+ ";周时间组设置:"+ weekno
												+ "。";
											}
										}
										// 获取人员信息
										else if (instructid == "13") {
											if (model != "V536") {
												for ( var key2 in res["ok"][key]["usergets"][key1]["users"]) {
													var userid = res["ok"][key]["usergets"][key1]["users"][key2]["userid"];
													var username = res["ok"][key]["usergets"][key1]["users"][key2]["name"];
													var userprivilege = res["ok"][key]["usergets"][key1]["users"][key2]["privilege"];

													var zhiwencount = 0;
													var passwordcount = 0;
													var cardcount = 0;
													var facecount = 0;
													var zhangwencount = 0;

													for ( var key3 in res["ok"][key]["usergets"][key1]["users"][key2]["enrollarrays"]) {
														var backupnumber = res["ok"][key]["usergets"][key1]["users"][key2]["enrollarrays"][key3]["backupnumber"];
														if (backupnumber < 10) {
															zhiwencount++;
														}
														if (backupnumber == 10) {
															passwordcount++;
														} else if (backupnumber == 11) {
															cardcount++;
														} else if (backupnumber == 12) {
															facecount++;
														} else if (backupnumber > 12) {
															zhangwencount++;
														}
													}

													html2 = html2 + "userid:"
															+ userid
															+ ";username:"
															+ username
															+ ";userprivilege:"
															+ userprivilege
															+ ";指纹:"
															+ zhiwencount
															+ "个;密码:"
															+ passwordcount
															+ "个;卡:"
															+ cardcount
															+ "个;人脸:"
															+ facecount
															+ "个;掌纹:"
															+ zhangwencount
															+ "个。";
												}
											} else {
												for ( var key2 in res["ok"][key]["usergets"][key1]["packages"]) {
													var userscount = res["ok"][key]["usergets"][key1]["packages"][key2]["userscount"];
													var html3 = "";
													for ( var key3 in res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"]) {
														var userid = res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["userid"]
														var name = res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["name"]
														var privilege = res[ "ok" ][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["privilege"]
														var card = res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["card"]
														var pwd = res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["pwd"]
														var face = res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["face"]
														var palm = res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["palm"]
														var photo = res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["photo"]
														var vaildstart = res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["vaildstart"]
														var vaildend = res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["validend"]

														if (face != null) {
															face = 1;
														} else {
															face = 0;
														}
														if (palm != null) {
															palm = 1;
														} else {
															palm = 0;
														}
														if (photo != null) {
															photo = 1;
														} else {
															photo = 0;
														}
														var fps = 0;
														for ( var key4 in res["ok"][key]["usergets"][key1]["packages"][key2]["userslist"][key3]["listfps"]) {
															fps++;
														}

														html3 = html3
																+ ";userid:"
																+ userid
																+ ";name:"
																+ name
																+ ";privilege:"
																+ privilege
																+ ";card:"
																+ card
																+ ";pwd:" + pwd
																+ ";人脸:" + face
																+ ";头像:"
																+ photo
																+ ";掌纹:" + palm
																+ ";指纹:" + fps
													}
													html2 = html2
															+ "userscount:"
															+ userscount + "；"
															+ html3 + "。";
												}

											}
										}

									}

								}
							}
						}
						$(".result2").val(html2);
					}
				})

	}
}

// 设置时间
function settime() {
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 1,
		"usersends" : userSends
	});
	sendajax(task);
}
// 重启设备
function resetfk() {
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 2,
		"usersends" : userSends
	});
	sendajax(task);

}

// 清除所有记录数据
function clearlogdata() {
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 6,
		"usersends" : userSends
	});
	sendajax(task);

}

// 清除所有注册人员
function clearenrolldata() {
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 5,
		"usersends" : userSends
	});
	sendajax(task);

}

//v536 恢复出厂
function returnold_V536(){
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 28,
		"usersends" : userSends
	});
	sendajax(task);
	
}


//清除管理员
function clearmanager() {
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 18,
		"usersends" : userSends
	});
	sendajax(task);

}

// 获取人员列表
function getuseridlist() {
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 10,
		"usersends" : userSends
	});
	sendajax(task);

}

//获取所有用户信息  x1000
function getalluserinfo_X1000(){
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 23,
		"usersends" : userSends
	});
	sendajax(task);
	
}

// 获取设备状况 X1000
function getdevicestatus_X1000() {
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 12,
		"usersends" : userSends
	});
	sendajax(task);

}

// 获取设备信息 V536
function getdeviceinfo_V536() {
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 17,
		"usersends" : userSends
	});
	sendajax(task);

}

// 获取人员信息
function getuserinfo() {
	var userSends = [ {
		"userid" : "2"
	} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 13,
		"usersends" : userSends
	});
	sendajax(task);

}

// V536获取设备参数
function getdevicesetting_V536(){
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 20,
		"usersends" : userSends
	});
	sendajax(task);

}

//获取门禁参数
function getdoorsetting_X1000(){
	var userSends = [ {} ];
	var task = JSON.stringify({
		"deviceid" : devid,
		"userid" : 3,
		"state" : "WAIT",
		"instructid" : 20,
		"usersends" : userSends
	});
	sendajax(task);

}

function sendajax(task) {
	$.ajax({
		url : path + "/user/ajax_userrequest",
		data : task,
		type : "post",
		dataType : "json",
		traditional : true,
		contentType : "application/json",
		async : false,
		success : function(res) {
			var html1 = "";

			var message = res["message"];
			var state = res["state"];
			var starttime = res["starttime"]
			var userid = res["userid"]
			var instruct = res["instruct"]

			var stime = new Date(starttime);
			var strtime = stime.getHours() + ":" + stime.getMinutes() + ":"
					+ stime.getSeconds();
			html1 = message + ";指令:" + instruct + ";状态：" + state + ";时间:"
					+ strtime;

			$(".result1").val(html1);

		}
	})

}

// x1000删除人员表单
function addform_deleteuser_X1000() {
	$(".form1").html("");
	html = "" + "<span>删除人员的id</span>" + "<input class='userid' >"
			+ "<input class='deleteuser_x1000' type='button' value='提交'>"
	$(".form1").append(html);

}

// v536删除人员表单
function addform_deleteuser_V536() {
	$(".form1").html("");
	html = "" + "<span>删除人员的id</span>"
			+ "<input class='usersid'  placeholder='多个id请用,隔开'><br>"
			+ "<span>删除人员的数量</span>"
			+ "<input class='userscount' placeholder='请与上面数量一致，0则删除全部'>"
			+ "<input class='deleteuser_v536' type='button' value='提交'>"
	$(".form1").append(html);

}

// x1000 更改设备名称表单
function addform_setfkname_X1000() {
	$(".form1").html("");
	html = "" + "<span>设备名称</span>" + "<input class='fkname'><br>"
			+ "<input class='setfkname_x1000' type='button' value='提交'>"
	$(".form1").append(html);

}


// x1000 写入人员表单
function addform_setuserinfo_X1000() {
	$(".form1").html("");
	html = "" + "<img class='showphoto' height=50 width=50><br>"
			+ "<span>人员id</span>" + "<input class='userid'><br>"
			+ "<span>姓名</span>" + "<input class='name'><br>"
			+ "<span>权限</span>" + "<select class='privilege'>"
			+ "<option value='USER'>USER</option>"
			+ "<option value='MANAGER'>MANAGER</option>" + "</select><br>"
			+ "<span>密码</span>" + "<input class='pwd'><br>" + "<span>卡号</span>"
			+ "<input class='card'><br>" + "<span>人脸</span>"
			+ "<input type='hidden' class='face_base64'>"
			+ "<input type='file'class='face'><br>" + "<span>指纹</span>"
			+ "<input type='hidden' class='fps_base64'>"
			+ "<input type='file'class='fps'><br>" + "<span>头像</span>"
			+ "<input type='hidden' class='photo_base64'>"
			+ "<input type='file'class='photo'><br>"
			+ "<input class='setuserinfo_x1000' type='button' value='提交'>"
	$(".form1").append(html);
}

// v536 写入人员表单
function addform_setuserinfo_V536() {
	$(".form1").html("");
	html = "" + "<div name='people' class='people'>"
				+ "<img class='showphoto' height=50 width=50><br>"
				+ "<span>人员id</span>" + "<input class='userid'><br>"
				+ "<span>姓名</span>" + "<input class='name'><br>"
				+ "<span>权限</span>" + "<select class='privilege'>"
				+ "<option value='USER'>USER</option>"
				+ "<option value='MANAGER'>MANAGER</option>" + "</select><br>"
				+ "<span>密码</span>" + "<input class='pwd'><br>" + "<span>卡号</span>"
				+ "<input class='card'><br>" + "<span>有效期</span>"
				+ "<input type='date'class='vaildstart'>-"
				+ "<input type='date'class='vaildend'><br>" + "<span>人脸</span>"
				+ "<input type='hidden' class='face_base64'>"
				+ "<input type='file'class='facev'><br>" + "<span>掌纹</span>"
				+ "<input type='hidden' class='palm_base64'>"
				+ "<input type='file'class='palmv'><br>" + "<span>头像</span>"
				+ "<input type='hidden'class='photo_base64'>"
				+ "<input type='file'class='photov'><br>" + "<span>指纹</span>"
				+ "<input type='hidden'class='fps_base64'>"
				+ "<input type='file' name='fps' class='fpsv'>"
				+ "<input type='button' class='addfps' value='增加指纹'><br>"
				+"<span>是否删除新增</span>"
				+"<select class='isupdate'>"
				  +"<option value=0>删除新增</option>"
				  +"<option value=1>覆盖新增</option>"
				+"</select><br>"
				+"<span>是否用照片</span>"
				+"<select class='isusephoto'>"
				  +"<option value=0>不使用</option>"
				  +"<option value=1>使用</option>"
				+"</select>"
			+ "</div>"
			+ "<input class='addpeople' type='button' value='增加人员'><br>"
			+ "<input class='setuserinfo_v536' type='button' value='提交'>"
	$(".form1").append(html);
}

// x1000 更改服务器ip端口表单
function addform_setwebserverinfo_X1000() {
	$(".form1").html("");
	html = "" + "<span>服务器ip</span>" + "<input class='serverip'><br>"
			+ "<span>端口</span>" + "<input class='serverport'><br>"
			+ "<input class='setwebserverinfo_x1000' type='button' value='提交'>"
	$(".form1").append(html);
}

// v536 更改服务器ip端口表单
function addform_setwebserverinfo_V536() {
	$(".form1").html("");
	html = "" + "<span>服务器ip</span>" + "<input class='serverip'><br>"
			+ "<span>端口</span>" + "<input class='serverport'><br>"
			+ "<input class='setwebserverinfo_v536' type='button' value='提交'>"
	$(".form1").append(html);
}

// x1000 更新固件表单
function addform_updatefirmware_X1000() {
	$(".form1").html("");
	html = "" + "<span>固件名称</span>" + "<input class='firmwarefilename'><br>"
			+ "<span>固件包</span>" + "<input type='hidden' class='data_base64'>"
			+ "<input type='file'class='data'><br>"
			+ "<input class='updatefirmware_x1000' type='button' value='提交'>"
	$(".form1").append(html);
}

// v536 更新固件表单
function addform_updatefirmware_V536() {
	$(".form1").html("");
	html = "" + "<span>固件包地址</span>" + "<input class='firmwareFIleURL'><br>"
			+ "<input class='updatefirmware_v536' type='button' value='提交'>"
	$(".form1").append(html);
}

// x1000 获取记录数据表单
function addform_getlogdata_X1000() {
	$(".form1").html("");
	html = "" + "<span>开始日期</span>"
			+ "<input type='date' class='begintime'><br>" + "<span>结束日期</span>"
			+ "<input type='date'class='endtime'><br>"
			+ "<input class='getlogdata_x1000' type='button' value='提交'>"
	$(".form1").append(html);
}

// v536 获取记录数据表单
function addform_getlogdata_V536() {
	$(".form1").html("");
	html = "" + "<span>开始日期</span>"
			+ "<input type='date' class='begintime'><br>" + "<span>结束日期</span>"
			+ "<input type='date'class='endtime'><br>"
			+ "<input class='getlogdata_v536' type='button' value='提交'>"
	$(".form1").append(html);
}

// x1000 获取人员信息表单
function addform_getuserinfo_X1000() {
	$(".form1").html("");
	html = "" + "<span>人员id</span>" + "<input class='userid'><br>"
			+ "<input class='getuserinfo_x1000' type='button' value='提交'>"
	$(".form1").append(html);
}

// v536 获取人员信息表单
function addform_getuserinfo_V536() {
	$(".form1").html("");
	html = "" + "<span>人员id</span>"
			+ "<input class='usersid' placeholder='多个id请用,隔开'><br>"
			+ "<input class='getuserinfo_v536' type='button' value='提交'>"
	$(".form1").append(html);
}

//X1000远程注册表单
function addform_longrange_X1000() {
	$(".form1").html("");
	html = "" + "<span>人员id</span>" + "<input class='userid'><br>"
			+ "<span>注册方式</span>" + "<select class='backupnumber'>"
			+ "<option value=0>指纹</option>"
			+ "<option value=10>密码</option>"
			+ "<option value=11>卡</option>"
			+ "<option value=12>人脸</option>"
			+ "<option value=13>掌纹</option>"
			+ "</select><br>"
			+ "<input class='longrange_x1000' type='button' value='提交'>"
	$(".form1").append(html);

}

// V536远程注册表单
function addform_longrange_V536() {
	$(".form1").html("");
	html = "" + "<span>人员id</span>" + "<input class='userid'><br>"
			+ "<span>注册方式</span>" + "<select class='feature'>"
			+ "<option value='face'>人脸</option>"
			+ "<option value='fp'>指纹</option>"
			+ "<option value='card'>卡</option>"
			+ "<option value='pwd'>密码</option>"
			+ "</select><br>"
			+ "<input class='longrange_v536' type='button' value='提交'>"
	$(".form1").append(html);

}

// V536获取设备参数表单
function addform_getdevicepro_V536() {
	$(".form1").html("");
	html = "" + "<span>参数key</span>"
			+ "<input class='params'placeholder='多个key请用,隔开'><br>"
			+ "<input class='getdevicepro_v536' type='button' value='提交'>"
	$(".form1").append(html);

}

// V536设置设备参数表单
function addform_setdevicesetting_V536() {
	$(".form1").html("");
	html = "" + "<span>参数</span>"
	+"<select class='paramskey'>"
	   +"<option value='alarmDelay'>报警延时(秒)</option>"
	   +"<option value='antiPass'>是否开启反潜(yes/no)</option>"
	   +"<option value='devName'>设备名</option>"
	   +"<option value='interval'>心跳间隔(秒)</option>"
	   +"<option value='language'>语言(CHS/CHT等)</option>"
	   +"<option value='openDoorDelay'>开门延时(秒)</option>"
	   +"<option value='reverifyTime'>重复确认时间(分钟)</option>"
	   +"<option value='screensaversTime'>多久屏保(分钟)</option>"
	   +"<option value='serverHost'>服务器地址</option>"
	   +"<option value='serverPort'>服务器端口</option>"
	   +"<option value='sleepTime'>多久睡眠(分钟)</option>"
	   +"<option value='tamperAlarm'>是否防拆报警(yes/no)</option>"
	   +"<option value='volume'>音量(0-10)</option>"
	   +"<option value='wiegandType'>维根格式(26/34)</option>"
	   +"<option value='pushServerHost'>实时推送地址</option>"
	   +"<option value='pushServerPort'>实时推送端口(26/34)</option>"
	   +"<option value='pushEnable'>是否推送实时(yes/no)</option>"
	   +"<option value='verifyMode'>打卡方式</option>"
	   +"<option value='adminPwd'>登录密码</option>"
	+"</select>"   
	+ "<input class='paramsval'><br>"
	+ "<input class='setdevicesetting_v536' type='button' value='提交'>"
$(".form1").append(html);

}

//设置门禁时间组表单
function addform_settimezone_X1000() {
	$(".form1").html("");
	html = "" + "<span>时间组序号</span>"
	+"<input class='timezoneno' placeholder='请输入1~255'><br>"
	+ "<span>t1</span>"
	+ "<input class='t1start' placeholder='HHmm'>-<input class='t1end' placeholder='HHmm'><br>"
	+ "<span>t2</span>"
	+ "<input class='t2start' placeholder='HHmm'>-<input class='t2end' placeholder='HHmm'><br>"
	+ "<span>t3</span>"
	+ "<input class='t3start' placeholder='HHmm'>-<input class='t3end' placeholder='HHmm'><br>"
	+ "<span>t4</span>"
	+ "<input class='t4start' placeholder='HHmm'>-<input class='t4end' placeholder='HHmm'><br>"
	+ "<span>t5</span>"
	+ "<input class='t5start' placeholder='HHmm'>-<input class='t5end' placeholder='HHmm'><br>"
	+ "<span>t6</span>"
	+ "<input class='t6start' placeholder='HHmm'>-<input class='t6end' placeholder='HHmm'><br>"
	+ "<input class='settimezone_x1000' type='button' value='提交'>"
$(".form1").append(html);

}


//获取门禁时间组表单
function addform_gettimezone_X1000() {
	$(".form1").html("");
	html = "" + "<span>时间组序号</span>"
	+"<input class='timezoneno' placeholder='请输入1~255'><br>"
	+ "<input class='gettimezone_x1000' type='button' value='提交'>"
$(".form1").append(html);

}


//设置用户门禁时间段表单
function addform_setuserparsetime_X1000() {
	$(".form1").html("");
	html = "" + "<span>人员id</span>"
	+"<input class='userid'><br>"
	+ "<span>起始日期</span>"
	+ "<input class='start' placeholder='yyMMdd'><br>"
	+ "<span>结束日期</span>"
	+ "<input class='end' placeholder='yyMMdd'><br>"
	+ "<span>星期日</span>"
	+ "<input class='sun' placeholder='1~255'><br>"
	+ "<span>星期一</span>"
	+ "<input class='mon' placeholder='1~255'><br>"
	+ "<span>星期二</span>"
	+ "<input class='tue' placeholder='1~255'><br>"
	+ "<span>星期三</span>"
	+ "<input class='wed' placeholder='1~255'><br>"
	+ "<span>星期四</span>"
	+ "<input class='thu' placeholder='1~255'><br>"
	+ "<span>星期五</span>"
	+ "<input class='fri' placeholder='1~255'><br>"
	+ "<span>星期六</span>"
	+ "<input class='sat' placeholder='1~255'><br>"
	+ "<input class='setuserparsetime_x1000' type='button' value='提交'>"
$(".form1").append(html);

}


//获取用户门禁时间段表单
function addform_getuserparsetime_X1000() {
	$(".form1").html("");
	html = "" + "<span>人员id</span>"
	+"<input class='userid'><br>"
	+ "<input class='getuserparsetime_x1000' type='button' value='提交'>"
$(".form1").append(html);

}


//设置门禁参数表单
function addform_setdoorsetting_X1000() {
	$(".form1").html("");
	html = "" + "<span>OpenDoor_Delay</span><input class='OpenDoor_Delay' placeholder='秒数(数值)'><br>"
	+ "<span>DoorMagnetic_Type</span><input class='DoorMagnetic_Type' placeholder='no/open/close(三选一)'><br>"
	+ "<span>DoorMagnetic_Delay</span><input class='DoorMagnetic_Delay' placeholder='秒数(数值)'><br>"
	+ "<span>Anti_back</span><input class='Anti_back' placeholder='yes/no(二选一)'><br>"
	+ "<span>Alarm_Delay</span><input class='Alarm_Delay' placeholder='秒数(数值)'><br>"
	+ "<span>Use_Alarm</span><input class='Use_Alarm' placeholder='yes/no(二选一)'><br>"
	+ "<span>Wiegand_Type</span><input class='Wiegand_Type' placeholder='26/34(二选一)'><br>"
	+ "<span>Sleep_Time</span><input class='Sleep_Time' placeholder='分钟数(数值)'><br>"
	+ "<span>Screensavers_Time</span><input class='Screensavers_Time' placeholder='分钟数(数值)'><br>"
	+ "<span>Reverify_Time</span><input class='Reverify_Time' placeholder='分钟数(数值)'><br>"
	+ "<span>Glog_Warning</span><input class='Glog_Warning' placeholder='1~1000'><br>"
	+ "<span>Volume</span><input class='Volume' placeholder='0~10'><br>"
	+ "<input class='setdoorsetting_x1000' type='button' value='提交'>"
$(".form1").append(html);

}

//设置门控继电器状态 表单
function addform_setdoorstatus_v536() {
	$(".form1").html("");
	html = "" + "<span>设置门控继电器状态</span>"
	+"<select class='doorstatus'>"
	   +"<option value='open'>open</option>"
	   +"<option value='close'>close</option>"
	   +"<option value='open_close'>open_close</option>"
	+"</select><br>"   
	+ "<input class='setdoorstatus_v536' type='button' value='提交'>"
$(".form1").append(html);

}


function gettime(time) {
	var lyear = time.getFullYear();
	var lmonth = time.getMonth() + 1 > 9 ? time.getMonth() + 1 : "0"
			+ (time.getMonth() + 1);
	var ldate = time.getDate() > 9 ? time.getDate() : "0" + time.getDate();
	var lhours = time.getHours() > 9 ? time.getHours() : "0" + time.getHours();
	var lminutes = time.getMinutes() > 9 ? time.getMinutes() : "0"
			+ time.getMinutes();
	time = lyear + "-" + lmonth + "-" + ldate + " " + lhours + ":" + lminutes;
	return time;
}

function gettime2(time) {
	
	var lhours = time.getHours() > 9 ? time.getHours() : "0" + time.getHours();
	var lminutes = time.getMinutes() > 9 ? time.getMinutes() : "0"
			+ time.getMinutes();
	time = lhours +  lminutes;
	return time;
}
// 文件转base_64 readAsDataURL
// 若取出数据库数据转换图片时字符串前面要加头信息作为url可以直接赋给img的src(图片头：data:image/jpg;base64,)
function uploadFile(file, dom) {
	var reader = new FileReader();
	reader.readAsDataURL(file);
	reader.onload = function(e) {
		var base_64 = this.result;
		dom.val(base_64.split(",")[1]);

	}
}

//文件转 readAsArrayBuffer() /x1000
function uploadFile2(file, dom) {
	var reader = new FileReader();
	reader.readAsArrayBuffer(file);
	//reader.readAsDataURL(file);
	reader.onload = function(e) {
		var data = this.result;
		data = String.fromCharCode.apply(null, new Uint8Array(data))
		dom.val(data);
		  }

	}


