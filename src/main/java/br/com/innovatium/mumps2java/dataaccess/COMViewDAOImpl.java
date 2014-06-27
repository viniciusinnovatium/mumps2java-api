package br.com.innovatium.mumps2java.dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import br.com.innovatium.mumps2java.todo.TODO;

@Stateless
public class COMViewDAOImpl extends AbstractDAO implements COMViewDAO {

	private PreparedStatement ps;

	public COMViewDAOImpl() {
		this(ConnectionType.DATASOURCE_RELATIONAL);
	}

	public COMViewDAOImpl(ConnectionType connectionType) {
		super(connectionType);
	}

	private void closeResouce(PreparedStatement ps) {
		try {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Fail to close prepare statement",
					e);
		}
	}

	public PreparedStatement createPreparedStatement(String sql) {
		try {
			closeResouce(ps);
			return ps = con.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
