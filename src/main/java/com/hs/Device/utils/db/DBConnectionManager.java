package com.hs.Device.utils.db;

import java.sql.Connection;

import com.hs.Device.utils.tools.Config;

public class DBConnectionManager {  
	private static DBConnectionManager instance;//Ψһ���ݿ����ӳع���ʵ����  
	private DBConnectionPool pool=new DBConnectionPool();//���ӳ� 
	private DSConfigBean driver;
	/** 
	 * ʵ���������� 
	 */  
	private DBConnectionManager() {  
		this.init();  
	}  
	/** 
	 * �õ�Ψһʵ�������� 
	 * @return 
	 */  
	static synchronized public DBConnectionManager getInstance()  
	{  
		if(instance==null)  
		{  
			instance=new DBConnectionManager();  
		}  
		return instance;  
	}
	
	/** 
	 * �ͷ����� 
	 * @param con 
	 */  
	public void freeConnection(Connection con)  
	{  
		pool.freeConnection(con);//�ͷ�����   
	}  
	/** 
	 * @return 
	 * @throws Exception 
	 */  
	public Connection getConnection() throws Exception  
	{  
		Connection con=null;  
		con=pool.getConnection();//��ѡ�������ӳ��л������  
		return con;  
	}  
	/** 
	 * �ͷ��������� 
	 */  
	public synchronized void release()  
	{  
		if(pool!=null)pool.release();
	}  
	/** 
	 * �������ӳ� 
	 */  
	private void createPools(DSConfigBean dsb)  
	{  
		pool.setName(dsb.getName());  
		pool.setDriver(dsb.getDriver());  
		pool.setUrl(dsb.getUrl());  
		pool.setUser(dsb.getUsername());  
		pool.setPassword(dsb.getPassword());  
		pool.setMaxConn(dsb.getMaxconn());  
	}  
	/** 
	 * ��ʼ�����ӳصĲ��� 
	 */  
	private void init()  
	{  
		//������������  
		this.loadDrivers();  
		this.createPools(driver);
		System.out.println("�������ӳ���ϡ�����");  
	}  
	/** 
	 * ������������ 
	 */  
	private void loadDrivers()  
	{  
		driver = new DSConfigBean();
		driver.setType("MSSQL");  
		driver.setName("FkWebServer");  
		driver.setDriver(Config.getString("DBDriver"));
		String url="jdbc:sqlserver://"+Config.getString("connectionString")+";DatabaseName="+Config.getString("DatabaseName");
		driver.setUrl(url);
		driver.setUsername(Config.getString("user"));  
		driver.setPassword(Config.getString("pwd"));  
		driver.setMaxconn(Integer.parseInt(Config.getString("maxconn")));  
		//��ȡ���ݿ������ļ�  
		System.out.println("�����������򡣡���");  
	}
	@Override
	public String toString() {
		return pool.getName() + "--" + pool.getInUsed() + " connect is used";
	}
}
