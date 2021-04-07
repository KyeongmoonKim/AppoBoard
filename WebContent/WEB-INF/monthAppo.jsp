<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="user.*, java.util.*" pageEncoding="UTF-8"%>
<!-- 월 일정 창  : 이제 폴링으로 월 일정(2021-12%겟지?예를들어) 폴링으로 댕겨오는데
	 섹터나누기 귀찮으니까 기존 텍스트 깔린거에 추가ㅑ해서 넣자 (ex)12(일정 5개) 그리고 이건 링크로. 그리고 가져올때 db접근할때도 날짜만 가져오자 귀찮으니까. 날짜에서 일일 부분만 꺼내와서 어차피 tr태그에 접근할꺼니까 ㄷ. tr텍스트 가져오고 empty하고 다시 텍스트넣고 ㅇㅇ
	 아근데 어차피 빈날짜라도 골라서 일정써야하니까 링크는 다걸어줘야하네 . 그럼 그 cnt적는거 없애면됌. 가져올때 아이디에서 골라낼꺼니까
 -->

<%
	request.setCharacterEncoding("utf-8");
	MyCalendar cal = new MyCalendar();
	String yearMonth = request.getParameter("Date");
	cal.setCalendar(yearMonth);
	String currDate = cal.currYM();
	String prevDate = cal.prevYM();
	String nextDate = cal.nextYM();
	//int dow = (cal.curr).get(Calendar.DAY_OF_WEEK); //1이 일요일, 7이 토요일
	int maximumDay = (cal.curr).getActualMaximum(Calendar.DAY_OF_MONTH);
	(cal.curr).set(Calendar.DATE, 1);
	int startDW = (cal.curr).get(Calendar.DAY_OF_WEEK);
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
            max-width: 1000px;
        }

        .view_btn {
            cursor: pointer;
        }

</style>

<script src="/AppoBaord/coco/jquery3.3.1.min.js"></script>
<script type="text/javascript">
function call_back_monthAppo(ret) {
	var size = Number(ret["size"]);
	for(var i = 0; i < size; i++) { //일정에 해당하는 그거 비우고
		var jsonKey = String(i);
		var DateKey = ret[jsonKey]['key'];
		var DateValue = ret[jsonKey]['value'];
		var Day = String(Number(DateKey.substring(8, 10)));
		$('#date_'+DateKey).empty();
		$("<a></a>").attr("href", "/AppoBoard/user/Main?date="+DateKey).text(Day+"("+DateValue+")").appendTo('#date_'+DateKey);
 	}
}
$(document).ready(function() {
	var dataJson = {
        currMonth : "<%=currDate.substring(0, 7)%>"
	};
	function get_Appointment(){	//월별 카운트를 가지고올꺼임.
		$.ajax({
        	url: '/webShop/user/monthAppoAjax',
        	dataType: 'json',
        	data: dataJson,
        	type: 'post',
        	success: function(ret) { // check if available
        		var size = Number(ret["size"]);
        		for(var i = 0; i < size; i++) { //일정에 해당하는 그거 비우고
        			var jsonKey = String(i);
        			var DateKey = ret[jsonKey]['key'];
        			var DateValue = ret[jsonKey]['value'];
        			var Day = String(Number(DateKey.substring(8, 10)));
        			$('#date_'+DateKey).empty();
        			$("<a></a>").attr("href", "/AppoBoard/user/Main?date="+DateKey).text(Day+"("+DateValue+")").appendTo('#date_'+DateKey);
        		}
           //success
        		/*$( '#tav_list').empty();
           		for(var i in ret) {
        	  	var tr = $("<tr></tr>").appendTo("#tav_list");
        	  	$("<td></td>").text(ret[i]['id']).appendTo(tr);
        	  	//var temp1 = $("<a></a>").attr("href", "/webShop/windowAppo.jsp?id="+ret[i]['id']);
        	 	 //var temp2 = $("<td></td>").appendTo(tr);
        	  	$("<a></a>").attr("href", "/webShop/windowAppo.jsp?id="+ret[i]['id']).text(ret[i]['title']).appendTo($("<td></td>").appendTo(tr));
        	  	//$("<td></td>").text(ret[i]['title']).appendTo(tr);
        	  	$("<td></td>").text(ret[i]['userId']).appendTo(tr);
        	  	$("<td></td>").text(ret[i]['startDate']).appendTo(tr);
        	  	$("<td></td>").text(ret[i]['endDate']).appendTo(tr);
           		}*/
        	},
         	error: function() { // error logging
           		console.log('Error!');
         	}
       	});
	}
 	(function() {
	 	var pollinterval = setInterval(function() {
		get_Appointment();
	 	}, 2000);
	 get_Appointment();
 	})(); 
 
 });
</script>

</head>
<body>
	<div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
               <%=currDate.substring(0,7)%>
            </h2>
            <div class="ui large form">
                <div class="ui stacked segment">
                	<a href="/webShop/monthAppo.jsp?Date=<%=prevDate%>"><button class="ui fluid large teal submit button">저번달</button></a><br>
                    <a href="/webShop/monthAppo.jsp?Date=<%=nextDate%>"><button class="ui fluid large teal submit button">다음달</button></a>
                    <table class="ui celled table" id="tav_table">
                    	<thead>
                    		<tr>
                    			<th>일</th>
                            	<th>월</th>
                            	<th>화</th>
                            	<th>수</th>
                            	<th>목</th>
                            	<th>금</th>
                            	<th>토</th>
                            </tr>                               
                    	</thead>
                        <tbody>
                        	<%int i=1;
                        	  while(i<startDW) { //i가 시작요일보다 작을때는 그냥 td만 넣음
                        		  if(i==1) {
                        	%>
                        	<tr>
                        	<%
                        		  }
                        	%>
                        	  <td></td>
                        	<%
                        		i++;
                        	}
                        	%>
                        	<%
                        	  int cnt = 1;
                        	  int tempDW = -1;
                        	  for(;cnt<=maximumDay;cnt++) {
                        		  (cal.curr).set(Calendar.DATE, cnt);
                        		  String tempStr = cal.currYM();
                        		  tempDW = (cal.curr).get(Calendar.DAY_OF_WEEK);
                        		  if(tempDW==0) { //<tr>태그넣고 td태그넣어야함
                        	%>
                        	<tr>
                        		<td id="date_<%=tempStr%>"><a href="/AppoBoard/user/Main?date=<%=tempStr%>"><%=cnt%></a></td>
                        	<%
                        		  } else if(tempDW==7) { //td넣고 </tr>해줘야함
                        	%>
                        		<td id="date_<%=tempStr%>"><a href="/AppoBoard/user/Main?date=<%=tempStr%>"><%=cnt%></a></td>
                        	</tr>
                        	<%
                        		  } else {
                        	%>
                        		<td id="date_<%=tempStr%>"><a href="/AppoBoard/user/Main?date=<%=tempStr%>"><%=cnt%></a></td>
                        	<%
                        		  }
                        	%>
                        	<%
                        	  }
                        	  tempDW++;
                        	%>
                        	<%
                        		while(tempDW < 8) {
                        			if(tempDW==7) {
                        	%>
                        		<td></td>
                       		</tr>
                        	<%
                        			} else {
                        	%>
                        	<td></td>
                        	<%
                        			}
                        	%>
                        	<%
                        			tempDW++;
                        		}
                        	%>
                        </tbody>
                    </table>
                </div>

                <div class="ui error message"></div>

            </div>
        </div>
    </div>    
</body>
</html>