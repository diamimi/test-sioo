var usrLoginUserNameIsNotValid = false;
var siteLoginUserNameIsNotValid = false;
var userLoginUserPwdIsNotValid = false;
var siteLoginUserPwdIsNotValid = false;
//用户验证码数据
var validResultUser;
//用户验证码验证状态
var validUserSuccess = false;
//用户登录_验证码组件对象
var captchaObjUser;
var willValid = false;
var initValid = false;

//站点验证码数据
var validResultSite;
//用户验证码验证状态
var validSiteSuccess = false;
//站长登录_验证码组件对象
var captchaObjSite;

function login(type) {
    var userNameInput = $("#" + type + "LoginForm input[name='userName']");
    var userPwdInput = $("#" + type + "LoginForm input[name='userPwd']");

    var userIsEmpty = checkUserNameIsEmpty(type);
    var userError = !checkUserNameFormat(type);
    var pwdIsEmpty = checkUserPwdIsEmpty(type);
    var pwdError = !checkUserPwdFormat(type);

    if (userIsEmpty || userError || pwdIsEmpty || pwdError) {
        $("#" + type + " .msg-error").removeClass("hide");

        if (userIsEmpty || userError) {
            userNameInput.parent().addClass("item-error");
        }
        if (pwdIsEmpty || pwdError) {
            userPwdInput.parent().addClass("item-error");
        }

        if (userIsEmpty) {
            $("#" + type + " .msg-error span").html("用户名不能为空");
        } else if (userError) {
            $("#" + type + " .msg-error span").html("用户名不存在");
        } else if (pwdIsEmpty) {
            $("#" + type + " .msg-error span").html("密码不能为空");
        } else if (userError) {
            $("#" + type + " .msg-error span").html("用户名或密码有误");
        }
        return;
    }

    if(willValid){
        if(type == "user"){
            if(!validUserSuccess){
                $("#" + type + " .msg-error").removeClass("hide");
                $("#" + type + " .msg-error span").html("验证码验证失败！");
                return;
            }
        }else if(type == "site"){
            if(!validSiteSuccess){
                $("#" + type + " .msg-error").removeClass("hide");
                $("#" + type + " .msg-error span").html("验证码验证失败！");
                return;
            }
        }
    }

    var loginSystem = '';
    if (type == 'user') {
        loginSystem = 'USER';
    } else {
        loginSystem = 'WEB_MASTER';
    }

    var validResultData = validResultUser;
    if(type == "site"){
        validResultData = validResultSite;
    }

    var userName = userNameInput.val();
    var userPwd = userPwdInput.val();
    var geetest_challenge_val = validResultData && validResultData.geetest_challenge?validResultData.geetest_challenge:'';
    var geetest_validate_val = validResultData && validResultData.geetest_validate?validResultData.geetest_validate:'';
    var geetest_seccode_val = validResultData && validResultData.geetest_seccode?validResultData.geetest_seccode:'';
    var postData = {
        "userName": userName,
        "userPwd": userPwd,
        "system": loginSystem,
        "geetestChallenge": geetest_challenge_val,
        "geetestValidate": geetest_validate_val,
        "geetestSeccode": geetest_seccode_val
    };

    $.ajax({
        url: '/api/sec/login',
        dataType: 'json',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(postData),
        success: function (data) {
            if (data.resultCode == '000000') {
                willValid = false;
                if(type == "user"){
                    location.href = "/user/index";
                }else if(type == "site"){
                    location.href = "/webmaster/index";
                }
            } else {
                $("#" + type + " .msg-error").removeClass("hide");
                if(data.resultCode == '010101'){
                    if(data.data && data.data.count && data.data.count >=8){
                        if(!willValid){
                            if(type == "user"){
                                initValidCode_user();
                            }else if(type == "site"){
                                initValidCode_site();
                            }
                        }
                        willValid = true;
                    }
                }

                if (data.resultCode == '010100') {
                    $("#" + type + " .msg-error span").html("验证码校验失败");
                } else if (data.resultCode == '010101') {
                    $("#" + type + " .msg-error span").html("密码错误");
                } else if (data.resultCode == '020102') {
                    $("#" + type + " .msg-error span").html("该用户名不存在");
                } else if (data.resultCode == '010105') {
                    $("#" + type + " .msg-error span").html("用户未授权访问");
                } else if (data.resultCode == '020206') {
                    $("#" + type + " .msg-error span").html("您的账号已经被锁定，请联系客服人员");
                } else {
                    $("#" + type + " .msg-error span").html("用户名或密码有误，请重新输入");
                }
                if(type == "user"){
                    if(willValid && captchaObjUser){
                        captchaObjUser.reset();
                    }
                    validUserSuccess = false;
                }else if(type == "site"){
                    if(willValid && captchaObjSite){
                        captchaObjSite.reset();
                    }
                    validSiteSuccess = false;
                }
            }
        }
    });
}

