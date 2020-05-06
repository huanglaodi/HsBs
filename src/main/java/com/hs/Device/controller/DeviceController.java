package com.hs.Device.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hs.Device.mapper.DeviceMapper;
import com.hs.Device.mapper.TaskMapper;
import com.hs.Device.mapper.UserSendMapper;
import com.hs.Device.model.Device;
import com.hs.Device.model.Task;
import com.hs.Device.model.User;
import com.hs.Device.model.UserSend;
import com.hs.Device.service.V536;
import com.hs.Device.service.X1000;
import com.hs.Device.utils.Utils;

@Controller
public class DeviceController {
	int i = 1;
	@Autowired
	TaskMapper taskMapper;
	@Autowired
	DeviceMapper deviceMapper;
	@Autowired
	V536 v536;
	@Autowired
	X1000 x1000;
	@Autowired
	UserSendMapper usersendMapper;

	public User user;

	byte[] bytEmpty = new byte[0];
	String requestCode;
	String asTransId;
	String devicename;
	String deviceid;
	String loknoString;
	String blklenString;
	String cmdreturncode = "";
	String jsonmessage;
	String devmodel;
	int count = 1;
	String[] noresultcmds = { "SET_TIME", "RESET_FK", "SET_FK_NAME", "CLEAR_ENROLL_DATA", "CLEAR_LOG_DATA","SET_TIMEZONE","SET_USER_PASSTIME","RESET_DEVICE",
			"SET_USER_INFO", "SET_WEB_SERVER_INFO", "UPDATE_FIRMWARE" ,"ENTER_ENROLL","SET_DEVICE_SETTING","CLEAR_MANAGER","SET_COMMAND","GET_ALL_USER_INFO","SET_DOOR_STATUS"};
	String[] hasresultcmds = { "GET_USER_ID_LIST", "GET_USER_INFO", "GET_LOG_DATA", "GET_DEVICE_STATUS",
			"DELETE_USER","GET_DEVICE_INFO","GET_DEVICE_SETTING","GET_TIMEZONE","GET_USER_PASSTIME" };

