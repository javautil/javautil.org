<#noparse>[#ftl]</#noparse>
<table class="${arguments.htmlTableClass}">
	<thead>
		<tr>
		<#list bean.columns as column>
			<th>${column.columnNameAsAttributeName}</th>
		</#list>
		</tr>
	</thead>
	<tbody>
	<#noparse><#list beans as bean></#noparse>
		<tr>
		<#list bean.columns as column>
			<td>${column.columnNameAsAttributeName}</td>
		</#list>
		</tr>
	<#noparse></#list></#noparse>
	</tbody>
</table>