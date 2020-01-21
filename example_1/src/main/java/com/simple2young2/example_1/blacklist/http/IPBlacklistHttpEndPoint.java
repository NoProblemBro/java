package com.simple2young2.example_1.blacklist.http;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.simple2young2.example_1.IPHelper;
import com.simple2young2.example_1.blacklist.domain.Blacklist;

@HttpEndpoint
public class IPBlacklistHttpEndPoint {
	
	@Autowired
	private Blacklist blacklist;
	
	@GetMapping("/ipBlacklist")
	public Collection<String> countIp(HttpServletRequest request) {
		String serviceId = request.getHeader("X-Zuul-ServiceId");
		return blacklist.findIpsInBlacklist(IPHelper.parserHttpIpHeaders(request),serviceId);
	}

}
