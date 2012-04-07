package org.javautil.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.Resource;

/**
 * Tests the FileSystemResourceResolver class, as the class name implies
 * 
 * @author bcm
 */
public class FileSystemResourceResolverTest {

	private final File directory = new File("src");

	private final File sampleFile = new File("src/myfile.txt");

	private final Log logger = LogFactory.getLog(getClass());

	private FileSystemResourceResolver resolver;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
	}

	@Before
	public void setup() throws Exception {
		resolver = new FileSystemResourceResolver(directory);
		resolver.afterPropertiesSet();
	}

	@Test
	public void testConstructors() throws Exception {
		resolver = new FileSystemResourceResolver();
		resolver.setRootPath(directory);
		resolver.afterPropertiesSet();
		Assert.assertEquals(directory.getCanonicalPath(), resolver
				.getRootPath().getCanonicalPath());
		Assert.assertEquals(directory.getName(), resolver.getName());
		resolver = new FileSystemResourceResolver(directory);
		resolver.afterPropertiesSet();
		Assert.assertEquals(directory.getName(), resolver.getName());
	}

	@Test(expected = IllegalStateException.class)
	public void testFailedConstructor() throws Exception {
		resolver = new FileSystemResourceResolver();
		resolver.setRootPath(null);
		resolver.afterPropertiesSet();
	}

	@Test
	public void testGetName() {
		Assert.assertEquals(directory.getName(), resolver.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetParentResource() throws Exception {
		final String path = new File("..").getAbsolutePath();
		logger.debug("path is " + path);
		final Resource resource = resolver.getResource("..");
		logger.error("getResource call did not error on parent "
				+ resource.getFile().getAbsolutePath());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetParentParentResource() throws Exception {
		final Resource resource = resolver.getResource("../..");
		logger.error("getResource call did not error on parent "
				+ resource.getFile().getAbsolutePath());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUrlFileResource() throws Exception {
		final Resource resource = resolver.getResource("file://myfile.txt");
		logger.error("did not prevent file:// url getResource call "
				+ resource.getFile().getAbsolutePath());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUrlClasspathResource() throws Exception {
		final Resource resource = resolver
				.getResource("classpath://myfile.txt");
		logger.error("did not prevent classpath:// url getResource call "
				+ resource.getFile().getAbsolutePath());
	}

	@Test
	public void testGetRootResource() throws Exception {
		final Resource resource = resolver.getResource("/");
		final String path = resource.getFile().getCanonicalPath();
		final String baseDirectoryPath = directory.getCanonicalPath();
		logger.debug(baseDirectoryPath + " == " + path);
		Assert.assertEquals(baseDirectoryPath, path);
	}

	@Test
	public void testGetResource() throws Exception {
		final String path = "test/java/org/javautil/io/FileSystemResourceResolverTest.java";
		final Resource resource = resolver.getResource(path);
		Assert.assertNotNull(resource);
		final String filePath = "src/test/java/org/javautil/io/FileSystemResourceResolverTest.java";
		final String classContents = IOUtils
				.readStringFromStream(new FileInputStream(filePath));
		Assert.assertEquals(classContents,
				IOUtils.readStringFromStream(resource.getInputStream()));
	}

	@Test
	public void testGetNonExistantResource() throws Exception {
		final String path = "main/java/org/javafool/cogufe/WeNeverEverTestBeforeRelease.java";
		final Resource resource = resolver.getResource(path);
		Assert.assertFalse(resource.exists());
	}

	@Test
	public void testGetChildResource() throws Exception {
		final Resource resource = resolver.getResource("myfile.txt");
		Assert.assertNotNull(resource);
		Assert.assertEquals(sampleFile.getCanonicalPath(), resource.getFile()
				.getCanonicalPath());
	}

	@Test
	public void testGetRelativePathToSelfResource() throws Exception {
		final Resource resource = resolver.getResource("../src/myfile.txt");
		Assert.assertNotNull(resource);
		Assert.assertEquals(sampleFile.getCanonicalPath(), resource.getFile()
				.getCanonicalPath());
	}

	@Test
	public void testGetRelativeSelfPathResource() throws Exception {
		final Resource resource = resolver.getResource("./myfile.txt");
		Assert.assertNotNull(resource);
		Assert.assertEquals(sampleFile.getCanonicalPath(), resource.getFile()
				.getCanonicalPath());
	}

	@Test
	public void testLeadingSlashResource() throws Exception {
		final Resource resource = resolver.getResource("/myfile.txt");
		Assert.assertNotNull(resource);
		Assert.assertEquals(sampleFile.getCanonicalPath(), resource.getFile()
				.getCanonicalPath());
	}

	@Test
	public void testGetResourceDirectory() throws Exception {
		final ResourceDirectory testDir = resolver.getResourceDirectory("test");
		Assert.assertNotNull(testDir);
		Assert.assertEquals("test", testDir.getName());
	}

	@Test
	public void testGetResourceDirectoryNestedResource() throws Exception {
		final ResourceDirectory testDir = resolver.getResourceDirectory("test");
		Assert.assertNotNull(testDir);
		Assert.assertEquals("test", testDir.getName());
		final Resource resource = testDir
				.getResource("java/org/javautil/io/FileSystemResourceResolverTest.java");
		Assert.assertNotNull(resource);
		Assert.assertTrue(resource.exists());
	}

	@Test(expected = IOException.class)
	public void testGetNotExistantResourceDirectory() throws Exception {
		final ResourceDirectory anotherDirectory = resolver
				.getResourceDirectory("ZZ:\\I\\DON'T\\EXIST!");
		logger.error("did not error on non-existant resource directory "
				+ anotherDirectory);
	}

	@Test
	public void testGetResourceDirectories() throws Exception {
		final Collection<ResourceDirectory> dirs = resolver
				.getResourceDirectories(true);
		Assert.assertTrue(dirs.size() > 10);
	}

	@Test
	public void testGetResourceDirectoriesFromPath() throws Exception {
		final Collection<ResourceDirectory> dirs = resolver
				.getResourceDirectories(false, "test/resources");
		Assert.assertEquals(2, dirs.size());
		Assert.assertEquals("org", dirs.iterator().next().getName());
	}

	@Test(expected = IOException.class)
	public void testGetNotExistantResourcesPath() throws Exception {
		resolver.getResources(true, "main/java/org/javafool/cogufe");
	}

	@Test
	public void testGetResources() throws Exception {
		final Collection<Resource> resources = resolver.getResources(true);
		Assert.assertTrue(resources.size() > 20);
	}

	@Test
	public void testGetResourcesFromPath() throws Exception {
		final Collection<Resource> resources = resolver.getResources(true,
				"test/java/org/javautil/io");
		boolean foundTestFile = false;
		for (final Resource resource : resources) {
			if (resource.getFilename().equals(
					"FileSystemResourceResolverTest.java")) {
				foundTestFile = true;
			}
		}
		Assert.assertTrue("test file not found", foundTestFile);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetClassLoader() throws Exception {
		resolver.getClassLoader();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSetClassLoader() throws Exception {
		resolver.setClassLoader(getClass().getClassLoader());
	}
}
