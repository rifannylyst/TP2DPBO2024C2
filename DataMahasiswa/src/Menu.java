import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class Menu extends JFrame{
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(480, 560);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.white);
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;
    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTextField emailField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel emailLabel;
    private JLabel usiaLabel;
    private JSpinner ageSpinner;

    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // buat objek database
        database = new Database();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        // sembunyikan button delete
        deleteButton.setVisible(true);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    deleteData();
                }
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // saat tombol
                clearForm();
            }
        });
        // Saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Dapatkan baris tabel yang diklik
                int row = mahasiswaTable.rowAtPoint(e.getPoint());

                // Pastikan baris yang diklik valid
                if (row >= 0 && row < mahasiswaTable.getRowCount()) {
                    // Ambil data dari baris yang diklik
                    String selectedNim = mahasiswaTable.getValueAt(row, 1).toString();
                    String selectedNama = mahasiswaTable.getValueAt(row, 2).toString();
                    String selectedJenisKelamin = mahasiswaTable.getValueAt(row, 3).toString();
                    String selectedEmail = mahasiswaTable.getValueAt(row, 4).toString();
                    int selectedAge = Integer.parseInt(mahasiswaTable.getValueAt(row, 5).toString());

                    // Isi data ke dalam formulir di atas tabel
                    nimField.setText(selectedNim);
                    namaField.setText(selectedNama);
                    jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                    emailField.setText(selectedEmail);
                    ageSpinner.setValue(selectedAge);

                    // Simpan indeks baris yang dipilih
                    selectedIndex = row;

                    // Ubah teks tombol "Add" menjadi "Update"
                    addUpdateButton.setText("Update");
                    // Tampilkan tombol delete
                    deleteButton.setVisible(true);
                }
            }
        });

    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Email", "Usia"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");

            int i = 0;
            while (resultSet.next()) {
                Object[] row = new Object[column.length];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("email");
                row[5] = resultSet.getString("usia");


                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return temp;
    }

    // Method untuk memasukkan data ke dalam database
    public void insertData() {
        // Ambil nilai dari field dan combo box
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = Objects.requireNonNull(jenisKelaminComboBox.getSelectedItem()).toString();
        String email = emailField.getText();
        int usia = (int) ageSpinner.getValue();

        // Validasi input
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua input harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cek apakah NIM sudah ada di database
        if (isNIMExists(nim)) {
            JOptionPane.showMessageDialog(null, "NIM " + nim + " sudah ada di database!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert data ke dalam database
        String sql = "INSERT INTO mahasiswa (nim, nama, jenis_kelamin, email, usia) VALUES ('" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + email + "', " + usia + ")";
        int result = database.insertUpdateDeleteQuery(sql);

        if (result > 0) {
            // Update tabel
            mahasiswaTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Beri feedback
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menambahkan data ke database!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method untuk memperbarui data di dalam database
    public void updateData() {
        // Ambil nilai dari field dan combo box
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = Objects.requireNonNull(jenisKelaminComboBox.getSelectedItem()).toString();
        String email = emailField.getText();
        int usia = (int) ageSpinner.getValue();

        // Validasi input
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua input harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perbarui data di dalam database
        String sql = "UPDATE mahasiswa SET nama='" + nama + "', jenis_kelamin='" + jenisKelamin + "', email='" + email + "', usia=" + usia + " WHERE nim='" + nim + "'";
        int result = database.insertUpdateDeleteQuery(sql);

        if (result > 0) {
            // Update tabel
            mahasiswaTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Beri feedback
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal memperbarui data di database!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        // Membuat dialog konfirmasi sebelum menghapus data
        int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            // Ambil NIM dari data yang dipilih
            String nim = nimField.getText();

            // Hapus data dari database
            String sql = "DELETE FROM mahasiswa WHERE nim='" + nim + "'";
            int result = database.insertUpdateDeleteQuery(sql);

            if (result > 0) {
                // Update tabel
                mahasiswaTable.setModel(setTable());

                // Bersihkan form
                clearForm();

                // Beri feedback
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data dari database!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // Method untuk membersihkan form
    public void clearForm() {
        // Bersihkan nilai dari field dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedIndex(0);
        emailField.setText("");
        ageSpinner.setValue(0);

        // Ubah teks tombol Add menjadi Add
        addUpdateButton.setText("Add");
        // Sembunyikan tombol delete
        deleteButton.setVisible(false);
        // Ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    // Method untuk memeriksa apakah NIM sudah ada di database
    private boolean isNIMExists(String nim) {
        try {
            // Lakukan query ke database untuk memeriksa keberadaan NIM
            String query = "SELECT COUNT(*) FROM mahasiswa WHERE nim = '" + nim + "'";
            ResultSet resultSet = database.selectQuery(query);
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void populateList() {
        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", "amelia@gmail.com", 20));
        listMahasiswa.add(new Mahasiswa("2202292", "Muhammad Iqbal Fadhilah", "Laki-laki", "iqbal@gmail.com", 22));
        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki", "rifky@gmail.com", 25));
        listMahasiswa.add(new Mahasiswa("2210239", "Muhammad Hanif Abdillah", "Laki-laki", "hanif@gmail.com", 24));
        listMahasiswa.add(new Mahasiswa("2202046", "Nurainun", "Perempuan", "ainun@gmail.com", 22));
        listMahasiswa.add(new Mahasiswa("2205101", "Kelvin Julian Putra", "Laki-laki", "kelvin@gmail.com", 21));
        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan", "rifanny@gmail.com", 20));
        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan", "revana@gmail.com", 21));
        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki", "rakha@gmail.com", 24));
        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki", "roshan@gmail.com", 23));
        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki", "rais@gmail.com", 22));
        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan", "ratu@gmail.com", 20));
        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki", "fahreza@gmail.com", 21));
        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki", "raven@gmail.com", 25));
        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki", "arya@gmail.com", 23));
        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki", "marvel@gmail.com", 24));
        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki", "hafiizh@gmail.com", 21));
        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan", "rifa@gmail.com", 22));
        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki", "imam@gmail.com", 24));
        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan", "meiva@gmail.com", 23));
    }
}