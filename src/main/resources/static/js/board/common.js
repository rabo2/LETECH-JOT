/**
 * 
 */
var data = "";
var boardDev = "";
var col = ['글번호', '분류', '제목', '작성자', '작성일자', '조화수'];

window.addEventListener('load', function() {
	let boardDev = document.querySelector('input[name="boardDev"]').value
	printBoardList(boardDev);

	$('#grid').on('click', 'table.tui-grid-table tbody tr', function(e) {
		let boardNo = this.getAttribute('data-boardNo');
		let boardDev = this.getAttribute('data-boardDev');
		location.href = boardDev + '/' + boardNo;
	})
	
	
});

function printBoardList(boardDev) {
	$.ajax({
		url: 'boardDev' + boardDev,
		method: 'GET',
		success: function(data) {
			document.getElementById('gridLabel').innerHTML = data[0].boardNm
			document.getElementById('totalCnt').innerText = '총 ' + data.length;
			pringToastGrid(data);
		}
	});
}
function pringToastGrid(gridData) {
	const grid = new tui.Grid({
		el: document.getElementById('grid'),
		contextMenu : null,
		data: gridData,
		pageOptions: {
			useClient: true,
			perPage: 15
		},
		minBodyHeight: 60,
		selectionUnit: 'row',
		scrollX: false,
		scrollY: false,
		rowHeaders: ['rowNum'],
		columns: [{
			header: '분류',
			name: 'boardClass',
			width : 100
		}, {
			header: '제목',
			name: 'title',
			width : 650
		}, {
			header: '작성자',
			name: 'writer',
			width : 120
		}, {
			header: '작성일자',
			name: 'registDate',
			width : 150
		}, {
			header: '조회수',
			name: 'viewCnt',
			width : 50
		}],
	});
	onGridMounted 
	
	grid.on('onGridMounted', (e) => {
		document.getElementById('pageCnt').innerText = '      1 /' ;
	});

	grid.on('afterPageMove', (e) => {
		const pagination = grid.getPagination();
		const currentPage = pagination.getCurrentPage();
			
		document.getElementById('pageCnt').innerText = currentPage+'/' ;
	});
}


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