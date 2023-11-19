import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class MainApplicationFrame extends JFrame {
    private User user;
    private DefaultListModel<String> productListModel;
    private JList<String> productList;
    private JLabel statusLabel;

    private void initComponents() {
        productListModel = new DefaultListModel<String>();
        productList = new JList<>(productListModel);
        statusLabel = new JLabel();

    }

    public MainApplicationFrame(User user) {
        this.user = user;
        initComponents();
        setTitle("Main Application");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Вывод ФИО пользователя в правом верхнем углу
        JLabel usernameLabel = new JLabel("Добро пожаловать, " + user.getFullName());
        add(usernameLabel);

        // Проверка роли пользователя и добавление соответствующих компонентов
        if (user.getRole() == 1) {
            // Если пользователь - администратор
            JButton addButton = new JButton("Добавить товар");
            JButton editButton = new JButton("Редактировать товар");
            JButton deleteButton = new JButton("Удалить товар");

            // Добавление обработчиков событий для кнопок
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Логика добавления товара
                    addProduct();
                }
            });

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Логика редактирования товара
                    JOptionPane.showMessageDialog(null, "Редактирование товара");
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Логика удаления товара
                    JOptionPane.showMessageDialog(null, "Удаление товара");
                }
            });

            // Добавление кнопок администратора
            add(addButton);
            add(editButton);
            add(deleteButton);

        } else {
            // Если пользователь - клиент или менеджер
            JButton viewProductsButton = new JButton("Показать товары");

            // Добавление обработчика события для кнопки
            viewProductsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        List<Product> productList = DatabaseHelper.selectProduct();
                        List<Manufacturer> manufacturers = DatabaseHelper.selectMan();

                        // Создаем новую модель таблицы
                        DefaultTableModel tableModel = new DefaultTableModel();

                        // Добавляем колонки в модель таблицы
                        tableModel.addColumn("Картинка");
                        tableModel.addColumn("Товар");
                        tableModel.addColumn("Наличие");

                        // Добавляем новые товары в таблицу
                        for (Product product : productList) {
                            String productInfo = "<html>"
                                    + "Название: " + product.getProductName() + "<br/> <br/>"
                                    + "Описание: " + product.getDescription() + "<br/> <br/>"
                                    + "Производитель: " + manufacturers.get(product.getManufacturer() - 1).getManName() + "<br/> <br/>"
                                    + "Цена: " + product.getCost()
                                    + "</html>";

                            tableModel.addRow(new Object[]{product.getImage(), productInfo, product.getQuantityInStock()});
                        }
                        // Создаем JTable с использованием новой модели
                        JTable table = new JTable(tableModel);

                        table.setRowHeight(180);

                        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                        renderer.setVerticalAlignment(SwingConstants.TOP);
                        table.getColumnModel().getColumn(1).setCellRenderer(renderer);

                        // Добавляем рендерер для первого столбца (изображения)
                        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
                        table.getColumnModel().getColumn(0).setPreferredWidth(10);
                        table.getColumnModel().getColumn(2).setPreferredWidth(10);
                        // Создаем JScrollPane и добавляем в него JTable
                        JScrollPane scrollPane = new JScrollPane(table);

                        // Создаем новый JFrame и добавляем в него JScrollPane
                        JFrame productsFrame = new JFrame("Список товаров");
                        productsFrame.getContentPane().add(scrollPane);

                        // Устанавливаем параметры отображения окна
                        productsFrame.setSize(600, 600);
                        productsFrame.setLocationRelativeTo(null);
                        productsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Закрываем окно, не завершая приложение
                        productsFrame.setVisible(true);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Ошибка при получении товаров: " + ex.getMessage());
                    }
                }
            });

            // Добавление кнопки просмотра товаров
            add(viewProductsButton);
        }

        // Добавление кнопки возврата к окну авторизации
        JButton logoutButton = new JButton("Выйти");
        logoutButton.addActionListener(new

                                               ActionListener() {
                                                   @Override
                                                   public void actionPerformed(ActionEvent e) {
                                                       // Логика выхода из учетной записи и возвращения к окну авторизации
                                                       dispose(); // Закрываем текущее окно
                                                       new LoginFrame(); // Открываем окно авторизации
                                                   }
                                               });

        add(logoutButton);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setVisible(true);

    }

    static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            if (value != null) {
                ImageIcon imageIcon = new ImageIcon(value.toString());
                Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(image));
            }
            return label;
        }
    }

    private void addProduct() {
        List<Manufacturer> manufacturers = DatabaseHelper.selectMan();
        List<Supplier> suppliers = DatabaseHelper.selectSuppliers();
        List<Category> categories = DatabaseHelper.selectCategories();
        // Создание формы для ввода данных
        JPanel inputPanel = new JPanel(new GridLayout(12, 2));
        JTextField skuField = new JTextField();
        JTextField productNameField = new JTextField();
        JComboBox<String> unitOfMeasurementComboBox = new JComboBox<>(getUnitOfMeasurementOptions()); // Замените String на ваш тип данных
        JTextField cost = new JTextField();
        JTextField maxDiscountSize = new JTextField();
        JComboBox<String> manufacturerComboBox = new JComboBox<>(getManufacturerOptions()); // Замените String на ваш тип данных
        JComboBox<String> supplierComboBox = new JComboBox<>(getSupplierOptions()); // Замените String на ваш тип данных
        JComboBox<String> categoryComboBox = new JComboBox<>(getCategoryOptions()); // Замените String на ваш тип данных
        JTextField currentDiscount = new JTextField();
        JTextField quantityInStock = new JTextField();
        JTextField description = new JTextField();
        JTextField image = new JTextField();
        JButton chooseImageButton = new JButton("Выбрать изображение");

        // Добавьте другие поля ввода для остальных атрибутов товара

        inputPanel.add(new JLabel("Артикуль:"));
        inputPanel.add(skuField);
        inputPanel.add(new JLabel("Название продукта:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Единица измерения товара:"));
        inputPanel.add(unitOfMeasurementComboBox);
        inputPanel.add(new JLabel("Стоимость:"));
        inputPanel.add(cost);
        inputPanel.add(new JLabel("Размер максимально возможной скидки:"));
        inputPanel.add(maxDiscountSize);
        inputPanel.add(new JLabel("Производитель:"));
        inputPanel.add(manufacturerComboBox);
        inputPanel.add(new JLabel("Поставщик:"));
        inputPanel.add(supplierComboBox);
        inputPanel.add(new JLabel("Категория товара:"));
        inputPanel.add(categoryComboBox);
        inputPanel.add(new JLabel("Действующая скидка:"));
        inputPanel.add(currentDiscount);
        inputPanel.add(new JLabel("Количество на складе:"));
        inputPanel.add(quantityInStock);
        inputPanel.add(new JLabel("Описание:"));
        inputPanel.add(description);
        inputPanel.add(new JLabel("Выбор изображения:"));
        inputPanel.add(chooseImageButton);

        // Добавление слушателя для кнопки выбора изображения
        chooseImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                image.setText(selectedFile.getAbsolutePath());
            }
        });

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Product Information",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int manId = 0;
            int supId = 0;
            int catId = 0;
            for (Manufacturer manufacturer : manufacturers) {
                int count = manufacturer.findByName(manufacturerComboBox.getSelectedItem().toString());
                if (count != -1) {
                    manId = count;
                    break;
                }
            }
            for (Supplier supplier : suppliers) {
                int count = supplier.findByName(supplierComboBox.getSelectedItem().toString());
                if (count != -1) {
                    supId = count;
                    break;
                }
            }for (Category category : categories) {
                int count = category.findByName(categoryComboBox.getSelectedItem().toString());
                if (count != -1) {
                    catId = count;
                    break;
                }
            }


            System.out.println(catId);
            System.out.println(manId);
            System.out.println(supId);
            // Создайте объект Product с введенными данными



            Product newProduct = new Product(
                    skuField.getText(),
                    productNameField.getText(),
                    1,
                    BigDecimal.valueOf(Double.parseDouble(cost.getText())),
                    BigDecimal.valueOf(Double.parseDouble(maxDiscountSize.getText())),
                    manId,
                    supId,
                    catId,
                    BigDecimal.valueOf(Double.parseDouble(currentDiscount.getText())),
                    Integer.parseInt(quantityInStock.getText()),
                    description.getText(),
                    image.getText()
            );

            // Вызов метода добавления товара в базу данных
            if (DatabaseHelper.addProduct(newProduct)) {
                JOptionPane.showMessageDialog(null, "Продукт успешно добавлен!");
            } else {
                JOptionPane.showMessageDialog(null, "Ошибка добавления продукта.");
            }
        }
    }

    private String[] getUnitOfMeasurementOptions() {
        return new String[]{"Рулон"};
    }

    private String[] getManufacturerOptions() {
        List<Manufacturer> manufacturers = DatabaseHelper.selectMan();

        String[] opt = new String[manufacturers.size()];
        int i = 0;
        for (Manufacturer manufacturer: manufacturers) {
            opt[i] = manufacturer.getManName();
            i++;
        }
        // Реализуйте логику получения опций для производителя
        return opt;
    }

    private String[] getSupplierOptions() {
        List<Supplier> suppliers = DatabaseHelper.selectSuppliers();

        String[] opt = new String[suppliers.size()];
        int i = 0;
        for (Supplier supplier: suppliers) {
            opt[i] = supplier.getSupplierName();
            i++;
        }
        // Реализуйте логику получения опций для производителя
        return opt;
    }

    private String[] getCategoryOptions() {
        List<Category> categories = DatabaseHelper.selectCategories();

        String[] opt = new String[categories.size()];
        int i = 0;
        for (Category category: categories) {
            opt[i] = category.getCategoryName();
            i++;
        }
        // Реализуйте логику получения опций для производителя
        return opt;
    }
}