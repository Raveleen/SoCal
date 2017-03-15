/**
 * Created by Святослав on 11.03.2017.
 */
$(document).ready(function () {
    var load_more_my_future_events = "<div id=\"load-more-my-future-events\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><hr class=\"middle\"></div><div class=\"col-sm-12 load-more\"><a id=\"load-more-my-future-events-href\"><span id=\"plus\" class=\"glyphicon glyphicon-plus\"></span><span class=\"glyphicon glyphicon-menu-up hidden\"></span></a></div><div class=\"col-sm-12\"><hr class=\"middle\"></div></div></div>";
    var user_id = $("#ids").text();
    var from = 0;
    var flag = true;
    var special_alert_no_my_future_events = "<div id=\"special-alert-no-my-future-events\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div class=\"no-more-events\"><h5>THERE IS NO EVENTS.</h5></div></div></div><hr class=\"middle\"></div>";
    $("#my-events").append(special_alert_no_my_future_events);
    var array = getFirstTen(user_id, "/get-my-future-events/", "#special-alert-no-my-future-events", "#my-events", load_more_my_future_events);
    $(document.body).on("click", "#load-more-my-future-events-href", function () {
        var array = getNextTen(user_id, from, "/get-my-future-events/", "#my-events", load_more_my_future_events);
        from = array[0];
    });
});