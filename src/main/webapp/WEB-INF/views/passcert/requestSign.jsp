<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>Barocert Service SpringMVC Example</title>
</head>
<body>
<div id="content">
    <p class="heading1">패스 전자서명 요청 API SDK SpringMVC Example</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <ul>
            <li>receiptID (접수아이디) : ${result.receiptID}</li>
            <li>scheme (앱스킴) : ${result.scheme}</li>
            <li>MarketUrl (앱다운로드URL) : ${result.marketUrl}</li>
        </ul>
    </fieldset>
</div>
</body>
</html>