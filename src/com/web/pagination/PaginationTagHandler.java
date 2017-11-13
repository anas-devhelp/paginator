package com.web.pagination;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
public class PaginationTagHandler extends TagSupport{
	private int limit;
	private int page;
	private int total;
	private String ulClass;
	public PaginationTagHandler() {
		limit=10;
		page=1;
		ulClass="pagination pagination-large";
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setUlClass(String ulClass) {
		if(!(ulClass==null || ulClass.trim().length()==0)) {
			this.ulClass = ulClass;
		}
	}
	public String getPagesLink(int total, int limit, int page, String ulClass) {
		int last		= (int)Math.ceil(total / limit );
		int links=10;
		int end        = ( ( page + links ) < last ) ? page + links : last;
		String html       = "<ul class=\""+ulClass+"\">";
		String link_class      = ( page == 1 ) ? "disabled" : "";
		
		if(page==1) {
			html+= "<li class="+link_class+"><a href=\"javascript:void(0);\">&laquo;</a></li>";
		}else {
			html+= "<li class="+link_class+"><a href=\"?page="+(page - 1 )+"\""+link_class+">&laquo;</a></li>";
		}
		if (( page - links) >0 ) {
			html+= "<li><a href=\"?page=1\">1</a></li>";
			html+= "<li class=\"disabled\"><span>...</span></li>";
		}
		int i=page/links;
		System.out.println("i=1="+i);
		if(page>links && page%links>0) {
			i+=1;
		}
		System.out.println("i=2="+i);
		if(i==0) {
			i=1;
		}
		System.out.println("i=3="+i);
		int endIndex=i*links;
		i=(page/links)*links;
		
		/*if((page-links)>0 && page<(last-links)) {
			i=page;
		}else if((page-links)>0 && page>=(last-links)) {
			i=last-links;
		}else {
			i=1;
		}
		int endIndex=( page - links) >0?end:links;*/
		
		for (; i <= endIndex; i++ ) {
			link_class  = ( page == i ) ? "active" : "";
			html   += "<li class=\""+link_class+"\"><a href=\"?page="+i+"\">"+i+"</a></li>";
		}
		if(last-endIndex>1) {
			html   += "<li class=\"disabled\"><span>...</span></li>";
		}
		if ( end < last ) {
			html   += "<li><a href=\"?page="+last+"\">"+last+"</a></li>";
		}
		link_class      = ( page == last ) ? "disabled" : "";
		if(page==last) {
			html+= "<li class="+link_class+"><a href=\"javascript:void(0);\">&raquo;</a></li>";
		}else {
			html+= "<li class="+link_class+"><a href=\"?page="+(page + 1 )+"\""+link_class+">&raquo;</a></li>";
		}
		html+= "</ul>";
		return html;
	}
	public String getPagesLink( ) {
		int last		= (int)Math.ceil(this.total / this.limit );
		int links=10;
		int end        = ( ( this.page + links ) < last ) ? this.page + links : last;
		String html       = "<ul class=\""+this.ulClass+"\">";
		String link_class      = ( this.page == 1 ) ? "disabled" : "";
		
		if(this.page==1) {
			html+= "<li class="+link_class+"><a href=\"javascript:void(0);\">&laquo;</a></li>";
		}else {
			html+= "<li class="+link_class+"><a href=\"?page="+(this.page - 1 )+"\""+link_class+">&laquo;</a></li>";
		}
		if (( this.page - links) >0 ) {
			html+= "<li><a href=\"?page=1\">1</a></li>";
			html+= "<li class=\"disabled\"><span>...</span></li>";
		}
		int i=0;
		int endIndex=0;
		
		if(page<=links) {
			i=1;
			endIndex=links;
		}else {
			if(page%links==0) {
				i=page/links-1;
				endIndex=page;
			}else {
				i=page/links;
				endIndex=((page/links)+1)*links;
			}
			i=i*links+1;
		}
			
		/*if((last-page)<links && (last-links)>0)
			i=last-links+1;*/
		
		System.err.println("i="+i);
		System.err.println("endIndex="+endIndex);
		
			
		/*if(page<=links) {
			i=1;
			endIndex=links;
			System.out.println("i=1="+i);
			System.out.println("endIndex=1="+i);
		}else {
			if(page%links==0) {
				i=(page/links-1)*links+1;
				System.out.println("i=2="+i);
			}else {
				i=(page/links)*links+1;
				System.out.println("i=3="+i);
			}
			endIndex=i+links-1;
			System.out.println("endIndex=2="+i);
			
		}*/
		if(endIndex>last) {
			endIndex=last;
			System.out.println("endIndex=3="+i);
		}
		for (; i <= endIndex; i++ ) {
			link_class  = ( this.page == i ) ? "active" : "";
			html   += "<li class=\""+link_class+"\"><a href=\"?page="+i+"\">"+i+"</a></li>";
		}
		if(last-endIndex>1) {
			html   += "<li class=\"disabled\"><span>...</span></li>";
		}
		if ( endIndex < last ) {
			html   += "<li><a href=\"?page="+last+"\">"+last+"</a></li>";
		}
		link_class      = ( this.page == last ) ? "disabled" : "";
		if(this.page==last) {
			html+= "<li class="+link_class+"><a href=\"javascript:void(0);\">&raquo;</a></li>";
		}else {
			html+= "<li class="+link_class+"><a href=\"?page="+(this.page + 1 )+"\""+link_class+">&raquo;</a></li>";
		}
		html+= "</ul>";
		return html;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out=pageContext.getOut();
		try {
			out.print(getPagesLink());
			//pageContext.setAttribute("pagination", getPagesLink());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;//will not evaluate the body content of the tag  
	}
}
