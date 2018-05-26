package rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rest.entities.Operater;

public class OperaterDAOImpl extends AbstractDatabaseDAO<Operater> implements OperaterDAO{

	public OperaterDAOImpl() {
		super(Operater.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Operater getOperater(String username, String password) {
		
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		
		try {
			Operater objectForIdName = clazz.newInstance();
			PreparedStatement st = conn.prepareStatement(String.format("select * from %s where %s=? and %s=?", this.clazz.getSimpleName(), objectForIdName.USERNAME, objectForIdName.PASSWORD));
			st.setObject(1, username);
			st.setObject(2, password);
			
			ResultSet rs = st.executeQuery();
			Operater object = null;
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

}
