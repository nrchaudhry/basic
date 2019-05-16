package com.cwiztech.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cwiztech.login.model.LoginUser;

public interface loginUserRepository extends JpaRepository<LoginUser, Long> {

	@Query(value = "select * from TBLLOGINUSER where USER_NAME=?1 AND PASSWORD=?2", nativeQuery = true)
	LoginUser getAuthenticationUser(String username, String password);

	@Query(value = "select * from TBLLOGINUSER where USER_NAME=?1 and ISACTIVE='Y'", nativeQuery = true)
	LoginUser getUser(String username);
	
	@Query(value = "select * from TBLLOGINUSER where USER_ID=?1 ", nativeQuery = true)
	LoginUser getUserbyID(Long id);
	
	@Query(value = "select a.* from TBLLOGINUSER as a inner join TBLLOGINPRIVILEGECATEGORY as b on a.PCATEGORY_ID=b.PCATEGORY_ID where APPLICATION_ID=?1 order by PCATEGORYORDER_NO, PRIVILEGEORDER_NO", nativeQuery = true)
	public List<LoginUser> findAllByApplication(Long aid);

	@Query(value = "select distinct a.* from TBLLOGINUSER as a inner join TBLLOGINUSERPRIVILEGE as b on a.PRIVILEGE_ID=b.PRIVILEGE_ID where USER_ID=?1 and a.ISACTIVE='Y' and b.ISACTIVE='Y'", nativeQuery = true)
	public List<LoginUser> byUserPrivilege(Long id);

	@Query(value = "select distinct a.* from TBLLOGINUSER as a inner join TBLLOGINUSERPRIVILEGE as b on a.PRIVILEGE_ID=b.PRIVILEGE_ID inner join TBLLOGINUSERROLE as c on b.ROLE_ID=c.ROLE_ID where USER_ID=?1 and a.ISACTIVE='Y' and b.ISACTIVE='Y' and c.ISACTIVE='Y'", nativeQuery = true)
	public List<LoginUser> byUserRole(Long id);

	@Query(value = "select * from TBLLOGINUSER where USER_NAME=?1 and ISACTIVE='Y'", nativeQuery = true)
	public LoginUser findByLoginUserActive(String sid);

	@Query(value = "select * from TBLLOGINUSER where USER_NAME=?1 ", nativeQuery = true)
	public LoginUser findByLoginUserName(Long sid);

	@Query(value = "select * from TBLLOGINUSER where USER_NAME=?1 and USER_TYPE=?2 ", nativeQuery = true)
	public LoginUser findByLoginUserType(String sid, Long utyp);
	
	@Query(value = "select * from TBLLOGINUSER where ISACTIVE='Y'", nativeQuery = true)
	public List<LoginUser> findLoginUserActive();
	
	@Query(value="select * from TBLLOGINUSER where USER_ID not in (select USER_ID from TBLLOGINUSERROLE where ROLE_ID=?1 and ISACTIVE='Y') and ISACTIVE='Y'",nativeQuery=true)	
    public List<LoginUser> findOtherUser(Long sid);

	@Query(value = "select * from TBLLOGINUSER as a " 
	        + "inner join TBLLOGINUSERROLE as b on a.USER_ID=b.USER_ID "
			+ "inner join TBLLOGINROLEPRIVILEGE as d on b.ROLE_ID=d.ROLE_ID "
			+ "inner join TBLLOGINPRIVILEGE as c on d.PRIVILEGE_ID=c.PRIVILEGE_ID "
			+ "where a.USER_ID=?1 and c.PRIVILEGE_STATE=?2", nativeQuery = true)
	public List<LoginUser> findLoginByUserId(Long id, String state);
}
