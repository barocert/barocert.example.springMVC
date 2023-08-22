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
    <p class="heading1">패스 간편로그인 검증 API SDK SpringMVC Example</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <ul>
            <li>ReceiptID (접수 아이디) : ${result.receiptID}</li>
            <li>State (상태) : ${result.state}</li>
            <li>ReceiverName (수신자 성명) : ${result.receiverName}</li>
            <li>ReceiverYear (수신자 출생년도) : ${result.receiverYear}</li>
            <li>ReceiverDay (수신자 출생월일) : ${result.receiverDay}</li>
            <li>ReceiverGender (수신자 성별) : ${result.receiverGender}</li>
            <li>ReceiverForeign (외국인 여부) : ${result.receiverForeign}</li>
            <li>ReceiverTelcoType (통신사 유형) : ${result.receiverTelcoType}</li>
            <li>SignedData (전자서명 데이터 전문) : ${result.signedData}</li>
            <li>Ci (연계정보) : ${result.ci}</li>
        </ul>
    </fieldset>
</div>
</body>
</html>