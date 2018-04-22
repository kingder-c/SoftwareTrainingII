package com.siemens.mc.visa.javabean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.siemens.mc.visa.common.Constant;
import com.siemens.mc.visa.utils.SecurityUtil;
import com.siemens.mc.visa.utils.TextUtil;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by admin on 2017/12/13.
 */
public class User extends BaseBean {

	private Integer id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String company;
    private String country;
    private String province;
    private String city;
    private String street;
    private String postcode;
    private String imageName;
    private Timestamp createdDateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
    private List<Role> roles;
    private List<DiagnosisDatabase> diagnosisDatabases;
    private List<MachineTool> machineTools;
    //whether the user's account is authorzied.  
    private Integer status;
    private Boolean isChecked;
    
    public void fromResultSet(ResultSet rs) throws SQLException, NoSuchFieldException, IllegalAccessException {
    	this.setValue(rs, "id", Types.INTEGER, "id");
        this.setValue(rs, "username", Types.NVARCHAR, "username");
        this.setValue(rs, "password", Types.NVARCHAR, "password");
        this.setValue(rs, "name", Types.NVARCHAR, "name");
        this.setValue(rs, "email", Types.NVARCHAR, "email");
        this.setValue(rs, "phone", Types.NVARCHAR, "phone");
        this.setValue(rs, "country", Types.NVARCHAR, "country");
        this.setValue(rs, "province", Types.NVARCHAR, "province");
        this.setValue(rs, "city", Types.NVARCHAR, "city");
        this.setValue(rs, "street", Types.NVARCHAR, "street");
        this.setValue(rs, "postcode", Types.NVARCHAR, "postcode");
        this.setValue(rs, "company", Types.NVARCHAR, "company");
        this.setValue(rs, "image_name", Types.NVARCHAR, "imageName");
        this.setValue(rs, "datetime_create", Types.TIMESTAMP, "createdDateTime");
        this.setValue(rs, "status", Types.INTEGER, "status");
        this.setValue(rs, "is_checked", Types.BOOLEAN, "isChecked");
    }

    //updated
    public ObjectNode toJsonObject() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();
        root.put("id", this.id);
        root.put("username", this.username);
        root.put("password", this.password);
        root.set("roles", Role.toJsonObject(roles));
        root.set("diagnosisDatabases", DiagnosisDatabase.toJsonObject(diagnosisDatabases));        
        root.put("name", this.name);
        root.put("email", this.email);
        root.put("phone", this.phone);
        root.put("country", this.country);
        root.put("province", this.province);
        root.put("city", this.city);
        root.put("street", this.street);
        root.put("postcode", this.postcode);
        root.put("company", this.company);
        root.put("imageName", this.imageName);
        root.put("createdDateTime", TextUtil.formatDate(this.createdDateTime));
        root.put("status", this.status);
        root.put("isChecked", this.isChecked);
        return root;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;
        if (this == object) {
            isEqual = true;
        } else if (object == null) {
            isEqual = false;
        } else if (object instanceof User) {
            User user = (User)object;
            if(user.username.equals(this.username)) {
                isEqual = true;
            } else {
                isEqual = false;
            }
        } else {
            isEqual = false;
        }
        return isEqual;
    }
    
    public Integer getId() {
 		return id;
 	}

 	public void setId(Integer id) {
 		this.id = id;
 	}
    
    public String getUsername() {
        return username;
    }

	public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Timestamp getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Timestamp createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public List<DiagnosisDatabase> getDiagnosisDatabases() {
        return diagnosisDatabases;
    }

    public void setDiagnosisDatabases(List<DiagnosisDatabase> diagnosisDatabases) {
        this.diagnosisDatabases = diagnosisDatabases;
    }
    
    public List<MachineTool> getMachineTools() {
        return machineTools;
    }

    public void setMachineTools(List<MachineTool> machineTools) {
        this.machineTools = machineTools;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
}
