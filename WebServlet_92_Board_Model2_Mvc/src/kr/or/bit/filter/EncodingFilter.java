package kr.or.bit.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;


@WebFilter(
		description = "어노테이션을 활용한 필터 적용하기", 
		urlPatterns = { "/*" }, //모든 요청에 필터를 걸어라!
		initParams = { 
				@WebInitParam(name = "encoding", value = "UTF-8", description = "모든페이지 한글처리")
		})
public class EncodingFilter implements Filter {
	private String encoding;

    public EncodingFilter() {

    }

    public void init(FilterConfig fConfig) throws ServletException {
    	this.encoding = fConfig.getInitParameter("encoding");
    	System.out.println("Filter init : " + this.encoding);
	}
    
	public void destroy() {

	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//요청할 때
		if(request.getCharacterEncoding() == null) { //페이지에서 인코딩방식을 정의하지 않으면
			request.setCharacterEncoding(this.encoding); //여기서 인코딩방식을 정의해주겠다.
			System.out.println("encoding : " + this.encoding);
		}
		chain.doFilter(request, response);
		//응답할때
	}


	

}
