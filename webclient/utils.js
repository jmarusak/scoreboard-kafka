function transformMessage(message) {
  var obj = JSON.parse(message);

  var court = obj.court;
  var data = Object.keys(obj)
    .filter(key => key !== 'court')
    .map(key => ({ team: key.toUpperCase(), score: obj[key] }));

  return [court, data];
}
