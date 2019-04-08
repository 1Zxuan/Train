/**
 * 
 */
window.onlocad=function(){
	$('.disable-example').prop('disabled', false);
	$('.disable-example').selectpicker('refresh');
}
var Seat,Cphone=1;	//购买时用于记录选中的座位;
var storage=window.localStorage;


function ST(){
	var options=$("#ST option:selected"); 
	Seat=options.val();
	if((storage.trainName).substr(0,1)=='D'){
		jg_D(storage.station_no);
	}else if((storage.trainName).substr(0,1)=='T'){
		jg_T(storage.station_no);
	}else if ((storage.trainName).substr(0,1)=='Z'){
		jg_Z(storage.station_no);
	}	
}


function huoquST(){
	var options=$("#ST option:selected"); 
	var Seatname=options.text();
	return Seatname;
}



function Checkphone(){
		var PhoneNum=$("#phone").val();
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|17[0-9]{1}|(18[0-9]{1}))+\d{8})$/; 
		if(!myreg.test(PhoneNum)){
			alert("手机号不合法,请重新输入");
			$("#phone").val('');
			Cphone=0;
		}else{
			Cphone=1;
		}
}

function gq(this1){
	ST();
	if(Cphone==1){
		var row=$(this1).parents('tr')[0];
		var data=$("#dataTable").dataTable().fnGetData(row);
		var userName=storage.userName;	//用户名
		var Befor_trianName=storage.GT;
		var trainName=data.trainName;
		var startstation=data.startstation;
		var endstation=data.endstation;
		var tourist=data.tourist;
		var IDCard=data.IDCard;
		var phone=data.phone;
		var SeatName=huoquST();
		var SeatName_=SeatName.indexOf('余票(');
		SeatName=SeatName.substr(0,SeatName_);
		if(SeatName=="一等座"){
			SeatName="FirstClass";
		}else if(SeatName=="二等座"){
			SeatName="SecondClass";
		}else if(SeatName=="无座"){
			SeatName="NoSeat"
		}else if(SeatName=="软卧"){
			SeatName="SoftBerth";
		}else if(SeatName=="硬卧"){
			SeatName="HardBerth";
		}else if(SeatName=="硬座"){
			SeatName="HardSeat";
		}else if(SeatName=="高级软卧"){
			SeatName="high_GradeSB";
		}
		var inf={"doString":"Buy2","userName":userName,"trainName":trainName,"startstation":startstation,"endstation":endstation,"tourist":tourist,"IDCard":IDCard,
				"phone":phone,"start_time":storage.start_time,"arrive_time":storage.arrive_time,"station_no":storage.station_no,"SeatName":SeatName,"gaiqian":"1","Befor_trianName":Befor_trianName
		};
		$.ajax({
			type:'post',
			url:'../BuyServlet',
			dataType:'json',
			data:inf,
			success:function(data){
				if(data){
					if(confirm("改签成功,单击确定跳转至个人界面查看购票信息")){
						window.location.href = "/Train/index/Homepage.html";
					}else{
						
					}
				}else{
					alert("出票失败,请确保未订票")
				}
			},
			error:function(err){
				alert('发起改签出错');
				console.log(err);
			}
		});
	}else{
		alert("请输入正确的手机号码");
	}
		
}

