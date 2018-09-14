//用户验证码数据
var validResultUser = {};
//用户验证码验证状态
var validUserSuccess = false;
//用户登录_验证码组件对象
var captchaObjUser;
//用户验证码验证状态
function init() {
    $('#tab').on('click', 'li', function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");

        if ($(this).data('type') == 'perfectInfo') {
            $("#bindUser").fadeOut(function () {
                $("#perfectInfo").fadeIn();
            });
        } else {
            $("#perfectInfo").fadeOut(function () {
                $("#bindUser").fadeIn();
            });
        }
    });
    $("#bind").on('click',function () {
        $("#bindUser_tab").addClass("active");
        $("#bindUser_tab").siblings().removeClass("active");
        $("#perfectInfo").fadeOut(function () {
            $("#bindUser").fadeIn();
        });
    });

    $("#register-bind").on('click',function () {
        $("#perfectInfo_tab").addClass("active");
        $("#perfectInfo_tab").siblings().removeClass("active");
        $("#bindUser").fadeOut(function () {
            $("#perfectInfo").fadeIn();
        });
    });

    //用户名验证
    $("#perfectInfo input[name='userName']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '您的账户名和登录名';
        if (validUserName("perfectInfo")) {
            validUserNameIsExist("perfectInfo");
        } else {
            $(this).next().find("span").html("用户名必须是字母开头的6-15字符，允许字母数字下划线");
        }
    });

    //密码验证
    $("#perfectInfo input[name='password']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '请输入6-12位数字英文组合密码';
        validPassword("perfectInfo");
    });

    //重复密码验证
    $("#perfectInfo input[name='rePassword']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '请再次输入密码';
        validRePassword("perfectInfo");
    });

    //公司名称验证
    $("#perfectInfo input[name='companyName']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '请填写与营业一致的公司名称，一个公司只能注册一个账号';
        validCompanyName("perfectInfo");
    });

    $("#perfectInfo input[name='email']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '您的账户名和登录名';
        validEmail("perfectInfo");
    });


    $("#userRegBtn").on('click', function () {

        if (!(validUserName("perfectInfo") & validPassword("perfectInfo") & validRePassword("perfectInfo") & validCompanyName("perfectInfo") & validEmail("perfectInfo"))) {
            return;
        }

        validUserNameIsExist("perfectInfo", function () {
            var userNameInput = $("#perfectInfo input[name='userName']");
            var passwordInput = $("#perfectInfo input[name='password']");
            var rePasswordnput = $("#perfectInfo input[name='rePassword']");
            var companyNameInput = $("#perfectInfo input[name='companyName']");
            var emailInput = $("#perfectInfo input[name='email']");

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
                "email": email
            };

            $.ajax({
                url: '/api/sec/register_and_login_wx',
                dataType: 'json',
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(postData),
                success: function (data) {
                    if (data.resultCode == '000000') {
                        location.href = "/user/index"
                    } else{
                        if (data.resultCode == '010106') {
                            $("#valid_user").html(data.resultMesg).show();

                        } else {
                            if(data.data){
                                $.each(data.data, function (i, v) {
                                    if (v.fieldCode == 'userName') {
                                        userNameInput.parent().addClass("error");
                                        userNameInput.next().find("span").html(v.errorMessage);
                                    }
                                });
                            }else{

                            }

                        }
                        //captchaObjUser.reset();
                        validUserSuccess = false;
                    }
                }
            });
        });
    });


    //绑定时-用户名验证
    $("#bindUser input[name='userName']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '您的账户名和登录名';
        validUserName("bindUser");
        // if (validUserName("bindUser")) {
        //     //validUserNameIsExist("bindUser");
        // } else {
        //     $(this).next().find("span").html("用户名必须是字母开头的6-15字符，允许字母数字下划线");
        // }
    });

    //绑定时-密码验证
    $("#bindUser input[name='password']").on("focus", function () {
        this.placeholder = '';
        $(this).next().addClass("info");

    }).on("blur", function () {
        this.placeholder = '请输入6-12位数字英文组合密码';
        validPassword("bindUser");
    });

    $("#bindBtn").on('click', function () {

        if (!(validUserName("bindUser") & validPassword("bindUser"))) {
            return;
        }

        var userNameInput = $("#bindUser input[name='userName']");
        var passwordInput = $("#bindUser input[name='password']");


        var userName = userNameInput.val();
        var userPwd = passwordInput.val();

        var postData = {
            "userName": userName,
            "userPwd": userPwd
        };

        $.ajax({
            url: '/api/sec/login_bind_wx',
            dataType: 'json',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(postData),
            success: function (data) {
                if (data.resultCode == '000000') {
                    location.href = "/user/index"
                } else{
                    if (data.resultCode == '010101') {
                        $("#bind_valid_user").html("用户名或密码有误！请重新输入").show();

                    } else if(data.resultCode == '010106') {
                        $("#bind_valid_user").html("微信登录超时，请重新登录！").show();
                    }else if(data.data) {
                        $.each(data.data, function (i, v) {
                            if (v.fieldCode && v.fieldCode == 'userName') {
                                userNameInput.parent().addClass("error");
                                userNameInput.next().find("span").html(v.errorMessage);
                            }
                        });
                    }else{
                        $("#bind_valid_user").html(data.mesg).show();
                    }
                }
            }
        });
    });
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

function validCompanyName(type) {
    return validField(type, "companyName", /^[\u4E00-\u9FA5]{6,40}$/);
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

