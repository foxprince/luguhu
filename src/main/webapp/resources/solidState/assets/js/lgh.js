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
// 读取微信用户信息
function loadWxUser(openId) {
	$.get(window.ApiDomian + "/wp/portal/user/"+openId, function(json) {
		if(json.code==0) {
			$('#userNickname').text(json.data.nickname);
			$('#userIcon').html('<img width="100%" src="' + (json.data.headImgUrl===null?"pic07.jpg":json.data.headImgUrl)  + '" alt="" />');
		}
	});
}
// 读取用户发送的微信图文
function loadAsset(openId,tag) {
	var url = "/api/asset/list?createFrom=WECHAT&wxUser.openId="+openId;
	if(tag!=null)
		url += "&tags="+tag;
	$.get(window.ApiDomian + url, function(json) {
		if(json.code==0) {
			$("#assetList").fadeOut().html("");
			for (var i = 0; i < json.data.numberOfElements; i++) {
				var j = json.data.content[i];
				var item = '<h4>'+j.ctime+'</h4><pre><code>';
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
//读取产品包详情
function loadProductPack(id) {
	$.get(window.ApiDomian + "/api/productSalePack/"+id, function(json) {
		if(json.code==0) {
			$('#productTitle').text(json.data.title);
			$('#productPrice').text("售价："+json.data.price);
			$('#productDetail').html(json.data.description);
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
			$('#productPrice').text("售价："+json.data.price);
			$('#productDetail').html(json.data.description);
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