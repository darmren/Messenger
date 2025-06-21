let stompClient = null;
const params = new URLSearchParams(location.search);
const username = params.get('user');
const chatId = params.get('chat')
document.getElementById('chat-title').textContent = `Чат с ${username}`;
function connect() {
    const socket = new SockJS('/ws-chat');
    // const socket = new SockJS('/http://localhost:8080/ws-chat"');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        console.log('🟢 Подключено к WebSocket');

        // const chatId = document.getElementById("chatId").value;
        stompClient.subscribe(`/topic/chat/${chatId}`, (message) => {
            const msg = JSON.parse(message.body);
            showMessage(msg);
        });
    });
}

function sendMessage() {
    const chatId = document.getElementById("chatId").value;
    const senderId = document.getElementById("senderId").value;
    const text = document.getElementById("messageInput").value;

    stompClient.send("/app/chat.send", {}, JSON.stringify({
        chatId: chatId,
        senderId: senderId,
        text: text,
    }));

    document.getElementById("messageInput").value = '';
}

function showMessage(msg) {
    const chat = document.getElementById("chat");
    const el = document.createElement("div");
    el.textContent = `[${msg.timestamp}] Пользователь ${msg.senderId}: ${msg.content}`;
    chat.appendChild(el);
    chat.scrollTop = chat.scrollHeight;
}

connect();