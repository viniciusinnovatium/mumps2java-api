package $Library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import mLibrary.mClass;
import mLibrary.mFncUtil;
import br.com.innovatium.mumps2java.dataaccess.COMViewDAO;
import br.com.innovatium.mumps2java.dataaccess.COMViewDAOImpl;
import br.com.innovatium.mumps2java.dataaccess.ConnectionFactory;
import br.com.innovatium.mumps2java.dataaccess.ConnectionType;
import br.com.innovatium.mumps2java.dataaccess.MetadataDAO;
import br.com.innovatium.mumps2java.dataaccess.ServiceLocator;
import br.com.innovatium.mumps2java.dataaccess.ServiceLocatorException;

public class ResultSet extends mClass {
	private PreparedStatement prepare;
	private java.sql.ResultSet resultSet;
	private COMViewDAO comViewDao;

	public Object $New() {
		try {
			comViewDao = ServiceLocator.locate(COMViewDAO.class);
		} catch (ServiceLocatorException e) {
			throw new IllegalArgumentException(
					"Fail to create data access object", e);
		}
		return this;
	}		

	public ResultSet(COMViewDAO comViewDao) {
		super();
		this.comViewDao = comViewDao;
	}

	public ResultSet() {
		super();
	}

	public void Prepare(String sqlMumps) {
		String sql = "";
		this.prepare = null;
		if(sqlMumps.equals("select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0 ")){
			//this.prepare = "select DISTINCT upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 2001 ORDER BY ID";	
			sql = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 20 ORDER BY ID_ROW";
		}else if(sqlMumps.equals("select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) ")){
			sql = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 20 ORDER BY DOB";
		}else if(mFncUtil.isMatcher(sqlMumps,"select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"","\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1))")/*prepare.matches(regex)startsWith(start = "select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"") && prepare.contains("\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) ")*/){
			String param = mFncUtil.matcher(sqlMumps,"select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"","\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1))");
			sql = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND MEDPatient.name like ?"
					+ " and rownum <= 20 ORDER BY DOB";
			//"select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"rena\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) "
			 try {
					this.prepare = comViewDao.createPreparedStatement(sql);
					this.prepare.setString(1, param+"%");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}else if(mFncUtil.isMatcher(sqlMumps,"select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith "," ")/*prepare.matches(regex)startsWith(start = "select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"") && prepare.contains("\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) ")*/){
			String param = mFncUtil.matcher(sqlMumps,"select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith "," ");
			sql = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND MEDPatient.id like ?"
					+ " and rownum <= 20";
			//"select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"rena\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) "
			 try {
					this.prepare = comViewDao.createPreparedStatement(sql);
					this.prepare.setString(1, "0||"+param+"%");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}else{				
			throw new UnsupportedOperationException();
		}
		if(this.prepare == null){
			this.prepare = comViewDao.createPreparedStatement(sql);
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
