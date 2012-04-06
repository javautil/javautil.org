package org.javautil.exceptionprocessing;

import java.sql.Connection;

import org.hibernate.Transaction;
import org.javautil.exceptionprocessing.dto.GttUtTableRowMsg;
import org.javautil.exceptionprocessing.dto.UtRuleGrpRun;

public interface ExceptionProcessingRulesDAO {

	public abstract UtRuleGrpRun getUtGrpRun(Integer runNbr);

	public abstract void commit(Transaction tx);

	public abstract Transaction beginTransaction();

	public abstract void save(GttUtTableRowMsg m);

	public abstract void flush();

	public abstract void evict(Class<GttUtTableRowMsg> class1);

	public abstract Connection connection();

	public abstract void closeSession();

	public abstract void clearTemporary();

}