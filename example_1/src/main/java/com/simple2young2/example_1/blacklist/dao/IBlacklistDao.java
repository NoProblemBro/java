package com.simple2young2.example_1.blacklist.dao;

import java.util.Collection;

public interface IBlacklistDao {
	
	Collection<String> findByIps(Collection<String> ips);

}
