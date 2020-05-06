package com.hs.Device.utils.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hs.Device.utils.db.DBConnectionManager;

public class DBUtil {
/*	private static ThreadLocal<Connection> conns=
			new ThreadLocal<Connection>();
			
	public static Connection getConnection()throws Exception{
		Connection conn=conns.get();
		if(conn==null){
			String connectionString = Config.getString("connectionString");
			String DatabaseName = Config.getString("DatabaseName");
			String user = Config.getString("user");
			String pwd = Config.getString("pwd");
			Class.forName(Config.getString("DBDriver"));
			//ʹ�õ�sqlserver���ݿ�url
			String url="jdbc:sqlserver://"+connectionString+";DatabaseName="+DatabaseName;
			try{
				conn=DriverManager.getConnection(url, user, pwd);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			conns.set(conn);
			System.out.println("=============DB connect==============\n");
		}
		return conn;
	}
	
	public static void close(){
		Connection conn=conns.get();
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//��ThreadLocal�е�Connection ��null
			conns.set(null);
			System.out.println("=============DB close==============\n");
		}
	}*/
	/**
	* @Description �����ݿ����ӳػ�ȡ����
	* @return
	* @throws Exception
	* @attention ����ʹ�õ�sqlserver���ݿ⣬���ʹ�����������ݿ�����,����������url�ַ���
	*/
	public static Connection getConnection() throws Exception{
		Connection conn = DBConnectionManager.getInstance().getConnection();
		return conn;
	}
	
	public static void close(Connection con){
		if(con != null)
			DBConnectionManager.getInstance().freeConnection(con);
	}
	
	public static void close(Statement stmt) {
		if(stmt!=null) try {stmt.close();stmt=null;} catch (Exception e) {}
	}
	public static void close(ResultSet rs) {
		if(rs!=null) try {rs.close();rs=null;} catch (Exception e) {}
	}
	
/*	public static void openTransaction()throws Exception{
		Connection conn=getConnection();
		if(conn!=null){
			conn.setAutoCommit(false);
		}
	}
	
	public static void commit()throws Exception{
		Connection conn=conns.get();
		if(conn!=null){
			conn.commit();
		}
	}*/
	
	public static List<Map<String, Object>> select(String sql) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return returnResult(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally{
			close(stmt);
			close(rs);
			close(conn);
		}
	}
	private static List<Map<String, Object>> returnResult(ResultSet rs)
			throws SQLException {
		List<Map<String, Object>> list= new ArrayList<>();
		Map<String, Object> map = null;
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		while(rs.next()){
			map = new HashMap<>();
			for (int i = 1; i <= count; i++) {
				map.put(rsmd.getColumnName(i), rs.getObject(i));
			}
			list.add(map);
		}
		return list;
	}
	/**�ʺ�insert,update,delete
	 * @param sql
	 * @param params Ϊsql��"?"��Ӧ��ֵ
	 * */
	public static void update(String sql,Object params[]) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			for (int i = 0;params!=null && i < params.length; i++) {
				stmt.setObject(i+1, params[i]);
			}
			stmt.executeUpdate();
		}finally{
			close(stmt);
			close(conn);
		}
	}
}
