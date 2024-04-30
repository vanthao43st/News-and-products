package jsoft.gui.article;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import org.javatuples.*;
import jsoft.objects.*;
import jsoft.*;

public class ArticleModel {
	private Article a;
	
	private ArrayList<ArrayList<ArticleObject>> items;
	private ArrayList<Pair<CategoryObject, Integer>> cates;
	private HashMap<String, Integer> tags;
	private Triplet<Integer, Short, Byte> paging;
	private ArticleObject item;

	public ArticleModel(ConnectionPool cp, Quartet<ArticleObject, Short, Byte, VIEW> infos) {
		this.a = new ArticleImpl(cp);
		this.items = new ArrayList<>();
		this.cates = new ArrayList<>();
		this.tags = new HashMap<>();
		this.paging = new Triplet<Integer, Short, Byte>(null, null, null);
		this.getArticleObjects(infos);
	}

	public ConnectionPool getCP() {
		return this.a.getCP();
	}

	public void releaseConnection() {
		this.a.releaseConnection();
	}
	
	
	//------------------------------------------------------------
	public ArrayList<ArrayList<ArticleObject>> getAllArticleObjects(){
		return this.items;
	}
	
	public ArrayList<Pair<CategoryObject, Integer>> getCates_Totals(){
		return this.cates;
	}
	
	public HashMap<String, Integer> getTags_Counts(){
		return this.tags;
	}
	
	public Triplet<Integer, Short, Byte> getPagingInfos(){
		return this.paging;
	}
	
	public Optional<ArticleObject> getArticleObject() {
		return Optional.ofNullable(this.item);
	}
	
	//------------------------------------------------------------

