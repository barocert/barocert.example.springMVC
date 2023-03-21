<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/resources/main.css" media="screen"/>
    <title>Barocert Kakaocert Service jsp Example.</title>
</head>
<body>
<div id="content">
    <p class="heading1">Barocert Kakaocert Service jsp Example.</p>
    <br/>
     <fieldset class="fieldset1">
        <legend>Service Attribute</legend>
        <ul>
            <li><a href="checkServiceAttribute">CheckServiceAttribute</a> - Serivce 속성 확인</li>
        </ul>
    </fieldset>
    <fieldset class="fieldset1">
        <legend>전자서명 API</legend>
        <ul>
            <li><a href="kakaocert/requestESign">RequestESign</a> - 전자서명 요청(단건)</li>
            <li><a href="kakaocert/bulkRequestESign">BulkRequestESign</a> - 전자서명 요청(다건)</li>
            <li><a href="kakaocert/getESignState">GetESignState</a> - 전자서명 상태확인(단건)</li>
            <li><a href="kakaocert/getBulkESignState">GetBulkESignState</a> - 전자서명 상태확인(다건)</li>
            <li><a href="kakaocert/verifyESign">VerifyESign</a> - 전자서명 검증(단건)</li>
            <li><a href="kakaocert/bulkVerifyESign">BulkVerifyESign</a> - 전자서명 검증(다건)</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>본인인증 API</legend>
        <ul>
            <li><a href="kakaocert/requestVerifyAuth">RequestVerifyAuth</a> - 본인인증 요청</li>
            <li><a href="kakaocert/getVerifyAuthState">GetVerifyAuthState</a> - 본인인증 상태확인</li>
            <li><a href="kakaocert/verifyAuth">VerifyAuth</a> - 본인인증 검증</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>출금동의 API</legend>
        <ul>
            <li><a href="kakaocert/requestCMS">RequestCMS</a> - 출금동의 요청</li>
            <li><a href="kakaocert/getCMSState">GetCMSState</a> - 출금동의 상태확인</li>
            <li><a href="kakaocert/verifyCMS">VerifyCMS</a> - 출금동의 검증</li>
        </ul>
    </fieldset>
</div>
</body>
</html>