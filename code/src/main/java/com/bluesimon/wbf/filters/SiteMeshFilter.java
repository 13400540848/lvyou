package com.bluesimon.wbf.filters;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * Created by Django on 2017/12/16.
 */
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        //customer decorator
        builder.addTagRuleBundle(new CustomTagRuleBundle());
    }

    public static class CustomTagRuleBundle implements TagRuleBundle {

        @Override
        public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
            defaultState.addRule("customscript", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("customscript"), false));
        }

        @Override
        public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {

        }
    }
}
