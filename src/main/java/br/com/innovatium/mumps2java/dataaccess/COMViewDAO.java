package br.com.innovatium.mumps2java.dataaccess;

import java.sql.PreparedStatement;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface COMViewDAO {
	PreparedStatement createPreparedStatement(String sql);
}
