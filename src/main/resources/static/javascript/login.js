function login() {
    $.ajax({
        type: "POST",
        url: "/rest/users",
        data: $('#login-form').serialize(),
        success: function () {
            window.location = "http://localhost:8080/messages";
        },
        error: function (data) {
            $('ul').remove();
            let details = data['responseJSON']['details'];
            let nameErrors = [];
            let statusErrors = [];
            for (let i = 0; i < details.length; i++) {
                if (details[i].substring(0, 4) === 'name')
                    nameErrors.push(details[i].substring(6));
                else if (details[i].substring(0, 6) === 'status')
                    statusErrors.push(details[i].substring(8));
            }
            setErrorOnInput($('#name'), nameErrors);
            setErrorOnInput($('#status'), statusErrors);
        }
    });
}

function setErrorOnInput(input, details) {
    if (details.length > 0) {
        input.addClass("form-control is-invalid");
        let ul = $('<ul>', {
            'class': "nav"
        });
        for (let i = 0; i < details.length; i++) {
            ul.append(createError(details[i]));
        }
        input.after(ul);
    }
}

function createError(error) {
    return $('<li>', {
        'class': "nav-item"
    }).append($('<small>', {
        'class': "text-danger",
        text: error
    }));
}
