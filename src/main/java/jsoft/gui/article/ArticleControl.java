package jsoft.gui.article;

import jsoft.*;
import jsoft.objects.*;
import java.util.*;
import org.javatuples.*;

public class ArticleControl {
	private ArticleModel am;
	
	public ArticleControl(ConnectionPool cp, Quartet<ArticleObject, Short, Byte, VIEW> infos) {
		this.am = new ArticleModel(cp, infos);
	}
	
	public ConnectionPool getCP() {
		return this.am.getCP();
	}

	public void releaseConnection() {
		this.am.releaseConnection();
	}
	
	
	//----------------------------------------------------
	
	
	
	public ArrayList<String> viewHomePage() {
		
		
		ArrayList<ArrayList<ArticleObject>> allDatas = this.am.getAllArticleObjects();
		
		return ArticleLibrary.viewHomePage(allDatas);
	}
	
	
	public String viewInnerPage(ArticleObject similar) {
		
		
		ArrayList<ArrayList<ArticleObject>> allDatas = this.am.getAllArticleObjects();
		ArrayList<Pair<CategoryObject, Integer>> cates_totals = this.am.getCates_Totals();
		HashMap<String, Integer> tags = this.am.getTags_Counts();
		
		if (similar.getArticle_id()>0) {
			//Xem chi tiết
			Optional<ArticleObject> item = this.am.getArticleObject();
			return ArticleLibrary.viewDetailPage(item, allDatas, cates_totals, tags, similar);
		}
		else {
			//Không phải xem chi tiết, có phân trang
			Triplet<Integer, Short, Byte> pagings = this.am.getPagingInfos();
			return ArticleLibrary.viewInnerPage(allDatas, cates_totals, tags, similar, pagings);
		}
		
	}
}
