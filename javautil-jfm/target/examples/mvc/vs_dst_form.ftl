<#ftl>
<#import "spring.ftl" as springftl />
<form  action='/myContext/myServlet/myController/myView.html' method='post'>	
	<table border="0">	

Expression bean is undefined on line 8, column 16 in org.javautil.jfm.mains.mvc.SpringTableMetaDataFTLFormGenerator@b48b11.
The problematic instruction:
----------
==> list bean.columns as column [on line 8, column 9 in org.javautil.jfm.mains.mvc.SpringTableMetaDataFTLFormGenerator@b48b11]
----------

Java backtrace for programmers:
----------
freemarker.core.InvalidReferenceException: Expression bean is undefined on line 8, column 16 in org.javautil.jfm.mains.mvc.SpringTableMetaDataFTLFormGenerator@b48b11.
	at freemarker.core.TemplateObject.assertNonNull(TemplateObject.java:124)
	at freemarker.core.TemplateObject.invalidTypeException(TemplateObject.java:134)
	at freemarker.core.Dot._getAsTemplateModel(Dot.java:78)
	at freemarker.core.Expression.getAsTemplateModel(Expression.java:89)
	at freemarker.core.IteratorBlock.accept(IteratorBlock.java:94)
	at freemarker.core.Environment.visit(Environment.java:209)
	at freemarker.core.MixedContent.accept(MixedContent.java:92)
	at freemarker.core.Environment.visit(Environment.java:209)
	at freemarker.core.Environment.process(Environment.java:189)
	at freemarker.template.Template.process(Template.java:237)
	at org.javautil.jfm.mains.mvc.SpringTableMetaDataFTLFormGenerator.render(SpringTableMetaDataFTLFormGenerator.java:85)
	at org.javautil.jfm.mains.mvc.SpringTableMetaDataFTLFormGenerator.process(SpringTableMetaDataFTLFormGenerator.java:52)
	at org.javautil.jfm.mains.mvc.SpringTableMetaDataFTLFormGenerator.main(SpringTableMetaDataFTLFormGenerator.java:135)
	at org.javautil.jfm.SpringTableMetaDataFormGeneratorTest.test5(SpringTableMetaDataFormGeneratorTest.java:67)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at org.junit.internal.runners.TestMethod.invoke(TestMethod.java:59)
	at org.junit.internal.runners.MethodRoadie.runTestMethod(MethodRoadie.java:98)
	at org.junit.internal.runners.MethodRoadie$2.run(MethodRoadie.java:79)
	at org.junit.internal.runners.MethodRoadie.runBeforesThenTestThenAfters(MethodRoadie.java:87)
	at org.junit.internal.runners.MethodRoadie.runTest(MethodRoadie.java:77)
	at org.junit.internal.runners.MethodRoadie.run(MethodRoadie.java:42)
	at org.junit.internal.runners.JUnit4ClassRunner.invokeTestMethod(JUnit4ClassRunner.java:88)
	at org.junit.internal.runners.JUnit4ClassRunner.runMethods(JUnit4ClassRunner.java:51)
	at org.junit.internal.runners.JUnit4ClassRunner$1.run(JUnit4ClassRunner.java:44)
	at org.junit.internal.runners.ClassRoadie.runUnprotected(ClassRoadie.java:27)
	at org.junit.internal.runners.ClassRoadie.runProtected(ClassRoadie.java:37)
	at org.junit.internal.runners.JUnit4ClassRunner.run(JUnit4ClassRunner.java:42)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:50)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:467)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:683)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:390)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:197)
