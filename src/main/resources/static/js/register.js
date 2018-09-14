//用户验证码数据
var validResultUser = {};
//用户验证码验证状态
var validUserSuccess = false;
//用户登录_验证码组件对象
var captchaObjUser;
//用户验证码验证状态
function init() {
    //用户名验证
    $("#user input[name='userName']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '您的账户名和登录名';
        if (validUserName("user")) {
            validUserNameIsExist("user");
        } else {
            $(this).next().find("span").html("用户名必须是字母开头的6-15字符，允许字母数字下划线");
        }
    });

    //密码验证
    $("#user input[name='password']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '请输入6-12位数字英文组合密码';
        validPassword("user");
    });

    //重复密码验证
    $("#user input[name='rePassword']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '请再次输入密码';
        validRePassword("user");
    });

    //公司名称验证
    $("#user input[name='companyName']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '请填写与营业一致的公司名称，一个公司只能注册一个账号';
        validCompanyName();
    });

    $("#user input[name='email']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '您的账户名和登录名';
        validEmail("user");
    });


    $("#userRegBtn").on('click', function () {

        if (!(validUserName("user") & validPassword("user") & validRePassword("user") & validCompanyName() & validEmail("user"))) {
            return;
        }

        if(!validUserSuccess){
            $("#valid_user").html("验证码校验失败").show();
            return;
        }
        var validResultData = validResultUser;

        validUserNameIsExist("user", function () {
            var userNameInput = $("#user input[name='userName']");
            var passwordInput = $("#user input[name='password']");
            var rePasswordnput = $("#user input[name='rePassword']");
            var companyNameInput = $("#user input[name='companyName']");
            var emailInput = $("#user input[name='email']");

            var userName = userNameInput.val();
            var userPwd = passwordInput.val();
            var rePassword = rePasswordnput.val();
            var companyName = companyNameInput.val();
            var email = emailInput.val();

            var postData = {
                "userName": userName,
                "password": userPwd,
                "confirmPassword": rePassword,
                "companyName": companyName,
                "email": email,
                // "geetestChallenge": validResultData.geetest_challenge,
                // "geetestValidate": validResultData.geetest_validate,
                // "geetestSeccode": validResultData.geetest_seccode
                "sessionId":validResultData.sessionId,
                "sig":validResultData.sig,
                "token":validResultData.token,
                "scene":validResultData.scene
            };

            $.ajax({
                url: '/api/sec/register_and_login',
                dataType: 'json',
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(postData),
                success: function (data) {
                    if (data.resultCode == '000000') {
                        location.href = "/user/index"
                    } else{
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
                        //captchaObjUser.reset();
                        validUserSuccess = false;
                    }
                }
            });
        });
    });

    var nc_token = ["FFFF00000000017A5EDA", (new Date()).getTime(), Math.random()].join(':');
    var NC_Opt =
        {
            renderTo: "#captcha",
            appkey: "FFFF00000000017A5EDA",
            scene: "register",
            token: nc_token,
            customWidth: 398,
            trans:{"key1":"code0"},
            elementID: ["usernameID"],
            is_Opt: 0,
            language: "cn",
            isEnabled: true,
            timeout: 3000,
            times:5,
            apimap: {
                // 'analyze': '//a.com/nocaptcha/analyze.jsonp',
                // 'get_captcha': '//b.com/get_captcha/ver3',
                // 'get_captcha': '//pin3.aliyun.com/get_captcha/ver3'
                // 'get_img': '//c.com/get_img',
                // 'checkcode': '//d.com/captcha/checkcode.jsonp',
                // 'umid_Url': '//e.com/security/umscript/3.2.1/um.js',
                // 'uab_Url': '//aeu.alicdn.com/js/uac/909.js',
                // 'umid_serUrl': 'https://g.com/service/um.json'
            },
            callback: function (data) {
                validResultUser.sessionId = data.csessionid;
                validResultUser.sig = data.sig;
                validResultUser.token = NC_Opt.token;
                validResultUser.scene = NC_Opt.scene;
                validUserSuccess = true;
                $("#valid_user").hide();
            }
        }
    var nc = new noCaptcha(NC_Opt)
    nc.upLang('cn', {
        _startTEXT: "请按住滑块，拖动到最右边",
        _yesTEXT: "验证通过",
        _error300: "哎呀，出错了，点击<a href=\"javascript:__nc.reset()\">刷新</a>再来一次",
        _errorNetwork: "网络不给力，请<a href=\"javascript:__nc.reset()\">点击刷新</a>",
    })
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

