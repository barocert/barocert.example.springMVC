package com.barocert.example;

import java.lang.reflect.Field;
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
import com.barocert.kakaocert.cms.ResponseCMS;
import com.barocert.kakaocert.cms.ResultCMS;
import com.barocert.kakaocert.cms.ResultVerifyCMS;
import com.barocert.kakaocert.esign.BulkRequestESign;
import com.barocert.kakaocert.esign.BulkResultESign;
import com.barocert.kakaocert.esign.BulkVerifyResult;
import com.barocert.kakaocert.esign.RequestESign;
import com.barocert.kakaocert.esign.ResponseESign;
import com.barocert.kakaocert.esign.ResultESign;
import com.barocert.kakaocert.esign.ResultVerifyEsign;
import com.barocert.kakaocert.esign.Tokens;
import com.barocert.kakaocert.verifyauth.RequestVerifyAuth;
import com.barocert.kakaocert.verifyauth.ResultReqVerifyAuth;
import com.barocert.kakaocert.verifyauth.ResultVerifyAuth;
import com.barocert.kakaocert.verifyauth.ResultVerifyAuthState;

@Controller
public class KakaocertServiceExample {

    @Autowired
    private KakaocertService kakaocertService;

    // 이용기관코드
    // 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
    @Value("#{EXAMPLE_CONFIG.ClientCode}")
    private String ClientCode;

    @RequestMapping(value = "CheckKakaoServiceAttribute")
    public String CheckKakaoServiceAttribute(Model m) {
        kakaoCert2ModelAttribute(m);
        return "CheckKakaoServiceAttribute";
    }

