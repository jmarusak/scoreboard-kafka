function toJson(court, blue, red) {
  let obj = {};
  obj["court"] = court;
  obj["blue"] = blue;
  obj["red"] = red;
  obj["ptime"] = new Date().getTime();
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

  const courtElement = document.getElementById('court');
  const blueScoreElement = document.getElementById('blueScore');
  const redScoreElement = document.getElementById('redScore');

  blueScoreElement.addEventListener('click', function() {
    blueScore++;
    blueScoreElement.textContent = blueScore;
    let message = toJson(court.value, blueScore, redScore);
    post(message);
  });

  redScoreElement.addEventListener('click', function() {
    redScore++;
    redScoreElement.textContent = redScore;
    let message = toJson(court.value, blueScore, redScore);
    post(message);
  });
}
