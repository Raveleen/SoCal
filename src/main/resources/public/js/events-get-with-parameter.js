/**
 * Created by Святослав on 14.03.2017.
 */
function getFirstTen(user_id, url, alert_to_remove_id, alert_to_append, container_to_append) {
    $.get(url + user_id + "/0", function (data) {
        var i = 0;
        if (data[0] == null) {
            $("#followers-container").append('' + special_alert_1);
        } else {
            while (i < data.length) {
                $("#followers-container").append('' + concatEvent(data[i]));
                i++;
            }
        }
    });
}

function getNextTen(from, user_id, url, alert_to_remove_id, alert_to_append, container_to_append) {
    var flag = false;
    from += 10;
    $.get(url + user_id + "/" + from, function (data) {
        var i = 0;
        if (data[0] == null) {
            flag = false;
        } else {
            while (i < data.length) {
                $("#followers-container").append('' + concatEvent(data[i]));
                if ((i == data.length - 1 ) && (data.length < 10)) {
                    from = 0;
                    flag = false;
                }
                i++;
            }
        }
    });
    return [flag, from];
}