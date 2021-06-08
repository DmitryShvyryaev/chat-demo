let username;

$(document).ready(function () {
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
    updateProfile();
    updateUsers();
    updateMessages();
});

function updateMessages() {
    $.ajax({
        type: "GET",
        url: "/rest/messages",
        success: function (response) {
            updateMessagesByData(response);
        }
    });
}

function updateUsers() {
    $('#users-list').empty();
    $.ajax({
        type: "GET",
        url: "/rest/users",
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                if (response[i]['name'] !== username)
                    renderUser(response[i]);
            }
        }
    });
}

function updateProfile() {
    $.ajax({
        type: "GET",
        url: "/rest/users/profile",
        success: function (response) {
            username = response['name'];
            $('#user-name').text(response['name']);
            $('#user-status').text(response['status']);
        }
    })
}

function logout() {
    $.ajax({
        type: "DELETE",
        url: "/rest/users/logout",
        success: window.location = "http://localhost:8080/login"
    })
}

function updateMessagesByData(response) {
    for (let i = 0; i < response.length; i++) {
        renderMessage(response[i]);
    }
}

function renderMessage(data) {
    $('#chat').append($('<li>', {
        'class': "clearfix"
    }).append($('<div>', {
        'class': data['username'] === username ? "message-data" : "message-data text-right"
    }).append($('<span>', {
            'class': "message-data-time",
            text: data['username'] + ', ' + data['dateTime']
        })
    )).append($('<div>', {
        'class': data['username'] === username ? "message my-message" : "message other-message float-right",
        text: data['content']
    })));
}

function renderUser(data) {
    $('#users-list').append($('<li>', {
        'class': "clearfix"
    }).append($('<div>', {
        'class': "about"
    }).append($('<div>', {
        'class': "name",
        text: data['name']
    })).append($('<div>', {
        'class': "status",
        text: " " + data['status']
    }).prepend($('<i>', {
        'class': "fa fa-circle online"
    })))));
}

function sendMessage() {
    $.ajax({
        url: '/rest/messages',
        type: 'POST',
        data: $('#content').serialize(),
        success: function (msg) {
            $('#inoutMessage').val("");
            updateMessages();
        }
    });
    updateUsers();
    updateMessages();
}

// setInterval(updateMessages, 1000);

function scroll() {
    console.log("SCROLL");
    $("#chat").scrollTop($('#chat')[0].scrollHeight);
}


