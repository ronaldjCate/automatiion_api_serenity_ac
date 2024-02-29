package atlantic.com.api.collection.runner;


import com.intuit.karate.Results;
import com.intuit.karate.Runner;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestRunner {

    @Test
    public void testParallel() {
        Results results =
                Runner.path("classpath:atlantic/com/api/collection/features/")
                // .tags("@ValidarGeneracionIdSesion")
                .outputCucumberJson(true)
                .parallel(1);
                cucumberReport(results.getReportDir());
    }

    public void cucumberReport(String path) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(path), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "Reporte");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }

}
