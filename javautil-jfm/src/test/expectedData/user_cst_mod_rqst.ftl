<#ftl>
<#import "spring.ftl" as springftl />
<form  action='/myContext/myServlet/myController/myView.html' method='post'>	
	<table border="0">	
		<tr>
		<td>productId</td>
		<td><@springftl.formInput path='productId' attributes="id='productId' size='9' maxlength='9'"/></td>
		</tr>
		<tr>
		<td>upc10</td>
		<td><@springftl.formInput path='upc10' attributes="id='upc10' size='10' maxlength='10'"/></td>
		</tr>
		<tr>
		<td>productStatus</td>
		<td><@springftl.formInput path='productStatus' attributes="id='productStatus' size='1' maxlength='1'"/></td>
		</tr>
		<tr>
		<td>descr</td>
		<td><@springftl.formInput path='descr' attributes="id='descr' size='50' maxlength='50'"/></td>
		</tr>
		<tr>
		<td>narrative</td>
		<td><@springftl.formInput path='narrative' attributes="id='narrative' size='2,147,483,647' maxlength='2,147,483,647'"/></td>
		</tr>
	</table>
</form>
