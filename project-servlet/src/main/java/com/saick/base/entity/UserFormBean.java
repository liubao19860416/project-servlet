package com.saick.base.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 表单提交的bean，自己封装了对自己的校验方法
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class UserFormBean implements Serializable {
    private static final long serialVersionUID = 4694846639946425479L;
    private String id;
    private String name;
    private String gender;
    private String cellphone;
    private String email;
    private String type;
    private String description;

    private String birthday;
    private String hobbies[];
    Map<String, String> errors = new HashMap<String, String>();

    @SuppressWarnings("unused")
    public boolean validate() {
        if (name == null || name.equals("")) {
            errors.put("name", "  !");
        } else if (!(name.matches("^[a-zA-Z0-9]{3,16}"))) {
            errors.put("name", " !");
        } else if (cellphone == null || cellphone.equals("")) {
            errors.put("cellphone", "  !");
        } else if (!cellphone.matches("^[1][3,4,5,8][0-9]{3,9}$")) {
            errors.put("cellphone", " !");
        } else if (email == null || email.equals("")) {
            errors.put("email", "  !");
        } else if (!email
                .matches("^[0-9a-zA-Z]{3,16}@[0-9a-zA-Z]{2,3}\\.[a-zA-Z]{2,3}$")) {
            errors.put("email", " !");
        } else if (birthday == null || birthday.equals("")) {
            errors.put("birthday", "  !");
        } else {
            try {
                Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
                // new DateLocaleConverter().convert(birthday);
            } catch (Exception e) {
                errors.put("birthday", " !");
            }
        }
        return errors.isEmpty();
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserFormBean [id=" + id + ", name=" + name + ", gender="
                + gender + ", cellphone=" + cellphone + ", email=" + email
                + ", type=" + type + ", description=" + description
                + ", birthday=" + birthday + ", hobbies="
                + Arrays.toString(hobbies) + ", errors=" + errors + "]";
    }
}
