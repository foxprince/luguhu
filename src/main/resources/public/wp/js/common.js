var utils = {
    init: function () {
        //绑定菜单事件
        $(".fixHeader > label").click(function () {
            if ($("aside").css("right") == "-100px")
                $("aside").animate({right:0});
            else
                $("aside").animate({right:-100});
        })
    },
    hd : function () {
        new Swiper('.swiper-hd', {pagination: {el: '.swiper-pagination',},loop: true,autoplay: {delay: 3000,disableOnInteraction: false,}})
    }
}