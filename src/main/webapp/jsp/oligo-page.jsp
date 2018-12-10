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
            padding-bottom: 60px;

          }
        </style>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


    </head>

    <body>

    <h1 class="display-2">Oligo Friend </h1>





    <div class="px-2">
        <!--<h2 class="p-3">Upload a FASTA File...</h2>-->

         <div class="container">
             <div class="panel panel-default">

                 <div class="panel-heading">Upload a FASTA File...</div>


                 <!div class="col-6">

                    <!--File Upload Form -->

                    <form action="/oligo" method="get" id="uploadFASTAFile" role="form" class="needs-validation" novalidate>
                        <input type="hidden" id="fileOrText1" name="fileOrText" value="file">

                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <label class="file-upload btn btn-primary">
                                    Browse ... <input type="file"  />
                                </label>
                                <button type="submit" class="btn btn-primary btn-lg" onclick="handleClick();">
                                    <span class="glyphicon glyphicon-arrow-up"></span> Upload

                                </button>
                            </div>
                        </div>


                        <br></br>
                        <br></br>

                        <!-- sizes --

                        <div class="panel panel-default">
                            <div class="panel-heading">Sequence Parameters:</div>
                            <div class="panel-body">

                                    <div class="row justify-content-md-center">
                                        <div class="col-sm">
                                            <label for="min_s">Mininum Oligo Size</label>
                                            <input type="text" class="form-control" placeholder="Mininum Oligo Size" value="90" id="min_s" required>
                                        </div>
                                        <div class="col-sm">
                                            <label for="max_s">Maximum Oligo Size</label>
                                            <input type="text" class="form-control" placeholder="Maximum Oligo Size" value="100"  id="max_s" required>
                                        </div>
                                        <div class="col-sm">
                                            <label for="min_o">Mininum Overlap Size</label>
                                            <input type="text" class="form-control" placeholder="Minimum Overlap Size" value="25"  id="min_o" required>
                                        </div>
                                        <div class="col-sm">
                                            <label for="max_o">Maxmimum Overlap Size</label>
                                            <input type="text" class="form-control" placeholder="Maximum Overlap Size" value="60"  id="max_o"  required>
                                        </div>
                                    </div>

                            </div>
                        </div>

                        <!--end of sizes-->
                    </form>
                 </div>


                 <!--Text Input Form -->
                 <div class="panel panel-default">
                     <div class="panel-heading">...Or Copy and Paste Sequence (FASTA format) Here</div>
                     <div class = "panel-body">
                     <form action ="/oligo" method = "get" id="getGreetingText" role="form">

                         <div class = "panel-body">
                             <input type="hidden" id="fileOrText2" name="fileOrText" value="text">
                             <!div class="col-6">
                                 <div class="form-group">
                                    <!-- <label for="exampleFormControlTextarea1"> </label>-->
                                     <textarea name="textInput" class="form-control" required="true" id="exampleFormControlTextarea1" rows="6"  placeholder="Paste FASTA sequence here"></textarea>
                                 </div>

                                 <div align="right">
                                     <button type="submit" class="btn btn-primary btn-lg" onclick="handleClick()">
                                     Submit Text
                                     <span class="glyphicon glyphicon-option-horizontal"></span>

                                 </button>
                                 </div>

                         </div>

                        <!/div>

                         <!-- sizes --

                         <div class="panel panel-default">
                             <div class="panel-heading">Sequence Parameters:</div>
                             <div class="panel-body">

                                     <div class="row justify-content-md-center">
                                         <div class="col-sm">
                                             <label for="min_s">Mininum Oligo Size</label>
                                             <input type="text" class="form-control" placeholder="Mininum Oligo Size" value="90" id="min_s" required>
                                         </div>
                                         <div class="col-sm">
                                             <label for="max_s">Maximum Oligo Size</label>
                                             <input type="text" class="form-control" placeholder="Maximum Oligo Size" value="100"  id="max_s" required>
                                         </div>
                                         <div class="col-sm">
                                             <label for="min_o">Mininum Overlap Size</label>
                                             <input type="text" class="form-control" placeholder="Minimum Overlap Size" value="25"  id="min_o" required>
                                         </div>
                                         <div class="col-sm">
                                             <label for="max_o">Maxmimum Overlap Size</label>
                                             <input type="text" class="form-control" placeholder="Maximum Overlap Size" value="60"  id="max_o"  required>
                                         </div>
                                     </div>

                             </div>
                         </div>
                         <!--end of sizes-->

                     </form>
                 </div>
                 </div>





            <!/div>
         </div>

    </div>








    <!--RESULTS! -->
        <div id="finalResultDiv">


            <c:choose>
                <c:when test="${not empty finalResult}">
                    <h4>Results</h4>
                    <br>
                    <p class = "lead">
                    <c:forEach var="result" items="${finalResult}">
                        <span>${result}</span>
                        <br></br>
                    </c:forEach>

                    </p>

                    <form action ="/oligo" method = "get" id="return" role="form">
                        <input type="hidden" id="returnFromResult" name="nextJSP" value="home">
                        <div class="col order-12">


                            <button type="submit" class="btn btn-primary btn-lg" onclick="handleClick();">

                                Another!
                                <span class="glyphicon glyphicon-option-horizontal"></span>

                            </button>

                        </div>
                    </form>

                </c:when>
                <c:otherwise>
                    <div id= "emptyInputError" class="alert alert-info">
                        <em>No results to show</em>
                    </div>
                </c:otherwise>
            </c:choose>


        </div>








    <!-- WELCOME MESSAGE -->


    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <button type="submit" class="close" data-dismiss="alert" aria-label="Close">
            Close This
            <span aria-hidden="true">&times;</span>
        </button>
        <h1 class="display-3">Hello friend!</h1>

        <h4 class="display-4">This is Oligo Friend, an online tool for breakpoint and overlap visualization.</h4>
        <hr class="my-4">
        <p class="text-sm-left">Created by Sarah Almeda, Computer Science Major at TCNJ<br> for Dr. Papamichail's Algorithms in Computational Biology Course.<br> This project funded by CRA-W CREU Fall 2018- Spring 2019. <br></p>

    </div>


    </body>
</html>


