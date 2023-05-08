package com.dogdailylog.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogdailylog.post.dao.PostMapper;
import com.dogdailylog.post.model.Post;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	public List<Post> getPostList() {
		return postMapper.selectPostList();
	}
}
