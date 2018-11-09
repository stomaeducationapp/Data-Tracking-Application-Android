package FileManagment;

import java.io.File;

/**
 * <h1>Delete_File</h1>
 * The Delete_File Java Class is used to delete files and directories for the Application. Implements File_Management
 * Interface. The Type of files and directories delete is based on the enum File_Task provided
 *
 * @author Patrick Crockford
 * @version 2.0
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
     * Public method from the Interface to delete the specified file or directory on the storage within the device.
     * If any errors occur exceptions are thrown specifying what occurred.
     * This Method can only handle the task values 'Delete_Account', 'Delete_Account_File', 'Delete_Medical_File',
     * and 'Delete_Review_File'
     *
     * @param file         Represents the File directory object to delete
     * @param task         Defines what task should be carried out
     * @param account_Name Name of the account directory to delete. If not used must be set to a non null
     *                     values otherwise Exception will be thrown
     * @return True if successful
     * @throws Invalid_Task_Exception if the task value given is not valid for Delete_File Class functionality
     * @throws Parameter_Exception    if the file object doesn't exist
     * @throws NullPointerException   if any of the method parameters are null
     * @throws File_Exception         if the file creation has failed
     */
    @Override
    public Boolean Manage_File(File file, File_Task task, String account_Name) throws Parameter_Exception, Invalid_Task_Exception, File_Exception, NullPointerException {
        if (file == null) {
            throw new NullPointerException("File object is Null");
        } else if (task == null) {
            throw new NullPointerException("Task enum value is null");
        } else if (account_Name == null) {
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
                    Delete_Account_Directory(file, account_Name);
                    break;
                default:
                    throw new Invalid_Task_Exception("Invalid Task enum value given");
            }
            return true;
        }
    }

    /**
     * This private method functionality is to delete the Account File, within the account file directory provided, on
     * the devices internal storage.
     *
     * @param file Represents the account file to delete
     * @throws File_Exception if the name of the file to delete is not expected or deletion fails
     */
    private void Delete_Account_File(File file) throws File_Exception {
        if (file.getName().equals(ACCOUNT_FILE_NAME)) {
            if (!file.delete()) {
                throw new File_Exception("Unable to delete file .delete() failed");
            }
        } else {
            throw new File_Exception("Wrong file given for deletion, expected: " + ACCOUNT_FILE_NAME + " Found: " + file.getName());
        }
    }

    /**
     * This private method functionality is to delete the Review File, within the account file directory provided, on
     * the devices internal storage.
     *
     * @param file Represents the review file to delete
     * @throws File_Exception if the name of the file to delete is not expected or deletion fails
     */
    private void Delete_Review_File(File file) throws File_Exception {
        if (file.getName().equals(REVIEW_FILE_NAME)) {
            if (!file.delete()) {
                throw new File_Exception("Unable to delete file .delete() failed");
            }
        } else {
            throw new File_Exception("Wrong file given for deletion, expected: " + REVIEW_FILE_NAME + " Found: " + file.getName());
        }
    }

    /**
     * This private method functionality is to delete the Medical File, within the account file directory provided, on
     * the devices internal storage.
     *
     * @param file Represents the medical file to delete
     * @throws File_Exception if the name of the file to delete is not expected or deletion fails
     */
    private void Delete_Medical_File(File file) throws File_Exception {
        if (file.getName().equals(MEDICAL_INFORMATION_FILE)) {
            if (!file.delete()) {
                throw new File_Exception("Unable to delete file .delete() failed");
            }
        } else {
            throw new File_Exception("Wrong file given for deletion, expected: " + MEDICAL_INFORMATION_FILE + " Found: " + file.getName());
        }
    }


    /**
     * This private method functionality is to delete the Account Directory, on the devices internal storage.
     *
     * @param file         Represents the account file to delete
     * @param account_Name the name of the account to be deleted
     * @throws File_Exception if the name of the file to delete is not expected or deletion fails
     */
    private void Delete_Account_Directory(File file, String account_Name) throws File_Exception {
        if (file.getName().equals(account_Name)) {
            if (!file.delete()) {
                throw new File_Exception("Unable to delete file .delete() failed");
            }
        } else {
            throw new File_Exception("Wrong file given for deletion, expected: " + account_Name + " Found: " + file.getName());
        }


    }
}
