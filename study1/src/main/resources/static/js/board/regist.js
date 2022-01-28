window.addEventListener('load', function() {
	printBoardSelet('board/regist :: #boardDev' , 'CD017');
	printBoardSelet('board/regist :: #boardClass' , 'CD020');
	
	document.getElementById('registBtn').addEventListener('click', function(e) {
		if (confirm('게시글을 등록하시겠습니까?')) {
			let data = {
				'title': $('input[name="title"]').val(),
				'content': $("textarea[name='content']").val().replace(/\n/g, "<br>"),
				'boardDev' : $('select[name="boardDev"] option:selected').val(),
				'boardClass' : $('select[name="boardClass"] option:selected').val()
			};

			$.ajax({
				url: 'regist',
				data: JSON.stringify(data),
				contentType: 'application/json; charset=utf-8',
				method: 'POST'
			}).done(function() {
				location.href = "/std/board";
			});
		}
	});
})