var form = "";
var codVal = "";
var codList = "";
var flag = true;
var data = "";
var html = "";
function commonCodeList(target, comnCd) {
	$.ajax({
		url : 'commonCode/list/'+comnCd,
		method : 'GET'
	}).done(function(fragment) {
		$(target).replaceWith(fragment);
	});
}

function registCode() {
	form = $('#registForm').serialize();
	
	$.ajax({
		url : 'commonCode/regist',
//		contentType: "application/json; UTF-8;",
		method : 'POST',
		data : form
	}).done(function(fragment) {
		$("#codeList").replaceWith(fragment);
		$('#registForm input').reset();
	});
}

function modifyCode() {
// 	if(!formCheck()){
// 		return false;
// 	};
	form = $('#registForm').serialize();
	$.ajax({
		url : 'commonCode/modify',
//		contentType: "application/json; UTF-8;",
		method : 'PUT',
		data : form
	}).done(function(fragment) {
		$("#codeList").replaceWith(fragment);
	});
}

function removeCode() {
	codeVal = document.querySelector('#detailCode input[name="comnCd"]').value;
	
	if(confirm('정말 삭제하시겠습니까?')){
		$.ajax({
			url : 'commonCode/remove/'+codeVal,
			method : 'DELETE'
		}).done(function(fragment) {
			$("#codeList").replaceWith(fragment);
		});
	}
}


function printNavbar(level,target,upcode){
	console.log(level, target, upcode);
	$.ajax({
		url : 'commonCode/navbar/'+level+'/'+upcode,
		contentType: "application/json; UTF-8;",
		data : JSON.stringify(data),
		type : 'GET',
		success : function(data){
			html = "";
			if(level == 1){
				html += '<ul class="navbar-nav mr-auto" id="oneDepth">';
				for(let i = 0; i < data.length; i++){
					html += '		<!-- Level one dropdown -->';
					html += '		<li class="nav-item dropdown">';
					html += '<a onclick="printNavbar(2, \'#'+data[i].comnCd+'\',\''+data[i].comnCd+'\')" id="dropdownMenu1 '+data[i].comnCd+'" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link">'+data[i].cdNm+'</a>';
					html += '		</li>';
				}
				html += '</ul>';
			}else if (level == 2){
				html += '<ul aria-labelledby="dropdownMenu1" class="dropdown-menu border-0 shadow">                                                                          ';
				for(let i = 0; i < data.length; i++){
					html += '		<li class="dropdown-submenu">                                                                                                                ';
					html += '			<a onclick="printNavbar(2,\'#'+data[i].comnCd+'\',\''+data[i].comnCd+'\')" id="dropdownMenu2" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="dropdown-item">'+data[i].cdNm+'</a>';
					html += '		</li>';
				}
				html += '</ul>';
			}
			console.log(html);
			$(target).append(html);
		}
	})
}

// function formCheck(){
// 	codeVal = document.querySelector('#comnCd').value;
// 	codList = document.getElementsByClassName('comnCd');
	
// 	for(let i = 0; i < codList.length; i++){
// 		if(codList[i].innerHTML == codeVal){
// 			alert('이미 존재하는 코드 입니다');
// 			flag = false;
// 		}
// 	}
	
// 	form = document.getElementsByClassName('form-control');
	
// 	for(let i = 0; i < form.length; i++){
// 		if(!form[i].value && (i != 3 || i != 6)){
// 			alert(i+'번째 값을 전부 입력하세요');
// 			flag =  false;
// 			return flag;
// 		}
// 	}
	
// 	return flag;
// }