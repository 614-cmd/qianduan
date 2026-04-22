package org.flowable.engine.impl.variable;

/**
 * 修复 flowable 6.7.2版本bug
 */

import org.flowable.bpmn.model.BoundaryEvent;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.variable.api.types.ValueFields;
import org.flowable.variable.api.types.VariableType;

import java.util.List;

public class ParallelMultiInstanceLoopVariableType implements VariableType {
    public static final String TYPE_NAME = "bpmnParallelMultiInstanceCompleted";
    protected static final String NUMBER_OF_INSTANCES = "nrOfInstances";
    protected final ProcessEngineConfigurationImpl processEngineConfiguration;

    public ParallelMultiInstanceLoopVariableType(ProcessEngineConfigurationImpl processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    public String getTypeName() {
        return "bpmnParallelMultiInstanceCompleted";
    }

    public boolean isCachable() {
        return false;
    }

    public boolean isAbleToStore(Object value) {
        return value instanceof ParallelMultiInstanceLoopVariable;
    }

    public void setValue(Object value, ValueFields valueFields) {
        if ( value instanceof ParallelMultiInstanceLoopVariable ) {
            valueFields.setTextValue ( ((ParallelMultiInstanceLoopVariable) value).getExecutionId ( ) );
            valueFields.setTextValue2 ( ((ParallelMultiInstanceLoopVariable) value).getType ( ) );
        } else {
            valueFields.setTextValue ( null );
            valueFields.setTextValue2 ( null );
        }

    }

    public Object getValue(ValueFields valueFields) {
        CommandContext commandContext = CommandContextUtil.getCommandContext ( );
        return commandContext != null ? this.getValue ( valueFields, commandContext ) : this.processEngineConfiguration.getCommandExecutor ( ).execute ( (context) -> {
            return this.getValue ( valueFields, context );
        } );
    }

    protected Object getValue(ValueFields valueFields, CommandContext commandContext) {
        String multiInstanceRootId = valueFields.getTextValue ( );
        String type = valueFields.getTextValue2 ( );
        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration ( commandContext );
        ExecutionEntityManager executionEntityManager = processEngineConfiguration.getExecutionEntityManager ( );
        ExecutionEntity multiInstanceRootExecution = executionEntityManager.findById ( multiInstanceRootId );
        if ( multiInstanceRootExecution == null ) {
            return 0;
        }
        List <? extends ExecutionEntity> childExecutions = multiInstanceRootExecution.getExecutions ( );
        int nrOfActiveInstances = (int) childExecutions.stream ( ).filter ( (execution) -> {
            return execution.isActive ( ) && !(execution.getCurrentFlowElement ( ) instanceof BoundaryEvent);
        } ).count ( );
        if ( "completed".equals ( type ) ) {
            Object nrOfInstancesValue = multiInstanceRootExecution.getVariable ( "nrOfInstances" );
            int nrOfInstances = (Integer) (nrOfInstancesValue != null ? nrOfInstancesValue : 0);
            return nrOfInstances - nrOfActiveInstances;
        } else {
            return "active".equals ( type ) ? nrOfActiveInstances : 0;
        }
    }

    public boolean isReadOnly() {
        return true;
    }
}
