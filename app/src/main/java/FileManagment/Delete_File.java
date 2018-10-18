package FileManagment;

import java.io.File;

public class Delete_File implements File_Management {

    @Override
    public Boolean Manage_File(File file, File_Task task, String New_Account_Name) throws Parameter_Exception, Invalid_Task_Exception, File_Exception, NullPointerException {
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
        } else {
            switch (task) {
                case Delete_Review_File:
                    DeleteFile(file);
                    break;
                case Delete_Account_File:
                    DeleteFile(file);
                    break;
                case Delete_Medical_File:
                    DeleteFile(file);
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

    private void DeleteFile(File file) throws File_Exception {
            if (!file.delete()) {
                throw new File_Exception("Unable to delete file .delete() failed");
            }
    }
}
