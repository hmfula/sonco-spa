var height = 10;
	var width = 20;
	var margin = {top: 20, right:20, bottom: 60, left: 40};

	var currencyFormat = d3.format("0.2f");
	var decimalFormat = d3.format("0.2f");

	var svg = d3.select("trend-panel")
			.append("svg")
			.attr("width", width + margin.left + margin.right)
			.attr("height", height + margin.top + margin.bottom)
			.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	svg.append("g")
			.attr("class", "y axis");

	svg.append("g")
			.attr("class", "x axis");

	var xScale = d3.scale.ordinal()
			.rangeRoundBands([margin.left, width], .1);

	var yScale = d3.scale.linear()
			.range([height, 0]);

	var xAxis = d3.svg.axis()
			.scale(xScale)
			.orient("bottom");

	var yAxis = d3.svg.axis()
			.scale(yScale)
			.orient("left");

	d3.csv("../data/weekly_kpis.csv", function(data) {

		// extract the x labels for the axis and scale domain
		var xLabels = data.map(function (d) { return d['yearmonth']; })







		xScale.domain(xLabels);
		yScale.domain([0, Math.round(d3.max(data, function(d) { return parseFloat(d['rate']); }))]);

		var line = d3.svg.line()
				.x(function(d) { return xScale(d['yearmonth']); })
				.y(function(d) { return yScale(d['rate']); });

		svg.append("path")
				.datum(data)
				.attr("class","line")
				.attr("d", line);

		svg.select(".x.axis")
				.attr("transform", "translate(0," + (height) + ")")
				.call(xAxis.tickValues(xLabels.filter(function(d, i) {
				//	if (i % 12 == 0)
						return d;
				})))
				.selectAll("text")
				.style("text-anchor","end")
				.attr("transform", function(d) {
					return "rotate(-45)";
				});

		svg.select(".y.axis")
				.attr("transform", "translate(" + (margin.left) + ",0)")
				.call(yAxis.tickFormat(currencyFormat));

		// chart title
		svg.append("text")
				.attr("x", (width + (margin.left + margin.right) )/ 2)
				.attr("y", 0 + margin.top)
				.attr("text-anchor", "middle")
				.style("font-size", "16px")
				.style("font-family", "sans-serif")
				.text("Call Setup Success Rate [%]");

		// x axis label
		svg.append("text")
				.attr("x", (width  - margin.right) )
				.attr("y", height + margin.bottom)
				.attr("dx", "-1.9em")
				.attr("class", "text-label")
				.attr("text-anchor", "middle")
				.style("font-size", "10px")
				.text("Measurement Date");

		//		//y axis label
		svg.append("text")
				.attr("transform", "rotate(-90)")
				.attr("y", margin.top)
				.attr("dx", "0.7em") //push down label
				.attr("dy", "-1.4em")//push away for axis
				.style("text-anchor", "end")
				.style("font-size", "10px")
				.text("CSSR [%]");


		//trendline
		// get the x and y values for least squares
		var xSeries = d3.range(1, xLabels.length + 1);
		var ySeries =  data.map(function (d) { return parseFloat(d['rate']); });// data.map(function(d) { return parseFloat(d['rate']);});

//scales. These need to hae both domain and range
		//xScale = xRange;//d3.time.scale().range([MARGINS.left, WIDTH - MARGINS.right]);
		//yScale = yRange;//d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]);



		var leastSquaresCoeff = leastSquares(xSeries, ySeries);

		// apply the reults of the least squares regression
		var x1 = xLabels[0];

		var y1 = leastSquaresCoeff[0] + leastSquaresCoeff[1];//assume x=1 at first data point in x. assumption in this graph x:=> goes from 1-6

		var x2 = xLabels[xLabels.length - 1];
		var y2 = leastSquaresCoeff[0] * xSeries.length + leastSquaresCoeff[1];
		var trendData = [[x1,y1,x2,y2]];

		var trendline = svg.selectAll(".trendline")
				.data(trendData);

		trendline.enter()
				.append("line")
				.attr("class", "trendline")
				.attr("x1", function(d) { return xScale(d[0]); })
				.attr("y1", function(d) { return yScale(d[1]); })
				.attr("x2", function(d) { return xScale(d[2]); })
				.attr("y2", function(d) { return yScale(d[3]); })
				.attr("stroke", "green")
				.attr("stroke-width", 1);

		// display equation on the chart
		svg.append("text")
				.text("eq: " + leastSquaresCoeff[0] + "x + " + y1)
			//leastSquaresCoeff[1])
				.attr("class", "text-label")
				.attr("x", function(d) {return xScale(x2)/2 ;})
				.attr("y", function(d) {return yScale(y2)/2-20;});

		// display r-square on the chart
		svg.append("text")
				.text("r-sq: " + leastSquaresCoeff[2])
				.attr("class", "text-label")
				.attr("x", function(d) {return xScale(x2)/2;})
				.attr("y", function(d) {return yScale(y2)/2;});

	});

	// returns slope, intercept and r-square of the line
	function leastSquares(xSeries, ySeries) {
		var reduceSumFunc = function(prev, cur) {
			return  prev + cur; };

		var xBar = xSeries.reduce(reduceSumFunc) * 1.0 / xSeries.length;
		var yBar = ySeries.reduce(reduceSumFunc) * 1.0 / ySeries.length;

		var ssXX = xSeries.map(function(d) { return Math.pow(d - xBar, 2); })
				.reduce(reduceSumFunc);

		var ssYY = ySeries.map(function(d) { return Math.pow(d - yBar, 2); })
				.reduce(reduceSumFunc);

		var ssXY = xSeries.map(function(d, i) { return (d - xBar) * (ySeries[i] - yBar); })
				.reduce(reduceSumFunc);

		var slope = ssXY / ssXX;
		var intercept = yBar - (xBar * slope);

		var rSquare = Math.pow(ssXY, 2) / (ssXX * ssYY);

		return [slope, intercept, rSquare];
	}
