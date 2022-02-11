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
		method: 'GET',
		success: function(data) {
			var jstree = new Array();
			$.each(data, function(idx, item) {
				if (item.upCd == item.comnCd) {
					item.upCd = '#';
				}
				jstree[idx] = { "id": item.comnCd, "parent": item.upCd, "text": item.cdNm };

			});
			$('#tree').jstree({
				'core': {
					'data': jstree
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
		url: 'commonCode/'+comnCd,
		success: function(data) {
			$('input[name="comnCd"]').val(data.comnCd);
			$('input[name="upCd"]').val(data.upCd);
			$('input[name="lvl"]').val(data.lvl);
			$('input[name="val"]').val(data.val);
			$('input[name="cdNm"]').val(data.cdNm);
			$('input[name="ord"]').val(data.ord);
			$('input[name="desc"]').val(data.desc);
		}
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