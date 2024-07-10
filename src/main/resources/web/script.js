console.log("script.js loaded");

// Sample data
const data = [
    { name: 'team A', value: 11, color: 'red' },
    { name: 'team B', value: 16, color: 'blue' }
];

// SVG dimensions
const svgWidth = 600;
const svgHeight = 160;

// Margins and padding
const margin = { top: 20, right: 30, bottom: 30, left: 60 };
const width = svgWidth - margin.left - margin.right;
const height = svgHeight - margin.top - margin.bottom;

// Create SVG element
const svg = d3.select('#barChart')
    .attr('width', svgWidth)
    .attr('height', svgHeight)

// Create scales
const xScale = d3.scaleLinear()
    .domain([0, 21])
    .range([margin.left, width]);

const yScale = d3.scaleBand()
    .domain(data.map(d => d.name))
    .range([margin.top, height + margin.top])
    .padding(0.1);

// Draw bars
svg.selectAll('.bar')
    .data(data)
    .enter()
    .append('rect')
    .attr('class', 'bar')
    .attr('x', margin.left)
    .attr('y', d => yScale(d.name))
    .attr('width', d => xScale(d.value) - margin.left)
    .attr('height', yScale.bandwidth())
    .attr('fill', d => d.color);

// Add labels
svg.selectAll('.bar-label')
    .data(data)
    .enter()
    .append('text')
    .attr('class', 'bar-label')
    .attr('x', d => xScale(d.value) + 10)
    .attr('y', d => yScale(d.name) + yScale.bandwidth() / 2)
    .text(d => d.value);

// Add y-axis
svg.append('g')
    .attr('transform', `translate(${margin.left}, 0)`)
    .call(d3.axisLeft(yScale));

// Add x-axis
svg.append('g')
    .attr('transform', `translate(0, ${height + margin.top})`)
    .call(d3.axisBottom(xScale));

