/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "WINNING_COLUMN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WinningColumn.findAll", query = "SELECT w FROM WinningColumn w")
    , @NamedQuery(name = "WinningColumn.findByGameId", query = "SELECT w FROM WinningColumn w WHERE w.winningColumnPK.gameId = :gameId")
      //Custom Query to fetch Winning Column by Draw Id AND Game ID
    , @NamedQuery(name = "WinningColumn.findByDrawId", query = "SELECT w FROM WinningColumn w WHERE w.winningColumnPK.drawId = :drawId and w.winningColumnPK.gameId = :gameId")
    , @NamedQuery(name = "WinningColumn.findByDrawTime", query = "SELECT w FROM WinningColumn w WHERE w.drawTime = :drawTime")
    , @NamedQuery(name = "WinningColumn.findByN1", query = "SELECT w FROM WinningColumn w WHERE w.n1 = :n1")
    , @NamedQuery(name = "WinningColumn.findByN2", query = "SELECT w FROM WinningColumn w WHERE w.n2 = :n2")
    , @NamedQuery(name = "WinningColumn.findByN3", query = "SELECT w FROM WinningColumn w WHERE w.n3 = :n3")
    , @NamedQuery(name = "WinningColumn.findByN4", query = "SELECT w FROM WinningColumn w WHERE w.n4 = :n4")
    , @NamedQuery(name = "WinningColumn.findByN5", query = "SELECT w FROM WinningColumn w WHERE w.n5 = :n5")
    , @NamedQuery(name = "WinningColumn.findByJ1", query = "SELECT w FROM WinningColumn w WHERE w.j1 = :j1")
    , @NamedQuery(name = "WinningColumn.findByWinningColumnDescr", query = "SELECT w FROM WinningColumn w WHERE w.winningColumnDescr = :winningColumnDescr")
      //Custom Query to retrieve all Winning Column info in given date range
    , @NamedQuery(name = "WinningColumn.findByDrawTimeRange", query = "SELECT w FROM WinningColumn w WHERE w.drawTime BETWEEN :drawTimeFrom AND :drawTimeTo")})
public class WinningColumn implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WinningColumnPK winningColumnPK;
    @Basic(optional = false)
    @Column(name = "DRAW_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date drawTime;
    @Basic(optional = false)
    @Column(name = "N_1")
    private int n1;
    @Basic(optional = false)
    @Column(name = "N_2")
    private int n2;
    @Basic(optional = false)
    @Column(name = "N_3")
    private int n3;
    @Basic(optional = false)
    @Column(name = "N_4")
    private int n4;
    @Basic(optional = false)
    @Column(name = "N_5")
    private int n5;
    @Basic(optional = false)
    @Column(name = "J_1")
    private int j1;
    @Basic(optional = false)
    @Column(name = "WINNING_COLUMN_DESCR")
    private String winningColumnDescr;

    public WinningColumn() {
    }

    public WinningColumn(WinningColumnPK winningColumnPK) {
        this.winningColumnPK = winningColumnPK;
    }

    public WinningColumn(WinningColumnPK winningColumnPK, Date drawTime, int n1, int n2, int n3, int n4, int n5, int j1, String winningColumnDescr) {
        this.winningColumnPK = winningColumnPK;
        this.drawTime = drawTime;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.n4 = n4;
        this.n5 = n5;
        this.j1 = j1;
        this.winningColumnDescr = winningColumnDescr;
    }

    public WinningColumn(int gameId, int drawId) {
        this.winningColumnPK = new WinningColumnPK(gameId, drawId);
    }

    public WinningColumnPK getWinningColumnPK() {
        return winningColumnPK;
    }

    public void setWinningColumnPK(WinningColumnPK winningColumnPK) {
        this.winningColumnPK = winningColumnPK;
    }

    public Date getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(Date drawTime) {
        this.drawTime = drawTime;
    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int getN3() {
        return n3;
    }

    public void setN3(int n3) {
        this.n3 = n3;
    }

    public int getN4() {
        return n4;
    }

    public void setN4(int n4) {
        this.n4 = n4;
    }

    public int getN5() {
        return n5;
    }

    public void setN5(int n5) {
        this.n5 = n5;
    }

    public int getJ1() {
        return j1;
    }

    public void setJ1(int j1) {
        this.j1 = j1;
    }

    public String getWinningColumnDescr() {
        return winningColumnDescr;
    }

    public void setWinningColumnDescr(String winningColumnDescr) {
        this.winningColumnDescr = winningColumnDescr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (winningColumnPK != null ? winningColumnPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WinningColumn)) {
            return false;
        }
        WinningColumn other = (WinningColumn) object;
        if ((this.winningColumnPK == null && other.winningColumnPK != null) || (this.winningColumnPK != null && !this.winningColumnPK.equals(other.winningColumnPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.WinningColumn[ winningColumnPK=" + winningColumnPK + " ]";
    }
    
}
