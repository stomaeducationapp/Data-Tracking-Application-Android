package FileManagment;

import java.io.File;

/**
 * <h1>Delete_File</h1>
 * The Delete_File Java Class is used to delete files and directories for the Application. Implements File_Management
 * Interface. The Type of files and directories delete is based on the enum File_Task provided
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Last Edited</h1>
 * 18-Oct-2018
 * Patrick Crockford
 */
public class Delete_File implements File_Management {

    /**
     * Name of the Review file for each account
     */
    private static final String REVIEW_FILE_NAME = "ReviewInformationFile.xml";

    /**
     * Name of the Medical file for each account
     */
    private static final String MEDICAL_INFORMATION_FILE = "MedicalInformationFile.xml";

    /**
     * Name of the Account file for each account
     */
    private static final String ACCOUNT_FILE_NAME = "AccountInformationFile.xml";

    /**
     * Name of the Login file for the application
     */
    private static final String LOGIN_FILE_NAME = "LoginInformation.xml";

    /**
     * Public method from the Interface to delete the specified file or directory on the storage within the device.
     * If any errors occur exceptions are thrown specifying what occurred.
     *
     * @param file             Represents the File directory object to delete
     * @param task             Defines what task should be carried out
     * @param New_Account_Name Name of the New Account to create a directory. If not used must be set to a non null
     *                         values otherwise Exception will be thrown
     * @return True if successful
     * @throws Invalid_Task_Exception if the task value given is not valid for Create_File.class functionality
     * @throws Parameter_Exception    if the file object doesn't exist
     * @throws NullPointerException   if any of the method parameters are null
     * @throws File_Exception         if the file creation has failed
     */
    @Override
    public Boolean Manage_File(File file, File_Task task, String New_Account_Name) throws Parameter_Exception, Invalid_Task_Exception, File_Exception, NullPointerException {
        if (file == null) {
            throw new NullPointerException("File object is Null");
        } else if (task == null) {
            throw new NullPointerException("Task enum value is null");
        } else if (New_Account_Name == null) {
            throw new NullPointerException("Account Name is Null");
        } else if (!file.exists()) {
            throw new Parameter_Exception("File provided doesn't exist");
        } else {
            switch (task) {
                case Delete_Review_File:
                    Delete_Review_File(file);
                    break;
                case Delete_Account_File:
                    Delete_Account_File(file);
                    break;
                case Delete_Medical_File:
                    Delete_Medical_File(file);
                    break;
                case Delete_Account:
                    DeleteFile(file);
                    break;
                default:
                    throw new Invalid_Task_Exception("Invalid Task enum value given");
            }
            return true;
        }
    }


    private void Delete_Account_File(File file) throws File_Exception {
        if (file.getName().equals(ACCOUNT_FILE_NAME)) {
            if (!file.delete()) {
                throw new File_Exception("Unable to delete file .delete() failed");
            }
        } else {
            throw new File_Exception("Wrong file given for deletion, expected: " + ACCOUNT_FILE_NAME + " Found: " + file.getName());
        }
    }

    private void Delete_Review_File(File file) throws File_Exception {
        if (file.getName().equals(REVIEW_FILE_NAME)) {
            if (!file.delete()) {
                throw new File_Exception("Unable to delete file .delete() failed");
            }
        } else {
            throw new File_Exception("Wrong file given for deletion, expected: " + REVIEW_FILE_NAME + " Found: " + file.getName());
        }
    }

    private void Delete_Medical_File(File file) throws File_Exception {
        if (file.getName().equals(MEDICAL_INFORMATION_FILE)) {
            if (!file.delete()) {
                throw new File_Exception("Unable to delete file .delete() failed");
            }
        } else {
            throw new File_Exception("Wrong file given for deletion, expected: " + MEDICAL_INFORMATION_FILE + " Found: " + file.getName());
        }
    }


    private void DeleteFile(File file) throws File_Exception {
        if (!file.delete()) {
            throw new File_Exception("Unable to delete file .delete() failed");
        }
    }


}