function checkLoginInputIsEempty(inputId) {
    var inputField = $("#" + inputId).val();
    if(inputField == undefined){
        return true;
    }
    if($.trim(inputField) == ''){
        return true;
    }
    if($.trim(inputField).length <= 0){
        return true;
    }
    return false;
}

function checkUserNameIsEmpty(type) {
    if (type == 'user') {
        return checkLoginInputIsEempty("user_userName");
    } else {
        return checkLoginInputIsEempty("site_userName");
    }
}

function checkUserPwdIsEmpty(type) {
    if (type == 'user') {
        return checkLoginInputIsEempty("user_userPwd");
    } else {
        return checkLoginInputIsEempty("site_userPwd");
    }
}

function checkUserNameFormat(type) {
    var currentInputVal = "";
    if (type == 'user') {
        currentInputVal = $("#user_userName").val();
    } else {
        currentInputVal = $("#site_userName").val();
    }

    var reg = /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/;
    var r = currentInputVal.match(reg);

    return r != null;
}

function checkUserPwdFormat(type) {
    var currentInputVal = "";
    if (type == 'user') {
        currentInputVal = $("#user_userPwd").val();
    } else {
        currentInputVal = $("#site_userPwd").val();
    }

    var reg = /^(?=.*\d)(?=.*[a-zA-Z]).{6,15}$/;
    var r = currentInputVal.match(reg);

    return r != null;
}

var handler1 = function (captchaObj) {
    captchaObjUser = captchaObj;

    //将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
    captchaObj.appendTo("#captcha1");
    captchaObj.onReady(function () {
        //$("#wait1").hide();
    });

    captchaObj.onError(function () {
        validUserSuccess = false;
        // 出错啦，可以提醒用户稍后进行重试
        $("#user .msg-error").removeClass("hide");

        $("#user .msg-error span").html("验证码错误，请稍后再试");
    });


    captchaObj.onSuccess(function () {
        validUserSuccess = true;
        validResultUser = captchaObj.getValidate();
    });
};

var handler2 = function (captchaObj) {
    captchaObjSite = captchaObj;
    // 将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
    captchaObj.appendTo("#captcha2");
    captchaObj.onReady(function () {
        $("#wait2").hide();
    });
    captchaObj.onError(function () {
        validSiteSuccess = false;
        // 出错啦，可以提醒用户稍后进行重试
        $("#site .msg-error").removeClass("hide");

        $("#site .msg-error span").html("验证码错误，请稍后再试");
    });


    captchaObj.onSuccess(function () {
        validSiteSuccess = true;
        validResultSite = captchaObj.getValidate();
    });
};

function initValidCode_user() {
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
            }, handler1);
        }
    });
}

function initValidCode_site() {
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
                product: "popup", // 产品形式，包括：float，popup
                width: "100%"
                // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
            }, handler2);
        }
    });
}

