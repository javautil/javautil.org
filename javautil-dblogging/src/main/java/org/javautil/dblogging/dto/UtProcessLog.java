package org.javautil.dblogging.dto;
// Generated Sep 10, 2011 1:41:31 PM by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UtProcessLog generated by hbm2java
 */
public class UtProcessLog  implements java.io.Serializable {


     private UtProcessLogId id;
     private UtProcessStatus utProcessStatus;
     private Integer utProcessLogNbr;
     private String logMsgId;
     private String logMsg;
     private String logMsgClob;
     private Date logMsgTs;
     private BigDecimal elapsedSeconds;
     private String callerName;
     private Integer lineNbr;
     private String callStack;
     private Byte logLevel;
     private Set<UtProcessStat> utProcessStats = new HashSet<UtProcessStat>(0);

    public UtProcessLog() {
    }

	
    public UtProcessLog(UtProcessLogId id, UtProcessStatus utProcessStatus) {
        this.id = id;
        this.utProcessStatus = utProcessStatus;
    }
    public UtProcessLog(UtProcessLogId id, UtProcessStatus utProcessStatus, Integer utProcessLogNbr, String logMsgId, String logMsg, String logMsgClob, Date logMsgTs, BigDecimal elapsedSeconds, String callerName, Integer lineNbr, String callStack, Byte logLevel, Set<UtProcessStat> utProcessStats) {
       this.id = id;
       this.utProcessStatus = utProcessStatus;
       this.utProcessLogNbr = utProcessLogNbr;
       this.logMsgId = logMsgId;
       this.logMsg = logMsg;
       this.logMsgClob = logMsgClob;
       this.logMsgTs = logMsgTs;
       this.elapsedSeconds = elapsedSeconds;
       this.callerName = callerName;
       this.lineNbr = lineNbr;
       this.callStack = callStack;
       this.logLevel = logLevel;
       this.utProcessStats = utProcessStats;
    }
   
    public UtProcessLogId getId() {
        return this.id;
    }
    
    public void setId(UtProcessLogId id) {
        this.id = id;
    }
    public UtProcessStatus getUtProcessStatus() {
        return this.utProcessStatus;
    }
    
    public void setUtProcessStatus(UtProcessStatus utProcessStatus) {
        this.utProcessStatus = utProcessStatus;
    }
    public Integer getUtProcessLogNbr() {
        return this.utProcessLogNbr;
    }
    
    public void setUtProcessLogNbr(Integer utProcessLogNbr) {
        this.utProcessLogNbr = utProcessLogNbr;
    }
    public String getLogMsgId() {
        return this.logMsgId;
    }
    
    public void setLogMsgId(String logMsgId) {
        this.logMsgId = logMsgId;
    }
    public String getLogMsg() {
        return this.logMsg;
    }
    
    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }
    public String getLogMsgClob() {
        return this.logMsgClob;
    }
    
    public void setLogMsgClob(String logMsgClob) {
        this.logMsgClob = logMsgClob;
    }
    public Date getLogMsgTs() {
        return this.logMsgTs;
    }
    
    public void setLogMsgTs(Date logMsgTs) {
        this.logMsgTs = logMsgTs;
    }
    public BigDecimal getElapsedSeconds() {
        return this.elapsedSeconds;
    }
    
    public void setElapsedSeconds(BigDecimal elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }
    public String getCallerName() {
        return this.callerName;
    }
    
    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }
    public Integer getLineNbr() {
        return this.lineNbr;
    }
    
    public void setLineNbr(Integer lineNbr) {
        this.lineNbr = lineNbr;
    }
    public String getCallStack() {
        return this.callStack;
    }
    
    public void setCallStack(String callStack) {
        this.callStack = callStack;
    }
    public Byte getLogLevel() {
        return this.logLevel;
    }
    
    public void setLogLevel(Byte logLevel) {
        this.logLevel = logLevel;
    }
    public Set<UtProcessStat> getUtProcessStats() {
        return this.utProcessStats;
    }
    
    public void setUtProcessStats(Set<UtProcessStat> utProcessStats) {
        this.utProcessStats = utProcessStats;
    }




}

