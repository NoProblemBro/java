package com.simple2young2.example_1.blacklist.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.simple2young2.example_1.blacklist.dao.IBlacklistDao;
import com.simple2young2.example_1.blacklist.domain.IBlacklistRepository;

/**
 * 
 * @author Leon.Zhong
 */
@Repository
public class BlacklistRepositoryImp implements IBlacklistRepository {
	
	@Autowired
	@Qualifier("globalBlacklistDao")
	private IBlacklistDao globalBlacklistDao;
	
	@Autowired
	@Qualifier("example1BlacklistDao")
	private IBlacklistDao example1BlacklistDao;
	
	@Autowired
	@Qualifier("example2BlacklistDao")
	private IBlacklistDao example2BlacklistDao;
	
	public Collection<String> findInGlobalBlacklist(Set<String> ips) {
		return globalBlacklistDao.findByIps(ips);
	}

	@Override
	public Collection<String> findInOtherBlacklist(Set<String> ips, String serviceId) {
		if("example-1".equalsIgnoreCase(serviceId)) {
			return example1BlacklistDao.findByIps(ips);
		}
		else if("example-2".equalsIgnoreCase(serviceId)) {
			return example2BlacklistDao.findByIps(ips);
		}
		return new HashSet<String>();
	}
}
