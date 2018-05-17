package cn.marioquer.dockerplatform.utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "myFilter", urlPatterns = {"/page/server/*", "/page/swarm/*", "/page/index/*"})
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            // session存在
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            // session不存在 准备跳转失败
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/page/login");
        }
    }

    @Override
    public void destroy() {
        System.out.println("过滤器摧毁");
    }
}