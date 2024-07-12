        // WebSocket connection URL
        const socketUrl = "ws://localhost:8080/score";

        // Create WebSocket instance
        const socket = new WebSocket(socketUrl);

        // WebSocket onopen event listener
        socket.onopen = function(event) {
            console.log("WebSocket connection opened.");
        };

        // WebSocket onmessage event listener
        socket.onmessage = function(event) {
            const message = event.data;
            console.log("Received message: ", message);

            // Display message in the message container
            //const messageContainer = document.getElementById("message-container");
            //messageContainer.innerHTML += `<p>${message}</p>`;
        };

        // WebSocket onerror event listener
        socket.onerror = function(error) {
            console.error("WebSocket error:", error);
        };

        // WebSocket onclose event listener
        socket.onclose = function(event) {
            console.log("WebSocket connection closed.");
        };
