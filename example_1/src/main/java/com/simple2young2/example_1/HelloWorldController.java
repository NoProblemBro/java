package com.simple2young2.example_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private HelloWorldService helloWorldService;
	
	@PostMapping("/bugs")
	public String fixBugs(String content) {
		return helloWorldService.fix();
	}

}
