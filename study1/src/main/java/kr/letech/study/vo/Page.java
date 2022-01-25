package kr.letech.study.vo;

import lombok.Data;

@Data
public class Page {
	private int totalCnt; // 전체 튜플 수
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private Criteria cri;
	private int displayPageNum = 10; // 한 게시판에서 보여질 페이지수.
									 // 게시글수가 아닌 페이지수이다.
	
	
	private void calcData() {
		endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1;
		int tempEndPage = (int) (Math.ceil(totalCnt / (double) cri.getPerPageNum()));

		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		prev = (startPage == 1 ? false : true);
		next = (endPage * cri.getPerPageNum() >= totalCnt ? false : true);
	}

	// 변경되는 값인 totalCnt가 지정된 후 calcData를 호출하여 값을 도출한다.
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
		calcData();
	}
}