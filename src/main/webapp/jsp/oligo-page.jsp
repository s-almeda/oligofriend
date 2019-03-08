<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <title>Oligo Friend</title>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <style>
          body {
            padding: 20px 60px 60px;
          }
        </style>
    </head>
    <body>
        <h1 class="display-2">Oligo Friend</h1>

        <div class="px-2">
            <div class="container">
                <div class="panel panel-default">
                    <div class="panel-heading">Upload a FASTA File...</div>
                    <form action="${pageContext.request.contextPath}/oligo" method="post" id="uploadFASTAFile" role="form" class="needs-validation" novalidate enctype='multipart/form-data'>
                        <input type="hidden" id="fileOrText1" name="fileOrText" value="file">

                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <label class="file-upload btn btn-primary">
                                    Browse ... <input type="file" name="file" />
                                </label>
                                <button type="submit" class="btn btn-primary btn-lg" onclick="handleClick();">
                                    <span class="glyphicon glyphicon-arrow-up"></span> Upload
                                </button>
                            </div>
                        </div>
                        <br>
                        <br>
                    </form>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">...Or Copy and Paste Sequence (FASTA format) Here</div>
                    <div class = "panel-body">
                        <form action ="${pageContext.request.contextPath}/oligo" method = "post" id="getGreetingText" role="form">
                            <div class = "panel-body">
                                <input type="hidden" id="fileOrText2" name="fileOrText" value="text">
                                <div class="form-group">
                                    <textarea name="textInput" class="form-control" required="true" id="exampleFormControlTextarea1" rows="6"  placeholder="Paste FASTA sequence here"></textarea>
                                </div>

                                <div align="right">
                                    <button type="submit" class="btn btn-primary btn-lg" onclick="handleClick()">
                                        Submit Text
                                        <span class="glyphicon glyphicon-option-horizontal"></span>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div id="finalResultDiv">
            <c:choose>
                <c:when test="${not empty finalResult}">
                    <h3>Results</h3>
                    <br>
                    <p>
                        <c:forEach var="result" items="${finalResult}">
                            <span>${result}</span>
                            <br>
                        </c:forEach>
                    </p>

                    <form action ="${pageContext.request.contextPath}/oligo" method = "get" id="return" role="form">
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

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