var init = function(initWillValid){
    if(initWillValid && initWillValid != 0){
        willValid = initWillValid;
        initValidCode_user();
        initValidCode_site();
    }
    $('#tab').on('click', 'li', function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");

        if ($(this).data('login-type') == 'user') {
            $("#site").fadeOut(function () {
                $("#user").fadeIn();
            });
        } else {
            $("#user").fadeOut(function () {
                $("#site").fadeIn();
            });
        }
    });

    $(".login_from li.item .input_txt").on("focus", function () {
        $(this).parent().addClass("item-focus")
    });
    $(".login_from li.item .input_txt").on("blur", function () {
        $(this).parent().removeClass("item-focus");

        var currentInputVal = $(this).val();

        if ($(this).attr("id").indexOf("user") == 0) {
            if ($(this).attr("id") == 'user_userName') {
                var userNameIsEmpty = checkUserNameIsEmpty("user");
                var userNameFormat = checkUserNameFormat("user");
                if (userNameIsEmpty || !userNameFormat) {
                    usrLoginUserNameIsNotValid = true;
                    $(this).parent().addClass("item-error");
                    $("#user .msg-error").removeClass("hide");

                    if (userNameIsEmpty) {
                        $("#user .msg-error span").html("用户名不能为空");
                    } else if (!userNameFormat) {
                        $("#user .msg-error span").html("用户名不存在");
                    }
                } else {
                    usrLoginUserNameIsNotValid = false;
                    $(this).parent().removeClass("item-error");
                    if (!userLoginUserPwdIsNotValid) {
                        $("#user .msg-error").addClass("hide");
                    }
                }
            } else {
                var userPwdIsEmpty = checkUserPwdIsEmpty("user");
                var userPwdFormat = checkUserPwdFormat("user");
                if (userPwdIsEmpty || !userPwdFormat) {
                    userLoginUserPwdIsNotValid = true;
                    if (userPwdIsEmpty) {
                        $(this).parent().addClass("item-error");
                        if (!usrLoginUserNameIsNotValid) {
                            $("#user .msg-error").removeClass("hide");
                            $("#user .msg-error span").html("密码不能为空");
                        }
                    } else if (!userPwdFormat) {
                        $(this).parent().addClass("item-error");
                        if (!usrLoginUserNameIsNotValid) {
                            $("#user .msg-error").removeClass("hide");
                            $("#user .msg-error span").html("用户名或密码有误");
                        }
                    }
                } else {
                    userLoginUserPwdIsNotValid = false;
                    $(this).parent().removeClass("item-error");
                    if (!usrLoginUserNameIsNotValid) {
                        $("#user .msg-error").addClass("hide");
                    }
                }
            }
        } else {
            if ($(this).attr("id") == 'site_userName') {
                var userNameIsEmpty = checkUserNameIsEmpty("site");
                var userNameFormat = checkUserNameFormat("site");
                if (userNameIsEmpty || !userNameFormat) {
                    siteLoginUserNameIsNotValid = true;
                    $(this).parent().addClass("item-error");
                    $("#site .msg-error").removeClass("hide");

                    if (userNameIsEmpty) {
                        $("#site .msg-error span").html("用户名不能为空");
                    } else if (!userNameFormat) {
                        $("#site .msg-error span").html("用户名不存在");
                    }
                } else {
                    siteLoginUserNameIsNotValid = false;
                    $(this).parent().removeClass("item-error");
                    if (!siteLoginUserPwdIsNotValid) {
                        $("#site .msg-error").addClass("hide");
                    }
                }
            } else {
                var userPwdIsEmpty = checkUserPwdIsEmpty("site");
                var userPwdFormat = checkUserPwdFormat("site");
                if (userPwdIsEmpty || !userPwdFormat) {
                    siteLoginUserPwdIsNotValid = true;
                    if (userPwdIsEmpty) {
                        $(this).parent().addClass("item-error");
                        if (!siteLoginUserNameIsNotValid) {
                            $("#site .msg-error").removeClass("hide");
                            $("#site .msg-error span").html("密码不能为空");
                        }
                    } else if (!userPwdFormat) {
                        $(this).parent().addClass("item-error");
                        if (!siteLoginUserNameIsNotValid) {
                            $("#site .msg-error").removeClass("hide");
                            $("#site .msg-error span").html("用户名或密码有误");
                        }
                    }
                } else {
                    siteLoginUserPwdIsNotValid = false;
                    $(this).parent().removeClass("item-error");
                    if (!siteLoginUserNameIsNotValid) {
                        $("#site .msg-error").addClass("hide");
                    }
                }
            }
        }
    });
    $(".clear-btn").on("click", function () {
        $(this).prev().val("");
    });

    $("#userLoginBtn").on('click', function () {
        login("user");
    });
    $("#siteLoginBtn").on('click', function () {
        login("site");
    });
    //initValidCode_user();
    //initValidCode_site();
}