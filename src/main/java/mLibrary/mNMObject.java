package mLibrary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import br.com.innovatium.mumps2java.dataaccess.RelationalDataDAO;
import br.com.innovatium.mumps2java.dataaccess.ServiceLocator;
import br.com.innovatium.mumps2java.dataaccess.ServiceLocatorException;
import br.com.innovatium.mumps2java.dataaccess.exception.SQLExecutionException;

public class mNMObject {
	private final RelationalDataDAO dao;

	public mNMObject() {
		try {
			dao = ServiceLocator.locate(RelationalDataDAO.class);
		} catch (ServiceLocatorException e) {
			throw new IllegalArgumentException(
					"There is not service to locate to this class "
							+ RelationalDataDAO.class.getName(), e);
		}
	}

	public String loadRecord(mContext m$, String className, String id) {
		String classCDef = (String) m$.var("^WWWCLASSCDEF", className, "def")
				.get();
		if ((classCDef == null) || (classCDef.isEmpty())) {
			return "";
		}
		String tableSQLName = mFncUtil.splitDemiliter(classCDef)[1];

		String classCDefCol = (String) m$.var("^WWWCLASSCDEF", className,
				"coldef").get();
		if ((classCDefCol == null) || (classCDefCol.isEmpty())) {
			return "";
		}
		String[] classCDefColMap = classCDefCol.split(";");

		String[] fieldCDefMap;
		StringBuilder tableSQLFields = new StringBuilder();
		for (int i = 0; i < classCDefColMap.length; i++) {
			fieldCDefMap = classCDefColMap[i].split("~");
			if (fieldCDefMap.length >= 2 && fieldCDefMap[1].trim().length() > 0) {
				tableSQLFields.append(fieldCDefMap[1]);
			} else {
				tableSQLFields.append("null");
			}
			if (i + 1 < classCDefColMap.length) {
				tableSQLFields.append(",");
			}
		}
		//
		ResultSet result = null;
		try {
			result = dao
					.loadRecord(tableSQLName, id, tableSQLFields.toString());
		} catch (SQLExecutionException e1) {
			throw new MLibraryException("Fail to load record from the table "
					+ className + " and id " + id, e1);
		}
		if (result == null) {
			return "";
		}
		//
		StringBuilder resultRecord = new StringBuilder();
		for (int i = 0; i < classCDefColMap.length; i++) {
			fieldCDefMap = mFncUtil.splitDemiliter(classCDefColMap[i]);

			if (fieldCDefMap.length >= 3) {
				// Date
				if (fieldCDefMap[2].equals("1")) {
					try {
						resultRecord.append(convertDateToMumps(result
								.getDate(i + 1)));
					} catch (SQLException e) {
						throw new IllegalArgumentException(
								"Fail to get result set", e);
					}
				}
				// Timestamp
				else if (fieldCDefMap[2].equals("14")) {
					try {
						resultRecord.append(convertDateToMumps(result
								.getTimestamp(i + 1)));
					} catch (SQLException e) {
						throw new IllegalArgumentException(
								"Fail to get result set", e);
					}
				}
				// Others
				else {
					try {
						Object obj = result.getObject(i + 1);
						if (obj != null) {
							if (obj.getClass().getName().contains("CLOB")) {
								try {
									resultRecord.append(dao.toString(result
											.getClob(i + 1)));
								} catch (SQLExecutionException e) {
									throw new MLibraryException(
											"Fail to parse clob from the table "
													+ className + " and id "
													+ id + " and column "
													+ (i + 1), e);
								}
							} else {
								resultRecord.append(obj);
							}
						} else {
							resultRecord.append("");
						}

					} catch (SQLException e) {
						throw new IllegalArgumentException(
								"Fail to get result set", e);
					}
				}
			}
			// Others
			else {
				try {
					resultRecord
							.append(result.getObject(i + 1) != null ? result
									.getObject(i + 1) : "");
				} catch (SQLException e) {
					throw new IllegalArgumentException(
							"Fail to get result set", e);
				}
			}

			if (i + 1 < classCDefColMap.length) {
				resultRecord.append("~");
			}
		}

		try {
			result.getStatement().close();
		} catch (SQLException e) {
			throw new IllegalArgumentException("Fail to close result set", e);
		}
		return resultRecord.toString();
	}

