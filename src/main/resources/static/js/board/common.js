/**
 * 
 */
var data = "";
var boardDev = "";
var col = ['글번호', '분류', '제목', '작성자', '작성일자', '조화수'];

window.addEventListener('load', function() {
	let boardDev = document.querySelector('input[name="boardDev"]').value
	printBoardList(boardDev);
});

function printBoardList(boardDev) {
	$.ajax({
		url: 'boardDev' + boardDev,
		method: 'GET',
		success: function(data) {
			document.getElementById('gridLabel').innerHTML = data[0].boardNm
			document.getElementById('totalCnt').innerText = '총 ' + data.length + '  건';
			let size;
			col = [
				{
					header: '분류',
					name: 'boardClass',
					keyColumnName: '100',
					width: 'auto',
					align: 'center'
				}, {
					header: '제목',
					name: 'title'
				}, {
					header: '작성자',
					name: 'writer',
					width: 'auto'
				}, {
					header: '작성일자',
					name: 'registDate',
					width: 'auto',
					minWidth: 150
				}, {
					header: '조회수',
					name: 'viewCnt',
					width: 'auto',
					align: 'right'
				}]

			if (data[0].thumbnail) {
				size = 900
				col.unshift({
					header: ' ',
					name: 'thumbnail',
					width: 85
				});

				for (let i = 0; i < data.length; i++) {
					data[i].thumbnail = '<div style="width:100px; height:80px; background-image: url(\'' + data[i].thumbnail+'\'); background-size: cover;  background-repeat: no-repeat; background-position: center center;"></div>';
				}
			}else{
				size = 'auto'
			}
			for (let i = 0; i < data.length; i++) {
				data[i].title += '<input type="hidden" id="' + data[i].boardDev + '/' + data[i].boardNo + '"/>'
			}


			pringToastGrid(col, data, size);
		}
	});
}
function pringToastGrid(column, gridData, size) {
	const grid = new tui.Grid({
		el: document.getElementById('grid'),
		contextMenu: null,
		data: gridData,
		bodyHeight: size,
		pageOptions: {
			useClient: true,
			perPage: 10
		},
		selectionUnit: 'row',
		scrollX: false,
		scrollY: false,
		columns: column
	});

	grid.on('mouseover', (e) => {
		if (e.targetType == 'cell') {
			$(e.nativeEvent.target).parents('tr').find('td').css({ 'background': '#cee6ff', 'cursor': 'pointer' });
		}
	})

	grid.on('mouseout', (e) => {
		if (e.targetType == 'cell') {
			$(e.nativeEvent.target).parents('tr').find('td').css('background', '');
		}
	});

	grid.on('click', (e) => {
		if (e.targetType == 'cell') {
			location.href = $(e.nativeEvent.target).parents('tr').find('input[type="hidden"]').attr('id')
		}
	});

	tui.Grid.applyTheme('clean');
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
			'content': $("textarea[name='content']").val(),
			'boardDev': boardDev,
			'boardClass': $('select[name="boardClass"] option:selected').val()
		};

		console.log(data)
		return false;
//		$.ajax({
//			url: '/std/boards',
//			data: JSON.stringify(data),
//			contentType: 'application/json; charset=utf-8',
//			method: 'POST'
//		})
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
			history.go(-1)
		});
	}
}