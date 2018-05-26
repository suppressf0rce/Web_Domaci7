package rest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;


import rest.entities.BaseEntity;

public abstract class AbstractDatabaseDAO<T extends BaseEntity> implements AbstractDAO<T> {
	public AbstractDatabaseDAO(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public boolean add(T object) {
		if( object == null ){
			return false;
		}
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}
		
		String columnsName = "";
		String questionMarks = "";
		for(String columnName : object.columnsName()){
			columnsName = columnsName == "" ? columnName : String.format("%s, %s", columnsName, columnName);
			questionMarks = questionMarks == "" ? "?" : String.format("%s, ?", questionMarks);
		}
		
		String strQuery = String.format("INSERT INTO %s (%s) VALUES (%s)", this.clazz.getSimpleName(), columnsName, questionMarks);
		
		try {
			PreparedStatement st = conn.prepareStatement(strQuery);
			int parameterIndex = 1;
			for(String columnName : object.columnsName()){
				st.setObject(parameterIndex++, object.getValueForColumnName(columnName));
			}
			return st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean removeById(int id) {
		return false;
	}

	@Override
	public boolean update(T object) {
		return true;
	}

	@Override
	public List<T> getAll() {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn.prepareStatement(String.format("select * from %s", this.clazz.getSimpleName()));
			ResultSet rs = st.executeQuery();
			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				list.add(readFromResultSet(rs));
			}
			closeStat(st);
			closeResultSet(rs);
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	@Override
	public T getById(int id) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		
		try {
			T objectForIdName = clazz.newInstance();
			PreparedStatement st = conn.prepareStatement(String.format("select * from %s where %s=?", this.clazz.getSimpleName(), objectForIdName.ID));
			st.setObject(1, id);
			
			ResultSet rs = st.executeQuery();
			T object = null;
			if( rs.next()) {
				object = readFromResultSet(rs);
			}
			closeStat(st);
			closeResultSet(rs);
			return object;
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}
	
	/**
	 * Pomocna metoda koja sluzi da cita podatke iz prosledjenog result seta.
	 * @param rs
	 * @return
	 */
	protected T readFromResultSet(ResultSet rs){
		if( rs == null ){
			return null;
		}
		
		try {
			T object = this.clazz.newInstance();
			object = clazz.newInstance();
			for(String columnName : object.columnsName()){
				object.setValueForColumnName(columnName, rs.getObject(columnName));
			}
			
			return object;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Pomocna metoda koja kreira konekciju ka bazi
	 * 
	 * @return
	 */
	protected Connection createConnection() {
		try {
			DataSource dataSource = setUpPool();
			return dataSource.getConnection();
		} catch (Exception e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Pomocna metoda zatvaranje konekcije
	 * 
	 * @param conn
	 */
	protected void closeConnection(Connection conn) {
		if (conn == null) {
			return;
		}

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}
	}

	/**
	 * Pomocna metoda za zatvaranje statemnt-a
	 * 
	 * @param stat
	 */
	protected void closeStat(PreparedStatement stat) {
		if (stat == null) {
			return;
		}

		try {
			stat.close();
		} catch (SQLException e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}
	}

	/**
	 * Pomocna metoda koja zatvara prosledjeni result set.
	 * @param rs
	 */
	protected void closeResultSet(ResultSet rs){
		if( rs == null ){
			return;
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// property
	/**
	 * Svojstvo koje cuva klasu za koju se pravi query
	 */
	public Class<T> clazz;

	// constants
	private String USERNAME = "domaci7";
	private String PASSWORD = "domaci7";
	
	
	
	///Connection pool
	private static GenericObjectPool gPool = null;

	@SuppressWarnings("unused")
	public DataSource setUpPool() throws Exception {
		if(gPool == null) {
		Class.forName("com.mysql.jdbc.Driver");

		// Creates an Instance of GenericObjectPool That Holds Our Pool of Connections Object!
		gPool = new GenericObjectPool();
		gPool.setMaxActive(5);

		// Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create the Connection Object!
		ConnectionFactory cf = new DriverManagerConnectionFactory("jdbc:mysql://localhost/domaci7?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", USERNAME, PASSWORD);

		// Creates a PoolableConnectionFactory That Will Wraps the Connection Object Created by the ConnectionFactory to Add Object Pooling Functionality!
		PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
		}
		
		return new PoolingDataSource(gPool);
		
	}

	private GenericObjectPool getConnectionPool() {
		return gPool;
	}

	// This Method Is Used To Print The Connection Pool Status
	protected void printDbStatus() {
		System.out.println("Max.: " + getConnectionPool().getMaxActive() + "; Active: " + getConnectionPool().getNumActive() + "; Idle: " + getConnectionPool().getNumIdle());
	}

}
