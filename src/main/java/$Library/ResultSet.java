package $Library;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.jms.IllegalStateException;

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

	public Object $New(Object... args) {
		try {
			comViewDao = ServiceLocator.locate(COMViewDAO.class);
			String sql = "";
			String initvalue = args.length>=1 && args[0]!=null?args[0].toString():"";
			if(initvalue.equals("alREQ.dUReqIssue:ByReqNum")){
				sql = "SELECT ID FROM INIssue WHERE (Reference = ?)";
				this.prepare = comViewDao.createPreparedStatement(sql);
			}
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
		String sqlPreffix = "";
		if(sqlMumps.equals("select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0 ")){
			//this.prepare = "select DISTINCT upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 2001 ORDER BY ID";	
			sql = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 20 ORDER BY ID_ROW";
		}else if(sqlMumps.equals("select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) ")){
			sql = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND rownum <= 20 ORDER BY DOB";
		}else if(mFncUtil.isMatcher(sqlMumps,"select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"","\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1))")/*prepare.matches(regex)startsWith(start = "select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"") && prepare.contains("\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) ")*/){
			String param = mFncUtil.matcher(sqlMumps,"select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"","\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1))")[0];
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
		}else if(mFncUtil.isMatcher(sqlMumps,sqlPreffix = "select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and ")/*prepare.matches(regex)startsWith(start = "select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"") && prepare.contains("\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) ")*/){
			String[] params = null;
			String sqlMumpsSuffix = sqlMumps.replace(sqlPreffix, "");
			StringBuilder criteriaBuilder = new StringBuilder();
			String value = "";
			String field = "";		
			String[] sqlMumpsSuffixArray = sqlMumpsSuffix.split(" and ");
			List<String> valueList = new ArrayList<>();
			for (int i = 0; i < sqlMumpsSuffixArray.length; i++) {
				String criteriaField = " like ?";
				String sqlMumpsSuffixItem = sqlMumpsSuffixArray[i];
				/*
				if(mFncUtil.isMatcher(sqlMumpsSuffixItem,"$find( $$RemoveMark^COMViewSQL(%upper(","),\"0\",\"","\") ,\"","\") > 1 ")){
					params = mFncUtil.matcher(sqlMumpsSuffixItem,"$find( $$RemoveMark^COMViewSQL(%upper(","),\"0\",\"","\") ,\"","\") > 1 ");
					field = params[0];
					value = "%"+params[2].toUpperCase()+"%";
					criteriaField = field+criteriaField;
				}else if(mFncUtil.isMatcher(sqlMumpsSuffixItem," $$RemoveMark^COMViewSQL(%upper(","),\"0\",\"","\")  %startswith \"","\" ")){
					params = mFncUtil.matcher(sqlMumpsSuffixItem," $$RemoveMark^COMViewSQL(%upper(","),\"0\",\"","\")  %startswith \"","\" ");		
					field = params[0];
					value = params[2].toUpperCase()+"%";
					criteriaField = field+criteriaField;
				}else if(mFncUtil.isMatcher(sqlMumpsSuffixItem," $$RemoveMark^COMViewSQL(%upper(","),\"0\",\"","\")  = \"","\"")){
					params = mFncUtil.matcher(sqlMumpsSuffixItem," $$RemoveMark^COMViewSQL(%upper(","),\"0\",\"","\")  = \"","\"");	
					field = params[0];
					value = params[2].toUpperCase();	
					criteriaField = field+criteriaField;
				}else if(mFncUtil.isMatcher(sqlMumpsSuffixItem,"( $$RemoveMark^COMViewSQL(%upper(","),\"0\",\"","\")  <> \"","\" OR  $$RemoveMark^COMViewSQL(%upper(","),\"0\",\"","\")  IS NULL) ")){
					params = mFncUtil.matcher(sqlMumpsSuffixItem,"( $$RemoveMark^COMViewSQL(%upper(","),\"0\",\"","\")  <> \"","\" OR  $$RemoveMark^COMViewSQL(%upper(","),\"0\",\"","\")  IS NULL) ");		
					field = params[0];
					value = params[2].toUpperCase();
					criteriaField = "("+field+" <> ? or "+field+" is null)";
				}else{
					throw new UnsupportedOperationException("Criteria not implemented for "+sqlMumpsSuffixItem);
				}*/
				if(mFncUtil.isMatcher(sqlMumpsSuffixItem,"$find(",",",") > 1 ")){
					params = mFncUtil.matcherLast(sqlMumpsSuffixItem,"$find(",",",") > 1 ");
					field = mFncUtil.convertMumpsSqlFieldToJavaSqlField(params[0]);
					value = "%"+mFncUtil.convertMumpsSqlValueToJavaSqlValue(params[1]).toUpperCase()+"%";
					criteriaField = field+criteriaField;
				}else if(mFncUtil.isMatcher(sqlMumpsSuffixItem," %startswith "," ")){
					params = mFncUtil.matcherLast(sqlMumpsSuffixItem," %startswith "," ");		
					field = mFncUtil.convertMumpsSqlFieldToJavaSqlField(params[0]);
					value = mFncUtil.convertMumpsSqlValueToJavaSqlValue(params[1]).toUpperCase()+"%";
					criteriaField = field+criteriaField;
				}else if(mFncUtil.isMatcher(sqlMumpsSuffixItem," = ")){
					params = mFncUtil.matcher(sqlMumpsSuffixItem," = ");	
					field = mFncUtil.convertMumpsSqlFieldToJavaSqlField(params[0]);
					value = mFncUtil.convertMumpsSqlValueToJavaSqlValue(params[1]).toUpperCase();	
					criteriaField = field+criteriaField;
				}else if(mFncUtil.isMatcher(sqlMumpsSuffixItem,"("," <> "," OR "," IS NULL) ")){
					params = mFncUtil.matcher(sqlMumpsSuffixItem,"("," <> "," OR "," IS NULL) ");		
					field = mFncUtil.convertMumpsSqlFieldToJavaSqlField(params[0]);
					value = mFncUtil.convertMumpsSqlValueToJavaSqlValue(params[1]).toUpperCase();
					criteriaField = "("+field+" <> ? or "+field+" is null)";
				}else if(mFncUtil.isMatcher(sqlMumpsSuffixItem," is not null AND "," not in (",") ")){
					params = mFncUtil.matcher(sqlMumpsSuffixItem," is not null AND "," not in (",") ");		
					field = mFncUtil.convertMumpsSqlFieldToJavaSqlField(params[0]);
					value = mFncUtil.convertMumpsSqlValueToJavaSqlValue(params[2]).toUpperCase();
					criteriaField = field+" is not null and "+field+" not in (?)";
				}else{
					throw new UnsupportedOperationException("Criteria not implemented for "+sqlMumpsSuffixItem);
				}				
				if(i>0){
					criteriaBuilder.append(" and ");
				}
				criteriaBuilder.append(criteriaField);
				valueList.add(value);
			}
	
			sql = "select DISTINCT upper(MEDPatient.ID) AS ID_ROW,SQLUser.MEDPatient.* from SQLUser.MEDPatient where MEDPatient.company = 0 AND "+criteriaBuilder
					+ " and rownum <= 20";
			//"select DISTINCT top 2001 %upper(MEDPatient.ID) AS ID from SQLUser.MEDPatient where MEDPatient.company = 0  and P1 %startswith \"rena\"  order by %upper(+$piece(SQLUser.MEDPatient.DOB,\".\",1)) "
			 try {
					this.prepare = comViewDao.createPreparedStatement(sql);
					for (int i = 0; i < valueList.size(); i++) {
						String valueItem = valueList.get(i);
						this.prepare.setString(i+1, valueItem);						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}else{				
			throw new UnsupportedOperationException("Fail of implementation in ResultSet.Prepare("+sqlMumps+")");
		}
		if(this.prepare == null){
			this.prepare = comViewDao.createPreparedStatement(sql);
		}
	}

	public Object Execute(Object... args) {
		try {
			String p1 = args.length>=1 && args[0]!=null?args[0].toString():"";
			if(!p1.isEmpty()){				
				this.prepare.setString(1, p1);		
			}			
			resultSet = prepare.executeQuery();			
			return 1;

		} catch (SQLException e) {
			System.err.println("Fail in ResultSet.Execute("+Arrays.toString(args)+"): "+e.getMessage());
			return 0;
		}catch(NullPointerException e){
			System.err.println("Fail in ResultSet.Execute("+Arrays.toString(args)+"): "+e.getMessage());
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
