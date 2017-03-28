function selectAsset(id, location) {
	$("#assetList").html(" ");
	$('#assetModal').modal('hide');
	$("#previewImg").html('<img class="preview" src="/asset/preview?fileName=' + location + '"/>');
	$("#addOrEditForm").append('<input type="hidden" name="asset"  value="' + id + '"name="img"/>');
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
				$("#addOrEditForm").append('<input type="hidden" id="packImg" value="' + json.data + '"name="img"/>');
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