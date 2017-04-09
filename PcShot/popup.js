chrome.tabs.query({currentWindow: true, active: true}, function(tabs) {
  angular.bootstrap(document);
});
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
        $http.post('http://mahikanthnag.net23.net/imageSave.php', image)
          .success(function(data, status, headers, config) {
             $scope.zipCodes = data;
        })
        .error(function(error, status, headers, config) {
             console.log(status);
             console.log("Error occured");
        });
        });
        
        
        
    };
    $scope.takeSnapshot = takeSnapshot;
// var ajax = new XMLHttpRequest();
//     ajax.open("POST",'http://mahikanthnag.net23.net/imageSave.php',true);
//     ajax.setRequestHeader("Content-type","application/upload");
 
//     ajax.send(image);


 
    
 });   
