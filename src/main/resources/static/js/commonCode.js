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
					'data': jstree,
					 "check_callback" : true
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
				for (let i = 0; i < btn.length; i++) {
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
			$('input[name="desc"]').val(data.desc);
		}
	});
}

function registCode() {
	form = $('#registForm').serialize();

	$.ajax({
		url: 'commonCode/regist',
		method: 'POST',
		data: form,
		success: function(data) {
			$('#tree').jstree().create_node (data.upCd, data.cdNm);
		}
	})
}

function modifyCode() {
	form = $('#registForm').serialize();
	$.ajax({
		url: 'commonCode/modify',
		method: 'PUT',
		data: form,
		success : function(data){
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