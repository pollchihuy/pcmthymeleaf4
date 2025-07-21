$(document).ready(function () {

    $(document).on("submit", "#formRegis", function (e) {
        e.preventDefault();
        var pwd = $("#password").val();
        var usrn = $("#username").val();

        var regPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\-$])[\w].{8,15}$/;
        var isOk = true;

        if(!regPattern.test(pwd)) {
            isOk = false;
            document.getElementById("passwordMessage").innerHTML = "Format Password Tidak Valid !!";
        }
        regPattern = /^([a-z0-9\.]{8,16})$/;
        if(!regPattern.test(usrn)) {
            isOk = false;
            document.getElementById("usernameMessage").innerHTML = "Format Username Tidak Valid !!";
        }

        if(isOk){
            var pwdEncrypt = btoa(pwd);
            $("#password").val(pwdEncrypt);
            var post_url = $(this).attr("action");
            var request_method = $(this).attr("method");
            let form_data = new FormData(this);
            $.ajax({
                url:post_url,
                type:request_method,
                data:form_data,
                enctype: 'multipart/form-data',
                contentType: false,
                processData: false,
            }).done(function (resp) {
                $('html').html(resp);
            })
        }
    })

})