package com.dogdailylog.youtube;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/youtube")
@ApiIgnore
public class YoutubeController {
	
	@GetMapping("/search_view")
	public String youtubeApiView(Model model) throws IOException {

		model.addAttribute("view", "youtube/trainingVideo");
		
		return "template/layout";
	}
}
