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

        /* var sendingMass = [];*/

        /*alert(JSON.stringify(formData));*/
        // DO POST
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
            /*success : function(result) {   alert("222");
                /*if(result.status == "Done"){
                    $("#postResultDiv").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" +
                                                "Post Successfully! <br>" +
                                                "---> Customer's Info: FirstName = " +
                                                result.data.firstname + " ,LastName = " + result.data.lastname + "</p>");
                }else{
                    $("#postResultDiv").html("<strong>Error</strong>");
                }
                console.log(result);
            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }*/
        });

        // очистка поля сообщения
        resetData();

    }

    function resetData(){
        $("#message").val("");
    }
})