package com.hs.Device.utils.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hs.Device.utils.entity.Command;
import com.hs.Device.utils.entity.Device;
import com.hs.Device.utils.entity.EnrollData;
import com.hs.Device.utils.entity.FKDataHS100_GLog;
import com.hs.Device.utils.entity.FKDataHS100_UserIdInfo;
import com.hs.Device.utils.entity.FKDataHS101_GLog;
import com.hs.Device.utils.entity.FKDataHS101_UserIdInfo;
import com.hs.Device.utils.entity.FKDataHS102_GLog;
import com.hs.Device.utils.entity.FKDataHS102_UserIdInfo;
import com.hs.Device.utils.entity.FKDataHS103_GLog;
import com.hs.Device.utils.entity.FKDataHS103_UserIdInfo;
import com.hs.Device.utils.entity.FKDataHS105_GLog;
import com.hs.Device.utils.entity.FKDataHS105_UserIdInfo;
import com.hs.Device.utils.entity.FKDataHS200_GLog;
import com.hs.Device.utils.entity.FKDataHS200_UserIdInfo;


public class CommandUtil {
	public static byte[] ConcateByteArray(byte[] abytDest, byte[] abytSrc) {
		int len_dest = abytDest.length + abytSrc.length;
		if (abytSrc.length == 0)
			return null;
		byte[] bytTmp = new byte[len_dest];
		System.arraycopy(abytDest, 0, bytTmp, 0, abytDest.length);
		System.arraycopy(abytSrc, 0, bytTmp, abytDest.length, abytSrc.length);
		return bytTmp;
	}

	public static String GetStringFromBSCommBuffer(byte[] abytBSComm)
	{
		if (abytBSComm.length < 4)
			return "";
		try
		{
			String sRet;

			int lenText = MyUtil.byte2int(abytBSComm);
			if (lenText > abytBSComm.length - 4)
				return "";

			if (lenText == 0)
				return "";

			if (abytBSComm[4 + lenText - 1] == 0) // if last value of string buufer is 0x0
				sRet = MyUtil.byte2String(abytBSComm, 4, lenText - 1);
			else
				sRet = MyUtil.byte2String(abytBSComm, 4, lenText);

			return sRet;
		}
		catch(Exception e)
		{
			return "";
		}
	}
	
	public static void ReceiveCmd(LogUtil log, Device dev, Command cmd) {
		final String csFuncName = "ReceiveCmd";

		cmd.setsResponse("ERROR");

		Connection sqlConn = null;

		log.i(csFuncName, "0 - Start");

		try
		{
			sqlConn = DBUtil.getConnection();
			log.i(csFuncName, "1 - Db connected");
		}
		catch (Exception e)
		{
			log.e(csFuncName, "Error - Not connected to db" + e.toString() + ", ConnString=" + sqlConn.toString());
			cmd.setsResponse("ERROR_DB_CONNECT");
			return;
		}

		log.i(csFuncName, "2");

		if (CheckResetCmd(log, dev.getDevId(), cmd))
		{
			log.e(csFuncName, "2.1 - " + cmd.getTransId());
			cmd.setsResponse("RESET_FK");
			return;
		}

		log.i(csFuncName, "3");

		UpdateFKDeviceStatus(log, dev);
		CallableStatement cstmt = null;
		try
		{
			log.i(csFuncName, "4");

			String sqlCmd = "{call dbo.usp_receive_cmd(?,?,?,?)}";
			cstmt = sqlConn.prepareCall(sqlCmd);
			cstmt.setString("dev_id", dev.getDevId());
			cstmt.registerOutParameter("trans_id", java.sql.Types.VARCHAR);
			cstmt.registerOutParameter("cmd_code", java.sql.Types.VARCHAR);
			cstmt.registerOutParameter("cmd_param_bin", java.sql.Types.LONGVARBINARY);
			cstmt.execute();

			log.i(csFuncName, "5");

			cmd.setTransId(cstmt.getString("trans_id"));            

			if (cmd.getTransId().length() == 0)
			{
				log.i(csFuncName, "5 - no cmd");
				cmd.setsResponse("ERROR_NO_CMD");
			}
			else
			{
				cmd.setCmdCode(cstmt.getString("cmd_code"));
				cmd.setBytCmd(cstmt.getBytes("cmd_param_bin"));

				log.i(csFuncName, "6 - trans_id:" + cmd.getTransId() + "cmd_code:" + cmd.getCmdCode());

				if (cmd.getCmdCode().equals("SET_TIME"))
				{
					String sCmdParam = "{\"time\":\"" + MyUtil.GetFKTimeString14(new Date()) + "\"}";
					CreateBSCommBufferFromString(sCmdParam, cmd);
				}
				else if (cmd.getCmdCode().equals("UPDATE_FIRMWARE"))
				{
					//TODO 更新固件
					if (MakeUpdateFirmwareCmdParamBin(log, dev.getDevId(), dev.getFk_info(), cmd) == false)
					{
						log.e(csFuncName, "7 - error get cmd param(UPDATE_FIRMWARE)");
						cmd.setBytCmd(new byte[0]);
						cmd.setsResponse("ERROR_GET_PARAM");
						return;
					}
				}
				else if ((cmd.getCmdCode().equals("SET_ENROLL_DATA")) ||(cmd.getCmdCode().equals("SET_USER_INFO")))
				{
					//TODO  发送员工信息
					if (ConvertEnrollDataInCmdParamBin(log, dev, cmd) == false)
					{
						log.e(csFuncName, "8 - error get cmd param(SET_USER_INFO)");
						cmd.setBytCmd(new byte[0]);
						cmd.setsResponse("ERROR_GET_PARAM");
						return;
					}
				}
				cmd.setsResponse("OK");
			}

			log.i(csFuncName, "11");
			return;
		}
		catch (Exception e)
		{
			log.e(csFuncName, "Except - 1 - " + e.toString());

			cmd.setBytCmd(new byte[0]);
			cmd.setCmdCode("");
			cmd.setTransId("");
			cmd.setsResponse("ERROR");
		}finally {
			DBUtil.close(cstmt);
			DBUtil.close(sqlConn);
		}
	}

