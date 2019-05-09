package com.jeeday.jenkins.variablesReplace;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.jvnet.hudson.test.JenkinsRule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class VariablesReplaceBuilderTest {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    private String fileEncoding = "UTF-8";
	private String content = "Hello my name is #{NAME}#";
    private String variablesPrefix = "#{";
    private String variablesSuffix = "}#";
    private String emptyValue = "}#";
    private String variablesSource = "custom";
	private File file;
	private List<VariablesReplaceConfig> configs;
	
    @Before
    public void init() throws IOException {
    	file = new File(getClass().getResource(".").getPath() + "tmp.txt");
    	configs = new ArrayList<>();
    	List<VariablesReplaceItemConfig> cfgs = new ArrayList<>();
    	cfgs.add(new VariablesReplaceItemConfig("#{NAME}#", "John"));
    	FileUtils.write(file, content, Charset.forName(fileEncoding));
    	configs.add(new VariablesReplaceConfig(file.getAbsolutePath(), fileEncoding, variablesPrefix, variablesSuffix, emptyValue, variablesSource, cfgs));
    }
    
    @After
    public void clean() throws IOException {
    	FileUtils.forceDelete(file);
    }
    
    @Test
    public void testBuild() throws Exception {
        FreeStyleProject project = jenkins.createFreeStyleProject();
        VariablesReplaceBuilder builder = new VariablesReplaceBuilder(configs);
        project.getBuildersList().add(builder);

        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
        jenkins.assertLogContains("replace times: 1, #{NAME}# => [Hello my name is John]", build);
        Assert.assertEquals(FileUtils.readFileToString(file, Charset.forName(fileEncoding)), "Hello my name is John");
    }

}