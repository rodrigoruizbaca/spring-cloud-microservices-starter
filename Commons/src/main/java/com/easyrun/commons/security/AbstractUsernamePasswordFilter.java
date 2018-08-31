package com.easyrun.commons.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.filter.GenericFilterBean;

public abstract class AbstractUsernamePasswordFilter extends GenericFilterBean {
	public abstract String[] getIgnoredUrls();

	public boolean isIgnored(String url) {
		boolean band = false;
		for (String ignored : getIgnoredUrls()) {
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
}
