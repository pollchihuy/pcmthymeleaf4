$(document).ready(function (){
    $(document).on('submit','#formAdd',function(event) {
        event.preventDefault(); //prevent default action
        let post_url = $(this).attr("action"); //get form action url
        let request_method = $(this).attr("method"); //get form GET/POST method
        let form_data = $(this).serialize(); //Encode form elements for submission
        let change_location = $("#changeloc").val();
        $.ajax({
            url: post_url,
            type: request_method,
            data: form_data
        }).done(function(response) { //
            let pattern = /50c9a8c2b1cb45799d8f0db5f8449c65/i;
            let result = response.match(pattern);
            if(result)
            {
                $("#server-results-add").html(response);
            }
            else
            {
                $('#add1').modal('hide');
                confirm("DATA BERHASIL DISIMPAN");
                window.location = "/"+change_location;// ini refresh page nya
            }
        });
    });

    $(document).on('submit','#formEdit',function(event) {
        event.preventDefault(); //prevent default action
        let post_url = $(this).attr("action"); //get form action url
        let request_method = $(this).attr("method"); //get form GET/POST method
        let form_data = $(this).serialize(); //Encode form elements for submission
        let change_location = $("#changeloc").val();
        $.ajax({
            url: post_url,
            type: request_method,
            data: form_data
        }).done(function(response) { //
            let pattern = /50c9a8c2b1cb45799d8f0db5f8449c65/i;
            let result = response.match(pattern);
            if(result)
            {
                $("#server-results-edit").html(response);
            }
            else
            {
                $('#edit1').modal('hide');
                confirm("DATA BERHASIL DIUBAH");
                window.location = "/"+change_location;// ini refresh page nya
            }
        });
    });
})