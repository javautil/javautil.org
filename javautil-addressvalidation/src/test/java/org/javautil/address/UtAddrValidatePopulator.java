/**
 * 
 */
package org.javautil.address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>UtAddrValidatePopulator class.</p>
 *
 * @author siyer
 * @version $Id: UtAddrValidatePopulator.java,v 1.2 2012/03/04 12:31:11 jjs Exp $
 * @since 0.11.0
 */
public class UtAddrValidatePopulator {

	Connection conn;
	String insertStmt = "insert into ut_addr_validate ( "
			+ "ut_addr_validate_nbr, run_nbr, data_src_nbr, data_src_pk,"
			+ "name, raw_addr_1, raw_addr_2, raw_city, raw_state_cd,"
			+ "raw_postal_cd )" + " values (?,?,?,?,?,?,?,?,?,?)";

	private final String addr1_1 = "Ste 110";
	private final String addr1_2 = "Apt # 2065";
	private final String addr1_3 = "Ste # 600";
	private final String addr2_1 = "410 S Main";
	private final String addr2_2 = "1216 Hidden Ridge Rd";
	private final String addr2_3 = "11260 Chester Road";
	private final String city1 = "Romeo";
	private final String city2 = "Irving";
	private final String city3 = "Cincinnati";
	private final String state1 = "MI";
	private final String state2 = "TX";
	private final String state3 = "OH";
	private final String zip1 = "48065";
	private final String zip2 = "75038";
	private final String zip3 = "45246";

	/**
	 * <p>Constructor for UtAddrValidatePopulator.</p>
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 */
	public UtAddrValidatePopulator(final Connection conn) {
		this.conn = conn;
	}

	/**
	 * <p>populateAddresses.</p>
	 *
	 * @throws java.sql.SQLException if any.
	 */
	public void populateAddresses() throws SQLException {
		PreparedStatement ps = null;
		String addr1 = null;
		String addr2 = null;
		String city = null;
		String state = null;
		String zip = null;
		if (ps == null) {
			ps = conn.prepareStatement(insertStmt);
		}
		for (int i = 1; i <= 3; i++) {
			if (i == 1) {
				addr1 = addr1_1;
				addr2 = addr2_1;
				city = city1;
				state = state1;
				zip = zip1;
			}
			if (i == 2) {
				addr1 = addr1_2;
				addr2 = addr2_2;
				city = city2;
				state = state2;
				zip = zip2;
			}
			if (i == 3) {
				addr1 = addr1_3;
				addr2 = addr2_3;
				city = city3;
				state = state3;
				zip = zip3;
			}
			ps.setInt(1, i);
			ps.setInt(2, 1);
			ps.setInt(3, 1);
			ps.setInt(4, i);
			ps.setString(5, "Name " + i);
			ps.setString(6, addr1);
			ps.setString(7, addr2);
			ps.setString(8, city);
			ps.setString(9, state);
			ps.setString(10, zip);

			ps.executeUpdate();
		}
	}
}
