/**
 * 登录js
 */


var yzm=false;

	$(function(){
		yz();
		$("#login").click(function(){//点击登录
			var user=$("#user").val();
			var pwd=$("#password").val();
			if(user!="" && pwd != "" && yzm==true ){
				pwd=hex_md5(pwd);//Md5加密密码
					$.ajax({
						type:'GET',
						url:'../CheckServlet',
						dataType:'json',
						data:{"doString":"Login","user":user,"password":pwd},
						success:function(data){
							//console.log(data);
							if(data!=null){
								if(data.type==0){
									var date=new Date();
//								date.setTime(date.getTime()+5*60*1000);
								/**
								 *保存用户登录信息 cookie 
								 **/
									if(confirm("登录成功,点击确定按钮跳转到主页")){
										$.cookie('usercookie',data.userName,{expires:7,path:"/"});
										window.location.href = "/Train/index/index.html";
									}else{
//										$("#code").strCode();
										$("#inputCode").val("");
										$("#user").val("");
										$("#password").val("");
										}
								}else if(data.type==1){
									$.cookie('usercookie',data.userName,{expires:7,path:"/"});
									window.location.href = "/Train/index/Root.html";
								}
							}else {
								alert("登录失败！");
								$("#user").val("");
								$("#password").val("");
								$("#inputCode").val("");
							}
						},
						error:function(err){
							alert("登录发生错误");
						}
					});
			}else if(user!="" && pwd != "" && yzm==false){
				alert("验证码错误");
			}
			else if(user=="" || pwd==""){
				alert("信息不完整！");
			}
		});
		
		$("#registered").click(function(){
			//在原有窗口打开
		    window.location.href = "/Train/login/registered.html";
		    //打开新的窗口
		    //window.open("http://www.baidu.com");
		});
	});
	
	function yz(){
		var inp = document.getElementById('inputCode');
	    var code = document.getElementById('code');
	    var submit = document.getElementById('login');
	    var c = new KinerCode({
	        len: 4,//需要产生的验证码长度
	        //        chars: ["1+2","3+15","6*8","8/4","22-15"],//问题模式:指定产生验证码的词典，若不给或数组长度为0则试用默认字典
	        chars: [
	            1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
	            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
	        ],//经典模式:指定产生验证码的词典，若不给或数组长度为0则试用默认字典
	        question: false,//若给定词典为算数题，则此项必须选择true,程序将自动计算出结果进行校验【若选择此项，则可不配置len属性】,若选择经典模式，必须选择false
	        copy: false,//是否允许复制产生的验证码
	        bgColor: "#000000",//背景颜色[与背景图任选其一设置]
	//        bgImg: "bg.jpg",//若选择背景图片，则背景颜色失效
	        randomBg: false,//若选true则采用随机背景颜色，此时设置的bgImg和bgColor将失效
	        inputArea: inp,//输入验证码的input对象绑定【 HTMLInputElement 】
	        codeArea: code,//验证码放置的区域【HTMLDivElement 】
	        click2refresh: true,//是否点击验证码刷新验证码
	        false2refresh: true,//在填错验证码后是否刷新验证码
	        validateObj: login,//触发验证的对象，若不指定则默认为已绑定的输入框inputArea
	        validateEven: "click",//触发验证的方法名，如click，blur等
	        validateFn: function (result, code) {//验证回调函数
	            if (result) {
	                yzm=true;
	            } else {
	//                if (this.opt.question) {
	//                    alert('验证失败:' + code.answer);
	//                } else {
	//                    alert('验证失败:' + code.strCode);
	//                    alert('验证失败:' + code.arrCode);
	//                }
	            	yzm=false;
	            }
	        }
	    });
	}

//}
	
	
	
	
//function getFormData($form) {
//    var unindexed_array = $form.serializeArray();
//    var indexed_array = {};
//
//    $.map(unindexed_array, function (n, i) {
//      indexed_array[n['name']] = n['value'];
//    });
//    return myencodeURI(indexed_array);
//}
//function myencodeURI(str){  
//	return  encodeURIComponent(str);  
//} 