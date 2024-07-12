  function createScoreBar(data, courtId) {
    var margin = { top: 20, right: 20, bottom: 20, left: 20 };
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

    svg.append('g')
      .attr('class', 'y-axis')
      .call(d3.axisLeft(y));

    svg.selectAll('.bar')
      .data(data)
      .enter().append('rect')
      .attr('class', 'bar')
      .attr('y', function(d) { return y(d.team); })
      .attr('height', y.bandwidth())
      .attr('x', 0)
      .attr('width', function(d) { return x(d.score); })
      .attr('fill', function(d) { return d.team == "A" ? 'blue' : 'red'; });

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
    var svg = d3.select('#' + courtId)
    svg.selectAll('.bar')
      .data(data)
      .enter().append('rect')
      .attr('class', 'bar')
      .attr('y', function(d) { return y(d.team); })
      .attr('height', y.bandwidth())
      .attr('x', 0)
      .attr('width', function(d) { return x(d.score); })
      .attr('fill', function(d) { return d.team == "A" ? 'blue' : 'red'; });
  }
