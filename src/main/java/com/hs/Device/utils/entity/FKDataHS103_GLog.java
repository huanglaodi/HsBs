package com.hs.Device.utils.entity;

import java.nio.charset.Charset;
import java.util.Arrays;

import com.hs.Device.utils.tools.MyUtil;

public class FKDataHS103_GLog
{

    public final int STRUCT_SIZE = 48;        // sizeof(GLOG_DATA_CIF13_1)
    public final int USER_ID_LEN = 32;

    class enumGLogDoorMode {
        public final static int LOG_CLOSE_DOOR = 1;                // Door Close
        public final static int LOG_OPEN_HAND = 2;                // Hand Open
        public final static int LOG_PROG_OPEN = 3;                 // Open by PC
        public final static int LOG_PROG_CLOSE = 4;                // Close by PC
        public final static int LOG_OPEN_IREGAL = 5;               // Illegal Open
        public final static int LOG_CLOSE_IREGAL = 6;              // Illegal Close
        public final static int LOG_OPEN_COVER = 7;                // Cover Open
        public final static int LOG_CLOSE_COVER = 8;               // Cover Close
        public final static int LOG_OPEN_DOOR = 9;                 // Door Open
        public final static int LOG_OPEN_DOOR_THREAT = 10;         // Door Open
        public final static int LOG_FIRE_ALARM = 13;               // Door Open
    }

    class enumVerifyKind {
        public final static int VK_NONE = 0;
        public final static int VK_FP = 1;
        public final static int VK_PASS = 2;
        public final static int VK_CARD = 3;
        public final static int VK_FACE = 4;
        public final static int VK_VEIN = 5;
        public final static int VK_IRIS = 6;
        public final static int VK_PV = 7;
    }

    private byte[] UserId;

    public int IoMode;
    public int VerifyMode;
    public byte WorkCode;

    public int Valid;
    private int Year;
    private byte Month;
    private byte Day;
    private byte Hour;
    private byte Minute;
    public byte Second;

    int tmLog;

    public FKDataHS103_GLog()
    {
        UserId = new byte[USER_ID_LEN];
        WorkCode = 0;
        IoMode = 0;
        VerifyMode = 0;
        Second = 0;
        tmLog = 0;
    }

