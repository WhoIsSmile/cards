var ws = new WebSocket("ws://localhost:8080");
ws.onopen = function () {
    console.log("open");
    ws.send("hello");
};
ws.onmessage = function (evt) {
    console.log(evt.data)
};
ws.onclose = function (evt) {
    console.log("WebSocketClosed!");
};
ws.onerror = function (evt) {
    console.log("WebSocketError!");
};