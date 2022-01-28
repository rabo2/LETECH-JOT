var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';
window.onload = function() {
	printNavbar(1, '#oneDepth');
	let navbar = $('#navbarContent');

	navbar.on('click', '.lvl1', function(event) {
		console.log($(this).attr('href') == '#');
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
					console.log(data[i].uri);
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


function printBoardSelet(target,upCd){
	let data = {'target' : target
				,'upCd' : upCd
	};
	
	let uri = '/std/commonCode/upCode?target=' + encodeURIComponent(target) +'&upCd='+encodeURIComponent(upCd);
	
	$.ajax({
		url : uri,
		method : 'GET'
	}).done(function(fragment){
		target = target.substring(target.indexOf('#'));
		$(target).replaceWith(fragment);
	})
	
}