    public FKDataHS103_GLog(byte[] abytLog)
    {
        if (abytLog.length != STRUCT_SIZE)
            return;
        UserId = new byte[USER_ID_LEN];
        System.arraycopy(abytLog, 0,
                UserId, 0,
                USER_ID_LEN);

        WorkCode = abytLog[34];
        Second = abytLog[35];

        tmLog = MyUtil.byte2int(Arrays.copyOfRange(abytLog,36,40));
        IoMode = MyUtil.byte2int(Arrays.copyOfRange(abytLog,40,44));
        VerifyMode = MyUtil.byte2int(Arrays.copyOfRange(abytLog,44,48));
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

    public String GetUserID() {
        return new String(UserId, Charset.defaultCharset());
    }
    public byte[] GetLogData()
    {
        byte[] abytLog = new byte[STRUCT_SIZE];

        System.arraycopy(
                UserId, 0,
                abytLog, 0,
                USER_ID_LEN);

        abytLog[34] = WorkCode;
        abytLog[35] = Second;

        System.arraycopy(
                MyUtil.int2byte(tmLog), 0,
                abytLog, 36,
                4);

        System.arraycopy(
                MyUtil.int2byte(IoMode), 0,
                abytLog, 40,
                4);
        System.arraycopy(
                MyUtil.int2byte(VerifyMode), 0,
                abytLog, 44,
                4);
        return abytLog;
    }

    private static int[] GetIoModeAndDoorMode(int nIoMode)
    {
        int vIoMode = 0, vDoorMode = 0, vInOut = 0;
        int vByteCount = 4;
        byte[] vbyteKind = new byte[vByteCount];
        byte[] vbyteDoorMode = new byte[vByteCount];
        vbyteKind = MyUtil.int2byte(nIoMode);
        //之前的定义有bug，下面是注释掉代码  The previous definition was buggy. Here is the code to comment out
        //vIoMode = vbyteKind[3];

        //----------更改后的代码 Codes Changed-----------
        vIoMode = vbyteKind[3] & 0x0f;
        vInOut = vbyteKind[3] >> 4;
        //----------更改后的代码 Codes Changed-----------

        for (int nIndex = 0; nIndex < 3; nIndex++)
        {
            vbyteDoorMode[nIndex] = vbyteKind[nIndex];
        }
        vbyteDoorMode[3] = 0;
        vDoorMode = MyUtil.byte2int(Arrays.copyOfRange(vbyteDoorMode,0,4));
        return new int[]{vIoMode, vDoorMode, vInOut};
    }
    public static String GetInOutModeString(int aIoMode)
    {
        String vstrTmp = "";

        int[] IoDoorInOut = GetIoModeAndDoorMode(aIoMode);
        int vIoMode = IoDoorInOut[0], vDoorMode = IoDoorInOut[1], vInOut = IoDoorInOut[2];

        if (vIoMode != 0)
            vstrTmp = "( " + vIoMode + " )";

        String strvDoorMode = "";
        if (vDoorMode != 0)
        {
            switch (vDoorMode)
            {
                case (int)enumGLogDoorMode.LOG_CLOSE_DOOR:
                    strvDoorMode += "Close Door"; break;
                case (int)enumGLogDoorMode.LOG_OPEN_HAND:
                    strvDoorMode += "Hand Open"; break;
                case (int)enumGLogDoorMode.LOG_PROG_OPEN:
                    strvDoorMode += "Prog Open"; break;
                case (int)enumGLogDoorMode.LOG_PROG_CLOSE:
                    strvDoorMode += "Prog Close"; break;
                case (int)enumGLogDoorMode.LOG_OPEN_IREGAL:
                    strvDoorMode += "Illegal Open"; break;
                case (int)enumGLogDoorMode.LOG_CLOSE_IREGAL:
                    strvDoorMode += "Illegal Close"; break;
                case (int)enumGLogDoorMode.LOG_OPEN_COVER:
                    strvDoorMode += "Cover Open"; break;
                case (int)enumGLogDoorMode.LOG_CLOSE_COVER:
                    strvDoorMode += "Cover Close"; break;
                case (int)enumGLogDoorMode.LOG_OPEN_DOOR:
                    strvDoorMode += "Open door"; break;
                case (int)enumGLogDoorMode.LOG_OPEN_DOOR_THREAT:
                    strvDoorMode += "Open Door as Threat"; break;
                case (int)enumGLogDoorMode.LOG_FIRE_ALARM:
                    strvDoorMode += "Fire Open"; break;
                default:
                    break;
            }
            if (vstrTmp != "")
            {
                vstrTmp += "&( " + strvDoorMode + " )";
            }
            else
            {
                vstrTmp += "( " + strvDoorMode + " )";
            }
        }

        String strvInOut = "";
        if (vInOut != 0)
        {
            strvInOut = vInOut == 1 ? "In" : "Out";
            if (vstrTmp != "")
            {
                vstrTmp += "&( " + strvInOut + " )";
            }
            else
            {
                vstrTmp += "( " + strvInOut + " )";
            }
        }
        if (vstrTmp == "") vstrTmp = "--";
        return vstrTmp;
    }


    public static String GetVerifyModeString(int nVerifyMode)
    {
        String vRet = "";
        int vByteCount = 4;
        byte[] vbyteKind = new byte[vByteCount];
        int vFirstKind, vSecondKind;
        vbyteKind = MyUtil.int2byte(nVerifyMode);
        for (int nIndex = vByteCount - 1; nIndex >= 0; nIndex--)
        {
            vFirstKind = vSecondKind = vbyteKind[nIndex];
            vFirstKind = vFirstKind & 0xF0;
            vSecondKind = vSecondKind & 0x0F;
            vFirstKind = vFirstKind >> 4;
            if (vFirstKind == 0) break;
            if (nIndex < vByteCount - 1)
                vRet += "+";
            switch (vFirstKind)
            {
                case (int)enumVerifyKind.VK_FP: vRet += "FP"; break;
                case (int)enumVerifyKind.VK_PASS: vRet += "PASS"; break;
                case (int)enumVerifyKind.VK_CARD: vRet += "CARD"; break;
                case (int)enumVerifyKind.VK_FACE: vRet += "FACE"; break;
                case (int)enumVerifyKind.VK_VEIN: vRet += "VEIN"; break;
                case (int)enumVerifyKind.VK_IRIS: vRet += "IRIS"; break;
                case (int)enumVerifyKind.VK_PV: vRet += "PALM"; break;
            }
            if (vSecondKind == 0) break;
            vRet += "+";
            switch (vSecondKind)
            {
                case (int)enumVerifyKind.VK_FP: vRet += "FP"; break;
                case (int)enumVerifyKind.VK_PASS: vRet += "PASS"; break;
                case (int)enumVerifyKind.VK_CARD: vRet += "CARD"; break;
                case (int)enumVerifyKind.VK_FACE: vRet += "FACE"; break;
                case (int)enumVerifyKind.VK_VEIN: vRet += "VEIN"; break;
                case (int)enumVerifyKind.VK_IRIS: vRet += "IRIS"; break;
                case (int)enumVerifyKind.VK_PV: vRet += "PALM"; break;
            }
        }
        //nVerifyMode.
        if (vRet == "") vRet = "--";
        return vRet;
    }
}
