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
    <p class="heading1">네이버 전자서명(단건) 상태확인 API SDK SpringMVC Example</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        <fieldset class="fieldset2">
            <ul>
                <li>ReceiptID (접수 아이디) : ${result.receiptID}</li>
                <li>ClientCode (이용기관 코드) : ${result.clientCode}</li>
                <li>State (상태코드) : ${result.state}</li>
                <li>ExpireIn (요청 만료시간) : ${result.expireIn}</li>
                <li>CallCenterName (이용기관 명) : ${result.callCenterName}</li>
                <li>CallCenterNum (이용기관 연락처) : ${result.callCenterNum}</li>
                <li>ReqTitle (인증요청 메시지 제목) : ${result.reqTitle}</li>
                <li>TokenType (원문 구분) : ${result.tokenType}</li>
                <li>ReturnURL (복귀 URL) : ${result.returnURL}</li>
                <li>ExpireDT (서명만료일시) : ${result.expireDT}</li>
                <li>Scheme (앱스킴) : ${result.scheme}</li>
                <li>DeviceOSType (모바일장비 유형) : ${result.deviceOSType}</li>
                <li>AppUseYN (앱사용유무) : ${result.appUseYN}</li>
            </ul>
        </fieldset>
        
    </fieldset>
</div>
</body>
</html>
