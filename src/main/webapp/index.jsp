<html>
    <head>
        <script type="text/javascript" src="assets/vendor.js"></script>
        <link rel="stylesheet" type="text/css" href="assets/app.css">
    </head>

    <body>
        <div id="app"></div>

        <script src="assets/app.js"></script>
    </body>
    <jsp:forward page="/employee" />


    <form class="md-form">
        <div class="file-field">
            <div class="btn btn-outline-success btn-rounded waves-effect btn-sm float-left">
                <span>Choose file</span>
                <input type="file">
            </div>
            <div class="file-path-wrapper">
               <input class="file-path validate" type="text" placeholder="Upload your file">
            </div>
        </div>
</html>