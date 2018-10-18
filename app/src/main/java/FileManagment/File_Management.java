package FileManagment;

import java.io.File;

public interface File_Management {

    /**
     *
     */
    enum File_Task {
        New_Account, Delete_Account, Create_Login_File,//
        Create_Account_File, Create_Medical_File, Create_Review_File, //
        Delete_Account_File, Delete_Medical_File, Delete_Review_File, //
        Modify_Account_Container_Name//
    }

    /**
     * @param file
     * @param task
     * @param New_Account_Name
     * @return
     * @throws Invalid_Task_Exception
     * @throws Parameter_Exception
     * @throws File_Exception
     * @throws NullPointerException
     */
    public Boolean Manage_File(File file, File_Task task, String New_Account_Name) throws Invalid_Task_Exception, Parameter_Exception, File_Exception, NullPointerException;
}
