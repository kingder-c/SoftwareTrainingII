package com.siemens.mc.visa.dao;

import com.siemens.mc.visa.common.Constant;
import com.siemens.mc.visa.common.Constant.UserStatus;
import com.siemens.mc.visa.javabean.DiagnosisDatabase;
import com.siemens.mc.visa.javabean.Role;
import com.siemens.mc.visa.javabean.User;
import com.siemens.mc.visa.javabean.UserRank;
import com.siemens.mc.visa.utils.PageUtil;

import javafx.animation.KeyValue.Type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/12/13.
 */
public class UserDao extends BaseDao<User> {

    public int insert(User user) throws SQLException {
        int rowCount = 0;
        try {
            if(user != null) {
                this.cleanStringBuffer();
                this.sql.append("INSERT INTO user ");
                this.sql.append("(userName, password, name, email, phone, ");
                this.sql.append("company, country, province, city, street, ");
                this.sql.append("postcode, is_checked, status) ");
                this.sql.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                this.pstmt = this.connection.prepareStatement(this.sql.toString());
                this.setParameter(1, user.getUsername(), Types.NVARCHAR);
                this.setParameter(2, user.getPassword(), Types.NVARCHAR);
                this.setParameter(3, user.getName(), Types.NVARCHAR);
                this.setParameter(4, user.getEmail(), Types.NVARCHAR);
                this.setParameter(5, user.getPhone(), Types.NVARCHAR);

                this.setParameter(6, user.getCompany(), Types.NVARCHAR);
                this.setParameter(7, user.getCountry(), Types.NVARCHAR);
                this.setParameter(8, user.getProvince(), Types.NVARCHAR);
                this.setParameter(9, user.getCity(), Types.NVARCHAR);
                this.setParameter(10, user.getStreet(), Types.NVARCHAR);

                this.setParameter(11, user.getPostcode(), Types.NVARCHAR);
                this.setParameter(12, user.getIsChecked(), Types.BOOLEAN);
                this.setParameter(13, UserStatus.CREATED.value, Types.INTEGER);
                
                rowCount = this.pstmt.executeUpdate();
            }
        } finally {
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        return rowCount;
    }

    @Override
    public int insert(List<User> users) throws SQLException {
        int rowCount = 0;
        try {
        	if(users != null && users.size() != 0) {
            	this.cleanStringBuffer();
            	this.sql.append("INSERT INTO user ");
                this.sql.append("(userName, password, name, email, phone, ");
                this.sql.append("company, country, province, city, street, ");
                this.sql.append("postcode, is_checked, status) ");
                this.sql.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                this.pstmt = this.connection.prepareStatement(this.sql.toString());
                for (int i = 0; i < users.size(); i++) {
                	User user = users.get(i);
                	this.setParameter(1, user.getUsername(), Types.NVARCHAR);
                    this.setParameter(2, user.getPassword(), Types.NVARCHAR);
                    this.setParameter(3, user.getName(), Types.NVARCHAR);
                    this.setParameter(4, user.getEmail(), Types.NVARCHAR);
                    this.setParameter(5, user.getPhone(), Types.NVARCHAR);

                    this.setParameter(6, user.getCompany(), Types.NVARCHAR);
                    this.setParameter(7, user.getCountry(), Types.NVARCHAR);
                    this.setParameter(8, user.getProvince(), Types.NVARCHAR);
                    this.setParameter(9, user.getCity(), Types.NVARCHAR);
                    this.setParameter(10, user.getStreet(), Types.NVARCHAR);

                    this.setParameter(11, user.getPostcode(), Types.NVARCHAR);
                    this.setParameter(12, user.getIsChecked(), Types.BOOLEAN);
                    this.setParameter(13, UserStatus.CREATED.value, Types.INTEGER);
                    
                    this.pstmt.addBatch();
                    this.pstmt.clearParameters();
                }
                int[] results = this.pstmt.executeBatch();
                rowCount = this.sum(results);
        	  }
        	} finally {
        		if(this.pstmt != null) {
        			this.pstmt.close();
        		}
        	}
        return rowCount;
    }

    @Override
    public int delete(Object toDelete) throws SQLException {
        int rowCount = 0;
        try {
            if(toDelete instanceof Integer) {
                this.cleanStringBuffer();
                this.sql.append("DELETE FROM user ");
                this.sql.append("WHERE id = ?");
                this.pstmt = this.connection.prepareStatement(this.sql.toString());
                this.setParameter(1, toDelete, Types.INTEGER);
                rowCount = this.pstmt.executeUpdate();
            }
        } finally {
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        return rowCount;
    }
    
    public int updateUserStatus(Integer id, UserStatus status) throws SQLException {
        int rowCount = 0;
        try {
            this.cleanStringBuffer();
            this.sql.append("UPDATE user ");
            this.sql.append("SET status = ? ");
            this.sql.append("WHERE id = ? ");
            this.pstmt = this.connection.prepareStatement(this.sql.toString());
            this.setParameter(1, status.value, Types.INTEGER);
            this.setParameter(2, id, Types.INTEGER);
            rowCount = this.pstmt.executeUpdate();
        } finally {
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        return rowCount;
    }

    @Override
    public int update(User user) throws SQLException {
        int rowCount = 0;
        try {
            if(user != null) {
                this.cleanStringBuffer();
                this.sql.append("UPDATE user ");
                this.sql.append("SET ");
                this.sql.append("name = ? , ");
                this.sql.append("email = ? , ");
                this.sql.append("phone = ? , ");
                this.sql.append("country = ? , ");
                this.sql.append("province = ? , ");
                this.sql.append("city = ? , ");
                this.sql.append("street = ? , ");
                this.sql.append("postcode = ?, ");
                this.sql.append("company = ?  ");
                this.sql.append("WHERE id = ?  ");
                this.pstmt = this.connection.prepareStatement(this.sql.toString());
                this.setParameter(1, user.getName(), Types.NVARCHAR);
                this.setParameter(2, user.getEmail(), Types.NVARCHAR);
                this.setParameter(3, user.getPhone(), Types.NVARCHAR);
                this.setParameter(4, user.getCountry(), Types.NVARCHAR);
                this.setParameter(5, user.getProvince(), Types.NVARCHAR);
                this.setParameter(6, user.getCity(), Types.NVARCHAR);
                this.setParameter(7, user.getStreet(), Types.NVARCHAR);
                this.setParameter(8, user.getPostcode(), Types.NVARCHAR);
                this.setParameter(9, user.getCompany(), Types.NVARCHAR);
                this.setParameter(10, user.getId(), Types.INTEGER);
                rowCount = this.pstmt.executeUpdate();
            }
        } finally {
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        return rowCount;
    }
    
    public int updateUserImage(Integer id, String imageName) throws SQLException {
        int rowCount = 0;
        try {
            this.cleanStringBuffer();
            this.sql.append("UPDATE user ");
            this.sql.append("SET ");
            this.sql.append("image_name = ? ");
            this.sql.append("WHERE id = ?  ");
            this.pstmt = this.connection.prepareStatement(this.sql.toString());
            this.setParameter(1, imageName, Types.NVARCHAR);
            this.setParameter(2, id, Types.INTEGER);
            rowCount = this.pstmt.executeUpdate();
        } finally {
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        return rowCount;
    }

    public int resetPassword(String username, String password) throws SQLException {
        int rowCount = 0;
        try {
            this.cleanStringBuffer();
            this.sql.append("UPDATE user ");
            this.sql.append("SET ");
            this.sql.append("password = ?  ");
            this.sql.append("WHERE username = ?  ");
            this.pstmt = this.connection.prepareStatement(this.sql.toString());
            this.setParameter(1, password, Types.NVARCHAR);
            this.setParameter(2, username, Types.NVARCHAR);
            rowCount = this.pstmt.executeUpdate();

        } finally {
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        return rowCount;	
    }

    @Override
    public User query(Object toQuery) throws SQLException, NoSuchFieldException, IllegalAccessException {
    	User user = null;
    	if (toQuery instanceof Integer) {
    		Integer id = (Integer)toQuery;
    		user = query(id, Constant.LOCALE_ENG);
    	}
    	else if (toQuery instanceof String) {
    		String username = (String)toQuery;
    		user = query(username, Constant.LOCALE_ENG);
    	}
        return user;
    }
    
    public User query(Object toQuery, String _locale) throws SQLException, NoSuchFieldException, IllegalAccessException {
    	String locale = null;
    	if (_locale == null)
    		locale = Constant.LOCALE_DEFAULT;
    	else
    		locale = _locale;
    	User user = null;
    	if (toQuery instanceof Integer) {
    		Integer id = (Integer)toQuery;
    		user = query(id, locale);
    	}
    	else if (toQuery instanceof String) {
    		String username = (String)toQuery;
    		user = query(username, locale);
    	}
        return user;
    }

    public User query(Integer id, String _locale) throws SQLException, NoSuchFieldException, IllegalAccessException {
    	String locale = null;
    	if (_locale == null)
    		locale = Constant.LOCALE_DEFAULT;
    	else
    		locale = _locale;
    	User user = null;
        try {
            this.cleanStringBuffer();
            this.sql.append("SELECT * FROM user WHERE id = ? ");
            this.pstmt = this.connection.prepareStatement(this.sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            this.setParameter(1, id, Types.INTEGER);
            this.rs = this.pstmt.executeQuery();
            if(rs.next()) {
                user = new User();
                user.fromResultSet(this.rs);
            }
        } finally {
            if(this.rs != null) {
                this.rs.close();
            }
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        
        if(null != user)
        {            
            List<Role> roles = queryUserRoles(id, locale);
            user.setRoles(roles);

            List<DiagnosisDatabase> diagnosisDatabase = queryUserDiagnosisDatabases(id);
            user.setDiagnosisDatabases(diagnosisDatabase);
        }

        return user;
    }

    public User query(String username, String _locale) throws SQLException, NoSuchFieldException, IllegalAccessException {
    	String locale = null;
    	if (_locale == null)
    		locale = Constant.LOCALE_DEFAULT;
    	else
    		locale = _locale;
    	User user = null;
        try {
            this.cleanStringBuffer();
          	this.sql.append("SELECT * FROM user WHERE username = ?");
            this.pstmt = this.connection.prepareStatement(this.sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            this.setParameter(1, username, Types.NVARCHAR);
            this.rs = this.pstmt.executeQuery();
            if(rs.next()) {
                user = new User();
                user.fromResultSet(this.rs);
            }
        } finally {
            if(this.rs != null) {
                this.rs.close();
            }
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        
        if(null != user)
        {            
            List<Role> roles = queryUserRoles(user.getId(), locale);
            user.setRoles(roles);

            List<DiagnosisDatabase> diagnosisDatabase = queryUserDiagnosisDatabases(user.getId());
            user.setDiagnosisDatabases(diagnosisDatabase);
        }
        return user;
    }
    
    public List<User> search(String username, PageUtil page, String _locale) throws SQLException, NoSuchFieldException, IllegalAccessException {
    	String locale = null;
    	if (_locale == null)
    		locale = Constant.LOCALE_DEFAULT;
    	else
    		locale = _locale;
    	User user = null;
    	List<User> listUser = null;
        try {
            this.cleanStringBuffer();
          	this.sql.append("SELECT * FROM user WHERE username LIKE ? ");
            this.pstmt = this.connection.prepareStatement(this.sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            if(null != username)
            	this.pstmt.setString(1, "%" + username + "%");
            
            this.pstmt.setMaxRows(page.getEndIndex());
            this.rs = this.pstmt.executeQuery();
            if (page.getBeginIndex() > 0) {
                this.rs.absolute(page.getBeginIndex());    
				}
            
            if(rs.next())
            {
            	listUser = new ArrayList<User>();
            	do
            	{
            		user = new User();
            		user.fromResultSet(this.rs);
            		listUser.add(user);
            	}while(rs.next());                
            }
        } finally {
            if(this.rs != null) {
                this.rs.close();
            }
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }        

        if(null != listUser)
        {         
        	for(int i=0;i<listUser.size();i++)
        	{        		
	            List<Role> roles = queryUserRoles(listUser.get(i).getId(), locale);
	            listUser.get(i).setRoles(roles);
	
	            List<DiagnosisDatabase> diagnosisDatabase = queryUserDiagnosisDatabases(listUser.get(i).getId());
	            listUser.get(i).setDiagnosisDatabases(diagnosisDatabase);
        	}
        }
        
        return listUser;
    }

    public List<Role> queryUserRoles(Integer id, String _locale) throws SQLException, NoSuchFieldException, IllegalAccessException {
    	String locale = null;
    	if (_locale == null)
    		locale = Constant.LOCALE_DEFAULT;
    	else
    		locale = _locale;
    	
    	List<Role> roles = new ArrayList<Role>();
        try {

            this.cleanStringBuffer();
            this.sql.append("SELECT role.name, role_global.* from user_role ");
            this.sql.append("LEFT JOIN role on user_role.role_id = role.id ");
            this.sql.append("LEFT JOIN role_global on role.id = role_global.id ");
            this.sql.append("WHERE user_role.user_id = ? and role_global.locale = ? ");
            this.pstmt = this.connection.prepareStatement(this.sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            this.pstmt.setInt(1, id);
            this.pstmt.setString(2, locale);
            this.rs = this.pstmt.executeQuery();
            while(rs.next()) {
                Role role = new Role();
                role.fromResultSet(this.rs);
                roles.add(role);
            }
        } finally {
            if(this.rs != null) {
                this.rs.close();
            }
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        
        return roles;
    }

    public List<DiagnosisDatabase> queryUserDiagnosisDatabases(Integer id) throws SQLException, NoSuchFieldException, IllegalAccessException {
    	List<DiagnosisDatabase> diagnosisDatabases = new ArrayList<DiagnosisDatabase>();
        try {

            this.cleanStringBuffer();
            this.sql.append("SELECT diagnosis_database.* from user_diagnosis_database ");
            this.sql.append("LEFT JOIN diagnosis_database on user_diagnosis_database.diagnosis_database_id = diagnosis_database.id ");
            this.sql.append("WHERE  user_diagnosis_database.user_id = ? ");
            this.pstmt = this.connection.prepareStatement(this.sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            this.pstmt.setInt(1, id);
            this.rs = this.pstmt.executeQuery();
            while(rs.next()) {
            	DiagnosisDatabase diagnosisDatabase = new DiagnosisDatabase();
            	diagnosisDatabase.fromResultSet(this.rs);
            	diagnosisDatabases.add(diagnosisDatabase);
            }
        } finally {
            if(this.rs != null) {
                this.rs.close();
            }
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        
        return diagnosisDatabases;
    }

    public User queryUserByPhone(String phone) throws SQLException, NoSuchFieldException, IllegalAccessException {
        User user =  null;
        try {
            if(phone != null) {
                this.cleanStringBuffer();
                this.sql.append("SELECT * ");
                this.sql.append("FROM user ");
                this.sql.append("WHERE Phone = ? ");
                this.pstmt = this.connection.prepareStatement(this.sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                this.pstmt.setString(1, phone);
                this.rs = this.pstmt.executeQuery();
                boolean isFirst = true;
                while(rs.next()) {
                    if(isFirst) {
                        user = new User();
                        user.fromResultSet(this.rs);
                        isFirst = false;
                    }
                }
            }
        } finally {
            if(this.rs != null) {
                this.rs.close();
            }
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        return user;
    }

    public List<User> query(PageUtil page, String _locale) throws SQLException, NoSuchFieldException, IllegalAccessException {
    	String locale = null;
    	if (_locale == null)
    		locale = Constant.LOCALE_DEFAULT;
    	else
    		locale = _locale;
    	
    	List<User> listUser = null;
    	User user = null;
        try {
            this.cleanStringBuffer();
            this.sql.append("SELECT * ");
            this.sql.append("FROM User ");
            
            //this.sql.append("ORDER BY id DESC ");
            //PageUtil.setPaging(this.sql, pageNumber, pageSize);
            
            this.pstmt = this.connection.prepareStatement(this.sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            this.pstmt.setMaxRows(page.getEndIndex());      
            this.rs = this.pstmt.executeQuery();
            if (page.getBeginIndex() > 0) {
                this.rs.absolute(page.getBeginIndex());    
				}
            
            if(rs.next())
            {
            	listUser =  new ArrayList<User>();
            	do 
            	{
	                user = new User();
	                user.fromResultSet(this.rs);                
	                listUser.add(user);            		
            	} while(rs.next());
            }
        } finally {
            if(this.rs != null) {
                this.rs.close();
            }
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        if(null != listUser)
        {
        	for(int i=0;i<listUser.size();i++)
        	{        		
	            List<Role> roles = queryUserRoles(listUser.get(i).getId(), locale);
	            listUser.get(i).setRoles(roles);
	
	            List<DiagnosisDatabase> diagnosisDatabase = queryUserDiagnosisDatabases(listUser.get(i).getId());
	            listUser.get(i).setDiagnosisDatabases(diagnosisDatabase);
        	}
        }
        
        return listUser;
    }
    
    public Integer count() throws SQLException, NoSuchFieldException, IllegalAccessException {
	    Integer count = count(null);
	    return count;
    }

    public Integer count(Calendar calendar) throws SQLException, NoSuchFieldException, IllegalAccessException {
        Integer count = 0;
        String dateString = getDateString(calendar);
        try {
            this.cleanStringBuffer();
            this.sql.append("SELECT count(*) ");
            this.sql.append("FROM `User` ");
            if (dateString != null)
            	this.sql.append("Where CreatedDateTime < '" + dateString + "' ");
            this.pstmt = this.connection.prepareStatement(this.sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            this.rs = this.pstmt.executeQuery();
            if(rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if(this.rs != null) {
                this.rs.close();
            }
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        return count;
    }

    public void countByRole(ArrayList<Object> labels, ArrayList<Object> data) throws SQLException, NoSuchFieldException, IllegalAccessException {
        try {
            this.cleanStringBuffer();
            //this.sql.append("SELECT addressType, count(distinct(company)) ");
            this.sql.append("SELECT b.roleName, count(*) ");
            this.sql.append("FROM User a ");
            this.sql.append("LEFT JOIN user_role b ");
            this.sql.append("on a.UserName = b.UserName ");
            this.sql.append("GROUP by b.roleName ");
            this.pstmt = this.connection.prepareStatement(this.sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            this.rs = this.pstmt.executeQuery();
            while(rs.next()) {
                labels.add(rs.getString(1));
                data.add(rs.getInt(2));
            }
        } finally {
            if(this.rs != null) {
                this.rs.close();
            }
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        return;
    }
    
    public void recordUserLogin(Integer id) throws SQLException{
        try {
            this.cleanStringBuffer();
            this.sql.append("UPDATE user ");
            this.sql.append("SET atetime_last_login = ? ");
            this.sql.append("AND number_of_login = number_of_login +1 ");
            this.sql.append("WHERE id = ? ");
            this.pstmt = this.connection.prepareStatement(this.sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            Calendar calendar = Calendar.getInstance();
            this.pstmt.setTimestamp(1, new Timestamp(calendar.getTimeInMillis()));
            this.pstmt.setInt(2, id);
            this.pstmt.executeUpdate();
        } finally {
            if(this.rs != null) {
                this.rs.close();
            }
            if(this.pstmt != null) {
                this.pstmt.close();
            }
        }
        return;    	
    }
    
    private String getDateString(Calendar calendar) {
    	if(calendar == null)
    		return null;
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    String dateString = "" + year + "-" + (month+1) + "-" + "01";
	    return dateString;
    }
}
