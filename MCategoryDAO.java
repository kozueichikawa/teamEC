package com.internousdev.casablanca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.casablanca.dto.MCategoryDTO;
import com.internousdev.casablanca.util.DBConnector;

public class MCategoryDAO {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public List<MCategoryDTO> getMCategoryList() {
		List<MCategoryDTO> mCategoryDTOList = new ArrayList<MCategoryDTO>();
		DBConnector db = new DBConnector();
		con = db.getConnection();
		String sql = "select * from m_category";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MCategoryDTO mCategoryDTO = new MCategoryDTO();
				mCategoryDTO.setId(rs.getInt("id"));
				mCategoryDTO.setCategoryId(rs.getInt("category_id"));
				mCategoryDTO.setCategoryName(rs.getString("category_name"));
				mCategoryDTOList.add(mCategoryDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mCategoryDTOList;
	}
}
