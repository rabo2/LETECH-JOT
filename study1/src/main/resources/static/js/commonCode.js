var form = "";
var codVal = "";
var codList = "";
var flag = true;
var data = "";
var html = "";

window.onload = function() {
	commonCodeList('#codeList');
	printNavbar(1, '#oneDepth');

	let navbar = $('#navbarContent');

	navbar.on('click', '.lvl1', function(event) {
		let id = $(this).attr("id");
		printNavbar(2, '#' + id + '+ ul.dropdown-menu', id);
		//$(this).siblings("ul.dropdown-menu").toggleClass("show");
	});

	navbar.on('mouseenter', '.lvl2', function(event) {
		let id = $(this).attr("id");
		printNavbar(3, '#' + id + '+ ul.dropdown-menu', id);
		$(this).siblings("ul.dropdown-menu").toggleClass("show");
	});



	$('#navbarContent').on("mouseenter","ul.dropdown-menu [data-toggle='dropdown']", function (event) {
		event.preventDefault();
		event.stopPropagation();
	
		$(this).parents().siblings('.dropdown-submenu').find('a.dropdown-item').siblings('ul.dropdown-menu').removeClass("show");
		
		if (!$(this).next().hasClass('show')) {
			$(this).parents('.dropdown-menu').first().find('.show').removeClass("show");
		}
		$(this).parents('li.nav-item.dropdown.show').on('hidden.bs.dropdown', function (e) {
			$('.dropdown-submenu .show').removeClass("show");
		});
		
	});
}


function commonCodeList(target, comnCd) {
	$.ajax({
		url: 'commonCode/list/' + comnCd,
		method: 'GET'
	}).done(function(fragment) {
		$(target).replaceWith(fragment);
	});
}

function registCode() {
	form = $('#registForm').serialize();

	$.ajax({
		url: 'commonCode/regist',
		//		contentType: "application/json; UTF-8;",
		method: 'POST',
		data: form
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
		url: 'commonCode/modify',
		//		contentType: "application/json; UTF-8;",
		method: 'PUT',
		data: form
	}).done(function(fragment) {
		$("#codeList").replaceWith(fragment);
	});
}

function removeCode() {
	codeVal = document.querySelector('#detailCode input[name="comnCd"]').value;

	if (confirm('정말 삭제하시겠습니까?')) {
		$.ajax({
			url: 'commonCode/remove/' + codeVal,
			method: 'DELETE'
		}).done(function(fragment) {
			$("#codeList").replaceWith(fragment);
		});
	}
}


function printNavbar(level, target, upcode) {
	$.ajax({
		url: 'commonCode/navbar/' + level + '/' + upcode,
		contentType: "application/json; UTF-8;",
		data: JSON.stringify(data),
		type: 'GET',
		success: function(data) {
			if(data.length < 1){
				$(target).toggleClass('show');
			}
			html = "";
			for (let i = 0; i < data.length; i++) {
				if (level == 1) {
					html += '<!-- Level one dropdown -->';
					html += '<li class="nav-item dropdown">';
					html += '	<a id="' + data[i].comnCd + '" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link lvl1" data-bs-auto-close="true">' + data[i].cdNm + '</a>';
					html += '	<ul aria-labelledby="dropdownMenu1" class="dropdown-menu border-0 shadow">';
					html += '	</ul>';
					html += '</li>';
				} else if (level == 2) {
					html += '<li class="dropdown-submenu">';
					html += '	<a id="' + data[i].comnCd + '" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="lvl2 dropdown-item" data-bs-auto-close="true">' + data[i].cdNm + '</a>';
					html += '	<ul aria-labelledby="dropdownMenu2" class="dropdown-menu border-0 shadow">';
					html += '	</ul>';
					html += '</li>';
				} else if (level == 3) {
					html += '<li>';
					html += '	<a tabindex="-1" href="#" class="dropdown-item show">' + data[i].cdNm + '</a>';
					html += '</li>';
				}
			}
				$(target).html(html);
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