package com.simple2young2.framework.zuul.server.blacklist;


import java.util.Set;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;


public class IPBlacklistFilter extends ZuulFilter {
	
	private IIPBlacklist blacklist;

	public IPBlacklistFilter(IIPBlacklist blacklist) {
		super();
		this.blacklist = blacklist;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		Set<String> ipsInBlacklist = blacklist.findBlacklist();
		if(ipsInBlacklist.size() > 0) {
			// TODO 这里需要打印详细的日志，如链路的UUID，access token 等等，需要保证能精确定位到什么时间——>什么操作——>那个用户——>请求IP——>请求头dump数据
			throw new ZuulException(String.format("发现黑名单，拒绝访问，ip:%s",ipsInBlacklist), HttpStatus.FORBIDDEN.value(), "access denied");
		}
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
	}

	
}
