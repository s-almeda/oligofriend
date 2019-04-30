console.log("woop")
console.log(thisResult);

var canvas = document.getElementById("myCanvas");
var width = canvas.width;
var height = canvas.height;
var verticalCenter = (canvas.height)*0.5;
var ctx = canvas.getContext("2d");

var marginBetweenLines = 10;

var up = verticalCenter - marginBetweenLines;
var down = verticalCenter + marginBetweenLines;

var isUp = true;

drawOligoStroke(ctx, 0, 97, up, 10);



console.log("woowoopp")

var start = 0;

oligos.slice().reverse().forEach(element => {
    console.log(element);

    if(isUp){
        drawOligoStroke(ctx, start, element.end, up, 0);
        isUp = false;
    }
    else{

        drawOligoStroke(ctx, start, element.end, down, 0);
        isUp = true;
    }

    start = element.end*2 - element.overlap*2;


});




function getRandomColor(){
    return ('#'+(0x1000000+(Math.random())*0xffffff).toString(16).substr(1,6));
}
function drawOligoStroke(ctx, start, end, height, leftPadding){
ctx.strokeStyle = getRandomColor();
ctx.moveTo(leftPadding+start, height);
ctx.lineTo(leftPadding+end*2, height);
ctx.stroke();
}