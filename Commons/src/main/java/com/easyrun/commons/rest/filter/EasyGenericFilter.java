package com.easyrun.commons.rest.filter;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public abstract class EasyGenericFilter extends GenericFilterBean {
	public abstract String[] getIgnoredUrls();
	public abstract String[] getMatchUrls();
	
	public boolean isIgnoredUrl(String url) {
		return isMatched(url, getIgnoredUrls());
	}
	
	private boolean isMatched(String url, String[] urls) {
		boolean band = false;
		for (String ignored : urls) {
			if (ignored.endsWith("**")) {
				ignored = ignored.substring(0, ignored.length() - 2);
				if (ignored.startsWith("/")) {
					ignored = ignored.substring(1);
				}
				if (!ignored.endsWith("/")) {
					ignored += "/";
				}
				if (!url.endsWith("/")) {
					url += "/";
				}
				Pattern pattern = Pattern.compile("/" + ignored + "+(.*)[/]*");
				Matcher m = pattern.matcher(url);
				band |= m.find();

			} else {
				band |= url.endsWith(ignored);
			}
		}
		return band;
	}
	
	public boolean isMatchedUrl(String url) {
		return isMatched(url, this.getMatchUrls());
	}
	
	public void getMappedUrls(ServletContext servletContext) {
		WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext, HandlerMapping.class, true, false);
		for (HandlerMapping handlerMapping : allRequestMappings.values()) {
		    if (handlerMapping instanceof RequestMappingHandlerMapping) {
		          RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
		          Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
		          for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
		             RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
		             PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
		             final Set<String> patterns = patternsCondition.getPatterns(); 
		             String requestUrl = patterns.stream().findFirst().orElse("");
		             System.out.println(requestUrl);
		          }
		    }
		}
	}
}
