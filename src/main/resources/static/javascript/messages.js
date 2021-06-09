let username;
let maxMessageId = 0;
let maxUsersId = 0;

$(document).ready(function () {
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
    updateProfile();
    getUsers();
    $.ajax({
        type: "GET",
        url: "/rest/messages/last-part",
        success: function (response) {
            updateMessagesByData(response);
        }
    });
    setInterval(update, 5000);
});

function updateMessages() {
    $.ajax({
        type: "GET",
        url: "/rest/messages/update",
        data: {maxId: maxMessageId},
        success: function (response) {
            updateMessagesByData(response);
            scroll();
        }
    });
}

function updateMessagesByData(response) {
    for (let i = 0; i < response.length; i++) {
        if (response[i]['id'] > maxMessageId)
            maxMessageId = response[i]['id'];
        renderMessage(response[i]);
    }
}

function getUsers() {
    $.ajax({
        type: "GET",
        url: "/rest/users",
        success: function (response) {
            maxUsersId = response['versionID'];
            for (let i = 0; i < response['users'].length; i++) {
                renderUser(response['users'][i]);
            }
        }
    });
}

function updateUsers() {
    $.ajax({
        type: "GET",
        url: "/rest/users/update",
        data: {maxId: maxUsersId},
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                if (response[i]['id'] > maxUsersId) maxUsersId = response[i]['id'];
                if (response[i]['action'] === "DELETE") {
                    let blockName = "#" + response[i]['user']['name'] + "-block";
                    $(blockName).remove();
                } else {
                    renderUser(response[i]['user']);
                }
            }
        }
    });
}

function update() {
    updateUsers();
    updateMessages();
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
        'id': data['name'] + "-block",
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
            updateUsers();
        }
    });
}

function logout() {
    $.ajax({
        type: "DELETE",
        url: "/rest/users/logout",
        success: window.location = "http://localhost:8080/login"
    })
}

function scroll() {
    console.log("SCROLL");
    $("#chat-history").scrollTop($('#chat-history')[0].scrollHeight);
}


