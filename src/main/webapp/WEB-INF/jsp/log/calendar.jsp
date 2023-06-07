<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.dogdailylog.training.model.TrainingLog"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
/* 캘린더 위의 해더 스타일(날짜가 있는 부분) */
.fc-header-toolbar {
padding-top: 1em;
padding-left: 1em;
padding-right: 1em;
}
</style>

<body>
    <!-- calendar 태그 -->
    <div id='calendar-container' style="padding-top:150px;" class="container">
        <div id='calendar'></div>
    </div>
    <script>
       	
        $(document).ready(function(){
        	var date = new Date();
        	
            // calendar element 취득
            var calendarEl = $('#calendar')[0];

            // full-calendar 생성하기
            var calendar = new FullCalendar.Calendar(calendarEl, {
                height: '700px', // calendar 높이 설정
                expandRows: true, // 화면에 맞게 높이 재설정
                slotMinTime: '08:00', // Day 캘린더에서 시작 시간
                slotMaxTime: '20:00', // Day 캘린더에서 종료 시간

                // 해더에 표시할 툴바
                headerToolbar: {
                    left: 'prev,next',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
            },

            initialView: 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
            //initialDate: '2021-07-15', // 초기 날짜 설정 (설정하지 않으면 오늘 날짜가 보인다.)
            navLinks: true, // 날짜를 선택하면 Day 캘린더나 Week 캘린더로 링크
            editable: false, // 수정 가능?
            selectable: false, // 달력 일자 드래그 설정가능
            nowIndicator: true, // 현재 시간 마크
            dayMaxEvents: true, // 이벤트가 오버되면 높이 제한 (+ 몇 개식으로 표현)
            locale: 'ko', // 한국어 설정

            // 캘린더에서도 바로 훈련일지를 작성할 수 있게?
            eventAdd: function(obj) { // 이벤트가 추가되면 발생하는 이벤트
                //console.log(obj);
            },
            eventChange: function(obj) { // 이벤트가 수정되면 발생하는 이벤트
                //console.log(obj);
            },
            eventRemove: function(obj){ // 이벤트가 삭제되면 발생하는 이벤트
                //console.log(obj);
            },
            
            // 이벤트
            events: [
            	
            	<% List<TrainingLog> trainingLogList = (List<TrainingLog>) request.getAttribute("trainingLogList");%>
            	<% for (TrainingLog log : trainingLogList) {%>
		                <fmt:formatDate var="createdDate" value="<%= log.getCreatedAt() %>" pattern="yyyy-MM-dd"/>
	                {
	                	id: '<%= log.getId() %>',
		                title: '<%= log.getTitle() %>',
		                start: "${createdDate}"
	                },
                	
                <% }%>
                
            ]
            });
            // 캘린더 랜더링
            calendar.render();
            
            /* $(document).on('click', '.fc-daygrid-event', function() {
            	console.log($(this).html());
            }); */

            
        });
    </script>
</body>