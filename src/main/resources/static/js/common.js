/**
 * Created by L.H.S on 2017/12/16.
 */
NProgress.start();

function login() {

    var username = $("#username").val();
    var password = $("#password").val();

    $.ajax({
        url: '/user/login',
        type: 'POST',
        data: {
            id: username,
            password: password
        },
        success: function (resp) {
            if (resp === 'no_user') {
                alert(resp);
            } else if (resp === 'wrong_password') {
                alert(resp);
            } else if (resp === 'student') {
                sessionStorage.setItem('studentId', username);
                location.href = '/student-entry';
            } else {
                sessionStorage.setItem("teacherId", username);
                location.href = '/exam-list';
            }
        },
        error: function (err) {
            alert('登录失败');
            console.log(err);
        }
    });

}

function register() {

    var id = $("#username").val();
    var password = $("#password").val();
    var name = $("#name").val();
    var grade = $("#grade").val();
    var email = $("#email").val();
    var code = $("#verify_code").val();
    var role = $("input:radio:checked").val();

    $.ajax({
        url: '/user/register',
        type: 'POST',
        data: {
            id: id,
            name: name,
            grade: grade,
            password: password,
            email: email,
            verificationCode: code,
            role: role
        },
        success: function () {
            location.href = '/login';
        },
        error: function (err) {
            alert('注册失败');
            console.log(err);
        }
    });
}

function switchModal(flag, id) {
    if (flag) {
        $("#" + id).fadeIn(300);
    } else {
        $("#" + id).fadeOut(300);
    }
}