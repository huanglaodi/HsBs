package com.hs.Device.utils.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.hs.Device.utils.db.DBConnectionManager;
import com.hs.Device.utils.entity.Command;
import com.hs.Device.utils.entity.Device;
import com.hs.Device.utils.entity.FKDataHS100_GLog;
import com.hs.Device.utils.entity.FKDataHS101_GLog;
import com.hs.Device.utils.entity.FKDataHS102_GLog;
import com.hs.Device.utils.entity.FKDataHS103_GLog;
import com.hs.Device.utils.entity.FKDataHS105_GLog;
import com.hs.Device.utils.entity.FKDataHS200_GLog;
import com.hs.Device.utils.entity.FKWebTransBlockData;
import com.hs.Device.utils.tools.CommandUtil;
import com.hs.Device.utils.tools.Config;
import com.hs.Device.utils.tools.Constant;
import com.hs.Device.utils.tools.LogUtil;
import com.hs.Device.utils.tools.MyUtil;

/**
 * Servlet implementation class Default
 */
@WebServlet("/Default")
public class Default extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private byte[] bytRequestBin;
	private byte[] bytRequestTotal;
	private LogUtil log;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Default() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sRequestCode = "";
		String sTransId = "";
		byte[] bytEmpty = new byte[0];
		
		final String csFuncName = "Page_Load";
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		log = LogUtil.getInstance(Config.getbool("ShowDebugMsg"));
		try{

			sRequestCode = request.getHeader("request_code");
			if (!MyUtil.isValidEngDigitString(sRequestCode))
			{
				log.e(csFuncName, "Invalid request_code : " + sRequestCode);
				return;
			}
			sTransId = request.getHeader("trans_id");
			if(sTransId == null) sTransId = "";
			log.i(csFuncName, "1 - request_code : " + sRequestCode + " ,trans_id : " + sTransId);

			String sDevId = request.getHeader("dev_id");
			if (!MyUtil.isValidEngDigitString(sDevId))
			{
				log.e(csFuncName, "error - Invalid dev_id : " + sDevId);
				return;
			}

			int lenContent = GetRequestStreamBytes(request);
			if (lenContent < 0)
			{
				log.e(csFuncName, "2.1" + lenContent);
				return;
			}

			int nBlkNo = 0;
			int nBlkLen = 0;
			try{
				nBlkNo = Integer.parseInt(request.getHeader("blk_no"));
				nBlkLen = Integer.parseInt(request.getHeader("blk_len"));
			}catch(Exception e){
				log.e(csFuncName, "2.2 --" + e.getMessage() + "--nBlkNo = " + nBlkNo + "nBlkLen = " + nBlkLen);
			}
			int vRet;
			if (nBlkNo > 0)
			{
				log.i(csFuncName, "3.0 - blk_no=" + nBlkNo + ", blk_len=" + nBlkLen);
				vRet = AddBlockData(sDevId, nBlkNo, bytRequestBin);
				if (vRet != 0)
				{
					log.e(csFuncName, "3.0 - error - AddBlockData:"+ vRet);
					SendResponseToClient("ERROR_ADD_BLOCK_DATA", sTransId, "", bytEmpty,response);
					return;
				}

				log.i(csFuncName, "3.1");

				SendResponseToClient("OK", sTransId, "", bytEmpty,response);
				return;
			}
			else if (nBlkNo < 0)
			{
				log.e(csFuncName, "3.3 - blk_no=" + nBlkNo + ", blk_len=" + nBlkLen);
				SendResponseToClient("ERROR_INVLAID_BLOCK_NO", sTransId, "", bytEmpty,response);
				return;
			}
			else
			{
				log.i(csFuncName, "3.4 - blk_no=" + nBlkNo + ", blk_len=" + nBlkLen);
				GetBlockDataAndRemove(sDevId);
				bytRequestTotal = CommandUtil.ConcateByteArray(bytRequestTotal, bytRequestBin);
			}

			if (sRequestCode.equalsIgnoreCase(Constant.REQ_CODE_RECV_CMD))
			{
				RemoveOldBlockStream();
				OnReceiveCmd(sDevId, sTransId, bytRequestTotal,response);
			}
			else if (sRequestCode.equalsIgnoreCase(Constant.REQ_CODE_SEND_CMD_RESULT))
			{
				OnSendCmdResult(sDevId, sTransId, bytRequestTotal,response,request);
			}
			else if (sRequestCode.equalsIgnoreCase(Constant.REQ_CODE_REALTIME_GLOG))
			{
				OnRealtimeGLog(sDevId, bytRequestTotal,response,request);
			}
			else if (sRequestCode.equalsIgnoreCase(Constant.REQ_CODE_REALTIME_ENROLL))
			{
				OnRealtimeEnrollData(sDevId, bytRequestTotal,response,request);
			}
			else
			{
				SendResponseToClient("ERROR_INVLAID_REQUEST_CODE", sTransId, "", bytEmpty,response);
			}
		}catch(Exception e){
			log.e("Page_Load---->" + e.getMessage());
			e.printStackTrace();
		}finally {
			log.i("DB", DBConnectionManager.getInstance().toString());
		}
	}

	private void OnRealtimeEnrollData(String asDevId, byte[] abytRequest, HttpServletResponse response,
			HttpServletRequest request) {
		final String csFuncName = "Page_Load - realtime_enroll_data";

		String sRequest;
		String sResponse;
		byte[] bytRequest = abytRequest;

		try
		{
			log.i(csFuncName, "1");

			sRequest = CommandUtil.GetStringFromBSCommBuffer(bytRequest);
			if (sRequest.length() == 0)
			{
				return;
			}

			log.i(csFuncName, "2");

			JSONObject jobjRequest = new JSONObject(sRequest);
			String sUserId = jobjRequest.getString("user_id");

			log.i(csFuncName, "3");

			sResponse = CommandUtil.InsertRealtimeEnrollData(log, asDevId, sUserId, bytRequest);

			response.addHeader("response_code", sResponse);
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", "0");
			response.flushBuffer();

			log.i(csFuncName, "4");
		}
		catch (Exception ex)
		{
			log.e(csFuncName, "Except - 1 - " + ex.getMessage());
			return;
		}
	}

	private void OnRealtimeGLog(String asDevId, byte[] abytRequest, HttpServletResponse response,
			HttpServletRequest request) {
		final String csFuncName = "Page_Load - realtime_glog";

		String sRequest ="";
		String sResponse;
		byte[] bytRequest = abytRequest;
		byte[] bytLogImage = new byte[0];

		try
		{
			log.i(csFuncName, "1------------------>");

			Command cmd = new Command();
			CommandUtil.GetStringAnd1stBinaryFromBSCommBuffer(log,bytRequest, cmd);
			sRequest = cmd.getJsonstr();
			bytLogImage = cmd.getBytCmd();
			if (sRequest.length() == 0)
			{
				log.e(csFuncName, "1-- length=" + sRequest.length());
				return;
			}

			log.i(csFuncName, "2");

			String sUserId;
			String sVerifyMode;
			String sIOMode;
			String sIOTime;
//			String sLogImg;
			String sFKBinDataLib;            

			JSONObject jobjRequest = new JSONObject(sRequest);
			log.i(csFuncName, "2-1");
			try{
				sFKBinDataLib = jobjRequest.getString("fk_bin_data_lib");
			}catch(Exception e){
				log.e(csFuncName, "2-- sFKBinDataLib.length=0");
				response.addHeader("response_code", "ERROR_INVALID_LIB_NAME");
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Length", "0");
				response.flushBuffer();
				return;
			}
			log.i(csFuncName, "2-2");
			sUserId = jobjRequest.getString("user_id");
			sVerifyMode = jobjRequest.getString("verify_mode");
			sIOMode = jobjRequest.getString("io_mode");
			sIOTime = jobjRequest.getString("io_time");
//			try
//			{
//				sLogImg = jobjRequest.getString("log_image");
//			}catch(Exception e)
//			{
//			}
			log.i(csFuncName, "2-3");
			log.i(csFuncName, "2" + "user_id = " + sUserId + "  sIOTime = " + sIOTime);
			if (sFKBinDataLib == "FKDataHS101")
			{
				sIOMode = FKDataHS101_GLog.GetInOutModeString(Integer.parseInt(sIOMode));
				sVerifyMode = FKDataHS101_GLog.GetVerifyModeString(Integer.parseInt(sVerifyMode));
			}

			else if (sFKBinDataLib == "FKDataHS100")
			{
				sIOMode = FKDataHS100_GLog.GetInOutModeString(Integer.parseInt(sIOMode));
				sVerifyMode = FKDataHS100_GLog.GetVerifyModeString(Integer.parseInt(sVerifyMode));
			}
			else if (sFKBinDataLib == "FKDataHS102")
			{
				sIOMode = FKDataHS102_GLog.GetInOutModeString(Integer.parseInt(sIOMode));
				sVerifyMode = FKDataHS102_GLog.GetVerifyModeString(Integer.parseInt(sVerifyMode));
			}
			else if (sFKBinDataLib == "FKDataHS103")
			{
				sIOMode = FKDataHS103_GLog.GetInOutModeString(Integer.parseInt(sIOMode));
				sVerifyMode = FKDataHS103_GLog.GetVerifyModeString(Integer.parseInt(sVerifyMode));
			}
			else if (sFKBinDataLib == "FKDataHS105")
			{
				sIOMode = FKDataHS105_GLog.GetInOutModeString(Integer.parseInt(sIOMode));
				sVerifyMode = FKDataHS105_GLog.GetVerifyModeString(Integer.parseInt(sVerifyMode));
			}
			else if (sFKBinDataLib == "FKDataHS200")
			{
				sIOMode = FKDataHS200_GLog.GetInOutModeString(Integer.parseInt(sIOMode));
				sVerifyMode = FKDataHS200_GLog.GetVerifyModeString(Integer.parseInt(sVerifyMode));
			}
			log.i(csFuncName, "3");

			sResponse = CommandUtil.InsertRealtimeGLog(log, asDevId, sUserId, sVerifyMode,
					sIOMode, sIOTime, bytLogImage);

			response.addHeader("response_code", sResponse);
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", "0");            
			response.flushBuffer();

			log.i(csFuncName, "4");
		}
		catch (Exception ex)
		{
			log.e(csFuncName, "Except - 1 - " + ex.getMessage());
			return;
		}
	}

	private void OnSendCmdResult(String asDevId, String sTransId, byte[] abytRequest, HttpServletResponse response,
			HttpServletRequest request) {
		final String csFuncName = "Page_Load - send_cmd_result";

		byte[] bytCmdResult = abytRequest;
		byte[] bytEmpty = new byte[0];
		String sResponse = null;
		String sReturnCode;

		log.i(csFuncName, "1");
		
		try
		{
			log.i(csFuncName, "2 - trans_id:" + sTransId);

			Command cmd = new Command();
			if (CommandUtil.CheckResetCmd(log, asDevId, cmd))
			{
				log.i(csFuncName, "2.1");

				SendResponseToClient("RESET_FK", sTransId, "", bytEmpty,response);                
				return;
			}
			if (CommandUtil.IsCancelledCmd(log, asDevId, sTransId))
			{
				log.i(csFuncName, "2.2");
				SendResponseToClient("ERROR_CANCELED", sTransId, "", bytEmpty,response);
				return;
			}

			log.i(csFuncName, "3");

			sReturnCode = request.getHeader("cmd_return_code").toString();

			CommandUtil.SetCmdResult(log, sTransId, asDevId, sReturnCode, bytCmdResult, cmd);

			log.i(csFuncName, "4");

			SendResponseToClient(sResponse, sTransId, "", bytEmpty,response);

			log.i(csFuncName, "5");
		}
		catch (Exception ex)
		{
			log.e(csFuncName, "Except - 1 - " + ex.toString());
			return;
		}
	}

	private void OnReceiveCmd(String asDevId, String asTransId, byte[] abytRequest, HttpServletResponse response) {
		final String csFuncName = "Page_Load - receive_cmd";

		String sRequest;
		String sResponse = "";
		String sTransId = asTransId;
		byte[] bytRequest = abytRequest;
		String sCmdCode = "";

		try
		{
			log.i(csFuncName, "2");

			sRequest = CommandUtil.GetStringFromBSCommBuffer(bytRequest);
			if (sRequest.length() == 0)
			{
				return;
			}

			String sDevName;
			String sDevTime;
			String sDevInfo;

			JSONObject jobjRequest = new JSONObject(sRequest);
			sDevName = jobjRequest.getString("fk_name");
			sDevTime = jobjRequest.getString("fk_time");
			sDevInfo = jobjRequest.getString("fk_info").toString();

			log.i(csFuncName, "3 - DevName=" + sDevName + ", DevTime=" + sDevTime + ", DevInfo=" + sDevInfo);

			byte[] bytCmdParam = new byte[0];
			Command cmd = new Command(sTransId,sCmdCode,bytCmdParam,sResponse);
			Device dev = new Device(asDevId, sDevName, sDevTime, sDevInfo);
			CommandUtil.ReceiveCmd(log, dev, cmd);

			log.i(csFuncName, "4");
			SendResponseToClient(cmd.getsResponse(),cmd.getTransId(),cmd.getCmdCode(),cmd.getBytCmd(),response);

			log.i(csFuncName, "5");
		}
		catch (Exception ex)
		{
			log.i(csFuncName, "Except - 1 - " + ex.toString());
//			ex.printStackTrace();
			return;
		}
	}

	//返回了request.getContentLength()或-1
	private int GetRequestStreamBytes(HttpServletRequest request) {
		int lenContent = request.getContentLength();
		if (lenContent < 1)
			return 0;
		InputStream streamIn;
		byte [] bytRecv = new byte[lenContent];
		try {
			streamIn = request.getInputStream();
			int lenRead = 0,readlen = 0;
			
			while((readlen = streamIn.read(bytRecv, lenRead, 1024)) != -1){
				lenRead += readlen;
			}
//			lenRead = streamIn.read(bytRecv, 0, lenContent);
			if (lenRead != lenContent){
				return -1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		bytRequestBin = bytRecv;
		return lenContent;
	}
	
	private void RemoveOldBlockStream() {
		Date dtCur = new Date();
		try
		{
			List<String> listDevIdKey = new ArrayList<String>();
			FKWebTransBlockData app_val_blk;
			Iterator<Entry<String, FKWebTransBlockData>> iter = Constant.Application.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, FKWebTransBlockData> entry = (Map.Entry<String, FKWebTransBlockData>) iter.next();
				String sKey = entry.getKey();
				if(!sKey.equalsIgnoreCase("key_dev_"))
					continue;
				app_val_blk = entry.getValue();

				if(dtCur.getTime() - app_val_blk.TmLastModified.getTime() > 1000*60*30){
					listDevIdKey.add(sKey);
				}
			}
			for (int i = 0; i < listDevIdKey.size(); i++) {
				Constant.Application.remove(listDevIdKey.get(i));
			}
		}
		catch(Exception e)
		{
			log.e(e.getMessage());
		}
	}
	
	private int GetBlockDataAndRemove(String asDevId) {
		bytRequestTotal = new byte[0];

		if (asDevId.length() == 0)
			return -1;
		try
		{
			String app_key;

			app_key = "key_dev_" + asDevId;

			FKWebTransBlockData app_val_blk = (FKWebTransBlockData)Constant.Application.get(app_key);
			if (app_val_blk == null)
			{
				log.i("GetBlockDataAndRemove->", "app_val_blk == null");
				return 0;
			}

			bytRequestTotal = Arrays.copyOf(app_val_blk.getAbytBlkData(), app_val_blk.getAbytBlkData().length);
			Constant.Application.remove(app_key);

			return 0;
		}
		catch(Exception e)
		{
			return -11;
		}
	}
	
	private int AddBlockData(String asDevId, int anBlkNo, byte [] abytBlkData){
		if (asDevId.length() == 0)
			return -1; // -1 : 

		if (anBlkNo < 1)
			return -1;

		if (abytBlkData.length == 0)
			return -1;

		try
		{
			String app_key;            

			app_key = "key_dev_" + asDevId;
			
			if (anBlkNo == 1)
			{
				FKWebTransBlockData app_val_blk = (FKWebTransBlockData)Constant.Application.get(app_key);
				if (app_val_blk == null)
				{
					app_val_blk = new FKWebTransBlockData();
					Constant.Application.put(app_key, app_val_blk);
				}
				else
				{
					Constant.Application.remove(app_key);
					app_val_blk = new FKWebTransBlockData();
					Constant.Application.put(app_key, app_val_blk);
				}
				app_val_blk.LastBlockNo = 1;
				app_val_blk.setAbytBlkData(abytBlkData);
			}
			else
			{
				FKWebTransBlockData app_val_blk = (FKWebTransBlockData)Constant.Application.get(app_key);
				if (app_val_blk == null)
				{
					return -2;
				}
				if (app_val_blk.LastBlockNo != anBlkNo - 1)
				{
					return -3;
				}
				app_val_blk.LastBlockNo = anBlkNo;
				app_val_blk.setAbytBlkData(abytBlkData);
			}

			return 0;
		}
		catch(Exception e)
		{
			return -11;
		}
	}
	
	private void SendResponseToClient(String asResponseCode,String asTransId,
			String asCmdCode,byte[] abytCmdParam, HttpServletResponse response){
		response.addHeader("response_code", asResponseCode);
		response.addHeader("trans_id", asTransId);
		response.addHeader("cmd_code", asCmdCode);

		response.setContentType("application/octet-stream");
		response.addHeader("Content-Length", String.valueOf(abytCmdParam.length));
		try {
			if (abytCmdParam.length > 0)
			{
				OutputStream streamOut = response.getOutputStream();
				streamOut.write(abytCmdParam, 0, abytCmdParam.length);
				streamOut.close();
			}
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}























