<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAl_Pd_tt-bsWhqukpBncgQNw51UtnleUA&callback=initMap&region=kr"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<div class="container" style="padding-top:200px">
	<!-- 애견 호텔 정보 -->
	<div id="petHotelInfo">
		<span id="address" class="d-none">${petHotel.location}</span>
	</div>
	<div>
		<img src="/static/img/schoolBus.png" id="busImg" alt="스쿨버스 이미지" width="100"/>
	</div>
	<div class="d-flex">
	
		<div class="d-flex flex-column mr-3">
			<div class="d-flex justify-content-center">
				<div class="mr-1">
					<div id="myMap" style="width:400px; height: 450px; border-radius:15px;"></div>
				</div>
				<div class="ml-1">
					<div id="map" style="width:400px; height: 450px; border-radius:15px;"></div>
				</div>
			</div>
			<div class="text-center">
				<span id="delayInfo" class="d-none text-danger" style="font-size:15px; font-weight:light;">위치를 찾는데 약 10초 정도가 소요됩니다.</span>
			</div>
			<div id="mapInfoContainer" class="d-flex justify-content-center text-center mt-3 mb-3" style="font-size:20px; font-weight:bold;">
				<div class="" id="loading"></div>
			</div>
		</div>
	
		<div class="d-flex flex-column justify-content-center align-items-center">
			<div class="d-flex flex-column align-items-center">
			<div class="d-flex align-items-center">
				<label for="distance" style="margin:0 auto;">거리</label>
				<input type="text" name="distance" class="form-control col-8 text-center" id="distance" style="background-color: #ffff;
    border: none;" readonly disabled>
			</div>
			<div class="d-flex mt-2 align-items-center">
				<label for="price" style="margin:0 auto;">가격</label>
				<input type="text" name="price" class="form-control col-8 text-center" id="price" style="background-color: #ffff;
    border: none;" readonly disabled>
			</div>
			</div>
			<div class="d-flex align-items-center mt-2">
				<label for="datepicker" style="margin:0 auto;">날짜선택</label>
				<input type="text" name="pickUpDate" class="adoptionDate form-control col-8 text-center" id="datepicker" placeholder="">
			</div>
			<table class="col-5 mt-3">
				<tbody class="d-flex justify-content-center">
					<tr id="pickUpTime" class="d-flex flex-wrap justify-content-start">
						<td>09:00</td>
						<td>10:00</td>
						<td>11:00</td>
						<td>12:00</td>
						<td>13:00</td>
						<td>14:00</td>
						<td>15:00</td>
						<td>16:00</td>
						<td>17:00</td>
						<td>18:00</td>
						<td>19:00</td>
						<td>20:00</td>
					</tr>
				</tbody>
			</table>
		</div>
	
	</div>
	
	<div class="text-center mt-3">
		<!-- modal로 띄워서 예 버튼 클릭시 book완료 -> 결제창 -->
		<button type="button" class="btn btn-primary" id="goMoalBtn" data-toggle="modal" data-target="#modal">예약하기</button>
	</div>
	
	<div class="modal fade" id="modal">
	    <!-- modal-dialog-centered 수직가운데 정렬 -->
	    <div class="modal-dialog modal-dialog-centered modal-lg">
	        <div class="modal-content text-center" id="typeModal">
	            <div class="modal-body d-flex justify-content-center">
	                <div class="d-flex flex-column align-items-start" style="font-weight:bold; font-size:20px; padding:15px; background-color:#fff; border-radius:10px;">
	                    <span id="bookNameModal">예약자 성함 : ${name}</span>
	                    <span id="bookDateModal">예약날짜 : </span>
	                    <span id="bookTimeModal">예약시간 : </span>
	                    <span id="bookHotelLocationModal">애견호텔 위치 : </span>
	                    <span id="bookPriceModal">가격 : </span>
	                </div>
	            </div>
	            <h4>입력하신 정보가 맞으면 결제를 진행해주세요.</h4>
	            <div class="modal-footer">
	                <!-- data-dismiss="modal" 모달닫기 -->
	                <div class="py-2 btn" data-dismiss="modal">
	                    <a href="#" class="text-danger">취소하기</a>
	                </div>
	                <!-- 훈련 타입 생성버튼으로 타입 생성 API 연결 -->
	                <button type="button" class="btn type-create-btn" id="reservationBtn" data-hotel-id="${petHotel.id}" >결제하기</button>
	            </div>
	        </div>
	    </div>
	</div>
	
	<script>
	
	// script 전역변수
	let time = "";
	let hotelLat = 0;
	let hotelLng = 0;
	$(document).ready(function() {
		
		$('#delayInfo').removeClass('d-none');
		
		// document가 ready되고 나서 실행해야 새로고침후에도 다시 불러옴
		// 내 현재위치 가져오기
		$(window).on('load',function() {
			window.navigator.geolocation.getCurrentPosition(success, error);//()를 붙여 자동호출하는것 x
		});
		
		let myLat = 0;
		let myLng = 0;
		
		function success(position) {
			$('#delayInfo').addClass('d-none');
			
			myLat = position.coords.latitude;
			myLng = position.coords.longitude;
			
			let here = {
				lat : position.coords.latitude,
				lng : position.coords.longitude
			};

			// 맵 생성
			let map = new google.maps.Map(document.getElementById('myMap'),
					{
						zoom : 19,
						center : here
					});

			// 마커 표시
			new google.maps.Marker({
				position : here,
				map : map,
				label : "현재위치"
			});
			
			// 현재위치가 나오는데 시간이 걸리므로 후에 실행하도록
			setTimeout(call(), 2000);
			
		}

		function call() {
			$('#mapInfoContainer').empty();
			
			$.ajax({
				url : '/map/distance',
				type : 'POST',
				data : {
					"originLat" : myLat,
					"originLng" : myLng,
					"destinationLat" : hotelLat,
					"destinationLng" : hotelLng,
				},
				success : function(data) {
					
					let distance = data.rows[0].elements[0].distance.text;
					$('#distance').val(distance);
					$('#price').val((distance.split('km')[0] * 1000).toLocaleString('ko-KR'));
					$('#loading').addClass('d-none');
					$('#mapInfoContainer').append('<span>현재위치로부터 </span><span style="color:blue;">${petHotel.preSchoolName}</span><span> 까지의 거리는 </span><span style="color:red;">약 ' + distance + '</span><span>입니다.</span>');
					
					// modal에 표시
					$('#bookHotelLocationModal').append('${petHotel.preSchoolName}');
					$('#bookPriceModal').append($('#price').val());
					
				}
			})
		};
		
		function error(err) {
			$('#locationResult').text("조회 실패 ==>" + err.code);
		}
		
		// input
		$.datepicker.setDefaults({
			dateFormat: 'yy-mm-dd',
			minDate : 0,
			maxDate: 10
		});
		
		$("#datepicker").datepicker();
        
		$('td').on('click', function() {

			$("td").removeClass("timePick");
			$(this).addClass("timePick");//그 요소에게 효과주고
			     	
			// 예약하기 버튼 누를 때 넘겨줄 값
			// input 타입을 버튼으로 처리하면 val() 로 꺼낼수 있음
			time = $(this).html();
			
			// modal
			$('#bookTimeModal').append(time);
		});
		
		$('#goMoalBtn').on('click', function() {
   			let date = $("#datepicker").val();
   			
   			// validation
   			if(!date) {
   				alert('날짜를 입력하세요.');
   				return false;
   			}
   			if(time == "") {
   				alert('예약시간을 입력하세요.');
   				return false;
   			}
   			
   			// modal
   			$('#bookDateModal').append(date);
		});
   		
		// 예약하기 버튼
   		$('#reservationBtn').on('click', function() {
   			let hotelId = $(this).data('hotel-id');
   			let date = $("#datepicker").val();
   			
   			// 거리행렬서비스를 통해 나온 가격을 input에 넣어줘야 함
   			/* let price = $("#pickUpPrice").val(); */
   			let price = 100; /* test용 */
   			
   			// ajax
   			$.ajax({
   				type : 'post',
   				url : '/payment/create',
   				data : {
   					'schoolId' : hotelId,
   					'pickUpDate' : date,
   					'pickUpTime' : time,
   					'price' : price
   				},
   				success : function(data) {
   					if(data.code == 1) {
	   					alert('예약되었습니다.');   
	   					/* proceedPay(); */
	   					requestPay(data);
   					} else {
   						alert('예약에 실패하였습니다.');
   					}
   				},
   				error : function(status, error, request) {
   					alert('관리자에게 문의바랍니다.');
   				}
   			});
   			
   		});
			
		function requestPay(data) {
			//var IMP = window.IMP; // 생략 가능
	    	IMP.init("imp55743063"); // 예: imp00000000
        	// IMP.request_pay(param, callback); /* 결제창 호출 */
	      	let bookingId = data.paymentView.bookingInfoOptional.id;
        	
			IMP.request_pay({ // param
					pg : "html5_inicis", // 결제대행사 설정
					pay_method : "card", // 결제방법 설정
					merchant_uid : data.paymentView.paymentInfoOptional.id, // 예약(db에서 불러옴) 고유번호
					name : data.paymentView.user.name,
					amount : 100, //data.paymentInfo.price // 현재 결제테스트 최소금액으로 설정해놓음
					buyer_email : data.paymentView.user.loginEmail,
					buyer_name : data.paymentView.user.name
				}, function(rsp) { // callback
					if (rsp.success) {
						// 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
						// jQuery로 HTTP 요청
						$.ajax({
							url : "/payment/verify/" + rsp.imp_uid,
							type : "POST",
							success : function(data) {
								// 위의 rsp.paid_amount 와 data.response.amount를 비교한후 로직 실행 (iamport 서버검증)
								console.log(rsp.paid_amount);
								console.log(data.response.amount);

								if (rsp.paid_amount == data.response.amount) {
									succeedPay(rsp.imp_uid, rsp.merchant_uid);
								} else {
									alert("결제 검증 실패");
									
									// 결제 검증 실패 시 삭제
									$.ajax({
										type : "delete",
										url : "/payment/delete",
										data : {
											"bookingId"	: bookingId
										},
										success : function(data) {
											if (data.code == 1) {
												alert(data.result);
												location.reload(true);
											} else {
												alert(error);
											}
										}
									});
									return false;
								}
							}
						});
					} else {
						// 예약 delete & 결제 delete
						$.ajax({
							type : "delete",
							url : "/payment/delete",
							data : {
								"bookingId"	: bookingId
							},
							success : function(data) {
								if (data.code == 1) {
									alert(data.result);
									location.reload(true);
								} else {
									alert(error);
								}
							}
						});
						
						return false;
					}
				});
			}

			function succeedPay(imp_uid, merchant_uid) {
				$.ajax({
					url : '/payment/succeed',
					type : 'POST',
					data : {
						imp_uid : imp_uid, // 결제 고유번호
						merchant_uid : merchant_uid
					// 주문번호 
					},
					success : function(data) {
						if (data.cnt > 0) {
							var msg = '결제가 완료되었습니다.'
							alert(msg);
							location.href="/hotel/history_view";
						} else {
							var msg = '결제가 완료되었으나 에러가 발생했습니다.'
							alert(msg);
						}
					},
					error : function(e) {
						alert("에러")
					}
				});
			}

		});
		
	
		function initMap() {
			let address = document.getElementById('address').innerText;
			// 서울 중심 좌표
			let seoul = {
				lat : 37.5642135,
				lng : 127.0016985
			};

			// 맵 생성
			let map = new google.maps.Map(document.getElementById('map'));

			// 검색 주소 표시하기 위한 Geocoder 라이브러리
			var geocoder = new google.maps.Geocoder();

			geocodeAddress(geocoder, map, address);
			// 검색 주소 찾기 함수
			function geocodeAddress(geocoder, resultMap, address) {

				/**
				 * 입력받은 주소로 좌표에 맵 마커를 찍는다.
				 * 1번째 파라미터 : 주소 등 여러가지. 
				 *      ㄴ 참고 : https://developers.google.com/maps/documentation/javascript/geocoding#GeocodingRequests
				 * 
				 * 2번째 파라미터의 함수
				 *      ㄴ result : 결과값
				 *      ㄴ status : 상태. OK가 나오면 정상.
				 */

				geocoder.geocode({
					'address' : address
				}, function(result, status) {

					if (status === 'OK') {
						// 맵의 중심 좌표를 설정
						resultMap.setCenter(result[0].geometry.location);

						// 맵의 확대 정도를 설정
						resultMap.setZoom(19);

						// 맵 마커
						let marker = new google.maps.Marker({
							map : resultMap,
							position : result[0].geometry.location,
							label : "${petHotel.preSchoolName}"
						});

						// 위도
						// console.log('위도(latitude) : ' + marker.position.lat());
						// 경도
						// console.log('경도(longitude) : ' + marker.position.lng());
						hotelLat = marker.position.lat();
						hotelLng = marker.position.lng();
						
					} else {
						alert('지오코드가 다음의 이유로 성공하지 못했습니다 : ' + status);
					}
				});
			}
			
		}
		
		
		
	</script>
</div>
