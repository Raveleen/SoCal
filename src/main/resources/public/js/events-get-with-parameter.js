/**
 * Created by Святослав on 14.03.2017.
 */
var load_more_my_past_events = "<div id=\"load-more-my-past-events\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><hr class=\"middle\"></div><div class=\"col-sm-12 load-more\"><a id=\"load-more-my-past-events-href\"><span id=\"plus\" class=\"glyphicon glyphicon-plus\"></span><span class=\"glyphicon glyphicon-menu-up hidden\"></span></a></div><div class=\"col-sm-12\"><hr class=\"middle\"></div></div></div>";

function getFirstTen(user_id, url, alert_to_remove_id, container_to_append, href_to_append) {
    $.get(url + user_id + "/0", function (data) {
        var i = 0;
        if (data[0] !== null) {
            $(alert_to_remove_id).remove();
            while (i < data.length) {
                $(container_to_append).append('' + concatEvent(data[i]));
                if ((i === data.length - 1 ) && (data.length === 10)) {
                    $(container_to_append).append('' + href_to_append);
                }
                i++;
            }
        }
    });
}

function getNextTen(user_id, from, url, container_to_append, href_to_append) {
    var flag = false;
    from += 10;
    $.get(url + user_id + "/" + from, function (data) {
        var i = 0;
        $(href_to_append).remove();
        if (data[0] == null) {
            flag = false;
        } else {
            while (i < data.length) {
                $(container_to_append).append('' + concatEvent(data[i]));
                if ((i == data.length - 1 ) && (data.length < 10)) {
                    from = 0;
                    flag = false;
                } else {
                    $(container_to_append).append('' + href_to_append);
                }
                i++;
            }
        }
    });
    return [flag, from];
}