<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>BaroCert SpringMVC Response</title>
</head>
<body>
<div id="content">
    <p class="heading1">패스 간편로그인 상태확인 API SpringMVC Example</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        
        <fieldset class="fieldset2">
            <ul>
                <li>ClientCode (이용기관 코드) : ${result.clientCode}</li>
                <li>ReceiptID (접수 아이디) : ${result.receiptID}</li>
                <li>State (상태) : ${result.state}</li>
                <li>RequestDT (서명요청일시) : ${result.requestDT}</li>
                <li>CompleteDT (서명완료일시) : ${result.completeDT}</li>
                <li>ExpireDT (서명만료일시) : ${result.expireDT}</li>
                <li>RejectDT (서명거절일시) : ${result.rejectDT}</li>
            </ul>
        </fieldset>
        
    </fieldset>
</div>
</body>
</html>
