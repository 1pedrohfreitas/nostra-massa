package dev.pedrohfreitas.genericstore.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import dev.pedrohfreitas.genericstore.configs.TenantLocalStorage;
import dev.pedrohfreitas.genericstore.configs.TerminalLocalStorage;
import dev.pedrohfreitas.genericstore.configs.UserLocalStorage;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RequestFilter implements Filter {

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,final FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String tenantID = httpServletRequest.getHeader("tenantID");
		String userID = httpServletRequest.getHeader("userID");
		String terminalID = httpServletRequest.getHeader("terminalID");
		TenantLocalStorage.setTenantID(tenantID);
		UserLocalStorage.setUserID(userID);
		TerminalLocalStorage.setTerminalID(terminalID);
		
		chain.doFilter(httpServletRequest, servletResponse);
		
	}
}