	public String saveRecord(mContext m$, String className, String id,
			String record) {

		String classCDef = (String) m$.var("^WWWCLASSCDEF", className, "def")
				.get();
		if ((classCDef == null) || (classCDef.isEmpty())) {
			return "";
			//throw new IllegalStateException(	"There is no one configuration to this class: " + className);
		}
		String tableSQLName = mFncUtil.splitDemiliter(classCDef)[1];

		String classCDefPK = (String) m$.var("^WWWCLASSCDEF", className,
				"pkdef").get();
		if ((classCDefPK == null) || (classCDefPK.isEmpty())) {
			return "";
			//throw new IllegalStateException(	"There is no primary key configuration to this class: "+ className);
		}
		String[] classCDefPKMap = classCDefPK.split(";");

		String classCDefCol = (String) m$.var("^WWWCLASSCDEF", className,
				"coldef").get();
		if ((classCDefCol == null) || (classCDefCol.isEmpty())) {
			throw new IllegalStateException(
					"There is no column configuration to this class: "
							+ className);
		}
		String[] classCDefColMap = classCDefCol.split(";");

		boolean exists;
		try {
			exists = dao.existsRecord(tableSQLName, id);
		} catch (SQLExecutionException e) {
			throw new MLibraryException(
					"Fail to verify existence of the record from the table "
							+ className + " and id " + id, e);
		}

		String tableSQLFields = "";
		Object[] tableSQLValues;
		if (!exists) {
			tableSQLValues = new Object[classCDefPKMap.length
					+ classCDefColMap.length];
		} else {
			tableSQLValues = new Object[classCDefColMap.length];
		}
		String[] fieldCDefMap;

		int count = 0;

		if (!exists) {
			String[] idMap = id.split("\\|\\|");
			//
			for (int i = 0; i < classCDefPKMap.length; i++) {
				fieldCDefMap = mFncUtil.splitDemiliter(classCDefPKMap[i]);
				if (fieldCDefMap.length > 2) {
					tableSQLFields = tableSQLFields
							+ (tableSQLFields.isEmpty() ? "" : ",")
							+ fieldCDefMap[1];
					if (i < idMap.length) {
						// Date
						if (fieldCDefMap[2].equals("1")) {
							tableSQLValues[count++] = convertDateToJava(idMap[i]);
						}
						// Timestamp
						else if (fieldCDefMap[2].equals("14")) {
							tableSQLValues[count++] = convertTimestampToJava(idMap[i]);
						}
						// Others
						else {
							tableSQLValues[count++] = idMap[i];
						}
					} else {
						tableSQLValues[count++] = null;
					}
				}
			}
		}
		//
		String[] recordMap = mFncUtil.splitDemiliter(record);
		//
		for (int i = 0; i < classCDefColMap.length; i++) {
			fieldCDefMap = mFncUtil.splitDemiliter(classCDefColMap[i]);
			if (fieldCDefMap.length > 2) {
				tableSQLFields = tableSQLFields
						+ (tableSQLFields.isEmpty() ? "" : ",")
						+ fieldCDefMap[1];
				if (i < recordMap.length) {
					// Date
					if (fieldCDefMap[2].equals("1")) {
						tableSQLValues[count++] = convertDateToJava(recordMap[i]);
					}
					// Timestamp
					else if (fieldCDefMap[2].equals("14")) {
						tableSQLValues[count++] = convertTimestampToJava(recordMap[i]);
					}
					// Others
					else {
						tableSQLValues[count++] = recordMap[i];
					}
				} else {
					tableSQLValues[count++] = null;
				}
			}
		}
		//
		if (!exists) {
			try {
				return dao.insertRecord(tableSQLName, tableSQLFields,
						tableSQLValues);
			} catch (SQLExecutionException e) {
				throw new MLibraryException(
						"Fail to insert record into the table " + className
								+ " and id " + id, e);
			}
		} else {
			try {
				return dao.updateRecord(tableSQLName, id, tableSQLFields,
						tableSQLValues);
			} catch (SQLExecutionException e) {
				throw new MLibraryException(
						"Fail to update record from the table " + className
								+ " and id " + id, e);
			}
		}
	}

	public String deleteRecord(mContext m$, String className, String id) {
		String classCDef = (String) m$.var("^WWWCLASSCDEF", className, "def")
				.get();
		if ((classCDef == null) || (classCDef.isEmpty())) {
			return "";
		}
		String tableSQLName = mFncUtil.splitDemiliter(classCDef)[1];

		try {
			return dao.deleteRecord(tableSQLName, id);
		} catch (SQLExecutionException e) {
			throw new MLibraryException("Fail to delete record from the table "
					+ className + " and id " + id, e);
		}
	}

	public String convertDateToMumps(Object val) {
		if (val == null) {
			return "";
		}
		Double res = mFncUtil.dateJavaToMumps(((Date) val).getTime());
		if (res == null) {
			return "";
		}

		return (String.valueOf(res.longValue()));
	}

	public Date convertDateToJava(String val) {
		if (val == null) {
			return null;
		}
		if (val.isEmpty()) {
			return null;
		}
		return new Date(mFncUtil.dateMumpsToJava(val).longValue());
	}

	public Timestamp convertTimestampToJava(String val) {
		if (val == null) {
			return null;
		}
		if (val.isEmpty()) {
			return null;
		}
		return new Timestamp(mFncUtil.dateMumpsToJava(val).longValue());
	}
}
