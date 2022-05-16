package app;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.freixas.jcalendar.JCalendarCombo;
import com.l2fprod.common.swing.JOutlookBar;
import com.l2fprod.common.swing.JTipOfTheDay;
import com.l2fprod.common.swing.tips.DefaultTip;
import com.l2fprod.common.swing.tips.DefaultTipModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


public class MainMyWindow extends JFrame {

    private JPanel cPane;

    private TitledBorder titledBorder;
    private JLabel jLabel,infoLabel,statusLabel;
    private JTextField jTextField,statusTextField;
    private JPanel northPanel, southPanel,northNorthPanel,createPanel,btnPanel,calPanel,infoPanel,centerPanel,outBarPanel;
    private JTextArea jTextArea, jTextArea1, jTextArea2, resultTextArea,calTextArea;
    private JSlider jSliderw, jSliderk;
    private JComboBox jComboBox;
    private static JTextField infoTextField;
    private JDialog jDialog;

    private JCalendarCombo calendar;

    private JOutlookBar jOutlookBar;

    AboutWindow aboutWindow =null;
    HelpWindow HelpWindow=null;

    JToolBar toolBar;
    JButton jtbLogin, jtbLogout, jtbExit, jtbHelp, jtbAbout,jtbMin,jtbMax,jtbSum,
            calBtn,btnDodaj,btnSave,btnReset,jtbAve,jtbWykres;

    JMenu fileMenu, editMenu, viewMenu, calMenu, helpMenu;
    JMenuItem loginMI, logoutMI, printMI,exitMI,helpMI,aboutMI,sumMI,minMI,maxMI,aveMi;

    Icon iconLogin, iconLogout, iconAbout, iconMin,iconMax,iconSum,iconExit, angry,iconAve,iconHelp, iconWykres;

    Font resultFont = new Font("", Font.BOLD, 15);

    JTable jTable;

