/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Social;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import javax.servlet.http.Part;

/**
 *
 * @author SONY
 */
@Named(value = "allUsers")
@SessionScoped
@ManagedBean
public class AllUsers implements Serializable {

    private String fullName = UserInfo.getFullName();
    private String userName = UserInfo.getUserName();
    private String phone = UserInfo.getPhone();
    private String email = UserInfo.getEmail();
    private String password = UserInfo.getPassword();
    private String img = UserInfo.getImg();

    private String errMsg = "";
    private String errMsgs = "";
    private String passResetmsg = "";
    private String passResetmsgs = "";

    // Need these attributes for the settings page
    private String currentPassword;
    private String newPassword;
    private String errorMessage;

    //Need these for messages page
    private ArrayList<String> arrUsers;
    private String messageString;
    private String successMsg;
    private String recipient;

    //Need these for image upload
    private Part file;
    private boolean uploaded;

    //Database
    //final String DB_URL = "jdbc:mysql://localhost:3306/twitter";
    //final String DB_USER = "root";
    //final String DB_PASS = "admin123";
    final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/prabhua6510";
    final String DB_USER = "prabhua6510";
    final String DB_PASS = "1441868";

    Connection conn = null;
    Statement stat = null;
    ResultSet rs = null;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsgs() {
        return errMsgs;
    }

    public void setErrMsgs(String errMsgs) {
        this.errMsgs = errMsgs;
    }

    public String getPassResetmsg() {
        return passResetmsg;
    }

    public void setPassResetmsg(String passResetmsg) {
        this.passResetmsg = passResetmsg;
    }

    public String getPassResetmsgs() {
        return passResetmsgs;
    }

