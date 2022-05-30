package gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MenuOverlay {
    
    private JPanel selectePanelLevel01;
    
    //Hilight on/off Main Menu upon mouseover
    public  void setHilighted(JPanel panel, boolean isHighlighted){        
        //Ignore highligt for selected panel
        if (!panel.equals(selectePanelLevel01) && panel.isEnabled()){
            if(isHighlighted){
                panel.setBackground(new Color(255,195,20));
                ((JLabel)panel.getComponent(0)).setForeground(new Color(51,51,51));
            }
            else {
                panel.setBackground(new Color(192,139,6));
                ((JLabel)panel.getComponent(0)).setForeground(new Color(255,255,255));                     
            }
        }
    }
    
    //set selected Menu item
    public void setSelected(JPanel panel){        
        selectePanelLevel01 = panel;
    }
     //get selected Menu item
    public JPanel getSelected(){        
        return selectePanelLevel01;
    }
    
     
    public boolean isEnabled(JPanel panel){        
        return panel.isEnabled();
    }
    
       
    public void setEnabled(JPanel panel, boolean isEnabled){     
        
        if (!isEnabled){
            panel.setBackground(new Color(192,139,6));
        }
                
        panel.setEnabled(isEnabled);
        Component[] components = panel.getComponents();
        for(Component comp : components){
            comp.setEnabled(isEnabled);
        }
        (panel.getComponent(0)).setForeground(new Color(255,255,255));   
    }    
    
    public void deselectAll(JPanel parentPanel){     
        
        Component[] components = parentPanel.getComponents();
        for(Component comp : components){
            if (comp instanceof JPanel ){
               setHilighted((JPanel)comp, false);            
            }
        }
    }        
    
    
    
}
