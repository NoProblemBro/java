package com.simple2young2.example_1.blacklist.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractBlacklistDao implements IBlacklistDao {
	
	protected abstract Collection<String> getIpsInBlacklist();

	@Override
	public Collection<String> findByIps(Collection<String> ips) {
		Set<String> ipsInBlacklist = getIpsInBlacklist().stream().filter(t -> ips.contains(t)).collect(Collectors.toSet());
		if(ipsInBlacklist == null) {
			ipsInBlacklist = new HashSet<String>();
		}
		return ipsInBlacklist;
	}

}
