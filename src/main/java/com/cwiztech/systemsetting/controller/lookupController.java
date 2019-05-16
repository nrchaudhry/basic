package com.cwiztech.systemsetting.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.login.model.LoginUser;
import com.cwiztech.login.repository.loginUserRepository;
import com.cwiztech.systemsetting.model.Lookup;
import com.cwiztech.systemsetting.repository.lookupRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/lookup")
public class lookupController{
	
	private static final Logger log = LoggerFactory.getLogger(lookupController.class);
	
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
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Lookup> list() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("lookup");
		
		List<Lookup> lookup =  lookuprepository.findAll();
		
		System.out.println("Output: "+mapper.writeValueAsString(lookup));
		System.out.println("--------------------------------------------------------");
		
		return lookup;
	}
	
	@RequestMapping(value="/entitylist",method=RequestMethod.GET)
	public String findEntityList() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;
		
		log.info("GET: /lookup/entitylist");
		
		List<Object> lookup =  lookuprepository.findEntityList();
		String rtnLookup, workstation=null;
		
		DatabaseTables databaseTableID = databaetablesrepository.findOne(Lookup.getDatabaseTableID());
		requestUser=userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, 0, "/lookup/entitylist", null, requestUser, workstation);
		
		rtnLookup = mapper.writeValueAsString(lookup);
		
		apiRequest.setREQUEST_OUTPUT(rtnLookup);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);
		
		log.info("Output: "+rtnLookup);
		log.info("--------------------------------------------------------");
		
		return rtnLookup;
	}
	
	@RequestMapping(value="/entity",method=RequestMethod.POST)
	public List<Lookup> findActiveByEntityName(@RequestBody String data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("lookup/entity");
		System.out.println("Input: "+mapper.writeValueAsString(data));
		
		JSONObject jsonObj = new JSONObject(data);
		String entity=(String) jsonObj.get("entityname");
		
		List<Lookup> lookup =  lookuprepository.findActiveByEntityName(entity);
		
		System.out.println("Output: "+mapper.writeValueAsString(lookup));
		System.out.println("--------------------------------------------------------");
		
		return lookup;
	}
	
	@RequestMapping(value="/entity/all",method=RequestMethod.POST)
	public List<Lookup> findAllByEntityName(@RequestBody String data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("lookup/admissioncourselocation");
		System.out.println("Input: "+mapper.writeValueAsString(data));
		
		JSONObject jsonObj = new JSONObject(data);
		String entity=(String) jsonObj.get("entityname");
		
		List<Lookup> lookup =  lookuprepository.findAllByEntityName(entity);
		
		System.out.println("Output: "+mapper.writeValueAsString(lookup));
		System.out.println("--------------------------------------------------------");
		
		return lookup;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Lookup get(@PathVariable Long id) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("lookup/admissioncourselocation");
		System.out.println("Input: "+id);
		
		Lookup lookup =  lookuprepository.findOne(id);
		
		System.out.println("Output: "+mapper.writeValueAsString(lookup));
		System.out.println("--------------------------------------------------------");
		
		return lookup;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String createLookup(@RequestBody String data) throws JsonProcessingException,JSONException, ParseException
	{
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;
		
		log.info("POST: /lookup");
		log.info("Input: "+data);
		
		JSONObject jsonObj=new JSONObject(data);
		Lookup lookup = new Lookup();
		String rtn, workstation=null;
		
		
		DatabaseTables databaseTableID = databaetablesrepository.findOne(Lookup.getDatabaseTableID());
		if (jsonObj.has("user"))
			requestUser=userrepository.getUser(jsonObj.getString("user"));
		else
			requestUser=userrepository.findOne((long) 0);
		if (jsonObj.has("workstation"))
			workstation=jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, 0, "/lookup", data, requestUser, workstation);
			
		if (!jsonObj.has("entityname")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Lookup", "Entityname  is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		lookup.setENTITYNAME(jsonObj.getString("entityname"));
		
		if (!jsonObj.has("code")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Lookup", "Code  is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		lookup.setCODE(jsonObj.getString("code"));
		
		if (!jsonObj.has("description")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Lookup", "Description  is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		lookup.setDESCRIPTION(jsonObj.getString("description"));
		
		if (jsonObj.has("entity_STATUS") && !jsonObj.isNull("entity_STATUS")) 
		lookup.setENTITY_STATUS(jsonObj.getString("entity_STATUS").toUpperCase());
		
		lookup.setISACTIVE("Y");
		lookup.setMODIFIED_BY(requestUser);
		lookup.setMODIFIED_WORKSTATION(workstation);
		lookup.setMODIFIED_WHEN(dateFormat1.format(date));
		lookup = lookuprepository.saveAndFlush(lookup);
		rtn = mapper.writeValueAsString(lookup);
		
		tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(lookup.getID(), databaseTableID, requestUser, rtn));
		
		apiRequest.setREQUEST_ID(lookup.getID());
		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);
		
		log.info("Output: "+rtn);
		log.info("--------------------------------------------------------");
		
		return rtn;
		
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public String updateLookup(@PathVariable Long id, @RequestBody String data) throws JsonProcessingException,JSONException, ParseException
	{
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;
		
		log.info("PUT: /lookup/"+id);
		log.info("Input: "+data);
		
		JSONObject jsonObj=new JSONObject(data);
		Lookup lookup = lookuprepository.findOne(id);
		String rtn, workstation=null;
		
		
		DatabaseTables databaseTableID = databaetablesrepository.findOne(Lookup.getDatabaseTableID());
		if (jsonObj.has("user"))
			requestUser=userrepository.getUser(jsonObj.getString("user"));
		else
			requestUser=userrepository.findOne((long) 0);
		if (jsonObj.has("workstation"))
			workstation=jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, 0, "/department", data, requestUser, workstation);
	
		if (jsonObj.has("entityname") && !jsonObj.isNull("entityname"))
			lookup.setENTITYNAME(jsonObj.getString("entityname"));
		if (jsonObj.has("code") && !jsonObj.isNull("code"))
			lookup.setCODE(jsonObj.getString("code"));
		if (jsonObj.has("description") && !jsonObj.isNull("description")) 
			lookup.setDESCRIPTION(jsonObj.getString("description"));
		if (jsonObj.has("entity_STATUS")) 
			lookup.setENTITY_STATUS(jsonObj.getString("entity_STATUS"));
	
		if (jsonObj.has("isactive"))
			lookup.setISACTIVE(jsonObj.getString("isactive"));
		lookup.setMODIFIED_BY(requestUser);
		lookup.setMODIFIED_WORKSTATION(workstation);
		lookup.setMODIFIED_WHEN(dateFormat1.format(date));
		lookup = lookuprepository.saveAndFlush(lookup);
		rtn = mapper.writeValueAsString(lookup);
		
		tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(lookup.getID(), databaseTableID, requestUser, rtn));
		
		apiRequest.setREQUEST_ID(lookup.getID());
		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);
		
		log.info("Output: "+rtn);
		log.info("--------------------------------------------------------");
		
		return rtn;
	}
	
	/*
	@RequestMapping(value="/all/{id}",method=RequestMethod.GET)
	public List<Lookup> getlookup(@PathVariable Long id){
	return lookuprepository.getLookup(id);
	
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Lookup create(@RequestBody Lookup data) throws JsonProcessingException{
	ObjectMapper mapper = new ObjectMapper();
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
	Date date = new Date();
	System.out.println("semester/add");
	System.out.println("Input: "+data);
	
	data.setSTATUS("Y");
	if (data.getCREATED_BY()==null)
	data.setCREATED_BY(userrepository.findOne((long) 0));
	data.setCREATED_WHEN(dateFormat1.format(date));
	Lookup lookup = lookuprepository.saveAndFlush(data);
	
	
	tbldatalogrepository.saveAndFlush(tableDataLogs.TableDeleteDataLog(lookup.getID(), databaetablesrepository.findOne((long) 12), lookup.getCREATED_BY(), mapper.writeValueAsString(lookup)));
	
	System.out.println("Output: "+mapper.writeValueAsString(lookup));
	System.out.println("--------------------------------------------------------");
	return lookup;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Lookup update(@PathVariable Long id,@RequestBody Lookup data) throws JsonProcessingException{
	ObjectMapper mapper = new ObjectMapper();
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
	Date date = new Date();
	System.out.println("semester/update/"+id);
	System.out.println("Input: "+mapper.writeValueAsString(data));
	
	if (data.getCREATED_BY()==null)
	data.setCREATED_BY(userrepository.findOne((long) 0));
	data.setCREATED_WHEN(dateFormat1.format(date));
	
	Lookup lookup=lookuprepository.findOne(id);
	BeanUtils.copyProperties(data, lookup);
	lookup = lookuprepository.saveAndFlush(lookup);
	
	tbldatalogrepository.saveAndFlush(tableDataLogs.TableDeleteDataLog(lookup.getID(), databaetablesrepository.findOne((long) 12), lookup.getCREATED_BY(), mapper.writeValueAsString(lookup)));
	
	System.out.println("Output: "+mapper.writeValueAsString(lookup));
	System.out.println("--------------------------------------------------------");
	return lookup;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Lookup delete(@PathVariable Long id){
	Lookup existingStd=lookuprepository.findOne(id);
	lookuprepository.delete(existingStd);
	return existingStd;
	
	}
	
	*/
	
	// Lookup services for Admission Online Application
	
	@RequestMapping(value="/active",method=RequestMethod.GET)
	public List<Lookup> activelist() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("lookup/active");
		
		List<Lookup> lookup =  lookuprepository.findActiveForAdmission();
		
		System.out.println("Output: "+mapper.writeValueAsString(lookup));
		System.out.println("--------------------------------------------------------");
		
		return lookup;
	}
	
	// Lookup services for Academics
	
	@RequestMapping(value="/unitGroupBySemCourse",method=RequestMethod.POST)
	public List<Lookup> unitGroupBySemCourse(@RequestBody String data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("lookup/unitGroupBySemCourse");
		System.out.println("Input: "+mapper.writeValueAsString(data));
		
		JSONObject jsonObj = new JSONObject(data);
		Long sID=jsonObj.getLong("semester_ID");
		Long cID=jsonObj.getLong("course_ID");
		Long uID=jsonObj.getLong("unit_ID");
		
		List<Lookup> lookup =  lookuprepository.getUnitGroupBySemCourse(sID, cID, uID);
		
		System.out.println("Output: "+mapper.writeValueAsString(lookup));
		System.out.println("--------------------------------------------------------");
		
		return lookup;
	}
	
	@RequestMapping(value="/unitGroupBySemCourseTimetable",method=RequestMethod.POST)
	public List<Lookup> unitGroupBySemCourseTimetable(@RequestBody String data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("lookup/unitGroupBySemCourseTimetable");
		System.out.println("Input: "+mapper.writeValueAsString(data));
		
		JSONObject jsonObj = new JSONObject(data);
		Long sID=jsonObj.getLong("semester_ID");
		Long cID=jsonObj.getLong("course_ID");
		Long uID=jsonObj.getLong("unit_ID");
		
		List<Lookup> lookup =  lookuprepository.getUnitGroupBySemCourseTimetable(sID, cID, uID);
		
		System.out.println("Output: "+mapper.writeValueAsString(lookup));
		System.out.println("--------------------------------------------------------");
		
		return lookup;
	}
	
	@RequestMapping(value="/{id}/remove", method = RequestMethod.GET)
	public String removeStudentLookup(@PathVariable Long id) throws JsonProcessingException,JSONException, ParseException
	{
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();
		
		ObjectMapper mapper = new ObjectMapper();
		LoginUser requestUser;
		
		log.info("GET: /lookup/"+id+"/remove");
		
		Lookup lookup = lookuprepository.findOne(id);
		String rtnLookup, workstation=null;
		
		DatabaseTables databaseTableID = databaetablesrepository.findOne(Lookup.getDatabaseTableID());
		requestUser=userrepository.findOne((long) 0);
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, 0, "/lookup"+id+"/remove", "", requestUser, workstation);
		lookup.setISACTIVE("N");
		lookup.setMODIFIED_BY(requestUser);
		lookup.setMODIFIED_WORKSTATION(workstation);
		lookup.setMODIFIED_WHEN(dateFormat1.format(date));
		lookup = lookuprepository.saveAndFlush(lookup);
		rtnLookup = mapper.writeValueAsString(lookup);
		tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(lookup.getID(), databaseTableID, requestUser, rtnLookup));

		apiRequest.setREQUEST_ID(lookup.getID());
		apiRequest.setREQUEST_OUTPUT(rtnLookup);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);
		
		log.info("Output: "+rtnLookup);
		log.info("--------------------------------------------------------");
		
		return rtnLookup;
	}
	
	
	
	

	
};
