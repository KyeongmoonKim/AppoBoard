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
var call_back_temp = function(ret) {
	for(var i in ret) {
		console.log(ret[0]['userId']);
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