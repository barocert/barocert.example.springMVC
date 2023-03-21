package com.barocert.example;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.barocert.BarocertException;
import com.barocert.KakaocertService;
import com.barocert.kakaocert.cms.RequestCMS;
import com.barocert.kakaocert.cms.ResultCMS;
import com.barocert.kakaocert.cms.ResultCMSState;
import com.barocert.kakaocert.cms.VerifyCMSResult;
import com.barocert.kakaocert.esign.BulkRequestESign;
import com.barocert.kakaocert.esign.BulkResultESignState;
import com.barocert.kakaocert.esign.BulkVerifyESignResult;
import com.barocert.kakaocert.esign.RequestESign;
import com.barocert.kakaocert.esign.ResultESign;
import com.barocert.kakaocert.esign.ResultESignState;
import com.barocert.kakaocert.esign.VerifyEsignResult;
import com.barocert.kakaocert.esign.Tokens;
import com.barocert.kakaocert.verifyauth.RequestVerifyAuth;
import com.barocert.kakaocert.verifyauth.ReqVerifyAuthResult;
import com.barocert.kakaocert.verifyauth.VerifyAuthResult;
import com.barocert.kakaocert.verifyauth.VerifyAuthStateResult;

@Controller
public class KakaocertServiceExample {

    @Autowired
    private KakaocertService kakaocertService;

    // 이용기관코드
    // 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
    @Value("#{EXAMPLE_CONFIG.ClientCode}")
    private String ClientCode;

