package com.siemens.mc.visa.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.siemens.mc.visa.common.Constant;
import com.siemens.mc.visa.common.Constant.UserStatus;
import com.siemens.mc.visa.javabean.Role;
import com.siemens.mc.visa.javabean.StatisticResult;
import com.siemens.mc.visa.javabean.User;
import com.siemens.mc.visa.javabean.UserRankTotal;
import com.siemens.mc.visa.javabean.UserRole;
import com.siemens.mc.visa.utils.FileUtil;
import com.siemens.mc.visa.utils.PageUtil;
import com.siemens.mc.visa.utils.PathUtil;
import com.siemens.mc.visa.utils.WebUtil;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.core.Context;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

/**
 * Created by admin on 2017/12/13.
 */
public class UserService extends BaseService {

    public UserService() {
        super();
    }

    public void insertUser(User user) throws NamingException, SQLException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();
            user.setStatus(UserStatus.CREATED.value);
            this.userDao.insert(user);
            this.connection.commit();
        } catch (SQLException e) {
            if (this.connection != null) {
                this.connection.rollback();
            }
            throw e;
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }

    public void deleteUser(Integer id) throws NamingException, SQLException, NoSuchFieldException, IllegalAccessException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();

            //check if the image name is null
            User user = this.userDao.query(id);
            
            String imageName = user.getImageName();

            this.userRoleDao.delete(user.getId());
            this.userDiagnosisDatabaseDao.delete(user.getId());
            this.userDao.delete( id);
            this.connection.commit();
            
            //delete the image file
            if(imageName != null) {
                FileUtil.deleteFile(PathUtil.mergePath(PathUtil.getStoragePath() + File.separator + PathUtil.USER_IMAGE_FOLDER, imageName));
            }           

        } catch (SQLException e) {
            if (this.connection != null) {
                this.connection.rollback();
            }
            throw e;
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }
    
    public void deleteUser(String username) throws NamingException, SQLException, NoSuchFieldException, IllegalAccessException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();

            //check if the image name is null
            User user = this.userDao.query(username);

			String imageName = user.getImageName();
			this.userRoleDao.delete(user.getId());
			this.userDiagnosisDatabaseDao.delete(user.getId());
			this.userDao.delete(user.getId());
			this.connection.commit();

			// delete the image file
			if (imageName != null)
			{
				FileUtil.deleteFile(PathUtil.mergePath(
						PathUtil.getStoragePath() + File.separator + PathUtil.USER_IMAGE_FOLDER, imageName));
			}

        } catch (SQLException e) {
            if (this.connection != null) {
                this.connection.rollback();
            }
            throw e;
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }
    
    public void disableUser(Integer id) throws NamingException, SQLException, NoSuchFieldException, IllegalAccessException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();
            this.userDao.updateUserStatus(id, UserStatus.DELETED);
			this.connection.commit();
        } catch (SQLException e) {
            if (this.connection != null) {
                this.connection.rollback();
            }
            throw e;
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }
    
    public void enableUser(Integer id) throws NamingException, SQLException, NoSuchFieldException, IllegalAccessException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();
            this.userDao.updateUserStatus(id, UserStatus.CREATED);
			this.connection.commit();
        } catch (SQLException e) {
            if (this.connection != null) {
                this.connection.rollback();
            }
            throw e;
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }
    
    public void approveUser(Integer id) throws NamingException, SQLException, NoSuchFieldException, IllegalAccessException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();
            this.userDao.updateUserStatus(id, UserStatus.APPROVED);
			this.connection.commit();
        } catch (SQLException e) {
            if (this.connection != null) {
                this.connection.rollback();
            }
            throw e;
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }
    
    public void updateUser(User user) throws NamingException, SQLException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();
            this.userDao.update(user);
            this.connection.commit();
        } catch (SQLException e) {
            if (this.connection != null) {
                this.connection.rollback();
            }
            throw e;
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }

    public void updateUserRoles(Integer id, List<Integer> ListRoleId) throws NamingException, SQLException, NoSuchFieldException, IllegalAccessException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();
            this.userRoleDao.delete(id);
            this.userRoleDao.insert(id, ListRoleId);
            this.connection.commit();            
        } catch (SQLException e) {
            if (this.connection != null) {
                this.connection.rollback();
            }
            throw e;
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }

    public void updateUserDiagnosisDatabases(Integer id, List<Integer> diagnosisDatabseIDs) throws NamingException, SQLException, NoSuchFieldException, IllegalAccessException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();
            this.userDiagnosisDatabaseDao.delete(id);
            this.userDiagnosisDatabaseDao.insert(id, diagnosisDatabseIDs);
            this.connection.commit();            
        } catch (SQLException e) {
            if (this.connection != null) {
                this.connection.rollback();
            }
            throw e;
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }

    public void updateUserImage(Integer id, String imageName) throws NamingException, SQLException, NoSuchFieldException, IllegalAccessException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();
            
            //check if the imagename is null
            User user = this.userDao.query(id);
            String imageNameOld = user.getImageName();
            
            this.userDao.updateUserImage(id , imageName);
            this.connection.commit();
            
            //delete the image file
            if(imageNameOld != null) {
                FileUtil.deleteFile(PathUtil.mergePath(PathUtil.getStoragePath() + File.separator + PathUtil.USER_IMAGE_FOLDER, imageNameOld));
            }
            
        } catch (SQLException e) {
            if (this.connection != null) {
                this.connection.rollback();
            }
            throw e;
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }

    public User getUser(Integer id) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException {
        User user = null;
        try {
            this.getConnection();
            this.connection.setAutoCommit(true);
            this.setDaoConnection();
            user = this.userDao.query(id);
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
        return user;
    }
    
    public User getUser(Integer id, String locale) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException {
        User user = null;
        try {
            this.getConnection();
            this.connection.setAutoCommit(true);
            this.setDaoConnection();
            user = this.userDao.query(id, locale);
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
        return user;
    }

    
    public User getUser(String username) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException {
        User user = null;
        try {
            this.getConnection();
            this.connection.setAutoCommit(true);
            this.setDaoConnection();
            user = this.userDao.query(username);
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
        return user;
    }

    public User getUser(String userName, String locale) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException {
        User user = null;
        try {
            this.getConnection();
            this.connection.setAutoCommit(true);
            this.setDaoConnection();
            user = this.userDao.query(userName, locale);
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
        return user;
    }

    public User getUserByPhone(String phone) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException {
        User user = null;
        try {
            this.getConnection();
            this.connection.setAutoCommit(true);
            this.setDaoConnection();
            user = this.userDao.queryUserByPhone(phone);
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
        return user;
    }
    
    public Object getUser(String userName, String password, String locale) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException {
        Object output = null;
        if (userName == null) {
            output = Constant.ResponseStatus.INVALIDUSERNAME;
        } else if (password == null) {
            output = Constant.ResponseStatus.INVALIDPASSWORD;
        } else {
            try {
                this.getConnection();
                this.connection.setAutoCommit(true);
                this.setDaoConnection();
                User user = this.userDao.query(userName, locale);
                if (user == null) {
                    output = Constant.ResponseStatus.INVALIDUSERNAME;
                } else {
                    if (!password.equals(user.getPassword())) {
                        output = Constant.ResponseStatus.INVALIDPASSWORD;
                    } else {
                        output = user;
                    }
                }
            } finally {
                if (this.connection != null) {
                    this.connection.close();
                    this.connection = null;
                }
            }
        }
        return output;
    }
    
	public User getUser(Integer id, String phone, String username, String locale)
			throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException
	{
		User user = null;
		try
		{
			this.getConnection();
			this.connection.setAutoCommit(true);
			this.setDaoConnection();
			if(null != id)
				user = this.getUser(id);
			else if(null != phone)
				user = this.getUserByPhone(phone);
			else if(null != username)
				user = this.getUser(username, locale);
		} finally
		{
			if (this.connection != null)
			{
				this.connection.close();
				this.connection = null;
			}
		}
		
		return user;
	}
    
    public void resetPassword(String userName, String password) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException {
        try {
            this.getConnection();
            this.connection.setAutoCommit(true);
            this.setDaoConnection();
            this.userDao.resetPassword(userName, password);
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
    }
    
    public void getUserCount(String byRole, ArrayList<Object> labels, ArrayList<Object> data) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException {
        int count = 0;
        try {
            this.getConnection();
            this.connection.setAutoCommit(true);
            this.setDaoConnection();
            if (byRole!= null &&
            		!(byRole.compareToIgnoreCase("false") == 0) &&
            		!(byRole.compareToIgnoreCase("no") == 0) ) {
            	this.userDao.countByRole(labels, data);
            }
            else //
            {
	            count = this.userDao.count();
	            labels.add("total");
	            data.add(count);
            }
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
        return;
    }

    public ObjectNode getUserCountJson(String byRole) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException, IOException {
    	ArrayList<Object> labels = new ArrayList<Object>();
    	ArrayList<Object> data = new ArrayList<Object>();
    	this.getUserCount(byRole, labels, data);
    	StatisticResult sr = new StatisticResult();
    	sr.setLabels(labels.toArray());
    	sr.setData(data.toArray());
        return sr.toJsonObject();
    }
    
    public UserRankTotal recordUserLogin(Integer id) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException{
    	UserRankTotal userRankTotal = new UserRankTotal();
        try {
            this.getConnection();
            this.connection.setAutoCommit(false);
            this.setDaoConnection();
            this.userDao.recordUserLogin(id);
            this.connection.commit();
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
        return userRankTotal;
    }
        
	public List<User> getUsers(String username, Boolean fuzzyFlag, Integer pageNumber, Integer pageSize, String locale)
			throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException
	{
		List<User> users = null;
		try
		{
			this.getConnection();
			this.connection.setAutoCommit(true);
			this.setDaoConnection();

            int count = this.userDao.count();
			if (null == pageNumber)
			{
				pageNumber = 1;
				if (null == pageSize)
					pageSize = count;
			} else
			{
				if (pageSize == null)
					pageSize = PageUtil.USER_PAGE_SIZE;
			}
			PageUtil page = new PageUtil(count, pageNumber, pageSize);
			
			if(null == username)
			{
				users = this.userDao.query(page, locale);
			}
			else
			{
				if(true == fuzzyFlag)
					users = this.userDao.search(username, page, locale);
				else
				{
					User user = this.userDao.query(username, locale);
					if(null != user)
					{
						users = new ArrayList<User>();
						users.add(user);
					}
				}
			}

		} finally
		{
			if (this.connection != null)
			{
				this.connection.close();
				this.connection = null;
			}
		}

		return users;
	}
        
    public List<User> searchUsers(String phone) throws SQLException, NamingException, NoSuchFieldException, IllegalAccessException {
        List<User> users = null;
        try {
            this.getConnection();
            this.connection.setAutoCommit(true);
            this.setDaoConnection();
            //users = this.userDao.queryUserByPhone(phone);            
        } finally {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }
        return users;
    }
    
    public Boolean isAdministrator(HttpServletRequest request)
	{
    	Boolean isAdministrator = false;
		try
		{
			HttpSession session = request.getSession(false);

			if (session != null)
			{
				User user = (User) session.getAttribute("user");
				if (user != null)
					isAdministrator = Role.containsRole(user.getRoles(), Constant.ADMINISTRATOR);
			}
		} catch (Exception e)
		{

		}
		return isAdministrator;
	}
}
