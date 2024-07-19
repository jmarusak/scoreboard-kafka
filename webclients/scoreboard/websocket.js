function transformMessage(message) {
  var obj = JSON.parse(message);

  var court = obj.court;
  var data = Object.keys(obj)
    .filter(key => key == 'blue' || key == 'red')
    .map(key => ({ team: key, score: obj[key] }));
  return [court, data];
}

function setupWebSocket() {
  //connection URL
  const socketUrl = "ws://localhost:8082/score";

  // Create instance
  const socket = new WebSocket(socketUrl);

  // onopen event listener
  socket.onopen = function(event) {
    console.log("WebSocket connection opened.");
  };

  // onmessage event listener
  socket.onmessage = function(event) {
    const message = event.data;
    console.log("Received message: ", message);

    var [court, data] = transformMessage(message);
    console.log("Transformed message: ", data);

    updateScoreBar(data, "court"+court);
  };

  // onerror event listener
  socket.onerror = function(error) {
    console.error("WebSocket error:", error);
  };

  // onclose event listener
  socket.onclose = function(event) {
    console.log("WebSocket connection closed.");
  };
}