    Object[] columns ={1,2,3,4,5};
    Object[][] data ={
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0}

    };

    String[] choseoperation = {"Suma","Średnia","Max","Min"}; ;

    public MainMyWindow() {

        this.setTitle("Moje Okno");
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //zamkniecie okna
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                String ObjBtn[]={"Tak","Nie"};
                int response = JOptionPane.showOptionDialog(null,"Czy jesteś tego pewien?","",
                        JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE, angry,ObjBtn,ObjBtn[1]);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        //rozmieszczenie okna na srodku ekranu oraz nadanie wielkosci okna
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//pobranie rozdzielczosci pulpitu
        setSize(1000,600);
        setLocation((screenSize.width-900)/2,(screenSize.height-600)/2);

        cPane = (JPanel) this.getContentPane();
        cPane.setLayout(new BorderLayout());
        showTip();
        createIcons();
        createMenu();
        createGUI();

    }

    public void createMenu() {

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        fileMenu = new JMenu("Plik");
        editMenu = new JMenu("Edycja");
        viewMenu = new JMenu("Widok");
        calMenu = new JMenu("Obliczenia");
        helpMenu = new JMenu("Pomoc");

        loginMI = new JMenuItem("Login");
        logoutMI = new JMenuItem("Logout");
        printMI = new JMenuItem("Print");
        exitMI = new JMenuItem("Wyjście");
        helpMI = new JMenuItem("Pomoc");
        aboutMI = new JMenuItem("Info");
        sumMI = new JMenuItem("Suma");
        minMI = new JMenuItem("Min");
        maxMI = new JMenuItem("Max");
        aveMi = new JMenuItem("Srednia");

        calendar = new JCalendarCombo();

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(calMenu);
        menuBar.add(helpMenu);
        menuBar.add(calendar);

        fileMenu.add(loginMI);
        fileMenu.add(logoutMI);
        fileMenu.addSeparator();
        fileMenu.add(exitMI).addActionListener(this::exitActionEvent);

        helpMenu.add(helpMI).addActionListener(this::helpActionEvent);
        helpMenu.add(aboutMI).addActionListener(this::aboutActionEvent);

        calMenu.add(sumMI).addActionListener(this::sumTableActionEvent);
        calMenu.add(aveMi).addActionListener(this::aveTableActionEvent);
        calMenu.add(minMI).addActionListener(this::minActionEvent);
        calMenu.add(maxMI).addActionListener(this::maxActionEvent);

        calendar.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        calendar.setBounds(525, 278, 122, 23);

        calendar.addActionListener(this::calendarActionEvent);
    }

    public void createToolBar (JToolBar cjtb) {

        cPane.add(cjtb);
        cjtb.setFloatable(false);

        jtbLogin = createJButton("", iconLogin,"Login");
        jtbLogout = createJButton("",iconLogout,"Logout");
        jtbAbout = createJButton("",iconAbout,"Informacja o Autorze");
        jtbMax = createJButton("", iconMax,"Max  wartość");
        jtbMin = createJButton("", iconMin,"Min wartość");
        jtbSum = createJButton("",iconSum,"Suma elementów");
        jtbExit = createJButton("", iconExit,"Wyjście");
        jtbAve = createJButton("", iconAve, "Srednia tablicy");
        jtbHelp = createJButton("", iconHelp, "Pomoc");
        jtbWykres = createJButton("",iconWykres,"Wykres");

        cjtb.add(jtbLogin);
        cjtb.add(jtbLogout);
        cjtb.addSeparator();
        cjtb.add(jtbExit);
        cjtb.add(jtbMax);
        cjtb.add(jtbMin);
        cjtb.add(jtbSum);
        cjtb.add(jtbAve);
        cjtb.addSeparator();
        cjtb.add(jtbWykres);
        cjtb.addSeparator();
        cjtb.addSeparator();
        cjtb.add(jtbHelp);
        cjtb.add(jtbAbout);

        jtbSum.addActionListener(this::sumTableActionEvent);
        jtbAve.addActionListener(this::aveTableActionEvent);
        jtbMin.addActionListener(this::minActionEvent);
        jtbMax.addActionListener(this::maxActionEvent);
        jtbAbout.addActionListener(this::aboutActionEvent);
        jtbExit.addActionListener(this::exitActionEvent);
        jtbHelp.addActionListener(this::helpActionEvent);
        jtbWykres.addActionListener(this::wykresActionEvent);


    }

    public JButton createJButton(String nazwa, Icon icon, String toolTip) {

        JButton jb = new JButton(nazwa, icon);
        jb.setToolTipText(toolTip);

        return jb;
    }

    public void createIcons() {

        try {
            iconLogin = createIcon("login.png");
            iconLogout = createIcon("logout.png");
            iconAbout = createIcon("info.png");
            iconMax = createIcon("max.jpg");
            iconMin = createIcon("min.jpg");
            iconSum = createIcon("sum.png");
            iconExit = createIcon("exit.png");
            angry = createIcon("angry.png");
            iconAve = createIcon("ave.png");
            iconHelp = createIcon("help.png");
            iconWykres = createIcon("wykres.png");

        } catch (Exception e) {

            Timer t = new Timer(2000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    infoTextField.setText("Błąd wczytywania ikon");
                }
            });
            t.setRepeats(true);
            t.start();
        }
    }

    public Icon createIcon(String file) {
        String name = "/Graphics/" + file;
        Icon icon = new ImageIcon(getClass().getResource(name));
        return icon;
    }

    private void createGUI() {

        toolBar = new JToolBar();
        centerPanel = createCenterPanel();
        outBarPanel = createOutLookBar();

        createToolBar(toolBar);

        cPane.add(outBarPanel, BorderLayout.WEST);
        cPane.add(toolBar, BorderLayout.NORTH);
        cPane.add(centerPanel, BorderLayout.CENTER);
    }

    public JPanel createOutLookBar() {
        JPanel jPanel = new JPanel();

        JOutlookBar outlookbar = new JOutlookBar();
        outlookbar.setPreferredSize(new Dimension(100,300));

        jPanel.add(outlookbar);

        JButton bt1 = new JButton("Zakoncz", null);
        bt1.setOpaque(false);
        bt1.setContentAreaFilled(false);
        bt1.setBorderPainted(false);

        JButton bt2 = new JButton(btnDodaj.getText(), btnDodaj.getIcon());
        bt2.setOpaque(false);
        bt2.setContentAreaFilled(false);
        bt2.setBorderPainted(false);

        JButton bt3 = new JButton(btnReset.getText(), btnDodaj.getIcon());
        bt3.setOpaque(false);
        bt3.setContentAreaFilled(false);
        bt3.setBorderPainted(false);

        JButton bt4 = new JButton(btnSave.getText(), btnSave.getIcon());
        bt4.setOpaque(false);
        bt4.setContentAreaFilled(false);
        bt4.setBorderPainted(false);

        JButton bt5 = new JButton("Suma");
        bt5.setOpaque(false);
        bt5.setContentAreaFilled(false);
        bt5.setBorderPainted(false);

        JButton bt6 = new JButton("Srednia");
        bt6.setOpaque(false);
        bt6.setContentAreaFilled(false);
        bt6.setBorderPainted(false);

        JButton bt8 = new JButton("Min");
        bt8.setOpaque(false);
        bt8.setContentAreaFilled(false);
        bt8.setBorderPainted(false);

        JButton bt9 = new JButton("Max");
        bt9.setOpaque(false);
        bt9.setContentAreaFilled(false);
        bt9.setBorderPainted(false);

        JButton bt10 = new JButton("Info");
        bt10.setOpaque(false);
        bt10.setContentAreaFilled(false);
        bt10.setBorderPainted(false);

        JButton bt11 = new JButton("Help");
        bt11.setOpaque(false);
        bt11.setContentAreaFilled(false);
        bt11.setBorderPainted(false);

        outlookbar.setBackground(this.getBackground());
        outlookbar.addTab("Zamknij", bt1);
        outlookbar.addTab("Dodaj", bt2);
        outlookbar.addTab("Wyzeruj", bt3);
        outlookbar.addTab("Zapisz", bt4);
        outlookbar.addTab("Suma", bt5);
        outlookbar.addTab("Srednia", bt6);
        outlookbar.addTab("Min", bt8);
        outlookbar.addTab("Max", bt9);
        outlookbar.addTab("Info", bt10);
        outlookbar.addTab("Help", bt11);

        bt1.addActionListener(this::exitActionEvent);
        bt2.addActionListener(this::addToTableActionEvent);
        bt3.addActionListener(this::resetTableActionEvent);
        bt4.addActionListener(this::saveTableActionEvent);
        bt5.addActionListener(this::sumTableActionEvent);
        bt6.addActionListener(this::aveTableActionEvent);
        bt8.addActionListener(this::minActionEvent);
        bt9.addActionListener(this::maxActionEvent);
        bt10.addActionListener(this::aboutActionEvent);
        bt11.addActionListener(this::helpActionEvent);

        return jPanel;

    }

    public JPanel createCenterPanel() {

        JPanel jPanel = new JPanel();

        jPanel.setLayout(new GridLayout(2, 1, 10, 10));

        northPanel = createNorthPanel();
        southPanel = createSouthPanel();

        jPanel.add(northPanel);
        jPanel.add(southPanel);

        return jPanel;
    }

    public JPanel createNorthPanel() {

        northNorthPanel = createNorthNorthPanel();
        createPanel = createCenterNorthPanel();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        jPanel.add(northNorthPanel,BorderLayout.NORTH);
        jPanel.add(createPanel,BorderLayout.CENTER);


        return jPanel;
    }

    public JPanel createSouthPanel() {

        calPanel = createCalPanel();
        infoPanel = createInfoPanel();

        resultTextArea = new JTextArea();

        JPanel jPanel = new JPanel();
        JPanel jPanelResult = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanelResult.setLayout(new BorderLayout(1,5));

        titledBorder = BorderFactory.createTitledBorder("Uzyskany rezultat");
        titledBorder.setTitleJustification(TitledBorder.CENTER);

        jPanelResult.setBorder(titledBorder);

        resultTextArea.setEnabled(false);
        resultTextArea.setDisabledTextColor(Color.black);
        resultTextArea.setFont(resultFont);
        resultTextArea.setLineWrap(true);

        resultTextArea.append("");

        jPanel.add(calPanel,BorderLayout.NORTH);
        jPanelResult.add(new JScrollPane(resultTextArea));
        jPanel.add(jPanelResult, BorderLayout.CENTER);
        jPanel.add(infoPanel, BorderLayout.SOUTH);

        return jPanel;
    }

    public JPanel createNorthNorthPanel() {

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        jSliderk = new JSlider(1, 5, 1);
        jSliderw = new JSlider(1, 5, 1);

        jSliderw.setMajorTickSpacing(0);
        jSliderk.setMajorTickSpacing(0);
        jSliderw.setMajorTickSpacing(1);
        jSliderk.setMajorTickSpacing(1);

        jSliderw.setPaintTicks(true);
        jSliderk.setPaintTicks(true);
        jSliderk.setPaintLabels(true);
        jSliderw.setPaintLabels(true);

        jLabel = new JLabel();
        jTextField = new JTextField(10);
        jTextArea = new JTextArea("Wprowadź liczbę");
        jTextArea1 = new JTextArea("Numer Wiersza");
        jTextArea2 = new JTextArea("Numer kolumny");

        jTextArea.setLayout(null);
        jTextArea1.setLayout(null);

        jTextArea.setOpaque(false);
        jTextArea1.setOpaque(false);
        jTextArea2.setOpaque(false);

        jTextArea.setFont(jTextArea.getFont().deriveFont(14f));
        jTextArea1.setFont(jTextArea.getFont().deriveFont(14f));
        jTextArea2.setFont(jTextArea.getFont().deriveFont(14f));

        jPanel.add(jLabel);
        jPanel.add(jTextArea);

        jPanel.add(jTextField);
        jPanel.add(jTextArea1);
        jPanel.add(jSliderw);
        jPanel.add(jTextArea2);
        jPanel.add(jSliderk);

        return jPanel;
    }

    public JPanel createCenterNorthPanel() {

        btnPanel = createBtnPanel();

        JPanel jPanel = new JPanel();
        JPanel tablePanel = new JPanel();
        JPanel panelIntBtnPanel = new JPanel();

        tablePanel.setLayout(new CardLayout(8,8));
        jPanel.setLayout(new BorderLayout(20,10));
        panelIntBtnPanel.setLayout(new CardLayout(40,40));

        jTable = new JTable(data, columns){
            DefaultTableCellRenderer renderRight = new DefaultTableCellRenderer();

            {
                renderRight.setHorizontalAlignment(SwingConstants.RIGHT);
            }

            @Override
            public TableCellRenderer getCellRenderer (int arg0, int arg1) {
                return renderRight;
            }
        };
        jTable.setEnabled(false);
        jTable.setRowHeight(31);

        JScrollPane jScrollPane = new JScrollPane(jTable);

        panelIntBtnPanel.add(btnPanel);
        tablePanel.add(jScrollPane);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());

        jPanel.add(tablePanel,BorderLayout.CENTER);
        jPanel.add(panelIntBtnPanel,BorderLayout.EAST);

        return jPanel;
    }

    public JPanel createBtnPanel() {

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));

        btnDodaj = new JButton("Dodaj");
        btnReset = new JButton("Zerowanie");
        btnSave = new JButton("Zapisz");

        btnDodaj.add(Box.createRigidArea(new Dimension(100, 20)));
        btnReset.add(Box.createRigidArea(new Dimension(100, 20)));
        btnSave.add(Box.createRigidArea(new Dimension(100, 20)));

        jPanel.add(btnDodaj);
        jPanel.add(Box.createVerticalStrut(10));

        jPanel.add(btnReset);
        jPanel.add(Box.createVerticalStrut(10));

        jPanel.add(btnSave);
        jPanel.add(Box.createVerticalStrut(10));

        btnDodaj.addActionListener(this::addToTableActionEvent);
        btnReset.addActionListener(this::resetTableActionEvent);
        btnSave.addActionListener(this::saveTableActionEvent);

        return jPanel;
    }

    public JPanel createCalPanel(){

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        calBtn = new JButton("Oblicz");

        calTextArea = new JTextArea("Obliczenia");

        calTextArea.setOpaque(false);

        jComboBox = new JComboBox(choseoperation);

        jComboBox.setPreferredSize(new Dimension(200,25));
        calBtn.setPreferredSize(new Dimension(100,25));

        jPanel.add(calTextArea);
        jPanel.add(jComboBox);
        jPanel.add(calBtn);

        calBtn.addActionListener(this::calActioEvent);

        return jPanel;
    }

    public JPanel createInfoPanel() {

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));

        infoLabel = new JLabel("Info");
        infoTextField = new JTextField();
        statusLabel = new JLabel("Status");
        statusTextField = new JTextField("ON");

        infoTextField.setEnabled(false);
        statusTextField.setEnabled(false);
        infoTextField.setDisabledTextColor(Color.black);
        statusTextField.setDisabledTextColor(Color.black);

        jPanel.add(Box.createHorizontalStrut(10));
        jPanel.add(infoLabel);
        jPanel.add(Box.createHorizontalStrut(10));
        jPanel.add(infoTextField);
        jPanel.add(Box.createHorizontalStrut(10));
        jPanel.add(statusLabel);
        jPanel.add(Box.createHorizontalStrut(10));
        jPanel.add(statusTextField);
        jPanel.add(Box.createHorizontalStrut(10));

        jPanel.setPreferredSize(new Dimension(1,25));

        return jPanel;
    }

    public void showTip()
    {
        DefaultTipModel tipModel = new DefaultTipModel();
        tipModel.add(new DefaultTip("Tip1", "Ludzkie oczy nie zmieniają swojego rozmiaru od urodzenia."));
        tipModel.add(new DefaultTip("Tip2", "W Chinach zakazane jest przytulanie drzew."));
        tipModel.add(new DefaultTip("Tip3", "Rakiety do tenisa były kiedyś wykonywane z krowich i owczych " +
                "jelit."));
        tipModel.add(new DefaultTip("Tip4", "W Singapurze zabronione jest żucie gumy."));
        tipModel.add(new DefaultTip("Tip5", "Woda kokosowa może być stosowano jako osocze krwi."));

        JTipOfTheDay tips = new JTipOfTheDay(tipModel);
        tips.showDialog(this);
    }

    public JDialog createWykres(){
        JDialog jDialog = new JDialog();
        jDialog.setSize(600,600);
        jDialog.setLocationRelativeTo(null);
        DefaultPieDataset dataset = new DefaultPieDataset();

        double columnNumber = 0;
        String columnText = null;

        for(int j = 0; j < 5; j++)
        {
            for(int i = 0; i < 5; i++)
            {
                columnNumber = columnNumber + Double.parseDouble(data[j][i].toString());
            }

            columnText = "["+String.valueOf(j + 1)+"]";
            dataset.setValue(columnText , columnNumber);
            columnNumber = 0;
        }

        JFreeChart chart = ChartFactory.createPieChart("Suma wierszy zawartych w komórkach tabeli",
                dataset, false, true, false);
        JPanel pane = new ChartPanel(chart);
        jDialog.add(pane);

        return jDialog;
    }

    public static int objectToInt(Object object) {
        int x = (Integer) object;
        return x;
    }

    public int sumTable() {

        int suma=0;

        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++) {
                suma = suma+objectToInt(jTable.getValueAt(i,j));
            }

        return suma;
    }

    public void zeroSetTable() {

        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++) {
                jTable.setValueAt(0,i,j);
            }
    }

    public int minTableValue() {
        int wynik=0;

        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++) {
                if(objectToInt(jTable.getValueAt(i,j))<=wynik);
                wynik = objectToInt(jTable.getValueAt(i, j));
            }

        return wynik;
    }

    public int maxTableValue() {

        int wynik = 0;

        try
        {
            wynik = objectToInt(jTable.getValueAt(0, 0));

            for(int i=0; i<5; i++) {
                for(int j=0; j<5;j++) {
                    if( objectToInt(jTable.getValueAt(i, j)) >= wynik ) {
                        wynik = objectToInt(jTable.getValueAt(i, j));
                    }
                }
            }
        }
        catch(Exception e)
        {
            return wynik;
        }

        return wynik;
    }

    private int getSliderk() {return jSliderk.getValue()-1;}

    private int getSliderw() {return jSliderw.getValue()-1;}

    private void addToTableActionEvent(ActionEvent e) {

        try {
            int i = Integer.parseInt(jTextField.getText());

            int k = getSliderk();
            int w = getSliderw();

            jTable.setValueAt(i, w, k);

        } catch (Exception txtError) {
            JOptionPane.showMessageDialog(this, "Błąd wprowadzania danych do tabeli.");
            infoTextField.setText("ERROR...Wprowadź liczbę");
            Timer t = new Timer(2000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    infoTextField.setText(null);
                }
            });
            t.setRepeats(false);
            t.start();
        }
    }

    private void sumTableActionEvent(ActionEvent event) {
        resultTextArea.setText("Suma wynosi: "+Integer.toString(sumTable()));
    }

    private void aveTableActionEvent(ActionEvent event){

        double db = (sumTable()/25.0);
        String str = db+"" ;
        resultTextArea.setText("Średnia wynosi: "+str);
    }

    private void minActionEvent(ActionEvent event){

        resultTextArea.setText("Wartość minimalna wynosi: "+Integer.toString(minTableValue()));
    }

    private void maxActionEvent(ActionEvent event){

        resultTextArea.setText("Wartość maksymalna wynosi: "+Integer.toString(maxTableValue()));
    }

    public void resetTableActionEvent(ActionEvent e) {zeroSetTable();}

    public void saveTableActionEvent(ActionEvent e) {

        String ObjBtn[]={"Tak","Nie"};
        int response = JOptionPane.showOptionDialog(null,"Zapisać do pliku?","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE, null,ObjBtn,ObjBtn[1]);
        if (response == JOptionPane.YES_OPTION) {
            try
            {
                FileWriter csvWriter = new FileWriter("Table.txt");
                for (int i = 0; i < 5; i++)
                {
                    for (int j = 0; j < 5; j++)
                    {
                        csvWriter.append(data[i][j].toString());
                        csvWriter.append(" ");
                    }
                    csvWriter.append("\n");
                }
                csvWriter.flush();
                csvWriter.close();
                JOptionPane.showMessageDialog(this, "Zapis udany");
                infoTextField.setText("Zapisano do pliku");
                Timer t = new Timer(2000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        infoTextField.setText(null);
                    }
                });
                t.setRepeats(false);
                t.start();

            }
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(this, "Błąd przy zapisie do pliku");
            }
        }

    }

    public void calActioEvent(ActionEvent event) {

        switch (jComboBox.getSelectedIndex()) {
            case 0:

                resultTextArea.setText("Suma wynosi: "+Integer.toString(sumTable()));
                break;

            case 1:

                double db = (sumTable()/25.0);
                String str = db+"" ;
                resultTextArea.setText("Średnia wynosi: "+str);
                break;

            case 2:

                resultTextArea.setText("Wartość maksymalna wynosi: "+Integer.toString(maxTableValue()));
                break;

            case 3:

                resultTextArea.setText("Wartość minimalna wynosi: "+Integer.toString(minTableValue()));
                break;
        }
    }

    public void aboutActionEvent(ActionEvent event) {

        if(aboutWindow!=null) aboutWindow.setVisible(true);
        else {
            aboutWindow = new AboutWindow();
            aboutWindow.setVisible(true);
        }
    }

    public void helpActionEvent(ActionEvent event) {

        if(HelpWindow!=null) HelpWindow.setVisible(true);
        else {
            HelpWindow = new HelpWindow();
            HelpWindow.setVisible(true);
        }
    }

    public void exitActionEvent(ActionEvent event) {

        String ObjBtn[]={"Tak","Nie"};
        int response = JOptionPane.showOptionDialog(null,"Czy jesteś tego pewien?","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE, angry,ObjBtn,ObjBtn[1]);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }

    }

    public void wykresActionEvent(ActionEvent event) {
        jDialog = createWykres();
        jDialog.setVisible(true);
    }

    public void calendarActionEvent(ActionEvent event) {
        resultTextArea.setText("Wybrana Data:"+calendarGetDate());
    }

    public String calendarGetDate()
    {
        calendar.updateUI();
        return calendar.getDate().toString();
    }

    public static void main(String[] args) {

        MainMyWindow myWindow = new MainMyWindow();
        myWindow.setVisible(true);
    }

}
