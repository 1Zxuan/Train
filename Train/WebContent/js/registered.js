/**
 * 
 * 注册js
 */
$(function(){
	var UN=false;	//用户名有效性
	var PW0=false;	//密码有效性
	var PW1=false;	//密码1有效性
	var ID=false;	//身份证有效性
	var N=false;	//姓名有效性
	var E=false;	//邮箱有效性
	var PN=false;	//手机号有效性
	
	$("#userName").blur(function(){								//验证是否已存在用户名
		var userName=$("#userName").val();
		var ch=/^[A-Za-z0-9]{2,10}$/;
		if(ch.test(userName)){		//验证是否满足命名规则
			if(userName!=""){		//发起验证
				$.ajax({
					type:'GET',
					url:'../CheckServlet',
					dataType:'json',
					data:{"doString":"CheckuserName","name":userName},
					success:function(data){
						if(data.msg=="False"){
							UN=false;
							$("#CheckuserName").html("用户名已存在,请重新输入");
						}else if(data.msg="True"){
							UN=true;
							$("#CheckuserName").html("");
						}/*else{
							UN=false;
							alert(data.msg);
						}*/
						
					},
					error:function(err){
						alert("用户名验证发生错误");
					}
				});
			}
		}else{
			$("#CheckuserName").html("用户名不符合命名规则,请重新输入");
		}
	});
	
	$("#password0").blur(function(){							//验证密码有效性
		var pw=$("#password0").val();
		var ch=/^[A-Za-z0-9]{2,10}$/;
		if(ch.test(pw)){
			PW0=true;
			$("#Checkpassword0").html("");
		}else{
			PW0=false;
			$("#Checkpassword0").html("密码不符合规则，请重新输入");
		}
		
	});
	
	$("#password1").blur(function(){
		var pw0=$("#password0").val();
		var pw1=$("#password1").val();
		if(pw0==pw1){
			PW1=true;
			$("#Checkpassword1").html("");
		}else{
			PW1=false;
			$("#Checkpassword1").html("两次密码输入不一致，请重新输入");
		}
	});
	
	$("#IDCard").blur(function(){								//验证身份证有效性
		var Validator = new IDValidator();
		var IDCard=$("#IDCard").val();
		var F=Validator.isValid(IDCard);
		if(!F){
			$("#CheckIDCard").html("身份证号码不合法,请重新输入");
			//$("#IDCard").focus();使身份证文本框获得焦点
			ID=false;
		}else{//进行身份证是否存在验证!
//			ID=true;
//			$("#CheckIDCard").html("");
			$.ajax({
				type:'GET',
				url:'../CheckServlet',
				dataType:'json',
				data:{"doString":"CheckIDCard","IDCard":IDCard},
				success:function(data){
					if(data.msg=="False"){
						ID=false;
						$("#CheckIDCard").html("身份证号码已存在,请重新输入");
					}else if (data.msg=="True"){
						ID=true;
						$("#CheckIDCard").html("");
					}
				},
				error:function(err){
					console.log(err);
					alert('身份证验证发生错误');
				}
			});
			
			
		}
	});
	
	$("#Name").blur(function(){									//验证姓名有效性
		var Name=$("#Name").val();
		var regName =/^[\u4e00-\u9fa5]{2,4}$/;
		if(!regName.test(Name)){
			$("#CheckName").html("姓名不合法,请重新输入");
			N=false;
		}else{
			N=true;
			$("#CheckName").html("");
		}
	});
	
	$("#Email").blur(function(){
		var em=$("#Email").val();
		var ch=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		if(ch.test(em)){
			E=true;
			$("#CheckEmail").html("");
		}else{
			E=false;
			$("#CheckEmail").html("邮箱输入错误，请重新输入");
		}
		
	});
	
	
	$("#PhoneNum").blur(function(){								//验证手机号有效性
		var PhoneNum=$("#PhoneNum").val();
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|17[0-9]{1}|(18[0-9]{1}))+\d{8})$/; 
		if(!myreg.test(PhoneNum)){
			$("#CheckPhoneNum").html("手机号不合法,请重新输入");
			PN=false;
		}else{
			PN=true;
			$("#CheckPhoneNum").html("");
		}
	});
	
	$("#insert").click(function(){
//		console.log(datajson);
		if(UN==true && PW0==true && PW1==true && ID==true && N==true && E==true && PN==true){ //可以注册
			var datajson={
					"doString":"Insert",
					"userName":$("#userName").val(),
					"password":hex_md5($("#password0").val()),
					"IDCard":$("#IDCard").val(),
					"Name":$("#Name").val(),
					"Email":$("#Email").val(),
					"PhoneNum":$("#PhoneNum").val(),
					"Type":$("#Type option:selected").val(),
			}
			console.log(datajson);
			$.ajax({
				type:'post',
				url:'../CheckServlet',
				dataType:'json',
				data:datajson,
				success:function(data){
					if(data.msg=="True"){
						
						if(confirm("注册成功,点击确定按钮跳转到登录页面"))
							window.location.href = "/Train/login/login.html";
						else{
							$("#userName").val("");
							$("#password0").val("");
							$("#password1").val("");
							$("#IDCard").val("");
							$("#Name").val("");
							$("#Email").val("");
							$("#PhoneNum").val("");
						}
					}
				},
				error:function(err){
					alert("注册时发送错误");
				}
			});
		}else{
			alert("请补全信息,并确保填写正确");
		}
	});
	
})
