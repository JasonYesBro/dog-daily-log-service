<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header>
	<div id="navWrapper">
		<div id="navContainer">
			<img src="/static/img/menu-bar.png" alt="메뉴바이미지" id="navImg" width="30" height="30">
		</div>
		<div id="liContainer" class="">
			<a href="">
				<li>일지 작성하러가기</li>
			</a>
			<a href="">
				<li>훈련일지 보기</li>
			</a> 
			<a href="">
				<li>유기견을 보살펴주세요!</li>
			</a> 
			<a href="">
				<li>훈련 꿀팁 보러가기</li>
			</a> 
			<a href="">
				<li>훈련 꿀팁 작성하기</li>
			</a>
		</div>
	</div>
	<div id="navBackground"></div>
	<div id="headerWrapper">
		<div id="headerContainer" class="d-flex align-items-center">
			<div id="title">
				<p>우리집 강형욱</p>
			</div>
			<div id="subTitle" class="d-flex align-items-end">
				<p>반려견 훈련일지를 작성해보세요!</p>
			</div>
		</div>
		<div>
			<div class="d-flex justify-content-center align-items-center">
				<hr>
				<div id="signStatus" class="d-flex justify-content-between ml-2">
					<!-- session 유무 -->
					<!-- <span><a href="">로그인</a></span>
                            <span><a href="">회원가입</a></span> -->
					<span><a href="">회원 이름</a></span> <span><a href="">강아지이름</a></span>
				</div>
			</div>
		</div>
	</div>
	<script>
		
		$(document).ready(function() {
			$("#navImg").on("click", function(e) {
				if($("#liContainer").css("display") == "none" && $("#navBackground").css("display") == "none") {
					$("#liContainer").show(200);
					$("#navBackground").show(300);
				} else {
					$("#liContainer").hide(200);
					$("#navBackground").hide(300);
				}
			});
		})
	</script>
</header>