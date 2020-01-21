package com.simple2young2.example_1.blacklist.domain;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

@DomainFactory
public class BlacklistFactory {
	
	@Bean
	@ConditionalOnBean(IBlacklistRepository.class)
	public Blacklist build(IBlacklistRepository blacklistRepository) {
		return new Blacklist(blacklistRepository);
	}

}
