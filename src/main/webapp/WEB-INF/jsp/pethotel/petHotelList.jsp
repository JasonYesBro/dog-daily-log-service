<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<title>Insert title here</title>

<div class="container" style="padding-top:200px;">
	<h3 class="text-center">인천광역시의 애견호텔리스트</h3>
	<span class="d-flex justify-content-end" style="font-weight:light; font-size:12px;">로고이미지는 애견호텔과 상관없는 이미지입니다.</span>
	<div class="d-flex flex-wrap justify-content-start">
        <!-- hotel item 목록 -->
        <c:forEach items="${hotelList}" var="hotel">
        	
        <div class="hotel-box p-2" style="width:33.3%; height:300px;">
            <a href="/hotel/detail_view?id=${hotel.id}">
	            <div class="width:100%; height:80%;">
	                <img src="/static/img/hotel5.png" alt="호텔로고" class="logo-img" style="object-fit:cover; width:100%; height:220px;">
	            </div>
	            <div class="d-flex flex-column" class="hotel-content-box">
	                <span class="name">호텔 이름 : ${hotel.preSchoolName}</span>
	                <span class="address">호텔 위치 : ${hotel.location}</span>
	            </div>
            </a>
        </div>
        </c:forEach>
    </div>
   	<script>
   		$(document).ready(function() {
   			$('.logo-img').each(function(){
   				let logImgArr = ['/static/img/hotel1.png', '/static/img/hotel2.png', '/static/img/hotel3.png', '/static/img/hotel4.png', '/static/img/hotel5.png', '/static/img/hotel6.png'];
   				
   				let randomNum = Math.floor(Math.random() * 10) + 1;
   				let logImgSrc = '/static/img/hotel'+ randomNum +'.png';
   				$(this).attr('src', logImgSrc);
   			
   		  });
   		})
   	</script>
</div>
