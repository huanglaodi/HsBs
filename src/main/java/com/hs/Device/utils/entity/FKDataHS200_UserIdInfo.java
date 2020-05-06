package com.hs.Device.utils.entity;


import java.util.Arrays;

import com.hs.Device.utils.tools.MyUtil;

public class FKDataHS200_UserIdInfo
{
    public int UserId;
    public byte Privilege;
    public byte Enabled;
    public byte BackupNumber;

     /*   public byte PasswordFlag
        {
            get { return (byte)flagEnrolled[sctPassword]; }
            set { flagEnrolled[sctPassword] = (byte)value; }
        }
        public byte CardFlag
        {
            get { return (byte)flagEnrolled[sctCard]; }
            set { flagEnrolled[sctCard] = (byte)value; }
        }
        public byte FaceFlag
        {
            get { return (byte)flagEnrolled[sctFace]; }
            set { flagEnrolled[sctFace] = (byte)value; }
        }
        public byte FpCount
        {
            get { return (byte)flagEnrolled[sctFpCount]; }
            set { flagEnrolled[sctFpCount] = (byte)value; }
        }
        public byte VeinCount
        {
            get { return (byte)flagEnrolled[sctVeinCount]; }
            set { flagEnrolled[sctVeinCount] = (byte)value; }
        }*/

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

    public FKDataHS200_UserIdInfo()
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

    public FKDataHS200_UserIdInfo(byte[] abytUserIdInfo)
    {
        if (abytUserIdInfo.length != STRUCT_SIZE)
            return;
        Enabled = abytUserIdInfo[0];
        Privilege = abytUserIdInfo[1];
        BackupNumber = abytUserIdInfo[2];
        UserId = MyUtil.byte2int(Arrays.copyOfRange(abytUserIdInfo,4,8));
            /*Privilege = abytUserIdInfo[4];
            Enabled = abytUserIdInfo[5];
            flagEnrolled = new BitVector32(BitConverter.ToInt16(abytUserIdInfo, 6));*/
    }
    /*
            public void GetBackupNumberEnrolledFlag(out byte[] abytEnrolledFlag)
            {
                abytEnrolledFlag = new byte[20];
                Array.Clear(abytEnrolledFlag, 0, 20);

                if ((byte)flagEnrolled[sctPassword] == 1)
                    abytEnrolledFlag[BACKUP_PSW] = 1;

                if ((byte)flagEnrolled[sctCard] == 1)
                    abytEnrolledFlag[BACKUP_CARD] = 1;

                if ((byte)flagEnrolled[sctFace] == 1)
                    abytEnrolledFlag[BACKUP_FACE] = 1;

                int fpcnt = flagEnrolled[sctFpCount];
                int k;

                for (k = 0; k < fpcnt; k++)
                    abytEnrolledFlag[BACKUP_FP_0 + k] = 1;
            }

            public void SetBackupNumberEnrolledFlag(byte[] abytEnrolledFlag)
            {
                if (abytEnrolledFlag.Length < 13)
                    return;

                flagEnrolled = new BitVector32(0);
                if (abytEnrolledFlag[BACKUP_PSW] == 1)
                    flagEnrolled[sctPassword] = 1;

                if (abytEnrolledFlag[BACKUP_CARD] == 1)
                    flagEnrolled[sctCard] = 1;

                if (abytEnrolledFlag[BACKUP_FACE] == 1)
                    flagEnrolled[sctFace] = 1;

                int fpcnt = 0;
                int k;
                for (k = 0; k < 10; k++)
                {
                    if (abytEnrolledFlag[BACKUP_FP_0 + k] == 1)
                        fpcnt++;
                }
                flagEnrolled[sctFpCount] = (byte)fpcnt;
            }
    */
    public byte[] GetUserIdInfo()
    {
        byte[] abytUserIdInfo = new byte[8];

        abytUserIdInfo[0] = Enabled;
        abytUserIdInfo[1] = Privilege;
        abytUserIdInfo[2] = BackupNumber;

        System.arraycopy(
                MyUtil.int2byte(UserId), 0,
                abytUserIdInfo, 4,
                4);
        return abytUserIdInfo;
    }

}