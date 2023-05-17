<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<header>
	<div id="navWrapper">
		<div id="navContainer">
			<img src="/static/img/menu-bar.png" alt="메뉴바이미지" id="navImg" width="30" height="30">
		</div>
		<div id="liContainer">
			<a href="/training/my_page_view">
				일지 작성하러가기
			</a>
			<a href="/training/calendar_view">
				훈련일지 보기
			</a> 
			<a href="">
				유기견을 보살펴주세요!
			</a> 
			<a href="">
				훈련 꿀팁 보러가기
			</a> 
			<a href="">
				훈련 꿀팁 작성하기
			</a>
		</div>
	</div>
	<div id="navBackground"></div>
	<div id="headerWrapper">
		<div id="headerContainer" class="d-flex align-items-center">
			<div id="title">
				<p><a href="/main">우리집 강형욱</a></p>
			</div>
			<div id="subTitle" class="d-flex align-items-end">
				<p>반려견 훈련일지를 작성해보세요!</p>
			</div>
		</div>
		<div>
			<div class="d-flex justify-content-center align-items-center">
				<hr>
				<div id="signStatus" class="d-flex justify-content-around ml-2">
				<c:if test="${ empty userId }">
					<!-- session 유무 -->
					<span><a href="/user/sign_in_view">로그인</a></span>
                    <span><a href="/user/sign_up_view">회원가입</a></span>
				</c:if>
				<c:if test="${ not empty userId }">
					<!-- 프로필 이미지 나 닉네임 클릭 시 mypage로 이동 -->
					<span><a href="/training/my_page_view">${userName}</a></span>
					<span><a href="">${puppyName}</a></span>
					<span><a href="/user/sign_out">로그아웃</a></span>	
				</c:if>
				</div>
			</div>
		</div>
	</div>
	<script>
		
		$(document).ready(function() {
			$("#navImg").on("click", function(e) {
			
				if($("#liContainer").css("display") == "none" && $("#navBackground").css("display") == "none") {
					$("#liContainer").show(200);
					// display none 과 flex 가 한번에 적용이 안되는 현상으로 인해 이벤트가 발생할 시에 css 추가
				    $("#liContainer").css({
				    	"display":"flex", 
				    	"flex-direction": "column",
				    	"justify-content": "space-between"
				    });
					$("#navBackground").show(300);
				} else {
					$("#liContainer").hide(200);
					$("#navBackground").hide(300);
				}
			});
			
		})
	</script>
</header>