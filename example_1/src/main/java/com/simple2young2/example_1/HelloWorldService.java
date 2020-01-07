package com.simple2young2.example_1;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HelloWorldService {
	
	private static final boolean[] BUSY_NOW = {
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false,
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false,
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false,
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false,
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false,
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false,
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false,
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false,
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false,
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false,
			false,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false
	};
	
	private static final int LEN = BUSY_NOW.length;
	
	@HystrixCommand(fallbackMethod = "writeDownBugs")
	public String fix() {
		int index = (int)Math.round(Math.random() * (LEN - 1));
		if(BUSY_NOW[index]) {
			throw new RuntimeException("i am too busy to fix the bugs");
		}else {
			String words = "i am coding now";
			System.out.println(words);
			return words;
		}
		
	}
	
	public String writeDownBugs() {
		String words = "i have writen down bugs. i will fix the bugs when i am free";
		System.out.println(words);
		return words;
	}

}
