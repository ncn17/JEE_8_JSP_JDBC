if(typeof cg_ncn === 'undefined'){
	var cg_ncn = {
		//information
		author : "NKOUNKOU Clerc Nicephore",
		version : 1.05,
		lastUpdate : "22/07/2019",
		//select methods
		get : {
			elmtById : function (q){
				return document.getElementById(q);
				},
			elmtsByTagName : function (q){
				return document.getElementsByTagName(q);
				},
			elmtsByName : function (q){
				return document.getElementsByName(q);
				},
			querySelector : function (q){
				return document.querySelector(q);
				},
			querySelectorAll : function (q){
				return document.querySelectorAll(q);
				},
		},
		//creation dom
		create : {
			text : function (text){
				return document.createTextNode(text);
			},
			elmt : function (elmt){
				return document.createElement(elmt);
			},
			link : function (hrefText,titleText,linkText){
				var link = document.createElement('a');
				link.appendChild(this.text(linkText));
				link.href = hrefText;
				link.title = titleText;
				return link;
			},
			strong : function (strongText){
				var strong = document.createElement('strong');
				strong.appendChild(this.text(strongText));
				return strong;
			},

		},
		//event Listener zonne
		addEvent : function (elmt,event,func,propa)	{
			if(elmt.attachEvent){
				elmt.attachEvent('on'+event,func);
			}else{
				elmt.addEventListener(event,func,propa);
			}
		} 
	};
}else{ /* exist dejà eviter l'écrasement */ }
