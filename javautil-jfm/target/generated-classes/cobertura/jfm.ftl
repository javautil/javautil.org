<#---
  - @namespace jfm
  -->
<#macro emitEscaped var><#compress>
	<#noparse>${</#noparse>${var}<#noparse>}</#noparse>
</#compress></#macro>