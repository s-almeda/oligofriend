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

        <h4>Final Result ASDFJKLASDF TESTING:</h4>
        <p class = "lead">
        <div id="finalResultDiv" >
        <c:choose>
            <c:when test="${not empty finalResult}">
                    <c:forEach var="result" items="${finalResult}">
                        <span>${result}</span>
                        <br></br>
                    </c:forEach>
            </c:when>
            <c:otherwise>
                <br>
                <div class="alert alert-info">
                    Sorry, we couldn't do that. Try again?
                </div>
            </c:otherwise>
        </c:choose>
    </div>
        </p>
        <form action ="/oligo" method = "get" id="return" role="form">
            <input type="hidden" id="returnFromResult" name="nextJSP" value="home">
            <div class="col order-12">


                <button type="submit" class="btn btn-primary btn-lg">

                    Another!
                    <span class="glyphicon glyphicon-option-horizontal"></span>
                </button>

            </div>
        </form>





</body>
</html>


