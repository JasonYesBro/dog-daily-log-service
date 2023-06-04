<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<div class="container" style="padding-top:200px;">
		<h4 class="text-center">애견호텔 예약목록</h4>
		<table class="table table-borderless text-center mt-3" id="bookingContainer">
			<thead>
				<tr>
		            <th scope="col">예약날짜</th>
		            <th scope="col">예약시간</th>
		            <th scope="col">결제일</th>
		            <th scope="col">가격</th>
		            <th scope="col">등원 상태</th>
		        </tr>
			</thead>
			<tbody>
			<c:forEach items="${bookingList}" var="booking">
				<tr>
					<td><fmt:formatDate type="date" value="${booking.pickUpDate}"/></td>
					<td>${booking.pickUpTime}</td>
					<td>${booking.bookedAt}</td>
					<td>${booking.price}</td>
					<c:if test="${booking.status == 0}">
					<td>준비중</td>
					</c:if>
					<c:if test="${booking.status == 1}">
					<td>등원중</td>
					</c:if>
				</tr>
			</c:forEach>
			</tbody>
		</table>
</div>