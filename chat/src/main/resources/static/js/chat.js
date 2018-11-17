$( document ).ready(function() {

    function ajaxAskNewMessages(){

        // PREPARE FORM DATA
        var formData = {
            login : $("#login").val(),
            message :  ""
        };

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "/getlatestmessages",
            data : JSON.stringify(formData),
            dataType : 'json',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },

 /*          complete: function(jqXHR, textStatus) {
                alert(textStatus);
            },*/
            success: function(jsondata) {

                 $.each(jsondata,function(index,value) {
                      /*alert("login=" + value['login'] + " message=" + value['message']);*/
                     var cnt = $("#currentCount").val();
                     var mx = $("#maxCount").val();

                     var number = Number(cnt)- Number(mx);

                     var nameVar = "block_" + number.toString();

                     $("."+nameVar).remove();


                     cnt = Number(cnt) + Number(1);

                     var number = Number(cnt);
                     var nameVar = "block_" + number.toString();

                     if (value['login'] == $("#login").val()) {

                            $(".messagesDiv").prepend("<div class=" +nameVar.toString() +"><div class='message own-message'><b><em>" + value['login'] + "</em></b><br>" +
                            value['message']+"</div></div>");
                     }
                     else {

                         $(".messagesDiv").prepend("<div class=" +nameVar.toString() +"><div class='message other-message'><b><em>" + value['login'] + "</em></b><br>" +
                             value['message']+"</div></div>");
                      }

                     $(".currentCount").remove();
                     $("#varDives").html('<input type="hidden" id="maxCount" name="maxCount" class="maxCount" value="' + mx.toString() + '"/>' +
                         '<input type="hidden" id="currentCount" name="currentCount" class="currentCount" value="' + cnt.toString() + '"/>');

                 });
             },
            error : function(e) {
                  $("#postResultDiv").html("<div style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" +
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
