var form = "";
var codVal = "";
var codList = "";
var flag = true;
var data = "";
var html = "";
window.addEventListener('load', function() {
	commonCodeList('#codeList');
});

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
		url: 'regist',
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
	let codeVal = document.querySelector('#detailCode input[name="comnCd"]').value;

	if (confirm('정말 삭제하시겠습니까?')) {
		$.ajax({
			url: 'remove/' + codeVal,
			method: 'DELETE'
		}).done(function(fragment) {
			$("#codeList").replaceWith(fragment);
		});
	}
}