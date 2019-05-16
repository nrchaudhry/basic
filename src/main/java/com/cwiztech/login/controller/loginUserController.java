package com.cwiztech.login.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.login.model.LoginUser;
import com.cwiztech.login.repository.loginUserRepository;
import com.cwiztech.systemsetting.repository.lookupRepository;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/loginuser")
public class loginUserController {
	private static final Logger log = LoggerFactory.getLogger(loginUserController.class);

	private String applicationPath;

	@Autowired
	public loginUserController(Environment env) {
		this.applicationPath = env.getRequiredProperty("file_path.applicationPath");
	}

	@Autowired
	private loginUserRepository loginuserrepository;

	@Autowired
	private lookupRepository lookuprepository;

	@Autowired
	private loginUserRepository userrepository;

	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;

	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databaetablesrepository;

	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	@Autowired
	private ConsumerTokenServices consumerTokenServices;

	@RequestMapping("/logout")
	public String logout(Principal principal) throws IOException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("message", "Logout Successfully");

		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
		OAuth2AccessToken accessToken = authorizationServerTokenServices.getAccessToken(oAuth2Authentication);
		consumerTokenServices.revokeToken(accessToken.getValue());
		return jsonObj.toString();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;

		log.info("GET: /loginuser");

		List<LoginUser> loginuser = loginuserrepository.findLoginUserActive();
		String rtnLoginUserCategory, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, 0, "/loginuser", null,
				requestUser, workstation);

		rtnLoginUserCategory = mapper.writeValueAsString(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtnLoginUserCategory);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUserCategory);
		log.info("--------------------------------------------------------");

		return rtnLoginUserCategory;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getLoginUserById(@PathVariable Long id) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;

		log.info("GET: /loginuser/" + id);

		LoginUser loginuser = loginuserrepository.findOne(id);
		String rtn, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, 0, "/loginuser/" + id,
				null, requestUser, workstation);

		rtn = mapper.writeValueAsString(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value = "/byusername/{name}", method = RequestMethod.GET)
	public String getActiveLoginUserList(@PathVariable String name) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;

		log.info("GET: /loginuser/byusername/" + name);

		LoginUser loginuser = loginuserrepository.findByLoginUserActive(name);
		String rtnLoginUserCategory, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, 0,
				"/loginuser/byusername/" + name, null, requestUser, workstation);

		rtnLoginUserCategory = mapper.writeValueAsString(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtnLoginUserCategory);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUserCategory);
		log.info("--------------------------------------------------------");

		return rtnLoginUserCategory;
	}

	@RequestMapping(value = "/byusername/{name}/all", method = RequestMethod.GET)
	public String getAllLoginUserList(@PathVariable String name) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;

		log.info("GET: /loginuser/byusername/" + name + "/all");

		LoginUser loginuser = loginuserrepository.findByLoginUserActive(name);
		String rtnLoginUserCategory, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, 0,
				"/loginuser/byusername/" + name + "/all", null, requestUser, workstation);

		rtnLoginUserCategory = mapper.writeValueAsString(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtnLoginUserCategory);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUserCategory);
		log.info("--------------------------------------------------------");

		return rtnLoginUserCategory;
	}

	
	// @RequestMapping(value = "/byemail", method = RequestMethod.GET)
	// public String getLoginUserByStudentAndEmployee(@RequestBody String data )
	// throws JsonProcessingException {
	// ObjectMapper mapper = new ObjectMapper();
	// LoginUser requestUser;
	// JSONObject jsonObj = new JSONObject(data);
	//
	// log.info("GET: /loginuser/byemail");
	//
	// String rtn, workstation = null;
	//
	// DatabaseTables databaseTableID =
	// databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
	// requestUser = userrepository.findOne((long) 0);
	// APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET",
	// databaseTableID, 0, "/loginuser/byemail",
	// null, requestUser, workstation);
	//
	// StudentContact studentcontact =
	// studencontactrepository.findByEmail(jsonObj.getString("email"));
	// EmployeeContact employeecontact =
	// employeecontactrepository.findByEmail(jsonObj.getString("email"));
	// rtn = mapper.writeValueAsString();
	//
	// apiRequest.setREQUEST_OUTPUT(rtn);
	// apiRequest.setREQUEST_STATUS("Success");
	// apirequestdatalogRepository.saveAndFlush(apiRequest);
	//
	// log.info("Output: " + rtn);
	// log.info("--------------------------------------------------------");
	//
	// return rtn;
	// }

	@RequestMapping(method = RequestMethod.POST)
	public String createLoginUser(@RequestBody String data)
			throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;

		log.info("POST: /loginuser");
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		LoginUser loginuser = new LoginUser();
		String rtnLoginUser, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		if (jsonObj.has("user"))
			requestUser = userrepository.getUser(jsonObj.getString("user"));
		else
			requestUser = userrepository.findOne((long) 0);
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0, "/loginuser", data,
				requestUser, workstation);

		if (!jsonObj.has("user_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Login User", "User_NAME is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		loginuser.setUSER_NAME(jsonObj.getString("user_NAME"));

		if (!jsonObj.has("password")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Login User", "password is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		loginuser.setPASSWORD(jsonObj.getString("password"));

		if (jsonObj.has("last_LOGING") && !jsonObj.isNull("last_LOGING"))
			loginuser.setLAST_LOGIN(jsonObj.getString("last_LOGING"));

		if (jsonObj.has("user_STATUS") && !jsonObj.isNull("user_STATUS"))
			loginuser.setUSER_STATUS(lookuprepository.findOne(jsonObj.getLong("user_STATUS")));

		loginuser.setISACTIVE("Y");
		loginuser.setMODIFIED_BY(requestUser);
		loginuser.setMODIFIED_WORKSTATION(workstation);
		loginuser.setMODIFIED_WHEN(dateFormat1.format(date));
		loginuser = loginuserrepository.saveAndFlush(loginuser);
		rtnLoginUser = mapper.writeValueAsString(loginuser);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(loginuser.getUSER_ID(), databaseTableID, requestUser, rtnLoginUser));

		apiRequest.setREQUEST_ID(loginuser.getUSER_ID());
		apiRequest.setREQUEST_OUTPUT(rtnLoginUser);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUser);
		log.info("--------------------------------------------------------");

		return rtnLoginUser;

	}

	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public String createEmployeeLoginUser(@RequestBody String data)
			throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();
		ObjectMapper mapper = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = token();
		LoginUser requestUser;

		log.info("POST: /loginuser/adduser");
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		LoginUser loginuser = new LoginUser();
		String rtnLoginUser, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		if (jsonObj.has("user"))
			requestUser = userrepository.getUser(jsonObj.getString("user"));
		else
			requestUser = userrepository.findOne((long) 0);
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0, "/loginuser/adduser",
				data, requestUser, workstation);

		if (!jsonObj.has("user_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Login User", "User_NAME is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}

		if (!jsonObj.has("id")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Login User", "User_NAME is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}

		if (jsonObj.isNull("id")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Login User", "User_NAME is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}

		LoginUser getuser = loginuserrepository.getUser(jsonObj.getString("user_NAME"));

		if (getuser != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Login User", "User NAME already exist!");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		loginuser.setUSER_NAME(jsonObj.getString("user_NAME"));

		if (!jsonObj.has("password")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Login User", "password is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		loginuser.setPASSWORD(jsonObj.getString("password"));

		loginuser.setISACTIVE("Y");
		loginuser.setMODIFIED_BY(requestUser);
		loginuser.setMODIFIED_WORKSTATION(workstation);
		loginuser.setMODIFIED_WHEN(dateFormat1.format(date));
		loginuser = loginuserrepository.saveAndFlush(loginuser);
		rtnLoginUser = mapper.writeValueAsString(loginuser);

		if (jsonObj.getLong("user_TYPE") == 1) {
			long userid = loginuser.getUSER_ID();
			jsonObj.put("user_ID", userid);
			HttpEntity<String> entity = new HttpEntity<String>(jsonObj.toString(), headers);
			ResponseEntity<String> response = restTemplate.exchange(
					applicationPath + "employee/" + jsonObj.getLong("id"), HttpMethod.PUT, entity, String.class);
			rtnLoginUser = response.getBody().toString();
		} else if (jsonObj.getLong("user_TYPE") == 2) {
			long userid = loginuser.getUSER_ID();
			jsonObj.put("user_ID", userid);
			HttpEntity<String> entity = new HttpEntity<String>(jsonObj.toString(), headers);
			ResponseEntity<String> response = restTemplate.exchange(
					applicationPath + "studentinstance/" + jsonObj.getLong("id"), HttpMethod.PUT, entity, String.class);
			rtnLoginUser = response.getBody().toString();
		}

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(loginuser.getUSER_ID(), databaseTableID, requestUser, rtnLoginUser));

		apiRequest.setREQUEST_ID(loginuser.getUSER_ID());
		apiRequest.setREQUEST_OUTPUT(rtnLoginUser);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUser);
		log.info("--------------------------------------------------------");

		return rtnLoginUser;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String updateLoginUser(@PathVariable Long id, @RequestBody String data)
			throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;

		log.info("PUT: /loginuser/" + id);
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		LoginUser loginuser = loginuserrepository.findOne(id);
		String rtnLoginUserCategory, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		if (jsonObj.has("user"))
			requestUser = userrepository.getUser(jsonObj.getString("user"));
		else
			requestUser = userrepository.findOne((long) 0);
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, 0, "/loginuser", data,
				requestUser, workstation);

		if (jsonObj.has("user_NAME") && !jsonObj.isNull("user_NAME"))
			loginuser.setUSER_NAME(jsonObj.getString("user_NAME"));

		if (jsonObj.has("password") && !jsonObj.isNull("password"))
			loginuser.setPASSWORD(jsonObj.getString("password"));

		if (jsonObj.has("last_LOGING"))
			loginuser.setLAST_LOGIN(jsonObj.getString("last_LOGING"));

		if (jsonObj.has("user_STATUS"))
			loginuser.setUSER_STATUS(lookuprepository.findOne(jsonObj.getLong("user_STATUS")));

		if (jsonObj.has("isactive"))
			loginuser.setISACTIVE(jsonObj.getString("isactive"));
		loginuser.setMODIFIED_BY(requestUser);
		loginuser.setMODIFIED_WORKSTATION(workstation);
		loginuser.setMODIFIED_WHEN(dateFormat1.format(date));
		loginuser = loginuserrepository.saveAndFlush(loginuser);
		rtnLoginUserCategory = mapper.writeValueAsString(loginuser);

		tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(loginuser.getUSER_ID(), databaseTableID,
				requestUser, rtnLoginUserCategory));

		apiRequest.setREQUEST_ID(loginuser.getUSER_ID());
		apiRequest.setREQUEST_OUTPUT(rtnLoginUserCategory);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUserCategory);
		log.info("--------------------------------------------------------");

		return rtnLoginUserCategory;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteLoginUser(@PathVariable Long id) throws JsonProcessingException, JSONException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;

		log.info("DELETE: /loginuser/" + id);

		LoginUser loginuser = loginuserrepository.findOne(id);
		String rtnLoginUserCategory, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, 0, "/loginuser", null,
				requestUser, workstation);

		loginuserrepository.delete(loginuser);
		rtnLoginUserCategory = mapper.writeValueAsString(loginuser);

		tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(loginuser.getUSER_ID(), databaseTableID,
				requestUser, rtnLoginUserCategory));

		apiRequest.setREQUEST_ID(loginuser.getUSER_ID());
		apiRequest.setREQUEST_OUTPUT(rtnLoginUserCategory);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUserCategory);
		log.info("--------------------------------------------------------");

		return rtnLoginUserCategory;
	}

	@RequestMapping(value = "/byusername", method = RequestMethod.POST)
	public String list(@RequestBody String data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;

		log.info("GET: loginuser/byusername");
		log.info("Input: " + data);
		JSONObject jsonObj = new JSONObject(data);
		String rtnLoginUser, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0,
				"/loginuser/byusername", data, requestUser, workstation);

		if (!jsonObj.has("user_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Login User", "User_NAME is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		LoginUser loginuser = loginuserrepository.getUser(jsonObj.getString("user_NAME"));
		rtnLoginUser = mapper.writeValueAsString(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtnLoginUser);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUser);
		log.info("--------------------------------------------------------");

		return rtnLoginUser;
	}

	@RequestMapping(value = "/userid", method = RequestMethod.POST)
	public String LoginByUserId(@RequestBody String data) throws JsonProcessingException {
		// ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;

		log.info("POST: loginuser/userid");
		log.info("Input: " + data);
		JSONObject jsonObj = new JSONObject(data);
		String rtnLoginUser = null, workstation = null;

		List<LoginUser> loginuser = loginuserrepository.findLoginByUserId(jsonObj.getLong("user_ID"),
				jsonObj.getString("current_STATE"));

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0,
				"/loginuser/byusername", data, requestUser, workstation);

		if (!loginuser.isEmpty()) {

			rtnLoginUser = "Yes";
		} else {
			rtnLoginUser = "No";
		}
		// rtnLoginUser = mapper.writeValueAsString(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtnLoginUser);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUser);
		log.info("--------------------------------------------------------");

		return rtnLoginUser;
	}

	@RequestMapping(value = "/{id}/remove", method = RequestMethod.GET)
	public String removeLoginUser(@PathVariable Long id) throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();

		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;

		log.info("GET: /loginuser/" + id + "/remove");

		LoginUser loginuser = loginuserrepository.findOne(id);
		String rtnLoginUser, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, 0,
				"/loginuser" + id + "/remove", "", requestUser, workstation);
		loginuser.setISACTIVE("N");
		loginuser.setMODIFIED_BY(requestUser);
		loginuser.setMODIFIED_WORKSTATION(workstation);
		loginuser.setMODIFIED_WHEN(dateFormat1.format(date));
		loginuser = loginuserrepository.saveAndFlush(loginuser);
		rtnLoginUser = mapper.writeValueAsString(loginuser);
		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(loginuser.getUSER_ID(), databaseTableID, requestUser, rtnLoginUser));

		apiRequest.setREQUEST_ID(loginuser.getUSER_ID());
		apiRequest.setREQUEST_OUTPUT(rtnLoginUser);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUser);
		log.info("--------------------------------------------------------");

		return rtnLoginUser;
	}

	@RequestMapping(value = "/forgetpassword", method = RequestMethod.POST)
	public String forgetPassword(@RequestBody String data) throws JsonProcessingException, ParseException {
		LoginUser requestUser;

		log.info("POST: /loginuser/forgetpassword");
		log.info("Input: " + data);

		JSONObject obj = new JSONObject(data);
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Date date = new Date();
		log.info("Date:  " + date);
		String rtnloginuser, workstation = null;
		RestTemplate restTemplate = new RestTemplate();

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0,
				"/loginuser/forgetpassword", null, requestUser, workstation);

		LoginUser loginuser = loginuserrepository.getUser(obj.getString("email"));


		if (loginuser == null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Student Application", "Email address not found!");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}

		if (!obj.has("email") || obj.isNull("email")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Student Application", "Invalid Email!");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}

		obj.put("letter_ID", 3);
		obj.put("student_ID", loginuser.getUSER_ID());
		obj.put("sendemail", true);

		loginuser.setISCHANGEPASSWORD_REQUESTED("Y");
		loginuser.setISCHANGEPASSWORD_REQUESTEDWHEN(dateFormat1.format(date));

		rtnloginuser = restTemplate.postForObject(applicationPath + "studentcommunicationletter/create", obj.toString(),
				String.class);
		loginuser = loginuserrepository.saveAndFlush(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtnloginuser);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnloginuser);
		log.info("--------------------------------------------------------");

		return rtnloginuser;
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String changePassword(@RequestBody String data) throws JsonProcessingException, ParseException {
		LoginUser requestUser;

		log.info("POST: /admission/changepassword");
		log.info("Input: " + data);

		JSONObject obj = new JSONObject(data);
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// Date date = new Date();
		// dateFormat1.format(date);

		String rtnAdmission, workstation = null;
		RestTemplate restTemplate = new RestTemplate();
		// ObjectMapper mapper = new ObjectMapper();
		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0,
				"/admission/changepassword", null, requestUser, workstation);

		LoginUser loginuser = loginuserrepository.getUser(obj.getString("user_NAME"));

		if (loginuser == null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Student Application", "Email address not found!");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}

		if (loginuser.getISCHANGEPASSWORD_REQUESTED().toString().equals("Y")) {

			String date2 = loginuser.getISCHANGEPASSWORD_REQUESTEDWHEN();
			Date requestDate = dateFormat1.parse(date2);

			if (requestDate.compareTo(new Date()) < 0) {
				apiRequest = tableDataLogs.errorDataLog(apiRequest, "Student Application", "Session Expire!");
				apirequestdatalogRepository.saveAndFlush(apiRequest);
				return apiRequest.getREQUEST_OUTPUT();
			}

		} else {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Student Application",
					"we have not recivierd request to change the password kindly send the request again !");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		if (!obj.has("email") || obj.isNull("email")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Student Application", "Invalid Email!");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		if (!obj.has("password") || obj.isNull("password")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Student Application", "Password is missing!");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}

		obj.put("letter_ID", 4);
		obj.put("student_ID", loginuser.getUSER_ID());
		obj.put("sendemail", true);
		rtnAdmission = restTemplate.postForObject(applicationPath + "studentcommunicationletter/create", obj.toString(),
				String.class);

		loginuser.setISCHANGEPASSWORD_REQUESTED("N");
		loginuser.setPASSWORD(obj.getString("password"));
		loginuser = loginuserrepository.saveAndFlush(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtnAdmission);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnAdmission);
		log.info("--------------------------------------------------------");

		return rtnAdmission;
	}

	@RequestMapping(value = "/changepassworddirectly", method = RequestMethod.PUT)
	public String changePassworddirectly(@RequestBody String data) throws JsonProcessingException, ParseException {
		LoginUser requestUser;

		log.info("PUT: /loginuser/changepassworddirectly");
		log.info("Input: " + data);

		JSONObject obj = new JSONObject(data);

		String workstation = null;
		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, 0,
				"/loginuser/changepassword", null, requestUser, workstation);

		LoginUser loginuser = loginuserrepository.getUser(obj.getString("user_NAME"));

		if (loginuser == null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Student Application", "Email address not found!");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}

		if (!obj.has("password") || obj.isNull("password")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Student Application", "Password is missing!");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		loginuser.setPASSWORD(obj.getString("password"));
		loginuser = loginuserrepository.saveAndFlush(loginuser);

		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + obj.put("sucess", "passwored successfuly changed !"));
		log.info("--------------------------------------------------------");

		return obj.put("sucess", "passwored successfuly changed !").toString();
	}

	public HttpHeaders token() {

		JSONObject jsonObjtoken = null;
		try {
			jsonObjtoken = new JSONObject(AccessToken.findToken());
		} catch (Exception e) {
		}
		log.info("OBJECT TOKEN " + jsonObjtoken.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		headers.add("Authorization", "Bearer " + jsonObjtoken.getString("access_token"));
		headers.add("grant_type", "password");
		return headers;

	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String getIntakeCourseListBySearch(@RequestBody String data) throws JsonProcessingException {
		log.info("POST: loginuser/search");
		log.info("Input: " + data);

		RestTemplate restTemplate = new RestTemplate();
		JSONObject jsonObj = new JSONObject(data);

		LoginUser requestUser;
		HttpHeaders headers = token();

		String rtnloginuser = null, workstation = null;
		long user_TYPE = 0;

		if (jsonObj.has("user_TYPE"))
			user_TYPE = jsonObj.getLong("user_TYPE");

		if (user_TYPE == 1) {
			HttpEntity<String> entityemployee = new HttpEntity<String>(jsonObj.toString(), headers);
			rtnloginuser = restTemplate.postForObject(applicationPath + "employee/search", entityemployee,
					String.class);
			DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
			requestUser = userrepository.findOne((long) 0);
			APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0,
					"/loginuser/search", data, requestUser, workstation);
			apiRequest.setREQUEST_OUTPUT(rtnloginuser);
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);

		} else if (user_TYPE == 2) {
			HttpEntity<String> entityemployee = new HttpEntity<String>(jsonObj.toString(), headers);
			rtnloginuser = restTemplate.postForObject(applicationPath + "studentinstance/search", entityemployee,
					String.class);
			DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
			requestUser = userrepository.findOne((long) 0);
			APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0,
					"/loginuser/search", data, requestUser, workstation);
			apiRequest.setREQUEST_OUTPUT(rtnloginuser);
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);

		}

		log.info("Output: " + rtnloginuser);
		log.info("--------------------------------------------------------");

		return rtnloginuser;

	}

	@RequestMapping(value = "/search/all", method = RequestMethod.POST)
	public String getuserbyID(@RequestBody String data) throws JsonProcessingException {

		log.info("POST: loginuser/search/all");
		log.info("Input: " + data);

		LoginUser requestUser;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = token();

		String rtnloginuser = null, workstation = null;
		JSONObject jsonObj = new JSONObject(data);
		long user_TYPE = 0;

		if (jsonObj.has("user_TYPE"))
			user_TYPE = jsonObj.getLong("user_TYPE");

		if (user_TYPE == 1) {
			HttpEntity<String> entity = new HttpEntity<String>(jsonObj.toString(), headers);
			rtnloginuser = restTemplate.postForObject(applicationPath + "employeeotherinfo/advancedsearch", entity,
					String.class);
			DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
			requestUser = userrepository.findOne((long) 0);
			APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0,
					"/loginuser/search/all", data, requestUser, workstation);

			apiRequest.setREQUEST_OUTPUT(rtnloginuser);
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);

		} else if (user_TYPE == 2) {
			HttpEntity<String> entity = new HttpEntity<String>(jsonObj.toString(), headers);
			rtnloginuser = restTemplate.postForObject(applicationPath + "studentinstance/advancedsearch", entity,
					String.class);
			DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
			requestUser = userrepository.findOne((long) 0);
			APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0,
					"/loginuser/search/all", data, requestUser, workstation);

			apiRequest.setREQUEST_OUTPUT(rtnloginuser);
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);

		}

		log.info("Output: " + rtnloginuser);
		log.info("--------------------------------------------------------");

		return rtnloginuser;

	}

	@RequestMapping(value = "byuser/{id}", method = RequestMethod.GET)
	public String getLoginUserByUserId(@PathVariable Long id) throws JsonProcessingException {
		LoginUser requestUser = null;

		log.info("GET: /loginuser/byuser" + id);

		LoginUser loginuser = loginuserrepository.findOne(id);
		String rtnloginuser = null, workstation = null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = token();
		JSONObject jsonObj = new JSONObject();

		long user_TYPE = 0;
		DatabaseTables databaseTableIDofUser = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequestuser = tableDataLogs.apiRequestDataLog("GET", databaseTableIDofUser, 0,
				"/loginuser/byuser/" + id, null, requestUser, workstation);

		if (loginuser != null) {
			jsonObj.put("user_ID", id);
		} else {
			apiRequestuser = tableDataLogs.errorDataLog(apiRequestuser, "loginuse", "user_ID not exist");
			apirequestdatalogRepository.saveAndFlush(apiRequestuser);
			return apiRequestuser.getREQUEST_OUTPUT();
		}

		if (user_TYPE == 1) {
			HttpEntity<String> entity = new HttpEntity<String>(jsonObj.toString(), headers);
			rtnloginuser = restTemplate.postForObject(applicationPath + "employeeotherinfo/advancedsearch", entity,
					String.class);
			DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
			requestUser = userrepository.findOne((long) 0);
			APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0,
					"/loginuser/search/all", null, requestUser, workstation);

			apiRequest.setREQUEST_OUTPUT(rtnloginuser);
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);

		} else if (user_TYPE == 2) {
			HttpEntity<String> entity = new HttpEntity<String>(jsonObj.toString(), headers);
			rtnloginuser = restTemplate.postForObject(applicationPath + "studentinstance/advancedsearch", entity,
					String.class);
			DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
			requestUser = userrepository.findOne((long) 0);
			APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0,
					"/loginuser/search/all", null, requestUser, workstation);

			apiRequest.setREQUEST_OUTPUT(rtnloginuser);
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);

		}

		log.info("Output: " + rtnloginuser);
		log.info("--------------------------------------------------------");

		return rtnloginuser;
	}

};
