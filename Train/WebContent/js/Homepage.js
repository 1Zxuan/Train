/**
 * 
 */


function tuiding(this1){
	if(confirm("是否退票")){
		var row=$(this1).parents('tr')[0];
		var data=$("#dataTable").dataTable().fnGetData(row);//获取选中行数据
		var userName=data.userName;
		var startstation=data.startstation;
		var endstation=data.endstation;
		var trainName=data.TrainName;
		var SeatName=data.SeatType;
		var inf={"doString":"Unsubscribe","userName":userName,"startstation":startstation,
				"endstation":endstation,"trainName":trainName,"SeatName":SeatName};
		$.ajax({
			type:'get',
			url:'../TicketServlet',
			dataType:'json',
			data:inf,
			success:function(data){
//				alert(data);
				if(data==true){
					alert('退票成功');
					window.location.href = "/Train/index/Homepage.html";
				}else if (data==false){
					alert('无票可退');
				}
			},
			error:function(err){
				console.log(err);
				alert('退票失败');
			}
		});
	}else{
		
	}
	
}

function gaiqian(this1){
	if(confirm("是否改签")){
		var row=$(this1).parents('tr')[0];
		var data=$("#dataTable").dataTable().fnGetData(row);//获取选中行数据
		var userName=data.userName;
		var trainName=data.TrainName;
		var start=data.startstation;
		var end=data.endstation;
		var data={"doString":"pdgaiqian","userName":userName,"trainName":trainName};
		console.log(data);
		$.ajax({
			type:'get',
			url:'../TicketServlet',
			dataType:'json',
			data:data,
			success:function(res){
				if(res){
					var storage=window.localStorage;
					storage.GS=start;
					storage.GE=end;
					storage.GT=trainName;
					window.location.href = "/Train/index/gaiqian.html";	
				}else{
					alert('只允许改签一次，该票已改签');
				}
			},
			error:function(err){
				console.log('查询是否可以改签出错');
			}
			
		});
		
	}else{
		
	}
}
$(function(){
	if(!window.localStorge){
		var storage=window.localStorage;
		var userName=storage.userName;
		$("#personalinfo").html(('<h1>'+userName+'车票信息</h1>'));
		var inf={"doString":"HomepageTicketInfo","userName":userName };
		$.ajax({//页面开启自动查询已购车票
			type:'get',
			url:'../TicketServlet',
			dataType:'json',
			data:inf,
			success:function(data){
				console.log(data);
				if(data!=null){
					storage.startstation=data.startstation;
					storage.endstation=data.endstation;
					storage.trainName=data.TrainName;
					storage.SeatName=data.SeatType;
					$("#table").html('<table id="dataTable" class="table table-striped table-bordered" style="text-align:center;halign:center"><tbody></tbody></table>');
					$("#dataTable").DataTable({
						searching:false,
						paging:false,
						ordering:false,
						destroy:true,
						orderable:false,
						bAutoWidth:true,
						info: false,
						data:data,
						columns:[
							{data:null,title:"用户名"},
							{data:null,title:"身份证"},
							{data:null,title:"手机号码"},
							{data:null,title:"车次"},
							{data:null,title:"起始站"},
							{data:null,title:"到达站"},
							{data:null,title:"发车时间"},
							{data:null,title:"到站时间"},
							{data:null,title:"座位类型"},
							{data:null,title:"其他"},
						],
						columnDefs:[
							{
								targets:0,
								render:function(data,type,full,meta){
									return '<small>'+data.userName+'</small>';
								}
							},{
								targets:1,
								render:function(data,type,full,meta){
									return '<small>'+data.IDCard+'</small>';
								}
							},{
								targets:2,
								render:function(data,type,full,meta){
									return '<small>'+data.phone+'</small>';
								}
							},{
								targets:3,
								render:function(data,type,full,meta){
									return '<small>'+data.TrainName+'</small>';
								}
							},{
								targets:4,
								render:function(data,type,full,meta){
									return '<small>'+data.startstation+'</small>';
								}
							},{
								targets:5,
								render:function(data,type,full,meta){
									return '<small>'+data.endstation+'</small>';
								}
							},{
								targets:6,
								render:function(data,type,full,meta){
									return '<small>'+data.starttime+'</small>';
								}
							},{
								targets:7,
								render:function(data,type,full,meta){
									return '<small>'+data.endtime+'</small>';
								}
							},{
								targets:8,
								render:function(data,type,full,meta){
									return '<small>'+data.SeatType+'</small>';
								}
							},{
								targets:-1,
								render:function(data,type,full,meta){
									return '<button class="btn btn-primary" onclick="tuiding(this)">退订</button>&nbsp;<button class="btn btn-primary" onclick="gaiqian(this)">改签</button>';
								}
							}
						]
					});
				}else{
					$("#table").html('<font color="red"><h1>没有订票信息!</h1></font>');
				}
			},
			error:function(err){
				console.log(err);
				alert("无法发起查询车票请求")
			}
			
		});
	}else{
		alert("浏览器不支持localStorge,无法继续!");
	}
	
	
	
	
	$("#goupiao").click(function(){
		window.location.href = "/Train/index/index.html";
	});
	$("#tuichu").click(function(){
		$.cookie("usercookie",'',{path:"/"});
		window.location.href = "/Train/login/login.html";
	});
});