package org.javautil.io;

import java.io.FileInputStream;
import java.util.Collection;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.javautil.lang.ThreadHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Tests the ClassPathResourceResolver class, as the class name implies
 * 
 * @author bcm
 */
public class ClassPathResourceResolverTest {

	private final String classpath = "org/javautil/io";

	private final Log logger = LogFactory.getLog(getClass());

	private ClassPathResourceResolver resolver = null;

	@BeforeClass
	public static void setupClass() {
		BasicConfigurator.configure();
	}

	@Before
	public void setup() throws Exception {
		resolver = new ClassPathResourceResolver(
				ClassPathResourceResolver.class);
		resolver.afterPropertiesSet();
	}

	@Test
	public void testConstructors() throws Exception {
		resolver = new ClassPathResourceResolver();
		resolver.setClassLoader(getClass().getClassLoader());
		resolver.setRootPath(classpath);
		resolver.afterPropertiesSet();
		Assert.assertEquals(classpath, resolver.getRootPath());
		final String[] names = classpath.split("\\/");
		Assert.assertEquals(names[names.length - 1], resolver.getName());
		resolver = new ClassPathResourceResolver(getClass());
		resolver.afterPropertiesSet();
		Assert.assertEquals(names[names.length - 1], resolver.getName());
	}

	@Test(expected = IllegalStateException.class)
	public void testNoInitializingBeanAfterPropertiesSet() throws Exception {
		final ClassPathResourceResolver resolver = new ClassPathResourceResolver(
				ClassPathResourceResolver.class);
		IOUtils.readStringFromStream(resolver.getResource(
				"ClassPathResourceResolver.class").getInputStream());
	}

