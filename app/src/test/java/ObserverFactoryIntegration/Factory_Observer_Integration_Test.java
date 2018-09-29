package ObserverFactoryIntegration;

import org.junit.Before;
import org.junit.Test;

import Factory.Factory;
import Observers.Form_Change_Observer;
import Observers.State_Observer;
import Observers.Time_Observer;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class Factory_Observer_Integration_Test {

    private Factory factory;
    private Boolean setup = false;

    @Before
    public void setUp() {
        if (!setup) {
            factory = Factory.Get_Factory();
        }
    }

    @Test
    public void make_Form_Change_Observer() {
        Form_Change_Observer form_change_observer = factory.Make_Form_Change_Observer();
        assertThat(form_change_observer, instanceOf(Form_Change_Observer.class));
    }

    @Test
    public void make_State_Observer() {
        State_Observer state_observer = factory.Make_State_Observer();
        assertThat(state_observer, instanceOf(State_Observer.class));
    }

    @Test
    public void make_Time_Observer_Daily_Review() {
        Time_Observer time_observer = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Daily_Review);
        assertThat(time_observer, instanceOf(Time_Observer.class));
    }

    @Test
    public void make_Time_Observer_Export_Data() {
        Time_Observer time_observer = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Export_Data);
        assertThat(time_observer, instanceOf(Time_Observer.class));
    }

    //Test for Giving Nulls

    @Test(expected = RuntimeException.class)
    public void Null_To_Make_Time_Observer(){
        Time_Observer time_observer = factory.Make_Time_Observer(null);
        assertNotNull(time_observer);
    }
}