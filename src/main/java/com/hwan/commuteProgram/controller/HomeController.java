package com.hwan.commuteProgram.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hwan.commuteProgram.service.CommuteService;
import com.hwan.commuteProgram.service.CommuteVO;
import com.hwan.commuteProgram.service.MemberService;
import com.hwan.commuteProgram.service.impl.CommuteDAOImpl;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	private CommuteService commuteservice;
	
	@Autowired
	private MemberService memberservice;
	
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
			
		return "home";
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
	
	@RequestMapping("/main.do")
	public ModelAndView main(HttpSession session, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		System.out.println(getIp(request));
		System.out.println(session.getAttribute("ip"));
		if(session.getAttribute("role").equals("admin")) { 	//admin 인지 먼저 검색
			mav.addObject("result", 1);
			mav.addObject("getIp", 1);
		}else {												//admin이 아닌경우 접속 환경 ip 가져오기
			mav.addObject("result", 0);
			if(getIp(request).equals("218.152.111.83")) {
				mav.addObject("getIp",1);
			}else {
				mav.addObject("getIp",1);
			}
		}
		mav.setViewName("main");
		mav.addObject("msg", session.getAttribute("userName"));
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("inTime.do")
	public String commuteInTime(HttpSession session, CommuteVO vo, Model model, HttpServletRequest request) throws Exception {
		
		List<CommuteVO> dataList = commuteservice.commute(vo);
		
		for(int i=0;i<dataList.size();i++) {
			if(commuteservice.commute(vo).get(i).getDate().equals((String)request.getParameter("date")) && commuteservice.commute(vo).get(i).getUserName().equals((String)session.getAttribute("userName"))) {
				return "main";
			}
		}
		
		vo.setUserId((String)session.getAttribute("userId"));
		vo.setUserName((String)session.getAttribute("userName"));
		vo.setDate((String)request.getParameter("date"));
		vo.setInTime((String)request.getParameter("inTime"));

		commuteservice.inTime(vo);

		model.addAttribute("result","성공");
		
		return "main";
		
		
	}
	
	@ResponseBody	
	@RequestMapping("outTime.do")
	public String commuteOutTime(HttpSession session, CommuteVO vo, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String inTime = "";
		vo.setUserId((String)session.getAttribute("userId"));
		vo.setDate((String)request.getParameter("date"));
		vo.setOutTime((String)request.getParameter("outTime"));
		
		commuteservice.outTime(vo);
		
		String outTime = vo.getOutTime();
		
		List<CommuteVO> dataList = commuteservice.commute(vo);
		
		for(int i=0;i<dataList.size();i++) {
			if(commuteservice.commute(vo).get(i).getDate().equals((String)request.getParameter("date")) && commuteservice.commute(vo).get(i).getUserName().equals((String)session.getAttribute("userName"))) {
				inTime = commuteservice.commute(vo).get(i).getInTime();
			}
		}

		String[] arrayInTime = inTime.split("-");
		String[] arrayOutTime = outTime.split("-");
		
		int time =Integer.valueOf(arrayOutTime[0]) - Integer.valueOf(arrayInTime[0]);
		int minute =Integer.valueOf(arrayOutTime[1]) - Integer.valueOf(arrayInTime[1]);
		
		if(Integer.valueOf(arrayInTime[0]) < 12 && Integer.valueOf(arrayOutTime[0]) > 12){//12시 이전에 출근 체크하고 13시 이후에 퇴근체크하면 점심시간 1시간 빠짐
			time = time -1;
			if(minute < 0) {
				minute = minute + 60;
				time = time - 1;
			}
		}else {
			if(minute < 0) {
				minute = minute + 60;
				time = time - 1;
			}
		}
		
		String totalTime = String.valueOf(time) + "시간 " + String.valueOf(minute) + "분";
		
		vo.setTotalTime(totalTime);
		
		commuteservice.totalTime(vo);
	
		return "main";
	}	
	
	@RequestMapping("db_all.do")
	public String db_all(CommuteVO vo, Model model,HttpServletRequest request, HttpSession session) throws Exception {

		model.addAttribute("dataList",commuteservice.commute(vo));
		model.addAttribute("result", "뭐냐얜");
		model.addAttribute("userList",memberservice.totalMember());
		
		return "db_all";
	}
	
	@RequestMapping("db_modify.do")
	public String db_modify(CommuteVO vo, Model model,HttpServletRequest request, HttpSession session) throws Exception {

		model.addAttribute("dataList",commuteservice.commute(vo));
		model.addAttribute("result", "뭐냐얜");
		model.addAttribute("userList",memberservice.totalMember());
		
		return "db_modify";
	}
	
	@RequestMapping("db_my.do")
	public String db_my(CommuteVO vo, Model model,HttpServletRequest request, HttpSession session) throws Exception {
		vo.setUserId((String)session.getAttribute("userId"));
		model.addAttribute("dataList",commuteservice.commute(vo));
		model.addAttribute("result", "뭐냐얜");
		model.addAttribute("userList",memberservice.oneMember(vo));
		
		return "db_my";
	}
	
	@RequestMapping("db_update.do") //시간 수정 후 db업데이트
	public String db_update(@ModelAttribute("CommuteVO")CommuteVO vo, Model model,HttpServletRequest request, HttpSession session) throws Exception {
		
		
		return "db_all";
	}
}
