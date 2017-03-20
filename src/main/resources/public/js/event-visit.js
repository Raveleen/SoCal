/**
 * Created by Святослав on 16.03.2017.
 */
$(document).ready(function () {
    $(document.body).on("click", ".attend-event-button", function () {
        var id = $(this).attr("id");
        $.ajax({
            url: '/event-visit/' + id,
            type: 'GET',
            contentType: false,
            processData: false,
            success: function () {
                $("#" + id).remove();
            }
        })
    });
});