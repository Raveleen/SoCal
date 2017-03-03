/**
 * Created by Святослав on 01.02.2017.
 */
$(document).ready(function () {
    var special_alert_1 = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-users\"><h5>THIS USER HAS NO FOLLOWERS.</h5></div></div></div><hr class=\"middle\"></div>";
    var from = 0;
    var user_id = $("#ids").text();
    var flag = true;
    $.get("/is-following/" + $("#ids").text(), function (data) {
        followStartingManipulation(data);
    });
    $("#button-follow").click(function () {
        $.get("/follow/" + $("#ids").text(), function (data) {
            if (data == "followed") {
                $.get("/user/followers-number/" + $("#ids").text(), function (data) {
                    $("#followers").text(data);
                });
                $("#follow").hide();
                $("#unfollow").show();
            }
        });
    });
    $("#button-unfollow").click(function () {
        $.get("/unfollow/" + $("#ids").text(), function (data) {
            if (data == "unfollowed") {
                $.get("/user/followers-number/" + $("#ids").text(), function (data) {
                    $("#followers").text(data);
                });
                $("#unfollow").hide();
                $("#follow").show();
            }
        });
    });
    $.get("/user-list/followers/" + user_id + "/0", function (data) {
        var i = 0;
        var array = data;
        if (array[0] == null) {
            $("#followers-container").append('' + special_alert_1);
        } else {
            while (i < data.length) {
                $("#followers-container").append('' + array[i]);
                i++;
            }
        }
    });
    //_Getting users dynamically on scrolling.
    $(window).scroll(function () {
        if (($(window).scrollTop() + $(window).height() > $(document).height() - 50) && (flag == true)) {
            from += 10;
            $.get("/user-list/followers/" + user_id + "/" + from, function (data) {
                var i = 0;
                var array = data;
                if (array[0] == null) {
                    flag = false;
                } else {
                    while (i < data.length) {
                        $("#followers-container").append('' + array[i]);
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