package com.ssafy.vue.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@RestController
public class HouseController {

	@RequestMapping("/house")
	public List<Object> getList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 파라미터 정리
		String LAWD_CD = request.getParameter("LAWD_CD");
		String DEAL_YMD = request.getParameter("DEAL_YMD");
		String serviceKey = request.getParameter("serviceKey");
		System.out.println(LAWD_CD);
		System.out.println(DEAL_YMD);
		System.out.println(serviceKey);

		// api로 데이터 가져오기
		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "="
				+ URLEncoder.encode(serviceKey, "UTF-8")); /* Service Key */
		urlBuilder.append(
				"&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "=" + URLEncoder.encode(LAWD_CD, "UTF-8")); /* 지역코드 */
		urlBuilder.append(
				"&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "=" + URLEncoder.encode(DEAL_YMD, "UTF-8")); /* 계약월 */
		urlBuilder.append(
				"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("40", "UTF-8")); /* 계약월 */
		
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		line = sb.toString();
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

		// 파싱하기
		JSONArray jArray = new JSONArray();
		List<Map<String, Object>> list = new ArrayList<>();
		if (sb.toString() != null && line.trim().length() != 0) {
			System.out.println("in the if sentence");
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(new ByteArrayInputStream(line.getBytes()));
				document.getDocumentElement().normalize();
				System.out.println("Root Element :" + document.getDocumentElement().getNodeName());
				NodeList nList = document.getElementsByTagName("item");
				System.out.println("----------------------------");
				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);
					System.out.println("\nCurrent Element :" + nNode.getNodeName());
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						JSONObject json = new JSONObject();
						NodeList childList = eElement.getChildNodes();
						for (int j = 0; j < childList.getLength(); j++) {
							Node childNode = childList.item(j);
							String tagName = childNode.getNodeName();
							String tagItem = childNode.getTextContent();
							json.put(tagName, tagItem.trim());
						}
						jArray.put(json);
						System.out.println(jArray.toString());
					}
				}
			} catch (Exception e) {
				System.out.println("xml 파싱 실패");
				System.out.println(e.getMessage());
				e.printStackTrace(); // 예외가 발생한 지점부터 역추적하는 코드
			}
		} else {
			System.out.println("다운로드 받은 데이터 없음");
		}
		if (jArray.isEmpty()) {
			jArray.put(new JSONObject().put("isEmpty", "true"));
		}
		System.out.println(jArray.toString());
		return jArray.toList();
	}
}
