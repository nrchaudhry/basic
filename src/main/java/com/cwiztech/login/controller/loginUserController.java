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
import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.login.model.LoginUser;
import com.cwiztech.login.repository.loginUserRepository;
import com.cwiztech.systemsetting.repository.lookupRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/loginuser")
public class loginUserController {
	private static final Logger log = LoggerFactory.getLogger(loginUserController.class);

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
		log.info("GET: /loginuser");

		List<LoginUser> loginuser = loginuserrepository.findLoginUserActive();
		String rtn, workstation = null;
		LoginUser requestUser;
		requestUser = userrepository.findOne((long) 0);

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/loginuser", null,
				workstation);

		rtn = mapper.writeValueAsString(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getLoginUserById(@PathVariable Long id) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("GET: /loginuser/" + id);

		LoginUser loginuser = loginuserrepository.findOne(id);
		String rtn, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, loginuser, "/loginuser/" + id,
				null, workstation);

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
		log.info("GET: /loginuser/byusername/" + name);

		LoginUser loginuser = loginuserrepository.findByLoginUserActive(name);
		String rtn, workstation = null;
		LoginUser requestUser;
		requestUser = userrepository.findOne((long) 0);

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/loginuser/byusername/" + name, null, workstation);

		rtn = mapper.writeValueAsString(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value = "/byusername/{name}/all", method = RequestMethod.GET)
	public String getAllLoginUserList(@PathVariable String name) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("GET: /loginuser/byusername/" + name + "/all");

		LoginUser loginuser = loginuserrepository.findByLoginUserActive(name);
		String rtn, workstation = null;
		LoginUser requestUser;
		requestUser = userrepository.findOne((long) 0);

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/loginuser/byusername/" + name + "/all", null, workstation);

		rtn = mapper.writeValueAsString(loginuser);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

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
		requestUser = userrepository.findOne((long) 0);
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/loginuser", data,
				workstation);

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

		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableSaveDataLog(loginuser.getUSER_ID(), databaseTableID, 0, rtnLoginUser));

		apiRequest.setREQUEST_ID(requestUser);
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

		log.info("PUT: /loginuser/" + id);
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		LoginUser loginuser = loginuserrepository.findOne(id);
		String rtn, workstation = null;
		LoginUser requestUser;
		requestUser = userrepository.findOne((long) 0);

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		if (jsonObj.has("user"))
			requestUser = userrepository.getUser(jsonObj.getString("user"));
		else
			requestUser = userrepository.findOne((long) 0);
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/loginuser", data,
				workstation);

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
		rtn = mapper.writeValueAsString(loginuser);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(loginuser.getUSER_ID(), databaseTableID, 0, rtn));

		apiRequest.setREQUEST_ID(requestUser);
		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteLoginUser(@PathVariable Long id) throws JsonProcessingException, JSONException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("DELETE: /loginuser/" + id);

		LoginUser loginuser = loginuserrepository.findOne(id);
		String rtn, workstation = null;

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, loginuser, "/loginuser", null,
				workstation);

		loginuserrepository.delete(loginuser);
		rtn = mapper.writeValueAsString(loginuser);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(loginuser.getUSER_ID(), databaseTableID, 0, rtn));

		apiRequest.setREQUEST_ID(loginuser);
		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value = "/byusername", method = RequestMethod.POST)
	public String list(@RequestBody String data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("GET: loginuser/byusername");
		log.info("Input: " + data);
		JSONObject jsonObj = new JSONObject(data);
		String rtnLoginUser, workstation = null;
		LoginUser requestUser;
		requestUser = userrepository.findOne((long) 0);

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/loginuser/byusername", data, workstation);

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
		log.info("POST: loginuser/userid");
		log.info("Input: " + data);
		JSONObject jsonObj = new JSONObject(data);
		String rtnLoginUser = null, workstation = null;
		LoginUser requestUser;
		requestUser = userrepository.findOne((long) 0);

		List<LoginUser> loginuser = loginuserrepository.findLoginByUserId(jsonObj.getLong("user_ID"),
				jsonObj.getString("current_STATE"));

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/loginuser/byusername", data, workstation);

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

		log.info("GET: /loginuser/" + id + "/remove");

		LoginUser loginuser = loginuserrepository.findOne(id);
		String rtnLoginUser, workstation = null;
		LoginUser requestUser;
		requestUser = userrepository.findOne((long) 0);

		DatabaseTables databaseTableID = databaetablesrepository.findOne(LoginUser.getDatabaseTableID());
		requestUser = userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/loginuser" + id + "/remove", "", workstation);
		loginuser.setISACTIVE("N");
		loginuser.setMODIFIED_BY(requestUser);
		loginuser.setMODIFIED_WORKSTATION(workstation);
		loginuser.setMODIFIED_WHEN(dateFormat1.format(date));
		loginuser = loginuserrepository.saveAndFlush(loginuser);
		rtnLoginUser = mapper.writeValueAsString(loginuser);
		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableSaveDataLog(loginuser.getUSER_ID(), databaseTableID, 0, rtnLoginUser));

		apiRequest.setREQUEST_ID(requestUser);
		apiRequest.setREQUEST_OUTPUT(rtnLoginUser);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtnLoginUser);
		log.info("--------------------------------------------------------");

		return rtnLoginUser;
	}

}
