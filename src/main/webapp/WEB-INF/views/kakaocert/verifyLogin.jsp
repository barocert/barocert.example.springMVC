<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>BaroCert SpringMVC Example</title>
</head>
<body>
<div id="content">
    <p class="heading1">Response</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <ul>
            <li>TxID (트랜잭션 아이디) : ${result.txID}</li>
            <li>State (상태) : ${result.state}</li>
            <li>SignedData (전자서명 데이터 전문) : ${result.signedData}</li>
            <li>Ci (연계정보) : ${result.ci}</li>
            <li>ReceiverName (수신자 성명) : ${result.receiverName}</li>
            <li>ReceiverYear (수신자 출생년도) : ${result.receiverYear}</li>
            <li>ReceiverDay (수신자 출생월일) : ${result.receiverDay}</li>
            <li>ReceiverHP (수신자 휴대폰번호) : ${result.receiverHP}</li>
            <li>ReceiverGender (수신자 성별) : ${result.receiverGender}</li>
            <li>receiverForeign (외국인 여부) : ${result.receiverForeign}</li>
            <li>receiverTelcoType (통신사 유형) : ${result.receiverTelcoType}</li>
        </ul>
    </fieldset>
</div>
</body>
</html>