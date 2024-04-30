package jsoft.gui.basic;

import java.sql.*;
import java.util.ArrayList;

import jsoft.*;

public interface Basic extends ShareControl{
	
	//PreparedStatement pre - Câu lệnh thực thi này đã được biên dịch,
	// được truyền dữ liệu đầy đủ.
	
	public ResultSet get(String sql, int id);
	public ResultSet get(String sql, String name, String pass);
	public ArrayList<ResultSet> getsMR(String multiSelect);
}
