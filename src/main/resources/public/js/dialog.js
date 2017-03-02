/**
 * Created by Святослав on 02.02.2017.
 */
$(document).ready(function () {
    $("#create-message-button").prop("disabled", true);
    var special_alert_1 = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-users\"><h5>THERE IS NO DIALOGS YET.</h5></div></div></div><hr class=\"middle\"></div>";
    var load_more_messages = "<div id=\"load-more-messages\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><hr class=\"middle\"></div><div class=\"col-sm-12 load-more\"><a id=\"load-messages-href\"><span id=\"plus\" class=\"glyphicon glyphicon-plus\"></span><span class=\"glyphicon glyphicon-menu-up hidden\"></span></a></div><div class=\"col-sm-12\"><hr class=\"middle\"></div></div></div>";
    var from = 0;
    var user_id = $("#ids").text();
    var dialog_id = $("#dialog-id").text();
    var flag = true;
    $.get("/messages/" + dialog_id + "/0", function (data) {
        var i = 0;
        var array = data;
        if (array[0] === null) {
        } else {
            while (i < data.length) {
                $("#dialog-container").prepend('' + array[i]);
                i++;
                if ((i === data.length) && (data.length === 10)) {
                    $("#dialog-container").prepend('' + load_more_messages);
                }
            }
        }
    });
    $(document.body).on("click", "#load-messages-href", function () {
        from += 10;
        $("#load-more-messages").remove();
        $.ajax({
            url: '/messages/' + dialog_id + '/' + from,
            type: 'GET',
            contentType: false,
            processData: false,
            success: function (data) {
                var i = 0;
                var array = data;
                if (array[0] === null) {
                } else {
                    while (i < data.length) {
                        $("#dialog-container").prepend('' + array[i]);
                        if ((i === data.length - 1 ) && (data.length < 10)) {
                        } else if ((i === data.length - 1 ) && (data.length === 10)) {
                            $("#dialog-container").prepend('' + load_more_messages);
                        }
                        i++;
                    }
                }
            }
        })
    });
    $("#create-message-button").click(function () {
        var form = $('form')[0];
        var formData = new FormData(form);
        $.ajax({
            url: '/message-create/' + dialog_id,
            data: formData,
            type: 'POST',
            contentType: false,
            processData: false,
            success: function (data) {
                document.getElementById("send-message").reset();
                $("#characters-number").text(1000);
                $("#create-message-button").prop("disabled", true);
            }
        })
    });
    var myVar = setInterval(refresh, 1000);

    function refresh() {
        $.get("/messages-get-unread/" + dialog_id + "/" + $("#last-message-time").text(), function (data) {
            var i = 1;
            var array = data;
            $("#last-message-time").text(array[0]);

            while (i < data.length) {
                $("#dialog-container").append('' + array[i]);
                if (i > 1) {
                    from += 1;
                }
                i++;
            }
        });
    }
    $("#mesage-text").keyup(function () {
        if($("#mesage-text").val().length === 0) {
            $("#create-message-button").prop("disabled", true);
        } else {
            $("#create-message-button").prop("disabled", false);
        }
        $("#characters-number").text(1000 - $("#mesage-text").val().length);
    });
    $(document.body).on("mouseover", ".unread-message", function () {
        $(this).removeClass("unread-message");
    });
});