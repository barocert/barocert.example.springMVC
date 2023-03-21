<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>Barocert Kakaocert Service SpringMVC Example.</title>
</head>
<body>
<div id="content">
    <p class="heading1">Response</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <ul>
            <li>ReceiptID (접수 아이디) : ${result.receiptID}</li>
            <li>State (상태) : ${result.state}</li>
            
            <c:forEach items="${result.bulkSignedData}" var="BulkSignedData" varStatus="status">
            	<li>BulkSignedData (전자서명 데이터 전문) : ${BulkSignedData}</li>
            </c:forEach>
            
            <li>Ci (연계정보) : ${result.ci}</li>
        </ul>
    </fieldset>
</div>
</body>
</html>