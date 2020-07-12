<form action="pawnerService.do">
	<input type="hidden" id="row" name="row" value="-1"/>	
	
	<div id="pawner-serchDiv" class="x-hidden">
       	<div class="x-window-header">
           	Search Pawner
           </div>
       	<div id="serch-tab">
           	<div class="x-tab" title="Search">
				<table style="width: 620px">
				<table border="0">					
					<tr height="5px"></tr>     
					<tr>
						<td width="30%" align="right">
							NIC No/BRN&nbsp;
						</td>
						<td>
							<input type="text" id="nicOrBrn" name="nicOrBrn" size="25" maxlength="20"/>
						</td>
					</tr>	
																			
					<tr>
						<td width="30%" align="right">
							Surname&nbsp;
						</td>
						<td>
							<input type="text" id="surname" name="surname" size="50" maxlength="40"/>
						</td>
					</tr>	
					
					<tr>
						<td width="30%" align="right">
							Town&nbsp;
						</td>
						<td>
							<input type="text" id="town" name="town" size="50" maxlength="30"/>
							<input type="button" value="Serch" onclick="serchPawner()">
						</td>
					</tr>							
					<tr height="5px"></tr>  
					<tr>
						<td colspan="2">
							<style>
								#pawnerGrid {height: 210px;width:600px;}
								#pawnerGrid .aw-row-selector {text-align: center}
								#pawnerGrid .aw-column-0 {width: 100px;}
								#pawnerGrid .aw-column-1 {width: 350px;}
								#pawnerGrid .aw-column-2 {width: 120px;}	
								#pawnerGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
								#pawnerGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}							
							</style>
							
							<script>
								var myColumnsSerchPawner  = ["Code","Client Name","NIC Number"];
	            				var strSerchPawner        = new AW.Formats.String; 
	            				var cellFormatSerchPawner = [strSerchPawner,strSerchPawner,strSerchPawner];
                    			var gridSerchPawner       = createBrowser(myColumnsSerchPawner,'pawnerGrid',cellFormatSerchPawner);							                    			 			                    			
                    			gridSerchPawner.setHeaderHeight(25);
                                document.write(gridSerchPawner);
                                gridSerchPawner.onRowDoubleClicked = function(event, row){
									try{
										$('pawnerId').value     = this.getCellText(22,row);
										$('pawnerCode').value   = this.getCellText(0,row);	
										$('pawnerName').value	= this.getCellText(1,row);																				
			                        	$('divPawnerCode').innerHTML = '';
			                        	$('nicOrBrn').value = '';		                        				                        	
			                        	$('surname').value  = '';
			                        	$('town').value     = '';
			                        	winPawner.hide();
			                        	getPawnerData();
									}catch(error){}
								};
								gridSerchPawner.onSelectedRowsChanged=function(row){
									try{											
										if(row!=''){
											//$('pawnerId').value     = this.getCellText(22,row);
											//$('pawnerCode').value   = this.getCellText(0,row);
											//$('pawnerName').value	= this.getCellText(1,row);
											$('row').value	= row;
										}else{
											$('row').value	= -1;
										}	
									}catch(error){}
								}
								
								var winPawner;
								Ext.onReady(function(){
								    
								    var button = Ext.get('ButtonPawnerSerch');
								    button.on('click', function(){
								        if(!winPawner){
								            winPawner = new Ext.Window({
								                el:'pawner-serchDiv',
								                layout:'fit',
								                width:620,
								                height:395,
								                closable:false, 
								                closeAction:'hide',
								                plain: true,
								
								                items: new Ext.TabPanel({
								                    el: 'serch-tab',
								                    autoTabs:true,
								                    activeTab:0,
								                    deferredRender:false,
								                    border:false
								                }),
								
								                buttons: [{
								                    text: 'Ok',
								                    handler: function(){				                   										                   		
								                   		$('divPawnerCode').innerHTML = '';								                   		
								                        winPawner.hide();
								                        if($('row').value>-1){								                        	
									                        $('pawnerId').value     = gridSerchPawner.getCellText(22,$('row').value);
															$('pawnerCode').value   = gridSerchPawner.getCellText(0,$('row').value);
															$('pawnerName').value	= gridSerchPawner.getCellText(1,$('row').value);
															getPawnerData();
														}else{
									                        $('pawnerId').value     = '0';
															$('pawnerCode').value   = '';
															$('pawnerName').value	= '';
														}	
								                        $('nicOrBrn').value = '';		                        				                        	
							                        	$('surname').value  = '';
							                        	$('town').value     = '';
								                   	}
								                }]
								            });
								        }
								        winPawner.show(this);					        
								    });
								 });										
								 
								 function serchPawner(){
								 	var data       = "dispatch=getPawnerSerchData";
								 	
								 	var nicOrBrn = $('nicOrBrn').value;
								 	var surname  = $('surname').value;
								 	var town	 = $('town').value;
								 	var userType = $('userType').value;
								 	
								 	data += "&nicOrBrn=" + nicOrBrn + "&surname=" + surname +
								 			"&town=" + town + "&userType=" + userType;
								 									 	
									var mySearchRequest = new ajaxObject('pawnerService.do');
									mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
										if (responseStatus==200) {						
											var message =  eval('(' + responseText + ')');
											if(message['error']){
												alert(message['error']);
											}else{							
												setGridData(gridSerchPawner,message);					
											}	
										}else {
								    	    alert(responseStatus + ' -- Error Processing Request'); 
									    }
									}
									mySearchRequest.update(data,'POST');
								 }	                               
							</script>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>			
</form>

