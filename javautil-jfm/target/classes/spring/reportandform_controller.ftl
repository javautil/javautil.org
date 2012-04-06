<#if arguments.packageName?has_content>
package ${arguments.packageName};

</#if>
import org.springframework.web.servlet.mvc.SimpleFormController;

<#assign className><#compress>
	<#if arguments.className?has_content>
		${arguments.className}
	<#else>
		${bean.simpleName}ReportAndFormController
	</#if>
</#compress></#assign>
public class ${className} extends SimpleFormController {

}