	//机器心跳入口，
	@RequestMapping("/")
	@ResponseBody
	public void sendxintiao(HttpServletRequest request,HttpServletResponse response) {
		try {
			asTransId = request.getHeader("trans_id");
			devmodel = request.getHeader("dev_model");
			Device device = new Device();
			if (devmodel == null) {
				device = getdevice_x1000(asTransId, request, response);
			} else if (devmodel.equals("V536")) {
				device = getdevice_v536(asTransId, request, response);
				
			}
			deviceid = device.getDevid();
			devmodel = device.getModel();
			// 若没有任务，查询数据库是否有此机器任务
			if (asTransId == null || asTransId.trim().equals("ReceiveCommandAction")) {
				// 查询是否有我的任务处于WAIT
				List<Task> tasklist = taskMapper.selectbydeviceid(deviceid);
				// 没有此设备任务
				if (tasklist.size() == 0) {
					
				} else {
					Task task = tasklist.get(0);
					String taskid = task.getTaskid() + "";
					String cmdname = task.getInstruct().getInstructname();

					// 修改任务状态RUN
					task.setState("RUN");
					task.setIssend(1);
					taskMapper.updateTask(task);

					// 根据指令名称，型号发送不同指令,同步时间
					if (cmdname.equals("SET_TIME")) {
						// V536
						if (devmodel != null) {

							if (devmodel.equals("V536")) {
								v536.updatetime_V536(task, request, response);
							}
						} else {
							x1000.updatetime_X1000(task, request, response);
						}
					}
					// 重启机器,没有反馈直接更改任务状态。
					else if (cmdname.equals("RESET_FK")) {
						// V536
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.restart_V536(task, request, response);
							}
						} else {
							x1000.restart_X1000(task, request, response);
						}
						task.setState("OK");
						task.setEndtime(new Timestamp(new Date().getTime()));
						taskMapper.updateTask(task);

					}
					// 删除人员
					else if (cmdname.equals("DELETE_USER")) {
						// V536删除一组
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.deleteuser_V536(task, request, response);
							}
						} else {
							// X1000只能删除一个，
							x1000.deleteuser_X1000(task, request, response);

						}

					}
					// 清除管理员
					else if (cmdname.equals("CLEAR_MANAGER")) {
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.clearmanager_V536(task, request, response);
							}
						} else {
							// X1000只能删除一个，
							x1000.clearmanager_X1000(task, request, response);

						}

					}
					// 获得注册人员列表
					else if (cmdname.equals("GET_USER_ID_LIST")) {
						// V536
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.getuserlist_V536(task, request, response);
							}
						} else {
							// X1000
							x1000.getuserlist_X1000(task, request, response);
						}
					}
					// 获取记录数据
					else if (cmdname.equals("GET_LOG_DATA")) {
						// V536
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.getloglist_V536(task, request, response);
							}
						} else {
							// X1000
							x1000.getloglist_X1000(task, request, response);
						}
					}
					// 变更考勤机名称
					else if (cmdname.equals("SET_FK_NAME")) {
						// V536暂没有此指令
						if (devmodel != null) {
							if (devmodel.equals("V536")) {

							}
						} else {
							// X1000
							x1000.setfkname_X1000(task, request, response);
						}

					}
					// 删除所有记录数据
					else if (cmdname.equals("CLEAR_LOG_DATA")) {
						// V536
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.clearlogdata_V536(task, request, response);
							}
						} else {
							// X1000
							x1000.clearlogdata_X1000(task, request, response);
						}

					}
					// 删除所有注册数据
					else if (cmdname.equals("CLEAR_ENROLL_DATA")) {
							// X1000
							x1000.clearenrolldata_X1000(task, request, response);
					}
					// 获取考勤机状况信息
					else if (cmdname.equals("GET_DEVICE_STATUS")) {
						// V536暂没有此指令
						if (devmodel != null) {
							if (devmodel.equals("V536")) {

							}
						} else {
							// X1000
							x1000.getdevicestatus_X1000(task, request, response);
						}
					}
					// 获取考勤机信息
					else if (cmdname.equals("GET_DEVICE_INFO")) {
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.getdeviceinfo_V536(task, request, response);
							}
						} else {
							// X1000 无此指令
							// x1000.getdeviceinfo_X1000(task, request, response);
						}
					}
					// 获取考勤机设置参数v536   /  获取门禁机门禁参数 x1000
					else if (cmdname.equals("GET_DEVICE_SETTING")) {
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.getdevicesetting_V536(task, request, response);
							}
						} else {
							// X1000
							 x1000.getdoorsetting_X1000(task, request, response);
						}
					}
					// 设置考勤机参数,v536/   设置门禁机门禁参数 x1000
					else if (cmdname.equals("SET_DEVICE_SETTING")) {
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.setdevicesetting_V536(task, request, response);
							}
						} else {
							// X1000 
							 x1000.setdoorsetting_X1000(task, request, response);
						}
					}
					// 向考勤机同步写入用户数据
					else if (cmdname.equals("SET_USER_INFO")) {
						// V536
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.insrtpeople_V536(task, request, response);
							}
						} else {
							// X1000
							x1000.insrtpeople_X1000(task, request, response);
						}

					}
					// 获取用户信息
					else if (cmdname.equals("GET_USER_INFO")) {
						// V536
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.getuserinfo_V536(task, request, response);
							}
						} else {
							// X1000
							x1000.getuserinfo_X1000(task, request, response);
						}
					}
					// 获取所有用户信息
					else if (cmdname.equals("GET_ALL_USER_INFO")) {
						// V536无此指令
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								
							}
						} else {
							// X1000
							x1000.getalluserinfo_X1000(task, request, response);
						}
					}
					// 服务器ip及端口变更
					else if (cmdname.equals("SET_WEB_SERVER_INFO")) {
						// V536 暂无此指令
						if (devmodel != null) {
							if (devmodel.equals("V536")) {

							}
						} else {
							// X1000
							x1000.setwebserverinfo_X1000(task, request, response);
						}
					}
					// 更新固件
					else if (cmdname.equals("UPDATA_FIRMWARE")) {
						// V536
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.updatefirmware_V536(task, request, response);
							}
						} else {
							// X1000
							x1000.updatefirmware_X1000(task, request, response);
						}
					}
					// 远程注册,指令不同
					else if (cmdname.equals("ENTER_ENROLL") || cmdname.equals("SET_COMMAND")){
						// V536
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.enterenroll_V536(task, request, response);
							}
						} else {
							// X1000
							x1000.enterenroll_X1000(task, request, response);
						}
					}
					// 设置门禁时间组
					else if (cmdname.equals("SET_TIMEZONE")){
						// V536无此指令
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
							}
						} else {
							// X1000
							x1000.settimezone_X1000(task, request, response);
						}
					}
					// 获取门禁时间组
					else if (cmdname.equals("GET_TIMEZONE")){
						// V536无此指令
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
							}
						} else {
							// X1000
							x1000.gettimezone_X1000(task, request, response);
						}
					}
					// 设置用户门禁时间段
					else if (cmdname.equals("SET_USER_PASSTIME")){
						// V536无此指令
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
							}
						} else {
							// X1000
							x1000.setuserparsetime_X1000(task, request, response);
						}
					}
					// 获取用户门禁时间段
					else if (cmdname.equals("GET_USER_PASSTIME")){
						// V536无此指令
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
							}
						} else {
							// X1000
							x1000.getuserparsetime_X1000(task, request, response);
						}
					}
					// 恢复出厂设置
					else if (cmdname.equals("RESET_DEVICE")){
						// V536
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.returnold(task, request, response);
							}
						} else {
							// X1000 无此指令
						}
						task.setState("OK");
						task.setEndtime(new Timestamp(new Date().getTime()));
						taskMapper.updateTask(task);
					}
					// 设置门控状态
					else if (cmdname.equals("SET_DOOR_STATUS")){
						// V536
						if (devmodel != null) {
							if (devmodel.equals("V536")) {
								v536.setdoorstatus_v536(task, request, response);
							}
						} else {
							// X1000 无此指令
							
						}
						
					}

				}
			}
			// 实时传输登记人员
			else if (asTransId.trim().equals("RTEnrollDataAction")) {

				// V536
				if (devmodel != null) {
					if (devmodel.equals("V536")) {
						v536.dengji_V536(deviceid, request, response);
					}
				} else {
					// X1000
					x1000.dengji_X1000(deviceid, request, response);
				}
			}
			// 实时传输出入记录
			else if (asTransId.trim().equals("RTLogSendAction")){
				// V536
				if (devmodel != null) {
					if (devmodel.equals("V536")) {
						v536.churu_V536(deviceid, request, response);
					}
				} else {
					// X1000
					x1000.churu_X1000(deviceid, request, response);
				}
			}
			// 若有任务id,根据机器返回结果判断此任务是ok,还是error
			else {
				String isok = request.getHeader("cmd_return_code");
				if (isok != null) {
					Task task = taskMapper.getTaskbytaskid(Integer.valueOf(asTransId));
					if (!isok.equals("OK")) {
						//执行失败
						task.setState("ERROR");
						task.setEndtime(new Timestamp(new Date().getTime()));
						taskMapper.updateTask(task);
					} else {
						
						String cmdname = task.getInstruct().getInstructname();
						// 根据指令判断获取什么内容,若是以下指令则不需要记录什么数据
						if (Arrays.asList(noresultcmds).contains(cmdname)) {

						} else if (Arrays.asList(hasresultcmds).contains(cmdname)) {
							// 根据指令往UserGet中写入此次任务上传的数据，
							// 获得注册人员列表
							if (cmdname.equals("GET_USER_ID_LIST")) {
								// V536
								if (devmodel != null) {
									if (devmodel.equals("V536")) {
										v536.insertuseridlist_V536(task, request, response);
									}
								} else {
									// X1000
									x1000.insertuseridlist_X1000(task, request, response);
								}
							}
							// 获取记录数据
							else if (cmdname.equals("GET_LOG_DATA")) {
								// V536
								if (devmodel != null) {
									if (devmodel.equals("V536")) {
										v536.insertlogdata_V536(task, request, response);
									}
								} else {
									// X1000
									x1000.insertlogdata_X1000(task, request, response);
								}
							}
							// 获取考勤机状况信息
							else if (cmdname.equals("GET_DEVICE_STATUS")) {
								// V536 暂无此指令
								if (devmodel != null) {
									if (devmodel.equals("V536")) {

									}
								} else {
									// X1000
									x1000.insertdevicestatus_X1000(task, request, response);
								}
							}
							// 获取设备基本信息
							else if (cmdname.equals("GET_DEVICE_INFO")) {
								// V536
								if (devmodel != null) {
									if (devmodel.equals("V536")) {
										v536.insertdeviceinfo_V536(task, request, response);
									}
								} else
								// X1000无此指令
								{

								}
							}
							// 获取时间组
							else if (cmdname.equals("GET_TIMEZONE")) {
								// V536
								if (devmodel != null) {
									if (devmodel.equals("V536")) {
									}
								} else
								{
									x1000.inserttimezone_X1000(task,request,response);
								}
							}
							// 获取用户门禁时间段
							else if (cmdname.equals("GET_USER_PASSTIME")) {
								// V536
								if (devmodel != null) {
									if (devmodel.equals("V536")) {
									}
								} else
								{
									x1000.insertparsetime_X1000(task,request,response);
								}
							}
							// 获取门禁参数X1000    /获取设备设置参数V536
							else if (cmdname.equals("GET_DEVICE_SETTING")) {
								// V536
								if (devmodel != null) {
									if (devmodel.equals("V536")) {
										v536.insertdevicesetting_V536(task, request, response);
									}
								} else
								{
									x1000.insertdoorsetting_X1000(task,request,response);
								}
							}
							// 获取考勤机用户信息
							else if (cmdname.equals("GET_USER_INFO")) {
								// V536
								if (devmodel != null) {
									if (devmodel.equals("V536")) {
										v536.insertuserinfo_V536(task, request, response);
									}
								} else {

									byte[] requestdata = new byte[0];
									requestdata = Utils.GetRequestStreamBytes(request);
									// service中获取不到
									x1000.insertuserinfo_X1000(task, requestdata, response);
								}
							}
							// 删除人员的反馈/V536
							else if (cmdname.equals("RESET_DEVICE")) {
								if (devmodel != null) {
									if (devmodel.equals("V536")) {
										v536.insertdlerror_V536(task, request, response);
									}
								}
							}
						}
						// 更改此任务状态，
						task.setState("OK");
						task.setEndtime(new Timestamp(new Date().getTime()));
						taskMapper.updateTask(task);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 获取心跳信息 x1000
	public Device getdevice_x1000(String transid, HttpServletRequest request, HttpServletResponse response) {

		Device device = new Device();
		if (transid ==null || transid.equals("ReceiveCommandAction")) {
			// 解析request输入流
			byte[] requestdata = Utils.GetRequestStreamBytes(request);
			Map<String, Object> map = Utils.GetStringAnd1stBinaryFromBSCommBuffer(requestdata);
			String jsonstr = (String) map.get("jsonstr");
			JSONObject json = JSONObject.parseObject(jsonstr);
			device.setDevname(json.getString("fk_name"));
			String fktime = json.getString("fk_time");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date fkdate = new Date();
			try {
				fkdate = sdf.parse(fktime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			device.setFktime(new Timestamp(fkdate.getTime()));
			JSONObject fkinfo = json.getJSONObject("fk_info");
			device.setFacedataver(fkinfo.getString("face_data_ver"));
			device.setFirmware(fkinfo.getString("firmware"));
			device.setFirmwarefilename(fkinfo.getString("firmware_filename"));
			device.setFkbindatalib(fkinfo.getString("fk_bin_data_lib"));
			device.setFpdataver(fkinfo.getString("fp_data_ver"));
			JSONArray supports = (JSONArray) fkinfo.get("supported_enroll_data");
			List<String> sups =supports.toJavaList(String.class);
			String support="";
			for(int i =0;i<sups.size();i++) {
				if(i<sups.size()-1) {
					support += sups.get(i)+",";
				}else {
					support += sups.get(i);
				}
			}
			device.setSupport(support);

		}
		device.setDevid(request.getHeader("dev_id"));
		device.setIsonline(2);
		device.setLasttime(new Timestamp(new Date().getTime()));
		device.setModel(request.getHeader("dev_model"));

		Device dv = deviceMapper.selectdevicebyid(device.getDevid());
		if (dv == null) {
			deviceMapper.insertdevice(device);
		} else {
			deviceMapper.updatedevice(device);
			device = deviceMapper.selectdevicebyid(device.getDevid());
		}
		return device;
	}

	// 获取心跳信息 v536
	public Device getdevice_v536(String transid,HttpServletRequest request, HttpServletResponse response) {
		  Device device = new Device(); 
		  String fktime = "";
		  if(transid ==null || transid.equals("ReceiveCommandAction")) { // 解析request输入流 获取body中的json字符串
		  try { 
			  byte[] requestdata = Utils.GetRequestStreamBytes(request); 
			  JSONObject jsonstr = JSONObject.parseObject(new String(requestdata,"UTF-8")); 
			  fktime =jsonstr.getString("time"); 
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			  Date fkdate = new Date(); 
		      fkdate =sdf.parse(fktime); 
		      device.setFktime(new Timestamp(fkdate.getTime())); 
		  } catch (Exception e) { 
			  e.printStackTrace(); 
		  }
		  }
	      device.setDevid(request.getHeader("dev_id")); 
	      device.setIsonline(2);
		  device.setLasttime(new Timestamp(new Date().getTime()));
		  device.setModel(request.getHeader("dev_model"));
		  
		  Device dv = deviceMapper.selectdevicebyid(device.getDevid()); 
		  if(dv==null) {
		     deviceMapper.insertdevice(device); 
		     //首次连接服务器，创建获取机器信息的任务
		     Task task = new Task();
		     task.setStarttime(new Timestamp(new Date().getTime()));
			 task.setIssend(1);
		     task.setDeviceid(device.getDevid());
		     task.setUserid(3);
		     task.setState("WAIT");
		     task.setInstructid(17);
		     taskMapper.insertTask(task);
		     UserSend usersend = new UserSend();
		     usersend.setTaskid(task.getTaskid());
		     usersendMapper.insertUsersend(usersend);
		   
		  }else {
		     deviceMapper.updatedevice(device); 
		  }
 		 return device;
 	}
 	
	
	}
