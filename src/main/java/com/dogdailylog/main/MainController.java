package com.dogdailylog.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/main")
	public String mainView(Model model) {
		model.addAttribute("title", "훈련일지를 작성해보세요!");
		model.addAttribute("view", "include/main");
		return "template/layout";
	}
}
