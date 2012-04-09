package org.javautil.dataset.render.typewriter;

public class TypewriterRendererFactoryImpl<R, C> implements
		TypewriterRendererFactory {

	private final TypewriterContent<R, C> content;

	private boolean createMissingRows = false;

	private boolean createMissingCells = false;

	public TypewriterRendererFactoryImpl(final TypewriterContent<R, C> content) {
		if (content == null) {
			throw new IllegalStateException("content is null");
		}
		this.content = content;
	}

	@Override
	public TypewriterRenderer getTypewriterRenderer(
			final TypewriterBehavior behavior) {
		if (behavior == null) {
			throw new IllegalStateException("behavior is null");
		}
		final TypewriterRendererImpl<R, C> renderer = new TypewriterRendererImpl<R, C>(
				content, behavior);
		renderer.setCreateMissingCells(isCreateMissingCells());
		renderer.setCreateMissingRows(isCreateMissingRows());
		return renderer;
	}

	public boolean isCreateMissingRows() {
		return createMissingRows;
	}

	public void setCreateMissingRows(final boolean createMissingRows) {
		this.createMissingRows = createMissingRows;
	}

	public boolean isCreateMissingCells() {
		return createMissingCells;
	}

	public void setCreateMissingCells(final boolean createMissingCells) {
		this.createMissingCells = createMissingCells;
	}
}
