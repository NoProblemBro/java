package com.simple2young2.framework.zuul.server.blacklist;

import java.util.Set;

import com.netflix.zuul.exception.ZuulException;

public interface IIPBlacklist {
	
	Set<String> findIpsInBlacklist() throws ZuulException ;

}
