/**
 * Created by Святослав on 11.02.2017.
 */
$(document).ready(function () {
    var special_alert_no_more_posts = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-posts\"><h5>THERE IS NO MORE POSTS.</h5></div></div></div><hr class=\"middle\"></div>";
    var special_alert_no_posts = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-posts\"><h5>THERE IS NO POSTS.</h5></div></div></div><hr class=\"middle\"></div>";
    var from = 0;
    var flag = true;
    var user_id = $("#ids").text();
    //GETTING POSTS.
    //_Getting first posts on load.
    $.get("/get-following-posts/" + user_id + "/0", function (data) {
        var i = 0;
        var array = data;
        if (array[0][0] === null) {
            $("#special-alert").remove();
            $("#posts-container").append('' + special_alert_no_posts);
        } else {
            while (i < data.length) {
                if (data[i][0] !== null) {
                    $("#posts-container").append('' + concatPost(data[i], false));
                } else {
                    $("#special-alert").remove();
                    $("#posts-container").append('' + special_alert_no_more_posts);
                }
                i++;
            }
        }
    });
    //_Getting posts dynamically on scrolling.
    $(window).scroll(function () {
        if (($(window).scrollTop() + $(window).height() > $(document).height() - 50) && (flag === true)) {
            from += 10;
            $.get("/get-following-posts/" + user_id + "/" + from, function (data) {
                var i = 0;
                var array = data;
                if (array[0][0] === null) {
                    flag = false;
                    $("#special-alert").remove();
                    $("#posts-container").append('' + special_alert_no_more_posts);
                } else {
                    while (i < data.length) {
                        if (data[i][0] !== null) {
                            $("#posts-container").append('' + concatPost(data[i], false));
                        } else {
                            $("#special-alert").remove();
                            $("#posts-container").append('' + special_alert_no_more_posts);
                            flag = false;
                        }
                        i++;
                    }
                }
            });
        }
    });
});