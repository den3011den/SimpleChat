$(document).ready(function () {

    function ajaxAskNewMessages() {

        // PREPARE FORM DATA
        var formData = {
            login: $("#login").val(),
            message: ""
        };

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getlatestmessages",
            data: JSON.stringify(formData),
            dataType: 'json',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },

            /*          complete: function(jqXHR, textStatus) {
                           alert(textStatus);
                       },*/
            success: function (jsondata) {

                $.each(jsondata, function (index, value) {
                    /*alert("login=" + value['login'] + " message=" + value['message']);*/
                    var cnt = $("#currentCount").val();
                    var mx = $("#maxCount").val();

                    var number = Number(cnt) - Number(mx);

                    var nameVar = "block_" + number.toString();

                    $("." + nameVar).remove();


                    cnt = Number(cnt) + Number(1);

                    var number = Number(cnt);
                    var nameVar = "block_" + number.toString();

                    if (value['login'] == $("#login").val()) {

                        $(".messagesDiv").prepend("<div class=" + nameVar.toString() + "><div class='message own-message'><b><em>" + value['login'] + "</em></b><br>" +
                            value['message'] + "</div></div>");
                    }
                    else {

                        $(".messagesDiv").prepend("<div class=" + nameVar.toString() + "><div class='message other-message'><b><em>" + value['login'] + "</em></b><br>" +
                            value['message'] + "</div></div>");
                    }

                    $(".currentCount").remove();
                    $("#varDives").html('<input type="hidden" id="maxCount" name="maxCount" class="maxCount" value="' + mx.toString() + '"/>' +
                        '<input type="hidden" id="currentCount" name="currentCount" class="currentCount" value="' + cnt.toString() + '"/>');

                });
            },
            error: function (e) {
                $("#postResultDiv").html("<div class='postResultDiv'>" +
                    "Не удалось получить данные с сервера <br>" + "</div>");
            }
        });
    }

    setInterval(ajaxAskNewMessages, Number($("#pageTickTime").val()));
    /*setTimeout(function run() {
       ajaxAskNewMessages;
       setTimeout(run, 5000);
   }, 5000);*/

});

$(document).ready(function () {

    // SUBMIT FORM
    $("#sendform").submit(function (event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });


    function ajaxPost() {

        // PREPARE FORM DATA
        var formData = {
            login: $("#login").val(),
            message: $("#message").val()
        };

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/sendmessage",
            data: JSON.stringify(formData),
            dataType: 'json',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (jsondata) {
                if (jsondata.success == "true") {
                    resetData();
                    $("#postResultDiv").html("<p></p>");
                }
                else {
                    $("#postResultDiv").html("<div id='postResultDiv' class='postResultDiv'>" +
                        "Не удалось отправить сообщение! Сервер получил данные, но не смог сохранить их в БД <br>" + "</div>");
                }
            },
            error: function (e) {
                $("#postResultDiv").html("<div id='postResultDiv' class='postResultDiv'>" +
                    "Не удалось отправить сообщение! Возможно сервер не доступен. <br>" + "</div>");
            }
        });
    };

    function resetData() {
        $("#message").val("");
    }

});