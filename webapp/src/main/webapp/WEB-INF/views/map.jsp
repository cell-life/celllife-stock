<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <script type="text/javascript" src="resources/js/jquery-1.8.2.js"></script>
    <title>Stock App alerts</title>
    <style>
      html, body, #map-canvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }
    </style>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script>

    
function initialize() {	
  // initialise the map
 	
  		
		      
  var centerLatlng = new google.maps.LatLng(-30,24);
  var mapOptions = {
    zoom: 6,
    center: centerLatlng
  }
  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

  // put a pin on the map
  $.getJSON( "service/alerts/summary", function( data ) {
	    $.each( data, function( key, val ) {
	    	var clinic = val["clinic"];
	    	var coord = clinic["coordinates"];
	    	var alertCount = val["level3AlertCount"];
	    	if (coord != null && alertCount != 0) {
		    	coord = coord.substr(1, coord.length-2);
		    	var coords = coord.split(",");
		    	var lat = coords[0];
		    	var longitude = coords[1];
		        var clinicName = clinic["clinicName"];
		    	var clinicLatlng = new google.maps.LatLng(longitude, lat);
		    	var marker = new google.maps.Marker({
			        position: clinicLatlng,
			        icon: 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld='+alertCount+'|FF0000|000000',
			        map: map,
			        title: clinicName + ':' + alertCount
	        	});
	    	}
		 });
	 
	 
	});

}

google.maps.event.addDomListener(window, 'load', initialize);

    </script>
	<script>
	
	</script>
  </head>
  <body>
    <div id="map-canvas"></div>
  </body>
</html>
