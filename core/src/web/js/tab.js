// JavaScript Document
function TabImageOnclick(e){
	try{
		nodelist=document.getElementById("tabTR").childNodes.length;
		document.getElementById("clickTab").value=e.id;
		if(navigator.appName=="Microsoft Internet Explorer"){
			nodelist=nodelist-1;
		}else{
			nodelist=nodelist-2;
		}
					
		e.className="hint"
		for (var i=0; i<nodelist; i++) {
			if(document.getElementById("tabTR").childNodes[i].id != e.id ){
				document.getElementById("tabTR").childNodes[i].className="def";
			}
		}
	}
	catch(error){
		//alert(error);
	}
}

function Tabonmouseover(e){
	try{
		if(document.getElementById("clickTab").value!=e.id && navigator.appName == "Microsoft Internet Explorer"){
			//document.getElementById("tabTR").childNodes[e.id-1].className="hint2"
			e.className="hint2"
		}
	}catch(error){
		//alert(error)
	}
	
}

function Tabonmouseout(e){
	try{
		if(document.getElementById("clickTab").value!=e.id && navigator.appName == "Microsoft Internet Explorer"){
			//document.getElementById("tabTR").childNodes[e.id-1].className="def"
			e.className="def"
		}
	}catch(error){
		//alert(error)
	}
}
	
// resize fix for ns4
var origWidth, origHeight;
if (document.layers) {
	origWidth = window.innerWidth; origHeight = window.innerHeight;
	window.onresize = function() { if (window.innerWidth != origWidth || window.innerHeight != origHeight) history.go(0); }
}

var cur_lyr;	// holds id of currently visible layer

function loadLyr(lyr) {
	if (cur_lyr) {
		var curcss = get_lyr_css(cur_lyr);
		if (curcss) curcss.visibility="hidden";
	}
	cur_lyr = lyr;
	
	var curcss = get_lyr_css(cur_lyr);

	if (curcss) {
		curcss.visibility = "visible";
		curcss.zIndex = 1000;	// some browsers need z-index set
	}
}

// get reference
function get_lyr_css(id) {
	var lyr, lyrcss;
	lyr = (document.getElementById)? document.getElementById(id): (document.all)? document.all[id]: (document.layers)? getLyrRef(id,document): null;
	if (lyr) lyrcss = (lyr.style)? lyr.style: lyr;
	return lyrcss;
}

// get reference to nested layer for ns4
function getLyrRef(lyr,doc) {
	if (document.layers) {
		var theLyr;
		for (var i=0; i<doc.layers.length; i++) {
		theLyr = doc.layers[i];
			if (theLyr.name == lyr) return theLyr;
			else if (theLyr.document.layers.length > 0) 
			if ((theLyr = getLyrRef(lyr,theLyr.document)) != null)
					return theLyr;
		}
		return null;
	}
}
