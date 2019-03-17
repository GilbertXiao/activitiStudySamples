package com.imooc.activiti.interceptor;

import org.activiti.engine.debug.ExecutionTreeUtil;
import org.activiti.engine.impl.agenda.AbstractOperation;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.logging.LogMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: activiti6-sample
 * @description: mdc
 * @author: GilbertXiao
 * @create: 2018-12-29 19:42
 **/
public class MDCCommandInvoker extends DebugCommandInvoker {
    private static final Logger logger= LoggerFactory.getLogger(MDCCommandInvoker.class);


    public void executeOperation(Runnable runnable) {
        boolean mdcEnabled= LogMDC.isMDCEnabled();
        if (runnable instanceof AbstractOperation) {
            AbstractOperation operation = (AbstractOperation)runnable;
            if (operation.getExecution() != null) {
                LogMDC.putMDCExecution(operation.getExecution());
            }
        }
        super.executeOperation(runnable);
        LogMDC.clear();
        if(!mdcEnabled ){
            LogMDC.setMDCEnabled(false);
        }
    }
}
