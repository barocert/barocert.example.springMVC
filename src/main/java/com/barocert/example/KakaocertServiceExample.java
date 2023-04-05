package com.barocert.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.barocert.BarocertException;
import com.barocert.kakaocert.KakaocertService;
import com.barocert.kakaocert.cms.RequestCMS;
import com.barocert.kakaocert.cms.ResponseCMS;
import com.barocert.kakaocert.cms.ResponseStateCMS;
import com.barocert.kakaocert.cms.ResponseVerifyCMS;
import com.barocert.kakaocert.esign.MultiESignTokens;
import com.barocert.kakaocert.esign.RequestESign;
import com.barocert.kakaocert.esign.RequestMultiESign;
import com.barocert.kakaocert.esign.ResponseESign;
import com.barocert.kakaocert.esign.ResponseMultiESign;
import com.barocert.kakaocert.esign.ResponseStateESign;
import com.barocert.kakaocert.esign.ResponseStateMultiESign;
import com.barocert.kakaocert.esign.ResponseVerifyESign;
import com.barocert.kakaocert.esign.ResponseVerifyMultiESign;
import com.barocert.kakaocert.verifyauth.RequestVerifyAuth;
import com.barocert.kakaocert.verifyauth.ResponseStateVerify;
import com.barocert.kakaocert.verifyauth.ResponseVerifyAuth;
import com.barocert.kakaocert.verifyauth.ResponseVerifyVerifyAuth;

@Controller
public class KakaocertServiceExample {

    @Autowired
    private KakaocertService kakaocertService;

    @Value("#{EXAMPLE_CONFIG.ClientCode}")
    private String ClientCode;

