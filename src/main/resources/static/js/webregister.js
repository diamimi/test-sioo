//用户验证码数据
var validResultUser;
//用户验证码验证状态
var validUserSuccess = false;
//用户登录_验证码组件对象
var captchaObjUser;
//用户验证码验证状态
var validSiteSuccess = false;
//站长登录_验证码组件对象
var captchaObjSite;
function init() {
    //用户名验证
    $("#site input[name='userName']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '您的账户名和登录名';
        if (validUserName("site")) {
            validUserNameIsExist("site");
        } else {
            $(this).next().find("span").html("用户名必须是字母开头的6-15字符，允许字母数字下划线");
        }
    });

    //密码验证
    $("#site input[name='password']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '请输入6-12位数字英文组合密码';
        validPassword("site");
    });

    //重复密码验证
    $("#site input[name='rePassword']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '请再次输入密码';
        validRePassword("site");
    });

    //网站名称验证
    $("#site input[name='siteName']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '请填写与营业一致的网站名称，一个网站只能注册一个账号';
        validSiteName();
    });

    $("#site input[name='email']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '您的账户名和登录名';
        validEmail("site");
    });

    $("#siteRegBtn").on('click', function () {
        if (!(validUserName("site") & validPassword("site") & validRePassword("site") & validSiteName() & validEmail("site"))) {
            return;
        }

        if(!validSiteSuccess){
            $("#valid_site").html("验证码校验失败").show();
            return;
        }

        var validResultData = validResultSite;

        validUserNameIsExist("site", function () {
            var userNameInput = $("#site input[name='userName']");
            var passwordInput = $("#site input[name='password']");
            var rePasswordnput = $("#site input[name='rePassword']");
            var siteNameInput = $("#site input[name='siteName']");
            var emailInput = $("#site input[name='email']");

            var userName = userNameInput.val();
            var userPwd = passwordInput.val();
            var rePassword = rePasswordnput.val();
            var siteName = siteNameInput.val();
            var email = emailInput.val();

            var postData = {
                "userName": userName,
                "password": userPwd,
                "confirmPassword": rePassword,
                "siteName": siteName,
                "email": email,
                "geetestChallenge": validResultData.geetest_challenge,
                "geetestValidate": validResultData.geetest_validate,
                "geetestSeccode": validResultData.geetest_seccode
            };

            $.ajax({
                url: '/api/sec/register_web_master',
                dataType: 'json',
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(postData),
                success: function (data) {
                    if (data.resultCode == '000000') {
                        location.href = "/webmaster/index"
                    } else {
                        if (data.resultCode == '010100') {
                            $("#valid_user").html("验证码校验失败").show();
                        } else {
                            data.data && $.each(data.data, function (i, v) {
                                if (v.fieldCode == 'userName') {
                                    userNameInput.parent().addClass("error");
                                    userNameInput.next().find("span").html(v.errorMessage);
                                }
                            });
                        }
                        captchaObjSite.reset();
                        validSiteSuccess = false;
                    }
                }
            });
        });
    });

    initValidCodeInfo(handler);
}

function validUserName(type) {
    return validField(type, "userName", /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/);
}

function validUserNameIsExist(type, callback) {
    var usernameInput = $("#" + type + " input[name='userName']");
    var userName = usernameInput.val();
    console.log(usernameInput.val());
    $.ajax({
        url: '/api/sec/check_user_name',
        dataType: 'json',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify({"userName": userName}),
        success: function (data) {
            if (data.resultCode == '000000') {
                if (!data.data) {
                    usernameInput.parent().addClass("error");
                    usernameInput.next().find("span").html("用户名已存在，请重新选择！");
                } else {
                    usernameInput.parent().removeClass("error");
                    usernameInput.next().removeClass("info");
                    if (callback) {
                        callback();
                    }
                }
            } else {
                data.data && $.each(data.data, function (i, v) {
                    if (v.fieldCode == 'userName') {
                        usernameInput.parent().addClass("error");
                        usernameInput.next().find("span").html(v.errorMessage);
                    }
                });
            }
        }
    });
}

function validPassword(type) {
    return validField(type, "password", /^(?=.*\d)(?=.*[a-zA-Z]).{6,15}$/);
}

function validRePassword(type) {
    var passwordInput = $("#" + type + " input[name='password']");
    var rePasswordInput = $("#" + type + " input[name='rePassword']");
    if (rePasswordInput.val() != passwordInput.val()) {
        rePasswordInput.parent().addClass("error");
        return false;
    } else {
        rePasswordInput.parent().removeClass("error");
        rePasswordInput.next().removeClass("info");
        return true;
    }
}

function validCompanyName() {
    return validField("user", "companyName", /^[\u4E00-\u9FA5]{6,40}$/);
}

function validSiteName() {
    return validField("site", "siteName", /^[\u4E00-\u9FA5]{2,40}$/);
}

function validEmail(type) {
    return validField(type, "email", /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/);
}

function validField(type, field, reg) {
    var validInput = $("#" + type + " input[name='" + field + "']");
    var r = validInput.val().match(reg);
    if (r == null) {
        validInput.parent().addClass("error");
        return false;
    } else {
        validInput.parent().removeClass("error");
        validInput.next().removeClass("info");
        return true;
    }
}


var handler = function (captchaObj) {
    captchaObjSite = captchaObj;
    // 将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
    captchaObj.appendTo("#captcha2");
    captchaObj.onReady(function () {
        $("#wait1").hide();
    });

    captchaObj.onError(function () {
        validSiteSuccess = false;
        // 出错啦，可以提醒用户稍后进行重试
        $("#valid_site").html("验证码校验失败").show();
    });
    captchaObj.onSuccess(function () {
        $("#valid_site").html("");
        validSiteSuccess = true;
        validResultSite = captchaObj.getValidate();
    });
    // 更多接口参考：http://www.geetest.com/install/sections/idx-client-sdk.html
};

function initValidCodeInfo(handler) {
    $.ajax({
        url: "slide_valid/start_captcha?t=" + (new Date()).getTime(), // 加随机数防止缓存
        type: "get",
        dataType: "json",
        success: function (data) {
            // 调用 initGeetest 初始化参数
            // 参数1：配置参数
            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
                offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
                product: "float", // 产品形式，包括：float，popup
                width: "100%"
                // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
            }, handler);
        }
    });
}