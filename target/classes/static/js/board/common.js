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
	if (confirm('게시글을 등록하시겠습니까?')) {
		let data = {
			'title': $('input[name="title"]').val(),
			'content': $("textarea[name='content']").val().replace(/\n/g, "<br>"),
			'boardDev': $('select[name="boardDev"] option:selected').val(),
			'boardClass': $('select[name="boardClass"] option:selected').val()
		};

		$.ajax({
			url: '/std/boards',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=utf-8',
			method: 'POST'
		}).done(function() {
			if($('select[name="boardDev"] option:selected').val() == 'CD018'){
				location.href = "/std/boards";
			}else{
				location.href = "/std/imageBoards";
			}
		});
	}
}

function modifyBoard(data) {
	let boardNo = data.boardNo;
	let boardDev = data.boardDev;
	if (confirm('해당 수정내용을 등록하시겠습니까?')) {
		$.ajax({
			url: '/std/boards',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=utf-8',
			method: 'put'
		}).done(function(data) {
			location.href = boardDev+'/'+boardNo;
		});
	}
}