<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container" style="padding-top:200px">

	<h1>훈련 영상을 참고해봐요!</h1>
	<div class="d-flex flex-column mt-5">
		<h4 class="text-center font-weight-bold mb-3">"훈련 keyword"</h4>
		<div class="d-flex justify-content-center">
			<button class="btn btn-secondary keywordBtn" style="width:100px;">사회화훈련</button>
			<button class="btn btn-secondary keywordBtn" style="width:100px;">기본훈련</button>
			<button class="btn btn-secondary keywordBtn" style="width:100px;">배변훈련</button>
			<button class="btn btn-secondary keywordBtn" style="width:100px;">개인기훈련</button>
			<button class="btn btn-secondary keywordBtn" style="width:100px;">강형욱훈련</button>
		</div>
		<div id="videoResultContainer" class="d-flex mt-5">
	
		</div>
	</div>
	<script>
		$(document).ready(function() {

				$('.keywordBtn').on('click', function() {
					
					let keyword = "반려견" + $(this).html();
					
					$.ajax({
						type : "get",
						dataType: "json",
						url : "/youtube/search",
						data : {"keyword" : keyword},
						success : function(data) {
							$('#videoResultContainer').empty();
						 	data.searchResult.items.forEach(function(element, index) {
			                    $('#videoResultContainer').append('<div class="mr-3"><iframe width="580" height="325" src="https://www.youtube.com/embed/'+element.id.videoId+'" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>');
			                });
						}
					});
					
				});
				
				$.ajax({
					type : "get",
					dataType: "json",
					url : "/youtube/search",
					/* data : {"search" : search}, */
					success : function(data) {
					 	data.searchResult.items.forEach(function(element, index) {
		                    $('#videoResultContainer').append('<div class="mr-3"><iframe width="580" height="325" src="https://www.youtube.com/embed/'+element.id.videoId+'" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>');
		                });
					}
				});
			/* }); */
		});
	</script>
</div>