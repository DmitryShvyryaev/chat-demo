$( document ).ready(function() {
    updateMessages();
    $('#send-message').click(function() {
        $.ajax({
            url: '/rest/messages',
            type: 'POST',
            data: $('#content').serialize(),
            success: function(msg) {
                $('#inoutMessage').val("");
                updateMessages();
            }
        });
    });
});

function updateMessages() {
    $('#chat').empty();
    $.ajax({
        type: "GET",
        url: "/rest/messages",
        success: function(response){
            updateMessagesByData(response);
        }
    });
    scroll();
}

function updateUsers() {
    $.ajax({
        type: "GET",
        url: "/rest/messages",
        success: function(response){
            updateMessagesByData(response);
        }
    });
}

function updateMessagesByData(response) {
    for (let i = 0; i < response.length; i++) {
        renderMessage(response[i]);
    }
}

function updateUsersByData(response) {
    for (let i = 0; i < response.length; i++) {
        renderUser(response[i]);
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
        text: data['status']
    }))));
}

// setInterval(updateMessages, 1000);

function scroll() {
    console.log("SCROLL");
    $("#chat").scrollTop($('#chat')[0].scrollHeight);
}


