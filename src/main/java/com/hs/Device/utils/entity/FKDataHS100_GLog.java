package com.hs.Device.utils.entity;

import java.util.Arrays;

import com.hs.Device.utils.tools.MyUtil;

public class FKDataHS100_GLog {
		public static final int STRUCT_SIZE = 12;        // sizeof(GLOG_DATA_CIF11)

		public byte Vaild; //must be 1

		public byte IoMode;
		public byte VerifyMode;

		private int Year;
		private byte Month;
		private byte Day;
		private byte Hour;
		private byte Minute;
		
		public byte BackupNumber;
		public byte Second;
		public int UserId;

		public FKDataHS100_GLog()
		{
			Vaild = 0;
			UserId = 0;
			BackupNumber = 0;
			//IoMode = 0;
			//VerifyMode = 0;
			Second = 0;
			
//			tmMode = new BitVector32(0);
//			tmLog = new BitVector32(0);
		}

		public FKDataHS100_GLog(byte[] abytLog)
		{
			if (abytLog.length != STRUCT_SIZE)
				return;
			Vaild = abytLog[0];
			short tmMode = MyUtil.byte2Short(Arrays.copyOfRange(abytLog,1,3)); 
			VerifyMode = (byte) (tmMode & 0x3f);
			IoMode = (byte) ((tmMode & 0xc0) >> 6);
			Second = abytLog[3];
			UserId = MyUtil.byte2int(Arrays.copyOfRange(abytLog, 4, 8));
			int tmLog = MyUtil.byte2int(Arrays.copyOfRange(abytLog,8,12));
			Year = (tmLog & 0xfff);
			Month = (byte) ((tmLog & 0xf000) >> 12);
			Day = (byte) ((tmLog & 0x1f0000) >> 16);
			Hour = (byte) ((tmLog & 0x3e00000) >> 21);
			Minute = (byte) ((tmLog & 0xfc000000) >> 26);
		}

		public boolean IsValidIoTime()
		{
			if (Year < 1900 || Year > 3000)
				return false;
			if (Month < 1 || Month > 12)
				return false;
			if (Day < 1 || Day > 31)
				return false;
			if (Hour < 0 || Hour > 24)
				return false;
			if (Minute < 0 || Minute > 60)
				return false;
			if (Second < 0 || Second > 60)
				return false;

			return true;
		}

		public String GetIoTimeString()
		{
			if (!IsValidIoTime())
				return "1970-1-1 0:0:0";

			return (Year + "-" + Month + "-" + Day + " " + Hour + ":" + Minute + ":" + Second);
		}

		public byte[] GetLogData()
		{
			byte[] abytLog = new byte[12];

			abytLog[0] = 1;
			abytLog[1] = (byte) ((IoMode<<6) | (VerifyMode));
			abytLog[2] = 0;
			abytLog[3] = Second;
			System.arraycopy(MyUtil.int2byte(UserId),0,abytLog,4,4);

			int tmlog = (Year) | (Month<<12) | (Day<<16) | (Hour<<21) | (Minute<<26);
			System.arraycopy(MyUtil.int2byte(tmlog), 0, abytLog, 8, 4);
			return abytLog;
		}

		//{ Log InOut Mode Constant    
		public static final int IO_MODE_OUT = 0;
		public static final int IO_MODE_IN = 1;
		//}
		public static String GetInOutModeString(int aIoMode)
		{
			String sRet = "";
			switch (aIoMode)
			{
			case IO_MODE_IN:
				sRet = "IN";
				break;

			case IO_MODE_OUT:
				sRet = "OUT";
				break;

			default:
				break;
			}
			return sRet;
		}

		//{ Log Verify Mode Constant
		//  public final int VERIFY_MODE_FP = 1;
		public static final int LOG_FPVERIFY		= 0x01;		 //Fp Verify           		   
		public static final int LOG_PASSVERIFY		=0x02;		 //Pass Verify                 
		public static final int LOG_CARDVERIFY		=0x03;		 //Card Verify                 
		public static final int LOG_FPPASS_VERIFY	=0x04;		 //Pass+Fp Verify      		   
		public static final int LOG_FPCARD_VERIFY	=0x05;		 //Card+Fp Verify      		   
		public static final int LOG_PASSFP_VERIFY	=0x06;		 //Pass+Fp Verify      		   
		public static final int LOG_CARDFP_VERIFY	=0x07;		 //Card+Fp Verify      		   
		public static final int LOG_JOB_NO_VERIFY	=0x08;		 //Job number Verify  		   
		public static final int LOG_CARDPASS_VERIFY	=0x09;		 //Card+Pass Verify    		   
		public static final int LOG_PASSCARD_VERIFY	=0x89;		 //Pass+Card Verify    		   


		/*public static final int VERIFY_MODE_PASSWORD = 2;
		public static final int VERIFY_MODE_IDCARD = 3;
		public static final int VERIFY_MODE_FP_PASSWORD = 4;
		public static final int VERIFY_MODE_FP_IDCARD = 5;
		public static final int VERIFY_MODE_PASSWORD_FP = 6;
		public static final int VERIFY_MODE_IDCARD_FP = 7;
		public static final int VERIFY_MODE_FACE = 20;
		public static final int VERIFY_MODE_FACE_IDCARD = 21;
		public static final int VERIFY_MODE_FACE_PASSWORD = 22;
		public static final int VERIFY_MODE_IDCARD_FACE = 23;
		public static final int VERIFY_MODE_PASSWORD_FACE = 24;*/
		//}
		public static String GetVerifyModeString(int aVerifyMode)
		{
			String sRet = "";
			switch (aVerifyMode)
			{
			case LOG_FPVERIFY:
				sRet = "[\"FP\"]";
				break;

			case LOG_PASSVERIFY:
				sRet = "[\"PASSWORD\"]";
				break;

			case LOG_CARDVERIFY:
				sRet = "[\"IDCARD\"]";
				break;

			case LOG_FPPASS_VERIFY:
				sRet = "[\"FP\",\"PASSWORD\"]";
				break;

			case LOG_FPCARD_VERIFY:
				sRet = "[\"FP\",\"IDCARD\"]";
				break;

			case LOG_PASSFP_VERIFY:
				sRet = "[\"PASSWORD\",\"FP\"]";
				break;

			case LOG_CARDFP_VERIFY:
				sRet = "[\"IDCARD\",\"FP\"]";
				break;

			case LOG_JOB_NO_VERIFY:
				sRet = "[\"JOB NO\"]";
				break;

			case LOG_CARDPASS_VERIFY:
				sRet = "[\"IDCARD\",\"PASSWORD\"]";
				break;

			case LOG_PASSCARD_VERIFY:
				sRet = "[\"PASSWORD\",\"IDCARD\"]";
				break;

	/*		case VERIFY_MODE_PASSWORD_FACE:
				sRet = "[\"PASSWORD\",\"FACE\"]";
				break;

			case VERIFY_MODE_IDCARD_FACE:
				sRet = "[\"IDCARD\",\"FACE\"]";
				break;*/

			default:
				break;
			}
			return sRet;
		}
}
