<form action="ticketService.do">
	<input type="hidden" id="row" name="row"/>	
	<input type="hidden" id="version" name="version"/> 
	
	<div id="ticket-serchDiv" class="x-hidden">
       	<div class="x-window-header">
           	Search Ticket
           </div>
       	<div id="ticket-serch-tab">
           	<div class="x-tab" title="Search">
				<table style="width: 620px">
				<table border="0">					
					<tr height="5px"></tr>     

					<tr>
						<td width="30%" align="right">
							Pawner Code&nbsp;
						</td>
						<td>
							<input type="text" id="pawnerCode" name="pawnerCode" size="15" maxlength="8"/>
						</td>
					</tr>	

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
							<input type="button" value="Serch" onclick="serchTicket()">
						</td>
					</tr>							
					<tr height="5px"></tr>  
					<tr>
						<td colspan="2">
							<style>
								#ticketGrid {height: 210px;width:600px;}
								#ticketGrid .aw-row-selector {text-align: center}
								#ticketGrid .aw-column-0 {width: 120px;}
								#ticketGrid .aw-column-1 {width: 350px;}	
								#ticketGrid .aw-column-2 {width: 80px;}
								#ticketGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
								#ticketGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}							
							</style>
							
							<script>
								var myColumnsSerchPawner  = ["Ticket Number","Pawner Name","Pawner Code"];
	            				var strSerchPawner        = new AW.Formats.String; 
	            				var cellFormatSerchPawner = [strSerchPawner,strSerchPawner,strSerchPawner];
                    			var gridSerchTicket       = createBrowser(myColumnsSerchPawner,'ticketGrid',cellFormatSerchPawner);							                    			 			                    			
                    			gridSerchTicket.setHeaderHeight(25);
                                document.write(gridSerchTicket);
                                gridSerchTicket.onRowDoubleClicked = function(event, row){
									try{
										$('ticketId').value     = this.getCellText(3,row);
										$('ticketNumber').value = this.getCellText(0,row);
										$('version').value		= this.getCellText(4,row);
			                        	winTicket.hide();
			                        	getTicketData();
									}catch(error){}
								};
								gridSerchTicket.onSelectedRowsChanged=function(row){
									try{											
										if(row!=''){
											$('ticketId').value     = this.getCellText(3,row);
											$('ticketNumber').value = this.getCellText(0,row);
											$('version').value		= this.getCellText(4,row);
											$('row').value	= row;
										}else{
											$('row').value	= -1;
										}	
									}catch(error){}
								}
								
								var winTicket;
								Ext.onReady(function(){
								    
								    var button = Ext.get('ButtonTicketSerch');
								    button.on('click', function(){
								        if(!winTicket){
								            winTicket = new Ext.Window({
								                el:'ticket-serchDiv',
								                layout:'fit',
								                width:620,
								                height:395,
								                closable:false, 
								                closeAction:'hide',
								                plain: true,
								
								                items: new Ext.TabPanel({
								                    el: 'ticket-serch-tab',
								                    autoTabs:true,
								                    activeTab:0,
								                    deferredRender:false,
								                    border:false
								                }),
								
								                buttons: [{
								                    text: 'Ok',
								                    handler: function(){				                   										                   		
								                   		$('divTicketno').innerHTML = '';								                   		
								                        winTicket.hide();
								                        if($('row').value>-1){
								                        	$('ticketId').value     = gridSerchTicket.getCellText(3,$('row').value);
															$('ticketNumber').value = gridSerchTicket.getCellText(0,$('row').value);
															$('version').value		= gridSerchTicket.getCellText(4,$('row').value);
															getTicketData();
														}else{
									                        $('ticketId').value     = '0';
															$('ticketNumber').value = '';
														}	
								                        $('nicOrBrn').value 	= '';		                        				                        	
							                        	$('surname').value  	= '';
							                        	$('pawnerCode').value   = '';
								                   	}
								                }]
								            });
								        }
								        $('nicOrBrn').value 	= '';		                        				                        	
			                        	$('surname').value  	= '';
			                        	$('pawnerCode').value   = '';
								        setGridData(gridSerchTicket,[]);
								        winTicket.show(this);					        
								    });
								 });										
								 
								 function serchTicket(){
								 	var data       = "dispatch=getAjaxData";
								 	
								 	var nicOrBrn 	= $('nicOrBrn').value;
								 	var surname  	= $('surname').value;
								 	var pawnerCode	= $('pawnerCode').value;								 		
								 	
								 	data += "&nicOrBrn=" + nicOrBrn + "&surname=" + surname +
								 			"&pawnerCode=" + pawnerCode ;
								 	
								 	if($('ticketStatus')){
								 		var ticketStatus= $('ticketStatus').value;
								 		data += "&ticketStatus=" + ticketStatus;
								 	}else{
								 		data += "&ticketStatus=8";
								 	}		
								 
								 									 	
									var mySearchRequest = new ajaxObject('ticketService.do');
									mySearchRequest.callback = function(responseText, responseStatus, responseXML) {
										if (responseStatus==200) {						
											var message =  eval('(' + responseText + ')');
											if(message['error']){
												alert(message['error']);
											}else{							
												setGridData(gridSerchTicket,message);					
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

