/**
 * Created by Святослав on 16.03.2017.
 */
$(document).ready(function () {
    $(document.body).on("click", ".event-delete-button", function () {
        var id = $(this).closest(".event").attr("id");
        $.ajax({
            url: '/event-delete/' + id,
            type: 'POST',
            contentType: false,
            processData: false,
            success: function () {
                $("#" + id).remove();
            }
        })
    });
});