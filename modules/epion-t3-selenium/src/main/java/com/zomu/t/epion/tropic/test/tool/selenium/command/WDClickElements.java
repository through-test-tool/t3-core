package com.zomu.t.epion.tropic.test.tool.selenium.command;

import com.zomu.t.epion.tropic.test.tool.core.annotation.CommandDefinition;
import com.zomu.t.epion.tropic.test.tool.core.model.scenario.Command;
import com.zomu.t.epion.tropic.test.tool.selenium.runner.WDClickElementsRunner;
import lombok.Getter;
import lombok.Setter;
import org.apache.bval.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@CommandDefinition(id = "WDClickElements", runner = WDClickElementsRunner.class)
public class WDClickElements extends WDCommand {
    private Integer elementIndex;
}
