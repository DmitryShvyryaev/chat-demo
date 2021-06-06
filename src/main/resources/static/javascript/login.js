function login() {
    $.ajax({
        type: "POST",
        url: "/rest/users",
        data: $('#login-form').serialize(),
        success: function () {
            console.log("SECCES");
            window.location = "http://localhost:8080/messages";
        }
    });
}