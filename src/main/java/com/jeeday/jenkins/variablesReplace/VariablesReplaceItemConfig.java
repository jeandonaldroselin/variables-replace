package com.jeeday.jenkins.variablesReplace;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;

public class VariablesReplaceItemConfig extends AbstractDescribableImpl<VariablesReplaceItemConfig> {

    private String name;
    private String value;
    private boolean hideVariableOnReplace = false;

    @DataBoundConstructor
    public VariablesReplaceItemConfig(String name, String value, boolean hideVariableOnReplace) {
        this.name = StringUtils.strip(name);
        this.value = StringUtils.strip(value);
        this.hideVariableOnReplace = hideVariableOnReplace;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
    
    public boolean getHideVariableOnReplace() {
        return hideVariableOnReplace;
    }

    @Symbol("variablesReplaceItemConfig")
    @Extension
    public static class DescriptorImpl extends Descriptor<VariablesReplaceItemConfig> {

        @Override
        public String getDisplayName() {
            return "";
        }

        public FormValidation doCheckName(@QueryParameter final String value) {
            if(value.length()==0)
                return FormValidation.error("Please set the variable.");
            try {
                Pattern.compile(value);
            } catch (PatternSyntaxException e) {
                return FormValidation.error("Syntax error: " + e.getMessage());
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckValue(@QueryParameter final String value) {
            return FormValidation.ok();
        }
    }
}