    public void setPassResetmsgs(String passResetmsgs) {
        this.passResetmsgs = passResetmsgs;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ArrayList<String> getArrUsers() {
        return arrUsers;
    }

    public void setArrUsers(ArrayList<String> arrUsers) {
        this.arrUsers = arrUsers;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public AllUsers(String fullName, String userName, String phone, String email, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        //this.errMsg = errMsg;
    }

    public AllUsers() {
    }

    public String signUp() {
        String sql = "Select username from allusers where username = '" + userName + "'";
        if (Database.IsRowPresent(sql)) {
            errMsgs = "UserName is not available. Please select another User Name.";
            return "signUp";
        } else if (!Database.IsRowPresent(sql)) {

            sql = "insert into allusers values ('" + fullName + "','" + userName + "', '" + phone + "', '" + email + "','" + password + "')";
            if (Database.InsertUpdate(sql)) {
                //insert default image during registration
                Database.InsertUpdate("insert into user_images values('" + userName + "', 'Images/Display_Picture/default-img.jpg')");
                System.out.println("Account Created Successfully");
            } else {
                System.out.println("Account creation FAILED.Please try again.");
            }
        }
        return "index";
    }

    public String login() {
        String page = "";
        String sql = "Select 1 from allusers where username = '" + userName + "'";
        passResetmsg = "";

        if (!Database.IsRowPresent(sql)) {
            errMsg = "The username and password you entered did not match our records. Please double-check and try again.";
            page = "login";
        }
        if (Database.IsRowPresent(sql)) {

            sql = "Select 1 from allusers where UserName = '" + userName + "' and Password = '" + password + "'";
            if (!Database.IsRowPresent(sql)) {

                errMsg = "The username and password you entered did not match our records. Please double-check and try again.";
                page = "login";

            } else {
                page = "homepage";

                //if the login is successful, we need to set all the values.
                //accessing database to get access to other data
                try {
                    Database.driverLoad();
                    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                    stat = conn.createStatement();
                    rs = stat.executeQuery("select * from allusers where UserName = '" + userName + "'");
                    //set the values
                    if (rs.next()) {
                        //set the current instance variables
                        this.setFullName(rs.getString(1));
                        this.setPhone(rs.getString(3));
                        this.setEmail(rs.getString(4));
                        this.setImg(Database.QueryReturnsOneString("select img_url from user_images where userName = '" + userName + "'"));

                        //set the values for future use
                        UserInfo.setFullName(fullName);
                        UserInfo.setUserName(userName);
                        UserInfo.setPhone(phone);
                        UserInfo.setEmail(email);
                        UserInfo.setPassword(password);
                        UserInfo.setImg(img);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        rs.close();
                        stat.close();
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return page;
    }

    public String logout() {
        //clear values
        UserInfo.setFullName(null);
        UserInfo.setUserName(null);
        UserInfo.setPhone(null);
        UserInfo.setEmail(null);
        UserInfo.setPassword(null);
        UserInfo.setImg(null);

        //invalidate session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    public String forgotPassword() throws MessagingException {
        String USER_NAME = "twitterresetpass";  // GMail user name (just the part before "@gmail.com")
        String PASSWORD = "Twitterretrievepassword123$"; // GMail password
        String RECIPIENT = email;
        String page = "";
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = {RECIPIENT}; // list of recipient email addresses
        String subject = "Forgot Password";
        String sql = "Select password from allusers where email = '" + email + "'";
        if (!Database.IsRowPresent(sql)) {
            passResetmsg = "Email id you entered is not registered with Twitter.";
            page = "ForgotPassword";
        } else if (Database.IsRowPresent(sql)) {
            String passwordRetrieved = Database.QueryReturnsOneString(sql);
            String body = "Your Twitter password is - " + passwordRetrieved;
            sendFromGMail(from, pass, to, subject, body);
            passResetmsgs = "Your password has been sent to your email ID- " + email + ". Use the password for logging in to Twitter. Thank You!";
            passResetmsg = "";
            page = backToLogin();
        }

        return page;
    }

    public void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public String backToLogin() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return "login";
    }

    public String modifySettings() {
        //image upload
        upload();

        //other settings
        if (Database.IsRowPresent("select * from allusers where UserName = '" + userName + "' and Password = '" + currentPassword + "'")) {
            if (!"".equals(email)) {
                Database.InsertUpdate("update allusers set email = '" + email + "' where UserName = '" + userName + "'");
                UserInfo.setEmail(email);
            }
            if (!"".equals(newPassword)) {
                Database.InsertUpdate("update allusers set Password = '" + newPassword + "' where UserName = '" + userName + "'");
                UserInfo.setPassword(newPassword);
            }
            this.errorMessage = "Your changes have been saved";
            return "settings.xhtml";
        } else {
            this.errorMessage = "Please enter a valid current Password";
            return "settings.xhtml";
        }
    }

    public ArrayList<Msg> fetchInboxMessages() {
        ArrayList<Msg> arr = new ArrayList<>();

        try {
            Database.driverLoad();
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from messages where ToUser = '" + userName + "' order by Timestamp desc");
            while (rs.next()) {
                arr.add(new Msg(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //arrUsers = Database.SelectQuery_ArrayListString("select UserName from allusers where UserName != '" + userName + "'");
        return arr;
    }

    public ArrayList<Msg> fetchSentMessages() {
        ArrayList<Msg> arr = new ArrayList<>();

        try {
            Database.driverLoad();
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from messages where FromUser = '" + userName + "' order by Timestamp desc");
            while (rs.next()) {
                arr.add(new Msg(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        arrUsers = Database.SelectQuery_ArrayListString("select UserName from allusers where UserName != '" + userName + "'");
        return arr;
    }

    public String shootMsg() {
        Database.InsertUpdate("insert into messages values('" + recipient + "','" + userName + "', '" + messageString + "', now())");
        this.successMsg = "Your message has been sent.";
        this.messageString = null;
        return ("messages.xhtml");
    }

    public void upload() {
        try {
            InputStream in = file.getInputStream();
//            File f = new File("\\Users\\GB\\Desktop\\Group_Project_3\\web\\Images\\Display_Picture\\" + file.getSubmittedFileName());
            File f = new File("\\Users\\GB\\Desktop\\Group_Project_3\\web\\Images\\Display_Picture\\" + userName);
            //File f = new File("\\Users\\SONY\\Desktop\\uhcl_FALL_2015\\ISAM5638_Adv_Appl_Prog_w_Java\\Projects\\Project 3 - Akshay Prabhu - 1441868\\Group_Project_3\\web\\Images\\Display_Picture\\" + file.getSubmittedFileName());
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);

            byte[] data = new byte[1024];
            int length;

            while ((length = in.read(data)) > 0) {
                out.write(data, 0, length);
            }

            out.close();
            in.close();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("path", f.getAbsolutePath());
            uploaded = true;

            //storing the image url in the database
            String imgURL = "Images/Display_Picture/" + userName;

            if (!Database.IsRowPresent("select * from user_images where userName = '" + userName + "'")) {
                Database.InsertUpdate("insert into user_images values('" + userName + "', '" + imgURL + "')");
            } else {
                Database.InsertUpdate("update user_images set img_url = '" + imgURL + "' where userName = '" + userName + "'");
            }

            //updating the UserInfo object
            UserInfo.setImg("Images/Display_Picture/" + userName);
            //updating the current Instance
            this.setImg("Images/Display_Picture/" + userName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "homepage";
    }
}
