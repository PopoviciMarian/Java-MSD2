<!DOCTYPE html>
<html>
<head>
    <title>File Upload with CAPTCHA</title>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script> <!-- Include Google reCAPTCHA API -->
</head>
<body>
<h2>Upload a Text File</h2>

<!-- Form for file upload -->
<form action="FileUploadServlet" method="post" enctype="multipart/form-data">
    <input type="file" name="file" accept=".txt" required>
    <br><br>

    <!-- Google reCAPTCHA widget -->
    <div class="g-recaptcha" data-sitekey="TOKEN"></div> <!-- Replace with your Site Key -->
    <br><br>

    <input type="submit" value="Upload">
</form>
</body>
</html>