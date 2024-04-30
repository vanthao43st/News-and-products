<%@page import="jsoft.library.Utilities"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jsoft.*, jsoft.objects.*" %>
<%@ page import="jsoft.gui.article.*, org.javatuples.*" %>

<%

	request.setCharacterEncoding("UTF-8");
	//Xác định trang chủ và trang con
	String home = "/home/";
	String uri = request.getRequestURI().substring(home.length());
	int at = uri.indexOf("/");
	
	
	





	//Tìm bộ quản lý kết nối
	ConnectionPool cp = (ConnectionPool)application.getAttribute("CPool");

	

	//Đối tượng lưu thông tin điều kiện
	ArticleObject similar = new ArticleObject();
	
	
	
	if (at!=-1){
		String pos = uri.substring(0, at);
		
		short cate = Utilities.getShortParam(request, "c");
		
		switch(pos){
		case "tin-tuc":
			similar.setArticle_section_id((short)2);
			similar.setArticle_category_id(cate);
			
			short p = Utilities.getPageParam(request);
			
			//Lấy id bài viết xem chi tiết nếu có
			int id = Utilities.getIntParam(request, "id");
			if (id>0){
				similar.setArticle_id(id);
			}
			
			String key = request.getParameter("key");
			String saveKey = (key!=null && !key.equalsIgnoreCase("")) ? key.trim() : "";
			similar.setArticle_title(saveKey);
			
			Quartet<ArticleObject, Short, Byte, VIEW> infos = 
					new Quartet<>(similar, (short)p, (byte)10, VIEW.INNERPAGE);
			
			ArticleControl ac = new ArticleControl(cp, infos);
			
			if (cp==null){
				application.setAttribute("CPool", ac.getCP());
			}
			
			
			
			String news = ac.viewInnerPage(similar); 
			
			
			//Trả lại kết nối
			ac.releaseConnection();
			
			session.setAttribute("news", news);
			break;
		case "dich-vu":
			break;
		}
	} else {
		similar.setArticle_section_id((short)2);
		
		Quartet<ArticleObject, Short, Byte, VIEW> infos = 
				new Quartet<>(similar, (short)1, (byte)5, VIEW.HOMEPAGE);
		
		ArticleControl ac = new ArticleControl(cp, infos);
		
		if (cp==null){
			application.setAttribute("CPool", ac.getCP());
		}
		
		ArrayList<String> homepages = ac.viewHomePage();
		//Lấy cấu trúc trình bày HomePage
		
		//Trả lại kết nối
		ac.releaseConnection();
		
		String grid = homepages.get(0);
		session.setAttribute("grid", grid);
		
		//Lấy cấu trúc image
		String images = homepages.get(1);
		session.setAttribute("images", images);
		
		//Lấy cấu trúc service
		String services = homepages.get(2);
		session.setAttribute("services", services);
	}
	

	
%>