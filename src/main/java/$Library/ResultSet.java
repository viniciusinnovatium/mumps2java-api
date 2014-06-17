package $Library;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ejb.Stateless;

import mLibrary.mClass;
import mLibrary.mFncUtil;
import mLibrary.mVar;
import br.com.innovatium.mumps2java.dataaccess.ConnectionFactory;
import br.com.innovatium.mumps2java.dataaccess.ConnectionType;

@Stateless
public class ResultSet extends mClass {
	private final Connection con;
	private String prepare;
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

	public void Prepare(String prepare) {
		if(prepare.equals("select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0 ")){
			//this.prepare = "select DISTINCT upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 2001 ORDER BY ID";	
			this.prepare = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 2001 ORDER BY ID_ROW";
		}else{
			throw new UnsupportedOperationException();
		}
	}

	public Object Execute() {
		try {			
			resultSet = con.prepareStatement(prepare).executeQuery();
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
			throw new IllegalArgumentException("Column "+column+" not found");
		}
	}
	
	public Object GetColumnCount(){
		try {
			return resultSet.getMetaData().getColumnCount();
		} catch (SQLException e) {
			return 0;
		}
	}
	
	public Object GetColumnName(Object column){
		try {
			return resultSet.getMetaData().getColumnName(mFncUtil.numberConverter(column).intValue());
		} catch (SQLException e) {
			throw new IllegalArgumentException("Column "+column+" not found");
		}
	}
}
