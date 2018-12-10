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

    </head>

    <body>

        <h2>Upload a FASTA File...</h2>

         <div class="container">
             <div class="row">
                 <div class="col">

                    <!--File Upload Form -->
                    <form action="/oligo" method="get" id="uploadFASTAFile" role="form">
                        <input type="hidden" id="fileOrText1" name="fileOrText" value="file">
                        <div class="form-group col-xs-5">
                            <input type="file" name="fileUpload" id="formControlFile" class="form-control-file" required="true" placeholder="Upload File..."/>
                        </div>
                        <button type="submit" class="btn btn-primary btn-lg" onclick="showDiv()">
                            <span class="glyphicon glyphicon-arrow-up"></span> Upload
                        </button>
                        <br></br>
                        <br></br>
                    </form>
                 </div>

                 <!--Text Input Form -->
                 <form action ="/oligo" method = "get" id="getGreetingText" role="form">
                     <input type="hidden" id="fileOrText2" name="fileOrText" value="text">
                     <div class="col order-12">
                         <div class="form-group">
                             <label for="exampleFormControlTextarea1">... Or Copy and Paste Sequence Here</label>
                             <textarea name="textarea" class="form-control" required="true" id="exampleFormControlTextarea1" rows="6"  placeholder="Paste FASTA sequence here"></textarea>
                         </div>
                         <br></br>

                         <button type="submit" class="btn btn-primary btn-lg">

                             Submit
                             <span class="glyphicon glyphicon-option-horizontal"></span>
                         </button>

                    </div>
                 </form>





            </div>
         </div>

         <!--RESULTS! -->

         <h2>Final Result: </h2>
         <div id="finalResultDiv" >
             <when test="${not empty finalResult}">
                 <span>${finalResult}</span>
             </when>
         </div>



    </body>
</html>


