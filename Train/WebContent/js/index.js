/**
 * 
 * 登录成功js
 */

window.onload=function(){
//	document.getElementById("StartStation").value="宁波";
//	document.getElementById("EndStation").value="台州";
}	
function yuding(this1){
	
	
	
		var row=$(this1).parents('tr')[0];
		var data=$("#dataTable").dataTable().fnGetData(row);//获取选中行数据
		
		var userName=$.cookie('usercookie');	//用户名
		var trainName=data.trainName;			//车次
		var startstation=data.station_name;		//出发站
		var endstation=data.endStation;			//到达站
		var buy;
		$.ajax({
			type:'get',
			url:'../BuyServlet',
			dataType:'json',
			data:{"doString":"checkticket","userName":userName,"trainName":trainName,},
			success:function(data){
				if(data){
					if(!window.localStorge){
						var storage=window.localStorage;
						storage.userName=userName;
						storage.trainName=trainName;
						storage.startstation=startstation;
						storage.endstation=endstation;
						window.location.href = "/Train/Buy/Buy.jsp";
					}else{
						alert('浏览器不支持localStorage,无法继续!');
					}
					
				}else
					alert('不能购买此辆车');
			},
			error:function(err){
				console.log(err);
			}
		});
		
}

$(function(){
	var storage=window.localStorage;
	storage.clear();//清空localstorage
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
		$('#userInfo').html('<h1>'+$.cookie('usercookie')+',登录成功</h1>');
		var storage=window.localStorage;
		storage.userName=$.cookie('usercookie');
		$("#StartDate").datepicker({
			dateFormat:'yy-mm-dd',
			minDate:0,
			maxDate:20
		});
		var timer=null;
		$("#Search").click(function(){
//			clearTimeout(timer);
//			timer=setTimeout(function(){console.clear();
			if($('#StartStation').val()!=0 && $("#EndStation").val()!=0){
				var Start_Station=$("#StartStation").val();
				var End_Station=$("#EndStation").val();
				var Start_Date=$("#StartDate").val();
//				if(StartStation !="" && EndStation!="" && StartDate!=""){
				var inf={"doString":"Search","StartStation":Start_Station,"EndStation":End_Station,"StartDate":Start_Date};
//					console.log(inf);
					$.ajax({
						type:'GET',
						url:'../SearchServlet',
						dataType:'json',
						data:inf,
						success:function(data){
							
							console.log(data);
							$("#table").html('<table id="dataTable" class="table table-striped table-bordered" style="text-align:center;halign:center" ><tbody></tbody></table>');
							$("#dataTable").DataTable({
								searching:false,
								paging:false,
								ordering:false,
								destroy:true,
								orderable:false,
								bAutoWidth:true,
								info: false,
								"data":data,
								"columns":[
									{data:"trainName",title:"车次"},
									{data:"station_name",title:"出发站"},
									{data:"endStation",title:"到达站"},
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
								columnDefs:[
									{
										targets:-1,
										"render":function(data,type,full,meta){
											return data='<button class="btn btn-primary" onclick="yuding(this)">预订</button>';
										},
									}
								]
							});
						},
						error:function(err){
								console.log(err); 
						}
					});
//				}
			}else{
				if($('#StartStation').val()==0 && $("#EndStation").val()==0){
					alert('请选择站点');
				}
				else if($('#StartStation').val()==0){
					alert('请选择起始站点');
				}else{
					alert('请选择目的站点');
				}
			}
			
//			},1000);
		});
	}else{
		alert("用户未登录");
		window.location.href = "/Train/login/login.html";
	}
	
	$("#homepage").click(function(){
		window.location.href = "/Train/index/Homepage.html";
	});
	
	$.ajax({
		type:'get',
		url:'../SearchServlet',
		datatype:'json',
		data:{"doString":"GetStartStation"},
		success:function(data){
			$("#StartStation").prepend('<option value="0">请选择</option>');
			for(var i=0;i<=Object.keys(data).length - 1;i++){
				$('#StartStation.selectpicker').append("<option value="+data[i].station_name+">"+data[i].station_name+"</option>");
			}
			$("#StartStation").selectpicker('refresh');
		},
		error:function(err){
			alert('查询车站失败')
			console.log(err);
		},
	});
	
	
//	$("#EndStation").focus(function(){
//		var startstation=$("#StartStation").val();
//		alert(startstation);
//		var data={"doString":"EndStation","startstation":startstation};
		$.ajax({
			type:'get',
			url:'../SearchServlet',
			datatype:'json',
			data:{"doString":"EndStation"},
			success:function(data){
				console.log(data);
				$("#EndStation").prepend('<option value="0">请选择</option>');
				for(var i=0;i<=Object.keys(data).length - 1;i++){
					$('#EndStation.selectpicker').append("<option value="+data[i].station_name+">"+data[i].station_name+"</option>");
				}
				$("#EndStation").selectpicker('refresh');
			},
			error:function(err){
				alert('查询车站失败')
				console.log(err);
			}
		});
//	});
		$("#StartStation")[0].selected=true;
		$("#EndStation")[0].selected=true;
})