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
     <!-- Again, we're serving up the unminified source for clarity. -->
     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/main.js"></script>

     <p>Results from query:</p>
     <p>${my_response}</p>
     <br><br>


     <p> Enter another query: </p>
     <p> Select query type: </p>
     <form method = "GET" action ="/results">
      <select name="option">
     <option value="neighbors">neighbors</option>
     <option value="radius">radius</option>
     </select>
     <br>
     <p> Please enter the search paramenter.  If radius is selected, please ensure that your entry is a positive decimal.  If neighbors is selected, please ensure that your entry is a positive integer. </p>
     <textarea name="number" placeholder="Search parameter"></textarea><br>

      <p> If the search origin will be a star, then please select 'star name' from the drop down and then enter the name in the text field.  If the search origin will be a point in space please select 'cartesian triple' from the dropdown and enter the triple commas and no spaces between entries.  For instance for the point at (1.2, 6, 4.33) enter "1.2,6,4.33"</p>
      <select name="center">
     <option value="name">star name</option>
     <option value="point">cartesian triple: x,y,z</option>
     </select>
     <br>
    
     <textarea name="entry" placeholder="Search center"></textarea><br> 


    <br>
    <input type="submit" value="Submit">
    </form>





  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>
