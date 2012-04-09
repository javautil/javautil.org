package org.javautil.dataset.render;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MootoolsTableRendererTest extends AbstractTableRendererTest {
	/*
	 * @BeforeClass public static void beforeClass() { final WriterAppender
	 * appender = new WriterAppender(); appender.setWriter(new
	 * PrintWriter(System.out)); appender.setLayout(new SimpleLayout());
	 * appender.setName(HtmlTableDatasetRenderer.class.getName());
	 * BasicConfigurator.configure(appender); }
	 */

	@SuppressWarnings("unchecked")
	static List<MootoolsTableRenderer> getRenderers() {
		final ArrayList<MootoolsTableRenderer> renderers = new ArrayList<MootoolsTableRenderer>();
		MootoolsTableRenderer r = new MootoolsTableRenderer();
		r.setEmbedCssStyles(true);
		r.setToggleColumns(true);
		r.setScrollable(true);
		r.setIncludeTableUtilsScript(true);
		r.setIncludeMootoolsScript(true);
		r.setIncludeSortingTableScript(true);
		r.setIncludePaginatingTableScript(true);
		renderers.add(r);
		r = new MootoolsTableRenderer();
		r.setToggleColumns(true);
		r.setScrollable(false);
		renderers.add(r);
		return renderers;
	}

	static HtmlTableRendererRequest getRequest() {
		final HtmlTableRendererRequest rendererRequest = new HtmlTableRendererRequest(
				getSampleDataset());
		rendererRequest.setStreamResult(getStreamResult());
		return rendererRequest;
	}

	// TODO this doesn't test anything need to generate output and compare
	@Test
	public void testSimple() throws Exception {
		useBufferStreamResult();
		for (final DatasetRenderer renderer : getRenderers()) {
			renderer.render(getRequest());
		}
		// System.out.println(getBuffer().toString());
	}

	/*
	 * public static void main(final String[] args) throws Exception {
	 * useTempFileStreamResult(); for (final DatasetRenderer renderer :
	 * getRenderers()) { renderer.render(getRequest()); } final String browser =
	 * "/usr/bin/firefox"; final String filePath = "file://" +
	 * getTempFile().getAbsolutePath(); final String command = browser + " " +
	 * filePath; Runtime.getRuntime().exec(command); Thread.sleep(5000); }
	 */

}
