package dao.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class ResultSetUtils {

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();

        // Nome das colunas
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i).substring(0, 1).toUpperCase() + metaData.getColumnName(i).substring(1).toLowerCase();
        }

        // Cria o modelo
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Adiciona as linhas
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }

        return model;
    }
}
