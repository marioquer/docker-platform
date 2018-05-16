NProgress.start();

var FADE_TIME = 300;
var TOAST_TIME = 2000;

//View

//add toast DOM
$("body").append("<div id=\"toast\" th:fragment=\"toast\">\n" +
    "    <span class=\"close-button\" onclick=\"switchToast(0)\">X</span>\n" +
    "    <div id=\"toast_text\">text</div>\n" +
    "</div>");

function switchModal(flag, id) {
    if (flag) {
        $("#" + id).fadeIn(FADE_TIME);
    } else {
        $("#" + id).find("input[type=text]").val("");
        $("#" + id).find("input[type=password]").val("");
        $("#" + id).fadeOut(FADE_TIME);
    }
}

function showMsgModal(msg) {
    $("#msg_modal_text").html(msg);
    switchModal(1, "msg_modal");
}

function switchToast(flag) {
    if (flag) {
        $("#toast").fadeIn(FADE_TIME);
    } else {
        $("#toast").fadeOut(FADE_TIME);
    }
}

function showToast(text, time, type, callback) {
    switch (type) {
        case 1:
            $("#toast").css("border-color", "#04AFE3");
            $("#toast .close-button:hover").css({"border-color": "#04AFE3", "color": "#04AFE3"});
            break;
        case -1:
            $("#toast").css("border-color", "#e3472f");
            $("#toast .close-button:hover").css({"border-color": "#e3472f", "color": "#e3472f"});
            break;
    }
    $("#toast_text").html(text);
    $("#toast").fadeIn(FADE_TIME);
    setTimeout(function () {
        $("#toast").fadeOut(FADE_TIME);
        if (callback != undefined) {
            setTimeout(function () {
                callback();
            }, FADE_TIME);
        }
    }, time);
}

//Auth
function login() {

    var username = $("#username").val();
    var password = $("#password").val();

    if (username == "" || password == "") {
        showToast("请填入完整信息", TOAST_TIME, -1);
    } else {
        $.ajax({
            url: '/user/login',
            type: 'POST',
            data: {
                username: username,
                password: password
            },
            success: function (resp) {
                if (resp.login_status === 'not_found') {
                    showToast("该用户不存在", TOAST_TIME, -1);
                } else if (resp.login_status === 'wrong_password') {
                    showToast("密码错误", TOAST_TIME, -1);
                } else {
                    sessionStorage.setItem("userId", resp.id);
                    showToast("欢迎回来，" + resp.username, TOAST_TIME, 1, function () {
                        location.href = '/page/serverList';
                    });
                }
            },
            error: function (err) {
                showToast('登录失败', TOAST_TIME, -1);
                console.log(err);
            }
        });
    }
}

function signup() {
    var username = $("#username").val();
    var password = $("#password").val();

    if (username == "" || password == "") {
        showToast("请填入完整信息", TOAST_TIME, -1);
    } else {
        $.ajax({
            url: '/user/signup',
            type: 'POST',
            data: {
                username: username,
                password: password
            },
            success: function (resp) {
                switch (resp) {
                    case "success":
                        showToast("注册成功", TOAST_TIME, 1, function () {
                            location.href = '/page/login';
                        });
                        break;
                    case "fail":
                        showToast("注册失败", TOAST_TIME, -1);
                        break;
                    case "exists":
                        showToast("该用户名已存在", TOAST_TIME, -1);
                        break;
                }
            },
            error: function (err) {
                showToast('注册失败', TOAST_TIME, -1);
                console.log(err);
            }
        });
    }
}

function logout() {
    $.ajax({
        url: '/user/logout',
        type: 'GET',
        success: function (resp) {
            showToast("登出成功", TOAST_TIME, 1, function () {
                location.href = resp;
            });
        },
        error: function (err) {
            showToast('注册失败', TOAST_TIME, -1);
            console.log(err);
        }
    });
}


//server-list
function addServer() {
    NProgress.start();
    var ip = $("#new_ip").val();
    var uname = $("#new_uname").val();
    var password = $("#new_password").val();
    var name = $("#new_name").val();
    var ownerId = $("#owner_id").val();
    $.ajax({
        url: '/addServer',
        type: 'POST',
        data: {
            name: name,
            ownerId: ownerId,
            ip: ip,
            uname: uname,
            password: password
        },
        success: function (resp) {
            if (resp == "success") {
                NProgress.done();
                location.reload();
            } else {
                showToast("增加失败", TOAST_TIME, -1);
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
}

