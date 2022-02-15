var form = "";
var codVal = "";
var codList = "";
var flag = true;
var data = "";
var html = "";
var maxComnCd = -1;

window.addEventListener('load', function() {
	commonCodeList('#codeList');
});

function commonCodeList(target, comnCd) {
	$.ajax({
		url: 'commonCode/list/' + comnCd,
		method: 'GET',
		success: function(data) {
			var jstree = new Array();
			$.each(data, function(idx, item) {
				if (item.upCd == item.comnCd) {
					item.upCd = '#';
				}
				jstree[idx] = { "id": item.comnCd, "parent": item.upCd, "text": item.cdNm };

				let comnCdNumber = Number(item.comnCd.substring(2));
				if (comnCdNumber > maxComnCd) {
					maxComnCd = comnCdNumber
				}
			});

			$('#tree').jstree({
				'core': {
					'data': jstree,
					"check_callback": true
				},
				'plugins': [
					'search'
				],
				"themes": {
					'name': "proton",
					'responsive': true
				}
			});
			$('#tree').on("select_node.jstree", function(e) {
				let comnCd = $(e.target).attr('aria-activedescendant');
				getCommonCode(comnCd);
				let btn = document.querySelectorAll('div.btn-div input.btn');
				for (let i = 0; i < btn.length - 1; i++) {
					btn[i].removeAttribute('disabled');
				}
			});

			var to = false;
			$('#plugins4_q').keyup(function() {
				if (to) {
					clearTimeout(to);
				}
				to = setTimeout(function() {
					var v = $('#plugins4_q').val();
					$('#tree').jstree(true).search(v);
				}, 250);
			});

		}
	})
}

function getCommonCode(comnCd) {
	$.ajax({
		url: 'commonCode/' + comnCd,
		success: function(data) {
			$('input[name="comnCd"]').val(data.comnCd);
			$('input[name="upCd"]').val(data.upCd);
			$('input[name="lvl"]').val(data.lvl);
			$('input[name="val"]').val(data.val);
			$('input[name="cdNm"]').val(data.cdNm);
			$('input[name="ord"]').val(data.ord);
			$('input[name="description"]').val(data.desc);
		}
	});
}

function abledRegist() {
	let comnCd = $('input[name="comnCd"]');
	let upCd = $('input[name="upCd"]');
	let lvl = $('input[name="lvl"]');

	upCd.val(comnCd.val());
	upCd = upCd.val();
	lvl.val(Number(lvl.val()) + 1);
	comnCd.val('CD' + leadingZeros(maxComnCd + 1, 3));
	comnCd = comnCd.val()

	$('input[name="val"]').val('').attr('placeholder', '값을 입력하세요').focus();
	$('input[name="cdNm"]').val('').attr('placeholder', '코드명을 입력하세요');
	$('input[name="ord"]').val('').attr('placeholder', '정렬순번을 입력하세요');
	$('input[name="description"]').val('').attr('placeholder', '설명을 입력하세요');

	let btn = document.querySelectorAll('div.btn-div input.btn');
	for (let i = 0; i < btn.length; i++) {
		btn[i].removeAttribute('disabled');
	}
}

function registCode() {
	let formInputs = {};

	$('input[type=text]').each(function() {
		formInputs[this.name] = this.value;
	});
	$.ajax({
		url: 'commonCode/regist',
		method: 'POST',
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify(formInputs),
		success: function(data) {
			let node = {id :data.comnCd, text : data.cdNm};
			
			$('#tree').jstree('create_node',data.upCd, node,'last');
		}
	})
}

function modifyCode() {
	let formInputs = {};

	$('input[type=text]').each(function() {
		formInputs[this.name] = this.value;
	});

	$.ajax({
		url: 'commonCode/modify',
		method: 'PUT',
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify(formInputs),
		success: function(data) {
			$('#tree').jstree().rename_node(data.comnCd, data.cdNm)
		}
	});
}

function removeCode() {
	let codeVal = document.querySelector('input[name="comnCd"]').value;

	if (confirm('정말 삭제하시겠습니까?')) {
		$.ajax({
			url: 'commonCode/remove/' + codeVal,
			method: 'DELETE'
		}).done(function() {
			$('#tree').jstree().delete_node($('#' + codeVal));
		});
	}
}

function leadingZeros(n, digits) {
	var zero = '';
	n = n.toString();

	if (n.length < digits) {
		for (var i = 0; i < digits - n.length; i++)
			zero += '0';
	}
	return zero + n;
}