package FileManagment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

public class Delete_FileTest {
    private Delete_File df;
    /**
     * Initialize.
     */
    @Before
    public void initialize() {
        df = new Delete_File();
    }

    //if-else check chain
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
//invalid switch value
    @Test(expected = Invalid_Task_Exception.class)
    public void Invalid_Task() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        df.Manage_File(file, File_Management.File_Task.Modify_Account_Container_Name, "new");
    }

    //delete account directory functionality
    @Test(expected = File_Exception.class)
    public void Del_AccountDir_Invalid_Name() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("invalid");
        df.Manage_File(file, File_Management.File_Task.Delete_Account, "fred");
    }

    @Test(expected = File_Exception.class)
    public void Del_AccountDir_Fail() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("bob");
        Mockito.when(file.delete()).thenReturn(Boolean.FALSE);
        df.Manage_File(file, File_Management.File_Task.Delete_Account, "bob");
    }

    @Test
    public void Del_AccountDir() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("bob");
        Mockito.when(file.delete()).thenReturn(Boolean.TRUE);
        Assert.assertTrue( df.Manage_File(file, File_Management.File_Task.Delete_Account, "bob"));
    }

    //delete review file functionality
    @Test(expected = File_Exception.class)
    public void Delete_Review_File_Invalid_Name() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("invalid");
        df.Manage_File(file, File_Management.File_Task.Delete_Review_File, "default");
    }

    @Test(expected = File_Exception.class)
    public void Delete_Review_File_Fail() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("ReviewInformationFile.xml");
        Mockito.when(file.delete()).thenReturn(Boolean.FALSE);
        df.Manage_File(file, File_Management.File_Task.Delete_Review_File, "default");
    }

    @Test
    public void Delete_Review_File() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("ReviewInformationFile.xml");
        Mockito.when(file.delete()).thenReturn(Boolean.TRUE);
        Assert.assertTrue( df.Manage_File(file, File_Management.File_Task.Delete_Review_File, "default"));
    }

    //delete Account file functionality
    @Test(expected = File_Exception.class)
    public void Delete_Account_File_Invalid_Name() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("invalid");
        df.Manage_File(file, File_Management.File_Task.Delete_Account_File, "default");
    }

    @Test(expected = File_Exception.class)
    public void Delete_Account_File_Fail() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("AccountInformationFile.xml");
        Mockito.when(file.delete()).thenReturn(Boolean.FALSE);
        df.Manage_File(file, File_Management.File_Task.Delete_Account_File, "default");
    }

    @Test
    public void Delete_Account_File() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("AccountInformationFile.xml");
        Mockito.when(file.delete()).thenReturn(Boolean.TRUE);
        Assert.assertTrue( df.Manage_File(file, File_Management.File_Task.Delete_Account_File, "default"));
    }

    //delete Medical file functionality
    @Test(expected = File_Exception.class)
    public void Delete_Medical_File_Invalid_Name() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("invalid");
        df.Manage_File(file, File_Management.File_Task.Delete_Medical_File, "default");
    }

    @Test(expected = File_Exception.class)
    public void Delete_Medical_File_Fail() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("MedicalInformationFile.xml");
        Mockito.when(file.delete()).thenReturn(Boolean.FALSE);
        df.Manage_File(file, File_Management.File_Task.Delete_Medical_File, "default");
    }

    @Test
    public void Delete_Medical_File() throws Parameter_Exception, File_Exception, Invalid_Task_Exception {
        File file = Mockito.mock(File.class);
        Mockito.when(file.exists()).thenReturn(Boolean.TRUE);
        Mockito.when(file.isDirectory()).thenReturn(Boolean.TRUE);
        Mockito.when(file.getName()).thenReturn("MedicalInformationFile.xml");
        Mockito.when(file.delete()).thenReturn(Boolean.TRUE);
        Assert.assertTrue( df.Manage_File(file, File_Management.File_Task.Delete_Medical_File, "default"));
    }
}