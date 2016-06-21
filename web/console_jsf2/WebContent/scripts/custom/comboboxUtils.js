if(!window.Richfaces)window.Richfaces={};if(!window.RichComboUtils)window.RichComboUtils={};Richfaces.defined=function(o){return(typeof(o)!="undefined");};RichComboUtils.execOnLoad=function(func,condition,timeout){if(condition()){func();}else{window.setTimeout(function(){RichComboUtils.execOnLoad(func,condition,timeout);},timeout);}};RichComboUtils.Condition={ElementPresent:function(element){return function(){var el=document.getElementById(element);return el&&el.offsetHeight>0;};}};Richfaces.getBody=function(){if(document.body){return document.body;}
if(document.getElementsByTagName){var bodies=document.getElementsByTagName("BODY");if(bodies!=null&&bodies.length>0){return bodies[0];}}
return null;};Richfaces.zero=function(n){return(!Richfaces.defined(n)||isNaN(n))?0:n;};Richfaces.getDocumentHeight=function(){var viewportheight=0;var jqw=jQuery(window);viewportheight=jqw.height()+jqw.scrollTop();return viewportheight;}
Richfaces.getScrollWidth=function(elem){if(elem.clientWidth!=0){return elem.offsetWidth-elem.clientWidth;}
return 0;}
Richfaces.getBorderWidth=function(el,side){return Richfaces.getStyles(el,side,Richfaces.borders);}
Richfaces.getPaddingWidth=function(el,side){return Richfaces.getStyles(el,side,Richfaces.paddings);}
Richfaces.getMarginWidth=function(el,side){return Richfaces.getStyles(el,side,Richfaces.margins);}
Richfaces.getStyles=function(el,sides,styles){var val=0;for(var i=0,len=sides.length;i<len;i++){var w=parseInt(Element.getStyle(el,styles[sides.charAt(i)]),10);if(!isNaN(w))val+=w;}
return val;}
Richfaces.borders={l:'border-left-width',r:'border-right-width',t:'border-top-width',b:'border-bottom-width'},Richfaces.paddings={l:'padding-left',r:'padding-right',t:'padding-top',b:'padding-bottom'},Richfaces.margins={l:'margin-left',r:'margin-right',t:'margin-top',b:'margin-bottom'}