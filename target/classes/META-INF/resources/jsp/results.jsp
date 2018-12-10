<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <!-- Material Design for Bootstrap fonts and icons -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">

    <!-- Material Design for Bootstrap CSS -->
    <link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css" integrity="sha384-wXznGJNEXNG1NFsbm0ugrLFMQPWswR3lds2VeinahP8N0zJw9VWSopbjv2x7WCvX" crossorigin="anonymous">


    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <script src="../js/bootstrap.min.js"></script>
    <style>
          body {
            padding-top: 20px;
            padding-left: 60px ;
            padding-right: 60px;

          }
     </style>

</head>

<body>
    <h1 class="display-2">Oligo Friend </h1>
    <br></br>
    <br></br>


    <!--RESULTS! -->

        <h4>Final Result:</h4>
        <p class = "lead">
        <div id="finalResultDiv" >
            <when test="${not empty finalResult}">
                <span>${finalResult}</span>
            </when>
        </div>
        </p>
        <form action ="/oligo" method = "get" id="return" role="form">
            <input type="hidden" id="returnFromResult" name="nextJSP" value="home">
            <div class="col order-12">


                <button type="submit" class="btn btn-primary btn-lg">

                    Return
                    <span class="glyphicon glyphicon-option-horizontal"></span>
                </button>

            </div>
        </form>





</body>
</html>


