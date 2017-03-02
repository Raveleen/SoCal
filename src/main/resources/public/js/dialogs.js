/**
 * Created by Святослав on 02.02.2017.
 */
$(document).ready(function () {
    var special_alert_1 = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-users\"><h5>THERE IS NO DIALOGS YET.</h5></div></div></div><hr class=\"middle\"></div>";
    var from = 0;
    var user_id = $("#ids").text();
    var flag = true;
    $.get("/dialogs/0", function (data) {
        var i = 0;
        var array = data;
        if (array[0] === null) {
            $("#dialogs-container").append('' + special_alert_1);
        } else {
            while (i < data.length) {
                $("#dialogs-container").append('' + array[i]);
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
                var array = data;
                if (array[0] === null) {
                    flag = false;
                } else {
                    while (i < data.length) {
                        $("#dialogs-container").append('' + array[i]);
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