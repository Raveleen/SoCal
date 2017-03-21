/**
 * Created by Святослав on 14.03.2017.
 */
$(document).ready(function () {
    var load_more_following_future_events = "<div id=\"load-more-following-future-events\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><hr class=\"middle\"></div><div class=\"col-sm-12 load-more\"><a id=\"load-more-following-future-events-href\"><span id=\"plus\" class=\"glyphicon glyphicon-plus\"></span><span class=\"glyphicon glyphicon-menu-up hidden\"></span></a></div><div class=\"col-sm-12\"><hr class=\"middle\"></div></div></div>";
    var user_id = $("#ids").text();
    var from = 0;
    var flag = true;
    var special_alert_no_events = "<div id=\"special-alert-no-events\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div class=\"no-more-events\"><h5>THERE IS NO EVENTS.</h5></div></div></div><hr class=\"middle\"></div>";
    $("#events").append(special_alert_no_events);
    getFirstTen(user_id, "/get-following-events/", "#special-alert-no-events", "#events", load_more_following_future_events);
    $(document.body).on("click", "#load-more-following-future-events-href", function () {
        var array = getNextTen(user_id, from, "/get-following-events/", "#events", "#load-more-following-future-events", load_more_following_future_events);
        from = array[0];
    });
});