<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*" pageEncoding="UTF-8"%>
<!-- 특정 id 일정 상세 보기, 수정은 링크로 넘기고 삭제는 그냥 바로 컨트롤러로 보냄. 이 때, 작성자 id가 일치하는지 체크해야함. 현재 session의 로그인아이디가 -->
<%
	request.setCharacterEncoding("utf-8"); 
	String currId = request.getParameter("id");
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
            max-width: 500px;
        }

        .view_btn {
            cursor: pointer;
        }
        th {
        	text-align: center;
        }
        tr {
        	text-align: center;
        }

</style>
<script src="/AppoBoard/coco/jquery3.3.1.min.js"></script>
<script src="/AppoBoard/myjs/sqlProcedure.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	var param1;
	var param2;
	var param3;
	var param4;
	var param5;
	var param6;
	function call_back_windowAppo(ret) {
		$("#th_1").attr("colspan", 2);
		var tr0 = $("<tr></tr>").appendTo("#tav_list"); // 제목 일정제목
		$("<td></td>").text('일정명').appendTo(tr0);
		$("<td></td>").text(ret[0]['TITLE']).appendTo(tr0);
		var tr1 = $("<tr></tr>").appendTo("#tav_list"); // 작성자 이름(td태그 2개를 가짐)
		$("<td></td>").text('작성자').appendTo(tr1);
		$("<td></td>").text(ret[0]['USERID']).appendTo(tr1);
		var tr2 = $("<tr></tr>").appendTo("#tav_list"); // 일정 시작일~일정 종료일
		$("<td></td>").attr("colspan", 2).attr("text-align", "left").text(ret[0]['STARTDATE']+'~'+ret[0]['ENDDATE']).appendTo(tr2);
		var tr3 = $("<tr></tr>").attr('height', '250px').appendTo("#tav_list"); //내용
		$("<td></td>").attr("colspan", 2).text(ret[0]['EXPLANATION']).appendTo(tr3); 
		//변수저장
		param1 = ret[0]['ID'];
		param2 = ret[0]['TITLE'];
		param3 = ret[0]['STARTDATE'];
		param4 = ret[0]['ENDDATE'];
		param5 = ret[0]['EXPLANATION'];
		param6 = ret[0]['USERID'];
	}
	function call_back_deleteAppo(ret) {
		location.href='/AppoBoard/user/Main?date='+param3.substring(0,10);
	}
	
	var temp = new sqlProcedure();
	temp.addParams("PID", "<%=currId%>", "integer");
	$("#btn_0").click(function(){ //일정 삭제 버튼
		var temp_delete = new sqlProcedure();
		temp_delete.addParams("PID", "<%=currId%>", "integer");
		temp_delete.syncAjax("DELETEAPPO", call_back_deleteAppo);		
	});
	
	$("#btn_1").click(function(){ //일정 수정 버튼
		console.log(param1);
		console.log(param2);
		console.log(param3);
		console.log(param4);
		console.log(param5);
		console.log(param6);
		var form = document.createElement('form'); //기존코드
		var form_input1, form_input2, form_input3, form_input4, form_input5, form_input6
		form_input1 = document.createElement('input');
		form_input2 = document.createElement('input');
		form_input3 = document.createElement('input');
		form_input4 = document.createElement('input');
		form_input5 = document.createElement('input');
		form_input6 = document.createElement('input');
		//alert(param2);
		form_input1.setAttribute('type', 'hidden');
		form_input1.setAttribute('name', 'id');
		form_input1.setAttribute('value',  param1);
		form_input2.setAttribute('type', 'hidden');
		form_input2.setAttribute('name', 'title')
		form_input2.setAttribute('value',  param2);
		form_input3.setAttribute('type', 'hidden');
		form_input3.setAttribute('name', 'startDate');
		form_input3.setAttribute('value',  param3);
		form_input4.setAttribute('type', 'hidden');
		form_input4.setAttribute('name', 'endDate');
		form_input4.setAttribute('value',  param4);
		form_input5.setAttribute('type', 'hidden');
		form_input5.setAttribute('name', 'explanation');
		form_input5.setAttribute('value',  param5);
		form_input6.setAttribute('type', 'hidden');
		form_input6.setAttribute('name', 'userId');
		form_input6.setAttribute('value',  param6);
		
		form.appendChild(form_input1);
		form.appendChild(form_input2);
		form.appendChild(form_input3);
		form.appendChild(form_input4);
		form.appendChild(form_input5);
		form.appendChild(form_input6);
		form.setAttribute('method', 'post');
		form.setAttribute('action', "/AppoBoard/user/ReviseAppo");
		document.body.appendChild(form);
		form.submit();
	});
	

	temp.asyncAjax("IDAPPO", call_back_windowAppo);
 });
 </script>
</head>
<body>
	<div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
               일정 상세 보기
            </h2>
            <div class="ui large form">
                <div class="ui stacked segment">
                	<button class="ui fluid large teal submit button" id="btn_0">일정 삭제</button><br>
                    <button class="ui fluid large teal submit button" id="btn_1">일정 수정</button>
                    <table class="ui celled table" id="tav_table">
                        <thead>
                            <tr>
                                <th id="th_1">일정</th>
                            </tr>
                        </thead>
                        <tbody id="tav_list">
                        </tbody>
                    </table>
                </div>

                <div class="ui error message"></div>

            </div>
            <a href="/webShop/todayAppoView2.jsp"><button class="ui fluid large teal submit button">뒤로가기</button></a>
        </div>
    </div>
</body>


</html>