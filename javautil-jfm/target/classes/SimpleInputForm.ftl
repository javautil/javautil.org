<#noparse>[#ftl]</#noparse>

<#macro emitEscaped var><#compress>
	<#noparse>${</#noparse>${var}<#noparse>}</#noparse>
</#compress></#macro>
<#-- generated template for java class ${bean.name} -->
<table>
	<tbody>
	<#list bean.columns as column >
		<tr>
			<td><input type='text' name='${column.columnName}' size='${column.columnSize}' maxlength='${column.columnSize}' />  </td>
		</tr>
	</#list>
	</tbody>
</table>