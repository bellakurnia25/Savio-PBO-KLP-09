# Savio-PBO-KLP-09
<img width="845" height="479" alt="Screenshot 2026-06-01 112849" src="https://github.com/user-attachments/assets/585d4322-8bb1-43e9-b57c-94abbc85a14d" />


SAVIO – Smart Personal Finance Tracker
## 1. Apa itu SAVIO?

Nama **SAVIO** dirancang sebagai representasi dua makna fungsional yang saling melengkapi (*double meaning*):
* **Filosofis (Bahasa Italia):** Kata *Savio* berarti **"Bijaksana"** (*Wise* / *Clever*), melambangkan visi utama aplikasi ini untuk memandu pengguna agar bertindak cerdas dalam mengambil keputusan finansial.
* **Fungsional (Bahasa Inggris):** Nama ini terinspirasi dari kata **"Saving"** atau **"Save"** yang merujuk pada aktivitas menabung, menyimpan, dan mengendalikan dana.
Kombinasi tersebut menegaskan posisi SAVIO sebagai asisten pintar yang membantu mahasiswa dan anak muda untuk **mengelola, mengalokasikan, dan menata** keuangan mereka secara bijak, rapi, dan terstruktur.

SAVIO (Smart Personal Finance Tracker) adalah aplikasi keuangan untuk mengelola keuangan pribadi yang dirancang untuk membantu pengguna mengatur pemasukan, pengeluaran, dan tabungan secara lebih teratur. Aplikasi ini ditujukan terutama bagi mahasiswa dan anak muda yang sering mengalami kesulitan dalam mengontrol keuangan sehari-hari.
Aplikasi ini memungkinkan pengguna untuk mencatat pemasukan, pengeluaran, mengatur alokasi dana otomatis, memantau dana darurat, serta melihat laporan keuangan dalam satu sistem terintegrasi.
SAVIO dibuat menggunakan konsep Object Oriented Programming (OOP) sehingga memiliki struktur kode yang modular, rapi, dan mudah dikembangkan.

## 2. Halaman dan Fitur SAVIO

**a. Welcome Screen (Halaman Pembuka)**  
<img width="1920" height="1080" alt="Screenshot 2026-05-31 233304" src="https://github.com/user-attachments/assets/7e8f7522-e213-4586-a11c-04b5ea743d64" />  

Welcome Screen merupakan antarmuka penyambut yang pertama kali dilihat oleh pengguna saat aplikasi SAVIO dijalankan. Halaman ini berfungsi untuk memperkuat identitas visual aplikasi dengan menampilkan logo resmi dan slogan utama. Secara fungsional, halaman ini dilengkapi dengan deteksi aksi klik global (*Click-anywhere Event Handler*) yang memudahkan pengguna untuk bertransisi langsung menuju halaman autentikasi secara interaktif.
* *Berkas terkait:* `WelcomeView.java`, `LogoSavio.java`, `MainApp.java`


**b. Login Screen (Halaman Autentikasi)**  
<img width="1920" height="1080" alt="Screenshot 2026-05-31 233320" src="https://github.com/user-attachments/assets/297ed4b2-8f0e-4d00-a67b-5181e8f6955b" />

Login Screen berfungsi sebagai sistem gerbang keamanan (*security gate*) yang memvalidasi hak akses pengguna sebelum masuk ke dalam sistem keuangan pribadi SAVIO. Halaman ini menyediakan formulir pengisian akun yang terintegrasi langsung dengan mekanisme pencarian data pada berkas basis data lokal. Selain memproses log masuk, halaman ini juga menyediakan opsi navigasi bagi pengguna baru yang ingin beralih ke menu registrasi pembuatan akun.
* *Berkas terkait:* `LoginView.java`, `DataSesi.java`, `KoneksiJSON.java`


**c. Dashboard**
<img width="1920" height="1080" alt="Screenshot 2026-06-01 112047" src="https://github.com/user-attachments/assets/2a3ebc2b-6fbb-4132-8ad8-8c6ed4273482" />


Fitur Dashboard berfungsi sebagai pusat informasi keuangan pengguna. Pada halaman ini pengguna dapat melihat secara *real-time* menggunakan *JavaFX Property Binding*:
* Saldo aktif
* Dana kebutuhan
* Dana keinginan
* Dana darurat

