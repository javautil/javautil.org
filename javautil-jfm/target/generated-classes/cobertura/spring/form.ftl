<#noparse>[#ftl]</#noparse>
<#-- generated template for java class ${bean.name} -->
<form <#if arguments.htmlFormClass?has_content><#compress> 
	class="${arguments.htmlFormClass}"
</#compress></#if> action="${arguments.htmlFormAction}" method="${arguments.htmlFormMethod}">

	[@spring.showErrors separator="<br />" classOrStyle="errors" /]

	<#list bean.properties as property>
	<div class="field">
		<#assign fieldId = "${bean.simpleName}_${property.name}" />
		<label for="${fieldId}">${property.label}</label>
		<#assign fieldValue>
			<@jfm.emitEscaped var="${property.name}" />
		</#assign>
		<#assign fieldAttributes><#compress>
			id=\"${fieldId}\"
		</#compress></#assign>
		[@spring.formInput path="${fieldValue}" attributes="${fieldAttributes}" /]
	</div>
	</#list>
	
</form>