	private static boolean MakeUpdateFirmwareCmdParamBin(LogUtil log, String devId, String asDevInfo, Command cmd){
		final String csFuncName = "MakeUpdateFirmwareCmdParamBin";

		if (!cmd.getCmdCode().equals("UPDATE_FIRMWARE"))
			return true;

		log.i(csFuncName, "1 - ");

		FileInputStream fs = null;
		try{
			JSONObject jobjDevInfo = new JSONObject(asDevInfo);
			String sPrefix = jobjDevInfo.getString("firmware_filename");

			log.i(csFuncName, "2 - prefix:" + sPrefix);

			File f = new File(Config.getString("FirmwareBinDir"));
			log.i(csFuncName, "3 - firmware file Dir:" + f.getAbsolutePath());
			File[] FirmwareFiles = f.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.startsWith(sPrefix) && name.endsWith(".bin"))
						return true;
					return false;
				}
			});

			if (FirmwareFiles == null || FirmwareFiles.length == 0)
			{
				String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_NO_FIRMWARE_FILE', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
				DBUtil.update(sSql, null);
				return false;
			}

			log.i(csFuncName, "4 - firmware file:" + FirmwareFiles[0].getName());

			byte[] bytFirmwareBin = new byte[(int) FirmwareFiles[0].length()];
			fs = new FileInputStream(FirmwareFiles[0]);
			int offset = 0;  
			int numRead = 0; 
			while (numRead >= 0) { 
				if(offset < bytFirmwareBin.length) break;
				numRead = fs.read(bytFirmwareBin, offset, bytFirmwareBin.length - offset);
				offset += numRead;  
			}  
			log.i(csFuncName, "5 - ");

			String sFileTitle = FirmwareFiles[0].getName();
			String sCmdParam = "{\"firmware_file_name\":\"" + sFileTitle + "\",\"firmware_bin_data\":\"BIN_1\"}";
			CreateBSCommBufferFromString(sCmdParam, cmd);
			AppendBinData(cmd, bytFirmwareBin);
			return true;
		}catch(Exception e){
			log.e(csFuncName, "Exception : " + e.getMessage());
			return false;
		}finally {
			if(fs != null)	try {fs.close();} catch (IOException e) {e.printStackTrace();}
		}
	}

	private static boolean ConvertEnrollDataInCmdParamBin(LogUtil log, Device dev, Command cmd) {
		final String csFuncName = "ConvertEnrollDataInCmdParamBin";

		log.i(csFuncName, "1 - ");

		try{
			JSONObject jobjDevInfo = new JSONObject(dev.getFk_info());
			int DstFpDataVer = jobjDevInfo.getInt("fp_data_ver");
			log.i(csFuncName, "1 - 1 - FpDataVer=" + DstFpDataVer);
			if (DstFpDataVer < 1 || DstFpDataVer > 10000)
			{
				String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_INVALID_FK_INFO', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
				DBUtil.update(sSql, null);
				return false;
			}
			Command innerCmd = new Command();
			if (cmd.getCmdCode().equals("SET_ENROLL_DATA"))
			{
				log.i(csFuncName, "2 - 1 - ");
				String sCmdParam = GetStringFromBSCommBuffer(cmd.getBytCmd());
				if (sCmdParam.length() == 0)
				{
					log.e(csFuncName, "2 - 1.1 - ");
					String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_INVALID_PARAM', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
					DBUtil.update(sSql, null);
					return false;
				}
				log.i(csFuncName, "2 - 2 - ");
				JSONObject jobjCmdParam = new JSONObject(sCmdParam);
				int bkNo = jobjCmdParam.getInt("backup_number");
				if (bkNo > 9)
				{
					log.i(csFuncName, "2 - 2.1 - ");
					return true;
				}
				log.i(csFuncName, "2 - 3 - ");
				byte[] bytFpData = GetBinDataFromBSCommBinData(1, cmd.getBytCmd());
				if (bytFpData.length == 0)
				{
					log.e(csFuncName, "2 - 3.1 - ");
					String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_INVALID_PARAM', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
					DBUtil.update(sSql, null);
					return false;
				}
				log.i(csFuncName, "2 - 4 - DstFpDataVer :" + DstFpDataVer);
				innerCmd.clear();
				//TODO jni
				if (!ConvertFpDataForDestFK(log, bytFpData, DstFpDataVer, innerCmd))
				{
					log.e(csFuncName, "2 - 4.1 - ");
					String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_CONVERT_FP', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
					DBUtil.update(sSql, null);
					return false;
				}
				byte[] bytFpDataConv = innerCmd.getBytCmd();

				log.i(csFuncName, "2 - 5 - ");
				innerCmd.clear();
				if (!CreateBSCommBufferFromString(sCmdParam, innerCmd))
				{
					String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_UNKNOWN', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
					DBUtil.update(sSql, null);
					return false;
				}
				AppendBinData(innerCmd, bytFpDataConv);
				log.i(csFuncName, "2 - 6 - ");
				return true;
			}
			else if (cmd.getCmdCode().equals("SET_USER_INFO"))
			{
				int k, m;

				log.i(csFuncName, "3 - 1 - ");
				String sCmdParam = GetStringFromBSCommBuffer(cmd.getBytCmd());
				if (sCmdParam.length() == 0)
				{
					log.e(csFuncName, "3 - 1.1 - ");
					String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_INVALID_PARAM', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
					DBUtil.update(sSql, null);
					return false;
				}

				log.i(csFuncName, "3 - 2 - ");
				List<EnrollData> listEnrollData = new ArrayList<EnrollData>();
				String user_id;
				String user_name;
				String user_privilege;
				byte[] user_photo = new byte[0];

				try
				{
					JSONObject jobjCmdParam = new JSONObject(sCmdParam);
					user_id = jobjCmdParam.getString("user_id");
					user_name = jobjCmdParam.getString("user_name");
					user_privilege = jobjCmdParam.getString("user_privilege");
					if (!jobjCmdParam.isNull("user_photo"))
					{
						log.i(csFuncName, "3 - 3 - ");
						String  sTemp;
						sTemp = jobjCmdParam.getString("user_photo");
						m = Integer.parseInt(sTemp.substring(4, sTemp.length()));
						if (m >= 1)
							user_photo = GetBinDataFromBSCommBinData(m, cmd.getBytCmd());
					}

					log.i(csFuncName, "3 - 4 - ");
					EnrollData ed;
					JSONObject jobjOneED;
					if(jobjCmdParam.has("enroll_data_array") && !jobjCmdParam.isNull("enroll_data_array")){
						JSONArray jarrEnrollData = jobjCmdParam.getJSONArray("enroll_data_array");
						for (k = 0; k < jarrEnrollData.length(); k++)
						{
							String  sTemp;
							byte[] bytTemp;
							jobjOneED = jarrEnrollData.getJSONObject(k);
							ed = new EnrollData();
							ed.setBackupNumber(Integer.parseInt(jobjOneED.getString("backup_number")));

							log.i(csFuncName, "3 - 5 - k=" + k);
							sTemp = jobjOneED.getString("enroll_data");
							if (sTemp.length() < 5)
							{
								log.e(csFuncName, "3 - 5.1 - k=" + k);
								String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_INVALID_PARAM', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
								DBUtil.update(sSql, null);
								return false;
							}

							log.i(csFuncName, "3 - 6 - k=" + k);
							m = Integer.parseInt(sTemp.substring(4));
							if (m < 1)
							{
								log.e(csFuncName, "3 - 6.1 - k=" + k);
								String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_INVALID_PARAM', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
								DBUtil.update(sSql, null);
								return false;
							}

							log.i(csFuncName, "3 - 7 - k=" + k +" m="+ m);
							bytTemp = GetBinDataFromBSCommBinData(m, cmd.getBytCmd());
							if ((ed.getBackupNumber() >= 0) && (ed.getBackupNumber() <= 9))
							{
								log.i(csFuncName, "3 - 8 - k=" + k + " - src_size=" + bytTemp.length);
								innerCmd.clear();
								if (!ConvertFpDataForDestFK(log, bytTemp, DstFpDataVer, innerCmd))
								{
									log.e(csFuncName, "3 - 8.1 - k=" + k);
									String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_INVALID_PARAM', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
									DBUtil.update(sSql, null);
									return false;
								}
								ed.setBytData(innerCmd.getBytCmd());
								log.i(csFuncName, "3 - 8 - 1 - k=" + k + " - conv_size=" + ed.getBytData().length);
							} 
							else 
							{
								log.i(csFuncName, "3 - 9 - k=" + k);
								ed.setBytData(bytTemp);
							}
							log.i(csFuncName, "3 - 10 - k=" + k);
							listEnrollData.add(ed);
						}
					}
				}catch(NoClassDefFoundError ex){
					log.e(csFuncName, System.getProperty("java.library.path"));
					log.e(csFuncName, "NoClassDefFoundError - " + ex.toString());
					String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_INVALID_PARAM', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
					DBUtil.update(sSql, null);
					return false;
				}
				catch(Exception ex)
				{
					log.e(csFuncName, "3 - except - " + ex.toString());
					String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_INVALID_PARAM', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
					DBUtil.update(sSql, null);
					return false;
				}

				log.i(csFuncName, "3 - 11 - ");
				if (listEnrollData.size() == 0)
				{
					log.i(csFuncName, "3 - 11 - 1");
					return true;
				}

				log.i(csFuncName, "3 - 12 - ");
				JSONObject jobjCmdParamNew = new JSONObject();
				jobjCmdParamNew.put("user_id", user_id);
				jobjCmdParamNew.put("user_name", user_name);
				jobjCmdParamNew.put("user_privilege", user_privilege);

				log.i(csFuncName, "3 - 13 - ");
				JSONArray jarrED = new JSONArray();
				EnrollData ed1 = null;
				m = 1;
				for (k = 0; k < listEnrollData.size(); k++)
				{
					JSONObject jobjOneED = new JSONObject();
					ed1 = listEnrollData.get(k);
					jobjOneED.put("backup_number", ed1.getBackupNumber());
					String sTemp = "BIN_" + m;
					jobjOneED.put("enroll_data", sTemp);
					jarrED.put(jobjOneED);
					m++;
				}
				jobjCmdParamNew.put("enroll_data_array", jarrED);

				log.i(csFuncName, "3 - 14 - ");
				if (user_photo.length > 0)
					jobjCmdParamNew.put("user_photo", "BIN_" + m);

				log.i(csFuncName, "3 - 15 - ");
				sCmdParam = jobjCmdParamNew.toString();
				//				innerCmd.clear();
				//				cmd.clear();
				if (!CreateBSCommBufferFromString(sCmdParam, cmd))
				{
					log.e(csFuncName, "3 - 15.1 - ");
					String sSql = "UPDATE tbl_fkcmd_trans SET return_code='ERROR_UNKNOWN', status='CANCELLED', update_time = GETDATE() WHERE trans_id='" + cmd.getTransId() + "'";
					DBUtil.update(sSql, null);
					return false;
				}
				for (k = 0; k < listEnrollData.size(); k++)
				{
					ed1 = listEnrollData.get(k);
					log.i(csFuncName, "3 - 15.2 - size = " + ed1.getBytData().length);
					AppendBinData(cmd, ed1.getBytData());
				}
				log.i(csFuncName, "3 - 16 - ");
				if (user_photo.length > 0)
					AppendBinData(cmd, user_photo);

				log.i(csFuncName, "3 - 17 - ");
				return true;

			}
			else
			{
				log.i(csFuncName, "3 - 18 - ");
				return true;
			}
		}catch (JSONException e) {
			log.e(csFuncName, "Except - 1 - " + e.toString());
			return false;
		}catch (Exception e) {
			log.e(csFuncName, "Except - 2 - " + e.toString());
			return false;
		}
	}

	private static void AppendBinData(Command Cmd, byte[] abytToAdd) {
		try
		{
			byte[] bytBSComm = Cmd.getBytCmd();
			byte[] bytToAdd = abytToAdd;

			if (bytToAdd.length == 0)
				return;

			int len_dest = bytBSComm.length + 4 + bytToAdd.length;
			byte[] bytRet = new byte[len_dest];
			byte[] bytAddLen = MyUtil.int2byte(bytToAdd.length);
			System.arraycopy(bytBSComm, 0, bytRet, 0, bytBSComm.length);
			System.arraycopy(bytAddLen, 0, bytRet, bytBSComm.length, 4);
			System.arraycopy(bytToAdd, 0, bytRet, bytBSComm.length + 4, bytToAdd.length);
			Cmd.setBytCmd(bytRet);
			return;
		}
		catch (Exception e)
		{
			return;
		}
	}

	//TODO jni转换指纹数据部分
	private static boolean ConvertFpDataForDestFK(LogUtil log, byte[] abytSrcFpData, int dstFpDataVer, Command Cmd) {

//		FpDataConv srcFpDataConv = new FpDataConv(abytSrcFpData,0,0); 
//		FpDataConv dstFpDataConv = new FpDataConv();
//		dstFpDataConv.setFpDataVer(dstFpDataVer);

		if (abytSrcFpData.length < 1)
			return false;
//		FpDataConv_JNI fpConv = new FpDataConv_JNI();
//		fpConv.Init();
//		if (fpConv.GetFpDataVersionAndSize(srcFpDataConv) != FpDataConv_JNI.FPCONV_ERR_NONE)
//			return false;
//		log.i(" ", "dataSrcVer =" + srcFpDataConv.getFpDataVer() + " dataDestVer =" + dstFpDataConv.getFpDataVer());
//		if ((srcFpDataConv.getFpDataVer() == 0) || (srcFpDataConv.getFpDataSize() == 0))
//			return false;
//		if (srcFpDataConv.getFpDataVer() == dstFpDataVer)
//		{
//			dstFpDataConv.setFpData(Arrays.copyOf(srcFpDataConv.getFpData(), 
//					srcFpDataConv.getFpData().length));
//			Cmd.setBytCmd(dstFpDataConv.getFpData());
//			return true;
//		}
//		if (fpConv.GetFpDataSize(dstFpDataConv) != FpDataConv_JNI.FPCONV_ERR_NONE)
//			return false;
//
//		dstFpDataConv.setFpData(new byte[dstFpDataConv.getFpDataSize()]);
//		if (fpConv.Convert(srcFpDataConv, dstFpDataConv) != FpDataConv_JNI.FPCONV_ERR_NONE)
//		{
//			dstFpDataConv.setFpData(new byte[0]);
//			return false;
//		}
//		Cmd.setBytCmd(dstFpDataConv.getFpData());
		Cmd.setBytCmd(abytSrcFpData);
		return true;
	}

	public static byte[] GetBinDataFromBSCommBinData(int anOrder, byte[] bytBSComm) {
		byte[] bytEmpty = new byte[0];

		try
		{
			// Convert SQLSever Data types to C# data types
			// SQLSever Data type (varbinary) <-> C# data type (byte [])
			System.out.println("bytBSComm = "+bytBSComm.length);
			if (bytBSComm.length < 4)
				return bytEmpty;

			if (anOrder < 0 || anOrder > 255)
				return bytEmpty;

			int orderBin;
			int posBin;
			int lenBin;
			int lenText = MyUtil.byte2int(bytBSComm);
			System.out.println("lenText = "+lenText);
			if (lenText > bytBSComm.length - 4)
				return bytEmpty;

			posBin = 4 + lenText;
			orderBin = 0;
			while (true)
			{
				System.out.println("posBin = "+posBin);
				if (posBin > bytBSComm.length - 4)
					return bytEmpty;

				lenBin = MyUtil.byte2int(bytBSComm, posBin);
				System.out.println("lenBin = "+lenBin);
				System.out.println("comparelen = "+(bytBSComm.length - posBin - 4));
				if (lenBin > bytBSComm.length - posBin - 4)
					return bytEmpty;

				orderBin++;
				if (orderBin == anOrder)
					break;

				posBin = posBin + 4 + lenBin;
			}

			byte[] bytRet = new byte[lenBin];
			System.arraycopy(
					bytBSComm, posBin + 4,
					bytRet, 0,
					lenBin);
			System.out.println("bytRet = "+bytRet.length);
			return bytRet;
		}
		catch (Exception e)
		{
			System.out.println("err");
			return bytEmpty;
		}
	}

	
	private static boolean CreateBSCommBufferFromString(String sCmdParam, Command cmd) {
		try
		{
			if (sCmdParam.length() == 0)
				return true;

			byte[] bytText = sCmdParam.getBytes("UTF-8");
			byte[] bytTextLen = int2byte(bytText.length + 1);
			cmd.setBytCmd(new byte[4 + bytText.length + 1]);
			System.arraycopy(bytTextLen,0,cmd.getBytCmd(),0,bytTextLen.length);
			System.arraycopy(bytText,0,cmd.getBytCmd(),4,bytText.length);
			cmd.getBytCmd()[4 + bytText.length] = 0;
			return true;
		}
		catch(Exception e)
		{
			cmd.setBytCmd(new byte[0]);
			return false;
		}
	}

	private static void UpdateFKDeviceStatus(LogUtil log, Device dev) {
		final String csFuncName = "UpdateFKDeviceStatus";
		Connection asqlConn = null;
		log.i(csFuncName, "0 - DevTime:" + dev.getFk_time() + ", DevId:" + dev.getDevId() + ", DevName:" 
				+ dev.getFk_name() + ", DevInfo:" + dev.getFk_info());
		PreparedStatement stmt = null;
		try
		{
			asqlConn = DBUtil.getConnection();
			log.i(csFuncName, "1");

			String sqlCmd = "{call dbo.usp_update_device_conn_status(?,?,?,?,?)}";
			stmt = asqlConn.prepareStatement(sqlCmd);
			stmt.setString(1, dev.getDevId());
			stmt.setString(2, dev.getFk_name());
			stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			stmt.setTimestamp(4, new Timestamp(MyUtil.ConvertFKTimeToNormalTime(dev.getFk_time())));
			stmt.setString(5, dev.getFk_info());

			stmt.executeUpdate();


			log.i(csFuncName, "2");
		}
		catch (Exception e)
		{
			log.e(csFuncName, "Except - 1 - " + e.toString());            
		}finally {
			DBUtil.close(stmt);
			DBUtil.close(asqlConn);
		}
	}


	public static byte[] int2byte(int res) {  
		byte[] targets = new byte[4];  

		targets[0] = (byte) (res & 0xff);// ���λ   
		targets[1] = (byte) ((res >> 8) & 0xff);// �ε�λ   
		targets[2] = (byte) ((res >> 16) & 0xff);// �θ�λ   
		targets[3] = (byte) (res >>> 24);// ���λ,�޷������ơ�   
		return targets;   
	}
	
	public static boolean CheckResetCmd(LogUtil log, String asDevId, Command cmd) {
		final String csFuncName = "CheckResetCmd";
		String sTransId = "";
		Connection asqlConn = null;
		log.i(csFuncName, "0 - dev_id:" + asDevId);

		try
		{
			asqlConn = DBUtil.getConnection();
			log.i(csFuncName, "1");

			String sqlcmd = "{call usp_check_reset_fk_cmd(?, ?)}";
			CallableStatement cstmt = asqlConn.prepareCall(sqlcmd);
			cstmt.setString("dev_id", asDevId);
			cstmt.registerOutParameter("trans_id", java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			sTransId = cstmt.getString("trans_id");

			DBUtil.close(cstmt);

			log.i(csFuncName, "2 - trans_id:" + sTransId);

			if (sTransId.length() == 0)
			{
				log.i(csFuncName, "3");                
				return false;
			}
			else
			{
				cmd.setTransId(sTransId);
				log.i(csFuncName, "4 - reset fk - " + sTransId);
				return true;
			}
		}
		catch (Exception e)
		{
			log.e(csFuncName, "Except - 1 - " + e.toString());
			return false;
		}finally {
			DBUtil.close(asqlConn);
		}
	}

	public static boolean IsCancelledCmd(LogUtil log, String asDevId, String asTransId) {
		final String csFuncName = "IsCancelledCmd";
		String sCmdStatus = "";

		log.i(csFuncName, "0 - dev_id:" + asDevId + "trans_id:" + asTransId);

		try
		{
			log.i(csFuncName, "1");

			String sSql = "select status as status from tbl_fkcmd_trans where device_id='"
					+ asDevId +"' and trans_id='"
					+ asTransId	+ "' and status='CANCELLED'";
			log.i(csFuncName, "1.1");
			List<Map<String, Object>> rs = DBUtil.select(sSql);
			try{
				sCmdStatus = (String) rs.get(0).get("status");
			}catch(Exception e){
				sCmdStatus = "";
			}

			log.i(csFuncName, "2 - status:" + sCmdStatus);

			if (sCmdStatus.length() == 0)
			{
				log.i(csFuncName, "3");
				return false;
			}
			else
			{
				log.i(csFuncName, "4");
				return true;
			}
		}
		catch (Exception e)
		{
			log.e(csFuncName, "Except - 1 - " + e.toString());
			return true;
		}
	}

	public static void SetCmdResult(LogUtil log, String asTransId, String asDevId, String asReturnCode,
			byte[] abytCmdResult, Command cmd) {
		final String csFuncName = "SetCmdResult";

		Connection sqlConn = null;

		log.i(csFuncName, "0 - trans_id:" + asTransId + ", dev_id:" + asDevId);

		//		try
		//		{
		//			sqlConn = DBUtil.getConnection();
		//		}
		//		catch (Exception e)
		//		{
		//			log.e(csFuncName, "Error - Not connected db");
		//			cmd.setsResponse("ERROR_DB_CONNECT");
		//			return;
		//		}

		log.i(csFuncName, "1");
		PreparedStatement pstmt = null; 
		try
		{
			//			Thread.sleep(10000);
			log.i(csFuncName, "2");

			if (SaveCmdResultLogData(log, asTransId, asDevId, abytCmdResult) == false)
			{
				cmd.setsResponse("ERROR_DB_SAVE_LOG");
				return;
			}

			if (SaveUserIdList(log, asTransId, asDevId, abytCmdResult) == false)
			{
				cmd.setsResponse("ERROR_DB_SAVE_USER_ID_LIST");
				return;
			}

			//			if (sqlConn.isClosed()){
			sqlConn = DBUtil.getConnection();
			//			}
			pstmt = sqlConn.prepareStatement("{call dbo.usp_set_cmd_result(?,?,?,?)}");
			pstmt.setString(1, asDevId);
			pstmt.setString(2, asTransId);
			pstmt.setString(3, asReturnCode);
			pstmt.setBytes(4, abytCmdResult);
			pstmt.execute();

			log.i(csFuncName, "3");

			cmd.setsResponse("OK");
			return;
		}
		catch (Exception e)
		{
			log.e(csFuncName, "Except - 1 - " + e.toString());
			cmd.setsResponse("ERROR_DB_ACCESS");
			return;
		}finally {
			DBUtil.close(pstmt);
			DBUtil.close(sqlConn);
		}
	}

	private static boolean SaveUserIdList(LogUtil log, String asTransId, String asDevId,
			byte[] abytCmdResult) {
		final String csFuncName = "SaveUserIdList";

		log.i(csFuncName, "1");

		log.i(csFuncName, "2" + " astrans_id = " + asTransId);
		if (!IsCmdCodeEqual(log, asTransId, "GET_USER_ID_LIST"))
		{
			log.i(csFuncName, "2-1" + " astrans_id = " + asTransId);
			return true;
		}

		log.i(csFuncName, "3");
		String sFKDataLib = GetFKDataLibName(log, asDevId);

		String sJson;
		byte[] bytUserIdList;
		Command cmd = new Command();
		System.out.println("sFKDataLib->"+sFKDataLib);
		GetStringAnd1stBinaryFromBSCommBuffer(log,abytCmdResult, cmd);
		sJson = cmd.getJsonstr();
		bytUserIdList = cmd.getBytCmd();
		if (sJson.length() == 0)
			return false;

		System.out.println("sJson->"+sJson);
		log.i(csFuncName, "4");
		int cntUserId, sizeOneUserId;
		final String csTblUserId = "tbl_fkcmd_trans_cmd_result_user_id_list";
		try
		{
			JSONObject jobjLogInfo = new JSONObject(sJson);
			cntUserId = jobjLogInfo.getInt("user_id_count");
			sizeOneUserId = jobjLogInfo.getInt("one_user_id_size");
			if (bytUserIdList.length < cntUserId * sizeOneUserId)
				return false;

			log.i(csFuncName, "5");
			if ("FKDataHS101".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneUserId != 8)
					return false;

				log.i(csFuncName, "6 - HS101");

				String sSqlTemp = "DELETE FROM " + csTblUserId + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp, null);

				int k;
				byte[] bytOneUserId = new byte[sizeOneUserId];
				byte[] bytEnrolledFlag;
				for (k = 0; k < cntUserId; k++)
				{
					int nEnrollDataCount = 0;

					System.arraycopy(
							bytUserIdList, k * sizeOneUserId,
							bytOneUserId, 0,
							sizeOneUserId);
					FKDataHS101_UserIdInfo usrid = new FKDataHS101_UserIdInfo(bytOneUserId);
					String sUserId = String.valueOf(usrid.UserId);
					bytEnrolledFlag = usrid.GetBackupNumberEnrolledFlag();
					for (int bkn = 0; bkn < bytEnrolledFlag.length; bkn++)
					{
						if (bytEnrolledFlag[bkn] == 1)
						{
							nEnrollDataCount++;
							sSqlTemp = "INSERT INTO " + csTblUserId;
							sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
							sSqlTemp += "VALUES('" + asTransId + "', '";
							sSqlTemp += asDevId + "', '";
							sSqlTemp += sUserId + "', ";
							sSqlTemp += bkn + ")";

							DBUtil.update(sSqlTemp, null);
						}
					}
					if (nEnrollDataCount == 0)
					{
						sSqlTemp = "INSERT INTO " + csTblUserId;
						sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
						sSqlTemp += "VALUES('" + asTransId + "', '";
						sSqlTemp += asDevId + "', '";
						sSqlTemp += sUserId + "', ";
						sSqlTemp += "-1)";

						DBUtil.update(sSqlTemp, null);
					}
				}
				log.i(csFuncName, "7 - HS101");
			}
			else if ("FKDataHS102".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneUserId != 20)
					return false;

				log.i(csFuncName, "6 - HS102");

				String sSqlTemp = "DELETE FROM " + csTblUserId + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp,null);

				int k;
				byte[] bytOneUserId = new byte[sizeOneUserId];
				byte[] bytEnrolledFlag;
				for (k = 0; k < cntUserId; k++)
				{
					int nEnrollDataCount = 0;

					System.arraycopy(
							bytUserIdList, k * sizeOneUserId,
							bytOneUserId, 0,
							sizeOneUserId);

					FKDataHS102_UserIdInfo usrid = new FKDataHS102_UserIdInfo(bytOneUserId);
					String sUserId = usrid.GetUserID();
					bytEnrolledFlag = usrid.GetBackupNumberEnrolledFlag();
					//PrintDebugMsg(csFuncName, "6 - " + FKWebTools.GetHexString(bytEnrolledFlag));
					for (int bkn = 0; bkn < bytEnrolledFlag.length; bkn++)
					{
						if (bytEnrolledFlag[bkn] == 1)
						{
							nEnrollDataCount++;
							sSqlTemp = "INSERT INTO " + csTblUserId;
							sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
							sSqlTemp += "VALUES('" + asTransId + "', '";
							sSqlTemp += asDevId + "', '";
							sSqlTemp += sUserId + "', ";
							sSqlTemp += bkn + ")";

							DBUtil.update(sSqlTemp,null);
						}
					}
					if (nEnrollDataCount == 0)
					{
						sSqlTemp = "INSERT INTO " + csTblUserId;
						sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
						sSqlTemp += "VALUES('" + asTransId + "', '";
						sSqlTemp += asDevId + "', '";
						sSqlTemp += sUserId + "', ";
						sSqlTemp += "-1)";

						DBUtil.update(sSqlTemp,null);
					}
				}
				log.i(csFuncName, "7 - HS102");
			}
			else if ("FKDataHS103".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneUserId != FKDataHS103_UserIdInfo.STRUCT_SIZE)
					return false;

				log.i(csFuncName, "6 - HS103");

				String sSqlTemp = "DELETE FROM " + csTblUserId + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp,null);

				int k;
				byte[] bytOneUserId = new byte[sizeOneUserId];
				byte[] bytEnrolledFlag;
				for (k = 0; k < cntUserId; k++)
				{
					int nEnrollDataCount = 0;

					System.arraycopy(
							bytUserIdList, k * sizeOneUserId,
							bytOneUserId, 0,
							sizeOneUserId);

					FKDataHS103_UserIdInfo usrid = new FKDataHS103_UserIdInfo(bytOneUserId);
					String sUserId = usrid.GetUserID();
					bytEnrolledFlag = usrid.GetBackupNumberEnrolledFlag();
					//PrintDebugMsg(csFuncName, "6 - " + FKWebTools.GetHexString(bytEnrolledFlag));
					for (int bkn = 0; bkn < bytEnrolledFlag.length; bkn++)
					{
						if (bytEnrolledFlag[bkn] == 1)
						{
							nEnrollDataCount++;
							sSqlTemp = "INSERT INTO " + csTblUserId;
							sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
							sSqlTemp += "VALUES('" + asTransId + "', '";
							sSqlTemp += asDevId + "', '";
							sSqlTemp += sUserId + "', ";
							sSqlTemp += bkn + ")";

							DBUtil.update(sSqlTemp,null);
						}
					}
					if (nEnrollDataCount == 0)
					{
						sSqlTemp = "INSERT INTO " + csTblUserId;
						sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
						sSqlTemp += "VALUES('" + asTransId + "', '";
						sSqlTemp += asDevId + "', '";
						sSqlTemp += sUserId + "', ";
						sSqlTemp += "-1)";

						DBUtil.update(sSqlTemp,null);
					}
				}
				log.i(csFuncName, "7 - HS103");
			}
			else if ("FKDataHS105".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneUserId != 8)
					return false;

				log.i(csFuncName, "6 - HS105");

				String sSqlTemp = "DELETE FROM " + csTblUserId + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp,null);
				DBUtil.update(sSqlTemp,null);

				int k;
				byte[] bytOneUserId = new byte[sizeOneUserId];
				byte[] bytEnrolledFlag;
				for (k = 0; k < cntUserId; k++)
				{
					int nEnrollDataCount = 0;

					System.arraycopy(
							bytUserIdList, k * sizeOneUserId,
							bytOneUserId, 0,
							sizeOneUserId);

					FKDataHS105_UserIdInfo usrid = new FKDataHS105_UserIdInfo(bytOneUserId);
					String sUserId = String.valueOf(usrid.UserId);
					bytEnrolledFlag = usrid.GetBackupNumberEnrolledFlag();
					//log.i(csFuncName, "6 - " + FKWebTools.GetHexString(bytEnrolledFlag));
					for (int bkn = 0; bkn < bytEnrolledFlag.length; bkn++)
					{
						if (bytEnrolledFlag[bkn] == 1)
						{
							nEnrollDataCount++;
							sSqlTemp = "INSERT INTO " + csTblUserId;
							sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
							sSqlTemp += "VALUES('" + asTransId + "', '";
							sSqlTemp += asDevId + "', '";
							sSqlTemp += sUserId + "', ";
							sSqlTemp += bkn + ")";

							DBUtil.update(sSqlTemp,null);
						}
					}
					if (nEnrollDataCount == 0)
					{
						sSqlTemp = "INSERT INTO " + csTblUserId;
						sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
						sSqlTemp += "VALUES('" + asTransId + "', '";
						sSqlTemp += asDevId + "', '";
						sSqlTemp += sUserId + "', ";
						sSqlTemp += "-1)";

						DBUtil.update(sSqlTemp,null);
					}
				}
				log.i(csFuncName, "7 - HS105");
			}
			else if ("FKDataHS100".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneUserId != 8)
					return false;

				log.i(csFuncName, "6 - HS100");

				String sSqlTemp = "DELETE FROM " + csTblUserId + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp, null);

				int k;
				byte[] bytOneUserId = new byte[sizeOneUserId];
				for (k = 0; k < cntUserId; k++)
				{
					System.arraycopy(
							bytUserIdList, k * sizeOneUserId,
							bytOneUserId, 0,
							sizeOneUserId);

					FKDataHS100_UserIdInfo usrid = new FKDataHS100_UserIdInfo(bytOneUserId);
					String sUserId = String.valueOf(usrid.UserId);
					int BackupNumber = (int)usrid.BackupNumber;
					sSqlTemp = "INSERT INTO " + csTblUserId;
					sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
					sSqlTemp += "VALUES('" + asTransId + "', '";
					sSqlTemp += asDevId + "', '";
					sSqlTemp += sUserId + "', ";
					sSqlTemp += BackupNumber + ")";
					log.i(csFuncName, "6 - HS100" + sSqlTemp);
					DBUtil.update(sSqlTemp, null);
				}
				log.i(csFuncName, "7 - HS100");
			}
			else if ("FKDataHS200".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneUserId != 8)
					return false;

				log.i(csFuncName, "6 - HS200");

				String sSqlTemp = "DELETE FROM " + csTblUserId + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp,null);

				int k;
				byte[] bytOneUserId = new byte[sizeOneUserId];
				// byte[] bytEnrolledFlag;
				for (k = 0; k < cntUserId; k++)
				{
					//  int nEnrollDataCount = 0;

					System.arraycopy(
							bytUserIdList, k * sizeOneUserId,
							bytOneUserId, 0,
							sizeOneUserId);

					FKDataHS200_UserIdInfo usrid = new FKDataHS200_UserIdInfo(bytOneUserId);
					String sUserId =String.valueOf(usrid.UserId);
					int BackupNumber = (int)usrid.BackupNumber;
					//if (BackupNumber == 255)
					// usrid.GetBackupNumberEnrolledFlag(out bytEnrolledFlag);
					//log.i(csFuncName, "6 - " + FKWebTools.GetHexString(bytEnrolledFlag));
					//for (int bkn = 0; bkn < bytEnrolledFlag.Length; bkn++)
					//{
					//    if (bytEnrolledFlag[bkn] == 1)
					//    {
					//        nEnrollDataCount++;
					sSqlTemp = "INSERT INTO " + csTblUserId;
					sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
					sSqlTemp += "VALUES('" + asTransId + "', '";
					sSqlTemp += asDevId + "', '";
					sSqlTemp += sUserId + "', ";
					sSqlTemp += BackupNumber + ")";
					log.i(csFuncName, "6 - HS100" + sSqlTemp);
					DBUtil.update(sSqlTemp,null);
                            /*    }
                            }
                            if (nEnrollDataCount == 0)
                            {
                                sSqlTemp = "INSERT INTO " + csTblUserId;
                                sSqlTemp += "(trans_id, device_id, user_id, backup_number)";
                                sSqlTemp += "VALUES('" + asTransId + "', '";
                                sSqlTemp += asDevId + "', '";
                                sSqlTemp += sUserId + "', ";
                                sSqlTemp += "-1)";

                                DBUtil.update(sSqlTemp,null);
                            }*/
				}
				log.i(csFuncName, "7 - HS200");

			}
			else
			{
				log.e(csFuncName, "error - 1");
				return false;
			}
		}
		catch (Exception ex)
		{
			log.e(csFuncName, "error - 2 - " + ex.toString());
			return false;
		}
		log.i(csFuncName, "8");
		return true;
	}

	private static boolean SaveCmdResultLogData(LogUtil log, String asTransId, String asDevId,
			byte[] abytCmdResult) {
		final String csFuncName = "SaveCmdResultLogData";

		log.i(csFuncName, "2");
		if (!IsCmdCodeEqual(log, asTransId, "GET_LOG_DATA"))
			return true;

		log.i(csFuncName, "3");
		String sFKDataLib = GetFKDataLibName(log, asDevId);

		String sJson;
		byte[] bytLogList;
		Command cmd = new Command();
		GetStringAnd1stBinaryFromBSCommBuffer(log,abytCmdResult, cmd);
		sJson = cmd.getJsonstr();
		bytLogList = cmd.getBytCmd();
		if (sJson.length() == 0)
			return false;

		log.i(csFuncName, "4");
		int cntLog, sizeOneLog;
		final String csTblLog = "tbl_fkcmd_trans_cmd_result_log_data";
		try
		{
			JSONObject jobjLogInfo = new JSONObject(sJson);
			cntLog = jobjLogInfo.getInt("log_count");
			sizeOneLog = jobjLogInfo.getInt("one_log_size");
			if (bytLogList.length < cntLog*sizeOneLog)
				return false;

			log.i(csFuncName, "5 log count="+cntLog);
			if ("FKDataHS101".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneLog != 12)
					return false;

				log.i(csFuncName, "6 - HS101");
				String sSqlTemp = "DELETE FROM " + csTblLog + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp, null);

				int k;
				byte[] bytOneLog = new byte[sizeOneLog];
				for (k = 0; k < cntLog; k++)
				{
					System.arraycopy(
							bytLogList, k * sizeOneLog,
							bytOneLog, 0,
							sizeOneLog);
					log.i("debug:","6 - HS101 bytOneLog="+Arrays.toString(bytOneLog));
					FKDataHS101_GLog glog = new FKDataHS101_GLog(bytOneLog);
					String sUserId = String.valueOf(glog.UserId);
					String sIoMode = FKDataHS101_GLog.GetInOutModeString(glog.IoMode);
					String sVerifyMode = FKDataHS101_GLog.GetVerifyModeString(glog.VerifyMode);
					String sIoTime = glog.GetIoTimeString();

					sSqlTemp = "INSERT INTO  " + csTblLog;
					sSqlTemp += "(trans_id, device_id, user_id, verify_mode, io_mode, io_time,io_workcode)";
					sSqlTemp += "VALUES('" + asTransId + "', '";
					sSqlTemp += asDevId + "', '";
					sSqlTemp += sUserId + "', '";
					sSqlTemp += sVerifyMode + "', '";
					sSqlTemp += sIoMode + "', '";
					sSqlTemp += sIoTime + "','0')";                        

					DBUtil.update(sSqlTemp, null);
				}
				log.i(csFuncName, "7 - HS101");
			}
			else if ("FKDataHS102".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneLog != 32)
					return false;

				log.i(csFuncName, "6 - HS102");
				// ??? ?? trans_id, dev_id? ??? ?? ?????? ??? ?? ????.
				String sSqlTemp = "DELETE FROM " + csTblLog + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp,null);

				int k;
				byte[] bytOneLog = new byte[sizeOneLog];
				for (k = 0; k < cntLog; k++)
				{
					System.arraycopy(
							bytLogList, k * sizeOneLog,
							bytOneLog, 0,
							sizeOneLog);

					FKDataHS102_GLog glog = new FKDataHS102_GLog(bytOneLog);
					String sUserId = glog.GetUserID();
					String sIoMode = FKDataHS102_GLog.GetInOutModeString(glog.IoMode);
					String sVerifyMode = FKDataHS102_GLog.GetVerifyModeString(glog.VerifyMode);
					String sIoTime = glog.GetIoTimeString();
					String sWorkCode = String.valueOf(glog.WorkCode);

					sSqlTemp = "INSERT INTO  " + csTblLog;
					sSqlTemp += "(trans_id, device_id, user_id, verify_mode, io_mode, io_time, io_workcode)";
					sSqlTemp += "VALUES('" + asTransId + "', '";
					sSqlTemp += asDevId + "', '";
					sSqlTemp += sUserId + "', '";
					sSqlTemp += sVerifyMode + "', '";
					sSqlTemp += sIoMode + "', '";
					sSqlTemp += sIoTime + "', '";
					sSqlTemp += sWorkCode + "')";

					DBUtil.update(sSqlTemp,null);
				}
				log.i(csFuncName, "7 - HS102");
			}
			else if ("FKDataHS103".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneLog != 48)
					return false;

				log.i("6 - HS103",null);
				// 鞚挫爠鞐� 頃措嫻 trans_id, dev_id鞐� 雽�頃橃棳 鞏混潃 搿滉犯鞛愲霌れ澊 鞛堨溂氅� 氇憪 靷牅頃滊嫟.
				String sSqlTemp = "DELETE FROM " + csTblLog + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp,null);

				int k;
				byte[] bytOneLog = new byte[sizeOneLog];
				for (k = 0; k < cntLog; k++)
				{
					System.arraycopy(
							bytLogList, k * sizeOneLog,
							bytOneLog, 0,
							sizeOneLog);

					FKDataHS103_GLog glog = new FKDataHS103_GLog(bytOneLog);
					String sUserId = glog.GetUserID();
					String sIoMode = FKDataHS103_GLog.GetInOutModeString(glog.IoMode);
					String sVerifyMode = FKDataHS103_GLog.GetVerifyModeString(glog.VerifyMode);
					String sIoTime = glog.GetIoTimeString();
					String sWorkCode = String.valueOf(glog.WorkCode);

					sSqlTemp = "INSERT INTO  " + csTblLog;
					sSqlTemp += "(trans_id, device_id, user_id, verify_mode, io_mode, io_time, io_workcode)";
					sSqlTemp += "VALUES('" + asTransId + "', '";
					sSqlTemp += asDevId + "', '";
					sSqlTemp += sUserId + "', '";
					sSqlTemp += sVerifyMode + "', '";
					sSqlTemp += sIoMode + "', '";
					sSqlTemp += sIoTime + "', '";
					sSqlTemp += sWorkCode + "')";

					DBUtil.update(sSqlTemp,null);
				}
				log.i(csFuncName, "7 - HS103");
			}
			else if ("FKDataHS105".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneLog != 20)
					return false;

				log.i(csFuncName, "6 - HS105");
				String sSqlTemp = "DELETE FROM " + csTblLog + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp,null);

				int k;
				byte[] bytOneLog = new byte[sizeOneLog];
				for (k = 0; k < cntLog; k++)
				{
					System.arraycopy(
							bytLogList, k * sizeOneLog,
							bytOneLog, 0,
							sizeOneLog);

					FKDataHS105_GLog glog = new FKDataHS105_GLog(bytOneLog);
					String sUserId = String.valueOf(glog.UserId);
					String sIoMode = FKDataHS105_GLog.GetInOutModeString(glog.IoMode);
					String sVerifyMode = FKDataHS105_GLog.GetVerifyModeString(glog.VerifyMode);
					String sIoTime = glog.GetIoTimeString();
					String sWorkCode = String.valueOf(glog.WorkCode);

					sSqlTemp = "INSERT INTO  " + csTblLog;
					sSqlTemp += "(trans_id, device_id, user_id, verify_mode, io_mode, io_time, io_workcode)";
					sSqlTemp += "VALUES('" + asTransId + "', '";
					sSqlTemp += asDevId + "', '";
					sSqlTemp += sUserId + "', '";
					sSqlTemp += sVerifyMode + "', '";
					sSqlTemp += sIoMode + "', '";
					sSqlTemp += sIoTime + "', '";
					sSqlTemp += sWorkCode + "')";

					DBUtil.update(sSqlTemp,null);
				}
				log.i(csFuncName, "7 - HS105");
			}
			else if ("FKDataHS100".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneLog != 12)
					return false;

				log.i(csFuncName, "6 - HS100   cnt=" + cntLog);
				String sSqlTemp = "DELETE FROM " + csTblLog + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp, null);

				int k;
				byte[] bytOneLog = new byte[sizeOneLog];
				for (k = 0; k < cntLog; k++)
				{
					System.arraycopy(
							bytLogList, k * sizeOneLog,
							bytOneLog, 0,
							sizeOneLog);
					log.i("debug:","6 - HS100 bytOneLog="+Arrays.toString(bytOneLog));
					FKDataHS100_GLog glog = new FKDataHS100_GLog(bytOneLog);
					String sUserId = String.valueOf(glog.UserId);
					String sIoMode = FKDataHS100_GLog.GetInOutModeString(glog.IoMode);
					String sVerifyMode = FKDataHS100_GLog.GetVerifyModeString(glog.VerifyMode);
					String sIoTime = glog.GetIoTimeString();

					sSqlTemp = "INSERT INTO  " + csTblLog;
					sSqlTemp += "(trans_id, device_id, user_id, verify_mode, io_mode, io_time)";
					sSqlTemp += "VALUES('" + asTransId + "', '";
					sSqlTemp += asDevId + "', '";
					sSqlTemp += sUserId + "', '";
					sSqlTemp += sVerifyMode + "', '";
					sSqlTemp += sIoMode + "', '";
					sSqlTemp += sIoTime + "')";

					DBUtil.update(sSqlTemp, null);
				}
				log.i(csFuncName, "7 - HS100");

			}
			else if ("FKDataHS200".equalsIgnoreCase(sFKDataLib))
			{
				if (sizeOneLog != 12)
					return false;

				log.i(csFuncName, "6 - HS200   cnt=" + cntLog);
				// 鞚挫爠鞐� 頃措嫻 trans_id, dev_id鞐� 雽�頃橃棳 鞏混潃 搿滉犯鞛愲霌れ澊 鞛堨溂氅� 氇憪 靷牅頃滊嫟.
				String sSqlTemp = "DELETE FROM " + csTblLog + " WHERE trans_id='" + asTransId +
						"' AND device_id='" + asDevId + "'";
				DBUtil.update(sSqlTemp,null);

				int k;
				byte[] bytOneLog = new byte[sizeOneLog];
				for (k = 0; k < cntLog; k++)
				{
					System.arraycopy(
							bytLogList, k * sizeOneLog,
							bytOneLog, 0,
							sizeOneLog);

					FKDataHS200_GLog glog = new FKDataHS200_GLog(bytOneLog);
					String sUserId = String.valueOf(glog.UserId);
					String sIoMode = FKDataHS200_GLog.GetInOutModeString(glog.IoMode);
					String sVerifyMode = FKDataHS200_GLog.GetVerifyModeString(glog.VerifyMode);
					String sIoTime = glog.GetIoTimeString();

					sSqlTemp = "INSERT INTO  " + csTblLog;
					sSqlTemp += "(trans_id, device_id, user_id, verify_mode, io_mode, io_time,io_workcode)";
					sSqlTemp += "VALUES('" + asTransId + "', '";
					sSqlTemp += asDevId + "', '";
					sSqlTemp += sUserId + "', '";
					sSqlTemp += sVerifyMode + "', '";
					sSqlTemp += sIoMode + "', '";
					sSqlTemp += sIoTime + "','0')";

					DBUtil.update(sSqlTemp,null);
					//  PrintDebugMsg1(csFuncName, "6 - HS100 num="+Convert.ToString(k)+"---->"+sSqlTemp);
				}
				log.i(csFuncName, "7 - HS200");

			}
			else
			{
				log.e(csFuncName, "error - 1");
				return false;
			}
		}
		catch(Exception ex)
		{
			log.e(csFuncName, "error - 2 - " + ex.toString());
			return false;
		}
		log.i(csFuncName, "8");
		return true;     
	}

	public static void GetStringAnd1stBinaryFromBSCommBuffer(LogUtil log, byte[] abytBSComm, Command cmd) {
		if (abytBSComm.length < 4)
			return;
		log.i("GetStringAnd1stBinaryFromBSCommBuffer","1");
		try
		{
			int lenText = MyUtil.byte2int(abytBSComm);
			if (lenText > abytBSComm.length - 4)
				return;

			if (lenText == 0)
			{
				cmd.setJsonstr("");
			}
			else
			{
				if (abytBSComm[4 + lenText - 1] == 0) // if last value of string buufer is 0x0
					cmd.setJsonstr(MyUtil.byte2String(abytBSComm, 4, lenText - 1));
				else
					cmd.setJsonstr(MyUtil.byte2String(abytBSComm, 4, lenText));
			}

			int posBin = 4 + lenText;
			int lenBin = MyUtil.byte2int(abytBSComm, posBin);
			if (lenBin < 1)
				return;

			if (lenBin > abytBSComm.length - posBin - 4)
				return;

			cmd.setBytCmd(new byte[lenBin]);
			System.arraycopy(abytBSComm, posBin + 4, cmd.getBytCmd(), 0, lenBin);
			return;
		}
		catch(Exception e)
		{
			return;
		}
	}

	private static String GetFKDataLibName(LogUtil log, String asDevId) {
		String sSql;

		sSql = "SELECT device_info from tbl_fkdevice_status WHERE device_id='" + asDevId+"'";

		List<Map<String, Object>> rs = DBUtil.select(sSql);
		if (rs.size()==0)
			return "";
		String sDevInfo = String.valueOf(rs.get(0).get("device_info"));
		try
		{
			JSONObject jobjDevInfo = new JSONObject(sDevInfo);
			String sFKDataLib = jobjDevInfo.getString("fk_bin_data_lib");
			return sFKDataLib;
		}
		catch (Exception e)
		{
			return "";
		}
	}

	private static boolean IsCmdCodeEqual(LogUtil log, String asTransId, String asCmdCode) {
		String sSql;

		sSql = "SELECT trans_id from tbl_fkcmd_trans WHERE trans_id ='"
				+ asTransId +"' AND cmd_code='"+ asCmdCode + "'";
		List<Map<String, Object>> rs = DBUtil.select(sSql);
		if (rs.size()==0)
			return false;
		return true;
	}

	public static String InsertRealtimeGLog(LogUtil log, String asDevId, String asUserId, String asVerifyMode,
			String asIOMode, String asIOTime, byte[] abytLogImage) {
		final String csFuncName = "InsertRealtimeGLog";
		String sStdIoTime = MyUtil.ConvertFKTimeToNormalTimeString(asIOTime);

		log.i(csFuncName, "1 - " + sStdIoTime);

		try
		{
			log.i(csFuncName, "2");

			if (!MyUtil.isValidEngDigitString(asUserId))
				return "ERROR_INVALID_USER_ID";
			if (MyUtil.IsNullOrEmptyString(asVerifyMode))
				return "ERROR_INVALID_VERIFY_MODE";
			if (MyUtil.IsNullOrEmptyString(asIOMode))
				return "ERROR_INVALID_IO_MODE";
			if (!MyUtil.IsValidTimeString(sStdIoTime))
				return "ERROR_INVALID_IO_TIME";
		}
		catch (Exception e)
		{
			log.e(csFuncName, "2 - error");
			return "ERROR_INVALID_LOG";
		}

		try
		{
			log.i(csFuncName, "3");

			log.i(csFuncName, "4");

			String strSql;
			strSql = "INSERT INTO tbl_realtime_glog";
			strSql = strSql + "(update_time, device_id, user_id, verify_mode, io_mode, io_time)VALUES('";
			strSql = strSql + MyUtil.TimeToString(new Date()) + "',?,?,?,?,?)";

			String[] params = new String[]{asDevId,asUserId,asVerifyMode,asIOMode,sStdIoTime};
			DBUtil.update(strSql, params);

			log.i(csFuncName, "5");

			if (SaveLogImageToFile(log, asDevId, asUserId, asIOTime, abytLogImage) != 0)
			{
				log.e(csFuncName, "Error to save log image");
				return "ERROR_SAVE_LOG_IMAGE";
			}                

			log.i(csFuncName, "6");
			return "OK";
		}
		catch (Exception ex)
		{
			log.e(csFuncName, "Except - 1 - " + ex.getMessage());            
			return "ERROR_DB_ACCESS";
		}
	}

	private static int SaveLogImageToFile(LogUtil log, String asDevId, String asUserId, 
			String asIOTime, byte[] abytLogImage) {
		final String csFuncName = "SaveLogImageToFile";
		String sLogImgFolder;

		try
		{
			log.i(csFuncName, "0");

			if (abytLogImage.length < 1)
				return 0;

			log.i(csFuncName, "2");

			File f = new File(Config.getString("LogImgRootDir"));
			sLogImgFolder = f.getParentFile().getAbsolutePath()+ File.separator + asIOTime.substring(0, 8);
			f = new File(sLogImgFolder);
			if(!f.exists())
				f.mkdir();

			log.i(csFuncName, "3 - " + sLogImgFolder);

			FileOutputStream fs = new FileOutputStream(sLogImgFolder + File.separator + asUserId + "_" + asIOTime + ".jpg");
			fs.write(abytLogImage, 0, abytLogImage.length);
			fs.flush();
			fs.close();

			log.i(csFuncName, "4");
			return 0;
		}
		catch(Exception ex)
		{
			log.e(csFuncName, "Except - " + ex.getMessage());
			return -100;
		}
	}

	public static String InsertRealtimeEnrollData(LogUtil log, String asDevId, String asUserId, byte[] abytUserData) {
		final String csFuncName = "InsertRealtimeEnrollData";

		try
		{
			log.i(csFuncName, "1");
			if (!MyUtil.isValidEngDigitString(asUserId))
				return "ERROR_INVALID_USER_ID";

			log.i(csFuncName, "2");
			if (abytUserData.length < 1)
				return "ERROR_INVALID_USER_DATA";

			log.i(csFuncName, "3");

			String strSql;
			strSql = "INSERT INTO tbl_realtime_enroll_data(update_time, device_id, user_id, user_data)";
			strSql = strSql + "VALUES('" + MyUtil.TimeToString(new Date()) + "',?,?,?)";

			Object[] params = new Object[]{asDevId,asUserId,abytUserData}; 
			DBUtil.update(strSql, params);

			log.i(csFuncName, "4");
			return "OK";
		}
		catch (Exception ex)
		{
			log.e(csFuncName, "Except - 1 - " + ex.getMessage());
			return "ERROR_DB_ACCESS";
		}
	}




















}
