function toJson(court, blue, red) {
  let obj = {};
  obj["court"] = court;
  obj["blue"] = blue;
  obj["red"] = red;
  return JSON.stringify(obj);
}

function post(message) {
  console.log(message)

  const apiUrl = 'http://localhost:8081/score';

  const options = {
    method: 'POST',
    mode: 'no-cors',
    headers: {'Content-Type': 'application/json' },
    body: message 
  };

  fetch(apiUrl, options)
    .then(response => {
        if (!response.ok) {
           throw new Error(response.ok);
        }
        return response.json(); // Parse the JSON in the response
     })
     .then(data => {
        console.log('API response:', data); // Handle the data from the API
     })
     .catch(error => {
        console.error('There has been a problem with your fetch operation:', error); // Handle errors
   });
}

function listener() {
  let blueScore = 0;
  let redScore = 0;

  const blueNumberElement = document.getElementById('blueScore');
  const redNumberElement = document.getElementById('redScore');

  blueNumberElement.addEventListener('click', function() {
    blueScore++;
    blueNumberElement.textContent = blueScore;
    let message = toJson("1", blueScore, redScore);
    post(message);
  });

  redNumberElement.addEventListener('click', function() {
    redScore++;
    redNumberElement.textContent = redScore;
    let message = toJson("1", blueScore, redScore);
    post(message);
  });
}
