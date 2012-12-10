/*
 * AccidentInterface.java
 *
 * Created on 21/Nov/2011, 9:11:56
 */
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;

import javax.swing.table.AbstractTableModel;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Font;


public class LaunchConfig extends javax.swing.JFrame {

    private Space2D space;
    private JLabel errorlbl;
    JComboBox comboBoxGas = new JComboBox();
    JComboBox comboBoxTime = new JComboBox();
    JComboBox comboBoxReserva = new JComboBox();
   
    /** Creates new form AccidentInterface */
    public LaunchConfig(IEnvironmentSpace space) {
        this.space = (Space2D) space;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Configura\u00E7\u00E3o do Ambiente");
        setResizable(true);
        
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        
        lblEstadoDoTempo = new JLabel("Estado do tempo:");
        lblEstadoDoTempo.setFont(new Font("Tahoma", Font.BOLD, 13));
        
        comboBoxTempo = new JComboBox();
        comboBoxTempo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        comboBoxTempo.addItem("Sol");
		comboBoxTempo.addItem("Chuva");
        
        JLabel lblGasolina = new JLabel("Gasolina:");
        lblGasolina.setFont(new Font("Tahoma", Font.BOLD, 13));
        //JComboBox comboBoxGas = new JComboBox();
        comboBoxGas.setFont(new Font("Tahoma", Font.PLAIN, 13));
        comboBoxGas.addItem("10");
        comboBoxGas.addItem("20");
        comboBoxGas.addItem("30");
        comboBoxGas.addItem("40");
        comboBoxGas.addItem("50");
        comboBoxGas.addItem("60");
        comboBoxGas.addItem("70");
        comboBoxGas.addItem("80");
        comboBoxGas.addItem("90");
        comboBoxGas.addItem("100");
        comboBoxGas.addItem("150");
        comboBoxGas.addItem("200");
        comboBoxGas.addItem("300");
        comboBoxGas.addItem("400");
        comboBoxGas.addItem("500");
        comboBoxGas.addItem("600");
        comboBoxGas.addItem("700");
        comboBoxGas.addItem("800");
        comboBoxGas.addItem("900");
        comboBoxGas.addItem("1000");
     
        
        JLabel lblReserva = new JLabel("Reserva:");
        lblReserva.setFont(new Font("Tahoma", Font.BOLD, 13));
        comboBoxReserva.setFont(new Font("Tahoma", Font.PLAIN, 13));
        //JComboBox comboBoxReserva = new JComboBox();
        comboBoxReserva.addItem("10");
        comboBoxReserva.addItem("20");
        comboBoxReserva.addItem("30");
        comboBoxReserva.addItem("40");
        comboBoxReserva.addItem("50");
        comboBoxReserva.addItem("60");
        comboBoxReserva.addItem("70");
        comboBoxReserva.addItem("80");
        comboBoxReserva.addItem("90");
        
        JLabel lblTempo = new JLabel("Tempo:");
        lblTempo.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTempo.setHorizontalAlignment(SwingConstants.TRAILING);
        comboBoxTime.setFont(new Font("Tahoma", Font.PLAIN, 13));
        //JComboBox comboBoxTime = new JComboBox();
        comboBoxTime.addItem("100");
        comboBoxTime.addItem("200");
        comboBoxTime.addItem("300");
        comboBoxTime.addItem("400");
        comboBoxTime.addItem("500");
        comboBoxTime.addItem("600");
        comboBoxTime.addItem("700");
        comboBoxTime.addItem("800");
        comboBoxTime.addItem("900");
        comboBoxTime.addItem("1000");
        
        errorlbl = new JLabel("");
        errorlbl.setForeground(Color.RED);
        errorlbl.setVisible(false);
        errorlbl.setText("A reserva tem que ser inferior ao valor da gasolina");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap(418, Short.MAX_VALUE)
        			.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
        			.addGap(71))
        		.addGroup(layout.createSequentialGroup()
        			.addGap(68)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblGasolina)
        				.addComponent(lblEstadoDoTempo)
        				.addComponent(lblReserva)
        				.addComponent(lblTempo))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(comboBoxTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(comboBoxReserva, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(errorlbl))
        				.addComponent(comboBoxGas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(comboBoxTempo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(287, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(32)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(comboBoxTempo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblEstadoDoTempo))
        			.addGap(33)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(comboBoxGas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblGasolina))
        			.addGap(34)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblReserva)
        				.addComponent(comboBoxReserva, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(errorlbl))
        			.addGap(41)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblTempo)
        				.addComponent(comboBoxTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(20)
        			.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(290, Short.MAX_VALUE))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    	
    	if(Integer.parseInt(comboBoxGas.getSelectedItem().toString()) <= Integer.parseInt(comboBoxReserva.getSelectedItem().toString()))
    		errorlbl.setVisible(true);
    	else{
    		errorlbl.setVisible(false);
    		
    		//space.getSpaceObjectsByType("driver")
    		ISpaceObject[] driver = space.getSpaceObjectsByType("driver");
    		//for (ISpaceObject d : driver){
            	driver[0].setProperty("time", Integer.parseInt(comboBoxTime.getSelectedItem().toString()));
            	driver[0].setProperty("gas", Integer.parseInt(comboBoxGas.getSelectedItem().toString()));
            	driver[0].setProperty("reserva", Integer.parseInt(comboBoxReserva.getSelectedItem().toString()));
    		//}
    		
    		space.setProperty("weather", comboBoxTempo.getSelectedItem().toString());
    		BDIMap.start = true;
    		System.out.println("weather: "+comboBoxTempo.getSelectedItem().toString());
    		System.out.println("time: "+ comboBoxTime.getSelectedItem().toString());
    		System.out.println("gas: "+ comboBoxGas.getSelectedItem().toString());
    		System.out.println("reserva: "+ comboBoxReserva.getSelectedItem().toString());
    		this.setVisible(false);
    		
    	}
		
		
		
    }//GEN-LAST:event_jButton1ActionPerformed

    class MyTableModel extends AbstractTableModel {

        private String[] columnNames = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", 
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
        private Object[][] data = new Object[30][30];
        
        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.

            return true;

        }

        public void setValueAt(Object value, int row, int col) {

            data[row][col] = value;
            fireTableCellUpdated(row, col);

        }
        
        
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    private JLabel lblEstadoDoTempo;
    private JComboBox comboBoxTempo;
    
    //private JComboBox comboBox
}