package com.zomu.t.epion.tropic.test.tool.selenium.runner;

import com.zomu.t.epion.tropic.test.tool.core.context.EvidenceInfo;
import com.zomu.t.epion.tropic.test.tool.core.command.runner.CommandRunner;
import com.zomu.t.epion.tropic.test.tool.selenium.command.WebDriverClick;
import com.zomu.t.epion.tropic.test.tool.selenium.util.WebElementUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;

import java.util.Map;

public class WebDriverSelectRunner implements CommandRunner<WebDriverClick> {
    @Override
    public void execute(
            WebDriverClick process,
            Map<String, Object> globalScopeVariables,
            Map<String, Object> scenarioScopeVariables,
            Map<String, Object> flowScopeVariables,
            Map<String, EvidenceInfo> evidences,
            Logger logger) throws Exception {
        WebDriver driver = WebDriver.class.cast(globalScopeVariables.get(process.getRefWebDriver()));
        WebElement element =
                WebElementUtils.getInstance().findWebElement(driver, process.getSelector(), process.getTarget());
        if (element.isEnabled()) {
            Select select = new Select(element);
            select.selectByVisibleText(process.getValue());

        }
    }
}
