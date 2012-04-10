<!-- Template for editing a selected item by the user.  ie: a single report group, customer set or broker field -->

<@html.page title="Edit XXX">
<script type="text/javascript">	
	<#include "/js/dynamicedit-showhide.js" />	
</script>

<table>
	<tr>
		<td>
			<div class="portal" style="width: 25em;">
				<!-- DESCRIPTION OF FORM -->
				<form>
					<div class="title">
						<div class="text">
							Edit XXX
						</div>
						<div class="text" style="float:right; margin-right:.4em;">
							<input type="button" value="Rename XXX" onclick="showHide('showButtons');dynamicEdit(form);"/>
						</div>
					</div>
					<div class="contents">
						<table cellpadding="0" cellspacing="4" width="100%">
							<tr>
								<td colspan="3" class="dynamicEdit">
									<span class="fieldContents">${NAME}</span>
									<input style="display: none" size="20" id="ID" name="ID" type="text" />
								</td>
								<td align="right">
									<div id="showButtons" style="display:none;">
										<button type="submit" onClick="document.pressed=this.value" VALUE="save"><img src="/images/icons/tick.png" />Save</button>
										<button name="reset" type="reset" value="reset"><img src="/images/icons/cross.png" />Reset</button>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<hr>
					<div class="contents">
					<!-- DESCRIPTION OF FORM -->
						<form  action='/myContext/myServlet/myController/myView.html' method='post'>	
							<table border="0" cellpadding="0" cellspacing="0" class="layout">
								<tr>
									<td colspan="2">SECTION DESCR:</td>
								</tr>
								<#list bean.columns as column >
								<tr>
									<td><input type="checkbox"></td>
									<td>${column.columnName}</td>
								</tr>
								</#list>
								<tr>
									<td colspan="2">
										<div class="buttonsLeft">
											<button type="submit" value="Save"><img src="/images/icons/tick.png" />Save</button>
											<button type="reset" value="Reset"><img src="/images/icons/cross.png" />Reset</button>
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</form>
			</div>
		</td>
		<td valign="top">
			<div class="note" style="width:15em">
				NOTE.
			</div>
		</td>
	</tr>
</table>
</@html.page>