    /*
     * 카카오톡 사용자에게 본인인증 전자서명을 요청합니다.
     */
    @RequestMapping(value = "kakaocert/requestVerifyAuth", method = RequestMethod.GET)
    public String requestVerifyAuth(Model m) throws BarocertException {

        // 본인인증 요청 정보 객체
        RequestVerifyAuth verifyAuthRequest = new RequestVerifyAuth();

        // 수신자 정보
        // 휴대폰번호,성명,생년월일 또는 Ci(연계정보)값 중 택 일
        verifyAuthRequest.setReceiverHP(kakaocertService.encryptGCM("01054437896"));
        verifyAuthRequest.setReceiverName(kakaocertService.encryptGCM("최상혁"));
        verifyAuthRequest.setReceiverBirthday(kakaocertService.encryptGCM("19880301"));
        // request.setCi(kakaocertService.encryptGCM(""));

        // 인증요청 메시지 제목 - 최대 40자
        verifyAuthRequest.setReqTitle("인증요청 메시지 제목란");
        // 인증요청 만료시간 - 최대 1,000(초)까지 입력 가능
        verifyAuthRequest.setExpireIn(1000);
        // 서명 원문 - 최대 2,800자 까지 입력가능
        verifyAuthRequest.setToken(kakaocertService.encryptGCM("본인인증요청토큰"));

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        verifyAuthRequest.setAppUseYN(false);

        // App to App 방식 이용시, 호출할 URL
        // verifyAuthRequest.setReturnURL("https://www.kakaocert.com");

        try {
            ResponseVerifyAuth result = kakaocertService.requestVerifyAuth(ClientCode, verifyAuthRequest);
            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/requestVerifyAuth";
    }

    /*
     * 본인인증 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다.
     */
    @RequestMapping(value = "kakaocert/stateVerifyAuth", method = RequestMethod.GET)
    public String stateVerifyAuth(Model m) {

        // 본인인증 요청시 반환된 접수아이디
        String receiptID = "02304050230300000040000000000003";

        try {
            ResponseStateVerify result = kakaocertService.stateVerifyAuth(ClientCode, receiptID);
            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/stateVerifyAuth";
    }

    /*
     * 본인인증 요청시 반환된 접수아이디를 통해 본인인증 서명을 검증합니다. 
     * 검증하기 API는 완료된 전자서명 요청당 1회만 요청 가능하며, 사용자가 서명을 완료후 유효시간(10분)이내에만 요청가능 합니다.
     */
    @RequestMapping(value = "kakaocert/verifyVerifyAuth", method = RequestMethod.GET)
    public String verifyVerifyAuth(Model m) {

        // 본인인증 요청시 반환된 접수아이디
        String receiptID = "02304050230300000040000000000003";

        try {
            ResponseVerifyVerifyAuth result = kakaocertService.verifyVerifyAuth(ClientCode, receiptID);

            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/verifyVerifyAuth";
    }

    /*
     * 카카오톡 사용자에게 전자서명을 요청합니다.(단건)
     */
    @RequestMapping(value = "kakaocert/requestESign", method = RequestMethod.GET)
    public String requestESign(Model m) throws BarocertException {

        // 전자서명 요청 정보 객체
        RequestESign eSignRequest = new RequestESign();

        // 수신자 정보
        // 휴대폰번호,성명,생년월일 또는 Ci(연계정보)값 중 택 일
        eSignRequest.setReceiverHP(kakaocertService.encryptGCM("01054437896"));
        eSignRequest.setReceiverName(kakaocertService.encryptGCM("최상혁"));
        eSignRequest.setReceiverBirthday(kakaocertService.encryptGCM("19880301"));
        // request.setCi(kakaocertService.encryptGCM(""));

        // 인증요청 메시지 제목 - 최대 40자
        eSignRequest.setReqTitle("전자서명단건테스트");
        // 인증요청 만료시간 - 최대 1,000(초)까지 입력 가능
        eSignRequest.setExpireIn(1000);
        // 서명 원문 - 원문 2,800자 까지 입력가능
        eSignRequest.setToken(kakaocertService.encryptGCM("전자서명단건테스트데이터"));
        // 서명 원문 유형
        // TEXT - 일반 텍스트, HASH - HASH 데이터
        eSignRequest.setTokenType("TEXT");

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        eSignRequest.setAppUseYN(false);

        // App to App 방식 이용시, 호출할 URL
        // eSignRequest.setReturnURL("https://www.kakaocert.com");

        try {
            ResponseESign result = kakaocertService.requestESign(ClientCode, eSignRequest);
            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/requestESign";
    }

    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다. (단건)
     */
    @RequestMapping(value = "kakaocert/stateESign", method = RequestMethod.GET)
    public String stateESign(Model m) {

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "02304050230300000040000000000007";

        try {
            ResponseStateESign result = kakaocertService.stateESign(ClientCode, receiptID);
            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/stateESign";
    }

    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명을 검증합니다. (단건)
     * 검증하기 API는 완료된 전자서명 요청당 1회만 요청 가능하며, 사용자가 서명을 완료후 유효시간(10분)이내에만 요청가능 합니다.
     */
    @RequestMapping(value = "kakaocert/verifyESign", method = RequestMethod.GET)
    public String verfiyESign(Model m) {

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "02304050230300000040000000000007";

        try {

            ResponseVerifyESign result = kakaocertService.verifyESign(ClientCode, receiptID);
            
            //TODO CI 복호화 처리
            
            m.addAttribute("result", result);

        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/verifyESign";
    }

    /*
     * 카카오톡 사용자에게 전자서명을 요청합니다.(복수)
     */
    @RequestMapping(value = "kakaocert/RequestMultiESign", method = RequestMethod.GET)
    public String RequestMultiESign(Model m) throws BarocertException {

        // 전자서명 요청 정보 객체
        RequestMultiESign multiESignRequest = new RequestMultiESign();

        // 수신자 정보
        // 휴대폰번호,성명,생년월일 또는 Ci(연계정보)값 중 택 일
        multiESignRequest.setReceiverHP(kakaocertService.encryptGCM("01054437896"));
        multiESignRequest.setReceiverName(kakaocertService.encryptGCM("최상혁"));
        multiESignRequest.setReceiverBirthday(kakaocertService.encryptGCM("19880301"));
        // multiESignRequest.setCi(kakaocertService.encryptGCM(""));

        // 인증요청 메시지 제목 - 최대 40자
        multiESignRequest.setReqTitle("전자서명복수테스트");
        // 인증요청 만료시간 - 최대 1,000(초)까지 입력 가능
        multiESignRequest.setExpireIn(1000);

        // 개별문서 등록 - 최대 20 건
        // 개별 요청 정보 객체
        MultiESignTokens token = new MultiESignTokens();
        // 인증요청 메시지 제목 - 최대 40자
        token.setReqTitle("전자서명복수문서테스트1");
        // 서명 원문 - 원문 2,800자 까지 입력가능
        token.setToken(kakaocertService.encryptGCM("전자서명복수테스트데이터1"));
        multiESignRequest.addToken(token);

        // 개별 요청 정보 객체
        MultiESignTokens token2 = new MultiESignTokens();
        // 인증요청 메시지 제목 - 최대 40자
        token2.setReqTitle("전자서명복수문서테스트2");
        // 서명 원문 - 원문 2,800자 까지 입력가능
        token2.setToken(kakaocertService.encryptGCM("전자서명복수테스트데이터2"));
        multiESignRequest.addToken(token2);

        // 서명 원문 유형
        // TEXT - 일반 텍스트, HASH - HASH 데이터
        multiESignRequest.setTokenType("TEXT");

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        multiESignRequest.setAppUseYN(false);

        // App to App 방식 이용시, 에러시 호출할 URL
        // request.setReturnURL("https://www.kakaocert.com");

        try {
            ResponseMultiESign result = kakaocertService.requestMultiESign(ClientCode, multiESignRequest);
            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/requestMultiESign";
    }

    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다. (복수)
     */
    @RequestMapping(value = "kakaocert/stateMultiESign", method = RequestMethod.GET)
    public String stateMultiESign(Model m) {

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "02304050230300000040000000000006";

        try {
            ResponseStateMultiESign result = kakaocertService.stateMultiESign(ClientCode, receiptID);
            m.addAttribute("result", result);

        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/stateMultiESign";
    }

    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명을 검증합니다. (복수)
     * 검증하기 API는 완료된 전자서명 요청당 1회만 요청 가능하며, 사용자가 서명을 완료후 유효시간(10분)이내에만 요청가능 합니다.
     */
    @RequestMapping(value = "kakaocert/verifyMultiESign", method = RequestMethod.GET)
    public String verifyMultiESign(Model m) {

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "02304050230300000040000000000006";

        try {
            ResponseVerifyMultiESign result = kakaocertService.verifyMultiESign(ClientCode, receiptID);
            
            //TODO CI 복호화 처리
            
            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/verifyMultiESign";
    }

    /*
     * 카카오톡 사용자에게 자동이체 출금동의 전자서명을 요청합니다.
     */
    @RequestMapping(value = "kakaocert/requestCMS", method = RequestMethod.GET)
    public String requestCMS(Model m) throws BarocertException {

    	// 출금동의 요청 정보 객체
        RequestCMS cmsRequest = new RequestCMS();

        // 수신자 정보
        // 휴대폰번호,성명,생년월일 또는 Ci(연계정보)값 중 택 일
        cmsRequest.setReceiverHP(kakaocertService.encryptGCM("01054437896"));
        cmsRequest.setReceiverName(kakaocertService.encryptGCM("최상혁"));
        cmsRequest.setReceiverBirthday(kakaocertService.encryptGCM("19880301"));
        // cmsRequest.setCi(kakaocertService.encryptGCM("");

        // 인증요청 메시지 제목 - 최대 40자
        cmsRequest.setReqTitle("인증요청 메시지 제공란");

        // 인증요청 만료시간 - 최대 1,000(초)까지 입력 가능
        cmsRequest.setExpireIn(1000);

        // 청구기관명 - 최대 100자
        cmsRequest.setRequestCorp(kakaocertService.encryptGCM("청구기관명란"));
        // 출금은행명 - 최대 100자
        cmsRequest.setBankName(kakaocertService.encryptGCM("출금은행명란"));
        // 출금계좌번호 - 최대 32자
        cmsRequest.setBankAccountNum(kakaocertService.encryptGCM("9-4324-5117-58"));
        // 출금계좌 예금주명 - 최대 100자
        cmsRequest.setBankAccountName(kakaocertService.encryptGCM("예금주명 입력란"));
        // 출금계좌 예금주 생년월일 - 8자
        cmsRequest.setBankAccountBirthday(kakaocertService.encryptGCM("19930112"));
        // 출금유형
        // CMS - 출금동의용, FIRM - 펌뱅킹, GIRO - 지로용
        cmsRequest.setBankServiceType(kakaocertService.encryptGCM("CMS")); // CMS, FIRM, GIRO

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        cmsRequest.setAppUseYN(false);

        // App to App 방식 이용시, 에러시 호출할 URL
        // request.setReturnURL("https://www.kakaocert.com");

        try {
            ResponseCMS result = kakaocertService.requestCMS(ClientCode, cmsRequest);
            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/requestCMS";
    }

    /*
     * 자동이체 출금동의 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다.
     */
    @RequestMapping(value = "kakaocert/stateCMS", method = RequestMethod.GET)
    public String stateCMS(Model m) {

        // 출금동의 요청시 반환된 접수아이디
        String receiptID = "02304050230300000040000000000008";

        try {
            ResponseStateCMS result = kakaocertService.stateCMS(ClientCode, receiptID);
            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/stateCMS";
    }

    /*
     * 자동이체 출금동의 요청시 반환된 접수아이디를 통해 서명을 검증합니다.
     * 검증하기 API는 완료된 전자서명 요청당 1회만 요청 가능하며, 사용자가 서명을 완료후 유효시간(10분)이내에만 요청가능 합니다.
     */
    @RequestMapping(value = "kakaocert/verifyCMS", method = RequestMethod.GET)
    public String verifyCMS(Model m) {

        // 출금동의 요청시 반환된 접수아이디
        String receiptID = "02304050230300000040000000000008";

        try {
            ResponseVerifyCMS result = kakaocertService.verifyCMS(ClientCode, receiptID);
            
            //TODO CI 복호화 처리
            
            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/verifyCMS";
    }

}
