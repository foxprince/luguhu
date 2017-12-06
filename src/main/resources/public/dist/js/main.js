function selectAsset(id, location) {
	$("#assetList").html(" ");
	$('#assetModal').modal('hide');
	$("#previewImg").html('<img class="preview" src="/asset/preview?fileName=' + location + '"/>');
	$("#addOrEditForm").append('<input type="hidden" name="asset"  value="' + id + '"/>');
}
function selectAssetForEditor(id, location) {
	$("#editor").append('<img class="preview" src="/asset/preview?fileName=' + location + '"/>');
}
$('#assetModal').on(
		'show.bs.modal',
		function(e) {
			var modal = $(this);
			var id = e.relatedTarget.id;
			$.get("/asset/list.json", function(json) {
				if (json.code == 0) {
					for (var i = 0; i < json.data.numberOfElements; i++) {
						var j = json.data.content[i];
						var item = '<div class="col-sm-6 col-md-3">';
						item += '	<div class="thumbnail">';
						item += '	<div>';
						//如果是选择缩略图，则必须设置id为assetSelect，如果是富文本，则设置id为pictureBtn
						if(id=='assetSelect')
							item += '		<a href="#" onclick="selectAsset(\'' + j.id + '\',\'' + j.location
								+ '\')"><img  style="max-width:200px;max-height:200px;" src="/asset/preview?fileName=' + j.location + '" /></a>';
						else
							item += '		<a href="#" onclick="_insertimg(this)"><img  style="max-width:200px;max-height:200px;" src="/asset/preview?fileName=' + j.location + '" /></a>';
						item += '	</div>';
						item += '	<div class="caption">';
						item += '		<h6 id="imgTitle-${item.id}">' + j.title + '</h6>';
						item += '	</div></div>';
						item += '</div>';
						$("#assetList").append(item);
					}
				} else
					alert('载入素材库失败。');
			});
			$(this).find('.modal-body').css({
	              width:'auto', //probably not needed
	              height:'auto', //probably not needed 
	              'max-height':'100%'
	       });
		});
//锁定编辑器中鼠标光标位置。。
function _insertimg(item){
	$('#assetModal').modal('hide');
	var selection= window.getSelection ? window.getSelection() : document.selection;
	var range= selection.createRange ? selection.createRange() : selection.getRangeAt(0);
	if (!window.getSelection){
		document.getElementById('editor').focus();
		var selection= window.getSelection ? window.getSelection() : document.selection;
		var range= selection.createRange ? selection.createRange() : selection.getRangeAt(0);
		range.pasteHTML($(item).html());
		range.collapse(false);
		range.select();
	}else{
		document.getElementById('editor').focus();
		range.collapse(false);
		var hasR = range.createContextualFragment($(item).html());
		var hasR_lastChild = hasR.lastChild;
		while (hasR_lastChild && hasR_lastChild.nodeName.toLowerCase() == "br" && hasR_lastChild.previousSibling && hasR_lastChild.previousSibling.nodeName.toLowerCase() == "br") {
			var e = hasR_lastChild;
			hasR_lastChild = hasR_lastChild.previousSibling;
			hasR.removeChild(e);
		}                                
		range.insertNode(hasR);
		if (hasR_lastChild) {
			range.setEndAfter(hasR_lastChild);
			range.setStartAfter(hasR_lastChild)
		}
		selection.removeAllRanges();
		selection.addRange(range)
	}
}
function imgUpload(sender) {
	var uploadLayer = layer.msg('文件上传中...', {
		icon : 16,
		shade : 0.01,
		time : 999999
	});
	var fd = new FormData(document.getElementById("uploadForm"));
	$.ajax({
		type : 'post',
		url : "/asset/upload",
		data : fd,
		processData : false, // tell jQuery not to process the data
		contentType : false, // tell jQuery not to set contentType
		beforeSerialize : function() {
		},
		complete : function() {
			layer.close(uploadLayer);
		},
		success : function(json) {
			if (json.code == 0) {
				alert('文件上传成功。');
				$("#previewImg").html('<img class="preview" src="/asset/preview?fileName=' + json.data.location + '"/>');
				$("#addOrEditForm").append('<input type="hidden" name="asset" id="packImg" value="' + json.data.id + '"/>');
			} else
				alert('文件上传失败。');
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			console.log(XmlHttpRequest);
			console.log(textStatus);
			console.log(errorThrown);
		}
	});
}