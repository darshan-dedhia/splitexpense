
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.darshandedhia.info6250.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author bhaVYa
 */
@Component
@WebFilter("/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hrq = (HttpServletRequest) request;
        HttpServletResponse hrs = (HttpServletResponse) response;
        hrs.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        hrs.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        hrs.setHeader("Access-Control-Allow-Headers", "*");
        hrs.setHeader("Access-Control-Allow-Credentials", "false");
        hrs.setHeader("Access-Control-Max-Age", "4800");
        
        chain.doFilter(request, response);
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		return;
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		return;
	}

}