*Fitur ini dibuat menggunakan:* `DashboardView.java`, `CustomCard.java`, `DataDompet.java`

---

**d. Transaksi**
<img width="1920" height="1080" alt="Screenshot 2026-06-01 112056" src="https://github.com/user-attachments/assets/d1c2bcb6-ebe0-422d-8865-ad48a023766e" />

<img width="587" height="845" alt="Screenshot 2026-06-01 112220" src="https://github.com/user-attachments/assets/afc67ee1-5930-44d8-9f6a-3062a457ef3c" />


Fitur Transaksi digunakan untuk:
* Menambahkan pemasukan
* Menambahkan pengeluaran
* Mengedit transaksi
* Menghapus transaksi
* Melihat riwayat arus kas

Sistem transaksi juga terhubung langsung dengan alokasi dana otomatis.
*Fitur ini dibuat menggunakan:* `TransaksiView.java`, `ModelTransaksi.java`

---

**e. Alokasi Dana**
<img width="1920" height="1080" alt="Screenshot 2026-06-01 112104" src="https://github.com/user-attachments/assets/81de947d-3fb4-4b44-8a8c-2100704122da" />


Fitur Alokasi Dana berfungsi untuk membagi pemasukan pengguna secara otomatis menggunakan metode persentase, yang bebas ditentukan oleh pengguna. 
* *Contoh:* 50% kebutuhan, 30% keinginan, 20% tabungan/dana darurat.
* Perhitungan dilakukan secara otomatis melalui fungsi `alokasikanPemasukanOtomatis()` pada file `DataDompet.java`.

---

**f. Dana Darurat**
<img width="1920" height="1080" alt="Screenshot 2026-06-01 112110" src="https://github.com/user-attachments/assets/f0b716b0-4e59-4183-bd08-1ca89987b2fc" />


Fitur Dana Darurat membantu pengguna menyimpan sebagian pemasukan secara otomatis sebagai tabungan keamanan finansial. Dana darurat akan bertambah otomatis setiap pengguna menambahkan pemasukan (*Income*).
* *Fitur ini menggunakan:* `DanaDaruratView.java`, `DataDompet.java`

---

**g. Laporan**
<img width="1920" height="1080" alt="Screenshot 2026-06-01 112117" src="https://github.com/user-attachments/assets/07e434b5-4eae-4864-aa10-0b499eb6c845" />


Fitur Laporan digunakan untuk menampilkan ringkasan kondisi keuangan pengguna berdasarkan transaksi yang telah dilakukan. Laporan membantu pengguna memahami:
* Total pemasukan
* Total pengeluaran
* Kondisi saldo saat ini

*Fitur ini dibuat menggunakan:* `LaporanView.java`

---

**h. Profil**
<img width="1920" height="1080" alt="Screenshot 2026-06-01 112142" src="https://github.com/user-attachments/assets/bf1e03c6-3ca5-4a4d-ae9f-2550662121ca" />


Fitur Profil digunakan untuk:
* Mengubah nama pengguna
* Mengganti *password*
* Melihat informasi akun

Semua perubahan profil akan langsung tersimpan otomatis ke database JSON.
*Fitur ini dibuat menggunakan:* `ProfilView.java`, `DataSesi.java`, `KoneksiJSON.java`

**i. Logout Function (Sistem Keluar Akun)**  
<img width="1920" height="1080" alt="Screenshot 2026-06-01 112150" src="https://github.com/user-attachments/assets/c5124fc8-5695-4a8b-b56b-4d5b0d2650c9" />


Fitur Logout merupakan mekanisme penutupan sesi yang menjamin keamanan data finansial pengguna setelah selesai menggunakan aplikasi. Saat diaktifkan melalui menu navigasi, fitur ini bekerja di latar belakang untuk mengosongkan data pada memori *runtime* aktif dan memastikan seluruh sinkronisasi data transaksi terakhir telah terkunci dengan aman di dalam database lokal, sebelum akhirnya mengembalikan visualisasi layar ke halaman login awal.
* *Berkas terkait:* `Sidebar.java`, `ProfilView.java`, `DataSesi.java`



## 3. Sistem Database & Manajemen Data
SAVIO menggunakan sistem penyimpanan data berbasis berkas (**File-Based Storage**) dengan format **JSON (JavaScript Object Notation)** sebagai database lokal yang persisten. 

