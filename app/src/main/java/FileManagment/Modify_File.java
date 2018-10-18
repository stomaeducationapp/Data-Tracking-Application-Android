package FileManagment;

import java.io.File;

public class Modify_File implements File_Management {
    private static final String FILE_SEPARATOR = "/";

    @Override
    public Boolean Manage_File(File file, File_Task task, String New_Account_Name) throws Parameter_Exception, Invalid_Task_Exception, NullPointerException, File_Exception {
        if (file == null) {
            throw new NullPointerException("File object is Null");
        } else if (task == null) {
            throw new NullPointerException("Task enum value is null");
        } else if (New_Account_Name == null) {
            throw new NullPointerException("Account Name is Null");
        } else if (!file.exists()) {
            throw new Parameter_Exception("File provided doesn't exist");
        } else if (!file.isDirectory()) {
            throw new Parameter_Exception("File provided is not a directory");
        } else {
            switch (task) {
                case Modify_Account_Container_Name:
                    RenameDirectory(file, New_Account_Name);
                    break;
                default:
                    throw new Invalid_Task_Exception("Invalid Task enum value given");
            }
            return true;
        }
    }

    private void RenameDirectory(File file, String New_Account_Name) throws File_Exception {

        if (!file.getName().equals(New_Account_Name)) {
            String path = file.getPath();
            path = path.replace(file.getName(), New_Account_Name);
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
