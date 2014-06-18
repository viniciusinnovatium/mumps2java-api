package $Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;

import mLibrary.mClass;
import mLibrary.mFncUtil;
import mLibrary.mVar;
import br.com.innovatium.mumps2java.dataaccess.ConnectionFactory;
import br.com.innovatium.mumps2java.dataaccess.ConnectionType;

@Stateless
public class ResultSet extends mClass {
	private final Connection con;
	private PreparedStatement prepare;
	private java.sql.ResultSet resultSet;

	public Object $New() {
		return new ResultSet();
	}

	public ResultSet(ConnectionType connectionType) {
		try {
			con = ConnectionFactory.getConnection(connectionType);
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to open connection to database access throught "
							+ connectionType + " strategy", e);
		}
	}

	public ResultSet() {
		this(ConnectionType.DATASOURCE_RELATIONAL);
	}

	public void Prepare(String sqlMumps) {
		String sql = "";
		this.prepare = null;
		if(sqlMumps.equals("select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0 ")){
			//this.prepare = "select DISTINCT upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 2001 ORDER BY ID";	
			sql = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 2001 ORDER BY ID_ROW";
		}else if(sqlMumps.equals("select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) ")){
			sql = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 2001 ORDER BY DOB";
		}else if(!mFncUtil.matcher(sqlMumps,"(\\Qselect DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"\\E{1,1}).*?(\\Q\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1))\\E{1,1})",0).isEmpty()/*prepare.matches(regex)startsWith(start = "select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"") && prepare.contains("\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) ")*/){
			String param = mFncUtil.matcher(sqlMumps,"(\\Qselect DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"\\E{1,1}).*?(\\Q\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1))\\E{1,1})",null);
			sql = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND MEDPatient.name like '%?' and rownum <= 2001 ORDER BY DOB";
			//"select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"rena\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) "
			 try {
					this.prepare = con.prepareStatement(sql);
					this.prepare.setObject(1, param);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}else{				
			throw new UnsupportedOperationException();
		}
		if(this.prepare == null){
		 try {
			this.prepare = con.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	public Object Execute() {
		try {
			resultSet = prepare.executeQuery();
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public Object Next() {
		try {
			if (resultSet.next()) {
				return 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public Object Data(String column) {
		try {
			return resultSet.getObject(column);
		} catch (SQLException e) {
			throw new IllegalArgumentException("Column " + column
					+ " not found");
		}
	}

	public Object GetColumnCount() {
		try {
			return resultSet.getMetaData().getColumnCount();
		} catch (SQLException e) {
			return 0;
		}
	}

	public Object GetColumnName(Object column) {
		try {
			return resultSet.getMetaData().getColumnName(
					mFncUtil.numberConverter(column).intValue());
		} catch (SQLException e) {
			throw new IllegalArgumentException("Column " + column
					+ " not found");
		}
	}
}
