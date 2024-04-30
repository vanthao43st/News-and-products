package jsoft.gui.article;

import java.sql.*;
import java.util.*;
import org.javatuples.*;
import jsoft.objects.*;
import jsoft.*;
import jsoft.gui.basic.*;
import jsoft.library.Utilities;

public class ArticleImpl extends BasicImpl implements Article {
	
	public ArticleImpl(ConnectionPool cp) {
		super(cp, "Article-GUI");
	}

	@Override
	public ResultSet getArticle(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <b>input</b>: Quartet<ArticleObject, Short, Byte, VIEW> infos  </br>
	 * <b>ArticleObject</b>: Đối tượng lấy bản ghi </br>
	 * <b>Short</b>: số trang </br>
	 * <b>Byte</b>: số bản ghi được lấy </br>
	 * <b>VIEW</b>: loại trang cần lấy
	 * 
	 */
	@Override
	public ArrayList<ResultSet> getArticle(Quartet<ArticleObject, Short, Byte, VIEW> infos) {
		
		ArticleObject similar = infos.getValue0();				
		byte total = infos.getValue2();							// Số bản ghi được lấy
		int at = (infos.getValue1() - 1)*total;					// Vị trí bắt đầu lấy bản ghi
		VIEW v = infos.getValue3();
		
		StringBuilder join = new StringBuilder();
		join.append("SELECT * FROM tblarticle ");
		join.append("LEFT JOIN tblcategory ON article_category_id = category_id ");
		join.append("LEFT JOIN tblsection ON category_section_id = section_id ");
		
		//Main SQL
		StringBuilder sql = new StringBuilder(join);
		sql.append(this.createConditions(similar));
		sql.append("ORDER BY DATE(STR_TO_DATE(article_last_modified, \"%d/%m/%Y\")) DESC ");
		sql.append("LIMIT ").append(at).append(", ").append(total).append("; ");
		
		//Trending SQL
		sql.append(join);
		sql.append(this.createConditions(similar));
		sql.append("ORDER BY article_visited DESC LIMIT 5; ");
		
		if (v==VIEW.HOMEPAGE) {
			//Project Image SQL
			sql.append(join);
			similar = new ArticleObject();
			similar.setArticle_section_id((short)11);
			sql.append(this.createConditions(similar));
			sql.append("ORDER BY article_id DESC LIMIT 10; ");
			
			
			//Service SQL
			sql.append(join);
			similar.setArticle_section_id((short)8);	//8
			sql.append(this.createConditions(similar));
			sql.append("ORDER BY article_id DESC LIMIT 10; ");
		} else {
			sql.append(join);
			sql.append(this.createConditions(similar));
			sql.append("ORDER BY article_id DESC LIMIT 10; ");
			
			
			// Danh sách thể loại
			sql.append("SELECT category_id, category_name, COUNT(article_id) AS total FROM ");
			sql.append("tblarticle LEFT JOIN tblcategory ON article_category_id=category_id ");
			if (similar.getArticle_section_id()>0) {
				sql.append("WHERE (category_section_id="+similar.getArticle_section_id()+") ");
			}
			sql.append("GROUP BY article_category_id ");
			sql.append("ORDER BY category_name ASC ; ");
			
			
			//Danh sách từ khoá (Tags)
			sql.append("SELECT article_tag FROM tblarticle ");
			sql.append(this.createConditions(similar).append("; "));
			
			if (similar.getArticle_id()>0) {
				sql.append(join);
				sql.append("WHERE article_id=").append(similar.getArticle_id());
			}
			else {
				//Tổng số bản ghi
				sql.append("SELECT COUNT(article_id) AS total FROM tblarticle ");
				sql.append(this.createConditions(similar));
			}
		}
		
		
		
		
		
		
		return this.getsMR(sql.toString());
	}

	private StringBuilder createConditions(ArticleObject similar) {
		
		StringBuilder tmp = new StringBuilder();
		if (similar!=null) {
			//Lấy ra chuyên mục
			short sid = similar.getArticle_section_id();
			if (sid == 0) {
				sid = similar.getCategory_section_id();
			}
			
			if (sid==0) {
				sid= similar.getSection_id();
			}
			
			if (sid>0) {
				tmp.append("(article_section_id=").append(sid).append(")");
			}
			
			
			
			//Thể loại
			short cid = similar.getArticle_category_id();
			if (cid==0) {
				cid = similar.getCategory_id();
			}
			
			if (cid>0) {
				if (!tmp.toString().equalsIgnoreCase("")) {
					tmp.append(" AND ");
				}
				
				tmp.append("(article_category_id=").append(cid).append(")");
			}
			
			
			String key = similar.getArticle_title();
			if (key!=null&&!key.equalsIgnoreCase("")) {
				
				key=Utilities.encode(key);
				
				if (!tmp.toString().equalsIgnoreCase("")) {
					tmp.append(" AND ");
				}
				
				tmp.append("(");
				tmp.append("(article_title LIKE '%"+key+"%') OR ");
				tmp.append("(article_summary LIKE '%"+key+"%') OR ");
				tmp.append("(article_content LIKE '%"+key+"%') OR ");
				tmp.append("(article_tag LIKE '%"+key+"%') ");
				tmp.append("");
				tmp.append("");
				tmp.append(")");
			}
			
			
		}
		
		if (!tmp.toString().equalsIgnoreCase("")) {
			tmp.append(" AND ");
		}
			
			tmp.append("(article_enable=1) AND (article_delete=0)");
			tmp.insert(0, " WHERE ");
		
		
		return tmp;
	}
}
