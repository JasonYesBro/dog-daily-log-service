package com.dogdailylog.post.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dogdailylog.post.model.Post;

@Repository
public interface PostMapper {
	public List<Post> selectPostList();
}
