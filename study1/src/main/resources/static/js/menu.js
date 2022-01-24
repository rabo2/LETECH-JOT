var form = "";
var codVal = "";
var codList = "";
var flag = true;
var data = "";
var html = "";

window.addEventListener('load', function() {
	menuList('#menuList');
});

function menuList(target, menuCd) {
	$.ajax({
		url: 'navbar/list?menuCd=' + menuCd,
		method: 'GET'
	}).done(function(fragment) {
		$(target).replaceWith(fragment);
	});
}

function detailMenu(target, menuCd){
	$.ajax({
		url : 'navbar/detail?menuCd=' + menuCd,
		method : 'GET'
	}).done(function(fragment){
		$(target).replaceWith(fragment);
	})
}

function registMenu() {
	form = $('#registForm').serialize();

	$.ajax({
		url: 'navbar/regist',
		method: 'POST',
		data: form
	}).done(function(fragment) {
		$("#menuList").replaceWith(fragment);
		$('#registForm input').reset();
	});
}

function modifyMenu() {
	// 	if(!formCheck()){
	// 		return false;
	// 	};
	form = $('#registForm').serialize();
	$.ajax({
		url: 'navbar/modify',
		//		contentType: "application/json; UTF-8;",
		method: 'PUT',
		data: form
	}).done(function(fragment) {
		$("#codeList").replaceWith(fragment);
	});
}

function removeCode() {
	let codeVal = document.querySelector('#detailCode input[name="menuCd"]').value;

	if (confirm('정말 삭제하시겠습니까?')) {
		$.ajax({
			url: 'navbar/remove/' + codeVal,
			method: 'DELETE'
		}).done(function(fragment) {
			$("#codeList").replaceWith(fragment);
		});
	}
}