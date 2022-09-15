/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "GAME_DRAW_RESULTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GameDrawResults.findAll", query = "SELECT g FROM GameDrawResults g")
    , @NamedQuery(name = "GameDrawResults.findByGameId", query = "SELECT g FROM GameDrawResults g WHERE g.gameDrawResultsPK.gameId = :gameId")
        //Custom Query to introduce game ID condition in search
    , @NamedQuery(name = "GameDrawResults.findByDrawId", query = "SELECT g FROM GameDrawResults g WHERE g.gameDrawResultsPK.drawId = :drawId and g.gameDrawResultsPK.gameId = :gameId")
        //Custom Query to retreive Draw Results info based on Winning category
    , @NamedQuery(name = "GameDrawResults.findByWinningCategoryDescr", query = "SELECT g FROM GameDrawResults g WHERE g.gameDrawResultsPK.winningCategoryDescr = :winningCategoryDescr")
    , @NamedQuery(name = "GameDrawResults.findBySuccessesCnt", query = "SELECT g FROM GameDrawResults g WHERE g.successesCnt = :successesCnt")
    , @NamedQuery(name = "GameDrawResults.findByDividentAmn", query = "SELECT g FROM GameDrawResults g WHERE g.dividentAmn = :dividentAmn")
    , @NamedQuery(name = "GameDrawResults.findByTotalPayout", query = "SELECT g FROM GameDrawResults g WHERE g.totalPayout = :totalPayout")})
public class GameDrawResults implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GameDrawResultsPK gameDrawResultsPK;
    @Column(name = "SUCCESSES_CNT")
    private Integer successesCnt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DIVIDENT_AMN")
    private Double dividentAmn;
    @Column(name = "TOTAL_PAYOUT")
    private Double totalPayout;

    public GameDrawResults() {
    }

    public GameDrawResults(GameDrawResultsPK gameDrawResultsPK) {
        this.gameDrawResultsPK = gameDrawResultsPK;
    }

    public GameDrawResults(int gameId, int drawId, String winningCategoryDescr) {
        this.gameDrawResultsPK = new GameDrawResultsPK(gameId, drawId, winningCategoryDescr);
    }

    public GameDrawResultsPK getGameDrawResultsPK() {
        return gameDrawResultsPK;
    }

    public void setGameDrawResultsPK(GameDrawResultsPK gameDrawResultsPK) {
        this.gameDrawResultsPK = gameDrawResultsPK;
    }

    public Integer getSuccessesCnt() {
        return successesCnt;
    }

    public void setSuccessesCnt(Integer successesCnt) {
        this.successesCnt = successesCnt;
    }

    public Double getDividentAmn() {
        return dividentAmn;
    }

    public void setDividentAmn(Double dividentAmn) {
        this.dividentAmn = dividentAmn;
    }

    public Double getTotalPayout() {
        return totalPayout;
    }

    public void setTotalPayout(Double totalPayout) {
        this.totalPayout = totalPayout;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameDrawResultsPK != null ? gameDrawResultsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GameDrawResults)) {
            return false;
        }
        GameDrawResults other = (GameDrawResults) object;
        if ((this.gameDrawResultsPK == null && other.gameDrawResultsPK != null) || (this.gameDrawResultsPK != null && !this.gameDrawResultsPK.equals(other.gameDrawResultsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.GameDrawResults[ gameDrawResultsPK=" + gameDrawResultsPK + " ]";
    }
    
}
