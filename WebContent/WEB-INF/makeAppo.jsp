<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 페이지 설명 : user/makeAppo로 title, explanation, statDate, endDate, userId(session정보 가져와서)로 post할꺼임. -->
<!DOCTYPE html>
<html>
<head>
<% 	String isLogin = (String)session.getAttribute("isLogin");
	if(isLogin==null||isLogin.compareTo("false")==0) {
%>
<script type="text/javascript">
	alert("로그인 하세욧!");
	window.location.assign("/webShop/login.jsp");
</script>
<%
	}
%>
<meta charset="UTF-8">

<!-- 일정 등록 창  -->
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/AppoBoard/coco/semantic.min.css">
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
<script src="/AppoBoard/coco/jquery3.3.1.min.js"></script>
<script src="/AppoBoard/myjs/sqlProcedure.js"></script>
<script type="text/javascript">

function call_back_makeAppo(ret) {
	location.href='/AppoBoard/user/Main';
}

function fn_validate(){	
	var temp = new sqlProcedure();
	
	var frmLogin=document.frmLogin;
    var title=frmLogin.title.value;
    var startDate=frmLogin.startDate.value;
    var endDate=frmLogin.endDate.value;
    var explanation=frmLogin.explanation.value;
    
	temp.addParams("PTITLE", title, "string"); //ID는 프로시저 안에서 EMP_SEQ.NEXTVAL하면됌.
	temp.addParams("PEXPLANATION", explanation, "string");
	temp.addParams("PSTARTDATE", startDate, "string");
	temp.addParams("PENDDATE", endDate, "string")
	temp.addParams("PUSERID", "<%=(String)session.getAttribute("userId")%>", "string");
    if((title.length==0 ||title=="") ||(startDate.length==0 ||startDate=="")||(endDate.length==0 ||endDate=="")){
		alert("제목과 날짜는 꼭 입력하셔야 합니다.");
    }else{
    	temp.syncAjax("MAKEAPPO", call_back_makeAppo);
    }
 }

</script>
</head>
<body>
 	<div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
                게시글 작성하기
            </h2>
            <form class="ui large form" name="frmLogin">
                <div class="ui stacked segment">
                    <div class="field">
                        <input type="text" id="title" name="title" placeholder="일정 제목">
                    </div>
                    <div class="field">
                    	<input type="text" id="startDate" name="startDate" placeholder="시작날짜(YYYY-MM-DD-hh-mm)">
                    </div>
                    <div class="field">
                    	<input type="text" id="endDate" name="endDate" placeholder="종료날짜(YYYY-MM-DD-hh-mm)">
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <textarea style="resize: vertical;" id="explanation" name="explanation" placeholder="게시글 내용" rows="8"></textarea>
                        </div>
                    </div>
                    <div class="ui fluid large teal submit button" id="write_bbs" onClick="fn_validate()">게시글 작성하기</div>
                </div>

            </form>

            <a href="/webShop/todayAppoView2.jsp"><button class="ui fluid large teal submit button">뒤로가기</button></a>
        </div>
    </div>
    
    

</body>
</html>