	private void getArticleObject(ResultSet rs) {

		if (rs != null) {
			try {
				if (rs.next()) {
					item = new ArticleObject();
					item.setArticle_id(rs.getInt("article_id"));
					item.setArticle_title(rs.getString("article_title"));
					item.setArticle_summary(rs.getString("article_summary"));
					item.setArticle_content(rs.getString("article_content"));
					item.setArticle_created_date(rs.getString("article_created_date"));
					item.setArticle_visited(rs.getShort("article_visited"));
					item.setArticle_author_name(rs.getString("article_author_name"));
					item.setArticle_section_id(rs.getShort("article_section_id"));
					item.setArticle_category_id(rs.getShort("article_category_id"));
					item.setArticle_last_modified(rs.getString("article_last_modified"));
					item.setArticle_image(rs.getString("article_image"));

					item.setCategory_id(rs.getShort("category_id"));
					item.setCategory_name(rs.getString("category_name"));

					item.setSection_id(rs.getShort("section_id"));
					item.setSection_name(rs.getString("section_name"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private ArrayList<ArticleObject> toArrayList(ResultSet rs){
		
		ArrayList<ArticleObject> items = new ArrayList<ArticleObject>();
		ArticleObject item = null;
		
		if (rs!=null) {
			try {
				while (rs.next()) {
					item = new ArticleObject();
					item.setArticle_id(rs.getInt("article_id"));
					item.setArticle_title(rs.getString("article_title"));
					item.setArticle_summary(rs.getString("article_summary"));
					//item.setArticle_content(rs.getString("article_content"));
					item.setArticle_created_date(rs.getString("article_created_date"));
					item.setArticle_visited(rs.getShort("article_visited"));
					item.setArticle_author_name(rs.getString("article_author_name"));
					item.setArticle_section_id(rs.getShort("article_section_id"));
					item.setArticle_category_id(rs.getShort("article_category_id"));
					item.setArticle_last_modified(rs.getString("article_last_modified"));
					item.setArticle_image(rs.getString("article_image"));

					item.setCategory_id(rs.getShort("category_id"));
					item.setCategory_name(rs.getString("category_name"));

					item.setSection_id(rs.getShort("section_id"));
					item.setSection_name(rs.getString("section_name"));
					
					items.add(item);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return items;
	}

	private void getArticleObjects(
			Quartet<ArticleObject, Short, Byte, VIEW> infos) {
		
		ArticleObject similar = infos.getValue0();

		// Danh sách bài viết mới
		ArrayList<ArticleObject> newest_item = new ArrayList<>();

		// Danh sách bài viết theo xu hướng
		ArrayList<ArticleObject> trend_item = new ArrayList<>();
		
		// Danh sách bài viết theo hình ảnh
		ArrayList<ArticleObject> image_item = new ArrayList<>();
		
		// Danh sách bài viết theo dịch vụ
		ArrayList<ArticleObject> service_item = new ArrayList<>();
		
		//Danh sách bài viết mới theo chuyên mục
		ArrayList<ArticleObject> news_item = new ArrayList<>();
		
		
		VIEW v = infos.getValue3();

		ArticleObject item = null;

		ArrayList<ResultSet> res = this.a.getArticle(infos);

		if (res != null && res.size() > 0) {
			
			//Danh sách bài viết mới
			ResultSet rs = res.get(0);
			newest_item = this.toArrayList(rs);
			
			
			//Danh sách bài viết xem nhiều (xu hướng)
			rs = res.get(1);
			if (rs!=null) {
				try {
					while (rs.next()) {
						item = new ArticleObject();
						item.setArticle_id(rs.getInt("article_id"));
						item.setArticle_title(rs.getString("article_title"));
						item.setCategory_name(rs.getString("category_name"));
						item.setArticle_created_date(rs.getString("article_created_date"));
						item.setArticle_author_name(rs.getString("article_author_name"));
						
						trend_item.add(item);
					}
					
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			
			
			
			if (v==VIEW.HOMEPAGE) {
				rs = res.get(2);//Hình ảnh dự án
				image_item = this.toArrayList(rs);
				
				
				rs = res.get(3); //Dịch vụ
				service_item = this.toArrayList(rs);
			} else {
				rs = res.get(2);//Tin tức mới trong chuyên mục
				news_item = this.toArrayList(rs);
				
				
				rs = res.get(3); // Danh sách thể loại cùng số lượng bài viết
				if (rs!=null) {
					Pair<CategoryObject, Integer> cate_total = null;
					CategoryObject c = null;
					try {
						while (rs.next()) {
							c = new CategoryObject();
							c.setCategory_id(rs.getShort("category_id"));
							c.setCategory_name(rs.getString("category_name"));
							cate_total = new Pair<>(c, rs.getInt("total"));
							
							this.cates.add(cate_total);
						}
						
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
				rs = res.get(4);
				if (rs!=null) {
					try {
						
						StringBuilder allTags = new StringBuilder("");
						
						while (rs.next()) {
							allTags.append(rs.getString("article_tag"));
						}
						
						rs.close();
						
						
						this.getStatisticTags(allTags.toString());
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				
				//Tổng số bài viết để phân trang hoặc bài viết để xem chi tiết
				rs = res.get(5);
				if (similar.getArticle_id()>0) {
					this.getArticleObject(rs);
				}
				else {
					int total = 0;
					if (rs!=null) {
						try {
							if (rs.next()) {
								total = rs.getInt("total");
							}
							rs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					this.paging = new Triplet<Integer, Short, Byte>(total, infos.getValue1(), infos.getValue2());
				}
				
			}
		}
		
		
		
		
		
		this.items.add(newest_item);
		this.items.add(trend_item);
		if (v==VIEW.HOMEPAGE) {
			this.items.add(image_item);
			this.items.add(service_item);
		} else {
			this.items.add(news_item);
		}
		
	}
	
	
	
	
	
	private void getStatisticTags(String allTags){
		if (!allTags.equalsIgnoreCase("")) {
			allTags = allTags.toLowerCase();
			
			while (allTags.indexOf("  ")!=-1) {
				allTags = allTags.replace("  ", " ");
			}
			
			String[] tags = allTags.split(",");
			
			for (String tag:tags) {
				if (!tag.equalsIgnoreCase("")) {
					tag = tag.trim();
					if (this.tags.containsKey(tag)) {
						this.tags.replace(tag, this.tags.get(tag)+1);
					}
					else {
						this.tags.put(tag, 1);
					}
				}
			}
			
			
			//Lọc theo số lượng
			this.tags.keySet().removeAll(
					this.tags.entrySet()
					.stream()
					.filter(a -> a.getValue().intValue()<3)
					.map(e -> e.getKey())
					.collect(Collectors.toList())
			);
				
			
		}
	}
	
	
	
}
