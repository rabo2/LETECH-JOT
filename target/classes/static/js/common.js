var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
window.onload = function() {
	printNavbar(1, '#oneDepth');
	let navbar = $('#navbarContent');

	navbar.on('click', '.lvl1', function(event) {
		if ($(this).attr('href') == '#') {
			let id = $(this).attr("id");
			printNavbar(2, '#' + id + '+ ul.dropdown-menu', id);
		} else {

		}
		//$(this).siblings("ul.dropdown-menu").toggleClass("show");
	});

	navbar.on('mouseenter', '.lvl2', function(event) {
		if ($(this).attr('href') == '#') {
			let id = $(this).attr("id");
			printNavbar(3, '#' + id + '+ ul.dropdown-menu', id);
			$(this).siblings("ul.dropdown-menu").toggleClass("show");
		}
	});

	$('#navbarContent').on("mouseenter", "ul.dropdown-menu [data-toggle='dropdown']", function(event) {
		event.preventDefault();
		event.stopPropagation();

		$(this).parents().siblings('.dropdown-submenu').find('a.dropdown-item').siblings('ul.dropdown-menu').removeClass("show");

		if (!$(this).next().hasClass('show')) {
			$(this).parents('.dropdown-menu').first().find('.show').removeClass("show");
		}
		$(this).parents('li.nav-item.dropdown.show').on('hidden.bs.dropdown', function(e) {
			$('.dropdown-submenu .show').removeClass("show");
		});
	});

	$('#summernote').summernote({
		height: 300,                 // 에디터 높이
		minHeight: null,             // 최소 높이
		maxHeight: null,             // 최대 높이
		focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
		lang: "ko-KR",					// 한글 설정
		placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정
		disableResizeEditor: true
	});

}

function printNavbar(lvl, target, upCd) {
	$.ajax({
		url: contextPath + '/navbar/' + lvl + '/' + upCd,
		contentType: "application/json; UTF-8;",
		type: 'GET',
		success: function(data) {
			if (data.length < 1) {
				$(target).toggleClass('show');
			}
			html = "";
			for (let i = 0; i < data.length; i++) {
				if (lvl == 1) {
					html += '<!-- Level one dropdown -->';
					html += '<li class="nav-item dropdown">';
					if (data[i].uri == '#') {
						html += '	<a id="' + data[i].menuCd + '" href="' + data[i].uri + '" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link lvl1" data-bs-auto-close="true">' + data[i].menuNm + '</a>';
					} else {
						html += '<a class="nav-link lvl1" id="' + data[i].menuCd + '" href="' + contextPath + data[i].uri + '">' + data[i].menuNm + '</a>';
					}
					html += '	<ul aria-labelledby="dropdownMenu1" class="dropdown-menu border-0 shadow">';
					html += '	</ul>';
					html += '</li>';
				} else if (lvl == 2) {
					html += '<li class="dropdown-submenu">';
					if (data[i].uri == '#') {
						html += '	<a id="' + data[i].menuCd + '" href="' + contextPath + data[i].uri + '" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="lvl2 dropdown-item" data-bs-auto-close="true">' + data[i].menuNm + '</a>';
					} else {
						html += '<a class="lvl2 dropdown-item" id="' + data[i].menuCd + '" href="' + contextPath + data[i].uri + '">' + data[i].menuNm + '</a>';
					}

					html += '	<ul aria-labelledby="dropdownMenu2" class="dropdown-menu border-0 shadow">';
					html += '	</ul>';
					html += '</li>';
				} else if (lvl == 3) {
					html += '<li>';
					html += '	<a tabindex="-1" href="#" class="dropdown-item show">' + data[i].menuNm + '</a>';
					html += '</li>';
				}
			}
			$(target).html(html);
		}
	})
}


function removeBoard(data) {
	if (confirm('해당 게시글을 정말 삭제하시겠습니끼?')) {
		$.ajax({
			url: '/std/boards',
			data: JSON.stringify(data),
			method: 'DELETE',
			contentType: 'application/json; charset=utf-8'
		}).done(function() {
			location.href = "/std/boards";
		});
	}
}



//댓굴 등록, 출력
function printReply(boardNo, pageNum, target) {
	$.ajax({
		url: '/std/replies/' + boardNo + '/' + pageNum + '?target=' + encodeURIComponent(target),
		method: 'GET'
	}).done(function(fragment) {
		target = target.substring(target.indexOf("#"));
		$(target).replaceWith(fragment);
	})
}

function registReply(boardNo, content, target) {
	let data = {
		'boardNo': boardNo
		, 'content': content.replace(/\n/g, "<br>")
		, 'target': target
	};
	$.ajax({
		url: '/std/replies',
		data: JSON.stringify(data),
		method: 'POST',
		contentType: 'application/json; charst=utf-8',
	}).always(function() {
		$('#registReply').val('');
		printReply(boardNo, 1, target);
	});
}
function changeTextAreaReply(target, ord, boardNo) {
	target = target.parentElement.parentElement.parentElement.parentElement;
	let comment = target.nextElementSibling.children[0].innerText;

	let page = document.querySelector('li.page-item.active').children[0].innerText;

	target.parentElement.innerHTML = `
			<textarea wrap="hard" style="resize: none;" rows="3" cols="145" class="form-control">${comment}</textarea>
			<div class="row">
				<div class="col-10"></div>
				<div class="col-2" style="text-align:right;">
					<button class="replyModifyBtn btn btn-sm btn-success" onclick="javascript:modifyReply(this,${boardNo}, ${ord}, ${page}, 'board/detail :: #replyList')">등록</button>
					<button class="btn btn-sm btn-secondary" onclick="javascript:printReply(${boardNo}, 1, 'board/detail :: #replyList')">취소</button>
				</div>
			</div>
	`;
};

function modifyReply(event, boardNo, ord, page, target) {

	let content = event.parentElement.parentElement.previousElementSibling.value;

	let data = {
		'boardNo': boardNo
		, 'content': content.replace(/\n/g, "<br>")
		, 'target': target
		, 'ord': ord
	};

	$.ajax({
		url: '/std/replies',
		method: 'put',
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8'
	}).done(function() {
		printReply(boardNo, page, target);
	})

}

function removeReply(ord, boardNo, target) {
	let page = document.querySelector('li.page-item.active').children[0].innerText;
	let data = {
		'ord': ord
		, 'boardNo': boardNo
		, 'delYn': 0
		,'target' : target
	};

	if (confirm('해당 댓글을 삭제하시겠습니까?')) {

		$.ajax({
			url: '/std/replies',
			method: 'delete',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=utf-8'
		}).done(function() {
			printReply(boardNo, page, target);
		});
	}

}