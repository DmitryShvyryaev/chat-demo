$( document ).ready(function() {
    updateMessages();
    $.ajaxSetup({cache: false});
});

function updateMessages() {
    $.ajax({
        type: "GET",
        url: "/rest/messages",
        success: function(response){
            updateMessagesByData(response);
        }
    });
    scroll();
}

function updateMessagesByData(response) {
    for (let i = 0; i < response.length; i++) {
        renderMessage(response[i]);
    }
}

function renderMessage(data) {
    $('#chat').append($('<li>', {
        'class': "clearfix"
    })
        .append($('<div>', {
        'class': data['username'] === username ? "message-data" : "message-data text-right"
    })
        .append($('<span>', {
        'class': "message-data-time",
        text: data['username']
        })
    ))
        .append($('<div>', {
        'class': data['username'] === username ? "message my-message" : "message other-message float-right",
        text: data['content']
    })));
}

function sendMessage() {
    $.ajax({
        type: "POST",
        url: "/rest/messages",
        data: $('#content').serialize(),
        success: updateMessages(),
        error: function () {
            console.log('error with send form');
        }
    });
}

function scroll() {
    $('html, body').animate({
        scrollTop: $("#inoutMessage").offset().top
    }, 1000);
}


