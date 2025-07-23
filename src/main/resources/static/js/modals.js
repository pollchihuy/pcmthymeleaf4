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