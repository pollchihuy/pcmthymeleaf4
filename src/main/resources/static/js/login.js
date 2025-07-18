$(document).ready(function () {

    $(document).on("submit", "#formLogin", function (e) {
        e.preventDefault();
        var pwd = $("#password").val();
        var usrn = $("#username").val();

        var regPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\-$])[\w].{8,15}$/;
        var isOk = true;

        if(!regPattern.test(pwd)) {
            isOk = false;
            document.getElementById("errorPwd").innerHTML = "Format Password Tidak Valid !!";
        }
        regPattern = /^^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?![.])[a-zA-Z0-9._%+-]+(?:(?<!\\)[.][a-zA-Z0-9-]+)*?)@[a-zA-Z0-9.-]+(?:\.[a-zA-Z]{2,50})+$|^([a-z0-9\.]{8,16})$|^(62|\+62|0)8[0-9]{9,13}$$/;
        if(!regPattern.test(usrn)) {
            isOk = false;
            document.getElementById("errorUsername").innerHTML = "Format Username Tidak Valid !!";
        }

        if(isOk){
            var pwdEncrypt = btoa(pwd);
            $("#password").val(pwdEncrypt);
            var post_url = $(this).attr("action");
            var request_method = $(this).attr("method");
            var form_data = $(this).serialize();
            $.ajax({
                url:post_url,
                type:request_method,
                data:form_data
            }).done(function (resp) {
                var pattern = /oajisdoiasdihoajsodijasd/i;
                var result = resp.match(pattern);
                if(result) {
                    $('html').html(resp);// terjadi error
                }else {
                    window.location = "/home";
                }
            })
        }
    })

})