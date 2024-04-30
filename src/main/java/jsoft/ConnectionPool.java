package jsoft;

import java.sql.*;


/**
 * Giao tiếp / giao diện cung cấp các chức năng làm việc với kết nối (<b>connection</b>)<br>
 * 
 * @author Jsoft
 *
 */

public interface ConnectionPool {
	public Connection getConnection(String objectName) throws SQLException;
	public void releaseConnection(Connection con, String objectName) throws SQLException;
}
