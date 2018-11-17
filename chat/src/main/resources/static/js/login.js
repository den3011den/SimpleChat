$(window, document, undefined).ready(function() {

    $('input').blur(function() {
        var $this = $(this);
        if ($this.val())
            $this.addClass('used');
        else
            $this.removeClass('used');
    });

    var $ripples = $('.ripples');

    $ripples.on('click.Ripples', function(e) {

        var $this = $(this);
        var $offset = $this.parent().offset();
        var $circle = $this.find('.ripplesCircle');

        var x = e.pageX - $offset.left;
        var y = e.pageY - $offset.top;

        $circle.css({
            top: y + 'px',
            left: x + 'px'
        });

        $this.addClass('is-active');

    });

    $ripples.on('animationend webkitAnimationEnd mozAnimationEnd oanimationend MSAnimationEnd', function(e) {
        $(this).removeClass('is-active');
    });

});

$( document ).ready(function() {

    // SUBMIT FORM
    $("#sendform").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });


    function ajaxPost(){

        // PREPARE FORM DATA
        var formData = {
            login : $("#login").val(),
            message :  $("#message").val()
        };

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "/sendmessage",
            data : JSON.stringify(formData),
            dataType : 'json',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function(jsondata) {
                if (jsondata.success == "true") {
                    resetData();
                    $("#postResultDiv").html("<p></p>");
                }
                else  {
                    $("#postResultDiv").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" +
                        "Не удалось отправить сообщение! Сервер получил данные, но не смог сохранить их в БД <br>" + "</p>");
                }
            },
            error : function(e) {
                $("#postResultDiv").html("<div style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" +
                    "Не удалось отправить сообщение! Возможно сервер не доступен. <br>" + "</div>");
            }
        });
    };

    function resetData(){
        $("#message").val("");
    }

});
