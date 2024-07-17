function toJson(court, blue, red) {
  let obj = {};
  obj["court"] = court;
  obj["blue"] = blue;
  obj["red"] = red;

  return JSON.stringify(obj);
}

function listener() {
  let blueScore = 0;
  let redScore = 0;

  const blueNumberElement = document.getElementById('blueScore');
  const redNumberElement = document.getElementById('redScore');

  blueNumberElement.addEventListener('click', function() {
    blueScore++;
    blueNumberElement.textContent = blueScore;
    console.log(toJson("1", blueScore, redScore));
  });

  redNumberElement.addEventListener('click', function() {
    redScore++;
    redNumberElement.textContent = redScore;
    console.log(toJson("1", blueScore, redScore));
  });
}
