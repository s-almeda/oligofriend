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



            <div class="container">
                <h2>Upload a FASTA File...</h2>
                <div class="row">
                    <div class="col">
                        <form class="md-form">
                            <div class="file-field">
                                <div class="btn btn-outline-success btn-lg float-left">
                                    <span>Choose file</span>
                                    <input type="file" class="form-control-file" id="exampleFormControlFile1">
                                </div>

                                <div class="file-path-wrapper">
                                    <button type="submit" class="btn btn-primary btn-lg">
                                        <span class="glyphicon glyphicon-arrow-up"></span> Upload
                                    </button>
                                </div>
                            </div>

                    </div>
                    <div class="col order-12">
                        <div class="form-group">
                            <label for="exampleFormControlTextarea1">... Or Copy and Paste Sequence Here</label>
                            <textarea class="form-control" id="exampleFormControlTextarea1" rows="6"></textarea>
                        </div>
                        <br></br>
                        <div class="file-path-wrapper">
                            <button type="submit" class="btn btn-primary btn-lg">

                                Submit
                                <span class="glyphicon glyphicon-option-horizontal"></span>
                            </button>
                        </div>
                    </div>
            </div>






            <h2>Employees</h2>
            <!--Search Form -->
            <form action="/employee" method="get" id="seachEmployeeForm" role="form">
                <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
                <div class="form-group col-xs-5">
                    <input type="text" name="employeeName" id="employeeName" class="form-control" required="true" placeholder="Type the Name or Last Name of the employee"/>                    
                </div>
                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Search
                </button>
                <br></br>
                <br></br>
            </form>
            
            <!--Employees List-->
            <form action="/employee" method="post" id="employeeForm" role="form" >              
               
                <c:choose>
                    <c:when test="${not empty employeeList}">
                        <table  class="table table-striped">
                            <thead>
                                <tr>
                                    <td>#</td>
                                    <td>Name</td>
                                    <td>Last name</td>
                                    <td>Birth date</td>
                                    <td>Role</td>
                                    <td>Department</td>
                                    <td>E-mail</td>                                 
                                </tr>
                            </thead>
                            <c:forEach var="employee" items="${employeeList}">
                                <c:set var="classSucess" value=""/>
                                <c:if test ="${idEmployee == employee.id}">                        	
                                    <c:set var="classSucess" value="info"/>
                                </c:if>
                                <tr class="${classSucess}">
                                    <td>${employee.id}</td>
                                    <td>${employee.name}</td>
                                    <td>${employee.lastName}</td>
                                    <td>${employee.birthDate}</td>
                                    <td>${employee.role}</td>
                                    <td>${employee.department}</td>
                                    <td>${employee.email}</td>   
                                   
                                </tr>
                            </c:forEach>               
                        </table>  
                    </c:when>                    
                    <c:otherwise>
                        <br>           
                        <div class="alert alert-info">
                            No people found matching your search criteria
                        </div>
                    </c:otherwise>
                </c:choose>                        
            </form>
           
        </div>
    </body>
</html>