$(function(){
	if(!window.localStorge){
		if(storage.userName!=null && storage.trainName!=null && storage.startstation!=null && storage.endstation!=null){
			var userName=storage.userName;	//用户名
			var trainName=storage.trainName;			//车次
			$("#traininfo").html(('<h1>'+trainName+'车票信息</h1>'));
			var startstation=storage.startstation;		//出发站
			var endstation=storage.endstation;			//到达站
//			storage.clear();//清空localstorage
			var inf={"doString":"Buyinfo","userName":userName,"trainName":trainName,"startstation":startstation,"endstation":endstation};
			$.ajax({
				type:'post',
				url:'../BuyServlet',
				dataType:'json',
				data:inf,
				success:function(data){
					storage.start_time=data.start_time;
					storage.arrive_time=data.arrive_time;
					storage.station_no=data.station_no;
					$("#table").html('<table id="dataTable" class="table table-striped table-bordered" style="text-align:center;halign:center" ><tbody></tbody></table>');
					$("#dataTable").DataTable({
						searching:false,
						paging:false,
						ordering:false,
						destroy:true,
						orderable:false,
						bAutoWidth:true,
						info: false,
						data:[data],
						columns:[
							{data:null,title:"车次"},
							{data:null,title:"票种"},
							{data:null,title:"历时"},
							{data:null,title:"历站数"},
							{data:null,title:"证件号码"},
							{data:null,title:"手机号码"},
							{data:null,title:"席别"},
							{data:null,title:"票价"},
							{data:null,title:"改签"},
							{data:null,title:"取消"}
							],
							columnDefs:[{
								targets:0,
								render:function(data,type,full,meta){
									return '<small>'+data.trainName+'</small>';
								}
							},{
								targets:1,
								render:function(data,type,full,meta){
									switch (data.tourist){
										case 0:return '<small>成人</small>';break;
										case 1:return '<small>儿童</small>';break;
										case 2:return '<small>学生</small>';break;
										default:return '<small>伤残军人、伤残人民警察</small>';break;
									}
								},
							},{
								targets:2,
								render:function(data,type,full,meta){
									return '<small>'+data.arrive_day_diff+'</small>';
								}
							},{
								targets:3,
								render:function(data,type,full,meta){
									return '<small>'+data.station_no+'</small>';
								}
							},{
								targets:4,
								render:function(data,type,full,meta){
									return '<small>'+data.IDCard+'</small>';
								}
							},{
								targets:5,
								render:function(data,type,full,meta){
									return '<input id="phone" onblur="Checkphone()" type="text" value="'+data.phone+'"  size="10"/>';
								}
							},{
								targets:8,
								render:function(data,type,full,meta){
									return '<button class="btn btn-primary" onclick="gq(this)">改签</button>';
								}
							},
							{
								targets:6,
								render:function(data,type,full,meta){
									if((data.trainName).substr(0,1)=='D'){
										return data='<select id="ST" onchange="ST()">'+
											'<option value="1" selected = "selected">'+'一等座余票('+data.FirstClass+')</option>'+
											'<option value="2" >'+'二等座余票('+data.SecondClass+')</option>'+
											'<option value="-1" >'+'无座余票('+data.NoSeat+')</option>'+
											'</select>';
									}else if((data.trainName).substr(0,1)=='T'){
										return data='<select id="ST" onchange="ST()">'+
										'<option value="0"selected = "selected" >'+'软卧余票('+data.SoftBerth+')</option>'+
										'<option value="1" >'+'硬卧余票('+data.HardBerth+')</option>'+
										'<option value="2" >'+'硬座余票('+data.HardSeat+')</option>'+
										'<option value="-1" >'+'无座余票('+data.NoSeat+')</option>'+
										'</select>';
									}else if((data.trainName).substr(0,1)=='Z'){
										return data='<select id="ST" onchange="ST()">'+
										'<option value="0" selected = "selected">'+'高级软卧余票('+data.high_GradeSB+')</option>'+
										'<option value="1" >'+'软卧余票('+data.SoftBerth+')</option>'+
										'<option value="2" >'+'硬卧余票('+data.HardBerth+')</option>'+
										'<option value="3" >'+'硬座余票('+data.HardSeat+')</option>'+
										'<option value="-1" >'+'无座余票('+data.NoSeat+')</option>'+
										'</select>';
									}
								}
							},
							{//票价
								targets:7,
//								render:function(data,type,full,meta){
//									if((data.trainName).substr(0,1)=='D'){
//										return jg_D(data.station_no);
//									}else if((data.trainName).substr(0,1)=='T'){
//										return jg_T(data.station_no);
//									}else if((data.trainName).substr(0,1)=='Z'){
//										return jg_Z(data.station_no);
//									}
//								}
								render:function(data,type,full,meta){
									return '<small>未实现</small>';
								}
							},{
								targets:-1,
								render:function(data,type,full,meta){
									return '<button class="btn btn-primary" onclick="fh(this)">取消</button>';
								}
							}
							]
						});
				},
				error:function(err){
					console.log(err);
					alert("请求失败");
				}
			});
		}else{
			alert('请重新登录!');
			$.cookie("usercookie",'',{path:"/"});
			window.location.href = "/Train/login/login.html";
		}
	}
	else{
		alert('浏览器不支持localStorage,无法继续!');
	}
});


/***
 * 返回
 * */
function fh(){
	var storage=window.storage;
	stroage.GE="";
	stroage.GS="";
	storage.GT="";
	window.location.href = "/Train/index/Homepage.html";
}

/**
 * 
 * 价格控制
 * 
 * */
function jg_D(station_no){
	return '<small>Seat:'+huoquST()+'</small>';
}
function jg_T(station_no){
	return '<small>Seat:'+huoquST()+'</small>';
}
function jg_Z(station_no){
	return '<small>Seat:'+huoquST()+'</small>';
}