Cara kerja manajemen data pada aplikasi ini adalah sebagai berikut:
* **Arsitektur Deserialisasi (Load Data):** Saat aplikasi pertama kali diluncurkan, kelas `KoneksiJSON.java` akan membaca berkas fisik `database_savio.json`. Teks string di dalam berkas tersebut kemudian diurai (*parsing*) dan diubah menjadi objek runtime Java ke dalam `DataDompet` agar bisa ditampilkan di layar.
* **Arsitektur Serialisasi (Save Data):** Setiap kali pengguna melakukan operasi manipulasi data—seperti menambah, mengedit, atau menghapus transaksi—aplikasi akan otomatis mengonversi kembali seluruh objek memori menjadi format teks JSON, lalu menguncinya secara permanen ke dalam berkas `database_savio.json`.
* **Keuntungan Pendekatan Ini:** Aplikasi menjadi sangat ringan, bersifat portabel (tidak perlu menginstal server database tambahan seperti MySQL di laptop penguji), serta proses baca-tulis data berjalan sangat cepat secara lokal.




### 4. Struktur Program

```text
SAVIO/
├── java/
│   └── com/
│       └── savio/
│           ├── dao/
│           │   └── TransaksiRepository.java  # Repository penyimpanan dan pengelolaan data transaksi
│           ├── model/
│           │   ├── DataDompet.java           # Pusat data keuangan dan kalkulasi alokasi dana
│           │   ├── DataSesi.java             # Menyimpan data sesi pengguna yang sedang login
│           │   ├── KomponenKeuangan.java     # Abstract class induk komponen keuangan
│           │   ├── Income.java               # Representasi objek pemasukan
│           │   ├── Outcome.java              # Representasi objek pengeluaran
│           │   ├── ModelTransaksi.java       # Model data transaksi (CRUD transaksi)
│           │   └── User.java                 # Model data pengguna
│           ├── scenes/
│           │   ├── DashboardView.java        # Halaman dashboard dan ringkasan keuangan
│           │   ├── TransaksiView.java        # Halaman manajemen transaksi
│           │   ├── AlokasiView.java          # Halaman pengaturan alokasi dana
│           │   ├── DanaDaruratView.java      # Halaman pemantauan dana darurat
│           │   ├── LaporanView.java          # Halaman laporan keuangan
│           │   ├── ProfilView.java           # Halaman profil pengguna
│           │   ├── LoginView.java            # Halaman login
│           │   ├── WelcomeView.java          # Halaman pembuka aplikasi
│           │   └── MainLayout.java           # Layout utama aplikasi
│           ├── components/
│           │   ├── Sidebar.java              # Komponen menu navigasi aplikasi
│           │   ├── CustomCard.java           # Komponen kartu informasi dashboard
│           │   └── LogoSavio.java            # Komponen logo aplikasi
│           ├── utils/
│           │   ├── ColorPalette.java         # Konfigurasi warna aplikasi
│           │   └── KoneksiJSON.java          # Pengelolaan database JSON (load & save)
│           └── MainApp.java                  # Entry point aplikasi JavaFX
├── resources/
│   ├── database_savio.json                   # Database penyimpanan data pengguna dan transaksi
│   └── logo_savio.png                        # Asset logo aplikasi
└── README.md                                 # Dokumentasi proyek
```

## 5. Panduan Menjalankan Program

**a. Prasyarat Sistem(Prerequisites)**  
Sebelum menjalankan aplikasi SAVIO, pastikan perangkat Anda telah memenuhi spesifikasi komponen berikut:
1. *Java Development Kit (JDK)*: Minimal Java 17 atau versi di atasnya (Sangat direkomendasikan menggunakan OpenJDK 17 atau Oracle JDK 17).
2. *Build Tools*: Gradle 8.x (Sudah dibungkus di dalam proyek lewat Gradle Wrapper).
3. *JavaFX SDK*: Versi 17 atau di atasnya (Disesuaikan dengan versi JDK laptop Anda jika dijalankan manual tanpa Gradle).

