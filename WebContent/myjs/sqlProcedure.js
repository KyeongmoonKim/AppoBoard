/**
 * 
 */
var jquery_script = document.createElement('script');
jquery_script.src = '/AppoBoard/coco/jquery3.3.1.min.js';
jquery_script.type = 'text/javascript';
document.getElementsByTagName('head')[0].appendChild(jquery_script);

function sqlProcedure() {
	this.params = [];
	this.count = 0;
	this.addParams = function(key, value, type) {
		var keytemp = 'key'+String(count);
		var valuetemp = 'value'+String(count);
		var typetemp = 'type'+String(count);
		params[keytemp] = key;
		params[valuetemp] = value;
		params[typetemp] = type;
	};
	
}