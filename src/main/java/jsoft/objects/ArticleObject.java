package jsoft.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ArticleObject extends CategoryObject{
	private int article_id;
	private String article_title;
	private String article_summary;
	private String article_content;
	private String article_created_date;
	private String article_last_modified;
	private String article_image;
	private short article_category_id;
	private short article_section_id;
	private short article_visited;
	private String article_author_name;
	private boolean article_enable;
	private String article_url_link;
	private String article_tag;
	private String article_title_en;
	private String article_summary_en;
	private String article_content_en;
	private String article_tag_en;
	private int article_fee;
	private boolean article_isfee;
	private boolean article_delete;
	private String article_deleted_date;
	private String article_restored_date;
	private String article_modified_author_name;
	private String article_author_permission;
	private String article_source;
	private byte article_language;
	private boolean article_focus;
	private byte article_type;
	private boolean article_forhome;
}
