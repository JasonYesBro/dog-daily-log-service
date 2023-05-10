<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="signUpWrpper" class="container d-flex flex-column align-items-center font-weight-bold text-center">
    <h3>비밀번호 재설정</h3>
    <div class="form-group input-box d-flex flex-column align-items-center justify-content-between">
        <input type="email" name="email" placeholder="가입하신 이메일을 입력해주세요" id="email" class="form-control col-10 mt-2">
        <button type="button" class="btn mt-2 reset-btn mt-3" id="codeMailBtn">인증코드 메일 보내기</button>
    </div>
    <div class="form-group input-box d-flex align-items-center justify-content-around">
        <input type="text" id="verifyCode" placeholder="인증코드를 입력해주세요" class="form-control col-7">
        <input type="hidden" id="verifyCheck" value="0" />
        <button type="button" class="btn col-3 reset-btn" id="verifyBtn">인증하기</button>
    </div>
    <div class="form-group input-box d-flex flex-column align-items-center justify-content-around">
        <input type="password" id="password" placeholder="변경하실 비밀번호를 입력해주세요" class="form-control col-10">
        <button type="button" class="btn col-10 mt-3 reset-btn" id="resetBtn">변경하기</button>
    </div>
    
    <script>
    	$(document).ready(function() {
    		// 0으로 초기화
    		$("#verifyCheck").val(0);
    		
    		// email 로 인증코드 보내기
    		$("#codeMailBtn").on('click', function() {
    			
	    		let email = $("#email").val().trim();
	    		
	    		if(!email) {
	    			alert("이메일을 입력해주세요.");
	    			return false;
	    		}
	    		
	    		$.ajax({
	    			type : "post",
	    			url : "/api/mail",
	    			data : {"email" : email},
	    			
	    			success : function(data) {
	    				if(data.result == true) {
	    					alert("인증코드를 전송하였습니다.");
	    					
	    				} else {
	    					alert(data.errorMessage);
	    				}
	    			},
	    			error : function(status, error, request) {
	    				alert("관리자에게 문의바랍니다.");
	    			}
	    		});
    		})
    		
    		// 인증하기 버튼
    		$("#verifyBtn").on('click', function() {
    			
    			let verifyCode = $("#verifyCode").val();
    			
    			if (!verifyCode) {
    				alert("인증코드를 입력해주세요.");
    			}
    			
    			$.ajax({
    				type : "post",
    				url : "/api/verify",
    				data : { "verifyCode" : verifyCode },
    				
    				success : function(data) {
    					if (data.code == 1) {
    						alert("인증되었습니다.");
    						// 인증 처리 flag 필요
    						$("#verifyCheck").val(1);
    					} else {
    						alert(data.errorMessage);
    					}
    					
    				},
    				error : function(status, error, request) {
    					alert("관리자에게 문의바랍니다.");
    				}
    			
    			});
    		});
    		
    		// 비밀번호 재설정 버튼
    		$("#resetBtn").on('click', function() {
    			let email = $("#email").val().trim();
    			let password = $("#password").val();
    			let verifyCheck = $("#verifyCheck").val();
    			
    			if(!email) {
    				alert("이메일을 입력해주세요.");
    				return false;
    			}
    			if(!password) {
    				alert("비밀번호를 입력해주세요.");
    				return false;
    			}
    			if(verifyCheck == 0) {
    				alert("인증코드를 인증해주세요.");
    				return false;
    			}
    			
    			$.ajax({
    				type : "put",
    				url : "/user/reset_pwd",
    				data : {
    					"email" : email,
    					"password" : password
    					},
    				success : function(data) {
    					if(data.code == 1) {
    						alert("비밀번호가 변경되었습니다.");
    						location.href = "/user/sign_in_view";
    						
    					} else {
    						alert(data.errorMessage);
    					}
    				},
    				error : function(status, error, request) {
    					alert("관리자에게 문의바랍니다.");
    				}
    				
    			});
    		});
    	});
    </script>
</div>