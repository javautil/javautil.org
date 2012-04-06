<#noparse><#ftl></#noparse>
<#noparse><#import "spring.ftl" as springftl /></#noparse>
<#-- generated template for java class ${bean.name} -->
<form <#if arguments.htmlFormClass?has_content><#compress> 
	class='${arguments.htmlFormClass}'
</#compress></#if> action='${arguments.htmlFormAction}' method='${arguments.htmlFormMethod}'>	
	<table border="0">	
	<#list bean.columns as column>	
		<tr>
		<#assign fieldId = '${column.columnNameAsAttributeName}' />
		<td>${column.columnNameAsAttributeName}</td>
		<#assign fieldValue='${column.columnNameAsAttributeName}'/>
		<#assign fieldAttributes><#compress>
			id='${fieldId}' size='${column.columnSize}' maxlength='${column.columnSize}'
		</#compress></#assign>
		<td><#noparse><@springftl.formInput path=</#noparse>'${fieldValue}' attributes="${fieldAttributes}"<#noparse>/></#noparse></td>
		</tr>
	</#list>
	</table>
</form>