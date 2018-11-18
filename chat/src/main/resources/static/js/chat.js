
/* запрос клиентом новых сообщений для отображения */
$(document).ready(function () {

    function ajaxAskNewMessages() {

        // нужен только логин. поле message всегда пустое
        var formData = {
            login: $("#login").val(),
            message: ""
        };

        // формируем запрос серверу на получение новых сообщений для логина login
        // с момента поледнего получения новых сообщений или с момента логина пользователя в системе
        // запрос в формате json (по типу объекта сервера MessageDTO)
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
            // запрос прошёл успешно
            success: function (jsondata) {
                // обработка списка полученных новых сообщений от сервера
                $.each(jsondata, function (index, value) {
                    // счётчик выводимых сообщений на html-страницы чата для текущей сессии
                    var cnt = $("#currentCount").val();
                    // максимальное количество сообщений, отображаемых одновременно на форме + 1
                    var mx = $("#maxCount").val();

                    // номер блока с самым старым сообщение для удаления со страницы
                    var number = Number(cnt) - Number(mx);

                    var nameVar = "block_" + number.toString();

                    $("." + nameVar).remove();

                    // увеличение счётчика
                    cnt = Number(cnt) + Number(1);

                    number = Number(cnt);
                    nameVar = "block_" + number.toString();

                    if (value['login'] === $("#login").val()) { // вывод "своего" сообщения

                        $(".messagesDiv").prepend("<div class=" + nameVar.toString() + "><div class='message own-message'><b><em>" + value['login'] + "</em></b><br>" +
                            value['message'] + "</div></div>");
                    }
                    else {     // вывод "чужого" сообщения

                        $(".messagesDiv").prepend("<div class=" + nameVar.toString() + "><div class='message other-message'><b><em>" + value['login'] + "</em></b><br>" +
                            value['message'] + "</div></div>");
                    }

                    $(".currentCount").remove();

                    // запоминание в форме текущего счётчика сообщений и их  максимального количества
                    $("#varDives").html('<input type="hidden" id="maxCount" name="maxCount" class="maxCount" value="' + mx.toString() + '"/>' +
                        '<input type="hidden" id="currentCount" name="currentCount" class="currentCount" value="' + cnt.toString() + '"/>');

                });
            },
            error: function (e) {  // неудача при запросе сервера клиентом
                $("#postResultDiv").html("<div class='postResultDiv'>" +
                    "Не удалось получить данные с сервера <br>" + "</div>");
            }
        });
    }

    // интервал запроса клиентом новых сообщений у сервера
    // по заданию : 1000 мс
    setInterval(ajaxAskNewMessages, Number($("#pageTickTime").val()));

});


/* отправка клиентом своих новых сообщений на сервер по кнопке */
$(document).ready(function () {

    // SUBMIT FORM
    $("#sendform").submit(function (event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });


    /* функция отправки клиентом своих сообщений на сервер */
    function ajaxPost() {

        // PREPARE FORM DATA
        var formData = {
            login: $("#login").val(),
            message: $("#message").val()
        };

        // передача клиенту в формате json по типу объкта сервера MessageDTO
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
            // запрос клиента к серверу прошёл усвешно
            success: function (jsondata) {
                if (jsondata.success === "true") {  // серверу удалось принять данные и записать в БД
                    resetData();
                    $("#postResultDiv").html("<p></p>");
                }
                else {  // серверу удалось получить данные, но запись в БД не удалась
                    $("#postResultDiv").html("<div id='postResultDiv' class='postResultDiv'>" +
                        "Не удалось отправить сообщение! Сервер получил данные, но не смог сохранить их в БД <br>" + "</div>");
                }
            },
            error: function (e) {   // запрос не удачен
                $("#postResultDiv").html("<div id='postResultDiv' class='postResultDiv'>" +
                    "Не удалось отправить сообщение! Возможно сервер не доступен. <br>" + "</div>");
            }
        });
    }
    // очишаем поле сообщения (используется если передача сообщения от клиента к серверу прошла полностью успешно)
    function resetData() {
        $("#message").val("");
    }

});