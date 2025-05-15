package core;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import core.utils.JUnitCustomExtension;
import core.utils.TestListener;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testng.annotations.Listeners;

@Listeners({TestListener.class, ReportPortalTestNGListener.class})
@ExtendWith({ReportPortalExtension.class, JUnitCustomExtension.class})
public class BaseTest {
}