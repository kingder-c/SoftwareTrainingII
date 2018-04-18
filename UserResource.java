package com.siemens.mc.visa.restful;

import com.siemens.mc.visa.javabean.RestOutput;
import com.siemens.mc.visa.javabean.User;
import com.siemens.mc.visa.restful.UserResource;
import com.siemens.mc.visa.common.Constant;
import com.siemens.mc.visa.common.MessageResource;
import com.siemens.mc.visa.javabean.*;
import com.siemens.mc.visa.service.UserService;
import com.siemens.mc.visa.utils.FileUtil;
import com.siemens.mc.visa.utils.PageUtil;
import com.siemens.mc.visa.utils.PathUtil;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/12/13.
 */

@Path("users")
public class UserResource {
    private static final Logger logger = LogManager.getLogger(UserResource.class);

	/**
	* @api {post} /restfulapi/users insertUser
	* @apiVersion 0.1.0
	* @apiName insertUser
	* @apiGroup users
	*
	* @apiDescription This method insert a new user to visa data base.
	* @apiParam {String} username The new user's registry name. (necessary parameter)
	* @apiParam {String} password The new user's password. (necessary parameter)
	* @apiParam {String} confirmPassword Again the new user's password to compare. (necessary parameter)
	* @apiParam {String} name The new user's real name.	(necessary parameter)
	* @apiParam {String} email The new user's email address. (necessary parameter)
	* @apiParam {String} phone The new user's phone number. (necessary parameter)
	* @apiParam {String} company The new user's company.
	* @apiParam {String} country The new user's country.
	* @apiParam {String} province The new user's province.
	* @apiParam {String} city The new user's city.
	* @apiParam {String} address The new user's street address.
	* @apiParam {String} postcode The new user's post code. (necessary parameter)
	*/  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("text/plain")
    public String addUser(@Context HttpServletRequest request,
                             @Context HttpServletResponse response,
                             @Context ServletContext servletContext,
                             @Context SecurityContext securityContext,
                             @FormParam("username") String username, 
                             @FormParam("password") String password,
                             @FormParam("confirmPassword") String confirmPassword,
                             @FormParam("name") String name,
                             @FormParam("email") String email,
                             @FormParam("phone") String phone,
                             @FormParam("company") String company,
                             @FormParam("country") String country,
                             @FormParam("province") String province,
                             @FormParam("city") String city,
                             @FormParam("street") String street,
                             @FormParam("postcode") String postcode
                             ){
        logger.info("Enter addUser");
        RestOutput restOutput = new RestOutput();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setContentType("text/plain; charset=utf-8");
        try {
            if(password == null || !password.equals(confirmPassword)) {
                restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
                restOutput.setDescription("Password is not valid.");
            }
            else if( phone == null || phone.length()==0){
                restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
                restOutput.setDescription("Phone number can not be null");
            }
            else {
                UserService userService = new UserService();
                User temp = userService.getUserByPhone(phone); 
                if(  temp != null  ){	
                    restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
                    restOutput.setDescription("The phone number already exists.");
                }
                else if ((temp = userService.getUser(username)) != null) {
                    restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
                    restOutput.setDescription("The username already exists.");                	
                }
                else {
	                User user = new User();
	                user.setUsername(username);
	                user.setPassword(password);
	                user.setName(name);
	                user.setEmail(email);
	                user.setPhone(phone);
	                user.setCompany(company);
	                user.setCountry(country);
	                user.setProvince(province);
	                user.setCity(city);
	                user.setStreet(street);
	                user.setPostcode(postcode);

	                userService.insertUser(user);
                }            	
            }
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug(restOutput.toJsonString());
        logger.info("Leave addUser");
        return restOutput.toJsonString();
    }
    
	/**
	* @api {delete} /restfulapi/users/{id} deleteUser
	* @apiVersion 0.1.0
	* @apiName deleteUser
	* @apiGroup users
	*
	* @apiDescription This method mark a exist user to 'DELETE' status in visa data base.
	* @apiParam {Integer} id The user's id for delete.
    */
    @Path("{id}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(@Context HttpServletRequest request,
                          @Context HttpServletResponse response,
                          @Context ServletContext servletContext,
                          @Context SecurityContext securityContext,
                          @PathParam("id") Integer id) {
        logger.info("Enter deleteUser");
        RestOutput restOutput = new RestOutput();
        try {
            UserService userService = new UserService();
            //userService.deleteUser(id);
            if(userService.isAdministrator(request))
            	userService.disableUser(id);
            else
            {
                restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
                restOutput.setDescription("User hasn't access right!");
            }
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug(restOutput.toJsonString());
        logger.info("Leave deleteUser");
        return restOutput.toJsonString();
    }
    
	/**
	* @api {get} /restfulapi/users/{id} getUser
	* @apiVersion 0.1.0
	* @apiName getUser
	* @apiGroup users
	*
	* @apiDescription This method get an exist user from visa data base.
	* @apiParam {Integer} id The user's id for get.
	*/  
    @Path("{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUser(@Context HttpServletRequest request,
                          @Context HttpServletResponse response,
                          @Context ServletContext servletContext,
                          @Context SecurityContext securityContext,
                          @PathParam("id") Integer id) {
        logger.info("Enter getUser");
        RestOutput restOutput = new RestOutput();
        try {
            UserService userService = new UserService();
            User user = null;            
          	user = userService.getUser(id);
            if (null != user){
                restOutput.setDataArray(user.toJsonArray());
            }
            else{
                restOutput.setStatus(Constant.ResponseStatus.INVALIDUSERNAME.getValue());
                restOutput.setDescription(MessageResource.getString("user.invalid.username"));
            }
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug(restOutput.toJsonString());
        logger.info("Leave getUser");
        return restOutput.toJsonString();
    }
    
    /**
	* @api {get} /restfulapi/users getUsers
	* @apiVersion 0.1.0
	* @apiName getUsers
	* @apiGroup users
	*
	* @apiDescription This method get group exist users from visa data base.
	* @apiParam {String} username The user's registry name string for search. 'null' means search all.
	* @apiParam {Boolean} fuzzyFlag The flag for fuzzy search base on precondition(username ...).
	* @apiParam {Integer} pageNumber The max pages for get a user list. Default is 1 page. if the user'id, phone and username are all null, pagenumber will be used for multiple get. 
	* @apiParam {Integer} pageSize The max users in one page for get a user list. Default is PageUtil.USER_PAGE_SIZE. if the user'id, phone and username are all null, pagesize will be used for multiple get.
	* @apiParam {String} locale The locale position , default is Constant.LOCALE_ENG.
	*/  
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUsers(@Context HttpServletRequest request,
                          @Context HttpServletResponse response,
                          @Context ServletContext servletContext,
                          @Context SecurityContext securityContext,
                          @QueryParam("username") String username,
                          @QueryParam("fuzzyFlag") @DefaultValue("false") Boolean fuzzyFlag,
                          @QueryParam("pageNumber") Integer pageNumber,
                          @QueryParam("pageSize") Integer pageSize,
                          @QueryParam("locale") @DefaultValue(Constant.LOCALE_ENG) String locale ) {
        logger.info("Enter getUsers");
        RestOutput restOutput = new RestOutput();
        try {
            UserService userService = new UserService();
            List<User> users = null;
            users = userService.getUsers(username, fuzzyFlag, pageNumber, pageSize, locale);
            
            if(null != users){
            	restOutput.setDataArray(User.toJsonObject(users));
            }
            else{
                restOutput.setStatus(Constant.ResponseStatus.INVALIDUSERNAME.getValue());
                restOutput.setDescription(MessageResource.getString("user.invalid.username"));
            }
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug(restOutput.toJsonString());
        logger.info("Leave getUsers");
        return restOutput.toJsonString();
    }
   
	/**
	* @api {put} /restfulapi/users/{id} updateUser
	* @apiVersion 0.1.0
	* @apiName updateUser
	* @apiGroup users
	*
	* @apiDescription This method update the exist user in visa data base.
	* @apiParam {Integer} id The exist user's id. (necessary parameter)
	* @apiParam {String} name The new user's real name.
	* @apiParam {String} email The new user's email address.
	* @apiParam {String} phone The new user's phone number.
	* @apiParam {String} company The new user's company.
	* @apiParam {String} country The new user's country.
	* @apiParam {String} province The new user's province.
	* @apiParam {String} city The new user's city.
	* @apiParam {String} address The new user's street address.
	* @apiParam {String} postcode The new user's post code.
	*/ 
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateUser(
    		@Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @Context ServletContext servletContext,
            @Context SecurityContext securityContext,
            @PathParam("id") Integer id,
            @FormParam("name") String name,
            @FormParam("email") String email,
            @FormParam("phone") String phone,
            @FormParam("company") String company,
            @FormParam("country") String country,
            @FormParam("province") String province,
            @FormParam("city") String city,
            @FormParam("street") String street,
            @FormParam("postcode") String postcode) {
        logger.info("Enter updateUser");
        RestOutput restOutput = new RestOutput();
        try {
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setCountry(country);
            user.setProvince(province);
            user.setCity(city);
            user.setStreet(street);
            user.setPostcode(postcode);
            user.setCompany(company);
            UserService userService = new UserService();
            userService.updateUser(user);
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug(restOutput.toJsonString());
        logger.info("Leave updateUser");
        return restOutput.toJsonString();
    }

	/**
	* @api {put} /restfulapi/users/{id}/roles updateUserRoles
	* @apiVersion 0.1.0
	* @apiName updateUserRoles
	* @apiGroup users
	*
	* @apiDescription This method update the exist user's related roles.
	* @apiParam {Integer} id The exist user's id.
	* @apiParam {Integer[]} roleIDs The roles' list.
	*/ 
    @Path("{id}/roles")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateUserRoles(
    		@Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @Context ServletContext servletContext,
            @Context SecurityContext securityContext,
            @PathParam("id") Integer id,
            @FormParam("roles") List<Integer> roleIDs) {
        logger.info("Enter updateUserRoles");
        RestOutput restOutput = new RestOutput();
        try {
            UserService userService = new UserService();
            userService.updateUserRoles(id, roleIDs);
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug(restOutput.toJsonString());
        logger.info("Leave updateUserRoles");
        return restOutput.toJsonString();
    }

	/**
	* @api {put} /restfulapi/users/{id}/diagnosisdatabases updateUserDiagnosisDatabases
	* @apiVersion 0.1.0
	* @apiName updateUserDiagnosisDatabases
	* @apiGroup users
	*
	* @apiDescription This method update the exist user's related diagnose data base.
	* @apiParam {Integer} id The exist user's id.
	* @apiParam {Integer[]} diagnosisDatabaseIDs The diagnosis data base's ID list.
	*/ 
    @Path("{id}/diagnosisdatabases")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateUserDiagnosisDatabases(
    		@Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @Context ServletContext servletContext,
            @Context SecurityContext securityContext,
            @PathParam("id") Integer id,
            @FormParam("diagnosisDatabases") List<Integer> diagnosisDatabaseIds) {
        logger.info("Enter updateUserDiagnosisDatabases");
        RestOutput restOutput = new RestOutput();
        try {
            UserService userService = new UserService();
            userService.updateUserDiagnosisDatabases(id, diagnosisDatabaseIds);
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug(restOutput.toJsonString());
        logger.info("Leave updateUserDiagnosisDatabases");
        return restOutput.toJsonString();
    }    

	/**
	* @api {put} /restfulapi/users/{id}/image updateUserImage
	* @apiVersion 0.1.0
	* @apiName updateUserDiagnosisDatabases
	* @apiGroup users
	*
	* @apiDescription This method update the exist user's related diagnose data base.
	* @apiParam {Integer} id The exist user's id.
	* @apiParam {InputStream} imageInputStream to be add description
	* @apiParam {FormDataContentDisposition} imageDisposition to be add description
	*/ 
    @Path("{id}/image")
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateUserImage(@Context HttpServletRequest request,
                          @Context HttpServletResponse response,
                          @Context ServletContext servletContext,
                          @Context SecurityContext securityContext,
                          FormDataMultiPart form,
                          @PathParam("id") Integer id,
                          @FormDataParam("image") InputStream imageInputStream,
                          @FormDataParam("image") FormDataContentDisposition imageDisposition) {
    	logger.info("Enter updateUserImage");
        RestOutput restOutput = new RestOutput();
        try {
        	String imageName = null;
        	imageName = FileUtil.saveImage(imageInputStream, imageDisposition, PathUtil.USER_IMAGE_FOLDER, imageName, 0);
	    	UserService userService = new UserService();
	    	userService.updateUserImage(id, imageName);
	    } catch (Exception e) {
	        logger.error("", e);
	        restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
	        restOutput.setDescription(e.toString());
	    }
	    System.out.println(restOutput.toJsonString());
	    logger.info("Leave updateUserImage");
	    return restOutput.toJsonString();
    }

	/**
	* @api {get} /restfulapi/users/image/{fileName} getUserImage
	* @apiVersion 0.1.0
	* @apiName getUserImage
	* @apiGroup users
	*
	* @apiDescription This method update the exist user's related diagnose data base.
	* @apiParam {String} fileName The user's image resource file name in database
	*/
    @Path("image/{fileName}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public HttpServletResponse getUserImage(@Context HttpServletRequest request,
                                    @Context HttpServletResponse response,
                                    @Context ServletContext servletContext,
                                    @Context SecurityContext securityContext,
                                    @PathParam("fileName") String fileName) {
        logger.info("Enter getUserImage");
        try {
            String fileFullPath = PathUtil.mergePath(PathUtil.getStoragePath() + File.separator + PathUtil.USER_IMAGE_FOLDER, fileName);
            File file = new File(fileFullPath);
            response.addHeader("Content-Length", "" + file.length());
            FileUtils.copyFile(file, response.getOutputStream());
        } catch (Exception e) {
            logger.error("", e);
        }
        logger.info("Leave getUserImage");
        return response;
    }

	/**
	* @api {get} /restfulapi/users/count getCount
	* @apiVersion 0.1.0
	* @apiName getCount
	* @apiGroup users
	*
	* @apiDescription This method returns the number of indicated users by role
	* @apiParam {String} byRole The users' role, null for total users.
	*/    
    @Path("count")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount(@Context HttpServletRequest request,
                            @Context HttpServletResponse response,
                            @Context ServletContext servletContext,
                            @Context SecurityContext securityContext, 
                            @QueryParam("byRole") String byRole  
                            ) {
        logger.info("Enter getCount");
        RestOutput restOutput = new RestOutput();
        try {
            UserService userService = new UserService();
            restOutput.setDataJson(userService.getUserCountJson(byRole));
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug("output:" + restOutput.toJsonString());
        logger.info("Leave getCount");
        return restOutput.toJsonString();
    }

	/**
	* @api {post} /restfulapi/users/{id}/status enableUser
	* @apiVersion 0.1.0
	* @apiName enableUser
	* @apiGroup users
	*
	* @apiDescription This method set a exist user to CREATE status.
	* @apiParam {Integer} id The user's id for enable.
    */
    @Path("{id}/status")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String enableUser(@Context HttpServletRequest request,
                          @Context HttpServletResponse response,
                          @Context ServletContext servletContext,
                          @Context SecurityContext securityContext,
                          @PathParam("id") Integer id) {
        logger.info("Enter enableUser");
        RestOutput restOutput = new RestOutput();
        try {
            UserService userService = new UserService();
            userService.enableUser(id);
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug(restOutput.toJsonString());
        logger.info("Leave enableUser");
        return restOutput.toJsonString();
    }
    
	/**
	* @api {delete} /restfulapi/users/{id}/status disableUser
	* @apiVersion 0.1.0
	* @apiName disableUser
	* @apiGroup users
	*
	* @apiDescription This method set a exist user to invalid status. The relationship in other table will be kept.
	* @apiParam {Integer} id The user's id for disable.
    */
    @Path("{id}/status")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String disableUser(@Context HttpServletRequest request,
                          @Context HttpServletResponse response,
                          @Context ServletContext servletContext,
                          @Context SecurityContext securityContext,
                          @PathParam("id") Integer id) {
        logger.info("Enter disableUser");
        RestOutput restOutput = new RestOutput();
        try {
            UserService userService = new UserService();
            userService.disableUser(id);
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug(restOutput.toJsonString());
        logger.info("Leave disableUser");
        return restOutput.toJsonString();
    }
    
	/**
	* @api {update} /restfulapi/users/{id}/status approveUser
	* @apiVersion 0.1.0
	* @apiName approveUser
	* @apiGroup users
	*
	* @apiDescription This method set a exist user to APPROVE status.
	* @apiParam {Integer} id The user's id for approve.
    */
    @Path("{id}/status")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String approveUser(@Context HttpServletRequest request,
                          @Context HttpServletResponse response,
                          @Context ServletContext servletContext,
                          @Context SecurityContext securityContext,
                          @PathParam("id") Integer id) {
        logger.info("Enter approveUser");
        RestOutput restOutput = new RestOutput();
        try {
            UserService userService = new UserService();
            userService.approveUser(id);
        } catch (Exception e) {
            logger.error("", e);
            restOutput.setStatus(Constant.ResponseStatus.FAILED.getValue());
            restOutput.setDescription(e.toString());
        }
        logger.debug(restOutput.toJsonString());
        logger.info("Leave approveUser");
        return restOutput.toJsonString();
    }

}
