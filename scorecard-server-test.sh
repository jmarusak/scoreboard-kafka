curl -X POST \
  http://localhost:8081/score \
  -H 'Content-Type: application/json' \
  -d '{"court":"1","blue":0,"red":1}'
