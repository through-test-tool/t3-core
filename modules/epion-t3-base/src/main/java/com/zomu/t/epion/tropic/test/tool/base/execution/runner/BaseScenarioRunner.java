package com.zomu.t.epion.tropic.test.tool.base.execution.runner;

import com.zomu.t.epion.tropic.test.tool.base.message.BaseMessages;
import com.zomu.t.epion.tropic.test.tool.base.execution.reporter.BaseScenarioReporter;
import com.zomu.t.epion.tropic.test.tool.base.context.BaseContext;
import com.zomu.t.epion.tropic.test.tool.core.execution.runner.ProcessRunner;
import com.zomu.t.epion.tropic.test.tool.core.execution.runner.ScenarioRunner;
import com.zomu.t.epion.tropic.test.tool.core.type.ScenarioExecuteStatus;
import com.zomu.t.epion.tropic.test.tool.core.exception.SystemException;
import com.zomu.t.epion.tropic.test.tool.core.context.execute.ExecuteProcess;
import com.zomu.t.epion.tropic.test.tool.core.context.execute.ExecuteScenario;
import com.zomu.t.epion.tropic.test.tool.core.type.ProcessStatus;
import com.zomu.t.epion.tropic.test.tool.core.type.ScenarioScopeVariables;
import com.zomu.t.epion.tropic.test.tool.core.util.ExecutionFileUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author takashno
 */
@Slf4j
public class BaseScenarioRunner implements ScenarioRunner<BaseContext> {

    /**
     * プロセス実行処理.
     */
    private final ProcessRunner processRunner;

    /**
     * コンストラクタ.
     */
    public BaseScenarioRunner() {
        this.processRunner = new BaseProcessRunner();
    }


    /**
     * {@inheritDoc}
     *
     * @param context
     */
    @Override
    public void execute(BaseContext context) {

        // 存在チェック
        if (context.getExecuteContext() == null || context.getExecuteContext().getScenarios() == null) {
            // システムエラー扱い（きちんとしたルートでの実行ではない）
            throw new SystemException(BaseMessages.BASE_ERR_0001);
        }


        // 全てのシナリオを実行する
        for (ExecuteScenario scenario : context.getExecuteContext().getScenarios()) {

            runScenario(context, scenario);

        }


    }

    private void runScenario(final BaseContext context, final ExecuteScenario scenario) {

        // シナリオ実行開始時間を設定
        scenario.setStart(LocalDateTime.now());

        try {

            // シナリオ開始ログ出力
            outputStartScenarioLog(context, scenario);

            // 結果ディレクトリの作成
            ExecutionFileUtils.createScenarioResultDirectory(context, scenario);

            // シナリオスコープの変数を設定
            settingScenarioVariables(context, scenario);

            for (ExecuteProcess process : scenario.getProcesses()) {
                this.processRunner.execute(context, scenario, process);

                if (process.getStatus() != ProcessStatus.SUCCESS) {
                    scenario.setStatus(ScenarioExecuteStatus.FAIL);
                    return;
                }

            }

            // プロセス成功
            scenario.setStatus(ScenarioExecuteStatus.SUUCESS);

        } catch (Throwable t) {

            // 発生したエラーを設定
            scenario.setError(t);

            // シナリオ失敗
            scenario.setStatus(ScenarioExecuteStatus.FAIL);

        } finally {

            // 掃除
            cleanScenarioVariables(context, scenario);

            // シナリオ実行終了時間を設定
            scenario.setEnd(LocalDateTime.now());

            // 所用時間を設定
            scenario.setDuration(Duration.between(scenario.getStart(), scenario.getEnd()));

            // シナリオ終了ログ出力
            outputEndScenarioLog(context, scenario);

            // レポート出力
            report(context, scenario);
        }

    }

    /**
     * シナリオスコープの変数を設定する.
     *
     * @param context
     * @param scenario
     */
    private void settingScenarioVariables(final BaseContext context, final ExecuteScenario scenario) {
        scenario.getScenarioVariables().put(
                ScenarioScopeVariables.SCENARIO_DIR.getName(),
                context.getOriginal().getScenarioPlacePaths().get(scenario.getInfo().getId()));
        scenario.getScenarioVariables().put(
                ScenarioScopeVariables.EVIDENCE_DIR.getName(),
                scenario.getEvidencePath());
        scenario.getScenarioVariables().put(
                ScenarioScopeVariables.CURRENT_SCENARIO.getName(),
                scenario.getFqsn());
    }

    /**
     * シナリオスコープの変数を掃除する.
     *
     * @param context
     * @param scenario
     */
    private void cleanScenarioVariables(final BaseContext context, final ExecuteScenario scenario) {
        scenario.getScenarioVariables().remove(
                ScenarioScopeVariables.SCENARIO_DIR.getName());
        scenario.getScenarioVariables().remove(
                ScenarioScopeVariables.EVIDENCE_DIR.getName());
        scenario.getScenarioVariables().remove(
                ScenarioScopeVariables.CURRENT_SCENARIO.getName());
    }

    /**
     * 結果ディレクトリが未作成であった場合に、作成する.
     *
     * @param context
     */
    private void createResultDirectory(final BaseContext context, final ExecuteScenario scenario) {
        ExecutionFileUtils.createResultDirectory(context);
    }

    private void report(final BaseContext context, final ExecuteScenario scenario) {
        BaseScenarioReporter.getInstance().scenarioReport(context, scenario);
    }


    /**
     * シナリオ開始ログ出力.
     *
     * @param context
     * @param scenario
     */
    protected void outputStartScenarioLog(BaseContext context, ExecuteScenario scenario) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n######################################################################################\n");
        sb.append("Start Scenario.\n");
        sb.append("Scenario ID         : ");
        sb.append(scenario.getInfo().getId());
        sb.append("\n");
        sb.append("Execute Scenario ID : ");
        sb.append(scenario.getExecuteScenarioId());
        sb.append("\n");
        sb.append("######################################################################################");
        log.info(sb.toString());
    }

    /**
     * シナリオ終了ログ出力.
     *
     * @param context
     * @param scenario
     */
    protected void outputEndScenarioLog(BaseContext context, ExecuteScenario scenario) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n######################################################################################\n");
        sb.append("End Scenario.\n");
        sb.append("Scenario ID         : ");
        sb.append(scenario.getInfo().getId());
        sb.append("\n");
        sb.append("Execute Scenario ID : ");
        sb.append(scenario.getExecuteScenarioId());
        sb.append("\n");
        sb.append("######################################################################################");
        if (scenario.getStatus() == ScenarioExecuteStatus.SUUCESS) {
            log.info(sb.toString());
        } else if (scenario.getStatus() == ScenarioExecuteStatus.FAIL) {
            log.error(sb.toString());
        } else {
            log.warn(sb.toString());
        }

    }

}
