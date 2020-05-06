package com.hs.Device.utils.entity;

import java.util.Arrays;

import com.hs.Device.utils.tools.MyUtil;

public class FKDataHS101_UserIdInfo {

	public int UserId;
	public byte Privilege;
	public byte Enabled;

	private byte PasswordFlag;
	private byte CardFlag;
	private byte FaceFlag;
	private byte FpCount;
	private byte VeinCount;

	public final int STRUCT_SIZE = 8;        // sizeof(USER_ID_CIF11)

	//{ Backup number constant
	public final int BACKUP_FP_0 = 0;        // Finger 0
	public final int BACKUP_FP_1 = 1;        // Finger 1
	public final int BACKUP_FP_2 = 2;        // Finger 2
	public final int BACKUP_FP_3 = 3;        // Finger 3
	public final int BACKUP_FP_4 = 4;        // Finger 4
	public final int BACKUP_FP_5 = 5;        // Finger 5
	public final int BACKUP_FP_6 = 6;        // Finger 6
	public final int BACKUP_FP_7 = 7;        // Finger 7
	public final int BACKUP_FP_8 = 8;        // Finger 8
	public final int BACKUP_FP_9 = 9;        // Finger 9
	public final int BACKUP_PSW = 10;        // Password
	public final int BACKUP_CARD = 11;       // Card
	public final int BACKUP_FACE = 12;       // Face
	public final int BACKUP_VEIN_0 = 20;     // Vein 0
	//}

	public FKDataHS101_UserIdInfo()
	{
		UserId = 0;
		Privilege = 0;
		Enabled = 0;

		PasswordFlag = 0;
		CardFlag = 0;
		FaceFlag = 0;
		FpCount = 0;
		VeinCount = 0;

	}

	public FKDataHS101_UserIdInfo(byte[] abytUserIdInfo){
		if (abytUserIdInfo.length != STRUCT_SIZE)
			return;

		UserId = MyUtil.byte2int(Arrays.copyOf(abytUserIdInfo, 4));
		Privilege = abytUserIdInfo[4];
		Enabled = abytUserIdInfo[5];

		short flagEnrolled = MyUtil.byte2Short(Arrays.copyOfRange(abytUserIdInfo,6,8));
		PasswordFlag = (byte) (flagEnrolled & 0x1);
		CardFlag = (byte) ((flagEnrolled & 0x2) >> 1);
		FaceFlag = (byte) ((flagEnrolled & 0x4) >> 2);
		FpCount = (byte) ((flagEnrolled & 0x78) >> 3);
		VeinCount = (byte) ((flagEnrolled & 0x1f80) >> 7);
	}


	public void SetBackupNumberEnrolledFlag(byte[] abytEnrolledFlag){
		if(abytEnrolledFlag.length < 13) return;

		if (abytEnrolledFlag[BACKUP_PSW] == 1)
			PasswordFlag = 1;

		if (abytEnrolledFlag[BACKUP_CARD] == 1)
			CardFlag = 1;

		if (abytEnrolledFlag[BACKUP_FACE] == 1)
			FaceFlag = 1;

		int fpcnt = 0;
		int k;
		for (k = 0; k < 10; k++){
			if (abytEnrolledFlag[BACKUP_FP_0 + k] == 1)
				fpcnt++;
		}
		FpCount = (byte) fpcnt;
	}

	public byte[] GetBackupNumberEnrolledFlag()
	{
		byte[] abytEnrolledFlag = new byte[20];

		if (PasswordFlag == 1)
			abytEnrolledFlag[BACKUP_PSW] = 1;

		if (CardFlag == 1)
			abytEnrolledFlag[BACKUP_CARD] = 1;

		if (FaceFlag == 1)
			abytEnrolledFlag[BACKUP_FACE] = 1;

		int fpcnt = FpCount;
		int k;

		for (k = 0; k < fpcnt; k++)
			abytEnrolledFlag[BACKUP_FP_0 + k] = 1;
		return abytEnrolledFlag;
	}

	public byte[] GetUserIdInfo()
	{
		byte[] abytUserIdInfo = new byte[8];
		System.arraycopy(MyUtil.int2byte(UserId),0,abytUserIdInfo,0,4);

		abytUserIdInfo[4] = Privilege;
		abytUserIdInfo[5] = Enabled;

		short flagEnrolled = (short) ((PasswordFlag) | (CardFlag<<1) | (FaceFlag<<2) | (FpCount<<3) | (VeinCount<<7));
		System.arraycopy(MyUtil.short2byte(flagEnrolled), 0, abytUserIdInfo, 6, 2);
		return abytUserIdInfo;
	}
}