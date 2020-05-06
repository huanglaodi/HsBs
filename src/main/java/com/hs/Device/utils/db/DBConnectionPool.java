package com.hs.Device.utils.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;

public class DBConnectionPool{
	private Connection con=null;
	private int inUsed=0;    //ʹ�õ�������
	private LinkedList<Connection> freeConnections = new LinkedList<>();//��������������
	private int minConn;     //��С������
	private int maxConn;     //�������
	private String name;     //���ӳ�����
	private String password; //����
	private String url;      //���ݿ����ӵ�ַ
	private String driver;   //����
	private String user;     //�û���
	public Timer timer;      //��ʱ
	/**
	 * �������ӳ�
	 */
	public DBConnectionPool() {
	}
	/**
	 * �������ӳ�
	 * @param driver
	 * @param name
	 * @param URL
	 * @param user
	 * @param password
	 * @param maxConn
	 */
	public DBConnectionPool(String name, String driver,String URL, String user, String password, int maxConn)
	{
		this.name=name;
		this.driver=driver;
		this.url=URL;
		this.user=user;
		this.password=password;
		this.maxConn=maxConn;
	}
	/**
	 * ���꣬�ͷ�����
	 * @param con
	 */
	public synchronized void freeConnection(Connection con) 
	{
		this.freeConnections.add(con);//��ӵ��������ӵ�ĩβ
		this.inUsed--;
	}
	/**
	 * �����ӳ���õ�����
	 * @return
	 * @throws Exception 
	 */
	public synchronized Connection getConnection() throws Exception
	{
		Connection con=null;
		if(this.maxConn==0||this.maxConn<this.inUsed)
		{
			throw new Exception("The number of connections is full!");
		}
		if(this.freeConnections.size()>0)
		{
//			con=(Connection)this.freeConnections.peekFirst();
			con = this.freeConnections.removeFirst();//������ӷ����ȥ�ˣ��ʹӿ���������ɾ��
			if(con==null)con=getConnection(); //�����������
		}
		else
		{
			con=newConnection(); //�½�����
		}
		if(con!=null)
		{
			this.inUsed++;
//			LogUtil.getInstance(true).i("DB_"+name, "Connect used-->"+inUsed);
		}
		return con;
	}
	/**
	 *�ͷ�ȫ������
	 *
	 */
	public synchronized void release()
	{
		Iterator<Connection> allConns=this.freeConnections.iterator();
		while(allConns.hasNext())
		{
			Connection con=allConns.next();
			try
			{
				con.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}

		}
		this.freeConnections.clear();

	}
	/**
	 * ����������
	 * @return
	 */
	private Connection newConnection()
	{
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("sorry can't find db driver!");
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("sorry can't create Connection!");
		}
		return con;

	}
	/**
	 * ��ʱ������
	 */
	public synchronized void TimerEvent() 
	{
		//��ʱ��û��ʵ���Ժ����ϵ�
	}
	/**
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * @param driver the driver to set
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	/**
	 * @return the maxConn
	 */
	public int getMaxConn() {
		return maxConn;
	}
	/**
	 * @param maxConn the maxConn to set
	 */
	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}
	/**
	 * @return the minConn
	 */
	public int getMinConn() {
		return minConn;
	}
	/**
	 * @param minConn the minConn to set
	 */
	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	* @param used connect
	*/
	public int getInUsed() {
		return inUsed;
	}
}