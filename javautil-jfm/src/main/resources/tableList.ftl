<#noparse>[#ftl]</#noparse>

<#macro emitEscaped var><#compress>
	<#noparse>${</#noparse>${var}<#noparse>}</#noparse>
</#compress></#macro>
<#-- generated template for java class ${bean.name} -->
<table>
	<thead>
		<tr>
	
		</tr>
	</thead>
	<tbody>
	<#list bean.columns as column >
		<tr>
			<td> var="${column.columnName}" </td>
				<div class="field">
		<#assign fieldId = "${column.columnName}" />
		<label for="${fieldId}">${column.columnName}</label>
		<#assign fieldValue>
			<@emitEscaped var="${column.columnName}" />
		</#assign>
		<#assign fieldAttributes><#compress>
			id='${fieldId}'
		</#compress></#assign>
		[@spring.formInput path="${fieldValue}" attributes="${fieldAttributes}" /]
	</div>
		</tr>
	</#list>
	</tbody>
</table>