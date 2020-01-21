package com.simple2young2.example_1;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public abstract class IPHelper {
	
	public static final String X_FORWARDED_FOR_HEADER = "X-Forwarded-For";
	
	public static final String[] IP_HEADERS = {X_FORWARDED_FOR_HEADER};
	
	public static Set<String> parserHttpIpHeaders(HttpServletRequest request){
		Set<String> ipSet = new HashSet<String>();
		for (int i = 0; i < IP_HEADERS.length; i++) {
			Set<String> ips = parserHttpHeaders(request,IP_HEADERS[i]);
			ipSet.addAll(ips);
		}
		return ipSet;
	}
	
	public static Set<String> parserHttpHeaders(HttpServletRequest request,String header){
		Enumeration<String> ipHeaders = request.getHeaders(header);
		Set<String> ipSet = new HashSet<String>();
		while(ipHeaders.hasMoreElements()) {
			String ip = ipHeaders.nextElement();
			if(StringUtils.isEmpty(ip) || (ip = ip.trim()).isEmpty()) {
				continue;
			}
			if(ip.indexOf(",") != -1) {
				ipSet.add(ip);
			}else {
				String[] ips = ip.split(",");
				for (int i = 0; i < ips.length; i++) {
					ip = ips[i];
					if(StringUtils.isEmpty(ip) || (ip = ip.trim()).isEmpty()) {
						continue;
					}
					ipSet.add(ip);
				}
			}
			
		}
		
		return ipSet;
	}

}
