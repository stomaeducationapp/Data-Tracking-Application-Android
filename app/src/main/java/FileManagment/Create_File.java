package FileManagment;

import java.io.File;
import java.io.IOException;

/**
 * <h1>Create_File</h1>
 * The Create_File Java Class is used to create new files and directories, in the specified tree design for the
 * Application. Implements File_Management Interface. The Type of files and directories created is based on the enum
 * File_Task provided
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Last Edited</h1>
 * 18-Oct-2018
 * Patrick Crockford
 */
public class Create_File implements File_Management {

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
     * Path directory Separator
     */
    private static final String FILE_SEPARATOR = "/";

    /**
     * Public method from the Interface to create the required file on the storage within the device.
     * If any errors occur exceptions are thrown specifying what occurred.
     *
     * @param file             Represents the File directory object to add files to
     * @param task             Defines what task should be carried out
     * @param New_Account_Name Name of the New Account to create a directory. If not used must be set to a non null values otherwise Exception will be thrown
     * @return True if successful
     * @throws Invalid_Task_Exception if the task value given is not valid for Create_File.class functionality
     * @throws Parameter_Exception    if the file object doesn't exist or is not a directory
     * @throws NullPointerException   if any of the method parameters are null
     * @throws File_Exception         if the file creation has failed
     */
    @Override
    public Boolean Manage_File(File file, File_Task task, String New_Account_Name) throws Invalid_Task_Exception, Parameter_Exception, NullPointerException, File_Exception {
        if (file == null) {
            throw new NullPointerException("File object is Null");
        } else if (task == null) {
            throw new NullPointerException("Task enum value is null");
        } else if (New_Account_Name == null) {
            throw new NullPointerException("Account Name is Null, set to non null value if not required");
        } else if (!file.exists()) {
            throw new Parameter_Exception("File provided doesn't exist");
        } else if (!file.isDirectory()) {
            throw new Parameter_Exception("File provided is not a directory");
        } else {
            switch (task) {
                case Create_Review_File:
                    CreateReviewFile(file);
                    break;
                case Create_Account_File:
                    CreateAccountFile(file);
                    break;
                case Create_Medical_File:
                    CreateMedicalFile(file);
                    break;
                case New_Account:
                    CreateAccountDirectory(file, New_Account_Name);
                    break;
                case Create_Login_File:
                    CreateLoginFile(file);
                    break;
                default:
                    throw new Invalid_Task_Exception("Invalid Task enum value given");
            }
            return true;
        }
    }

    /**
     * This private method functionality is to create a new account directory, within the file directory provided, on the devices internal storage. If
     * successful Methods CreateReviewFile(), CreateAccountFile(), and CreateMedicalFile() are called to create the
     * required new files within the account directory for use by the application
     *
     * @param file             Represents the file to add the new account directory to
     * @param New_Account_Name The value to name the new directory
     * @throws File_Exception if the direction creation fails in any way, or adding of files within it failed once the
     *                        directory has been created
     */
    private void CreateAccountDirectory(File file, String New_Account_Name) throws File_Exception {
        String path = file.getPath();
        path += FILE_SEPARATOR + New_Account_Name;
        File newFile = new File(path);
        if (!newFile.exists()) {
            try {
                if (newFile.mkdir()) {
                    CreateReviewFile(newFile);
                    CreateAccountFile(newFile);
                    CreateMedicalFile(newFile);
                } else {

                }
                throw new File_Exception("Unable to create directory, .mkdir() failed");
            } catch (SecurityException e) {
                throw new File_Exception("Unable to create directory, Security Exception has occurred: " + e);
            }
        } else {
            throw new File_Exception("Unable to create directory, already exists");
        }
    }

    /**
     * This private method functionality is to create a Review File, within the account file directory provided, on the devices internal storage.
     * @param file Represents the file to add the new account directory to
     * @throws File_Exception if the creating of the file fails in any way
     */
    private void CreateReviewFile(File file) throws File_Exception {
        String path = file.getPath();
        path += FILE_SEPARATOR + REVIEW_FILE_NAME;
        File newFile = new File(path);
        if (!newFile.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new File_Exception("Unable to create file, .createNewFile() failed");
                }
            } catch (IOException e) {
                throw new File_Exception("Unable to create file, IO Exception has occurred: " + e);
            }
        } else {
            throw new File_Exception("Unable to create file, already exists");
        }
    }

    /**
     * This private method functionality is to create a Review File, within the account file directory provided, on the devices internal storage.
     * @param file Represents the file to add the new account directory to
     * @throws File_Exception if the creating of the file fails in any way
     */
    private void CreateAccountFile(File file) throws File_Exception {
        String path = file.getPath();
        path += FILE_SEPARATOR + ACCOUNT_FILE_NAME;
        File newFile = new File(path);
        if (!newFile.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new File_Exception("Unable to create file, .createNewFile() failed");
                }
            } catch (IOException e) {
                throw new File_Exception("Unable to create file, IO Exception has occurred: " + e);
            }
        } else {
            throw new File_Exception("Unable to create file, already exists");
        }

    }

    /**
     * This private method functionality is to create a Review File, within the account file directory provided, on the devices internal storage.
     * @param file Represents the file to add the new account directory to
     * @throws File_Exception if the creating of the file fails in any way
     */
    private void CreateMedicalFile(File file) throws File_Exception {
        String path = file.getPath();
        path += FILE_SEPARATOR + MEDICAL_INFORMATION_FILE;
        File newFile = new File(path);
        if (!newFile.exists()) {
            try {

                if (!file.createNewFile()) {
                    throw new File_Exception("Unable to create file, .createNewFile() failed");
                }
            } catch (IOException e) {
                throw new File_Exception("Unable to create file, IO Exception has occurred: " + e);

            }
        } else {
            throw new File_Exception("Unable to create file, already exists");
        }
    }

    /**
     * This private method functionality is to create a Login File, within the directory provided for the application, on the devices internal storage.
     * <h1>Note</h1>
     * This method should be called at the start of the launch of the program as it is required for people to be able to create accounts and log in.
     * @param file Represents the file to add the new account directory to
     * @throws File_Exception if the creating of the file fails in any way
     */
    private void CreateLoginFile(File file) throws File_Exception {
        String path = file.getPath();
        path += FILE_SEPARATOR + LOGIN_FILE_NAME;
        File newFile = new File(path);
        if (!newFile.exists()) {
            try {

                if (!file.createNewFile()) {
                    throw new File_Exception("Unable to create file, .createNewFile() failed");
                }
            } catch (IOException e) {
                throw new File_Exception("Unable to create file, IO Exception has occurred: " + e);

            }
        } else {
            throw new File_Exception("Unable to create file, already exists");
        }
    }
}
