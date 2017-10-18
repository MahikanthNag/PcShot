chrome.tabs.query({currentWindow: true, active: true}, function(tabs) {
  angular.bootstrap(document);
});

// var jQueryScript = document.createElement('script');  
// jQueryScript.setAttribute('src','');
// document.head.appendChild(jQueryScript);

var myApp = angular.module("myApp" , []);
var myCtrl = myApp.controller("myCtrl", function($scope, $http)
  {
    function takeSnapshot()
    {  
      console.log("clicked");
      chrome.tabs.query({active:true,currentWindow:true},function(tabArray){
      console.log("hai");
      console.log(tabArray[0].url);
      });
      chrome.tabs.captureVisibleTab(null, {}, function (image) {
      // You can add that image HTML5 canvas, or Element.
      //document.body.appendChild(image);
      console.log(image);

      // Initialize Firebase  
      var config = {
      apiKey: "AIzaSyDFJBdQ7dVY3rJtEn0I3J6vZmVYVTVrXtE",
      authDomain: "pcshot-b411c.firebaseapp.com",
      databaseURL: "https://pcshot-b411c.firebaseio.com",
      projectId: "pcshot-b411c",
      storageBucket: "pcshot-b411c.appspot.com",
      messagingSenderId: "483111281699"
      };
      firebase.initializeApp(config);
      var database = firebase.database();
      console.log('ImageData'+$scope.emailid);
      var email = $scope.emailid.replace("@", "");
      email = email.replace(".","");
      database.ref('ImageData_'+email).set({
        "image": image
      });
      // $http.post('https://fcm.googleapis.com/fcm/send', image).success(function(data, status, headers, config) {
      //      $scope.zipCodes = data;
      // }).error(function(error, status, headers, config) {
      //      console.log(status);
      //      console.log("Error occured");
      //   });
     });             
    };
    $scope.takeSnapshot = takeSnapshot;
// var ajax = new XMLHttpRequest();
//     ajax.open("POST",'http://mahikanthnag.net23.net/imageSave.php',true);
//     ajax.setRequestHeader("Content-type","application/upload");
 
//     ajax.send(image);


 
    
 });   
