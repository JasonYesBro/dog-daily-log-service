package com.dogdailylog.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Youtube {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String search(String search) throws IOException {
		
		String apiurl = "https://www.googleapis.com/youtube/v3/search";
		apiurl += "?key=AIzaSyAl_Pd_tt-bsWhqukpBncgQNw51UtnleUA";
		apiurl += "&part=snippet&type=video&maxResults=10&videoEmbeddable=true";
		apiurl += "&q="+URLEncoder.encode(search,"UTF-8");
		
		URL url = new URL(apiurl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();
		
		logger.info("$$$$$$$$$$$$$  youtube $$$$$$$$$$$$$$ : {}", response.toString());
		
		return response.toString();
	}
}