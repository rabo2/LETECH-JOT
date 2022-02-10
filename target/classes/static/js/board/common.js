/**
 * 
 */
function printBoardSelect(target, upCd) {
	let uri = '/std/commonCode/upCode?target=' + encodeURIComponent(target) + '&upCd=' + encodeURIComponent(upCd);
	$.ajax({
		url: uri,
		method: 'GET'
	}).done(function(fragment) {
		target = target.substring(target.indexOf('#'));
		$(target).replaceWith(fragment);
	})
}

function registBoard() {
	let boardDev = $('select[name="boardDev"] option:selected').val();
	if (confirm('게시글을 등록하시겠습니까?')) {
		let data = {
			'title': $('input[name="title"]').val(),
			'content': $("textarea[name='content']").val().replace(/\n/g, "<br>"),
			'boardDev': boardDev,
			'boardClass': $('select[name="boardClass"] option:selected').val()
		};

		$.ajax({
			url: '/std/boards',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=utf-8',
			method: 'POST'
		}).done(function() {
			console.log(boardDev);
		});
	}
}

function modifyBoard(data) {
	let boardNo = data.boardNo;
	let boardDev = data.boardDev;
	if (confirm('해당 수정내용을 등록하시겠습니까?')) {
		$.ajax({
			url: '',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=utf-8',
			method: 'put'
		}).done(function(data) {
			location.href = boardDev + '/' + boardNo;
		});
	}
}