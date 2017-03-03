/**
 * Created by Святослав on 03.03.2017.
 */
function removeHiddenAndHide(elementOneID, elementTwoID) {
    $(elementOneID).removeClass("hidden");
    $(elementTwoID).hide();
    $(elementTwoID).removeClass("hidden");
}
function updateFollowSection(hideID, showID) {
    $.get("/user/followers-number/" + $("#ids").text(), function (data) {
        $("#followers").text(data);
    });
    $(hideID).hide();
    $(showID).show();
}
function followStartingManipulation(data) {
    if (data === true) {
        removeHiddenAndHide("#unfollow", "#follow");
    }
    if (data === false) {
        removeHiddenAndHide("#follow", "#unfollow");
    }
}
function buttonFollowClicked() {
    $.get("/follow/" + $("#ids").text(), function (data) {
        if (data === "followed") {
            updateFollowSection("#follow", "#unfollow");
        }
    });
}
function buttonUnfollowClicked() {
    $.get("/unfollow/" + $("#ids").text(), function (data) {
        if (data === "unfollowed") {
            updateFollowSection("#unfollow", "#follow");
        }
    });
}
$(document).ready(function () {
    $.get("/is-following/" + $("#ids").text(), function (data) {
        followStartingManipulation(data);
    });
    $("#button-follow").click(function () {
        buttonFollowClicked();
    });
    $("#button-unfollow").click(function () {
        buttonUnfollowClicked();
    });
});