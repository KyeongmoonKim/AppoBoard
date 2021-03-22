<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!-- 로그인 창 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정</title>

<link rel="stylesheet" type="text/css" href="./coco/semantic.min.css">

    <style type="text/css">
        body {
            background-color: #DADADA;
        }
        body>.grid {
            height: 100%;
        }
        .image {
            margin-top: -100px;
        }
        .column {
            max-width: 450px;
        }

    </style>
</head>
<body>
    <div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
 				로그인 페이지
            </h2>
            <form class="ui large form" name="frmLogin">
                <div class="ui stacked segment">
                    <div class="field">
                        <div class="ui left icon input">
                            <input type="text" id="user_id" name="user_id" placeholder="아이디">
                        </div>
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <input type="password" id="user_pw" name="user_pw" placeholder="비밀번호">
                        </div>
                    </div>
                    <div class="ui fluid large teal submit button" onClick="fn_validate()" id="login_btn">로그인</div>
                </div>

                <div class="ui error message"></div>

            </form>

            <div class="ui message">
                로그인 할 계정이 없다면 여기를 눌러주세요.
            </div>
        </div>
    </div>
    
    
<script src="./coco/jquery3.3.1.min.js"></script>
<script type="text/javascript">
function fn_validate(){	
    var frmLogin=document.frmLogin;
    var user_id=frmLogin.user_id.value;
    var user_pw=frmLogin.user_pw.value;

    if((user_id.length==0 ||user_id=="") ||(user_pw.length==0 ||user_pw=="")){
	 alert("아이디와 비밀번호는 필수입니다.");
    }else{
	frmLogin.method="post";
	frmLogin.action="/AppoBoard/user/login"; //directory 경로
	frmLogin.submit();
    }
 }
 window.onload=ol_alert();
</script>
</body>
</html>