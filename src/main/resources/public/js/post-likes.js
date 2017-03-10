/**
 * Created by Святослав on 01.02.2017.
 */
$(document).ready(function () {
    var special_alert_1 = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-users\"><h5>NOBODY LIKED POST.</h5></div></div></div><hr class=\"middle\"></div>";
    var from = 0;
    var post_id = $("#post-id").text();
    var flag = true;
    $.get("/user-list/likes/" + post_id + "/0", function (data) {
        var i = 0;
        if (data[0] == null) {
            $("#users-container").append('' + special_alert_1);
        } else {
            while (i < data.length) {
                $("#users-container").append('' + concatUsers(data[i]));
                i++;
            }
        }
    });
    //_Getting users dynamically on scrolling.
    $(window).scroll(function () {
        if (($(window).scrollTop() + $(window).height() > $(document).height() - 50) && (flag == true)) {
            from += 10;
            $.get("/user-list/likes/" + post_id + "/" + from, function (data) {
                var i = 0;
                if (data[0] == null) {
                    flag = false;
                } else {
                    while (i < data.length) {
                        $("#users-container").append('' + concatUsers(data[i]));
                        if ((i == data.length - 1 ) && (data.length < 10)) {
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