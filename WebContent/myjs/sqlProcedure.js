/**
 * 
 */
var jquery_script = document.createElement('script');
jquery_script.src = '/AppoBoard/coco/jquery3.3.1.min.js';
jquery_script.type = 'text/javascript';
document.getElementsByTagName('head')[0].appendChild(jquery_script);

function sqlProcedure() {
	this.params = new Object();
	this.count = 0;
	console.write("sqlProcedure made!");
	this.addParams = function(key, value, type) {
		var keytemp = 'key'+String(count);
		var valuetemp = 'value'+String(count);
		var typetemp = 'type'+String(count);
		this.params[keytemp] = key;
		this.params[valuetemp] = value;
		this.params[typetemp] = type;
		this.count = this.count+1;
		//test
		console.write(key);
		console.write(value);
		console.write(type);
	};
	
	this.asyncAjax = function(sqlReq, call_back) {//sqlReq는 스트링, call_back은 콜백함수
		var json_data = new Object();
		json_data.sqlReq = sqlReq;
		json_data.counts = String(this.count);
		for(var i = 0; i < this.count; i++) {
			var key0 = 'key'+String(count);
			var key1 = 'value'+String(count);
			var key2 = 'type'+String(count);
			json_data[key0] = this.params[key0];
			json_data[key1] = this.params[key1];
			json_data[key2] = this.params[key2];
		}
		$.ajax({
	         url: '/AppoBoard/sql/test',
	         dataType: 'json',
	         data: json_data,
	         type: 'post',
	         success: function(ret) { // check if available
	           //success
	           call_back(ret);
	         },
	         error: function() { // error logging
	           console.log('Error!');
	         }
	       });
		
	}
}