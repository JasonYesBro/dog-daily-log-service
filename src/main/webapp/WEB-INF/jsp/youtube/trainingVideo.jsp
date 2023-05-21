<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container" style="padding-top:100px">

	<h1>훈련 영상을 참고해봐요!</h1>
	<input type="text" id="youtubeSearch" />
	<button type="button" id="searchBtn">제출</button>
	
	<div id="videoResultContainer" class="d-flex">

	</div>
	<script>
		$(document).ready(function() {
			
			$('#searchBtn').on('click', function() {
				let search = $('#youtubeSearch').val();
				
				$.ajax({
					type : "get",
					dataType: "json",
					url : "/youtube/search",
					data : {"search" : search},
					success : function(data) {
					 	data.searchResult.items.forEach(function(element, index) {
		                    $('#videoResultContainer').append('<div><iframe width="560" height="315" src="https://www.youtube.com/embed/'+element.id.videoId+'" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>');
		                });
					}
				});
			});
		});
	</script>
</div>