package com.simple2young2.example_1.blacklist.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author Leon.Zhong
 */
@Dao("example2BlacklistDao")
@ConfigurationProperties(prefix = "ip-blacklist.example-2", ignoreUnknownFields = true)
public class Example2BlacklistDao extends AbstractBlacklistDao {
	
	private List<String> ip;

	public List<String> getIp() {
		return ip;
	}

	public void setIp(List<String> ip) {
		this.ip = ip;
	}

	@Override
	protected Collection<String> getIpsInBlacklist() {
		if(this.ip == null) {
			return new ArrayList<String>();
		}
		return this.ip;
	}
}
