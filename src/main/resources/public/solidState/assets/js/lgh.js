$(function() {
	loadTitle();
});
window.ApiDomian = '';
//读取产品列表
function loadProducts() {
	$.ajax({
		type : "get",
		url : window.ApiDomian + "/api/product/list",
		complete : function() {
			// layer.close(page_layer);
		},
		success : function(json) {
			for (var i = 0; i < json.data.numberOfElements; i++) {
				var j = json.data.content[i];
				var type = (j.pack)?'pack':'unit';
				var packIcon = (j.pack)?'<i alt="pack" class="fa fa-calendar"></i>':'';
				var item = ' <section id="' + j.id + '" class="wrapper '+((i%2!=0)?'alt':'')+' spotlight style' + (i + 1) % 6 + '">';
				item += ' 	<div class="inner">';
				item += ' 		<a href="generic.html?type='+type+'&id='+j.id+'" class="image"><img src="/asset/preview?fileName=' + (j.asset===null?"pic07.jpg":j.asset.location) + '" alt="" /></a>';
				item += ' 		<div class="content">';
				item += ' 			<h2 class="major"><a href="generic.html?type='+type+'&id='+j.id+'" >' + j.title + '</a> '+packIcon+'</h2>';
				item += ' 			<div style="display:inline-block;width:100%;"><p style="text-align:left;">' + j.intro + '</p>';
				item += ' 			<span style="float:left;"><a href="generic.html?type='+type+'&id='+j.id+'" class="special" >Learn more</a></span>';
				item += ' 			<span class="price" style="float:right;">' + j.price + ' <i class="fa fa-cny"></i></span></div>';
				item += ' 		</div>';
				item += ' 	</div>';
				item += ' </section>';
				$("#productWrapper").append(item);
			}
		},
		error : function() {
			alert('载入数据失败！');
		}
	});
}
// 读取网站标题
function loadTitle() {
	$.get(window.ApiDomian + "/api/getSiteTitle", function(data) {
		$('#siteTitle').text(data);
	});
}
//网络不通时测试用
function loadUserByWxUser(wxUserId) {
	$.get(window.ApiDomian + "/api/useres/"+wxUserId, function(json) {
		if(json.code==0){
			sessionStorage.wxUserId=json.data.wxUser.id;
			sessionStorage.userId=json.data.id;
			sessionStorage.user=JSON.stringify(json.data);
			$('#userNickname').text(json.data.wxUser.nickname);
			$('#userIcon').html('<img width="100%" src="' + (json.data.wxUser.headImgUrl===null?"pic07.jpg":json.data.wxUser.headImgUrl)  + '" alt="" />');
		}
	});
}
function loadUserFromSession() {
	var j = JSON.parse(sessionStorage.user);
	$('#name').val(j.name);
	$('#email').val(j.email);
	$('#phone').val(j.phone);
	$('#wxUserId').val(j.wxUser.id);
}
function loadAddressFromSession() {
	var j = JSON.parse(sessionStorage.user);
	$.each(j.addresses, function (n, v) {
        var item = "";
        item +='<ul class="contact" id="'+v.id+'">';
		item +='<li class="fa-home" editAttr="postAddress">'+v.postAddress+'</li>';
		item +='<li class="fa-phone" editAttr="phone">'+v.phone+'</li>';
		item +='<li class="fa-user" editAttr="consignee">'+v.consignee+'</li>';
		item +='<li><a href="javascript:;" onclick="editAddress(\''+v.id+'\',\''+v.postAddress+'\',\''+v.phone+'\',\''+v.consignee+'\')">修改<i class="fa fa-edit"></i></a><a href="javascript:;"onclick="removeAddress(this,'+v.id+')">删除 <i class="fa fa-remove"></i></a></li>';
		item += '<hr/>';
		item +='</ul>';
        $("#addressList").append(item).fadeIn();
    });
}
function loadAddress() {
	$.ajax({
		type : "get",
		url : window.ApiDomian + "/rest/addresses/search/findByUserId?userId="+sessionStorage.userId,
		success : function(json) {
			$("#addressList").html("").fadeOut();
			var j = json._embedded;
			$.each(j.addresses, function (n, v) {
		        var item = "";
		        item +='<ul class="contact" id="'+v.id+'">';
				item +='<li class="fa-home" editAttr="postAddress">'+v.postAddress+'</li>';
				item +='<li class="fa-phone" editAttr="phone">'+v.phone+'</li>';
				item +='<li class="fa-user" editAttr="consignee">'+v.consignee+'</li>';
				item +='<li><a href="javascript:;" onclick="editAddress(\''+v.id+'\',\''+v.postAddress+'\',\''+v.phone+'\',\''+v.consignee+'\')">修改<i class="fa fa-edit"></i></a><a href="javascript:;"onclick="removeAddress(this,'+v.id+')">删除 <i class="fa fa-remove"></i></a></li>';
				item += '<hr/>';
				item +='</ul>';
		        $("#addressList").append(item).fadeIn();
		    });
			$("#addressList").fadeIn();
		}
	});
}
function editAddress(id,postAddress,phone,consignee) {
	var t = '<form class="form-inline" id="addressForm_'+id+'" method="post" >';
	t += '<li class="fa-home"><input type="text" name="postAddress" value="'+postAddress+'"/></li>';
	t += '<li class="fa-phone"><input type="text" name="phone" value="'+phone+'"/></li>';
	t += '<li class="fa-user"><input type="text" name="consignee" value="'+consignee+'"/></li>';
	t +='<li><a href="javascript:;" onclick="updateAddress(\''+id+'\')">提交<i class="fa fa-edit"></i></a><a href="javascript:;"onclick="removeAddress(this,'+id+')">删除 <i class="fa fa-remove"></i></a></li>';
	t += '<hr/>';
	t += '</form>';
	$('#'+id).html(t);
}
function updateAddress(id) {
	var url = window.ApiDomian + "/api/addresses/"+id;
	$.ajax({
		type : "post",
		url : url,
		data : $('#addressForm_'+id).serialize(),//serialize(),
		complete : function() {
		},
		success : function(json) {
			if(json.code==0){
				var item ='<li class="fa-home" editAttr="postAddress">'+json.data.postAddress+'</li>';
				item +='<li class="fa-phone" editAttr="phone">'+json.data.phone+'</li>';
				item +='<li class="fa-user" editAttr="consignee">'+json.data.consignee+'</li>';
				item +='<li><a href="javascript:;" onclick="editAddress(\''+json.data.id+'\',\''+json.data.postAddress+'\',\''+json.data.phone+'\',\''+json.data.consignee+'\')">修改<i class="fa fa-edit"></i></a><a href="javascript:;"onclick="removeAddress(this,'+json.data.id+')">删除 <i class="fa fa-remove"></i></a></li>';
				item += '<hr/>';
				$('#'+id).html(item);
			}
		},
		error : function() {
			alert('载入数据失败！');
		}
	});
}
function addAddress() {
	var url = window.ApiDomian + "/api/addresses/";
	$.ajax({
		type : "post",
		url : url,
		data : $('#addressForm').serialize(),
		complete : function() {
		},
		success : function(json) {
			if(json.code==0){
				sessionStorage.user=JSON.stringify(json.data);
				window.location.href="address.html";
			}
		},
		error : function() {
			alert('添加地址失败！');
		}
	});
}
function removeAddress(item,id) {
	var url = window.ApiDomian + "/rest/addresses/"+id;
	$.ajax({
		type : "delete",
		url : url,
		complete : function() {
		},
		success : function(json) {
			$(item).parent().parent().fadeOut('slow').html('').remove();
		},
		error : function() {
			alert('删除地址失败！');
		}
	});
}
// 读取微信用户信息from微信服务器
function loadWxUser(openId) {
	$.get(window.ApiDomian + "/wp/portal/wxUser/"+openId, function(json) {
		if(json.code==0) {
			sessionStorage.wxUserId=json.data.wxUser.id;
			sessionStorage.userId=json.data.id;
			sessionStorage.user=JSON.stringify(json.data);
			$('#userNickname').text(json.data.wxUser.nickname);
			$('#userIcon').html('<img width="100%" src="' + (json.data.wxUser.headImgUrl===null?"pic07.jpg":json.data.wxUser.headImgUrl)  + '" alt="" />');
		}
	});
}
function loadUser() {
	$.ajax({
		type : "get",
		async : false,
		url : window.ApiDomian + "/api/user/"+sessionStorage.userId,
		success : function(json) {
			if(json.code==0) {
				alert('success');
				sessionStorage.user=JSON.stringify(json.data);
				alert('sessionStorage.user');
			}
		}
	});
}
function updateProfile() {
	var url = window.ApiDomian + "/api/users/";
	if(typeof sessionStorage.userId!=undefined)
		url += sessionStorage.userId;
	$.ajax({
		type : "post",
		url : url,
		data : $('#profileForm').serialize(),//serialize(),
		complete : function() {
		},
		success : function(json) {
			if(json.code==0){
				sessionStorage.user=JSON.stringify(json.data);
				$('#name').val(json.data.name);
				$('#email').val(json.data.email);
				$('#phone').val(json.data.phone);
			}
			$.fancybox.open('<div style="background-color: rgba(46, 49, 65, 0.8);" class="message"><p>修改成功!</p></div>');
		},
		error : function() {
			alert('载入数据失败！');
		}
	});
}

