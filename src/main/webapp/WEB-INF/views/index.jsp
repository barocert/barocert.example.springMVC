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
    <p class="heading1">Barocert SpringMVC Example</p>
    <br/>
    <fieldset class="fieldset1">
        <legend>Kakaocert 본인인증 API</legend>
        <ul>
            <li><a href="kakaocert/requestIdentity">RequestIdentity</a> - 본인인증 요청</li>
            <li><a href="kakaocert/getIdentityStatus">GetIdentityStatus</a> - 본인인증 상태확인</li>
            <li><a href="kakaocert/verifyIdentity">VerifyIdentity</a> - 본인인증 검증</li>
        </ul>
    </fieldset>
    
    <fieldset class="fieldset1">
        <legend>Kakaocert 전자서명 API</legend>
        <ul>
            <li><a href="kakaocert/requestSign">RequestSign</a> - 전자서명 요청(단건)</li>
            <li><a href="kakaocert/getSignStatus">GetSignStatus</a> - 전자서명 상태확인(단건)</li>
            <li><a href="kakaocert/verifySign">VerifySign</a> - 전자서명 검증(단건)</li>
            <li><a href="kakaocert/requestMultiSign">RequestMultiSign</a> - 전자서명 요청(복수)</li>
            <li><a href="kakaocert/getMultiSignStatus">getMultiSignStatus</a> - 전자서명 상태확인(복수)</li>
            <li><a href="kakaocert/verifyMultiSign">VerifyMultiSign</a> - 전자서명 검증(복수)</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>Kakaocert 출금동의 API</legend>
        <ul>
            <li><a href="kakaocert/requestCMS">RequestCMS</a> - 출금동의 요청</li>
            <li><a href="kakaocert/getCMSStatus">GetCMSStatus</a> - 출금동의 상태확인</li>
            <li><a href="kakaocert/verifyCMS">VerifyCMS</a> - 출금동의 검증</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>Kakaocert 간편로그인 API</legend>
        <ul>
            <li><a href="kakaocert/verifyLogin">VerifyLogin</a> - 간편로그인 검증</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>Navercert 본인인증 API</legend>
        <ul>
            <li><a href="navercert/requestIdentity">RequestIdentity</a> - 본인인증 요청</li>
            <li><a href="navercert/getIdentityStatus">GetIdentityStatus</a> - 본인인증 상태확인</li>
            <li><a href="navercert/verifyIdentity">VerifyIdentity</a> - 본인인증 검증</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>Navercert 전자서명 API</legend>
        <ul>
            <li><a href="navercert/requestSign">RequestSign</a> - 전자서명 요청(단건)</li>
            <li><a href="navercert/getSignStatus">GetSignStatus</a> - 전자서명 상태확인(단건)</li>
            <li><a href="navercert/verifySign">VerifySign</a> - 전자서명 검증(단건)</li>
            <li><a href="navercert/requestMultiSign">RequestMultiSign</a> - 전자서명 요청(복수)</li>
            <li><a href="navercert/getMultiSignStatus">getMultiSignStatus</a> - 전자서명 상태확인(복수)</li>
            <li><a href="navercert/verifyMultiSign">VerifyMultiSign</a> - 전자서명 검증(복수)</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>Navercert 출금동의 API</legend>
        <ul>
            <li><a href="navercert/requestCMS">RequestCMS</a> - 출금동의 요청</li>
            <li><a href="navercert/getCMSStatus">GetCMSStatus</a> - 출금동의 상태확인</li>
            <li><a href="navercert/verifyCMS">VerifyCMS</a> - 출금동의 검증</li>
        </ul>
    </fieldset>

    <br/>
    <fieldset class="fieldset1">
        <legend>Passcert 본인인증 API</legend>
        <ul>
            <li><a href="passcert/requestIdentity">RequestIdentity</a> - 본인인증 요청</li>
            <li><a href="passcert/getIdentityStatus">GetIdentityStatus</a> - 본인인증 상태확인</li>
            <li><a href="passcert/verifyIdentity">VerifyIdentity</a> - 본인인증 검증</li>
        </ul>
    </fieldset>
    
    <fieldset class="fieldset1">
        <legend>Passcert 전자서명 API</legend>
        <ul>
            <li><a href="passcert/requestSign">RequestSign</a> - 전자서명 요청</li>
            <li><a href="passcert/getSignStatus">GetSignStatus</a> - 전자서명 상태확인</li>
            <li><a href="passcert/verifySign">VerifySign</a> - 전자서명 검증</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>Passcert 출금동의 API</legend>
        <ul>
            <li><a href="passcert/requestCMS">RequestCMS</a> - 출금동의 요청</li>
            <li><a href="passcert/getCMSStatus">GetCMSStatus</a> - 출금동의 상태확인</li>
            <li><a href="passcert/verifyCMS">VerifyCMS</a> - 출금동의 검증</li>
        </ul>
    </fieldset>

    <fieldset class="fieldset1">
        <legend>Passcert 간편로그인 API</legend>
        <ul>
            <li><a href="passcert/requestLogin">RequestLogin</a> - 간편로그인 요청</li>
            <li><a href="passcert/getLoginStatus">GetLoginStatus</a> - 간편로그인 상태확인</li>
            <li><a href="passcert/verifyLogin">VerifyLogin</a> - 간편로그인 검증</li>
        </ul>
    </fieldset>
</div>
</body>
</html>