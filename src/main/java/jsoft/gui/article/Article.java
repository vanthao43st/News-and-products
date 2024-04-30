package jsoft.gui.article;

import java.sql.*;
import java.util.*;
import org.javatuples.*;
import jsoft.ShareControl;
import jsoft.objects.*;

public interface Article extends ShareControl{
	public	ResultSet getArticle(int id);
	public ArrayList<ResultSet> getArticle(Quartet<ArticleObject, Short, Byte, VIEW> infos);
}
