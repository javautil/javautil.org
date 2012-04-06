package org.javautil.exceptionprocessing;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class ExceptionProcessingController extends AbstractCommandController {

	public ExceptionProcessingController() {
		setCommandClass(ExceptionProcessingServerArgs.class);
	}

	@Override
	protected ModelAndView handle(final HttpServletRequest arg0,
			final HttpServletResponse arg1, final Object command,
			final BindException errors) throws Exception {

		final ExceptionProcessingServerArgs args = (ExceptionProcessingServerArgs) command;
		final ExceptionRuleService eRuleSer = new ExceptionRuleServiceImpl();
		final boolean processSuccess = eRuleSer.process(args);
		final String runNumber = args.getRunNumber().toString();
		final Map<String, String> model = new HashMap<String, String>();
		model.put("runNbr", runNumber);
		// if need to display exceptionCount to page then need to activate below
		// method.
		// model.put("exception", args.getExceptionCount());
		// Need to set poolsize in ExceptionProcessingServerArgs to generate
		// number of threads while running exception Processing.
		// model.put("threadPoolSize" , args.getThreadPoolSize());
		if (processSuccess) {
			return new ModelAndView("exceptionprocessing.jsp", model);
		} else {
			return new ModelAndView("errorInExceptionProcessing.jsp", model);
		}
	}
}
