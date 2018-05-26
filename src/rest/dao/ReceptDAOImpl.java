package rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rest.entities.Recept;

public class ReceptDAOImpl extends AbstractDatabaseDAO<Recept> implements ReceptDAO{

	public ReceptDAOImpl() {
		super(Recept.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Recept> searchByName(String name) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn.prepareStatement(String.format("select * from %s where %s like ?", super.clazz.getSimpleName(), Recept.NAME));
			st.setString(1, "%" + name + "%");
			ResultSet rs = st.executeQuery();
			List<Recept> list = new ArrayList<Recept>();
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
	public List<Recept> range100(int range) {
		Connection conn = createConnection();
		int end = range*100;
		int start = end-100;
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn.prepareStatement(String.format("select * from %s where %s between ? and ?", super.clazz.getSimpleName(), Recept.ID));
			st.setInt(1, start);
			st.setInt(2, end);
			ResultSet rs = st.executeQuery();
			List<Recept> list = new ArrayList<Recept>();
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

}
