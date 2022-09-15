/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class GameDrawResultsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "GAME_ID")
    private int gameId;
    @Basic(optional = false)
    @Column(name = "DRAW_ID")
    private int drawId;
    @Basic(optional = false)
    @Column(name = "WINNING_CATEGORY_DESCR")
    private String winningCategoryDescr;

    public GameDrawResultsPK() {
    }

    public GameDrawResultsPK(int gameId, int drawId, String winningCategoryDescr) {
        this.gameId = gameId;
        this.drawId = drawId;
        this.winningCategoryDescr = winningCategoryDescr;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getDrawId() {
        return drawId;
    }

    public void setDrawId(int drawId) {
        this.drawId = drawId;
    }

    public String getWinningCategoryDescr() {
        return winningCategoryDescr;
    }

    public void setWinningCategoryDescr(String winningCategoryDescr) {
        this.winningCategoryDescr = winningCategoryDescr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) gameId;
        hash += (int) drawId;
        hash += (winningCategoryDescr != null ? winningCategoryDescr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GameDrawResultsPK)) {
            return false;
        }
        GameDrawResultsPK other = (GameDrawResultsPK) object;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.drawId != other.drawId) {
            return false;
        }
        if ((this.winningCategoryDescr == null && other.winningCategoryDescr != null) || (this.winningCategoryDescr != null && !this.winningCategoryDescr.equals(other.winningCategoryDescr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.GameDrawResultsPK[ gameId=" + gameId + ", drawId=" + drawId + ", winningCategoryDescr=" + winningCategoryDescr + " ]";
    }
    
}
