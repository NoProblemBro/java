package com.simple2young2.framework.zuul.server.conf;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.netflix.ribbon.support.RibbonRequestCustomizer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple2young2.framework.zuul.server.blacklist.IPBlacklistFilter;
import com.simple2young2.framework.zuul.server.blacklist.RemoteIPBlacklist;


@EnableZuulProxy
@Configuration
public class ZuulConfiguration {
	
	@SuppressWarnings("rawtypes")
	@Autowired(required = false)
	private List<RibbonRequestCustomizer> requestCustomizers = Collections.emptyList();
	
	@Bean
	@ConditionalOnBean(BlacklistRemoteProperties.class)
	public RemoteIPBlacklist newRemoteIPBlacklist(
			ProxyRequestHelper helper, 
			RibbonCommandFactory<?> ribbonCommandFactory,
			BlacklistRemoteProperties properties,
			ObjectMapper objectMapper) {
		return new RemoteIPBlacklist(helper,ribbonCommandFactory,this.requestCustomizers,properties,objectMapper);
	}
	
	@Bean
	@ConditionalOnBean(RemoteIPBlacklist.class)
	public IPBlacklistFilter newIPBlacklistFilter(RemoteIPBlacklist blacklist) {
		return new IPBlacklistFilter(blacklist);
	}

}
