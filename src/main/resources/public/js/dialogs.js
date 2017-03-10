/**
 * Created by Святослав on 02.02.2017.
 */
$(document).ready(function () {
    var special_alert_1 = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-users\"><h5>THERE IS NO DIALOGS YET.</h5></div></div></div><hr class=\"middle\"></div>";
    var from = 0;
    var user_id = $("#ids").text();
    var flag = true;
    function concatDialog(array) {
        var string = "<div id=\"";
        string += array[0];
        string += "\" onclick=\"location.href='/message-to/";
        string += array[1];
        string += "'\" class=\"search appended-result\"><div class=\"row search-result dialogs\"><div class=\"col-sm-2\"><div>";
        if (array[2] === "-1") {
            string += "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/images/default-user-image.png\">";
        } else {
            string += "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/profile-image/";
            string += array[2];
            string += "\">";
        }
        string += "</div></div><div class=\"col-sm-6\"><div class=\"col-sm-12\"><div class=\"profile-usertitle-small\"><div class=\"profile-usertitle-name-small-dialog\"><p class=\"user-name\"><a class=\"user-name\" href=\"/user/";
        string += array[1];
        string += "\">";
        string += array[3];
        string += "</a></p></div></div></div><div class=\"col-sm-12\"><p class=\"post-body-date-dialog\">";
        if (array[5] === null) {
            if (array[4] === "-1") {
                string += "There is no messages yet";
            } else {
                string += "<span class=\"pink\">You have ";
                string += array[4];
                string += " unread mesages</span>";
            }
        } else {
            string += "Last message ";
            string += array[5];
        }
        string += "</p></div></div><div class=\"col-sm-4\"></div><div class=\"row search-result\"></div><hr class=\"middle\"></div>";
        return string;
    }
    $.get("/dialogs/0", function (data) {
        var i = 0;
        if (data[0] === null) {
            $("#dialogs-container").append('' + special_alert_1);
        } else {
            while (i < data.length) {
                $("#dialogs-container").append('' + concatDialog(data[i]));
                i++;
            }
        }
    });
    //_Getting dialogs dynamically on scrolling.
    $(window).scroll(function () {
        if (($(window).scrollTop() + $(window).height() > $(document).height() - 50) && (flag == true)) {
            from += 10;
            $.get("/dialogs/" + from, function (data) {
                var i = 0;
                if (data[0] === null) {
                    flag = false;
                } else {
                    while (i < data.length) {
                        $("#dialogs-container").append('' + concatDialog(data[i]));
                        if ((i === data.length - 1 ) && (data.length < 10)) {
                            from = 0;
                            flag = false;
                        }
                        i++;
                    }
                }
            });
        }
    });
});