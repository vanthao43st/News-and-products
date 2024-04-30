<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- ======= Post Grid Section ======= -->
    <section id="posts" class="posts">
      <div class="container" data-aos="fade-up">
        <div class="row g-5">
        
        	<%
        		String grid = (String)session.getAttribute("grid");
        		if (grid!=null){
        			out.print(grid);
        		}
        	%>

        </div> <!-- End .row -->
      </div>
    </section> <!-- End Post Grid Section -->