    /*
     * 카카오톡 사용자에게 본인인증 전자서명을 요청합니다.
     * - https://requestVerifyAuth
     */
    @RequestMapping(value = "kakaocert/requestVerifyAuth", method = RequestMethod.GET)
    public String requestVerifyAuth(Model m) throws BarocertException {

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        boolean isAppUseYN = false;

        // 본인인증 요청 정보 Object
        RequestVerifyAuth verifyAuthRequest = new RequestVerifyAuth();

        // 수신자 정보(휴대폰번호, 성명, 생년월일)와 Ci 값 중 택일
        verifyAuthRequest.setReceiverHP(kakaocertService.AES256Encrypt("01087674117"));
        verifyAuthRequest.setReceiverName(kakaocertService.AES256Encrypt("이승환"));
        verifyAuthRequest.setReceiverBirthday(kakaocertService.AES256Encrypt("19930112"));
        // request.setCi(kakaocertService.AES256Encrypt(""));

        verifyAuthRequest.setReqTitle("인증요청 메시지 제목란");
        verifyAuthRequest.setExpireIn(1000);
        
        verifyAuthRequest.setToken(kakaocertService.AES256Encrypt("본인인증요청토큰"));

        // App to App 방식 이용시, 에러시 호출할 URL
        // request.setReturnURL("https://kakao.barocert.com");

        try {
        	ReqVerifyAuthResult result = kakaocertService.requestVerifyAuth(ClientCode, verifyAuthRequest, isAppUseYN);

            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/requestVerifyAuth";
    }

    /*
     * 본인인증 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다.
     * - https://getVerifyAuthState
     */
    @RequestMapping(value = "kakaocert/getVerifyAuthState", method = RequestMethod.GET)
    public String getVerifyAuthState(Model m) {

        //전자서명 요청시 반환된 접수아이디
        String receiptID = "0230321223606000000000000000000000000001";

        try {
        	VerifyAuthStateResult result = kakaocertService.getVerifyAuthState(ClientCode, receiptID);
            
        	m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "kakaocert/getVerifyAuthState";
    }

    /*
     * 본인인증 요청시 반환된 접수아이디를 통해 본인인증 서명을 검증합니다.
     * - https://verifyAuth
     */
    @RequestMapping(value = "kakaocert/verifyAuth", method = RequestMethod.GET)
    public String verifyAuth(Model m) {

        //전자서명 요청시 반환된 접수아이디
        String receiptID = "0230321223606000000000000000000000000001";

        try {
        	VerifyAuthResult result = kakaocertService.verifyAuth(ClientCode, receiptID);
            
            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "kakaocert/verifyAuth";
    }


    /*
     * 카카오톡 사용자에게 전자서명을 요청합니다.(단건)
     * - https://requestESign
     */
    @RequestMapping(value = "kakaocert/resultESign", method = RequestMethod.GET)
    public String requestESign(Model m) throws BarocertException {

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        boolean isAppUseYN = false;

        // 전자서명 요청 정보 Object
        RequestESign eSignRequest = new RequestESign();
        
        // 수신자 정보(휴대폰번호, 성명, 생년월일)와 Ci 값 중 택일
        eSignRequest.setReceiverHP(kakaocertService.AES256Encrypt("01087674117"));
        eSignRequest.setReceiverName(kakaocertService.AES256Encrypt("이승환"));
        eSignRequest.setReceiverBirthday(kakaocertService.AES256Encrypt("19930112"));
        // request.setCi(kakaocertService.AES256Encrypt(""));

        eSignRequest.setReqTitle("전자서명단건테스트");
        eSignRequest.setExpireIn(1000);
        eSignRequest.setToken(kakaocertService.AES256Encrypt("전자서명단건테스트데이터"));
        eSignRequest.setTokenType("TEXT"); // TEXT, HASH

        // App to App 방식 이용시, 에러시 호출할 URL
        // eSignRequest.setReturnURL("https://kakao.barocert.com");
        
        ResultESign result = null;

        try {
        	result = kakaocertService.requestESign(ClientCode, eSignRequest, isAppUseYN);

            m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/resultESign";
    }
    
    /*
     * 카카오톡 사용자에게 전자서명을 요청합니다.(다건)
     * - https://bulkRequestESign
     */
    @RequestMapping(value = "kakaocert/bulkResultESign", method = RequestMethod.GET)
    public String bulkRequestESign(Model m) throws BarocertException {

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        boolean isAppUseYN = false;

        // 전자서명 요청 정보 Object
        BulkRequestESign bulkESignRequest = new BulkRequestESign();
    	
        // 수신자 정보(휴대폰번호, 성명, 생년월일)와 Ci 값 중 택일
        bulkESignRequest.setReceiverHP(kakaocertService.AES256Encrypt("01087674117"));
        bulkESignRequest.setReceiverName(kakaocertService.AES256Encrypt("이승환"));
        bulkESignRequest.setReceiverBirthday(kakaocertService.AES256Encrypt("19930112"));
        // request.setCi(kakaocertService.AES256Encrypt(""));
    	
        bulkESignRequest.setReqTitle("전자서명다건테스트");
        bulkESignRequest.setExpireIn(1000);
    	
        bulkESignRequest.setTokens(new ArrayList<Tokens>());
    	
        Tokens token = new Tokens();
        token.setReqTitle("전자서명다건문서테스트1");
        token.setToken(kakaocertService.AES256Encrypt("전자서명다건테스트데이터1"));
        bulkESignRequest.getTokens().add(token);
    	
        token = new Tokens();
        token.setReqTitle("전자서명다건문서테스트2");
        token.setToken(kakaocertService.AES256Encrypt("전자서명다건테스트데이터2"));
        bulkESignRequest.getTokens().add(token);
    	
        bulkESignRequest.setTokenType("TEXT"); // TEXT, HASH
    	
        // App to App 방식 이용시, 에러시 호출할 URL
        // request.setReturnURL("https://kakao.barocert.com");
        
        ResultESign result = null;

        try {
        	result = kakaocertService.bulkRequestESign(ClientCode, bulkESignRequest, isAppUseYN);

        	m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/bulkResultESign";
    }

    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다. (단건)
     * - https://getESignResult
     */
    @RequestMapping(value = "kakaocert/getESignState", method = RequestMethod.GET)
    public String getESignResult(Model m) {

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230321223300000000000000000000000000001";
        
        ResultESignState result = null;

        try {
        	result = kakaocertService.getESignState(ClientCode, receiptID);
            
        	m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "kakaocert/getESignState";
    }
    
    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다. (다건)
     * - https://getBulkESignState
     */
    @RequestMapping(value = "kakaocert/getBulkESignState", method = RequestMethod.GET)
    public String getBulkESignState(Model m) {

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230321223457000000000000000000000000001";
        
        BulkResultESignState result = null;

        try {
        	
        	result = kakaocertService.getBulkESignState(ClientCode, receiptID);
            
        	m.addAttribute("result", result);
        	
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "kakaocert/getBulkESignState";
    }

    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명을 검증합니다. (단건)
     * - https://verfiyESign
     */
    @RequestMapping(value = "kakaocert/verifyESign", method = RequestMethod.GET)
    public String verfiyESign(Model m) {

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230321223300000000000000000000000000001";

        try {
        	
        	VerifyEsignResult result = kakaocertService.verifyESign(ClientCode, receiptID);
            
        	m.addAttribute("result", result);
        	
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "kakaocert/verifyESign";
    }
    
    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명을 검증합니다. (다건)
     * - https://bulkVerfiyESign
     */
    @RequestMapping(value = "kakaocert/bulkVerifyESign", method = RequestMethod.GET)
    public String bulkVerfiyESign(Model m) {

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230321223457000000000000000000000000001";

        try {
        	BulkVerifyESignResult result = kakaocertService.bulkVerifyESign(ClientCode, receiptID);
            
        	m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "kakaocert/bulkVerifyESign";
    }

    
    /*
     * 카카오톡 사용자에게 자동이체 출금동의 전자서명을 요청합니다.
     * - https://requestCMS
     */
    @RequestMapping(value = "kakaocert/requestCMS", method = RequestMethod.GET)
    public String requestCMS(Model m) throws BarocertException {

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        boolean isAppUseYN = false;

        RequestCMS cmsRequest = new RequestCMS();
    	
    	// 수신자 정보(휴대폰번호, 성명, 생년월일)와 Ci 값 중 택일
    	cmsRequest.setReceiverHP(kakaocertService.AES256Encrypt("01087674117"));
    	cmsRequest.setReceiverName(kakaocertService.AES256Encrypt("이승환"));
    	cmsRequest.setReceiverBirthday(kakaocertService.AES256Encrypt("19930112"));
    	// cmsRequest.setCi(kakaocertService.AES256Encrypt("");
    	
    	cmsRequest.setReqTitle("인증요청 메시지 제공란");
    	cmsRequest.setExpireIn(1000);
    	
    	cmsRequest.setRequestCorp(kakaocertService.AES256Encrypt("청구 기관명란"));
    	cmsRequest.setBankName(kakaocertService.AES256Encrypt("출금은행명란"));
    	cmsRequest.setBankAccountNum(kakaocertService.AES256Encrypt("9-4324-5117-58"));
    	cmsRequest.setBankAccountName(kakaocertService.AES256Encrypt("예금주명 입력란"));
    	cmsRequest.setBankAccountBirthday(kakaocertService.AES256Encrypt("19930112"));
    	cmsRequest.setBankServiceType(kakaocertService.AES256Encrypt("CMS")); // CMS, FIRM, GIRO
    	
    	// App to App 방식 이용시, 에러시 호출할 URL
    	// request.setReturnURL("https://kakao.barocert.com");

        try {
        	ResultCMS result = kakaocertService.requestCMS(ClientCode, cmsRequest, isAppUseYN);

        	m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "kakaocert/resultCMS";
    }

    /*
     * 자동이체 출금동의 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다.
     * - https://getCMSState
     */
    @RequestMapping(value = "kakaocert/getCMSState", method = RequestMethod.GET)
    public String getCMSState(Model m) {

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230321224506000000000000000000000000001";

        try {
        	ResultCMSState result = kakaocertService.getCMSState(ClientCode, receiptID);
            
        	m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "kakaocert/getCMSState";
    }

    /*
     * 자동이체 출금동의 요청시 반환된 접수아이디를 통해 서명을 검증합니다.
     * - https://verifyCMS
     */
    @RequestMapping(value = "kakaocert/verifyCMS", method = RequestMethod.GET)
    public String verifyCMS(Model m) {

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230321224506000000000000000000000000001";

        try {
        	VerifyCMSResult result = kakaocertService.verifyCMS(ClientCode, receiptID);
            
        	m.addAttribute("result", result);
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "kakaocert/verifyCMS";
    }

}
