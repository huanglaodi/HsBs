package com.hs.Device.utils.entity;

import java.util.Arrays;

public class EnrollData {
    public EnrollData()
    {
        BackupNumber = -1;
        bytData = new byte[0];
    }

    public EnrollData(int anBackupNumber, byte[] abytData)
    {
        BackupNumber = -1;

        if (anBackupNumber >= 0)
        {
            BackupNumber = anBackupNumber;
            if (abytData.length > 0)
            {
                bytData = new byte[abytData.length];
                bytData = Arrays.copyOf(abytData, abytData.length);
            }
        }else
        	bytData = new byte[0];
    }

    public boolean IsValid()
    {
        if (BackupNumber < 0)
            return false;
        if (bytData.length < 1)
            return false;

        return true;
    }

    private int BackupNumber;
    private byte [] bytData;
	public int getBackupNumber() {
		return BackupNumber;
	}

	public void setBackupNumber(int backupNumber) {
		BackupNumber = backupNumber;
	}

	public byte[] getBytData() {
		return bytData;
	}

	public void setBytData(byte[] bytData) {
		this.bytData = bytData;
	}
}

