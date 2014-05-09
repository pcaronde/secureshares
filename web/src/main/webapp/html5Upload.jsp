<%--
  Created by IntelliJ IDEA.
  User: pcaron
  Date: 5/9/14
  Time: 3:14 PM
Original File
https://github.com/mockenoff/HTML5-AJAX-File-Uploader/blob/master/index.html
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="lastAction" value="${6}" scope="session"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />



<h3>Secure Uploads</h3>
<P>Choose files from your local computer and securely upload to the server</P>

<p><button id="upload">Upload</button></p>
<p><span id="progress" class="progress">0%</span></p>