// 读取用户发送的微信图文
function loadAsset(openId,tag,page,size) {
	var url = "/api/asset/list?createFrom=WECHAT&wxUser.openId="+openId+'&page='+page+'&size='+size;
	if(tag!=null)
		url += "&tags="+tag;
	$.get(window.ApiDomian + url, function(json) {
		if(json.code==0) {
			$("#assetList").fadeOut().html("");
			for (var i = 0; i < json.data.numberOfElements; i++) {
				var j = json.data.content[i];
				var item = '<h4>'+j.formatCtime+'</h4><pre><code>';
				if(j.type=='image')
					item += '<img  style="max-width:200px;max-height:200px;" src="/asset/preview?size=small&fileName='+j.location+' " />';
				else
					item += j.sourceName;
				$.each(j.tags, function (n, tag) {
					item +='<button onclick="loadAsset(\''+openId+'\',\''+tag.id+'\')" class="button special small">'+tag.label+'</button>';
		        });
				item +='</code></pre>';
				$("#assetList").append(item).fadeIn();
			}
		}
	});
}
function loadProduct(type,id) {
	if(type=='pack')
		loadProductPack(id);
	else if(type=='unit') 
		loadProductUnit(id);
}
function loadAddressForUser(userId) {
	$.get(window.ApiDomian + "/api/"+userId+"/address/", function(json) {
		if(json.code==0) {
			for (var i = 0; i < json.data.numberOfElements; i++) {
				var j = json.data.content[i];
				var item = '<ul class="contact">';
				item += '	<li class="fa-envelope">'+j.consignee+'</li>'
				item += '	<li class="fa-home">'+j.address+'</li>';
				item += '	<li class="fa-phone">'+j.phone+'</li>';
				item +='</ul>';
				$("#address").append(item);
			}
		}
	});
}
//读取产品包详情
function loadProductPack(id) {
	$.get(window.ApiDomian + "/api/productSalePack/"+id, function(json) {
		if(json.code==0) {
			$('#productTitle').text(json.data.title);
			$('#productPrice').html("<i class='fa fa-cny'></i>售价："+json.data.price);
			$('#productDetail').html(json.data.content);
			$('#productImg').html('<img width="100%" src="/asset/preview?fileName=' + (json.data.asset===null?"pic07.jpg":json.data.asset.location)  + '" alt="" />');
			
		}
	});
}
//读取文章详情
function loadArticle(id) {
	$.get(window.ApiDomian + "/api/article/"+id, function(json) {
		if(json.code==0) {
			$('#title').text(json.data.title);
			$('#content').html(json.data.content);
			$('#thumb').html('<img width="100%" src="/asset/preview?fileName=' + (json.data.asset===null?"pic07.jpg":json.data.asset.location)  + '" alt="" />');
			
		}
	});
}
//读取可单独销售的产品
function loadProductUnit(id) {
	$.get(window.ApiDomian + "/api/productSaleUnit/"+id, function(json) {
		if(json.code==0) {
			$('#productTitle').text(json.data.title);
			$('#productPrice').html("<i class='fa fa-cny'></i>售价："+json.data.price);
			$('#productDetail').html(json.data.content);
			$('#productImg').html('<img width="100%" src="/asset/preview?fileName=' + (json.data.asset===null?"pic07.jpg":json.data.asset.location) + '" alt="" />');
			var wxUser = json.data.product.producer.wxUser;
			$('#userNickname').text(wxUser.nickname);$('#userNickname').attr("href","profile.html?openId="+wxUser.openId);
			$('#userIcon').html('<img width="100%" src="' + (wxUser.headImgUrl===null?"pic07.jpg":wxUser.headImgUrl)  + '" alt="" />');
			$('#userIcon').attr("href","profile.html?openId="+wxUser.openId);
			$('#profile').fadeIn();
		}
	});
}
//读取生产者
function loadUser(id) {
}
//读取文章列表
function loadArticles() {
	$.ajax({
		type : "get",
		url : window.ApiDomian + "/api/article/",
		complete : function() {
			// layer.close(page_layer);
		},
		success : function(json) {
			for (var i = 0; i < json.data.numberOfElements; i++) {
				var j = json.data.content[i];
				var item = '<article>';
				item += ' 	<a href="article.html?id='+j.id+'" class="image"><img src="/asset/preview?fileName=' + (j.asset===null?"pic07.jpg":j.asset.location) + '" alt="" /></a>';
				item += '	<h3 class="major">'+j.title+'</h3>';
				item += '	<p>'+j.summary+'</p>';
				item += '	<a href="article.html?id='+j.id+'" class="special">more</a>';
				item += '</article>';
				$("#articles").append(item);
			}
		},
		error : function() {
			alert('载入数据失败！');
		}
	});
}

function ajaxLog(s) {
	console.log(s);
}
function getParams(key) {
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}