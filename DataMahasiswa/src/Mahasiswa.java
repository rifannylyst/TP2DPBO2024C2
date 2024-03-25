public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private String email;
    private int usia;

    public Mahasiswa(String nim, String nama, String jenisKelamin, String email, int usia) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.email = email;
        this.usia = usia;
    }

    public void setUsia(int usia) { this.usia = usia; }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setEmail(String email) { this.email = email; }

    public int getUsia() { return usia; }

    public String getNim() {
        return this.nim;
    }

    public String getNama() {
        return this.nama;
    }

    public String getJenisKelamin() {
        return this.jenisKelamin;
    }

    public String getEmail() { return email; }

}