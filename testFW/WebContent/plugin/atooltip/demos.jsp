<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
                      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>aToolTip Demos</title>
		
		<!-- CSS -->
		<link type="text/css" href="<%=ConstantsUtil.FW_DOMAIN%>/demo/atooltip/css/style.css" rel="stylesheet"  media="screen" />
		
		<!-- aToolTip css -->
		<link type="text/css" href="<%=ConstantsUtil.FW_DOMAIN%>/demo/atooltip/css/atooltip.css" rel="stylesheet"  media="screen" />
		
		
<script language="javascript" type="text/javascript"
	src="<%=ConstantsUtil.FW_DOMAIN%>/js/jquery-1.8.2.js"></script>
		
		<!-- aToolTip js -->
		<script type="text/javascript" src="<%=ConstantsUtil.FW_DOMAIN%>/demo/atooltip/js/jquery.atooltip.js"></script>
		<script type="text/javascript">
			$(function(){ 
				$('a.normalTip').aToolTip();
				
				
				$('a.fixedTip').aToolTip({
		    		fixed: true
				});
				
				$('a.clickTip').aToolTip({
		    		clickIt: true,
		    		tipContent: 'Hello I am aToolTip with content from the "tipContent" param'
				});		
				
				$('a.callBackTip').aToolTip({
					clickIt: true,
					onShow: function(){alert('I fired OnShow')},
					onHide: function(){alert('I fired OnHide')}
				});		
				
				
			}); 
		</script>
		
		
	
		
	</head>
	<body>
	 
	 	<div class="primaryWrapper">
	 	
	 		<div class="branding">
	 			<h1 class="logo"><a href="#">aToolTip</a></h1>
	 		</div>
			
		 

			<!-- DEMOS -->
			
			<div class="section" id="demos">
		 		<h2>Demos</h2>
			 
			 	<ul class="demos">
			 		<li><a href="#" class="normalTip exampleTip" title="Hello, I am aToolTip">Normal Tooltip</a> - This is a normal tooltip with default settings.</li>
			 		<li><a href="#" class="fixedTip exampleTip" title="Hello, I am aToolTip">Fixed Tooltip</a> - This is a fixed tooltip that doesnt follow the mouse.</li>
			 		<li><a href="#" class="clickTip exampleTip" >On Click Tooltip</a> - This is a click activated tooltip with content passed in from 'tipContent' param</li>
			 		<li><a href="#" class="callBackTip exampleTip" title="Hello, I am aToolTip">Callback Tooltip</a> - This is a click activated tooltip with callback functions.</li>
			 	</ul>

		 	</div>			
			
			<!-- END DEMOS -->
	
			
			<p class="copyright">
				Copyright &copy; 20011 Ara Abcarians. aToolTip was built for use with <a href="http://jquery.com">jQuery</a> by <a href="http://ara-abcarians.com/"> Ara Abcarians.</a> 
			</p>
			
			<p class="license">
<a rel="license" href="http://creativecommons.org/licenses/by/3.0/"><img alt="Creative Commons License" style="border-width:0" src="http://creativecommons.org/images/public/somerights20.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/">Creative Commons Attribution 3.0 Unported License</a>.			
			</p>

	 	
	 	</div> <!-- /primaryWrapper -->
	 

	</body>
</html>