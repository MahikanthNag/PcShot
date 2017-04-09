// // document.addEventListener('DOMContentLoaded', function() 
// {
// //   var checkPageButton = document.getElementById('checkPage');
// //   checkPageButton.addEventListener('click', function() 
//       {

// //     chrome.tabs.getSelected(null, function(tab) 
//         {
// //       d = document;

// //       var f = d.createElement('form');
// //       f.action = 'http://gtmetrix.com/analyze.html?bm';
// //       f.method = 'post';
// //       var i = d.createElement('input');
// //       i.type = 'hidden';
// //       i.name = 'url';
// //       i.value = tab.url;
// //       f.appendChild(i);
// //       d.body.appendChild(f);
// //       f.submit();
// //      });
// //   }, false);
// // }, false);
// document.addEventListener('DOMContentLoaded', function() {
//   var checkPageButton = document.getElementById('checkPage');
//   checkPageButton.addEventListener('click', function() {
//     console.log("hai buttonn clicked")
// html2canvas(document.body, {
//   onrendered: function(canvas) {
//     document.body.appendChild(canvas);

// var canvasData = canvas.toDataURL("image/png");
// var ajax = new XMLHttpRequest();
// ajax.open("POST",'http://mahikanthnag.net23.net/imageSave.php',false);
// ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
// ajax.send(canvasData);



//       },
//       // width: 300,
//       // height: 300

//   // });



//   // var dataUrl=canvas.toDataUrl();

  // $.ajax({
  // type: "POST",
  // url: "script.php",
  // data: { 
  //    imgBase64: dataURL
  //   }
  //   }).done(function(o) {
  //   console.log('saved'); 
  // // If you want the file to be visible in the browser 
  // - please modify the callback in javascript. All you
  // need is to return the url to the file, you just saved 
  // and than put the image in your browser.
  //  });


//   }, false);
//  }, false);


chrome.tabs.query({active:true,currentWindow:true},function(tabArray){
    console.log("hai");
    console.log(tabArray[0].url);
});

chrome.tabs.captureVisibleTab(null, {}, function (image) {
   // You can add that image HTML5 canvas, or Element.
     //document.body.appendChild(image);
     console.log(image);


  // $.ajax({
  // type: "POST",
  // url: "http://mahikanthnag.net23.net/imageSave.php",
  // data: { 
  //    imgBase64: image
  //   }
  //   }).done(function(o) {
  //   console.log('saved'); 
  // // If you want the file to be visible in the browser 
  // // - please modify the callback in javascript. All you
  // // need is to return the url to the file, you just saved 
  // // and than put the image in your browser.
  //  });

var ajax = new XMLHttpRequest();
    ajax.open("POST",'http://mahikanthnag.net23.net/imageSave.php',true);
    ajax.setRequestHeader("Content-type","application/upload");
 
    ajax.send(image);


});