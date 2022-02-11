package kr.letech.study.interceptor;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		SecurityContextImpl security = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if (security != null) {
			Collection<? extends GrantedAuthority> authorities = security.getAuthentication().getAuthorities();

			String requestURI = request.getRequestURI();

			if (authorities != null && requestURI.contains("admin")) {
				if (!authorities.contains("ADMIN")) {
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().print("<script>alert('권한이 존재하지 않습니다');\n history.go(-1);</script>");
					;
					return false;
				}
			}
		}

		return true;
	}
}
