package com.hs.Device.utils.entity;

import java.util.Arrays;

import com.hs.Device.utils.tools.MyUtil;

public class FKDataHS100_UserIdInfo {
    public int UserId;
    public byte Privilege;
    public byte Enabled;
    public byte BackupNumber;

	public static final int STRUCT_SIZE = 8;        // sizeof(USER_ID_CIF11)

	//{ Backup number constant
	public static final int BACKUP_FP_0 = 0;        // Finger 0
	public static final int BACKUP_FP_1 = 1;        // Finger 1
	public static final int BACKUP_FP_2 = 2;        // Finger 2
	public static final int BACKUP_FP_3 = 3;        // Finger 3
	public static final int BACKUP_FP_4 = 4;        // Finger 4
	public static final int BACKUP_FP_5 = 5;        // Finger 5
	public static final int BACKUP_FP_6 = 6;        // Finger 6
	public static final int BACKUP_FP_7 = 7;        // Finger 7
	public static final int BACKUP_FP_8 = 8;        // Finger 8
	public static final int BACKUP_FP_9 = 9;        // Finger 9
	public static final int BACKUP_PSW = 10;        // Password
	public static final int BACKUP_CARD = 11;       // Card

	//}

    public FKDataHS100_UserIdInfo()
    {
        UserId = 0;
        Privilege = 0;
        Enabled = 0;
        BackupNumber = 0;
        /*PasswordFlag = 0;
        CardFlag = 0;
        FaceFlag = 0;
        FpCount = 0;
        VeinCount = 0;

        flagEnrolled = new BitVector32(0);*/
    }

    public FKDataHS100_UserIdInfo(byte[] abytUserIdInfo)
    {
        if (abytUserIdInfo.length != STRUCT_SIZE)
            return;
        Enabled = abytUserIdInfo[0];
        Privilege = abytUserIdInfo[1];
        BackupNumber = abytUserIdInfo[2];
        UserId = MyUtil.byte2int(Arrays.copyOfRange(abytUserIdInfo, 4, 8));
        /*Privilege = abytUserIdInfo[4];
        Enabled = abytUserIdInfo[5];
        flagEnrolled = new BitVector32(BitConverter.ToInt16(abytUserIdInfo, 6));*/
    }

	public byte[] GetUserIdInfo()
    {
		byte[] abytUserIdInfo = new byte[8];

        abytUserIdInfo[0] = Enabled;
        abytUserIdInfo[1] = Privilege;
        abytUserIdInfo[2] = BackupNumber;

        System.arraycopy(MyUtil.int2byte(UserId),0,abytUserIdInfo,4,4);
        return abytUserIdInfo;
   }
}