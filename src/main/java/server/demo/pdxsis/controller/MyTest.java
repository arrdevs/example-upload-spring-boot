package server.demo.pdxsis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyTest {

	@RequestMapping("/mytest")
	@ResponseBody
	public String test() {
		return "working boys!!";
	}
}
