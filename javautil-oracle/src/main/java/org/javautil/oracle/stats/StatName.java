package org.javautil.oracle.stats;

public class StatName {
	/**
	 * Holds statistic_nbr.
	 */
	private Integer statisticNbr;
	/**
	 * Holds name.
	 */
	private String name;
	/**
	 * Holds class.
	 */
	private Integer statisticClass;
	/**
	 * Holds stat_id. todo do i really need this?
	 */
	private Long statId;

	public StatName() {

	}

	public StatName(final Integer statisticNbr, final String name,
			final Integer statisticClass, final Long statId) {
		super();
		this.statisticNbr = statisticNbr;
		this.name = name;
		this.statisticClass = statisticClass;
		this.statId = statId;
	}

	/**
	 * @return the statisticNbr
	 */
	public Integer getStatisticNbr() {
		return statisticNbr;
	}

	/**
	 * @param statisticNbr
	 *            the statisticNbr to set
	 */
	public void setStatisticNbr(final Integer statisticNbr) {
		this.statisticNbr = statisticNbr;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the statisticClass
	 */
	public Integer getStatisticClass() {
		return statisticClass;
	}

	/**
	 * @param statisticClass
	 *            the statisticClass to set
	 */
	public void setStatisticClass(final Integer statisticClass) {
		this.statisticClass = statisticClass;
	}

	/**
	 * @return the statId
	 */
	public Long getStatId() {
		return statId;
	}

	/**
	 * @param statId
	 *            the statId to set
	 */
	public void setStatId(final Long statId) {
		this.statId = statId;
	}

}
