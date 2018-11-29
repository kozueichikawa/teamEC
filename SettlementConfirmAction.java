package com.internousdev.casablanca.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.casablanca.dao.DestinationInfoDAO;
import com.internousdev.casablanca.dao.MCategoryDAO;
import com.internousdev.casablanca.dto.DestinationInfoDTO;
import com.internousdev.casablanca.dto.MCategoryDTO;
import com.internousdev.casablanca.dto.PurchaseHistoryInfoDTO;
import com.internousdev.casablanca.util.CommonUtility;
import com.opensymphony.xwork2.ActionSupport;

public class SettlementConfirmAction extends ActionSupport implements SessionAware{
	private List<DestinationInfoDTO> destinationInfoDtoList;
	private String productId;
	private String productCount;
	private String subtotal;
	private Map<String, Object> session;
	public String execute() {
		String result = ERROR;

		/* ログイン状態か否かを判定 */
		if(Objects.equals(session.get("logined"), "1")) {
			/* ログイン状態であれば、まず宛先情報をListにしてValueStackへ積む */
			DestinationInfoDAO destinationInfoDAO = new DestinationInfoDAO();
			destinationInfoDtoList = destinationInfoDAO.getDestinationInfo((session.get("loginId").toString()));
			if (destinationInfoDtoList.size() == 0) {
				destinationInfoDtoList = null;
			}
			result = SUCCESS;
		} else {
			/* ログイン状態でなければ、loginAction分岐用にカートフラグを立てる */
			session.put("fromCart", true);
		}
		if (productId == null) {
		} else {
			/* cart.jspに入っていた注文リストをSettlementCompleteActionへ送るためのListを生成する */
			List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDtoList = new ArrayList<PurchaseHistoryInfoDTO>();

			/* cart.jspから[xx, xx, xx]というStringで渡ってくる各値をCommonUtilityで,(カンマ)区切りで配列に変換する */
			CommonUtility commonUtility = new CommonUtility();
			String[] productIdList = commonUtility.parseArrayList(productId);
			String[] productCountList = commonUtility.parseArrayList(productCount);
			String[] subtotalList = commonUtility.parseArrayList(subtotal);

			/* 購入する品物数分のListに生成する */
			for(int i=0;i<productIdList.length;i++) {
				PurchaseHistoryInfoDTO purchaseHistoryInfoDTO = new PurchaseHistoryInfoDTO();
				purchaseHistoryInfoDTO.setProductId(Integer.parseInt(String.valueOf(productIdList[i])));
				purchaseHistoryInfoDTO.setProductCount(Integer.parseInt(String.valueOf(productCountList[i])));
				purchaseHistoryInfoDTO.setSubtotal(Integer.parseInt(String.valueOf(subtotalList[i])));
				purchaseHistoryInfoDtoList.add(purchaseHistoryInfoDTO);
			}

			/* 出来上がったListをセッションへ格納し、SettlementCompleteActionでgetできるようにする */
			session.put("purchaseHistoryInfoDtoList", purchaseHistoryInfoDtoList);

		}
		/* カテゴリリストのセッションが切れてしまい次ページでカテゴリが表示されない事象を防ぐため */
		if(!session.containsKey("mCategoryDtoList")) {
			MCategoryDAO mCategoryDAO=new MCategoryDAO();
			List<MCategoryDTO> mCategoryDtoList= mCategoryDAO.getMCategoryList();
			session.put("mCategoryDtoList", mCategoryDtoList);
		}
		/* ログイン状態で本Actionが実行されればSUCCESS(settlementConfirm.jsp)、未ログイン状態ならERROR(login.jsp) */
		return result;
	}

	public List<DestinationInfoDTO> getDestinationInfoDtoList() {
		return destinationInfoDtoList;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
}
