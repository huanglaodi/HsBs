package com.hs.Device.utils.entity;

import java.util.Arrays;

import com.hs.Device.utils.tools.MyUtil;

public class FKDataHS101_GLog {
		public static final int STRUCT_SIZE = 12;        // sizeof(GLOG_DATA_CIF11)

        public int UserId;
        public byte IoMode;
        public byte VerifyMode;

		private int Year;
		private byte Month;
		private byte Day;
		private byte Hour;
		private byte Minute;
		public byte Second;

        public FKDataHS101_GLog()
        {
            UserId = 0;
            IoMode = 0;
            VerifyMode = 0;
            Second = 0;
        }

        public FKDataHS101_GLog(byte[] abytLog)
        {
            if (abytLog.length != STRUCT_SIZE)
                return;
            UserId = MyUtil.byte2int(Arrays.copyOf(abytLog, 4));
            IoMode = abytLog[5];
            VerifyMode = abytLog[6];
            Second = abytLog[7];
            
            int tmlog = MyUtil.byte2int(Arrays.copyOfRange(abytLog,8,12));
			Year = (tmlog & 0x00000ffc) >>> 2;
			Month = (byte)((tmlog & 0x0000f000) >>> 12);
			Day = (byte)((tmlog & 0x001f0000) >>> 16);
			Hour = (byte)((tmlog & 0x03e00000) >>> 21);
			Minute = (byte)((tmlog & 0xfc000000) >>> 26);
        }
        
		public boolean IsValidIoTime()
		{
			if ((Year + 1900) < 1900 || (Year + 1900) > 3000)
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

			return ((Year + 1900) + "-" + Month + "-" + Day + " " + Hour + ":" + Minute + ":" + Second);
		}

        public byte[] GetLogData()
        {
        	byte[] abytLog = new byte[12];
        	System.arraycopy(MyUtil.int2byte(UserId),0,abytLog,0,4);
            abytLog[4] = 0;
            abytLog[5] = IoMode;
            abytLog[6] = VerifyMode;
            abytLog[7] = Second;

			int tmlog = (Year<<2) | (Month<<12) | (Day<<16) | (Hour<<21) | (Minute<<26);
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

		public static final int VERIFY_MODE_FP = 1;
		public static final int VERIFY_MODE_PASSWORD = 2;
		public static final int VERIFY_MODE_IDCARD = 3;
		public static final int VERIFY_MODE_FP_PASSWORD = 4;
		public static final int VERIFY_MODE_FP_IDCARD = 5;
		public static final int VERIFY_MODE_PASSWORD_FP = 6;
		public static final int VERIFY_MODE_IDCARD_FP = 7;
		public static final int VERIFY_MODE_FACE = 20;
		public static final int VERIFY_MODE_FACE_IDCARD = 21;
		public static final int VERIFY_MODE_FACE_PASSWORD = 22;
		public static final int VERIFY_MODE_IDCARD_FACE = 23;
		public static final int VERIFY_MODE_PASSWORD_FACE = 24;
		public static String GetVerifyModeString(int aVerifyMode)
		{
			String sRet = "";
			switch (aVerifyMode)
			{
			case VERIFY_MODE_FP:
				sRet = "[\"FP\"]";
				break;

			case VERIFY_MODE_PASSWORD:
				sRet = "[\"PASSWORD\"]";
				break;

			case VERIFY_MODE_IDCARD:
				sRet = "[\"IDCARD\"]";
				break;

			case VERIFY_MODE_FP_PASSWORD:
				sRet = "[\"FP\",\"PASSWORD\"]";
				break;

			case VERIFY_MODE_FP_IDCARD:
				sRet = "[\"FP\",\"IDCARD\"]";
				break;

			case VERIFY_MODE_PASSWORD_FP:
				sRet = "[\"PASSWORD\",\"FP\"]";
				break;

			case VERIFY_MODE_IDCARD_FP:
				sRet = "[\"IDCARD\",\"FP\"]";
				break;

			case VERIFY_MODE_FACE:
				sRet = "[\"FACE\"]";
				break;

			case VERIFY_MODE_FACE_PASSWORD:
				sRet = "[\"FACE\",\"PASSWORD\"]";
				break;

			case VERIFY_MODE_FACE_IDCARD:
				sRet = "[\"FACE\",\"IDCARD\"]";
				break;

			case VERIFY_MODE_PASSWORD_FACE:
				sRet = "[\"PASSWORD\",\"FACE\"]";
				break;

			case VERIFY_MODE_IDCARD_FACE:
				sRet = "[\"IDCARD\",\"FACE\"]";
				break;

			default:
				break;
			}
			return sRet;
		}
}
