//package com.spring.nesl_api;
//import com.spring.nesl_api.model.AuditMessaging;
//import com.spring.nesl_api.model.Content;
//import com.spring.nesl_api.repository.AuditMessageRepository;
//import com.spring.nesl_api.service.NeslService;
//import net.minidev.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//class NeslApiApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	NeslService neslService;
//
//	@Autowired
//	AuditMessageRepository auditMessageRepository;
//
//	public AuditMessaging  getAuditMessagingData(){
//		AuditMessaging auditMessaging = new AuditMessaging();
//		auditMessaging.setEmail("govind.ramavath@gmail.com");
//		auditMessaging.setActionName("input123");
//		auditMessaging.setControllerName("ConttrollerName");
//		auditMessaging.setSenderName("Application Name");
//		auditMessaging.setClientId("9cda849e-3e97-404f-9ac2-6d29edfba091");
//		auditMessaging.setCompanyId("b23af6c3-9919-4400-8412-2eaeffe18adf");
//		Content content = new Content();
//		content.setKey1("value1");
//		content.setKey2("value2");
//		auditMessaging.setContent(content);
//		return auditMessaging;
//	}
//
//	@Test
//	public void testGetNeslController() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/nesl/get-nesl-api")
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().string(""));
//	}
//
////	@Test
////	public void testGetNeslService(){
////		String expectedResponse = null;
////		assertEquals(expectedResponse, neslService.getNeslApi());
////	}
//
//	@Test
//	public void testAuditMessagingController() throws Exception {
//		JSONObject requestBodyJson = new JSONObject();
//		requestBodyJson.put("email", "govind.ramavath@gmail.com");
//		requestBodyJson.put("ActionName", "Input");
//		requestBodyJson.put("ControllerName", "ConttrollerName");
//		requestBodyJson.put("SenderName", "Application Name");
//		requestBodyJson.put("ClientId", "9cda849e-3e97-404f-9ac2-6d29edfba091");
//		requestBodyJson.put("CompanyId", "b23af6c3-9919-4400-8412-2eaeffe18adf");
//		JSONObject contentJson = new JSONObject();
//		contentJson.put("key1", "some value");
//		contentJson.put("key2", "some value2");
//		requestBodyJson.put("Content", contentJson);
//		String jsonString = requestBodyJson.toString();
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/nesl/post-audit-messaging")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(jsonString))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().string(""));
//	}
//
//	@Test
//	public void testAuditMessagingService(){
//		JSONObject requestBodyJson = new JSONObject();
//		requestBodyJson.put("email", "govind.ramavath@gmail.com");
//		requestBodyJson.put("ActionName", "Input1");
//		requestBodyJson.put("ControllerName", "ConttrollerName");
//		requestBodyJson.put("SenderName", "Application Name");
//		requestBodyJson.put("ClientId", "9cda849e-3e97-404f-9ac2-6d29edfba091");
//		requestBodyJson.put("CompanyId", "b23af6c3-9919-4400-8412-2eaeffe18adf");
//		JSONObject contentJson = new JSONObject();
//		contentJson.put("key1", "some value");
//		contentJson.put("key2", "some value2");
//		requestBodyJson.put("Content", contentJson);
//		int expectedContentLengthRes = 0;
//		String actualResponse = neslService.postAuditMessaging(requestBodyJson).toString();
//		int contentLengthIndex = actualResponse.indexOf("Content-Length:");
//		int endOfContentLengthIndex = actualResponse.indexOf(",", contentLengthIndex);
//		String contentLengthString = actualResponse.substring(contentLengthIndex + 15, endOfContentLengthIndex);
//		int contentLength = Integer.parseInt(contentLengthString.replaceAll("\"", ""));
//
//		assertEquals(expectedContentLengthRes, contentLength);
//	}
//
//	@Test
//	public void testAuditMessagingRepository() {
//		auditMessageRepository.save(getAuditMessagingData());
//	}
//
//}
