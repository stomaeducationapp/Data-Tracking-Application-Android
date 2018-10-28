package FileManagment;

import java.io.File;

/**
 * <h1>File_Management</h1>
 * The File_Management Interface Java Class is used to Allow access to the package private concrete classes that inherit
 * from
 * it.
 * It also stores the Enum File_Task required to specify what task is required from the concrete class
 * <h1>Note</h1>
 * Not all enum values are valid for each concrete class please refer to the class specific information on which values
 * are valid
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Last Edited</h1>
 * 17-Oct-2018
 * Patrick Crockford
 */
public interface File_Management {

    /**
     * The enum Tags to create, delete, or modify files/directories.
     */
    enum File_Task {
        New_Account, Delete_Account, Modify_Account_Container_Name, //Account Directory
        Create_Login_File, Create_Account_File, Create_Medical_File, Create_Review_File, // File creation
        Delete_Account_File, Delete_Medical_File, Delete_Review_File //File deletion
        //
    }

    /**
     * Public method from the Interface to create, delete or modify modify the required file/directory on the storage
     * within the device
     * If any errors occur exceptions are thrown specifying what occurred.
     *
     * @param file         Represents the File directory object to add to, delete, or modify
     * @param task         Defines what task should be carried out
     * @param account_Name Value to change the directory to
     * @return True if successful
     * @throws Invalid_Task_Exception if the task value given is not valid for Modify_File Class functionality
     * @throws Parameter_Exception    if the file object doesn't exist
     * @throws NullPointerException   if any of the method parameters are null
     * @throws File_Exception         if the file creation has failed
     */
    Boolean Manage_File(File file, File_Task task, String account_Name) throws Invalid_Task_Exception, Parameter_Exception, File_Exception, NullPointerException;
}
