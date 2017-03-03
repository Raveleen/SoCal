/**
 * Created by Святослав on 02.03.2017.
 */
function hideAndRemoveHidden(elementsIDs) {
    for (var i = 0; i < elementsIDs.length; i++) {
        $(elementsIDs[i]).hide();
        $(elementsIDs[i]).removeClass("hidden");
    }
}
