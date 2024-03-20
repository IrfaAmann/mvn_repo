package sg.baseClass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import sg.driver.CucumberTestRunner;
import sg.pages.HomePage;
import sg.pages.UsersPage;

public class UsersBaseClass extends CucumberTestRunner {
    /*****************************************
     * method Name     : userNavigatesTheURL()
     *
     *
     ******************************************/
    public boolean userNavigatesTheURL(WebDriver oBrowser, String strURL){
        try{
            return appDep.navigateURL(oBrowser, strURL);
        }catch(Exception e){
            reports.writeResult(oBrowser, "Exception", "Exception in 'userNavigatesTheURL()' method");
            return false;
        }
    }



    /*****************************************
     * method Name     : verifyUserLoginsInWithValidCredentials()
     *
     *
     ******************************************/
    public boolean verifyUserLoginsInWithValidCredentials(WebDriver oBrowser, String userName, String password){
        try{
            return appDep.loginToApplication(oBrowser, userName, password);
        }catch(Exception e){
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyUserLoginsInWithValidCredentials()' method");
            return false;
        }
    }



    /*****************************************
     * method Name     : createNewUser()
     *
     *
     ******************************************/
    public boolean createNewUser(WebDriver oBrowser, String firstName, String lastName, String email
            , String userName, String password, String retypePassword){
        try{
            appInd.clickObject(oBrowser, HomePage.obj_USERS_Menu);
            appInd.clickObject(oBrowser, UsersPage.obj_AddUser_Button);
            appInd.waitForElement(oBrowser, UsersPage.obj_CreateUser_Button, "Clickable", "", 10);

            appInd.setObject(oBrowser, UsersPage.obj_Users_FirstName_Edit, firstName);
            appInd.setObject(oBrowser, UsersPage.obj_Users_LastName_Edit, lastName);
            appInd.setObject(oBrowser, UsersPage.obj_Users_Email_Edit, email);
            appInd.setObject(oBrowser, UsersPage.obj_Users_UserName_Edit, userName);
            appInd.setObject(oBrowser, UsersPage.obj_Users_Password_Edit, password);
            appInd.setObject(oBrowser, UsersPage.obj_Users_RetypePassword_Edit, retypePassword);
            appInd.clickObject(oBrowser, UsersPage.obj_CreateUser_Button);
            userNameCreated = lastName + ", " + firstName;
            appInd.waitForElement(oBrowser, By.xpath(UsersPage.obj_UserName_Link.replace("%s", userNameCreated)), "Clickable", "", 10);
            appInd.waitForElement(oBrowser, By.xpath(UsersPage.obj_UserName_Link.replace("%s", userNameCreated)), "Text", userNameCreated, 10);
            reports.writeResult(oBrowser, "Pass", "The new user created successful");
            return true;
        }catch(Exception e){
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewUser()' method");
            return false;
        }
    }



    /*****************************************
     * method Name     : verifyUserCreatedSuccessful()
     *
     *
     ******************************************/
    public boolean verifyUserCreatedSuccessful(WebDriver oBrowser){
        try{
            boolean blnRes = appInd.verifyElementPresent(oBrowser, By.xpath(UsersPage.obj_UserName_Link.replace("%s", userNameCreated)));
            if(blnRes){
                reports.writeResult(oBrowser, "Pass", "The new user '"+userNameCreated+"' was created successful");
                return true;
            }else{
                reports.writeResult(oBrowser, "Fail", "Failed to create the new user '"+userNameCreated+"'");
                return true;
            }
        }catch(Exception e){
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyUserCreatedSuccessful()' method");
            return false;
        }
    }



    /*****************************************
     * method Name     : verifyUserDeletedSuccessful()
     *
     *
     ******************************************/
    public boolean verifyUserDeletedSuccessful(WebDriver oBrowser){
        try{
            appInd.clickObject(oBrowser, By.xpath(UsersPage.obj_UserName_Link.replace("%s", userNameCreated)));
            appInd.waitForElement(oBrowser, UsersPage.obj_SaveChanges_Button, "Clickable", "", 10);
            appInd.waitForElement(oBrowser, UsersPage.obj_DeleteUser_Button, "Clickable", "", 10);
            appInd.clickObject(oBrowser, UsersPage.obj_DeleteUser_Button);
            Thread.sleep(2000);
            oBrowser.switchTo().alert().accept();
            Thread.sleep(2000);
            appInd.waitForElement(oBrowser, By.xpath(UsersPage.obj_UserName_Link.replace("%s", userNameCreated)), "Invisibility", "", 10);
            reports.writeResult(oBrowser, "Pass", "The user '"+userNameCreated+"' is getting deleted");

            if(appInd.verifyElementNotPresent(oBrowser, By.xpath(UsersPage.obj_UserName_Link.replace("%s", userNameCreated)))){
                reports.writeResult(oBrowser, "Pass", "The user '"+userNameCreated+"' is deleted successful");
                return true;
            }else{
                reports.writeResult(oBrowser, "Fail", "Failed to delete the user '"+userNameCreated+"'");
                return false;
            }

        }catch(Exception e){
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyUserDeletedSuccessful()' method");
            return false;
        }
    }
}