	@Test
	public void testGetName() {
		final String[] names = classpath.split("\\/");
		Assert.assertEquals(names[names.length - 1], resolver.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetParentResource() throws Exception {
		final Resource resource = resolver.getResource("..");
		logger.error("getResource call did not error on parent "
				+ resource.getURI().toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetParentParentResource() throws Exception {
		final Resource resource = resolver.getResource("../..");
		logger.error("getResource call did not error on parent "
				+ resource.getURI().toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUrlClassPathResource() throws Exception {
		final Resource resource = resolver
				.getResource("classpath://myfile.txt");
		logger.error("did not prevent classpath:// url getResource call "
				+ resource.getURI().toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUrlFileResource() throws Exception {
		final Resource resource = resolver.getResource("file://myfile.txt");
		logger.error("did not prevent file:// url getResource call "
				+ resource.getURI().toString());
	}

	@Test
	public void testGetRootResource() throws Exception {
		final Resource resource = resolver.getResource("/");
		final String path = resource.getURI().toString();
		logger.debug(classpath + " == " + path);
		Assert.assertEquals(new ClassPathResource(classpath).getURI()
				.toString(), path);
	}

	@Test
	public void testGetResource() throws Exception {
		final String path = "ClassPathResourceResolver.class";
		final Resource resource = resolver.getResource(path);
		Assert.assertNotNull(resource);
		final String filePath = "target/classes/org/javautil/io/ClassPathResourceResolver.class";
		final String classContents = IOUtils
				.readStringFromStream(new FileInputStream(filePath));
		Assert.assertEquals(classContents,
				IOUtils.readStringFromStream(resource.getInputStream()));
	}

	@Test
	public void testGetNonExistantResource() throws Exception {
		final String path = "org/javafool/cogufe/WeNeverEverTestBeforeRelease.java";
		final Resource resource = resolver.getResource(path);
		Assert.assertFalse(resource.exists());
	}

	@Test
	public void testGetChildResource() throws Exception {
		final Resource resource = resolver
				.getResource("ClassPathResourceResolver.class");
		Assert.assertNotNull(resource);
		Assert.assertTrue(resource.exists());
		Assert.assertEquals("ClassPathResourceResolver.class",
				resource.getFilename());
	}

	@Test
	public void testGetRelativePathToSelfResource() throws Exception {
		final Resource resource = resolver
				.getResource("../io/ClassPathResourceResolver.class");
		Assert.assertNotNull(resource);
		Assert.assertTrue(resource.exists());
		Assert.assertEquals("ClassPathResourceResolver.class",
				resource.getFilename());
	}

	@Test
	public void testGetRelativeSelfPathResource() throws Exception {
		final Resource resource = resolver
				.getResource("./ClassPathResourceResolver.class");
		Assert.assertNotNull(resource);
		Assert.assertTrue(resource.exists());
		Assert.assertEquals("ClassPathResourceResolver.class",
				resource.getFilename());
	}

	@Test
	public void testLeadingSlashGetResource() throws Exception {
		final Resource resource = resolver
				.getResource("/ClassPathResourceResolver.class");
		Assert.assertNotNull(resource);
		Assert.assertTrue(resource.exists());
		Assert.assertEquals("ClassPathResourceResolver.class",
				resource.getFilename());
	}

	@Test
	public void testGetResourceDirectory() throws Exception {
		ResourceDirectory testDir = resolver
				.getResourceDirectory("org/javautil/");
		Assert.assertNotNull(testDir);
		Assert.assertEquals("javautil", testDir.getName());
		testDir = resolver.getResourceDirectory("org/javautil");
		Assert.assertNotNull(testDir);
		Assert.assertEquals("javautil", testDir.getName());
	}

	@Test
	public void testGetResourceDirectoriesEnsureNoRootIncluded()
			throws Exception {
		Collection<ResourceDirectory> dirs = resolver.getResourceDirectories(
				false, "org/javautil");
		for (final ResourceDirectory dir : dirs) {
			if (dir.getName().equals("javautil")) {
				Assert.fail("found the parent dir javautil or an "
						+ "incorrectly named dir");
			}
		}
		resolver = new ClassPathResourceResolver("org/javautil");
		resolver.afterPropertiesSet();
		dirs = resolver.getResourceDirectories(false);
		for (final ResourceDirectory dir : dirs) {
			if (dir.getName().equals("javautil")) {
				Assert.fail("found the parent dir javautil or an "
						+ "incorrectly named dir");
			}
		}
	}

	@Test
	public void testGetResourceDirectoryNestedResource() throws Exception {
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"org");
		resolver.afterPropertiesSet();
		final ResourceDirectory testDir = resolver
				.getResourceDirectory("javautil");
		Assert.assertNotNull(testDir);
		Assert.assertEquals("javautil", testDir.getName());
		final Resource resource = testDir
				.getResource("io/ClassPathResourceResolver.class");
		Assert.assertNotNull(resource);
		Assert.assertTrue(resource.exists());
	}

	@Test
	public void testGetResourceDirectories() throws Exception {
		logger.debug("running " + ThreadHelper.getCallerMethodName());
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"org/javautil");
		resolver.afterPropertiesSet();
		final Collection<ResourceDirectory> dirs = resolver
				.getResourceDirectories(true);
		if (dirs.size() == 0) {
			Assert.fail("no dirs were found using " + resolver.getRootPath());
		}
		checkDirsForIoPackage(dirs);
	}

	@Test
	public void testGetResourceDirectoriesNoRepeats() throws Exception {
		logger.debug("running " + ThreadHelper.getCallerMethodName());
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"org/javautil");
		resolver.afterPropertiesSet();
		final Collection<ResourceDirectory> dirs = resolver
				.getResourceDirectories(false);
		int ioCount = 0;
		for (final ResourceDirectory dir : dirs) {
			if (dir.getName().equals("io")) {
				ioCount++;
			}
		}
		Assert.assertEquals(1, ioCount);
	}

	@Test
	public void testGetResourceDirectoriesEnsureNoParentPruning()
			throws Exception {
		logger.debug("running " + ThreadHelper.getCallerMethodName());
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"org/javautil");
		resolver.afterPropertiesSet();
		final Collection<ResourceDirectory> dirs = resolver
				.getResourceDirectories(false);
		for (final ResourceDirectory dir : dirs) {
			final ClassPathResourceResolver resolver = (ClassPathResourceResolver) dir;
			Assert.assertEquals("org/javautil", resolver.getRootPath()
					.substring(0, 12));
		}
	}

	@Test
	public void testGetResourceDirectoriesFromPath() throws Exception {
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"/");
		resolver.afterPropertiesSet();
		final Collection<ResourceDirectory> dirs = resolver
				.getResourceDirectories(false, "org/javautil");
		checkDirsForIoPackage(dirs);
	}

	@Test
	public void testGetResources() throws Exception {
		final Collection<Resource> resources = resolver.getResources(true);
		Assert.assertTrue(resources.size() > 0);
		for (final Resource resource : resources) {
			Assert.assertTrue(resource.getDescription() + " does not exist!",
					resource.exists());
		}
	}

	@Test
	public void testGetResourceDirectoriesNotResursive() throws Exception {
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"org/javautil");
		resolver.afterPropertiesSet();
		final Collection<ResourceDirectory> resourceDirs = resolver
				.getResourceDirectories(false);
		Integer slashes = null;
		for (final ResourceDirectory resourceDir : resourceDirs) {
			final ClassPathResourceResolver resolver = (ClassPathResourceResolver) resourceDir;
			logger.debug(resolver.getRootPath());
			final int slashCount = resolver.getRootPath().split("/").length;
			if (slashes == null) {
				slashes = slashCount;
			} else {
				Assert.assertEquals(slashes.intValue(), slashCount);
			}
		}
	}

	@Test
	public void testGetResourcesNotResursive() throws Exception {
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"org/javautil/text");
		resolver.afterPropertiesSet();
		final Collection<Resource> resources = resolver.getResources(false);
		Integer slashes = null;
		for (final Resource resource : resources) {
			final int slashCount = resource.getURI().toString().split("/").length;
			if (slashes == null) {
				slashes = slashCount;
			} else {
				Assert.assertEquals(slashes.intValue(), slashCount);
			}
		}
	}

	@Test
	public void testEnsureLeadingSlashAppendedToSearchPath() throws Exception {

		// we want to ensue that although "test" and "text" are valid resource
		// packages we do not have a bug that causes resources in both to appear
		// under the resource path "te"
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"org/javautil/text");
		resolver.afterPropertiesSet();
		Collection<Resource> resources = resolver.getResources(true);
		Assert.assertTrue(resources.size() > 0);
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"org/javautil/test");
		resolver.afterPropertiesSet();
		resources = resolver.getResources(true);
		Assert.assertTrue(resources.size() > 0);
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"org/javautil/te");
		resolver.afterPropertiesSet();
		resources = resolver.getResources(true);
		Assert.assertEquals(0, resources.size());

		// ensure nested relative path lookup works the same
		resolver = new ClassPathResourceResolver(getClass().getClassLoader(),
				"/");
		resolver.afterPropertiesSet();
		resources = resolver.getResources(true, "org/javautil/text");
		Assert.assertTrue(resources.size() > 0);
		resources = resolver.getResources(true, "org/javautil/test");
		Assert.assertTrue(resources.size() > 0);
		resources = resolver.getResources(true, "org/javautil/te");
		Assert.assertEquals(0, resources.size());
	}

	@Test
	public void testGetResourcesFromPath() throws Exception {
		final Collection<Resource> resources = resolver.getResources(true, "/");
		checkResourcesForThisClass(resources);
	}

	@Test
	public void testGetDirectoriesFromJarFile() throws Exception {
		resolver = new ClassPathResourceResolver();
		resolver.afterPropertiesSet();
		final Collection<ResourceDirectory> dirs = resolver
				.getResourceDirectories(true);
		boolean foundTestFile = false;
		final StringBuilder message = new StringBuilder();
		for (final ResourceDirectory _dir : dirs) {
			final ClassPathResourceResolver dir = (ClassPathResourceResolver) _dir;
			if (message.length() > 0) {
				message.append(", ");
			}
			message.append(message);
			// this test requires the commons logging jar to be in the classpath
			// if (resource.getDescription().contains("LogFactory")) {
			// System.err.println(resource.getDescription());
			// }
			if (dir.getRootPath().equals("org/apache/commons/logging")) {
				foundTestFile = true;
			}
		}
		Assert.assertTrue("LogFactory class not found: resources=" + message,
				foundTestFile);
	}

	@Test
	public void testGetDirectoriesFromJarFileFromPath() throws Exception {
		resolver = new ClassPathResourceResolver("org/apache");
		resolver.afterPropertiesSet();
		final Collection<ResourceDirectory> dirs = resolver
				.getResourceDirectories(true, "commons");
		boolean foundTestFile = false;
		final StringBuilder message = new StringBuilder();
		for (final ResourceDirectory _dir : dirs) {
			final ClassPathResourceResolver dir = (ClassPathResourceResolver) _dir;
			if (message.length() > 0) {
				message.append(", ");
			}
			message.append(dir.getRootPath());
			// this test requires the commons logging jar to be in the classpath
			// if (resource.getDescription().contains("LogFactory")) {
			// System.err.println(resource.getDescription());
			// }
			if (dir.getRootPath().equals("org/apache/commons/logging")) {
				foundTestFile = true;
			}
		}
		Assert.assertTrue("org/apache/commons/logging package not found: dirs="
				+ message, foundTestFile);
	}

	@Test
	public void testGetResourcesFromJarFile() throws Exception {
		resolver = new ClassPathResourceResolver();
		resolver.afterPropertiesSet();
		final Collection<Resource> resources = resolver.getResources(true, "/");
		boolean foundTestFile = false;
		final StringBuilder message = new StringBuilder();
		for (final Resource resource : resources) {
			if (message.length() > 0) {
				message.append(", ");
			}
			message.append(message);
			// this test requires the commons logging jar to be in the classpath
			// if (resource.getDescription().contains("LogFactory")) {
			// System.err.println(resource.getDescription());
			// }
			if (resource
					.getDescription()
					.equals("class path resource [org/apache/commons/logging/LogFactory.class]")) {
				Assert.assertNotNull(resource);
				foundTestFile = true;
			}
		}
		Assert.assertTrue("LogFactory class not found: resources=" + message,
				foundTestFile);
	}

	protected void checkDirsForIoPackage(
			final Collection<ResourceDirectory> dirs) {
		boolean foundTestFile = false;
		final StringBuilder message = new StringBuilder();
		for (final ResourceDirectory _dir : dirs) {
			final ClassPathResourceResolver dir = (ClassPathResourceResolver) _dir;
			if (message.length() > 0) {
				message.append(", ");
			}
			logger.debug("resolver " + dir + " is for package "
					+ dir.getRootPath());
			message.append(dir.getRootPath());
			if (dir.getName().equals("io")) {
				foundTestFile = true;
			}
		}
		Assert.assertTrue("io package not found: dirs=" + message,
				foundTestFile);
	}

	protected void checkResourcesForThisClass(
			final Collection<Resource> resources) {
		boolean foundTestFile = false;
		final StringBuilder message = new StringBuilder();
		for (final Resource resource : resources) {
			if (message.length() > 0) {
				message.append(", ");
			}
			message.append(resource.getFilename());
			if (resource.getFilename()
					.equals("ClassPathResourceResolver.class")) {
				Assert.assertNotNull(resource);
				foundTestFile = true;
			}
		}
		Assert.assertTrue(
				"ClassPathResourceResolver class not found: resources="
						+ message, foundTestFile);
	}

}