**b. Clone Repository**  
git clone [https://github.com/username/Savio-PBO-KLP-09.git](https://github.com/username/Savio-PBO-KLP-09.git)

**c. Buka Project**  
Buka project menggunakan IDE seperti:
* IntelliJ IDEA
* Visual Studio Code
* NetBeans

**d. Tambahkan JavaFX SDK**  
Metode 1: Menggunakan Gradle (Rekomendasi Otomatis & Termudah)  
Proyek ini sudah dilengkapi dengan Gradle Wrapper, sehingga Anda tidak perlu mengunduh JavaFX secara manual atau mengatur VM Options.
1. Buka terminal bawaan di dalam IDE Anda (VS Code / IntelliJ).
2. Jalankan perintah berikut untuk membersihkan cache dan meluncurkan aplikasi SAVIO secara otomatis:
   * Windows (Command Prompt/PowerShell):
     ```Bash
     gradlew clean run
     ```
   * Mac / Linux (Terminal):
     ```Bash
     ./gradlew clean run
     ```

Metode 2: Menggunakan JavaFX SDK Manual   
Tambahkan JavaFX SDK ke project.
VM Options:
```Bash
--module-path "PATH_FX/lib" --add-modules javafx.controls
```
Contoh:
```Bash
--module-path "C:/javafx-sdk/lib" --add-modules javafx.controls
```

**e. Jalankan Program**  
Jika konfigurasi di atas selesai, Anda dapat langsung mengeksekusi berkas peluncur utama aplikasi dengan cara klik kanan -> Run:  
Main File Target:
`src/java/com/savio/MainApp.java`  

**f. Alur Penggunaan Aplikasi**  
Setelah program berhasil dijalankan, selanjutnya adalah mengoprasionalkan aplikasi. Berikut adalah tata cara operasional SAVIO:  

***🔐 Tata Cara Operasional Pembuatan Akun Baru (User Registration)**
Bagi pengguna baru yang ingin menggunakan aplikasi SAVIO untuk pertama kalinya, berikut adalah langkah-langkah operasional untuk mendaftarkan akun baru ke dalam sistem:

1. **Mengakses Halaman Registrasi:**
   * Pada halaman login (`LoginView.java`), klik tombol **"Daftar"** atau **"Register"** yang berada di bagian bawah formulir untuk dialihkan ke antarmuka pembuatan akun.

2. **Pengisian Data Kredensial Pengguna:**
   * **Nama Pengguna (Username):** Masukkan nama unik tanpa spasi (Contoh: `natalia_bela`). Sistem akan menggunakan input ini sebagai pengenal utama akun.
   * **Kata Sandi (Password):** Masukkan kombinasi sandi yang aman.
   * **Konfirmasi Kata Sandi:** Masukkan ulang kata sandi Anda untuk memastikan tidak ada kesalahan pengetikan (*typo*).

3. **Proses Validasi dan Enkapsulasi Data (Back-End Engine):**
   * Ketika tombol **"Submit"** atau **"Daftar Akun"** ditekan, data mentah dari kolom input akan ditangkap oleh sistem.
   * Sistem akan melakukan validasi dasar (memastikan semua kolom telah terisi dan memastikan *Password* cocok dengan *Konfirmasi Password*).
   * Data yang valid kemudian akan dibungkus ke dalam objek model pengguna melalui berkas `User.java`.

4. **Penyimpanan Permanen ke Database Lokal:**
   * Objek `User` yang baru dibuat akan dikirimkan ke kelas utilitas `KoneksiJSON.java`.
   * Sistem Back-End akan melakukan proses **Serialisasi**, yaitu mengonversi objek data Java tersebut menjadi format untaian teks (String JSON), lalu menambahkannya ke dalam berkas fisik `database_savio.json`.
   * Jika berhasil, sebuah notifikasi pop-up *“Registrasi Berhasil!”* akan muncul di layar. Pengguna kini sudah bisa kembali ke halaman `LoginView.java` untuk masuk menggunakan akun baru tersebut.

**🔑 Tata Cara Operasional Log Masuk (User Login)**
Bagi pengguna yang telah memiliki akun terdaftar di dalam sistem, berikut adalah alur operasional untuk melakukan autentikasi dan masuk ke antarmuka utama SAVIO:

1. **Pengisian Kredensial Log Masuk:**
   * Pada halaman login (`LoginView.java`), pengguna memasukkan **Username** dan **Password** yang telah didaftarkan sebelumnya ke dalam kolom yang tersedia.

2. **Proses Autentikasi dan Pencocokan Data (Back-End Authentication):**
   * Ketika tombol **"Login"** atau **"Masuk"** ditekan, kelas `LoginView.java` akan meneruskan data input tersebut ke bagian penanganan logika.
   * Sistem melalui `KoneksiJSON.java` akan melakukan proses **Deserialisasi**, yaitu membaca data teks pada berkas `database_savio.json` dan memuat daftar objek pengguna terdaftar ke dalam memori aplikasi.
   * Back-End akan melakukan pencarian dan validasi kondisi (`if-else`):
     * *Kondisi 1 (Valid):* Jika *Username* ditemukan dan *Password* yang diinput cocok dengan data di database JSON, proses autentikasi dinyatakan berhasil.
     * *Kondisi 2 (Invalid):* Jika *Username* tidak ditemukan atau *Password* salah, sistem akan menolak akses dan menampilkan pesan peringatan eror pada antarmuka pengguna.

3. **Inisialisasi Sesi Aktif (Session Management):**
   * Setelah autentikasi berhasil, data pengguna yang bersangkutan akan dibungkus ke dalam kelas **`DataSesi.java`** untuk menandai bahwa pengguna tersebut sedang dalam status sesi aktif (*logged in*).
   * Informasi dari `DataSesi.java` ini yang akan digunakan oleh sistem untuk menarik riwayat transaksi spesifik milik pengguna tersebut di folder pusat data.

4. **Pengalihan ke Antarmuka Utama:**
   * Sistem secara otomatis akan menutup halaman `LoginView.java` dan mengalihkan layar pengguna menuju layout utama aplikasi (`MainLayout.java`) yang langsung mengarah pada halaman ringkasan keuangan (`DashboardView.java`).

## 6. Struktur Kode & Penerapan Pilar OOP
**a. Encapsulation**  
Encapsulation diterapkan dengan penggunaan:
* private attribute,
* getter,
* setter.
Contoh pada file:
`ModelTransaksi.java`
```java
private String deskripsi;

public String getDeskripsi() {
    return deskripsi;
}

public void setDeskripsi(String deskripsi) {
    this.deskripsi = deskripsi;
}
```
Tujuan:
* melindungi data,
* membatasi akses langsung,
* menjaga keamanan objek.

**b. Inheritance**  
Inheritance diterapkan pada:
`KomponenKeuangan.java`
yang menjadi parent class dari:
* Income.java
* Outcome.java
Contoh:
``` java
public class Income extends KomponenKeuangan
public class Outcome extends KomponenKeuangan
```
Tujuan:
* mengurangi duplikasi kode,
* mempermudah pengembangan program.

**c. Abstraction**  
Abstraction diterapkan menggunakan abstract class:
```java
public abstract class KomponenKeuangan
```
dengan abstract method:
``` java
public abstract String getTipeKomponen();
public abstract double hitungNilaiBersih();
```
Child class wajib mengimplementasikan method tersebut.

**d. Polymorphism**  
Polymorphism diterapkan melalui method:
``` java
hitungNilaiBersih()
```
yang memiliki implementasi berbeda pada:
* Income.java
* Outcome.java

Contoh:  
Income.java
``` java
@Override
public double hitungNilaiBersih() {
    return getJumlah();
}
```
Outcome.java
``` java
@Override
public double hitungNilaiBersih() {
    return -getJumlah();
}
```
Method yang sama menghasilkan perilaku berbeda tergantung objek yang digunakan.



## 👥 Tim Pengembang (Kelompok 09)
1. Vivien — Project Manager (PM)
2. Randy — Front End Developer
3. Bela — Back End Developer

---

## 📄 Lisensi 

Hak Cipta (c) 2026 Kelompok 09 PBO

Dengan ini diberikan izin tanpa biaya kepada siapa pun yang mendapatkan salinan perangkat lunak ini dan dokumen terkait untuk **menggunakan, menggandakan, memodifikasi, menggabungkan, memublikasikan, dan mengembangkan lebih lanjut** proyek **SAVIO – Smart Personal Finance Tracker** ini untuk keperluan akademis, pembelajaran, maupun komersial.

***Salam hangat dan selamat menggunakan, dari KLP-PBO-09 untuk para pengguna👋***  
***Terima Kasih.***


