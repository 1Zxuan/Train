
function xiugai(this1){
	var row=$(this1).parents('tr')[0];
	var data=$("#TrainInfo").dataTable().fnGetData(row);//获取选中行数据
	if(confirm("确认修改")){
		$.ajax({
			type:'post',
			url:'../ChangeDataServlet',
			dataType:'json',
			data:data,
			success:function(data){
				if(data==true){
					alert('修改成功');
				}
			},
			error:function(err){
				alert('发起修改失败');
			}
		});
	}
}

function changestart_time(this1,text1){
	var row=$(this1).parents('tr')[0];
	var data=$("#TrainInfo").dataTable().fnGetData(row);//获取选中行数据
	data.start_time=text1;
}

function changearrive_time(this1,text1){
	var row=$(this1).parents('tr')[0];
	var data=$("#TrainInfo").dataTable().fnGetData(row);//获取选中行数据
	data.arrive_time=text1;
}

$(function(){
	
	
	$("#Search").click(function(){
		var trainName=$('#trainName').val();
		if(trainName!=0){
			$.ajax({
				type:'get',
				url:'../RootServlet',
				dataType:'json',
				data:{"doString":"SearchTrainInfo","trainName":trainName},
				success:function(data){
//					alert("成功");
					console.log(data);
					$("#table").html('<table id="TrainInfo" class="table table-striped table-bordered"><tbody></tbody></table>');
					$("#TrainInfo").dataTable({
						searching:false,
						paging:false,
						ordering:false,
						destroy:true,
						orderable:false,
						bAutoWidth:true,
						info: false,
						data:data,
						columns:[
							{data:"arrive_day_diff",title:"是否次日到达"},
							{data:"station_no",title:"站序"},
							{data:"trainName",title:"车次"},
							{data:"station_name",title:"车站"},
							{data:"arrive_time",title:"到站时间"},
							{data:"start_time",title:"出发时间",},
							{data:"businessClass",title:"商务座/特等座"},
							{data:"firstClass",title:"一等座"},
							{data:"secondClass",title:"二等座"},
							{data:"high_GradeSB",title:"高级软卧"},
							{data:"softBerth",title:"软卧"},
							{data:"reclining",title:"动卧"},
							{data:"hardBerth",title:"硬卧"},
							{data:"softSeats",title:"软座"},
							{data:"hardSeat",title:"硬座"},
							{data:"noSeat",title:"无座"},
							{data:"other",title:"其他"},
							{data:null,title:"备注"}
						],
						columnDefs:[{
							targets:[4],
							createdCell:function(cell,cellData,rowDate,rowIndex,colIndex){
								$(cell).click(function(){
									//alert(cellData);
									$(this).html('<input type="text" size="16"/>');
									var aInput=$(this).find(":input");
									aInput.focus().val(cellData);
								});
								$(cell).on("blur",":input",function(){
									var text=$(this).val();
									changearrive_time(this,text);
									$(cell).html(text);
//									$("#TrainInfo").table.cell(cell).data(text);
									cellData=text;
									
								})
							}
						},{
							targets:[5],
							createdCell:function(cell,cellData,rowDate,rowIndex,colIndex){
								$(cell).click(function(){
									//alert(cellData);
									$(this).html('<input type="text" size="16"/>');
									var aInput=$(this).find(":input");
									aInput.focus().val(cellData);
								});
								$(cell).on("blur",":input",function(){
									var text=$(this).val();
									changestart_time(this,text);
									$(cell).html(text);
//									$("#TrainInfo").table.cell(cell).data(text);
									cellData=text;
									
								})
							}
						},{
							targets:-1,
							render:function(data,type,full,meta){
								return '<button class="btn btn-primary" onclick="xiugai(this)">修改</button>';
							},
						}
						]
					});
//					editTableObj = new $.dataTableEditor(options);
					$('#TrainInfo').on( 'click', 'tbody td:not(:first-child)', function (e) {
//						TrainInfo.inline( this, {
//				            buttons: { label: '&gt;', fn: function () { this.submit(); } }
//				        } );
//						$("#TrainInfo").dataTableEditor({postURL:"/Enichment/Timetable"});
//						$("#TrainInfo").dataTableEditor({});
						
						
				    } );
				},
				error:function(err){
					alert("搜索时发生错误");
				}
				
			}); 
		}else{
			alert('请选择要修改的车辆');
		}
	});
	
	$("#loginout").click(function(){
		$.cookie("usercookie",'',{path:"/"});
		window.location.href = "/Train/login/login.html";
	});
	
		function getCookie(name){
			 var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
			  if (arr = document.cookie.match(reg)){
			    return 1;
			  }else{
			     return 0;
			  }   
			}
		var cookiestatus=getCookie('usercookie');
		$("#loginout").click(function(){
			$.cookie("usercookie",'',{path:"/"});
			window.location.href = "/Train/login/login.html";
		});
		if( $.cookie('usercookie')!='' && cookiestatus!=0 ){		//判断cookie
			$.ajax({
				type:'get',
				url:'../RootServlet',
				dataType:'json',
				data:{"doString":"trainName"},
				success:function(data){
//					alert('成功发起查询');
//					console.log(data);
					$("#trainName").prepend('<option value="0">请选择</option>');
					for(var i=0;i<=Object.keys(data).length - 1;i++){
//						$("#trainName").prepend('<option value="'+data[i].TrainName+'">'+data[i].TrainName+'</option>')
						$('#trainName.selectpicker').append("<option value="+data[i].TrainName+">"+data[i].TrainName+"</option>");
					};
					$("#trainName").selectpicker('refresh');
//					$("#trianName").options["0"].selected=true;
				},
				error:function(err){
					console.log(err);
					alert('发起查询失败');
				}
			});
		}else{
			alert("用户未登录");
			window.location.href = "/Train/login/login.html";
		}
	}
);



