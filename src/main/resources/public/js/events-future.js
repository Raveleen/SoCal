/**
 * Created by Святослав on 15.03.2017.
 */
$(document).ready(function () {
    var load_more_future_events = "<div id=\"load-more-future-events\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><hr class=\"middle\"></div><div class=\"col-sm-12 load-more\"><a id=\"load-more-future-events-href\"><span id=\"plus\" class=\"glyphicon glyphicon-plus\"></span><span class=\"glyphicon glyphicon-menu-up hidden\"></span></a></div><div class=\"col-sm-12\"><hr class=\"middle\"></div></div></div>";
    var user_id = $("#ids").text();
    var from = 0;
    var flag = true;
    var special_alert_no_future_events = "<div id=\"special-alert-no-future-events\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div class=\"no-more-events\"><h5>THERE IS NO EVENTS.</h5></div></div></div><hr class=\"middle\"></div>";
    $("#future-events").append(special_alert_no_my_future_events);
    getFirstTen(user_id, "/get-future-events/", "#special-alert-no-future-events", "#future-events", load_more_future_events);
    $(document.body).on("click", "#load-more-future-events-href", function () {
        var array = getNextTen(user_id, from, "/get-future-events/", "#future-events", load_more_future_events);
        from = array[0];
    });
});