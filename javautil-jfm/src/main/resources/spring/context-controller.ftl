<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="
http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd">
	
	<#assign controllerClassName = "${bean.simpleName}ReportAndFormController" />
	<#if bean.packageName?has_content>
		<#assign controllerClassName = "${bean.packageName}" />	
	</#if>
	<#assign controllerBeanId = "${bean.simpleName?uncap_first}" />
	
	<bean id="controllerMappingProperties"
		class="org.springframework.beans.factory.config.PropertiesFactoryBean">
		<property name="properties">
			<props>
				<prop key="/jfm/${bean.simpleName}/**/*">${controllerBeanId}</prop>
			</props>
		</property>
	</bean>	
	
	<bean id="${controllerBeanId}" class="${controllerClassName}">  
    	<property name="formView" value="/jfm/${bean.simpleName}/form"/>  
 		<property name="successView" value="/jfm/${bean.simpleName}/form"/>  
 		<property name="commandName" value="${bean.simpleName}"/>  
 		<property name="commandClass" value="${bean.name}" />
 		<!-- 
	 		<property name="validator">
	 			<bean class="com.custdata.examples.webapp.validator.PersonValidator" />
	 		</property>
 		-->
 	</bean>

</beans>