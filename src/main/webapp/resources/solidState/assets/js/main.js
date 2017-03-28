/*
	Solid State by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
 */
(function($) {
	"use strict";
	skel.breakpoints({
		xlarge : '(max-width: 1680px)',
		large : '(max-width: 1280px)',
		medium : '(max-width: 980px)',
		small : '(max-width: 736px)',
		xsmall : '(max-width: 480px)'
	});
	$(function() {
		var $window = $(window), $body = $('body'), $header = $('#header'), $banner = $('#banner');
		// Disable animations/transitions until the page has loaded.
		$body.addClass('is-loading');
		$window.on('load', function() {
			window.setTimeout(function() {
				$body.removeClass('is-loading');
			}, 100);
		});
		// Fix: Placeholder polyfill.
		$('form').placeholder();
		// Prioritize "important" elements on medium.
		skel.on('+medium -medium', function() {
			$.prioritize('.important\\28 medium\\29', skel.breakpoint('medium').active);
		});
		// Header.
		if (skel.vars.IEVersion < 9)
			$header.removeClass('alt');
		if ($banner.length > 0 && $header.hasClass('alt')) {
			$window.on('resize', function() {
				$window.trigger('scroll');
			});
			$banner.scrollex({
				bottom : $header.outerHeight(),
				terminate : function() {
					$header.removeClass('alt');
				},
				enter : function() {
					$header.addClass('alt');
				},
				leave : function() {
					$header.removeClass('alt');
				}
			});
		}
		// Menu.
		var $menu = $('#menu');
		$menu._locked = false;
		$menu._lock = function() {
			if ($menu._locked)
				return false;
			$menu._locked = true;
			window.setTimeout(function() {
				$menu._locked = false;
			}, 350);
			return true;
		};
		$menu._show = function() {
			if ($menu._lock())
				$body.addClass('is-menu-visible');
		};
		$menu._hide = function() {
			if ($menu._lock())
				$body.removeClass('is-menu-visible');
		};
		$menu._toggle = function() {
			if ($menu._lock())
				$body.toggleClass('is-menu-visible');
		};
		$menu.appendTo($body).on('click', function(event) {
			event.stopPropagation();
			// Hide.
			$menu._hide();
		}).find('.inner').on('click', '.close', function(event) {
			event.preventDefault();
			event.stopPropagation();
			event.stopImmediatePropagation();
			// Hide.
			$menu._hide();
		}).on('click', function(event) {
			event.stopPropagation();
		}).on('click', 'a', function(event) {
			var href = $(this).attr('href');
			event.preventDefault();
			event.stopPropagation();
			// Hide.
			$menu._hide();
			// Redirect.
			window.setTimeout(function() {
				window.location.href = href;
			}, 350);
		});
		$body.on('click', 'a[href="#menu"]', function(event) {
			event.stopPropagation();
			event.preventDefault();
			// Toggle.
			$menu._toggle();
		}).on('keydown', function(event) {
			// Hide on escape.
			if (event.keyCode == 27)
				$menu._hide();
		});
	});
	
})(jQuery);
//以下方法为自定义方法
$(function() {
	loadTitle();
});
window.ApiDomian = '/api';
//读取产品列表
function loadProducts() {
	$.ajax({
		type : "get",
		url : window.ApiDomian + "/product/list",
		complete : function() {
			// layer.close(page_layer);
		},
		success : function(json) {
			for (var i = 0; i < json.data.numberOfElements; i++) {
				var j = json.data.content[i];
				var type = (j.pack)?'pack':'unit';
				var item = ' <section id="' + j.id + '" class="wrapper '+((i%2!=0)?'alt':'')+' spotlight style' + (i + 1) % 6 + '">';
				item += ' 	<div class="inner">';
				item += ' 		<a href="generic.html?type='+type+'&packId='+j.id+'" class="image"><img src="/asset/preview?fileName=' + j.asset.location + '" alt="" /></a>';
				item += ' 		<div class="content">';
				item += ' 			<h2 class="major">' + j.title + '</h2>';
				item += ' 			<div style="display:inline-block;width:100%;"><p style="text-align:left;">' + j.description + '</p>';
				item += ' 			<span style="float:left;"><a href="generic.html?type='+type+'&packId='+j.id+'" class="special" >Learn more</a></span>';
				item += ' 			<span class="price" style="float:right;">' + j.price + '</span></div>';
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
	$.get(window.ApiDomian + "/getSiteTitle", function(data) {
		$('#siteTitle').text(data);
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
	$.get(window.ApiDomian + "/productSalePack/"+id, function(json) {
		if(json.code==0) {
			$('#productTitle').text(json.data.title);
			$('#productPrice').text("售价："+json.data.price);
			$('#productDetail').text(json.data.description);
			$('#productImg').html('<img width="100%" src="/asset/preview?fileName=' + json.data.asset.location + '" alt="" />');
			
		}
	});
}
//读取可单独销售的产品
function loadProductUnit(id) {
	$.get(window.ApiDomian + "/productSaleUnit/"+id, function(json) {
		if(json.code==0) {
			$('#productTitle').text(json.data.title);
			$('#productPrice').text("售价："+json.data.price);
			$('#productDetail').text(json.data.description);
			$('#productImg').html('<img width="100%" src="/asset/preview?fileName=' + json.data.asset.location + '" alt="" />');
			
		}
	});
}
function ajaxLog(s) {
	alert(s);
}
function getParams(key) {
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}