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
public class WinningColumnPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "GAME_ID")
    private int gameId;
    @Basic(optional = false)
    @Column(name = "DRAW_ID")
    private int drawId;

    public WinningColumnPK() {
    }

    public WinningColumnPK(int gameId, int drawId) {
        this.gameId = gameId;
        this.drawId = drawId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) gameId;
        hash += (int) drawId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WinningColumnPK)) {
            return false;
        }
        WinningColumnPK other = (WinningColumnPK) object;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.drawId != other.drawId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.WinningColumnPK[ gameId=" + gameId + ", drawId=" + drawId + " ]";
    }
    
}
