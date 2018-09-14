//页面滚动导航悬浮
$(window).scroll(function () {
    if ($(window).scrollTop() > 40) {
        $("#nav").addClass("fixed");
        $("#nav_empty").addClass("placeholder");
    } else {
        $("#nav_empty").removeClass("placeholder");
        $("#nav").removeClass("fixed");
    }

});

$(document).ready(function () {
    $(".gotop").click(function () {
        $('body,html').animate({ scrollTop: 0 }, 1000);
        return false;
    });
    //页面滚动,回到顶部出现
    $(window).scroll(function () {
        if ($(window).scrollTop() > 200) {
            $(".backTop").fadeIn();
        } else {
            $(".backTop").fadeOut();
        }
    });
})
