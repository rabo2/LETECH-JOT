var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
window.onload = function() {
	printNavbar(1, '#oneDepth');

	let navbar = $('#navbarContent');

	navbar.on('click', '.lvl1', function(e) {
		let id = $(this).attr("id");
		let uri = $(this).attr('href');

		if (!uri.includes(contextPath)) {
			$(this).siblings("ul.dropdown-menu").toggleClass("show");

			let show = $(this).parents('#oneDepth').find('.dropdown-menu');
			for (let i = 0; i < show.length; i++) {
				if ($(show[i]).hasClass) {
					$(show[i]).removeClass('show');
				}
			}
			printNavbar(2, '#' + id + '+ ul.dropdown-menu', id, uri);
		} else {
			location.href = uri;
		}
	});

	navbar.on('click', '.lvl2', function(event) {
		$(this).siblings("ul.dropdown-menu").toggleClass("show");
		if ($(this).attr('href') == '#') {
			let id = $(this).attr("id");

			printNavbar(3, '#' + id + '+ ul.dropdown-menu', id);
		}
	});

	$('#navbarContent').on("click", "ul.dropdown-menu [data-toggle='dropdown']", function(event) {
		let link = $(this).attr('href');
		if (!link.includes(contextPath)) {
			event.preventDefault();
			event.stopPropagation();

			$(this).parents().siblings('.dropdown-submenu').find('a.dropdown-item').siblings('ul.dropdown-menu').removeClass("show");

			if (!$(this).next().hasClass('show')) {
				$(this).parents('.dropdown-menu').first().find('.show').removeClass("show");
			}
			$(this).parents('li.nav-item.dropdown.show').on('hidden.bs.dropdown', function(e) {
				$('.dropdown-submenu .show').removeClass("show");
			});
		}
	});
}

function printNavbar(lvl, target, upCd, uri) {
	$.ajax({
		url: contextPath + '/navbars/' + lvl + '/' + upCd,
		contentType: "application/json; UTF-8;",
		type: 'GET',
		success: function(data) {
			if (data.length < 1) {
				$(target).toggleClass('show');
			}
			let html = "";
			for (let i = 0; i < data.length; i++) {
				if (lvl == 1) {
					html += '<!-- Level one dropdown -->';
					html += '<li class="nav-item dropdown">';
					if (data[i].subList == null) {
						html += '	<a id="' + data[i].comnCd + '" href="' + contextPath + data[i].val + '" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link lvl1" data-bs-auto-close="true">' + data[i].cdNm + '</a>';
					} else {
						html += '	<a id="' + data[i].comnCd + '" href="' + data[i].val + '" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link lvl1" data-bs-auto-close="true">' + data[i].cdNm + '</a>';
						html += '	<ul aria-labelledby="dropdownMenu1" class="dropdown-menu border-0">';
						html += '	</ul>';
					}

					html += '</li>';
				} else if (lvl == 2) {
					html += '<li class="dropdown-submenu">';
					if (data[i].subList != null) {
						html += '	<a id="' + data[i].comnCd + '" href="' + uri + '/' + data[i].comnCd + '" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="lvl2 dropdown-item" data-bs-auto-close="true">' + data[i].cdNm + '</a>';
					} else {
						html += '<a class="lvl2 dropdown-item" id="' + data[i].comnCd + '" href="' + contextPath + uri + '/' + data[i].comnCd + '">' + data[i].cdNm + '</a>';
					}
					html += '	<ul aria-labelledby="dropdownMenu2" class="dropdown-menu border-0 shadow">';
					html += '	</ul>';
					html += '</li>';
				} else if (lvl == 3) {
					html += '<li>';
					html += '	<a tabindex="-1" href="' + contextPath + data[i].val + '" class="dropdown-item show">' + data[i].cdNm + '</a>';
					html += '</li>';
				}
			}
			$(target).html(html);
		}
	})
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
		, 'target': target
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