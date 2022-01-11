package com.naicson.yugioh.dto.home;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "utilitarios")
public class HomeDTO {
	
	@Id
	private Long id;
	private long qtdDeck;
	private long qtdBoxes;
	private long qtdTins;
	private long qtdCards;
	
	@Transient
	private List<LastAddedDTO> hotNews;
	@Transient
	private List<LastAddedDTO> lastSets;
	@Transient
	private List<LastAddedDTO> lastCards;
	@Transient
	private List<RankingForHomeDTO> highCards;
	@Transient
	private List<RankingForHomeDTO> lowCards;
	@Transient
	private List<RankingForHomeDTO> weeklyMostView;
	
	public HomeDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getQtdDeck() {
		return qtdDeck;
	}

	public void setQtdDeck(long qtdDeck) {
		this.qtdDeck = qtdDeck;
	}

	public long getQtdBoxes() {
		return qtdBoxes;
	}

	public void setQtdBoxes(long qtdBoxes) {
		this.qtdBoxes = qtdBoxes;
	}

	public long getQtdTins() {
		return qtdTins;
	}

	public void setQtdTins(long qtdTins) {
		this.qtdTins = qtdTins;
	}

	public long getQtdCards() {
		return qtdCards;
	}

	public void setQtdCards(long qtdCards) {
		this.qtdCards = qtdCards;
	}

	public List<LastAddedDTO> getHotNews() {
		return hotNews;
	}

	public void setHotNews(List<LastAddedDTO> hotNews) {
		this.hotNews = hotNews;
	}

	public List<LastAddedDTO> getLastSets() {
		return lastSets;
	}

	public void setLastSets(List<LastAddedDTO> lastSets) {
		this.lastSets = lastSets;
	}

	public List<LastAddedDTO> getLastCards() {
		return lastCards;
	}

	public void setLastCards(List<LastAddedDTO> lastCards) {
		this.lastCards = lastCards;
	}

	public List<RankingForHomeDTO> getHighCards() {
		return highCards;
	}

	public void setHighCards(List<RankingForHomeDTO> highCards) {
		this.highCards = highCards;
	}

	public List<RankingForHomeDTO> getLowCards() {
		return lowCards;
	}

	public void setLowCards(List<RankingForHomeDTO> lowCards) {
		this.lowCards = lowCards;
	}

	public List<RankingForHomeDTO> getWeeklyMostView() {
		return weeklyMostView;
	}

	public void setWeeklyMostView(List<RankingForHomeDTO> weeklyMostView) {
		this.weeklyMostView = weeklyMostView;
	}
	
	
	
}
