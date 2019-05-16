package com.cwiztech.systemsetting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cwiztech.systemsetting.model.Lookup;;

@Repository
public interface lookupRepository extends JpaRepository<Lookup, Long> {
	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ENTITYNAME=?1 and ISACTIVE='Y'", nativeQuery = true)
	public List<Lookup> findActiveByEntityName(String data);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ENTITYNAME=?1", nativeQuery = true)
	public List<Lookup> findAllByEntityName(String data);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ENTITYNAME='APPLICATIONSTATUS' and CODE=?1", nativeQuery = true)
	public Lookup findApplicationStatusByCode(String code);

	@Query(value = "select distinct ENTITYNAME from TBLSYSTEMSETTINGLOOKUP where ENTITYNAME not in ('EMAILSETTING') order by ENTITYNAME", nativeQuery = true)
	public List<Object> findEntityList();

	@Query(value = "select * from TABLESTUDENTEQUALITY  e left outer join VLE_LOOKUP_DATA as di on e.DISABLE_ID=di.CODE left outer join VLE_LOOKUP_DATA as d on e.DOMICILE_ID=d.CODE ,left outer join VLE_LOOKUP_DATA as n on e.ETHNIC_ID=n.CODE ,left outer join VLE_LOOKUP_DATA as g on E.GENDER_ID=g.CODE ,left outer join VLE_LOOKUP_DATA as na on e.NATIONALITY_ID=na.CODE ,left outer join VLE_LOOKUP_DATA as s on e.SEX_ID=s.CODE ,left outer join VLE_LOOKUP_DATA as o on e.SEXORT_ID=o.CODE ,left outer join VLE_LOOKUP_DATA as r on e.RELIGION_ID=r.CODE where STUDENT_ID=?1", nativeQuery = true)
	public List<Lookup> getLookup(Long id);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ISACTIVE='Y' and ENTITYNAME in ('DOCUMENTLIST', 'ENGLISHTEST', 'PROVIDER', 'ADMISSIONQUALIFICATION', 'QUALIFICATIONSUBJECT', 'QUALIFICATIONGRADE', 'SOURCETUITIONFEES', 'SEX', 'NATIONALITY', 'ETHNICITY', 'DISABILITY', 'PREFERCONTACT', 'WORKTYPE') order by description", nativeQuery = true)
	public List<Lookup> findActiveForAdmission();

	@Query(value = "select distinct l.* from TBLSYSTEMSETTINGLOOKUP l inner join TBLACADEMICSCOURSE c on l.ID=c.COURSEMODE_ID inner join TBLACADEMICSINTAKE t on c.COURSE_ID=t.COURSE_ID where ENTITYNAME='STUDYMODE' and ISADMISSIONOPEN='Y'", nativeQuery = true)
	public List<Lookup> findAddmissionCourseMode();

	@Query(value = "select distinct l.* from TBLSYSTEMSETTINGLOOKUP l inner join TBLACADEMICSCOURSE c on l.ID=c.COURSE_LOCATION inner join TBLACADEMICSINTAKE t on c.COURSE_ID=t.COURSE_ID where ENTITYNAME='COURSELOCATION' and ISADMISSIONOPEN='Y' and c.COURSEMODE_ID=?1", nativeQuery = true)
	public List<Lookup> findAddmissionCourseLocation(Long id);

	@Query(value = "select distinct l.* from TBLSYSTEMSETTINGLOOKUP l inner join TBLACADEMICSCOURSE c on l.ID=c.COURSEAIM_ID inner join TBLACADEMICSINTAKE t on c.COURSE_ID=t.COURSE_ID where ENTITYNAME='COURSEAIM' and ISADMISSIONOPEN='Y' and c.COURSEMODE_ID=?1 and c.COURSE_LOCATION=?2", nativeQuery = true)
	public List<Lookup> findAddmissionCourseAim(Long cmid, Long clid);

	// Admission
	@Query(value = "select distinct d.* from TBLACADEMICSCOURSE as a inner join TBLACADEMICSINTAKECOURSE as b on a.COURSE_ID=b.COURSE_ID inner join TBLACADEMICSINTAKE as c on b.INTAKE_ID=c.INTAKE_ID inner join TBLSYSTEMSETTINGLOOKUP as d on b.COURSEMODE_ID=d.ID where a.course_ID=?1 and a.ISACTIVE='Y' and b.ISACTIVE='Y' and c.ISACTIVE='Y' and ISADMISSIONOPEN='Y'", nativeQuery = true)
	public List<Lookup> findCourseModeByCourseForAdmission(Long cid);

	@Query(value = "select distinct a.* from TBLSYSTEMSETTINGLOOKUP as a inner join TBLEMPLOYEEUNIT as b on a.ID=b.UNIT_GROUP inner join TBLSTUDENTUNIT as c on b.SEMESTER_ID=c.SEMESTER_ID and b.UNIT_ID=c.UNIT_ID and b.UNIT_GROUP=c.UNIT_GROUP inner join TBLSTUDENTINSTANCE as d on c.STUDENTINSTANCE_ID=d.STUDENTINSTANCE_ID inner join TBLACADEMICSINTAKE as e on d.INTAKE_ID=e.INTAKE_ID where ISACTIVE='Y' AND b.SEMESTER_ID=?1 and COURSE_ID=?2 and b.UNIT_ID=?3", nativeQuery = true)
	public List<Lookup> getUnitGroupBySemCourse(Long sid, Long cid, Long uid);

	@Query(value = "select distinct a.* from TBLSYSTEMSETTINGLOOKUP as a inner join TBLEMPLOYEEUNIT as b on a.ID=b.UNIT_GROUP inner join TBLSTUDENTUNIT as c on b.SEMESTER_ID=c.SEMESTER_ID and b.UNIT_ID=c.UNIT_ID and b.UNIT_GROUP=c.UNIT_GROUP inner join TBLSTUDENTINSTANCE as d on c.STUDENTINSTANCE_ID=d.STUDENTINSTANCE_ID inner join TBLACADEMICSINTAKE as e on d.INTAKE_ID=e.INTAKE_ID inner join TBLSEMESTERTIMETABLE as f on b.EMPLOYEEUNIT_ID=f.EMPLOYEEUNIT_ID where f.ISACTIVE='Y' AND b.SEMESTER_ID=?1 and COURSE_ID=?2 and b.UNIT_ID=?3", nativeQuery = true)
	public List<Lookup> getUnitGroupBySemCourseTimetable(Long sid, Long cid, Long uid);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ENTITYNAME='EMAILSETTING'", nativeQuery = true)
	public Lookup getEmailSettings();
	
	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ENTITYNAME='SMSSETTING'", nativeQuery = true)
	public Lookup getSmsSettings();

	@Query(value = "select top 1 * from TBLSYSTEMSETTINGLOOKUP where DESCRIPTION=?1 and ENTITY_STATUS='S'", nativeQuery = true)
	public Lookup getEntityDescription(String des);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ID=?1", nativeQuery = true)
	public Lookup getQualificationLooKupId(Long id);
	
	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where CODE=1 and ENTITYNAME='STUDENTUNITSTATUS'", nativeQuery = true)
	public Lookup getStudentModuleStatus();

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where DESCRIPTION=?1", nativeQuery = true)
	public Lookup getDescriptionID(String des);

}
