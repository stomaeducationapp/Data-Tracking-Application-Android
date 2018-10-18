package FileManagment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

public class Delete_FileTest {
    Delete_File df;
    final String path = "test";
    /**
     * Initialize.
     */
    @Before
    public void initialize() {
        df = new Delete_File();
    }

    @Test(expected = NullPointerException.class)
    public void File_Null() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        df.Manage_File(null, File_Management.File_Task.Delete_Review_File, "test");

    }

    @Test(expected = NullPointerException.class)
    public void Task_Null() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        df.Manage_File(new File("test"), null, "test");
    }

    @Test(expected = NullPointerException.class)
    public void New_Account_Name_Null() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        df.Manage_File(new File("test"), File_Management.File_Task.Delete_Review_File, null);
    }


    @Test(expected = Parameter_Exception.class)
    public void File_Does_Not_Exist() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.FALSE);
        df.Manage_File(file, File_Management.File_Task.Delete_Review_File, "new");
    }

    @Test(expected = Parameter_Exception.class)
    public void File_Is_Not_Directory() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.FALSE);

        df.Manage_File(file, File_Management.File_Task.Delete_Review_File, "new");

    }

    @Test(expected = Invalid_Task_Exception.class)
    public void Invalid_Task() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        df.Manage_File(file, File_Management.File_Task.Modify_Account_Container_Name, "new");
    }

    @Test(expected = File_Exception.class)
    public void Cant_del_NewAccountDir() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        df.Manage_File(file, File_Management.File_Task.Delete_Account, "new");
    }

    @Test(expected = File_Exception.class)
    public void Cant_del_NewAccountFile() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        df.Manage_File(file, File_Management.File_Task.Delete_Account_File, "new");
    }

    @Test(expected = File_Exception.class)
    public void Cant_del_NewMedicalFile() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        df.Manage_File(file, File_Management.File_Task.Delete_Medical_File, "new");
    }

    @Test(expected = File_Exception.class)
    public void Cant_del_NewReviewFile() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        df.Manage_File(file, File_Management.File_Task.Delete_Review_File, "new");
    }
}