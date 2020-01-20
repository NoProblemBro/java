package com.simple2young2.framework.zuul.server.blacklist;

import java.util.Set;

public interface IIPBlacklist {
	
	Set<String> findBlacklist();

}
