package com.zomu.t.epion.tropic.test.tool.basic.command.runner;

import com.zomu.t.epion.tropic.test.tool.basic.command.model.FileCopy;
import com.zomu.t.epion.tropic.test.tool.core.context.EvidenceInfo;
import com.zomu.t.epion.tropic.test.tool.core.command.runner.CommandRunner;
import org.slf4j.Logger;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 *
 */
public class FileCopyRunner implements CommandRunner<FileCopy> {

    /**
     *
     * @param process
     * @param globalScopeVariables
     * @param scenarioScopeVariables
     * @param flowScopeVariables
     * @param evidences
     * @param logger
     * @throws Exception
     */
    @Override
    public void execute(final FileCopy process,
                        final Map<String, Object> globalScopeVariables,
                        final Map<String, Object> scenarioScopeVariables,
                        final Map<String, Object> flowScopeVariables,
                        final Map<String, EvidenceInfo> evidences,
                        final Logger logger) throws Exception {

        logger.info("start FileCopy");

        Path from = Paths.get(process.getFrom());
        try (OutputStream os = new FileOutputStream(process.getTo())) {
            Files.copy(from, os);
        } finally {
            logger.info("end FileCopy");
        }

//        throw new RuntimeException("擬似エラー");
    }

}
