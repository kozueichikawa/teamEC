package com.internousdev.casablanca.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.casablanca.dao.CartInfoDAO;
import com.internousdev.casablanca.dao.MCategoryDAO;
import com.internousdev.casablanca.dao.PurchaseHistoryInfoDAO;
import com.internousdev.casablanca.dto.MCategoryDTO;
import com.internousdev.casablanca.dto.PurchaseHistoryInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class SettlementCompleteAction extends ActionSupport implements SessionAware{
	private int id;
	private Map<String, Object> session;

	public String execute() {
		String result = ERROR;

		/* ログイン済みか否かをチェック */
		if(Objects.equals(session.get("logined"), "1")){

			/* SettlementConfirmActionで格納した決済品物リストをセッションから取得 */
			@SuppressWarnings("unchecked")
			ArrayList<PurchaseHistoryInfoDTO> purchaseHistoryInfoDtoList =(ArrayList<PurchaseHistoryInfoDTO>)session.get("purchaseHistoryInfoDtoList");
			PurchaseHistoryInfoDAO purchaseHistoryInfoDAO = new PurchaseHistoryInfoDAO();
			int count = 0;

			/* settlementConfirm.jspで選択した商品を一つひとつDBへINSERTしていく */
			for(int i=0;i<purchaseHistoryInfoDtoList.size();i++){
				purchaseHistoryInfoDtoList.get(i).setDestinationId(id);
				count += purchaseHistoryInfoDAO.regist(
					 String.valueOf(session.get("loginId")),
					 purchaseHistoryInfoDtoList.get(i).getProductId(),
					 purchaseHistoryInfoDtoList.get(i).getProductCount(),
					 purchaseHistoryInfoDtoList.get(i).getDestinationId(),
					 purchaseHistoryInfoDtoList.get(i).getSubtotal());
			}
			/* INSERTが成功後、カート情報を削除 */
			if(count > 0){
				CartInfoDAO cartInfoDAO = new CartInfoDAO();
				count = cartInfoDAO.deleteAll(String.valueOf(session.get("loginId")));
				if(count > 0){
					result = SUCCESS;
				}
			}
		} else {
			result = "timeout";
		}
		if(!session.containsKey("mCategoryDtoList")) {
			MCategoryDAO mCategoryDAO=new MCategoryDAO();
			List<MCategoryDTO> mCategoryDtoList= mCategoryDAO.getMCategoryList();
			session.put("mCategoryDtoList", mCategoryDtoList);
		}
		return result;
	}

	public void setId(int id){
	   this.id = id;
	}
	public void setSession(Map<String,Object> session){
	   this.session = session;
	}
}