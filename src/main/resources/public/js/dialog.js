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
    function concatMessage(array) {
        var string = "";
        if (array[0] === "-1") {
            string += "<div class=\"unread-message comment appended-result row\" id=\"";
            string += array[1];
            string += "\">";
        } else {
            string += "<div id=\"";
            string += array[1];
            string += "\" class=\"comment appended-result row\">";
        }
        string += "<div class=\"col-sm-3 profile-usertitle-small\"><div class=\"col-sm-12\"><div class=\"profile-usertitle-name-small\"><p class=\"user-name\"><a class=\"user-name\" href=\"/user/";
        string += array[2];
        string += "\">";
        string += array[3];
        string += "</a></p></div></div><div class=\"col-sm-12\"><p class=\"post-body-date\">";
        string += array[4];
        string += "</p></div></div><div class=\"col-sm-9\"><p class=\"post-comment-text\">";
        string += array[5];
        string += "</p></div></div>";
        return string;
    }
    $.get("/messages/" + dialog_id + "/0", function (data) {
        var i = 1;
        if (data[1] === null) {
        } else {
            while (i < data.length) {
                $("#dialog-container").prepend('' + concatMessage(data[i]));
                i++;
                if ((i === data.length) && (data.length === 11)) {
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
                if (data[0] === null) {
                } else {
                    while (i < data.length) {
                        $("#dialog-container").prepend('' + concatMessage(data[i]));
                        if ((i === data.length - 1 ) && (data.length === 11)) {
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
            $("#last-message-time").text(data[0]);
            while (i < data.length) {
                $("#dialog-container").append('' + concatMessage(data[i]));
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