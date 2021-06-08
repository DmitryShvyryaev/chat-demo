let failedNote;

$(document).ready(function () {
    // $(document).ajaxError(function (event, jqXHR, options, jsExc) {
    //     failNoty(jqXHR);
    // });
});

function failNoty(jqXHR) {
    closeNoty();
    var errorJson = jqXHR.responseJSON;
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorJson.type + "<br>" + errorJson.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show();
}

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}