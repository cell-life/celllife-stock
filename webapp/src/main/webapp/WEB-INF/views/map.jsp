<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
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
  var clinicLatlng = new google.maps.LatLng(-32.7496, 25.6035); // Clinic: Aeroville
  var marker = new google.maps.Marker({
      position: clinicLatlng,
      map: map,
      title: 'Aeroville: 20 red alerts'
  });
}

google.maps.event.addDomListener(window, 'load', initialize);

    </script>
<!-- To be Fixed -->
  </head>
  <body>
    <div id="map-canvas"></div>
  </body>
</html>
