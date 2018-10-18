package FileManagment;

import java.io.File;

/**
 * <h1>Modify_File</h1>
 * The Modify_File Java Class is used to modify the name of an accounts directory stored on the internal storage fot he
 * users device
 * Implements File_Management Interface.
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Last Edited</h1>
 * 18-Oct-2018
 * Patrick Crockford
 */
public class Modify_File implements File_Management {

    /**
     * Public method from the Interface to modify the required file on the storage within the device
     * If any errors occur exceptions are thrown specifying what occurred.
     * <p1>NOTE</p1>
     * Currently only the account directory can be renamed as there is no functionality requirement outside of that to
     * renamed other files.
     * This Method can only handle the task value 'Modify_Account_Container_Name',
     *
     * @param file         Represents the File directory object to modify
     * @param task         Defines what task should be carried out
     * @param account_Name Value to change the directory to
     * @return True if successful
     * @throws Invalid_Task_Exception if the task value given is not valid for Modify_File Class functionality
     * @throws Parameter_Exception    if the file object doesn't exist
     * @throws NullPointerException   if any of the method parameters are null
     * @throws File_Exception         if the file creation has failed
     */
    @Override
    public Boolean Manage_File(File file, File_Task task, String account_Name) throws Parameter_Exception, Invalid_Task_Exception, NullPointerException, File_Exception {
        if (file == null) {
            throw new NullPointerException("File object is Null");
        } else if (task == null) {
            throw new NullPointerException("Task enum value is null");
        } else if (account_Name == null) {
            throw new NullPointerException("Account Name is Null");
        } else if (!file.exists()) {
            throw new Parameter_Exception("File provided doesn't exist");
        } else if (!file.isDirectory()) {
            throw new Parameter_Exception("File provided is not a directory");
        } else {
            switch (task) {
                case Modify_Account_Container_Name:
                    RenameDirectory(file, account_Name);
                    break;
                default:
                    throw new Invalid_Task_Exception("Invalid Task enum value given");
            }
            return true;
        }
    }

    /**
     * This private method functionality is to rename the file directory provided, on the devices internal storage, to
     * the new value contained in the account_Name string.
     *
     * @param file         Represents the file to rename
     * @param account_Name The value to rename the directory
     * @throws File_Exception if the directory modification fails in any way
     */
    private void RenameDirectory(File file, String account_Name) throws File_Exception {
        if (!file.getName().equals(account_Name)) {
            String path = file.getPath();
            path = path.replace(file.getName(), account_Name);
            File newFileName = new File(path);
            if (!newFileName.exists()) {
                if (!file.renameTo(newFileName)) {
                    throw new File_Exception("Unable to rename directory/file, .renameTo() failed");
                }
            } else {
                throw new File_Exception("Unable to rename file, new name already exists");
            }
        } else {
            throw new File_Exception("Unable to create file, name is the same as file given");
        }
    }
}
