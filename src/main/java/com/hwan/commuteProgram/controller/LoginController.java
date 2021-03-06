package com.hwan.commuteProgram.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hwan.commuteProgram.service.MemberDAO;
import com.hwan.commuteProgram.service.MemberService;
import com.hwan.commuteProgram.service.MemberVO;



@Controller
@RequestMapping("/member/*")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Inject
	MemberService memberService;
	
	
	/**
     * SHA-256으로 해싱하는 메소드
     * 
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public static String sha256(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        
        return bytesToHex(md.digest());
    }
 
 
    /**
     * 바이트를 헥스값으로 변환한다
     * 
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
          builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

	
	
	
	@RequestMapping("login.do")
	public String login() {
		return "member/login";
	}
	@RequestMapping("add.do")
	public String add() {
		return "member/add";
	}
	
	private String getIp(HttpServletRequest request) {
		 
        String ip = request.getHeader("X-Forwarded-For");
 
        logger.info(">>>> X-FORWARDED-FOR : " + ip);
 
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            logger.info(">>>> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
            logger.info(">>>> WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            logger.info(">>>> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            logger.info(">>>> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        
        logger.info(">>>> Result : IP Address : "+ip);
 
        return ip;
 
    }
	
	
	@ResponseBody
	@RequestMapping("addUser.do")
	public ModelAndView addUser(@ModelAttribute MemberVO vo, HttpSession session, HttpServletRequest request) throws NoSuchAlgorithmException {
		ModelAndView mav = new ModelAndView();
		
		System.out.println(getIp(request));
		
		
		vo.setUserName((String)request.getParameter("userName"));
		vo.setUserId((String)request.getParameter("userId"));
		vo.setUserPw(sha256((String)request.getParameter("userPw")));
		vo.setIp((String)getIp(request));
		memberService.addMember(vo);
		
		mav.setViewName("member/login");
		System.out.println("여기됨");
		return mav;
	}
	
	@RequestMapping("loginCheck.do")
	public ModelAndView loginCheck(@ModelAttribute MemberVO vo, HttpSession session, HttpServletRequest request) throws NoSuchAlgorithmException {
		vo.setUserPw(sha256((String)request.getParameter("userPw")));
		
		boolean result = memberService.loginCheck(vo, session);
		ModelAndView mav = new ModelAndView();
		if(result == true) {
			mav.setViewName("welcome");
			mav.addObject("msg","success");
		}else {
			mav.setViewName("member/login");
			mav.addObject("msg","failure");
		}
		return mav;
	}
	
	@RequestMapping("logout.do")
	public ModelAndView logout(HttpSession session) {
		memberService.logout(session);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/login");
		mav.addObject("msg", "logout");
		session.setAttribute("userId", null);
		return mav;
	}
}