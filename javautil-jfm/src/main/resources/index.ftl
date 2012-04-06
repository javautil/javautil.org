<!-- Template for listing all items available to a user.  ie: all report groups, customer sets or broker fields -->

<@html.page title="TITLE">

<table border="0" cellpadding="0" cellspacing="0" class="layout">
	<tr>
		<td>			
			<div id="members" class="portal" style="width:25em;">
				<div class="title">
					<div class="text"><img src="/images/icons/page.png" /> All </div>
				</div>
				<div class="contents">
					<table  cellpadding="0" cellspacing="0" class="report" width="100%">
						<thead>
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>Name</th>
							</tr>
						</thead>
						<tbody>
							<#assign count=0>
							<#list bean.columns as column >
								<#if count = 1>
									<tr class="odd">
								<#else>
									<tr class="even">
									<#assign count=0>
								</#if>
									<td><a href="edit.html?id=${column.columnName}"><img src="/images/icons/edit.png" alt="Edit" title="TITLE"></a></td>
									<td><a href=""><img src="/images/icons/del.png" alt="Delete" title="TITLE"></a></td>
									<td>${column.columnName}</td>
								</tr>
								<#assign count=count+1 >
							</#list>
						</tbody>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>

</@html.page>