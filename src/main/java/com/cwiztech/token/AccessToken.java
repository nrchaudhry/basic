package com.cwiztech.token;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class AccessToken {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);

	private static String applicationPath;
	private static String applicationPathUM;


	@Autowired
	public AccessToken(Environment env) {
		log.info("Trying to get Application Path..........");
		AccessToken.applicationPath = env.getRequiredProperty("file_path.applicationPath");
		AccessToken.applicationPathUM = env.getRequiredProperty("file_path.applicationPathUM");
		log.info("Application Path: "+AccessToken.applicationPath);
	}

	public static String findToken(String apllication_ID, String UserName, String Password)
			throws JsonProcessingException, JSONException, ParseException {

		JSONObject jsonObjmain = new JSONObject();
		jsonObjmain.put("user_NAME", UserName);
		jsonObjmain.put("password", Password);
		jsonObjmain.put("code", apllication_ID);

		log.info("Application Code for Token: " + apllication_ID);

		String rtnToken = null, appPath = null;

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entityformudel = new HttpEntity<String>(jsonObjmain.toString());
		ResponseEntity<String> application = restTemplate.exchange(applicationPathUM + "application/bycode", HttpMethod.POST,
				entityformudel, String.class);
		JSONArray applicationSetting = new JSONArray(application.getBody());
		if (applicationSetting.length() != 0) {

			HttpHeaders headers = new HttpHeaders();
			JSONObject detail = new JSONObject(applicationSetting.get(0).toString());
			headers.add("Content-type", detail.getString("content_TYPE"));
			headers.add("Authorization", detail.getString("authorization_CODE"));
			appPath = detail.getString("applicationservice_PATH");
			log.info(detail.toString());
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			rtnToken = restTemplate.postForObject(
					appPath + "oauth/token?grant_type=password&username=" + UserName + "&password=" + Password, entity,
					String.class);
			JSONObject jsonFinalRtnObject = new JSONObject(rtnToken);
			jsonFinalRtnObject.put("applicationservice_PATH", detail.getString("applicationservice_PATH"));
			if (!detail.isNull("applicationpath_FRONTEND"))
				jsonFinalRtnObject.put("applicationpath_FRONTEND", detail.getString("applicationpath_FRONTEND"));
			else
				jsonFinalRtnObject.put("applicationpath_FRONTEND", "");
			if (!detail.isNull("applicationlogo_PATH"))
				jsonFinalRtnObject.put("applicationlogo_PATH", detail.getString("applicationlogo_PATH"));
			else
				jsonFinalRtnObject.put("applicationlogo_PATH", "");
			if (!detail.isNull("oauthservice_PATH"))
				jsonFinalRtnObject.put("oauthservice_PATH", detail.getString("oauthservice_PATH"));
			else
				jsonFinalRtnObject.put("oauthservice_PATH", "");
			if (!detail.isNull("headername"))
				jsonFinalRtnObject.put("HeaderName", detail.getString("headername"));
			else
				jsonFinalRtnObject.put("HeaderName", "");
			if (!detail.isNull("projecttitle"))
				jsonFinalRtnObject.put("ProjectTitle", detail.getString("projecttitle"));
			else
				jsonFinalRtnObject.put("ProjectTitle", "");
			if (!detail.isNull("copyrights_YEAR"))
				jsonFinalRtnObject.put("CopyRights", detail.getString("copyrights_YEAR"));
			else
				jsonFinalRtnObject.put("CopyRights", "");
			if (!detail.isNull("companylink"))
				jsonFinalRtnObject.put("CompanyLink", detail.getString("companylink"));
			else
				jsonFinalRtnObject.put("CompanyLink", "");
			if (!detail.isNull("companyname"))
				jsonFinalRtnObject.put("CompanyName", detail.getString("companyname"));
			else
				jsonFinalRtnObject.put("CompanyName", "");

			rtnToken = jsonFinalRtnObject.toString();
		} else {
			jsonObjmain.put("message", "Application does't exist");
			rtnToken = jsonObjmain.toString();
		}

		log.info("Output: " + rtnToken);
		log.info("--------------------------------------------------------");

		return rtnToken;
	}

	public static String getTokenDetail(String accessToken) {
		log.info("----------------------------------------------------------------------------------");
		log.info("Get Toeken Detail By Token Service");
		log.info("----------------------------------------------------------------------------------");
		RestTemplate restTemplate = new RestTemplate();
		String UserDetail;
		
		String token = accessToken;
		String[] parts = token.split(" ");
		String OauthToken = parts[1];
		log.info(OauthToken);

		ResponseEntity<String> getToken = restTemplate.exchange(applicationPath + "oauth/check_token?token=" + OauthToken, HttpMethod.GET, null, String.class);
		JSONObject myobj = new JSONObject(getToken.getBody().toString());
		UserDetail = myobj.getString("user_name");
		log.info("----------------------------------------------------------------------------------");
		
		return UserDetail;
	}

	public static String findToken() throws JsonProcessingException, JSONException, ParseException {

		log.info("Process to get Token.......");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded");
		headers.add("Authorization", "Basic Y21pczpzZWNyZXQ=");

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		log.info("Application Path: "+applicationPath);
		String rtnToken = restTemplate.postForObject(
				applicationPath + "oauth/token?grant_type=password&username=development&password=development", entity,
				String.class);
		log.info("Output: " + rtnToken);
		log.info("--------------------------------------------------------");

		return rtnToken;
	}
}
