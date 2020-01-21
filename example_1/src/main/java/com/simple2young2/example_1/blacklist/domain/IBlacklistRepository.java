package com.simple2young2.example_1.blacklist.domain;

import java.util.Collection;
import java.util.Set;

/**
 * 
 * @author Leon.Zhong
 */
public interface IBlacklistRepository {
	
	Collection<String> findInGlobalBlacklist(Set<String> ips);

	Collection<String> findInOtherBlacklist(Set<String> ips, String serviceId);

}
