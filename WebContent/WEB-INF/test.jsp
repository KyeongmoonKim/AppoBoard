<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="/AppoBoard/myjs/sqlProcedure.js"></script>
<script src="/AppoBoard/coco/jquery3.3.1.min.js"></script>
<script type="text/javascript">
var temp = new sqlProcedure(); //중요
temp.addParams("date", "2021-03-07", "string");
function call_back_temp(lad) {
	for(var i in lad) {//지금 return 값이 List<UserVO>임.
		console.log(lad[i]['userId']);
		console.log(lad[i]['userPwd']);
		console.log(lad[i]['name']);
	}
	return;
}
temp.asyncAjax("test", call_back_temp);

</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>