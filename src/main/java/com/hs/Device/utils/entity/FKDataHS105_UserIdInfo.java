package com.hs.Device.utils.entity;

import java.util.Arrays;

import com.hs.Device.utils.tools.MyUtil;

public class FKDataHS105_UserIdInfo {
    public int UserId;
    public byte Privilege;
    public byte Enabled;

    private byte PasswordFlag;
    private byte CardFlag;
    private byte FaceFlag;
    private byte FpCount;
    private byte VeinCount;
    public byte PalmCount;

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
    public final int BACKUP_PV_0 = 13;       // Palm 0
    public final int BACKUP_PV_1 = 14;       // Palm 1
    public final int BACKUP_VEIN_0 = 20;     // Vein 0
    //}

    int flagEnrolled;

    public FKDataHS105_UserIdInfo() {
        UserId = 0;
        Privilege = 0;
        Enabled = 0;

        PasswordFlag = 0;
        CardFlag = 0;
        FaceFlag = 0;
        FpCount = 0;
        VeinCount = 0;
        PalmCount = 0;

        flagEnrolled = 0;
    }

    public FKDataHS105_UserIdInfo(byte[] abytUserIdInfo) {
        if (abytUserIdInfo.length != STRUCT_SIZE)
            return;

        UserId = MyUtil.byte2int(Arrays.copyOfRange(abytUserIdInfo, 0, 4));
        Privilege = abytUserIdInfo[4];
        Enabled = abytUserIdInfo[5];
        flagEnrolled = MyUtil.byte2Short(Arrays.copyOfRange(abytUserIdInfo, 6, 8));
    }

    public byte[] GetBackupNumberEnrolledFlag() {
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

        int pvcnt = PalmCount;

        for (k = 0; k < pvcnt; k++)
            abytEnrolledFlag[BACKUP_PV_0 + k] = 1;
        return abytEnrolledFlag;
    }

    public void SetBackupNumberEnrolledFlag(byte[] abytEnrolledFlag) {
        if (abytEnrolledFlag.length < 15)
            return;

        flagEnrolled = 0;
        if (abytEnrolledFlag[BACKUP_PSW] == 1)
            PasswordFlag = 1;

        if (abytEnrolledFlag[BACKUP_CARD] == 1)
            CardFlag = 1;

        if (abytEnrolledFlag[BACKUP_FACE] == 1)
            FaceFlag = 1;

        int fpcnt = 0;
        int k;
        for (k = 0; k < 10; k++) {
            if (abytEnrolledFlag[BACKUP_FP_0 + k] == 1)
                fpcnt++;
        }
        FpCount = (byte) fpcnt;

        int pvcnt = 0;
        for (k = 0; k < 2; k++) {
            if (abytEnrolledFlag[BACKUP_PV_0 + k] == 1)
                pvcnt++;
        }
        PalmCount = (byte) pvcnt;
    }

    public byte[] GetUserIdInfo() {
        byte[] abytUserIdInfo = new byte[8];

        System.arraycopy(
                MyUtil.int2byte(UserId), 0,
                abytUserIdInfo, 0,
                4);

        abytUserIdInfo[4] = Privilege;
        abytUserIdInfo[5] = Enabled;

        System.arraycopy(
                MyUtil.short2byte((short) flagEnrolled), 0,
                abytUserIdInfo, 6,
                2);
        return abytUserIdInfo;
    }


}