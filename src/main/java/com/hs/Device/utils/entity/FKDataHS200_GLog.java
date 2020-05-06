package com.hs.Device.utils.entity;

import java.util.Arrays;

import com.hs.Device.utils.tools.MyUtil;

public class FKDataHS200_GLog {
    public final int STRUCT_SIZE = 12;        // sizeof(GLOG_DATA_CIF11)

    public byte Vaild; //must be 1

    // public byte IoMode;
    // public byte VerifyMode;

    short tmMode;
    public byte IoMode;
    public byte VerifyMode;

    int tmLog;
    public short Year;
    public byte Month;
    public byte Day;
    public byte Hour;
    public byte Minute;
    //public byte BackupNumber;
    public byte Second;
    public int UserId;

    public FKDataHS200_GLog() {
        Vaild = 0;
        UserId = 0;
        // BackupNumber = 0;
        //IoMode = 0;
        //VerifyMode = 0;
        Second = 0;
        tmMode = 0;
        tmLog = 0;
    }

    public FKDataHS200_GLog(byte[] abytLog) {
        if (abytLog.length != STRUCT_SIZE)
            return;
        Vaild = abytLog[0];
        Second = abytLog[1];
        tmMode = MyUtil.byte2Short(Arrays.copyOfRange(abytLog, 2, 4));
        VerifyMode = (byte) (tmMode & 0x3f);
        IoMode = (byte) (tmMode & 0x7c0 >>> 6);

        UserId = MyUtil.byte2int(Arrays.copyOfRange(abytLog, 4, 8));
        tmLog = MyUtil.byte2int(Arrays.copyOfRange(abytLog, 8, 12));
        Year = (short) (tmLog & 0xffff);
        Month = (byte) (tmLog & 0xf0000);
        Day = (byte) (tmLog & 0x1f00000);
        Hour = (byte) (tmLog & 0x2e00000);
        Minute = (byte) (tmLog & 0xfc000000);
    }

    public boolean IsValidIoTime() {
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

    public String GetIoTimeString() {
        if (!IsValidIoTime())
            return "1970-1-1 0:0:0";

        return ((Year + 1900) + "-" +
                Month + "-" +
                Day + " " +
                Hour + ":" +
                Minute + ":" +
                Second);
    }

    public byte[] GetLogData() {
        byte[] abytLog = new byte[12];

        abytLog[0] = 1;
        abytLog[1] = Second;
        System.arraycopy(
                MyUtil.int2byte((short) tmMode), 0,
                abytLog, 2,
                2);
        //abytLog[2] = 0;


        System.arraycopy(
                MyUtil.int2byte(UserId), 0,
                abytLog, 4,
                4);

        System.arraycopy(
                MyUtil.int2byte(tmLog), 0,
                abytLog, 8,
                4);
        return abytLog;
    }

    //{ Log InOut Mode Constant
    public final int IO_MODE_OUT = 0;
    public final int IO_MODE_IN = 1;

    //}
    public static String GetInOutModeString(int aIoMode) {
        String sRet = "";
            /*switch (aIoMode)
            {
                case IO_MODE_IN:
                    sRet = "IN";
                    break;

                case IO_MODE_OUT:
                    sRet = "OUT";
                    break;

                default:
                    break;
            }*/
        try {
            sRet = String.valueOf(aIoMode);
        } catch (Exception e) {
            sRet = "";
        }
        return sRet;
    }

    //{ Log Verify Mode Constant
    //  public const Int32 VERIFY_MODE_FP = 1;
    public static final int LOG_FPVERIFY = 0x01;        /* Fp Verify           		   */
    public static final int LOG_PASSVERIFY = 0x02;        /* Pass Verify                 */
    public static final int LOG_CARDVERIFY = 0x03;        /* Card Verify                 */
    public static final int LOG_FPPASS_VERIFY = 0x04;        /* Pass+Fp Verify      		   */
    public static final int LOG_FPCARD_VERIFY = 0x05;        /* Card+Fp Verify      		   */
    public static final int LOG_PASSFP_VERIFY = 0x06;        /* Pass+Fp Verify      		   */
    public static final int LOG_CARDFP_VERIFY = 0x07;        /* Card+Fp Verify      		   */
    public static final int LOG_JOB_NO_VERIFY = 0x08;        /* Job number Verify  		   */
    public static final int LOG_CARDPASS_VERIFY = 0x09;        /* Card+Pass Verify    		   */
    public static final int LOG_PASSCARD_VERIFY = 0x89;        /* Pass+Card Verify    		   */


    /* public const Int32 VERIFY_MODE_PASSWORD = 2;
     public const Int32 VERIFY_MODE_IDCARD = 3;
     public const Int32 VERIFY_MODE_FP_PASSWORD = 4;
     public const Int32 VERIFY_MODE_FP_IDCARD = 5;
     public const Int32 VERIFY_MODE_PASSWORD_FP = 6;
     public const Int32 VERIFY_MODE_IDCARD_FP = 7;
     public const Int32 VERIFY_MODE_FACE = 20;
     public const Int32 VERIFY_MODE_FACE_IDCARD = 21;
     public const Int32 VERIFY_MODE_FACE_PASSWORD = 22;
     public const Int32 VERIFY_MODE_IDCARD_FACE = 23;
     public const Int32 VERIFY_MODE_PASSWORD_FACE = 24;*/
    //}
    public static String GetVerifyModeString(int aVerifyMode) {
        String sRet = "";
        switch (aVerifyMode) {
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
/*
                case VERIFY_MODE_PASSWORD_FACE:
                    sRet = "[\"PASSWORD\",\"FACE\"]";
                    break;

                case VERIFY_MODE_IDCARD_FACE:
                    sRet = "[\"IDCARD\",\"FACE\"]";
                    break;
                    */
            default:
                break;
        }
        return sRet;
    }
}
