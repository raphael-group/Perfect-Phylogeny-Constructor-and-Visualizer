<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/main.css">
  </head>
  <body>
    <h3>
      Perfect Phylogeny
    </h3>
     <!-- Again, we're serving up the unminified source for clarity. -->
     <div id="entry stuff">
     <textarea id="matrix" placeholder="Enter Matrix"></textarea>
     <textarea id="stateTrees" placeholder="Enter State Trees"></textarea>
     States:
     <input type="number" name="quantity" min="0" max="99" id="states"></input>
     <button onclick = "getTree()">
        Construct
     </button>
     <br> <br>
     Time:<input type="number" id="randomTime" min="0"></input> <br>
     States:<input type="number" id="randomStates" min="0"></input> <br>
     n:<input type="number" id="randomFilter" min="0"></input> <br>
     <button id = "randomButton"> Random
     </button>
      </div>

      <script src="js/d3.js" charset="utf-8"></script>
      <script src="js/jquery-2.1.1.js"></script>
     <script src="js/main.js"></script>
  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>
