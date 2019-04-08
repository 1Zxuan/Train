/**
 * 
 */

function gaiqian(this1){
	var row=$(this1).parents('tr')[0];
	var data=$("#dataTable").dataTable().fnGetData(row);//获取选中行数据
	var userName=$.cookie('usercookie');	//用户名
	var trainName=data.trainName;
	var storage=window.localStorage;
	if(trainName==storage.GT){
		alert('不能改签此辆车');
	}else{
		var userName=$.cookie('usercookie');	//用户名
		if(!window.localStorge){
			storage.userName=userName;
			storage.trainName=trainName;
			storage.startstation=storage.GS;
			storage.endstation=storage.GE;
			window.location.href = "/Train/Buy/Buy_gaiqian.html";
		}else{
			alert('浏览器不支持localStorage,无法继续!');
		}
	}
}

$(function(){
	var storage=window.localStorage;
	$("#startstation").val(storage.GS);
	$("#endstation").val(storage.GE);
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
	
	$("#StartDate").datepicker({
		dateFormat:'yy-mm-dd',
		minDate:0,
		maxDate:20
	});
	
	$("#Search").click(function(){
		var Start_Station=$("#startstation").val();
		var End_Station=$("#endstation").val();
		var StartDate=$("StartDate").val();
		var inf={"doString":"Search","StartStation":Start_Station,"EndStation":End_Station,"StartDate":StartDate};
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
								return data='<button class="btn btn-primary" onclick="gaiqian(this)">改签</button>';
							},
						}
					]
				});
			},
			error:function(err){
					console.log(err); 
			}
		});
	});
	
	$("#quxiao").click(function(){
		var storage=window.localStorage;
		storage.GS="";
		storage.GE="";
		window.location.href = "/Train/index/Homepage.html";
	});
});