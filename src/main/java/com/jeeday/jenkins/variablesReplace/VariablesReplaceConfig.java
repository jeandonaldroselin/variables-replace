package com.jeeday.jenkins.variablesReplace;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;

public class VariablesReplaceConfig extends AbstractDescribableImpl<VariablesReplaceConfig> {

    private String filePath;
    private String fileEncoding = "UTF-8";
    private String variablesPrefix = "#{";
    private String variablesSuffix = "}#";
    private String emptyValue = "";
    private List<VariablesReplaceItemConfig> configs;

    @DataBoundConstructor
    public VariablesReplaceConfig(String filePath, String fileEncoding, String variablesPrefix, String variablesSuffix, String emptyValue, List<VariablesReplaceItemConfig> configs) {
        this.filePath = StringUtils.strip(filePath);
        this.fileEncoding = fileEncoding;
        this.variablesPrefix = variablesPrefix;
        this.variablesSuffix = variablesSuffix;
        this.emptyValue = emptyValue;
        this.configs = configs;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public String getVariablesPrefix() {
        return variablesPrefix;
    }

    public String getVariablesSuffix() {
        return variablesSuffix;
    }

    public String getEmptyValue() {
        return emptyValue;
    }

    public List<VariablesReplaceItemConfig> getConfigs() {
        return configs;
    }

    @Symbol("variablesReplaceConfig")
    @Extension
    public static class DescriptorImpl extends Descriptor<VariablesReplaceConfig> {

        @Override
        public String getDisplayName() {
            return "";
        }

        public FormValidation doCheckFilePath(@QueryParameter StaplerRequest req, @QueryParameter StaplerResponse rsp, @QueryParameter final String value) {
            if(value.length() == 0) {
                return FormValidation.error("Please set the target files.");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckVariablesPrefix(@QueryParameter StaplerRequest req, @QueryParameter StaplerResponse rsp, @QueryParameter final String value) {
            if(value.length() == 0) {
                return FormValidation.error("Please set the variables prefix.");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckVariablesSuffix(@QueryParameter StaplerRequest req, @QueryParameter StaplerResponse rsp, @QueryParameter final String value) {
            if(value.length() == 0) {
                return FormValidation.error("Please set the variables suffix.");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckEmptyValue(@QueryParameter StaplerRequest req, @QueryParameter StaplerResponse rsp, @QueryParameter final String value) {
            return FormValidation.ok();
        }

    }
}