<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*" pageEncoding="UTF-8"%>
<!-- 일정 수정. windowAppo에서 post로 전송받은 데이터를 폼형식 안에 넣을거임 폼형식은 똑같음 makeAppo랑 -->
<%
	request.setCharacterEncoding("utf-8");  //굉장히 굉장히 중요
	String id = (String)request.getParameter("id");
	String title = (String)request.getParameter("title");
	String explanation = (String)request.getParameter("explanation");
	String startDate = (String)request.getParameter("startDate");
	String endDate = (String)request.getParameter("endDate");
	String userId = (String)session.getAttribute("userId");
%>
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

$(document).ready(function() {
	$("#title").attr("value", "<%=title%>");
	function call_back_reviseAppo(ret) {
		location.href='/AppoBoard/user/Main?date='+document.frmLogin.startDate.value.substring(0,10);
	}
	$("#btn_0").click(function(){
		var temp = new sqlProcedure();
		var frmLogin=document.frmLogin;
    	var title=frmLogin.title.value;
    	var startDate=frmLogin.startDate.value;
    	var endDate=frmLogin.endDate.value;
    	var explanation=frmLogin.explanation.value;
    	temp.addParams("PID", '<%=id%>', "integer");
		temp.addParams("PTITLE", title, "string"); //ID는 프로시저 안에서 EMP_SEQ.NEXTVAL하면됌.
		temp.addParams("PEXPLANATION", explanation, "string");
		temp.addParams("PSTARTDATE", startDate, "string");
		temp.addParams("PENDDATE", endDate, "string");
		if((title.length==0 ||title=="") ||(startDate.length==0 ||startDate=="")||(endDate.length==0 ||endDate=="")){
			alert("제목과 날짜는 꼭 입력하셔야 합니다.");
    	} else{
   		 	temp.syncAjax("REVISEAPPO", call_back_reviseAppo);
    	}
	});
});

</script>

</head>
<body>
	<div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
                일정 수정하기
            </h2>
            <form class="ui large form" name="frmLogin" id="frmRevise">
                <div class="ui stacked segment">
                    <div class="field" id="field1">
                        <input type="text" id="title" name="title" >
                    </div>
                    <div class="field">
                    	<input type="text" id="startDate" name="startDate" value="<%=startDate%>">
                    </div>
                    <div class="field">
                    	<input type="text" id="endDate" name="endDate" value="<%=endDate%>">
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <textarea style="resize: vertical;" id="explanation" name="explanation" rows="8"><%=explanation %></textarea>
                        </div>
                    </div>
                    <div class="ui fluid large teal submit button" id="btn_0">일정 수정하기</div>
                </div>

            </form>

            <a href="/AppoBoard/user/Main"><button class="ui fluid large teal submit button">홈으로 돌아가기</button></a>
        </div>
    </div>
    

</body>
</html>