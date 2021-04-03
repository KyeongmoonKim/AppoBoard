<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
     
<%
	//request.setCharacterEncoding("utf-8"); 
	//String currDate = request.getParameter("date");
%>
<!DOCTYPE html>
<html>
<head>
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
            max-width: 1000px;
        }

        .view_btn {
            cursor: pointer;
        }

</style>

<script src="/AppoBoard/coco/jquery3.3.1.min.js"></script>
<script src="/AppoBoard/myjs/sqlProcedure.js"></script>
<script type="text/javascript">
function call_back_todayAppo(ret) {
	for(var i in ret) {
	  //alert(typeof(i)); //스트링임
	  /*var tr = $("<tr></tr>").appendTo("#tav_list");
	  $("<td></td>").text(ret[i]['id']).appendTo(tr);
	  //var temp1 = $("<a></a>").attr("href", "/webShop/windowAppo.jsp?id="+ret[i]['id']);
	  //var temp2 = $("<td></td>").appendTo(tr);
	  $("<a></a>").attr("href", "/webShop/windowAppo.jsp?id="+ret[i]['id']).text(ret[i]['title']).appendTo($("<td></td>").appendTo(tr));
	  //$("<td></td>").text(ret[i]['title']).appendTo(tr);
	  $("<td></td>").text(ret[i]['userId']).appendTo(tr);
	  $("<td></td>").text(ret[i]['startDate']).appendTo(tr);
	  $("<td></td>").text(ret[i]['endDate']).appendTo(tr);*/
	  console.log("success!!!!!");
 	}
}

$(document).ready(function() {
	var temp = new sqlProcedure();
	console.log("${todayDate}");
	temp.addParams("date", "${todayDate}", "string");
 	(function() {
	 var pollinterval = setInterval(function() {
		 temp.asyncAjax("getDayAppo", call_back_todayAppo);
	 }, 5000);
	 temp.asyncAjax("getDayAppo", call_back_todayAppo);
 	})(); 
});
</script>

</head>
<body>
	<div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
               일정 페이지
            </h2>
            <div class="ui large form">
                <div class="ui stacked segment">
                	<a href="/webShop/monthAppo.jsp?Date=${todayDate}"><button class="ui fluid large teal submit button">월별 일정보기</button></a><br>
                    <a href="/webShop/makeAppo.jsp"><button class="ui fluid large teal submit button">일정 등록하기</button></a>
                    <table class="ui celled table" id="tav_table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>제목</th>
                                <th>등록자</th>
                                <th>시작날짜</th>
                                <th>끝날짜</th>
                            </tr>
                        </thead>
                        <tbody id="tav_list">
                        </tbody>
                    </table>
                </div>

                <div class="ui error message"></div>

            </div>
        </div>
    </div>

    <div class="ui modal" id='view_modal'>
        <i class="close">x</i>
        <div class="header" id="b_title">
            
        </div>
        <div class="content">
            <div class="description">
            	<p style = "text-align: right" id = "b_review"></p>
            	<div id = 'b_content'></div>
            </div>
        </div>
        <div class="actions">
            <div class="ui black deny button">
                닫기
            </div>
        </div>
    </div>
</body>

</html>