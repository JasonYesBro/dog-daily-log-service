<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<title>Insert title here</title>

<div class="container" style="padding-top:200px;">
	<div class="d-flex flex-wrap justify-content-start bg-secondary">
        <!-- hotel item 목록 -->
        <c:forEach items="${hotelList}" var="hotel">
        	
        <div class="hotel-box" style="width:33.3%; height:300px;">
            <div class="">
                <img src="" alt="" width="200">
            </div>
            <a href="/hotel/detail_view?id=${hotel.id}">
	            <div class="d-flex flex-column" class="hotel-content-box">
	                <span class="name">호텔 이름 : ${hotel.preSchoolName}</span>
	                <span class="address">호텔 위치 : ${hotel.location}</span>
	            </div>
            </a>
        </div>
        </c:forEach>
    </div>
</div>
