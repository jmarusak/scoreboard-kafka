function createScoreBar(data, courtId) {
  var margin = { top: 10, right: 20, bottom: 10, left: 20 };
  var width = 800 - margin.left - margin.right;
  var height = 140 - margin.top - margin.bottom;

  var svg = d3.select('#' + courtId)
    .append('svg')
    .attr('width', width + margin.left + margin.right)
    .attr('height', height + margin.top + margin.bottom)
    .append('g')
    .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

  var y = d3.scaleBand()
    .domain(data.map(function(d) { return d.team; }))
    .range([0, height])
    .padding(0.1);

  var x = d3.scaleLinear()
    .domain([0, 25])
    .nice()
    .range([0, width]);

  svg.selectAll('.bar')
    .data(data)
    .enter().append('rect')
    .attr('class', 'bar')
    .attr('y', function(d) { return y(d.team); })
    .attr('height', y.bandwidth())
    .attr('x', 0)
    .attr('width', function(d) { return x(d.score); })
    .attr('fill', function(d) { return d.team == "blue" ? 'blue' : 'red'; });

  svg.selectAll('.bar-score')
    .data(data)
    .enter().append('text')
    .attr('class', 'bar-score')
    .attr('x', function(d) { return x(d.score); })
    .attr('y', function(d) { return y(d.team) + y.bandwidth() / 2; })
    .attr('dx', 5) // padding-right
    .text(function(d) { return d.score; });
}

function updateScoreBar(data, courtId) {
  var margin = { top: 20, right: 20, bottom: 20, left: 20 };
  var width = 800 - margin.left - margin.right;
  var height = 140 - margin.top - margin.bottom;

  var svg = d3.select('#' + courtId).select('svg').select('g');

  // Update y scale domain with the new data
  var y = d3.scaleBand()
    .domain(data.map(function(d) { return d.team; }))
    .range([0, height])
    .padding(0.1);

  // Update x scale domain based on maximum score in the new data
  var x = d3.scaleLinear()
    .domain([0, 25])
    .nice()
    .range([0, width]);

  // Update the bars
  var bars = svg.selectAll('.bar')
    .data(data);

  bars.enter().append('rect')
    .attr('class', 'bar')
    .merge(bars)
    .attr('y', function(d) { return y(d.team); })
    .attr('height', y.bandwidth())
    .attr('x', 0)
    .transition()
    .duration(500)
    .attr('width', function(d) { return x(d.score); })
    .attr('fill', function(d) { return d.team == "blue" ? 'blue' : 'red'; });

  bars.exit().remove();

  // Update the score text
  var scoreText = svg.selectAll('.bar-score')
    .data(data);

  scoreText.enter().append('text')
    .attr('class', 'bar-score')
    .merge(scoreText)
    .attr('x', function(d) { return x(d.score); })
    .attr('y', function(d) { return y(d.team) + y.bandwidth() / 2; })
    .attr('dx', 5) // padding-right
    .text(function(d) { return d.score; });

  scoreText.exit().remove();

  // Update the y-axis
  svg.select('.y-axis')
    .call(d3.axisLeft(y));
}

function randomScore() {
  return Math.floor(Math.random() * 20) + 1;
}
