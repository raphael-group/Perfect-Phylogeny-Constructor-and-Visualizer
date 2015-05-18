$("#randomButton").on("click", function() {setRandom();});


data = { nodes: [
    { name: "A", x: 200, y: 150 },
    { name: "B", x: 140, y: 300 },
    { name: "C", x: 300, y: 300 },
    { name: "D", x: 300, y: 180 }
], links: [
    { source: 0, target: 1 },
    { source: 1, target: 2 },
    { source: 2, target: 3 },
] };



var svg = d3.select("body")    
    .append("svg")
    .attr("width",1200)
    .attr("height",2000);
/*
var drag = d3.behavior.drag()
    .on("drag", function(d,i) {
        d.x += d3.event.dx
        d.y += d3.event.dy
        d3.select(this).attr("cx", d.x).attr("cy",d.y);
        links.each(function(l,li){ 
            if(l.source==i){
               d3.select(this).attr("x1",d.x).attr("y1",d.y);        
            } else if(l.target==i){
               d3.select(this).attr("x2",d.x).attr("y2",d.y);
            } 
        });
    }); */

    updateLinks();
updateNodes();

function setRandom() {
    console.log($("#randomTime").val(), $("#randomStates").val(), $("#randomFilter").val());
    if ($("#randomTime").val() == "" || $("#randomStates").val() == "" || $("#randomFilter").val() == "") {
        alert("Please fill in all fields.");
        return;
    }
    $.post("/random", {time : $("#randomTime").val(), max : $("#randomStates").val(), filter : $("#randomFilter").val()}, function(str) {
        $("#matrix").val(str);
        $("#stateTrees").val("default " + $("#randomStates").val());
        $("#states").val($("#randomStates").val());
        getTree();
    });

}

function getTree() {
    var matString = $("#matrix").val();
    var stateTrees = $("#stateTrees").val();
    var states = $("#states").val();
    var matChunk = matString.split("\n");
    var mat = [];

    for (var i =0; i < matChunk.length; i++) {
        mat[i] = matChunk[i].split(" ");
    }

    for (var i=0; i<mat.length; i++) {
        for (var j=0; j<mat[i].length; j++) {
            mat[i][j] = parseInt(mat[i][j]);
        }
    }

    console.log(mat, states);

    if (states == "") states = "2";

    $.post("/calculate", {matrix : JSON.stringify(mat), trees : stateTrees, states : states}, function(responseJSON) {
        if (responseJSON.indexOf("Error") != -1) {
            alert(responseJSON);
            return;
        }


        gnodes.remove();
        nodes.remove();
        labels.remove();
        links.remove();
        var res = JSON.parse(responseJSON);
        console.log(res);
        var nodeDetails = {};
        var nodeOrder = {};
        for (var i=0; i<res.nodes.length; i++) {
            var stringArr = res.nodes[i].split(/, |\[|\]/);
            if (nodeDetails[stringArr[2]] === undefined) nodeDetails[stringArr[2]] = 1;
            else nodeDetails[stringArr[2]]++;
            nodeOrder[stringArr[1]] = i;
        }
        console.log(nodeDetails);
        var levelHeight = 50;
        var newData = {};
        newData.nodes = [];
        newData.links = [];
        for (var i=0; i<res.nodes.length; i++) {
            var obj = {};
            var str = res.nodes[i].split(":");
            var deets = str[0].split(/, |\[|\]/);
            var level = deets[2];
            var split = deets[3];
            console.log(str);
            if (str[2] == "0") obj.name = str[1];
            else obj.name = "";
            obj.sequence = str[1];
            obj.x = (500 / (nodeDetails[level] + 1)) * (parseInt(split)+1);
            obj.y = levelHeight * parseInt(level) + 25;
            newData.nodes.push(obj);
            console.log(obj);
        }

        for (var i=0; i<res.edges.length; i++) {
            var obj = {};
            var str = res.edges[i].split("-");
            obj.source = parseInt(nodeOrder[str[0]]);
            obj.target = parseInt(nodeOrder[str[1]]);
            newData.links.push(obj);
        }
        console.log(newData);
        data = newData;
        updateLinks();
        updateNodes();
    });
}


function updateLinks() {
 links  = svg.selectAll("link")    
    .data(data.links)
    .enter()
    .append("line")
    .attr("class","link")
    .attr("x1",function(l){ 
        var sourceNode = data.nodes.filter(function(d,i){ return i==l.source })[0];
        d3.select(this).attr("y1",sourceNode.y);
        return sourceNode.x
    })
    .attr("x2",function(l){ 
        var targetNode = data.nodes.filter(function(d,i){ return i==l.target })[0];
         d3.select(this).attr("y2",targetNode.y);
        return targetNode.x
    }) 
    .attr("fill","none")
    .attr("stroke", "white");        
}
function updateNodes() { 

gnodes = svg.selectAll('g.gnode')
  .data(data.nodes)
  .enter()
  .append('g')
  .classed('gnode', true)
      .attr("cx",function(d){ return d.x })
    .attr("cy",function(d){ return d.y });

// Add one circle in each group
nodes = gnodes.append("circle")
  .attr("class", "node")
  .attr("r", 8)
      .attr("cx",function(d){ return d.x })
    .attr("cy",function(d){ return d.y })
    .attr("sequence", function(d){return d.sequence})
  .style("fill", "blue")
  .on("click", function(d) {
    
    if (d.name != "") d.name = "";
    else d.name = d.sequence;
    gnodes.remove();
        nodes.remove();
        labels.remove();
    updateNodes();
    console.log("clicked ", d);
  });

// Append the labels to each group
labels = gnodes.append("text")
      .attr("x",function(d){ return d.x  - 15})
    .attr("y",function(d){ return d.y + 20})
  .text(function(d) { return d.name; })
  .on("click", function(d) {
    console.log("clicked ", d);
  });
}