    private void kakaoCert2ModelAttribute(Model m) {
        Field[] fields = kakaocertService.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                m.addAttribute(field.getName(), field.get(kakaocertService));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 카카오톡 사용자에게 전자서명을 요청합니다.(단건)
     * - https://requestESign
     */
    @RequestMapping(value = "requestESign", method = RequestMethod.GET)
    public String requestESign(Model m) {

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        boolean isAppUseYN = false;

        // 전자서명 요청 정보 Object
        RequestESign eSignRequest = new RequestESign();
        
        // 수신자 정보(휴대폰번호, 성명, 생년월일)와 Ci 값 중 택일
        eSignRequest.setReceiverHP("01087674117");
        eSignRequest.setReceiverName("이승환");
        eSignRequest.setReceiverBirthday("19930112");
        // request.setCi("");

        eSignRequest.setReqTitle("전자서명단건테스트");
        eSignRequest.setExpireIn(1000);
        eSignRequest.setToken("전자서명단건테스트데이터");
        eSignRequest.setTokenType("TEXT"); // TEXT, HASH

        // App to App 방식 이용시, 에러시 호출할 URL
        eSignRequest.setReturnURL("https://kakao.barocert.com");
        
        ResponseESign result = null;

        try {

        	result = kakaocertService.requestESign(ClientCode, eSignRequest, isAppUseYN);

            m.addAttribute("result", result);

        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "resultESign";
    }
    
    /*
     * 카카오톡 사용자에게 전자서명을 요청합니다.(다건)
     * - 
     */
    @RequestMapping(value = "bulkRequestESign", method = RequestMethod.GET)
    public String bulkRequestESign(Model m) {

    	// 이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        boolean isAppUseYN = false;

        // 전자서명 요청 정보 Object
        BulkRequestESign bulkESignRequest = new BulkRequestESign();
    	
        // 수신자 정보(휴대폰번호, 성명, 생년월일)와 Ci 값 중 택일
        bulkESignRequest.setReceiverHP("01087674117");
        bulkESignRequest.setReceiverName("이승환");
        bulkESignRequest.setReceiverBirthday("19930112");
        // request.setCi("");
    	
        bulkESignRequest.setReqTitle("전자서명다건테스트");
        bulkESignRequest.setExpireIn(1000);
    	
        bulkESignRequest.setTokens(new ArrayList<Tokens>());
    	
        Tokens token = new Tokens();
        token.setReqTitle("전자서명다건문서테스트1");
        token.setToken("전자서명다건테스트데이터1");
        bulkESignRequest.getTokens().add(token);
    	
        token = new Tokens();
        token.setReqTitle("전자서명다건문서테스트2");
        token.setToken("전자서명다건테스트데이터2");
        bulkESignRequest.getTokens().add(token);
    	
        bulkESignRequest.setTokenType("TEXT"); // TEXT, HASH
    	
        // App to App 방식 이용시, 에러시 호출할 URL
        // request.setReturnURL("https://kakao.barocert.com");
        
        ResponseESign result = null;

        try {

        	result = kakaocertService.bulkRequestESign(clientCode, bulkESignRequest, isAppUseYN);

        	m.addAttribute("result", result);

        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "bulkResultESign";
    }

    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다. (단건)
     * - 
     */
    @RequestMapping(value = "getESignState", method = RequestMethod.GET)
    public String getESignResult(Model m) {

    	// 이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230310143306000000000000000000000000001";
        
        ResultESign result = null;

        try {
        	
        	result = kakaocertService.getESignState(clientCode, receiptID);
            
        	m.addAttribute("result", result);
        	
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "getESignState";
    }
    
    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다. (다건)
     * - 
     */
    @RequestMapping(value = "getBulkESignState", method = RequestMethod.GET)
    public String getBulkESignState(Model m) {

    	// 이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230310143306000000000000000000000000001";
        
        BulkResultESign result = null;

        try {
        	
        	result = kakaocertService.getBulkESignState(clientCode, receiptID);
            
        	m.addAttribute("result", result);
        	
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "getBulkESignState";
    }

    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명을 검증합니다. (단건)
     * - 
     */
    @RequestMapping(value = "verifyESign", method = RequestMethod.GET)
    public String verfiyESign(Model m) {

    	// 이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230310143306000000000000000000000000001";

        try {
        	
        	ResultVerifyEsign result = kakaocertService.verifyESign(clientCode, receiptID);
            
        	m.addAttribute("result", result);
        	
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "verifyESign";
    }
    
    /*
     * 전자서명 요청시 반환된 접수아이디를 통해 서명을 검증합니다. (다건)
     * - 
     */
    @RequestMapping(value = "bulkVerifyESign", method = RequestMethod.GET)
    public String bulkVerfiyESign(Model m) {

    	// 이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230310143951000000000000000000000000001";

        try {
        	
        	BulkVerifyResult result = kakaocertService.bulkVerifyESign(clientCode, receiptID);
            
        	m.addAttribute("result", result);
        	
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "bulkVerifyESign";
    }

    /*
     * 카카오톡 사용자에게 본인인증 전자서명을 요청합니다.
     * - 
     */
    @RequestMapping(value = "requestVerifyAuth", method = RequestMethod.GET)
    public String requestVerifyAuth(Model m) {

    	//이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        boolean isAppUseYN = false;

        RequestVerifyAuth verifyAuthRequest = new RequestVerifyAuth();

        // 수신자 정보(휴대폰번호, 성명, 생년월일)와 Ci 값 중 택일
        verifyAuthRequest.setReceiverHP("01087674117");
        verifyAuthRequest.setReceiverName("이승환");
        verifyAuthRequest.setReceiverBirthday("19930112");
        // request.setCi("");

        verifyAuthRequest.setReqTitle("인증요청 메시지 제목란");
        verifyAuthRequest.setExpireIn(1000);
        verifyAuthRequest.setToken("본인인증요청토큰");

        // App to App 방식 이용시, 에러시 호출할 URL
        // request.setReturnURL("https://kakao.barocert.com");

        try {

        	ResultReqVerifyAuth result = kakaocertService.requestVerifyAuth(clientCode, verifyAuthRequest, isAppUseYN);

            m.addAttribute("result", result);

        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "requestVerifyAuth";
    }

    /*
     * 본인인증 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다.
     * - 
     */
    @RequestMapping(value = "getVerifyAuthState", method = RequestMethod.GET)
    public String getVerifyAuthState(Model m) {

    	//이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        //전자서명 요청시 반환된 접수아이디
        String receiptID = "0230309201738000000000000000000000000001";

        try {
        	
        	ResultVerifyAuthState result = kakaocertService.getVerifyAuthState(clientCode, receiptID);
            
        	m.addAttribute("result", result);
        	
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "getVerifyAuthState";
    }

    /*
     * 본인인증 요청시 반환된 접수아이디를 통해 본인인증 서명을 검증합니다.
     * - 
     */
    @RequestMapping(value = "verifyAuth", method = RequestMethod.GET)
    public String verifyAuth(Model m) {

    	//이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        //전자서명 요청시 반환된 접수아이디
        String receiptID = "0230309201738000000000000000000000000001";

        try {
        	
        	ResultVerifyAuth result = kakaocertService.verifyAuth(clientCode, receiptID);
            
            m.addAttribute("result", result);
            
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "verifyAuth";
    }

    /*
     * 카카오톡 사용자에게 자동이체 출금동의 전자서명을 요청합니다.
     * - 
     */
    @RequestMapping(value = "requestCMS", method = RequestMethod.GET)
    public String requestCMS(Model m) {

    	//이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        // AppToApp 인증요청 여부
        // true - AppToApp 인증방식, false - Talk Message 인증방식
        boolean isAppUseYN = false;

        RequestCMS cmsRequest = new RequestCMS();
    	
    	// 수신자 정보(휴대폰번호, 성명, 생년월일)와 Ci 값 중 택일
    	cmsRequest.setReceiverHP("01087674117");
    	cmsRequest.setReceiverName("이승환");
    	cmsRequest.setReceiverBirthday("19930112");
    	// cmsRequest.setCi("");
    	
    	cmsRequest.setReqTitle("인증요청 메시지 제공란");
    	cmsRequest.setExpireIn(1000);
    	cmsRequest.setRequestCorp("청구 기관명란");
    	cmsRequest.setBankName("출금은행명란");
    	cmsRequest.setBankAccountNum("9-4324-5117-58");
    	cmsRequest.setBankAccountName("예금주명 입력란");
    	cmsRequest.setBankAccountBirthday("19930112");
    	cmsRequest.setBankServiceType("CMS"); // CMS, FIRM, GIRO
    	
    	// App to App 방식 이용시, 에러시 호출할 URL
    	// request.setReturnURL("https://kakao.barocert.com");

        try {

        	ResponseCMS result = kakaocertService.requestCMS(clientCode, cmsRequest, isAppUseYN);

        	m.addAttribute("result", result);

        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }

        return "resultCMS";
    }

    /*
     * 자동이체 출금동의 요청시 반환된 접수아이디를 통해 서명 상태를 확인합니다.
     * - 
     */
    @RequestMapping(value = "getCMSState", method = RequestMethod.GET)
    public String getCMSState(Model m) {

    	// 이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        // 전자서명 요청시 반환된 접수아이디
        String receiptID = "0230309201738000000000000000000000000001";

        try {
        	
        	ResultCMS result = kakaocertService.getCMSState(clientCode, receiptID);
            
        	m.addAttribute("result", result);
        	
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "getCMSState";
    }

    /*
     * 자동이체 출금동의 요청시 반환된 접수아이디를 통해 서명을 검증합니다.
     * - 
     */
    @RequestMapping(value = "verifyCMS", method = RequestMethod.GET)
    public String verifyCMS(Model m) {

    	//이용기관코드, 파트너가 등록한 이용기관의 코드, (파트너 사이트에서 확인가능)
        String clientCode = "023020000003";

        //전자서명 요청시 반환된 접수아이디
        String receiptID = "0230309201738000000000000000000000000001";

        try {
        	
        	ResultVerifyCMS result = kakaocertService.verifyCMS(clientCode, receiptID);
            
        	m.addAttribute("result", result);
        	
        } catch (BarocertException ke) {
            m.addAttribute("Exception", ke);
            return "exception";
        }
        
        return "verifyCMS";
    }

}
