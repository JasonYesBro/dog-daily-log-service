<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach items="${trainingLogList}" var="trainingLog">
	<div class="type-log-box" data-log-id="${trainingLog.id}">
		<div class="log-img-box"><img src="${trainingLog.imagePath }" class="log-img" alt="훈련일지 이미지"></div>
		<div class="d-flex flex-column font-weight-bold" style="height:20%;">
			<div><span>훈련제목 : </span><span>${trainingLog.title}</span></div>
			<fmt:formatDate var="createdAt" value="${trainingLog.createdAt}" pattern="yyyy-MM-dd" />
			<div><span>일지작성날짜 : </span><span>${createdAt}</span></div>
		</div>
	</div>
</c:forEach>