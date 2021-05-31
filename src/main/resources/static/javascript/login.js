function save() {
    $.ajax({
        type: "POST",
        url: "/security-check",
        data: $('#login-form').serialize()
    });
}