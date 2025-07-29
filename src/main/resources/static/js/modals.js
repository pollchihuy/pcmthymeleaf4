function funcModalsHandler(event)
{
    event.preventDefault(); //prevent default action
    var button = event.target
    var dataTitle = button.dataset.title
    var dataTarget = button.dataset.target
    var urlz = button.dataset.url
    var serverz = button.dataset.server
    $(dataTarget).one('show.bs.modal',function(){
        // $(this).data('bs.modal')._config.backdrop = 'static';
        // $(this).data('bs.modal')._config.keyboard = false;
        $.get(urlz, function (data) {
            let pattern = /oajisdoiasdihoajsodijasd/i;
            let result = data.match(pattern);
            try{
                if(result){
                    confirm("TERJADI KESALAHAN DI SERVER");
                    $('add1').modal('hide');
                    window.location = "/logout";
                }else{
                    $(serverz).html(data);
                }
            }catch(r)
            {
                console.log('error '+r)
            }finally
            {
                $(dataTarget).find('.modal-title').text(dataTitle)
            }
        });
    })
};

function funcModalsDataTableHandler(dataTitle,dataTarget,urlz,serverz)
{
    $(dataTarget).one('show.bs.modal',function(){
        $.get(urlz, function (data) {
            try{
                $(serverz).html(data);
            }catch(r)
            {
                console.log('error '+r)
            }finally
            {
                $(dataTarget).find('#data-title').text(dataTitle)
            }
        });
    })
};

function requestDeleteHandler(event)
{
    event.preventDefault();
    var button = event.target
    var urlz = button.dataset.url
    var dataTitle = button.dataset.title
    $.ajax({
        url: urlz,
        type: 'post'
    }).done(function(response) {
        var pattern = /SUCCESS/i;
        var result = response.match(pattern);

        if(result){
            confirm('Data Berhasil Dihapus');
            window.location = "/"+dataTitle;
        }else {
            window.location = "/"+dataTitle+"/err/500";
        }
    });
};

function funcModalsDataMasterHandler(idComp,descComp,idVal,descVal)
{
    $(idComp).val(idVal);
    $(descComp).val(descVal);
    $('#dataTable').modal('hide');
};

function callDataMaster()
{
    var delimiter= '/';
    var sort = $('#sort').val();
    var idComp = $('#idComp').val();
    var descComp = $('#descComp').val();
    var path = $('#pathServer').val();
    var sortBy = $('#sortBy').val();
    var page = $('#currentPage').val();
    var column = $('#colName').val();
    var value = $('#textVal').val();
    var size = $('#sizeChange').val();
    var total = $('#totalData').val();
    page = ((page * size) > total)?0:page;
    var urlz =  delimiter.concat(path,'/',idComp,'/',descComp,'/',sort,'/',sortBy,'/',page,'?column=',column,'&value=',value,'&size=',size);
    $.get(urlz, function (data) {
        try{
            $('#data-result').html(data);
        }catch(r)
        {
            console.log('error '+r)
        }
    });
};

function callDataMasterPaging(page)
{
    var delimiter= '/';
    var sort = $('#sort').val();
    var idComp = $('#idComp').val();
    var descComp = $('#descComp').val();
    var path = $('#pathServer').val();
    var sortBy = $('#sortBy').val();
    var column = $('#colName').val();
    var value = $('#textVal').val();
    var size = $('#sizeChange').val();
    var urlz =  delimiter.concat(path,'/',idComp,'/',descComp,'/',sort,'/',sortBy,'/',page,'?column=',column,'&value=',value,'&size=',size);
    $.get(urlz, function (data) {
        try{
            $('#data-result').html(data);
        }catch(r)
        {
            console.log('error '+r)
            // $('#pilih-data').prop('disabled', false);
        }
    });
}

function checkAll(checkEm,divNames) {
    var cbs = document.getElementsByTagName('input');
    for (var i = 0; i < cbs.length; i++) {
        if (cbs[i].type == 'checkbox') {
            if (cbs[i].name == divNames) {
                cbs[i].checked = checkEm;
            }
        }
    }
}

function callDataMasterSorting(sort,sortBy)
{
    var delimiter= '/';
    var idComp = $('#idComp').val();
    var descComp = $('#descComp').val();
    var path = $('#pathServer').val();
    var column = $('#colName').val();
    var page = $('#currentPage').val();
    var value = $('#textVal').val();
    var size = $('#sizeChange').val();
    var urlz =  delimiter.concat(path,'/',idComp,'/',descComp,'/',sort,'/',sortBy,'/',page,'?column=',column,'&value=',value,'&size=',size);
    $.get(urlz, function (data) {
        try{
            $('#data-result').html(data);
        }catch(r)
        {
            console.log('error '+r)
            // $('#pilih-data').prop('disabled', false);
        }
    });
}