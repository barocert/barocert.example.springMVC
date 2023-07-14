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
    <p class="heading1">패스 본인인증 상태확인 API SDK SpringMVC Example</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>${requestScope['javax.servlet.forward.request_uri']}</legend>
        
        <fieldset class="fieldset2">
            <ul>
                <li>ClientCode (이용기관 코드) : ${result.clientCode}</li>
                <li>ReceiptID (접수 아이디) : ${result.receiptID}</li>
                <li>State (상태코드) : ${result.state}</li>
                <li>ExpireIn (요청 만료시간) : ${result.expireIn}</li>
                <li>CallCenterName (이용기관 명) : ${result.callCenterName}</li>
                <li>CallCenterNum (이용기관 연락처) : ${result.callCenterNum}</li>
                <li>ReqTitle (인증요청 메시지 제목) : ${result.reqTitle}</li>
                <li>ReqMessage (인증요청 메시지 내용) : ${result.reqMessage}</li>
                <li>RequestDT (서명요청일시) : ${result.requestDT}</li>
                <li>CompleteDT (서명완료일시) : ${result.completeDT}</li>
                <li>ExpireDT (서명만료일시) : ${result.expireDT}</li>
                <li>RejectDT (서명거절일시) : ${result.rejectDT}</li>
                <li>TokenType (원문 유형) : ${result.tokenType}</li>
                <li>UserAgreementYN (사용자동의필요여부) : ${result.userAgreementYN}</li>
                <li>ReceiverInfoYN (사용자정보포함여부) : ${result.receiverInfoYN}</li>
                <li>TelcoType (통신사 유형) : ${result.telcoType}</li>
                <li>DeviceOSType (모바일장비 유형) : ${result.deviceOSType}</li>
                <li>Scheme (앱스킴) : ${result.scheme}</li>
                <li>AppUseYN (앱사용유무) : ${result.appUseYN}</li>
            </ul>
        </fieldset>
        
    </fieldset>
</div>
</body>
</html>
