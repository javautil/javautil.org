<#noparse>[#ftl]</#noparse>
<#-- generated template for java class ${bean.name} -->
<table class="${arguments.htmlTableClass}">
	<thead>
		<tr>
		<#list bean.properties as property>
			<th>${property.heading}</th>
		</#list>
		</tr>
	</thead>
	<tbody>
	[#list ${bean.simpleName}s as ${bean.simpleName}]
		<tr>
		<#list bean.properties as property>
			<td><@jfm.emitEscaped var="${bean.simpleName}.${property.name}" /></td>
		</#list>
		</tr>
	[/#list]
	</tbody>
</table>