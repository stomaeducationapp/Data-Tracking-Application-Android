package FileManagment;

import java.io.File;
import java.io.IOException;

/**
 * The type Create file.
 */
public class Create_File implements File_Management {
    private static final String REVIEW_FILE_NAME = "ReviewInformationFile.txt";
    private static final String MEDICAL_INFORMATION_FILE = "MedicalInformationFile.txt";
    private static final String ACCOUNT_FILE_NAME = "AccountInformationFile.txt";
    private static final String FILE_SEPARATOR = "/";

    @Override
    public Boolean Manage_File(File file, File_Task task, String New_Account_Name) throws Invalid_Task_Exception, Parameter_Exception, NullPointerException, File_Exception {
        if (file == null) {
            throw new NullPointerException("File object is Null");
        } else if (task == null) {
            throw new NullPointerException("Task enum value is null");
        } else if (New_Account_Name == null) {
            throw new NullPointerException("Account Name is Null");
        }else if (!file.exists()) {
            throw new Parameter_Exception("File provided doesn't exist");
        } else if (!file.isDirectory()) {
            throw new Parameter_Exception("File provided is not a directory");
        }  else {
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
                default:
                    throw new Invalid_Task_Exception("Invalid Task enum value given");
            }
            return true;
        }
    }

    private void CreateAccountDirectory(File file, String New_Account_Name) throws File_Exception {
        String path = file.getPath();
        path += FILE_SEPARATOR + New_Account_Name;
        File newFile = new File(path);
        if (!newFile.exists()) {
            try {
                if (!newFile.mkdir()) {
                    throw new File_Exception("Unable to create directory, .mkdir() failed");
                }
            } catch (SecurityException e) {
                throw new File_Exception("Unable to create directory, Security Exception has occurred: " + e);
            }
        } else {
            throw new File_Exception("Unable to create directory, already exists");
        }
    }


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
}
