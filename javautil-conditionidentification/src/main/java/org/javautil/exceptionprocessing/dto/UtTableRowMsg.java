package org.javautil.exceptionprocessing.dto;

// Generated Jun 7, 2009 8:20:16 PM by Hibernate Tools 3.2.2.GA

/**
 * UtTableRowMsg generated by hbm2java
 */
public class UtTableRowMsg implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UtTableRowMsgId id;
	private UtTableRule utTableRule;
	private UtRuleGrpRun utRuleGrpRun;
	private String msg;
	private Integer utUserNbrAllow;
	private String dispCdEnum;
	private Integer utUserNbrDisp;

	public UtTableRowMsg() {
	}

	public UtTableRowMsg(final UtTableRowMsgId id,
			final UtTableRule utTableRule, final UtRuleGrpRun utRuleGrpRun) {
		this.id = id;
		this.utTableRule = utTableRule;
		this.utRuleGrpRun = utRuleGrpRun;
	}

	public UtTableRowMsg(final UtTableRowMsgId id,
			final UtTableRule utTableRule, final UtRuleGrpRun utRuleGrpRun,
			final String msg, final Integer utUserNbrAllow,
			final String dispCdEnum, final Integer utUserNbrDisp) {
		this.id = id;
		this.utTableRule = utTableRule;
		this.utRuleGrpRun = utRuleGrpRun;
		this.msg = msg;
		this.utUserNbrAllow = utUserNbrAllow;
		this.dispCdEnum = dispCdEnum;
		this.utUserNbrDisp = utUserNbrDisp;
	}

	public UtTableRowMsgId getId() {
		return this.id;
	}

	public void setId(final UtTableRowMsgId id) {
		this.id = id;
	}

	public UtTableRule getUtTableRule() {
		return this.utTableRule;
	}

	public void setUtTableRule(final UtTableRule utTableRule) {
		this.utTableRule = utTableRule;
	}

	public UtRuleGrpRun getUtRuleGrpRun() {
		return this.utRuleGrpRun;
	}

	public void setUtRuleGrpRun(final UtRuleGrpRun utRuleGrpRun) {
		this.utRuleGrpRun = utRuleGrpRun;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(final String msg) {
		this.msg = msg;
	}

	public Integer getUtUserNbrAllow() {
		return this.utUserNbrAllow;
	}

	public void setUtUserNbrAllow(final Integer utUserNbrAllow) {
		this.utUserNbrAllow = utUserNbrAllow;
	}

	public String getDispCdEnum() {
		return this.dispCdEnum;
	}

	public void setDispCdEnum(final String dispCdEnum) {
		this.dispCdEnum = dispCdEnum;
	}

	public Integer getUtUserNbrDisp() {
		return this.utUserNbrDisp;
	}

	public void setUtUserNbrDisp(final Integer utUserNbrDisp) {
		this.utUserNbrDisp = utUserNbrDisp;
	}

}
