package com.ssafy.vue.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vue.dto.MemberVO;
import com.ssafy.vue.service.MemberService;
import com.ssafy.vue.service.OAuthService;
import com.ssafy.vue.util.JwtTokenProvider;

import io.jsonwebtoken.Claims;

@RestController
public class MemberController {
   @Autowired
   MemberService memberService;
   @Autowired
   OAuthService oAuthService;

   @GetMapping("oauth")
    public String kakaoCallback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
       System.out.println(code);
       String access_Token=oAuthService.getKaKaoAccessToken(code);
       String email=oAuthService.createKakaoUser(access_Token);
       
       if(email!=null) {
          //db...
          HttpSession session=request.getSession();
          //email=JwtTokenProvider.createToken(email);
          System.out.println(email);
          session.setAttribute("email", email);
          return email;
       }
       else {
          return "fail";
       }     
       
    }
   @PostMapping("user")
   public String getUser(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
      
      String jwtToken = (String)request.getHeader("Authorization");
      System.out.println(jwtToken);
      MemberVO vo = null;
      if(jwtToken != null && JwtTokenProvider.validateToken(jwtToken)){
         Claims claims= JwtTokenProvider.getInformation(jwtToken);
         String id = (String)claims.get("userId");
         System.out.println(id);
         // MemberVO vo2 = (MemberVO) session.getAttribute("loginUser");
         // System.out.println(vo2.getId());
         if(id!=null) {
            vo = memberService.getUser(id);
            if(vo != null) {
               JSONObject json = new JSONObject();
               json.put("id", vo.getId());
               json.put("email", vo.getEmail());
               json.put("name", vo.getName());
               return json.toString();
            }
            else {
               return "Fail";
            }
         } else {
            return "JWT Fail";
         }
      } 
      return "JWT Fail";      
   }
   @PostMapping("memberUpdate")
   public String memberUpdate(@RequestBody String filterJSON, HttpSession session) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         MemberVO vo = objectMapper.readValue(filterJSON, MemberVO.class);
         memberService.memberUpdate(vo);
         session.invalidate();
         return "ok";
      } catch (Exception e) {
         e.printStackTrace();
         return "error";
      }

   }

   @PostMapping("memberDelete")
   public String memberDelete(@RequestBody String filterJSON, HttpSession session) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         MemberVO vo = objectMapper.readValue(filterJSON, MemberVO.class);
         String id = vo.getId();
         memberService.memberDelete(id);
         session.invalidate();
         return "ok";
      } catch (Exception e) {
         e.printStackTrace();
         return "error";
      }

   }

   @PostMapping("check")
   public String idCheck(@RequestBody String filterJSON) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
         MemberVO vo = objectMapper.readValue(filterJSON, MemberVO.class);
         String name = memberService.idcheck(vo.getId());
         if (name == null) {
            return "ok";
         } else {
            return "fail";
         }
      } catch (JsonMappingException e) {
         e.printStackTrace();
         return "error";
      } catch (JsonProcessingException e) {
         e.printStackTrace();
         return "error";
      }
   }

   @PostMapping("signup")
   public String memberInsert(@RequestBody String filterJSON) {
      ObjectMapper objectMapper = new ObjectMapper();      
      try {
         MemberVO vo = objectMapper.readValue(filterJSON, MemberVO.class);
         memberService.memberInsert(vo);         
         return "ok";
      } catch (JsonMappingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (JsonProcessingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return "fail";
   }

   @PostMapping("login")
   public String login(@RequestBody String filterJSON, HttpSession session, HttpServletRequest request,
         HttpServletResponse response) {
      System.out.println(filterJSON);
      ObjectMapper objectMapper = new ObjectMapper();
      String name = null;
//      MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
      MemberVO vo2 = null;
//      if (loginUser != null) {
//         loginUser.setPw("");
//         try {
//            String value = objectMapper.writeValueAsString(loginUser);
//            return value;
//         } catch (JsonProcessingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return "fail2";
//         }
//      }
      try {
         MemberVO vo = objectMapper.readValue(filterJSON, MemberVO.class);
         // vo 값이 제대로 전달되지 않았을 때
         if(vo == null) {
            return "Fail";
         }
         vo2 = memberService.login(vo);
         if (vo2 != null) {
            name = vo2.getName();
         }
      } catch (JsonMappingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (JsonProcessingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      if (name == null) {
         return "Login Fail";
      } else {
         String jwtToken = JwtTokenProvider.createToken(vo2.getId(), vo2.getEmail(), vo2.getName(), "normal");
         session.setAttribute("loginUser", vo2);
         JSONObject json = new JSONObject();
         json.put("JSESSIONID", session.getId());
         json.put("JWT", jwtToken);
         return json.toString();
      }
   }
   @PostMapping("find")
   public String findPW(@RequestBody String filterJSON) {
      ObjectMapper objectMapper = new ObjectMapper();
      String pw = null;
      try {
         MemberVO vo = objectMapper.readValue(filterJSON, MemberVO.class);
         pw = memberService.getPW(vo);
      } catch (Exception e) {
         e.printStackTrace();
      }
      if(pw==null) {
         return "fail";
      }
      else {
         return pw;
      }   
         
   }
   @PostMapping("logout")
   public String logout(HttpServletRequest request, HttpServletResponse response) {
      HttpSession session = request.getSession(false);
      System.out.println(session);
      session.invalidate();
      return "ok";
   }
}