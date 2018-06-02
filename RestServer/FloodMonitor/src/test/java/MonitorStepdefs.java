import com.floodMonitor.mapper.HistoryInfoMapper;
import com.floodMonitor.pojo.HistoryInfo;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

public class MonitorStepdefs {
    private ApplicationContext ac;
    private HistoryInfoMapper mapper;
    private HistoryInfo hi;

    @Given("^A FloodMonitor$$")
    public void aFloodMonitor() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        mapper = ac.getBean(HistoryInfoMapper.class);
    }

    @When("^the number (\\d+) is id$")
    public void theNumberIsId(int id) {
        hi = mapper.findById(id);
    }

    @Then("^I should be told the correct answer is \"([^\"]*)\"$")
    public void I_should_be_told_the_correct_answer_is(String expectedResult) {
        assertEquals(expectedResult, hi.getMonitoringValue());
    }
}
