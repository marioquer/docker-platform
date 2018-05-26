NProgress.start();

var FADE_TIME = 300;
var TOAST_TIME = 2000;

//View render

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


function jumpToContainer() {
    location.href = "/page/server/container?serverId=" + $("#server_id").html();
}

function jumpToImage() {
    location.href = "/page/server/image?serverId=" + $("#server_id").html();
}


//Auth page
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
                        location.href = '/page/server/serverList';
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
                sessionStorage.removeItem("userId");
                location.href = resp;
            });
        },
        error: function (err) {
            showToast('登出失败', TOAST_TIME, -1);
            console.log(err);
        }
    });
}


//server-list page
function addServer() {
    NProgress.start();
    var ip = $("#new_ip").val();
    var uname = $("#new_uname").val();
    var password = $("#new_password").val();
    var name = $("#new_name").val();
    var ownerId = sessionStorage.getItem("userId");

    if (ip == "" || uname == "" || password == "" || name == "") {
        showToast('请填写完整信息', TOAST_TIME, -1);
    } else {
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
                    showToast("增加成功", TOAST_TIME, 1, function () {
                        location.reload();
                    });
                } else if (resp == "exists") {
                    showToast("服务器已存在", TOAST_TIME, -1);
                } else if (resp == "wrong_password") {
                    showToast("账号或密码错误", TOAST_TIME, -1);
                } else if (resp == "wrong_sec_setting") {
                    showToast("服务器未设置允许账号密码登录", TOAST_TIME, -1);
                } else {
                    showToast("增加失败", TOAST_TIME, -1);
                }
            },
            error: function (err) {
                showToast("增加失败", TOAST_TIME, -1);
                console.log(err);
            },
            complete: function () {
                NProgress.done();
            }
        });
    }
}

//container page
function addContainer() {
    NProgress.start();
    var rep = $("#new_rep").val();
    var tag = $("#new_tag").val();
    var port = $("#new_port").val();
    var ownerId = sessionStorage.getItem("userId");
    var serverId = $("#server_id").html();

    if (rep == "") {
        showToast('仓库名必须填写', TOAST_TIME, -1);
    } else {
        $.ajax({
            url: '/deployContainer',
            type: 'POST',
            data: {
                rep: rep,
                tag: tag,
                port: port,
                ownerId: ownerId,
                serverId: serverId
            },
            success: function (resp) {
                if (resp == "success") {
                    showToast("部署成功", TOAST_TIME, 1, function () {
                        location.reload();
                    });
                } else if (resp == "port_unavailable") {
                    showToast("服务器该端口被占用", TOAST_TIME, -1);
                } else if (resp == "not_exists") {
                    showToast("Hub中该镜像不存在或版本号不存在", TOAST_TIME, -1);
                } else if (resp == "access_denied") {
                    showToast("您不是该服务器的拥有者", TOAST_TIME, -1);
                } else {
                    showToast("部署失败", TOAST_TIME, -1);
                }
            },
            error: function (err) {
                showToast("部署失败", TOAST_TIME, -1);
                console.log(err);
            },
            complete: function () {
                NProgress.done();
            }
        });
    }
}

//swarm page
function addSwarmServer() {
    NProgress.start();
    var role = "";
    var server = $("#new_server").val();
    var ownerId = sessionStorage.getItem("userId");
    if ($("#manager").is(':checked')) {
        role = "manager";
    } else if ($("#worker").is(':checked')) {
        role = "worker";
    }

    if (role == "" || server == "") {
        alert(role);
        alert(server);
        showToast("请填写完整信息", TOAST_TIME, -1);
    } else {
        $.ajax({
            url: '/addSwarmServer',
            type: 'POST',
            data: {
                role: role,
                serverName: server,
                ownerId: ownerId
            },
            success: function (resp) {
                if (resp == "success") {
                    showToast("添加成功", TOAST_TIME, 1, function () {
                        location.reload();
                    });
                } else {
                    showToast("添加失败", TOAST_TIME, -1);
                }
            },
            error: function (err) {
                showToast("添加失败", TOAST_TIME, -1);
                console.log(err);
            },
            complete: function () {
                NProgress.done();
            }
        });
    }
}

