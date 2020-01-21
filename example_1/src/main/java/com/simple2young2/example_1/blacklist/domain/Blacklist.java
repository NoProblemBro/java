package com.simple2young2.example_1.blacklist.domain;

import java.util.Collection;
import java.util.Set;

public class Blacklist {
	
	private Set<String> ipsSet;
	
	private IBlacklistRepository blacklistRepository;
	
	public Blacklist(IBlacklistRepository blacklistRepository) {
		this.blacklistRepository = blacklistRepository;
	}

	public Set<String> getIpsSet() {
		return ipsSet;
	}

	public void setIpsSet(Set<String> ipsSet) {
		this.ipsSet = ipsSet;
	}
	
	/**
	 * 在领域驱动中，虽然比较流行的是查询与职责分离。
	 * 但是在黑名单这个领域中，这个行为才是核心业务
	 * 所以虽然是查询，但是这个业务依然领域内的业务逻辑
	 * 
	 * 
	 * 
	 * @param ips
	 * @param serviceId
	 * @return
	 */
	public Collection<String> findIpsInBlacklist(Set<String> ips, String serviceId) {
		// 全局ip黑名单，意味着这个ip为恶意ip，攻击者名录
		Collection<String> ipsInBlacklist = blacklistRepository.findInGlobalBlacklist(ips);
		if(ipsInBlacklist != null && !ipsInBlacklist.isEmpty()) {
			// 理论上，如果在全局命中黑名单就不应该继续查询。当然这只是一个例子
			return ipsInBlacklist;
		}
		return blacklistRepository.findInOtherBlacklist(ips,serviceId);
		
	}
}
