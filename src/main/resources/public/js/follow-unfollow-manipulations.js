/**
 * Created by Святослав on 03.03.2017.
 */
function followStartingManipulation(data) {
    if (data === true) {
        $("#unfollow").removeClass("hidden");
        $("#follow").hide();
        $("#follow").removeClass("hidden");
    }
    if (data === false) {
        $("#follow").removeClass("hidden");
        $("#unfollow").hide();
        $("#unfollow").removeClass("hidden");
    }
}

