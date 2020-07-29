package me.bytebeats.handler;

import com.intellij.ui.JBColor;
import me.bytebeats.meta.Stock;
import me.bytebeats.tool.StringResUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsStockHandler {
    public static final long REFRESH_INTERVAL = 10L * 1000L;

    protected List<Stock> stocks = new ArrayList<>();
    private boolean isHidden = false;
    private boolean isRedRise = true;
    private JTable jTable;
    private JLabel jLabel;
    private int[] tab_sizes = {0, 0, 0, 0, 0, 0, 0, 0};
    private String[] column_names = {StringResUtils.STOCK_NAME, StringResUtils.STOCK_SYMBOL, StringResUtils.STOCK_LATEST_PRICE,
            StringResUtils.STOCK_RISE_AND_FALL, StringResUtils.STOCK_RISE_AND_FALL_RATIO, StringResUtils.STOCK_VOLUME,
            StringResUtils.STOCK_TURNOVER, StringResUtils.STOCK_MKT_VALUE};

    private int[] numColumnIdx = {3, 4};

    public AbsStockHandler(JTable table, JLabel label) {
        this.jTable = table;
        this.jLabel = label;
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        FontMetrics metrics = jTable.getFontMetrics(jTable.getFont());
        jTable.setRowHeight(Math.max(jTable.getRowHeight(), metrics.getHeight()));
    }

    public abstract void load(List<String> symbols);

    protected void updateView() {
        SwingUtilities.invokeLater(() -> {
            restoreTabSizes();
            DefaultTableModel model = new DefaultTableModel(convert2Data(), column_names);
            jTable.setModel(model);
            resetTabSize();
            updateRowTextColors();
            updateTimestamp();
        });
    }

    private void updateTimestamp() {
        jLabel.setText(String.format(StringResUtils.REFRESH_TIMESTAMP, LocalDateTime.now().format(DateTimeFormatter.ofPattern(StringResUtils.TIMESTAMP_FORMATTER))));
    }

    private void restoreTabSizes() {
        if (jTable.getColumnModel().getColumnCount() == 0) {
            return;
        }
        for (int i = 0; i < tab_sizes.length; i++) {
            tab_sizes[i] = jTable.getColumnModel().getColumn(i).getWidth();
        }
    }

    private void resetTabSize() {
        for (int i = 0; i < tab_sizes.length; i++) {
            if (tab_sizes[i] > 0) {
                jTable.getColumnModel().getColumn(i).setWidth(tab_sizes[i]);
                jTable.getColumnModel().getColumn(i).setPreferredWidth(tab_sizes[i]);
            }
        }
    }

    private Object[][] convert2Data() {
        Object[][] data = new Object[stocks.size()][column_names.length];
        for (int i = 0; i < stocks.size(); i++) {
            Stock stock = stocks.get(i);
            data[i] = new Object[]{stock.getName(), stock.getSymbol(), stock.getLatestPrice(), stock.getChange(),
                    stock.getChangeRatioString(), stock.getVolume(), stock.getTurnover(), stock.getMarketValue()};
        }
        return data;
    }

    protected void updateStock(Stock stock) {
        int idx = stocks.indexOf(stock);
        if (idx > -1 && idx < stocks.size()) {
            stocks.set(idx, stock);
        } else {
            stocks.add(stock);
        }
    }

    private void updateRowTextColors() {
        for (int idx : numColumnIdx) {
            jTable.getColumn(jTable.getColumnName(idx)).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    double chg = 0.0;
                    try {
                        String chgRaw = value.toString();
                        if (column == 4) {
                            chgRaw = chgRaw.substring(0, chgRaw.length() - 1);
                        }
                        chg = Double.parseDouble(chgRaw);
                    } catch (NumberFormatException e) {
                        chg = 0.0;
                    }
                    if (!isHidden) {
                        if (chg == 0) {
                            setForeground(JBColor.DARK_GRAY);
                        } else if (isRedRise) {
                            if (chg > 0) {
                                setForeground(JBColor.RED);
                            } else {
                                setForeground(JBColor.GREEN);
                            }
                        } else {
                            if (chg > 0) {
                                setForeground(JBColor.GREEN);
                            } else {
                                setForeground(JBColor.RED);
                            }
                        }
                    } else {
                        setForeground(JBColor.DARK_GRAY);
                    }
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            });
        }
        if (isHidden) {

        } else if (isRedRise) {

        }
    }

    public String appendParams(String params) {
        return StringResUtils.QT_STOCK_URL + params;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isRedRise() {
        return isRedRise;
    }

    public void setRedRise(boolean redRise) {
        isRedRise = redRise;
    }
}
