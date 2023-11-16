import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class MainApplicationFrame extends JFrame {
    private User user;

    public MainApplicationFrame(User user) {
        this.user = user;

        setTitle("Main Application");
        setSize(600, 400);
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
                    // Логика просмотра товаров
                    JOptionPane.showMessageDialog(null, "Просмотр товаров");
                }
            });

            // Добавление кнопки просмотра товаров
            add(viewProductsButton);
        }

        // Добавление кнопки возврата к окну авторизации
        JButton logoutButton = new JButton("Выйти");
        logoutButton.addActionListener(new ActionListener() {
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

    private void addProduct() {
        // Создание формы для ввода данных
        JPanel inputPanel = new JPanel(new GridLayout(12, 2));
        JTextField skuField = new JTextField();
        JTextField productNameField = new JTextField();
        JTextField unitOfMeasurement = new JTextField();
        JTextField cost = new JTextField();
        JTextField maxDiscountSize = new JTextField();
        JTextField manufacturer = new JTextField();
        JTextField supplier = new JTextField();
        JTextField category = new JTextField();
        JTextField currentDiscount = new JTextField();
        JTextField quantityInStock = new JTextField();
        JTextField description = new JTextField();
        JTextField image = new JTextField();

        // Добавьте другие поля ввода для остальных атрибутов товара

        inputPanel.add(new JLabel("Артикуль:"));
        inputPanel.add(skuField);
        inputPanel.add(new JLabel("Название продукта:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Единица измерения товара:"));
        inputPanel.add(unitOfMeasurement);
        inputPanel.add(new JLabel("Стоимость:"));
        inputPanel.add(cost);
        inputPanel.add(new JLabel("Размер максимально возможной скидки:"));
        inputPanel.add(maxDiscountSize);
        inputPanel.add(new JLabel("Производитель:"));
        inputPanel.add(manufacturer);
        inputPanel.add(new JLabel("Поставщик:"));
        inputPanel.add(supplier);
        inputPanel.add(new JLabel("Категория товара:"));
        inputPanel.add(category);
        inputPanel.add(new JLabel("Действующая скидка:"));
        inputPanel.add(currentDiscount);
        inputPanel.add(new JLabel("Количество на складе:"));
        inputPanel.add(quantityInStock);
        inputPanel.add(new JLabel("Описание:"));
        inputPanel.add(description);
        inputPanel.add(new JLabel("Изображение:"));
        inputPanel.add(image);


        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Product Information",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Создайте объект Product с введенными данными
            Product newProduct = new Product(
                    skuField.getText(),
                    productNameField.getText(),
                    Integer.parseInt(unitOfMeasurement.getText()),
                    BigDecimal.valueOf(Double.parseDouble(cost.getText())),
                    BigDecimal.valueOf(Double.parseDouble(maxDiscountSize.getText())),
                    Integer.parseInt(manufacturer.getText()),
                    Integer.parseInt(supplier.getText()),
                    Integer.parseInt(category.getText()),
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
}